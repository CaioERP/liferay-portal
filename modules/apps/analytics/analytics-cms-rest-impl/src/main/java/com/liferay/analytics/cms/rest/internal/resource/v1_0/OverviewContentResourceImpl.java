/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.v1_0;

import com.liferay.analytics.cms.rest.dto.v1_0.OverviewContent;
import com.liferay.analytics.cms.rest.dto.v1_0.Trend;
import com.liferay.analytics.cms.rest.resource.v1_0.OverviewContentResource;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRelTable;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetEntries_AssetTagsTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.object.model.ObjectDefinitionTable;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectFolderTable;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;

import java.sql.Date;

import java.time.LocalDate;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rachael Koestartyo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/overview-content.properties",
	scope = ServiceScope.PROTOTYPE, service = OverviewContentResource.class
)
public class OverviewContentResourceImpl
	extends BaseOverviewContentResourceImpl {

	@Override
	public OverviewContent getOverviewContent(
			String languageId, Integer rangeKey, Integer spaceId)
		throws Exception {

		AssetCategoryTable assetCategoryTable = AssetCategoryTable.INSTANCE;
		AssetEntries_AssetTagsTable assetEntriesAssetTagsTable =
			AssetEntries_AssetTagsTable.INSTANCE;
		AssetEntryAssetCategoryRelTable assetEntryAssetCategoryRelTable =
			AssetEntryAssetCategoryRelTable.INSTANCE;
		AssetEntryTable assetEntryTable = AssetEntryTable.INSTANCE;
		ObjectDefinitionTable objectDefinitionTable =
			ObjectDefinitionTable.INSTANCE;
		ObjectEntryTable objectEntryTable = ObjectEntryTable.INSTANCE;
		ObjectFolderTable objectFolderTable = ObjectFolderTable.INSTANCE;

		Expression<Boolean> previousExpression =
			DSLFunctionFactoryUtil.caseWhenThen(
				objectEntryTable.createDate.lt(
					Date.valueOf(_getStartDate(rangeKey))),
				true
			).elseEnd(
				false
			);

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			DSLFunctionFactoryUtil.countDistinct(
				assetEntryAssetCategoryRelTable.assetCategoryId
			).as(
				"categoriesCount"
			),
			previousExpression.as("previous"),
			DSLFunctionFactoryUtil.countDistinct(
				assetEntriesAssetTagsTable.tagId
			).as(
				"tagsCount"
			),
			DSLFunctionFactoryUtil.count(
				objectEntryTable.objectEntryId
			).as(
				"totalCount"
			),
			DSLFunctionFactoryUtil.countDistinct(
				assetCategoryTable.vocabularyId
			).as(
				"vocabulariesCount"
			)
		).from(
			objectFolderTable
		).innerJoinON(
			objectDefinitionTable,
			objectDefinitionTable.objectFolderId.eq(
				objectFolderTable.objectFolderId)
		).innerJoinON(
			objectEntryTable,
			objectEntryTable.objectDefinitionId.eq(
				objectDefinitionTable.objectDefinitionId)
		).innerJoinON(
			assetEntryTable,
			assetEntryTable.classPK.eq(objectEntryTable.objectEntryId)
		).leftJoinOn(
			assetEntriesAssetTagsTable,
			assetEntriesAssetTagsTable.entryId.eq(assetEntryTable.entryId)
		).leftJoinOn(
			assetEntryAssetCategoryRelTable,
			assetEntryAssetCategoryRelTable.assetEntryId.eq(
				assetEntryTable.entryId)
		).leftJoinOn(
			assetCategoryTable,
			assetCategoryTable.categoryId.eq(
				assetEntryAssetCategoryRelTable.assetCategoryId)
		).where(
			objectFolderTable.externalReferenceCode.eq(
				"L_CMS_CONTENT_STRUCTURES"
			).and(
				objectEntryTable.createDate.gte(
					Date.valueOf(_getPreviousStartDate(rangeKey)))
			)
		).groupBy(
			previousExpression
		);

		return _toOverviewContent(_objectEntryLocalService.dslQuery(dslQuery));
	}

	private String _getPreviousStartDate(int rangeKey) {
		LocalDate localDate = LocalDate.now();

		localDate = localDate.minusDays(rangeKey * 2L);

		return localDate.toString();
	}

	private String _getStartDate(int rangeKey) {
		LocalDate localDate = LocalDate.now();

		localDate = localDate.minusDays(rangeKey);

		return localDate.toString();
	}

	private OverviewContent _toOverviewContent(List<Object[]> results) {
		long categoriesCount = 0;
		long currentTotalCount = 0;
		long previousTotalCount = 0;
		long tagsCount = 0;
		long vocabulariesCount = 0;

		for (Object[] result : results) {
			if (Boolean.TRUE.equals(result[1])) {
				previousTotalCount = (Long)result[3];
			}
			else {
				categoriesCount = (Long)result[0];
				currentTotalCount = (Long)result[3];
				tagsCount = (Long)result[2];
				vocabulariesCount = (Long)result[4];
			}
		}

		Trend.Classification classification = Trend.Classification.NEUTRAL;
		double percentage = 0.0;

		if (previousTotalCount > 0) {
			double diff = currentTotalCount - previousTotalCount;

			percentage = diff / previousTotalCount * 100;

			if (percentage > 0) {
				classification = Trend.Classification.POSITIVE;
			}
			else if (percentage < 0) {
				classification = Trend.Classification.NEGATIVE;
			}
		}
		else if (currentTotalCount > 0) {
			classification = Trend.Classification.POSITIVE;
			percentage = 100.0;
		}

		return _toOverviewContent(
			categoriesCount, classification, percentage, tagsCount,
			vocabulariesCount);
	}

	private OverviewContent _toOverviewContent(
		long categoriesCount, Trend.Classification classification,
		double percentage, long tagsCount, long vocabulariesCount) {

		Trend trend = new Trend();

		trend.setClassification(() -> classification);
		trend.setPercentage(() -> percentage);

		OverviewContent overviewContent = new OverviewContent();

		overviewContent.setCategoriesCount(() -> categoriesCount);
		overviewContent.setTagsCount(() -> tagsCount);
		overviewContent.setTrend(() -> trend);
		overviewContent.setVocabulariesCount(() -> vocabulariesCount);

		return overviewContent;
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}