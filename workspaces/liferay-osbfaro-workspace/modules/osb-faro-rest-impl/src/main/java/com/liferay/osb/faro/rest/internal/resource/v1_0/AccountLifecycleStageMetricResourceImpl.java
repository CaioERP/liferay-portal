/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageMetric;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.resource.v1_0.AccountLifecycleStageMetricResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-lifecycle-stage-metric.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AccountLifecycleStageMetricResource.class
)
public class AccountLifecycleStageMetricResourceImpl
	extends BaseAccountLifecycleStageMetricResourceImpl {

	@Override
	public Page<AccountLifecycleStageMetric>
			getWorkspaceGroupAccountLifecycleStageMetricsPage(
				Long groupId, String accountLifecycleId, String country,
				String industry, Long segmentId)
		throws Exception {

		return Page.of(
			transform(
				_contactsEngineClient.getAccountLifecycleStageMetrics(
					_faroProjectLocalService.getFaroProjectByGroupId(groupId),
					country, accountLifecycleId, industry, segmentId),
				accountLifecycleStageMetric ->
					_accountLifecycleStageMetricDTOConverter.toDTO(
						new FaroDTOConverterContext(
							contextAcceptLanguage.isAcceptAllLanguages(),
							accountLifecycleId,
							contextAcceptLanguage.getPreferredLocale()),
						accountLifecycleStageMetric)));
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.AccountLifecycleStageMetricDTOConverter)"
	)
	private DTOConverter
		<com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric,
		 AccountLifecycleStageMetric> _accountLifecycleStageMetricDTOConverter;

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}