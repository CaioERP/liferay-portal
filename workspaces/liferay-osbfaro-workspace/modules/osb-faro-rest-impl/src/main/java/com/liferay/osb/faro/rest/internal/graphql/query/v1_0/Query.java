/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.query.v1_0;

import com.liferay.osb.faro.rest.dto.v1_0.Account;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycle;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleHistory;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageMetric;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageTransition;
import com.liferay.osb.faro.rest.dto.v1_0.Acquisition;
import com.liferay.osb.faro.rest.dto.v1_0.AssetSummaryMetric;
import com.liferay.osb.faro.rest.dto.v1_0.Channel;
import com.liferay.osb.faro.rest.dto.v1_0.Event;
import com.liferay.osb.faro.rest.dto.v1_0.EventAttributeDefinition;
import com.liferay.osb.faro.rest.dto.v1_0.EventDefinition;
import com.liferay.osb.faro.rest.dto.v1_0.EventMetric;
import com.liferay.osb.faro.rest.dto.v1_0.Individual;
import com.liferay.osb.faro.rest.dto.v1_0.IndividualSegment;
import com.liferay.osb.faro.rest.dto.v1_0.IndividualSegmentMembership;
import com.liferay.osb.faro.rest.dto.v1_0.IndividualSegmentMembershipChange;
import com.liferay.osb.faro.rest.dto.v1_0.IndividualSegmentMembershipChangeMetric;
import com.liferay.osb.faro.rest.dto.v1_0.PageMetric;
import com.liferay.osb.faro.rest.dto.v1_0.SearchTerm;
import com.liferay.osb.faro.rest.dto.v1_0.UserSession;
import com.liferay.osb.faro.rest.dto.v1_0.VisitFrequencyMetric;
import com.liferay.osb.faro.rest.dto.v1_0.VisitorMetric;
import com.liferay.osb.faro.rest.dto.v1_0.Workspace;
import com.liferay.osb.faro.rest.resource.v1_0.AccountLifecycleHistoryResource;
import com.liferay.osb.faro.rest.resource.v1_0.AccountLifecycleResource;
import com.liferay.osb.faro.rest.resource.v1_0.AccountLifecycleStageMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.AccountLifecycleStageTransitionResource;
import com.liferay.osb.faro.rest.resource.v1_0.AccountResource;
import com.liferay.osb.faro.rest.resource.v1_0.AcquisitionResource;
import com.liferay.osb.faro.rest.resource.v1_0.AssetSummaryMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.ChannelResource;
import com.liferay.osb.faro.rest.resource.v1_0.EventAttributeDefinitionResource;
import com.liferay.osb.faro.rest.resource.v1_0.EventDefinitionResource;
import com.liferay.osb.faro.rest.resource.v1_0.EventMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.EventResource;
import com.liferay.osb.faro.rest.resource.v1_0.IndividualResource;
import com.liferay.osb.faro.rest.resource.v1_0.IndividualSegmentMembershipChangeMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.IndividualSegmentMembershipChangeResource;
import com.liferay.osb.faro.rest.resource.v1_0.IndividualSegmentMembershipResource;
import com.liferay.osb.faro.rest.resource.v1_0.IndividualSegmentResource;
import com.liferay.osb.faro.rest.resource.v1_0.PageMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.SearchTermResource;
import com.liferay.osb.faro.rest.resource.v1_0.UserSessionResource;
import com.liferay.osb.faro.rest.resource.v1_0.VisitFrequencyMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.VisitorMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.WorkspaceResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class Query {

	public static void setAccountResourceComponentServiceObjects(
		ComponentServiceObjects<AccountResource>
			accountResourceComponentServiceObjects) {

		_accountResourceComponentServiceObjects =
			accountResourceComponentServiceObjects;
	}

	public static void setAccountLifecycleResourceComponentServiceObjects(
		ComponentServiceObjects<AccountLifecycleResource>
			accountLifecycleResourceComponentServiceObjects) {

		_accountLifecycleResourceComponentServiceObjects =
			accountLifecycleResourceComponentServiceObjects;
	}

	public static void
		setAccountLifecycleHistoryResourceComponentServiceObjects(
			ComponentServiceObjects<AccountLifecycleHistoryResource>
				accountLifecycleHistoryResourceComponentServiceObjects) {

		_accountLifecycleHistoryResourceComponentServiceObjects =
			accountLifecycleHistoryResourceComponentServiceObjects;
	}

	public static void
		setAccountLifecycleStageMetricResourceComponentServiceObjects(
			ComponentServiceObjects<AccountLifecycleStageMetricResource>
				accountLifecycleStageMetricResourceComponentServiceObjects) {

		_accountLifecycleStageMetricResourceComponentServiceObjects =
			accountLifecycleStageMetricResourceComponentServiceObjects;
	}

	public static void
		setAccountLifecycleStageTransitionResourceComponentServiceObjects(
			ComponentServiceObjects<AccountLifecycleStageTransitionResource>
				accountLifecycleStageTransitionResourceComponentServiceObjects) {

		_accountLifecycleStageTransitionResourceComponentServiceObjects =
			accountLifecycleStageTransitionResourceComponentServiceObjects;
	}

	public static void setAcquisitionResourceComponentServiceObjects(
		ComponentServiceObjects<AcquisitionResource>
			acquisitionResourceComponentServiceObjects) {

		_acquisitionResourceComponentServiceObjects =
			acquisitionResourceComponentServiceObjects;
	}

	public static void setAssetSummaryMetricResourceComponentServiceObjects(
		ComponentServiceObjects<AssetSummaryMetricResource>
			assetSummaryMetricResourceComponentServiceObjects) {

		_assetSummaryMetricResourceComponentServiceObjects =
			assetSummaryMetricResourceComponentServiceObjects;
	}

	public static void setChannelResourceComponentServiceObjects(
		ComponentServiceObjects<ChannelResource>
			channelResourceComponentServiceObjects) {

		_channelResourceComponentServiceObjects =
			channelResourceComponentServiceObjects;
	}

	public static void setEventResourceComponentServiceObjects(
		ComponentServiceObjects<EventResource>
			eventResourceComponentServiceObjects) {

		_eventResourceComponentServiceObjects =
			eventResourceComponentServiceObjects;
	}

	public static void
		setEventAttributeDefinitionResourceComponentServiceObjects(
			ComponentServiceObjects<EventAttributeDefinitionResource>
				eventAttributeDefinitionResourceComponentServiceObjects) {

		_eventAttributeDefinitionResourceComponentServiceObjects =
			eventAttributeDefinitionResourceComponentServiceObjects;
	}

	public static void setEventDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<EventDefinitionResource>
			eventDefinitionResourceComponentServiceObjects) {

		_eventDefinitionResourceComponentServiceObjects =
			eventDefinitionResourceComponentServiceObjects;
	}

	public static void setEventMetricResourceComponentServiceObjects(
		ComponentServiceObjects<EventMetricResource>
			eventMetricResourceComponentServiceObjects) {

		_eventMetricResourceComponentServiceObjects =
			eventMetricResourceComponentServiceObjects;
	}

	public static void setIndividualResourceComponentServiceObjects(
		ComponentServiceObjects<IndividualResource>
			individualResourceComponentServiceObjects) {

		_individualResourceComponentServiceObjects =
			individualResourceComponentServiceObjects;
	}

	public static void setIndividualSegmentResourceComponentServiceObjects(
		ComponentServiceObjects<IndividualSegmentResource>
			individualSegmentResourceComponentServiceObjects) {

		_individualSegmentResourceComponentServiceObjects =
			individualSegmentResourceComponentServiceObjects;
	}

	public static void
		setIndividualSegmentMembershipResourceComponentServiceObjects(
			ComponentServiceObjects<IndividualSegmentMembershipResource>
				individualSegmentMembershipResourceComponentServiceObjects) {

		_individualSegmentMembershipResourceComponentServiceObjects =
			individualSegmentMembershipResourceComponentServiceObjects;
	}

	public static void
		setIndividualSegmentMembershipChangeResourceComponentServiceObjects(
			ComponentServiceObjects<IndividualSegmentMembershipChangeResource>
				individualSegmentMembershipChangeResourceComponentServiceObjects) {

		_individualSegmentMembershipChangeResourceComponentServiceObjects =
			individualSegmentMembershipChangeResourceComponentServiceObjects;
	}

	public static void
		setIndividualSegmentMembershipChangeMetricResourceComponentServiceObjects(
			ComponentServiceObjects
				<IndividualSegmentMembershipChangeMetricResource>
					individualSegmentMembershipChangeMetricResourceComponentServiceObjects) {

		_individualSegmentMembershipChangeMetricResourceComponentServiceObjects =
			individualSegmentMembershipChangeMetricResourceComponentServiceObjects;
	}

	public static void setPageMetricResourceComponentServiceObjects(
		ComponentServiceObjects<PageMetricResource>
			pageMetricResourceComponentServiceObjects) {

		_pageMetricResourceComponentServiceObjects =
			pageMetricResourceComponentServiceObjects;
	}

	public static void setSearchTermResourceComponentServiceObjects(
		ComponentServiceObjects<SearchTermResource>
			searchTermResourceComponentServiceObjects) {

		_searchTermResourceComponentServiceObjects =
			searchTermResourceComponentServiceObjects;
	}

	public static void setUserSessionResourceComponentServiceObjects(
		ComponentServiceObjects<UserSessionResource>
			userSessionResourceComponentServiceObjects) {

		_userSessionResourceComponentServiceObjects =
			userSessionResourceComponentServiceObjects;
	}

	public static void setVisitFrequencyMetricResourceComponentServiceObjects(
		ComponentServiceObjects<VisitFrequencyMetricResource>
			visitFrequencyMetricResourceComponentServiceObjects) {

		_visitFrequencyMetricResourceComponentServiceObjects =
			visitFrequencyMetricResourceComponentServiceObjects;
	}

	public static void setVisitorMetricResourceComponentServiceObjects(
		ComponentServiceObjects<VisitorMetricResource>
			visitorMetricResourceComponentServiceObjects) {

		_visitorMetricResourceComponentServiceObjects =
			visitorMetricResourceComponentServiceObjects;
	}

	public static void setWorkspaceResourceComponentServiceObjects(
		ComponentServiceObjects<WorkspaceResource>
			workspaceResourceComponentServiceObjects) {

		_workspaceResourceComponentServiceObjects =
			workspaceResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupAccount(accountId: ___, groupId: ___){accountName, accountType, activitiesCount, annualRevenue, country, dateModified, firstActivityDate, id, industry, lastActivityDate, lifecycleStage, numberOfEmployees, website}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetch a single account by ID from an LDP workspace. Use this when you already have an account ID. To search accounts by name or filter, use `getWorkspaceGroupChannelAccountsPage`."
	)
	public Account workspaceGroupAccount(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("accountId") String accountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.getWorkspaceGroupAccount(
				groupId, accountId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAccounts(accountLifecycleId: ___, channelId: ___, groupId: ___, lifecycleStage: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List or search accounts synced to an LDP workspace. Optionally narrow results to a single channel (also known as property). Optionally narrow results to a single lifecycle stage with `lifecycleStage`, passing the name of a stage; the stage is looked up in the account lifecycle given by `accountLifecycleId`, or in the channel's default account lifecycle when it is omitted. A channel can have several account lifecycles; use `getWorkspaceGroupChannelAccountLifecyclesPage` to list them. Use this to browse or search accounts by name. To fetch a single account by ID, use `getWorkspaceGroupAccount`. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public AccountPage workspaceGroupChannelAccounts(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountLifecycleId") String accountLifecycleId,
			@GraphQLName("lifecycleStage") String lifecycleStage,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> new AccountPage(
				accountResource.getWorkspaceGroupChannelAccountsPage(
					groupId, channelId, accountLifecycleId, lifecycleStage,
					rangeEnd, rangeKey, rangeStart, search,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(accountResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAccountLifecycles(channelId: ___, groupId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the account lifecycles of a channel (also known as property), each with its stages in display order. A channel can have several lifecycles, and exactly one of them is its default. Use this to find the `accountLifecycleId` that the other account lifecycle tools and `getWorkspaceGroupChannelAccountsPage` take, and the stage names that the stage filters accept."
	)
	public AccountLifecyclePage workspaceGroupChannelAccountLifecycles(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountLifecycleResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountLifecycleResource -> new AccountLifecyclePage(
				accountLifecycleResource.
					getWorkspaceGroupChannelAccountLifecyclesPage(
						groupId, channelId, search,
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							accountLifecycleResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAccountAccountLifecycleHistory(accountId: ___, accountLifecycleId: ___, channelId: ___, groupId: ___){accountLifecycleStageHistories, id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "The stage history of one account in an account lifecycle: every stage of the lifecycle in display order, with when the account most recently entered and left it. The stage with a `startDate` and no `endDate` is the one the account is in now. Use this to answer 'how long has this account been in its stage' or 'when did it become a customer' style questions. To see moves across many accounts, use `getWorkspaceGroupAccountLifecycleStageTransitionsPage` instead. The lifecycle must belong to the channel; otherwise the call returns 404."
	)
	public AccountLifecycleHistory
			workspaceGroupChannelAccountAccountLifecycleHistory(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("channelId") String channelId,
				@GraphQLName("accountId") String accountId,
				@GraphQLName("accountLifecycleId") String accountLifecycleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountLifecycleHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountLifecycleHistoryResource ->
				accountLifecycleHistoryResource.
					getWorkspaceGroupChannelAccountAccountLifecycleHistory(
						groupId, channelId, accountId, accountLifecycleId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAccountLifecycleStageMetrics(accountLifecycleId: ___, channelId: ___, country: ___, groupId: ___, industry: ___, page: ___, pageSize: ___, segmentId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Per-stage metrics of an account lifecycle, one entry per stage in display order: how many accounts are in each stage now, their share of the lifecycle, the average days spent in the stage and the conversion rate to the next stage. Durations and conversion rates cover the last 90 days; the date range can't be changed. Use this to answer 'where do accounts stall' style questions. Optionally narrow the accounts by country, industry or segment membership. A lifecycle has at most 20 stages, so the default page returns them all. The lifecycle must belong to the channel; otherwise the call returns 404."
	)
	public AccountLifecycleStageMetricPage
			workspaceGroupChannelAccountLifecycleStageMetrics(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("channelId") String channelId,
				@GraphQLName("accountLifecycleId") String accountLifecycleId,
				@GraphQLName("country") String country,
				@GraphQLName("industry") String industry,
				@GraphQLName("segmentId") Long segmentId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountLifecycleStageMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountLifecycleStageMetricResource ->
				new AccountLifecycleStageMetricPage(
					accountLifecycleStageMetricResource.
						getWorkspaceGroupChannelAccountLifecycleStageMetricsPage(
							groupId, channelId, accountLifecycleId, country,
							industry, segmentId,
							Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupAccountLifecycleStageTransitions(accountLifecycleId: ___, country: ___, fromLifecycleStage: ___, groupId: ___, industry: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, segmentId: ___, sorts: ___, toLifecycleStage: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the accounts that moved from one account lifecycle stage to another within a date range, one entry per move and most recent first. Each entry names the account, the stage it left, the stage it entered, and when the move happened. Filter by origin stage (`fromLifecycleStage`), destination stage (`toLifecycleStage`), account country or industry, or segment membership. Lifecycle stage values are stage names (e.g. 'Pipeline' or 'at risk'), matched ignoring case and surrounding spaces. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 90 days are used. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public AccountLifecycleStageTransitionPage
			workspaceGroupAccountLifecycleStageTransitions(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("accountLifecycleId") String accountLifecycleId,
				@GraphQLName("country") String country,
				@GraphQLName("fromLifecycleStage") String fromLifecycleStage,
				@GraphQLName("industry") String industry,
				@GraphQLName("rangeEnd") String rangeEnd,
				@GraphQLName("rangeKey") String rangeKey,
				@GraphQLName("rangeStart") String rangeStart,
				@GraphQLName("segmentId") Long segmentId,
				@GraphQLName("toLifecycleStage") String toLifecycleStage,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountLifecycleStageTransitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountLifecycleStageTransitionResource ->
				new AccountLifecycleStageTransitionPage(
					accountLifecycleStageTransitionResource.
						getWorkspaceGroupAccountLifecycleStageTransitionsPage(
							groupId, accountLifecycleId, country,
							fromLifecycleStage, industry, rangeEnd, rangeKey,
							rangeStart, segmentId, toLifecycleStage,
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								accountLifecycleStageTransitionResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAcquisitions(accountId: ___, channelId: ___, groupId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, segmentId: ___, type: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List where the sessions of a channel (also known as property) came from in a date range, in descending order of number of sessions. `type` picks the breakdown: CHANNEL (the acquisition channel LDP assigns to each session, such as direct), REFERRER (referring URL), or SOURCE_MEDIUM (`source / medium` pairs from UTM parameters); it defaults to CHANNEL. Optionally narrow to the individuals of an account with `accountId` or of a segment with `segmentId`. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window of at most 180 days; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Use this for 'top traffic sources' or 'top referrers' style queries. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public AcquisitionPage workspaceGroupChannelAcquisitions(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("segmentId") String segmentId,
			@GraphQLName("type") String type,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_acquisitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			acquisitionResource -> new AcquisitionPage(
				acquisitionResource.getWorkspaceGroupChannelAcquisitionsPage(
					groupId, channelId, accountId, rangeEnd, rangeKey,
					rangeStart, segmentId, type,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAssetSummaries(accountId: ___, channelId: ___, groupId: ___, individualId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, segmentId: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List analytics asset summaries for pages, blogs, documents, forms, journal articles, and object entries. Rank summaries by the requested sort metric. Each summary includes download, impression, read, and view counts along with their period over period trend percentages. Optionally narrow results to a single channel (also known as property) or to a date range. Optionally narrow results further with `accountId`, `individualId`, or `segmentId` to report only on the activity of the individuals in that account, that individual, or the individuals in that segment. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Use this to answer 'what content is performing best' and to pick assets for deeper drill-down via `getWorkspaceGroupChannelPagesPage`. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public AssetSummaryMetricPage workspaceGroupChannelAssetSummaries(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("segmentId") String segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_assetSummaryMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			assetSummaryMetricResource -> new AssetSummaryMetricPage(
				assetSummaryMetricResource.
					getWorkspaceGroupChannelAssetSummariesPage(
						groupId, channelId, accountId, individualId, rangeEnd,
						rangeKey, rangeStart, search, segmentId,
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							assetSummaryMetricResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannel(channelId: ___, groupId: ___){id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetch a single channel (also known as property) from an LDP workspace. To list available channels in the workspace use `getWorkspaceGroupChannelsPage`."
	)
	public Channel workspaceGroupChannel(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.getWorkspaceGroupChannel(
				groupId, channelId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannels(groupId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List channels configured within an LDP workspace. To fetch a single channel by ID, use `getWorkspaceGroupChannel`."
	)
	public ChannelPage workspaceGroupChannels(
			@GraphQLName("groupId") Long groupId)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> new ChannelPage(
				channelResource.getWorkspaceGroupChannelsPage(groupId)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelEvents(accountId: ___, channelId: ___, groupId: ___, includeAnonymousUsers: ___, individualId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, segmentId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List tracked analytics events for a specific channel (also known as property), optionally narrowed to a date range. Optionally narrow results further with `accountId`, `individualId`, or `segmentId` to list only the events from the individuals in that account, from that individual, or from the individuals in that segment. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. For aggregated metrics across events, prefer `getWorkspaceGroupChannelAssetSummariesPage` or `getWorkspaceGroupChannelPagesPage`. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public EventPage workspaceGroupChannelEvents(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("includeAnonymousUsers") Boolean includeAnonymousUsers,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("segmentId") String segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_eventResourceComponentServiceObjects,
			this::_populateResourceContext,
			eventResource -> new EventPage(
				eventResource.getWorkspaceGroupChannelEventsPage(
					groupId, channelId, accountId, includeAnonymousUsers,
					individualId, rangeEnd, rangeKey, rangeStart, search,
					segmentId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupEventAttributeDefinitions(attributeType: ___, eventDefinitionId: ___, groupId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the event attribute definitions of an LDP workspace: the attribute keys events carry, with their data type and a sample value. Pass `eventDefinitionId` (from `getWorkspaceGroupEventDefinitionsPage`) to list only the attributes of that event; an unknown ID returns an empty list. Use this to learn which keys appear in `Event.attributes` and how to read their values."
	)
	public EventAttributeDefinitionPage workspaceGroupEventAttributeDefinitions(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("attributeType") String attributeType,
			@GraphQLName("eventDefinitionId") String eventDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_eventAttributeDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			eventAttributeDefinitionResource ->
				new EventAttributeDefinitionPage(
					eventAttributeDefinitionResource.
						getWorkspaceGroupEventAttributeDefinitionsPage(
							groupId, attributeType, eventDefinitionId, search,
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								eventAttributeDefinitionResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupEventDefinitions(blocked: ___, eventType: ___, groupId: ___, hidden: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the event definitions of an LDP workspace: every event name that the workspace has seen, across all channels, with its display name and whether it's a DEFAULT or CUSTOM event. Use this to find out which events exist before querying events, and to find the `eventDefinitionId` that `getWorkspaceGroupEventAttributeDefinitionsPage` takes."
	)
	public EventDefinitionPage workspaceGroupEventDefinitions(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("blocked") Boolean blocked,
			@GraphQLName("eventType") String eventType,
			@GraphQLName("hidden") Boolean hidden,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_eventDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			eventDefinitionResource -> new EventDefinitionPage(
				eventDefinitionResource.getWorkspaceGroupEventDefinitionsPage(
					groupId, blocked, eventType, hidden, search,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						eventDefinitionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAccountEventMetric(accountId: ___, channelId: ___, groupId: ___, interval: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___){totalEvents, totalSessions}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Event and session totals for all individuals associated with an account over a date range, each with the previous period's total, a trend percentage, and a time series bucketed by `interval`. Use this to answer 'has engagement dropped compared to last month' style questions: compare `value` to `previousValue`, or read the `histogram` with `interval=MONTH`. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public EventMetric workspaceGroupChannelAccountEventMetric(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("interval") String interval,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search)
		throws Exception {

		return _applyComponentServiceObjects(
			_eventMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			eventMetricResource ->
				eventMetricResource.getWorkspaceGroupChannelAccountEventMetric(
					groupId, channelId, accountId, interval, rangeEnd, rangeKey,
					rangeStart, search));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelIndividualEventMetric(channelId: ___, groupId: ___, individualId: ___, interval: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___){totalEvents, totalSessions}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Event and session totals for a single individual over a date range, each with the previous period's total, a trend percentage, and a time series bucketed by `interval`. Use this to answer 'has engagement dropped compared to last month' style questions: compare `value` to `previousValue`, or read the `histogram` with `interval=MONTH`. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public EventMetric workspaceGroupChannelIndividualEventMetric(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("interval") String interval,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search)
		throws Exception {

		return _applyComponentServiceObjects(
			_eventMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			eventMetricResource ->
				eventMetricResource.
					getWorkspaceGroupChannelIndividualEventMetric(
						groupId, channelId, individualId, interval, rangeEnd,
						rangeKey, rangeStart, search));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelIndividuals(accountId: ___, activityStatus: ___, channelId: ___, groupId: ___, individualSegmentId: ___, interestName: ___, page: ___, pageSize: ___, profileType: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List individuals for an LDP workspace. Optionally narrowed to an associated account, segment, interest, activity status, profile type, or date range, and searchable by name or email with `search`. Both KNOWN and ANONYMOUS individuals are returned unless `profileType` is set; pass KNOWN to list only identified people. Use this for queries to find a person. To fetch a single individual by ID, use `getWorkspaceGroupIndividual` instead. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, individuals are not filtered by activity date. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public IndividualPage workspaceGroupChannelIndividuals(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("activityStatus") String activityStatus,
			@GraphQLName("individualSegmentId") String individualSegmentId,
			@GraphQLName("interestName") String interestName,
			@GraphQLName("profileType") String profileType,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualResource -> new IndividualPage(
				individualResource.getWorkspaceGroupChannelIndividualsPage(
					groupId, channelId, accountId, activityStatus,
					individualSegmentId, interestName, profileType, rangeEnd,
					rangeKey, rangeStart, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(individualResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupIndividual(channelId: ___, groupId: ___, individualId: ___){accountName, activitiesCount, activityStatus, averageSessionDuration, dateCreated, dateModified, demographics, emailAddress, firstActivityDate, id, knownSinceDate, lastActivityDate, lastSessionCountry, name, profileType, sessionsCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetch a single Individual from an LDP workspace. Optionally narrowed to a channel (also known as property). Use this to fetch a contact's full profile once you have an ID. To search individuals by name, email, or other attributes, use `getWorkspaceGroupChannelIndividualsPage`."
	)
	public Individual workspaceGroupIndividual(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("channelId") String channelId)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualResource ->
				individualResource.getWorkspaceGroupIndividual(
					groupId, individualId, channelId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelIndividualSegments(channelId: ___, groupId: ___, name: ___, page: ___, pageSize: ___, search: ___, status: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List individual segments within an LDP workspace. Optionally narrowed to a channel (also known as a property). To fetch a single segment by ID, use `getWorkspaceGroupIndividualSegment`. To list members of a segment, use `getWorkspaceGroupIndividualSegmentMembershipsPage`."
	)
	public IndividualSegmentPage workspaceGroupChannelIndividualSegments(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("name") String name,
			@GraphQLName("search") String search,
			@GraphQLName("status") String status,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualSegmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualSegmentResource -> new IndividualSegmentPage(
				individualSegmentResource.
					getWorkspaceGroupChannelIndividualSegmentsPage(
						groupId, channelId, name, search, status,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupIndividualIndividualSegments(channelId: ___, groupId: ___, individualId: ___, page: ___, pageSize: ___, search: ___, status: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the individual segments a single individual belongs to, or formerly belonged to. Use this to check whether a person is already in a nurture or intent segment before recommending an action. To list all segments in a channel, use `getWorkspaceGroupChannelIndividualSegmentsPage`."
	)
	public IndividualSegmentPage workspaceGroupIndividualIndividualSegments(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("search") String search,
			@GraphQLName("status") String status,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualSegmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualSegmentResource -> new IndividualSegmentPage(
				individualSegmentResource.
					getWorkspaceGroupIndividualIndividualSegmentsPage(
						groupId, individualId, channelId, search, status,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupIndividualSegment(groupId: ___, individualSegmentId: ___){activeIndividualCount, anonymousIndividualCount, channelId, dateCreated, dateModified, filter, id, includeAnonymousUsers, individualCount, knownIndividualCount, lastActivityDate, name, segmentType, state, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Fetch a single individual segment by ID from an LDP workspace. To list segments, use `getWorkspaceGroupChannelIndividualSegmentsPage`. To list members of a segment, use `getWorkspaceGroupIndividualSegmentMembershipsPage`."
	)
	public IndividualSegment workspaceGroupIndividualSegment(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("individualSegmentId") String individualSegmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualSegmentResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualSegmentResource ->
				individualSegmentResource.getWorkspaceGroupIndividualSegment(
					groupId, individualSegmentId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupIndividualSegmentMemberships(groupId: ___, individualSegmentId: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List individuals currently or formerly in an individual segment. Each entry links one individual to the individual segment and records when the individual entered (and, if applicable, exited). Use the resulting `individualId` values with `getWorkspaceGroupIndividual` to retrieve the full individual record."
	)
	public IndividualSegmentMembershipPage
			workspaceGroupIndividualSegmentMemberships(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("individualSegmentId") String individualSegmentId,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualSegmentMembershipResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualSegmentMembershipResource ->
				new IndividualSegmentMembershipPage(
					individualSegmentMembershipResource.
						getWorkspaceGroupIndividualSegmentMembershipsPage(
							groupId, individualSegmentId,
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								individualSegmentMembershipResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupIndividualSegmentMembershipChanges(groupId: ___, individualSegmentId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Deprecated: always returns an empty page. The analytics engine does not record which individuals entered or left a segment, so do not use this to find who joined or exited a segment. `rangeStart` and `rangeEnd` must be given together; a request with only one of them fails. For how a segment grew or shrank over a date range, use `getWorkspaceGroupIndividualSegmentMembershipChangeMetric`. For the current members, use `getWorkspaceGroupChannelIndividualsPage` with `individualSegmentId`. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public IndividualSegmentMembershipChangePage
			workspaceGroupIndividualSegmentMembershipChanges(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("individualSegmentId") String individualSegmentId,
				@GraphQLName("rangeEnd") String rangeEnd,
				@GraphQLName("rangeKey") String rangeKey,
				@GraphQLName("rangeStart") String rangeStart,
				@GraphQLName("search") String search,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualSegmentMembershipChangeResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualSegmentMembershipChangeResource ->
				new IndividualSegmentMembershipChangePage(
					individualSegmentMembershipChangeResource.
						getWorkspaceGroupIndividualSegmentMembershipChangesPage(
							groupId, individualSegmentId, rangeEnd, rangeKey,
							rangeStart, search, Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								individualSegmentMembershipChangeResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupIndividualSegmentMembershipChangeMetric(groupId: ___, individualSegmentId: ___, rangeKey: ___){addedIndividuals, individuals, knownIndividuals, removedIndividuals}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "How an individual segment's membership changed over a date range: its size at the end of the range and how much it grew and shrank, each with the preceding range's figure, a trend percentage, and a daily time series. Use this to answer 'is this segment growing or shrinking' style questions. Growth and shrinkage are net per day and cannot report individuals joining and leaving on the same day; see the `IndividualSegmentMembershipChangeMetric` schema. Buckets are always daily and the range always ends today, so pass `rangeKey` as one of LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR. LAST_30_DAYS is used when omitted. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public IndividualSegmentMembershipChangeMetric
			workspaceGroupIndividualSegmentMembershipChangeMetric(
				@GraphQLName("groupId") Long groupId,
				@GraphQLName("individualSegmentId") String individualSegmentId,
				@GraphQLName("rangeKey") String rangeKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_individualSegmentMembershipChangeMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			individualSegmentMembershipChangeMetricResource ->
				individualSegmentMembershipChangeMetricResource.
					getWorkspaceGroupIndividualSegmentMembershipChangeMetric(
						groupId, individualSegmentId, rangeKey));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelPages(accountId: ___, channelId: ___, dataSourceId: ___, groupId: ___, individualId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___, segmentId: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List analytics metrics for tracked pages on the workspace, ranked by visitors or another metric, optionally narrowed to a single channel (also known as property) or data source. Returns flattened view, visitor, bounce, exit, and access path metrics for each page. Optionally narrow results further with `accountId`, `individualId`, or `segmentId` to report only on the activity of the individuals in that account, that individual, or the individuals in that segment. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Use this for 'top pages' style queries. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public PageMetricPage workspaceGroupChannelPages(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("dataSourceId") String dataSourceId,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("segmentId") String segmentId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageMetricResource -> new PageMetricPage(
				pageMetricResource.getWorkspaceGroupChannelPagesPage(
					groupId, channelId, accountId, dataSourceId, individualId,
					rangeEnd, rangeKey, rangeStart, search, segmentId,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(pageMetricResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelSearchTerms(channelId: ___, groupId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List search terms used on pages tracked by the LDP workspace for a date range in a single channel (also known as property). Results are ordered in descending order of number of times terms have been searched. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Use this for 'most searched words' style queries. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public SearchTermPage workspaceGroupChannelSearchTerms(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_searchTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchTermResource -> new SearchTermPage(
				searchTermResource.getWorkspaceGroupChannelSearchTermsPage(
					groupId, channelId, rangeEnd, rangeKey, rangeStart,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelAccountUserSessions(accountId: ___, channelId: ___, groupId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the browsing sessions of the individuals associated with an account over a date range, most recent first, each with the events recorded during it. Use this to see what a specific account or person actually did recently. Pagination counts page groups, not sessions: a page group is all the events of a session on the same page URL, and every other event is a group of its own. Each page returns up to `pageSize` page groups merged into their sessions, so a page can hold fewer than `pageSize` sessions and a long session can continue on the next page. `totalCount` and `lastPage` are expressed in page groups; keep requesting pages until `page` reaches `lastPage`. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public UserSessionPage workspaceGroupChannelAccountUserSessions(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("accountId") String accountId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_userSessionResourceComponentServiceObjects,
			this::_populateResourceContext,
			userSessionResource -> new UserSessionPage(
				userSessionResource.
					getWorkspaceGroupChannelAccountUserSessionsPage(
						groupId, channelId, accountId, rangeEnd, rangeKey,
						rangeStart, search, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelIndividualUserSessions(channelId: ___, groupId: ___, individualId: ___, page: ___, pageSize: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List the browsing sessions of a single individual over a date range, most recent first, each with the events recorded during it. Use this to see what a specific account or person actually did recently. Pagination counts page groups, not sessions: a page group is all the events of a session on the same page URL, and every other event is a group of its own. Each page returns up to `pageSize` page groups merged into their sessions, so a page can hold fewer than `pageSize` sessions and a long session can continue on the next page. `totalCount` and `lastPage` are expressed in page groups; keep requesting pages until `page` reaches `lastPage`. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, LAST_YEAR, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public UserSessionPage workspaceGroupChannelIndividualUserSessions(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("individualId") String individualId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_userSessionResourceComponentServiceObjects,
			this::_populateResourceContext,
			userSessionResource -> new UserSessionPage(
				userSessionResource.
					getWorkspaceGroupChannelIndividualUserSessionsPage(
						groupId, channelId, individualId, rangeEnd, rangeKey,
						rangeStart, search, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelVisitFrequencyMetric(channelId: ___, groupId: ___, rangeEnd: ___, rangeKey: ___, rangeStart: ___){biWeeklyVisitors, dailyVisitors, monthlyVisitors, occasionalVisitors, visitors, weeklyVisitors}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Get how often the visitors of a channel (also known as property) came back in a date range, as counts of daily, weekly, bi-weekly, monthly and occasional visitors. The buckets are relative to the length of the range, so compare ranges of the same length. It can't be narrowed to a segment or an account. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS, or pass both `rangeStart` and `rangeEnd` as `yyyy-MM-dd` dates for a custom window of at most 180 days; a custom window takes precedence over `rangeKey`. Without either, the last 30 days are used. Use this for 'how loyal are our visitors' style queries. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public VisitFrequencyMetric workspaceGroupChannelVisitFrequencyMetric(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("rangeEnd") String rangeEnd,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("rangeStart") String rangeStart)
		throws Exception {

		return _applyComponentServiceObjects(
			_visitFrequencyMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			visitFrequencyMetricResource ->
				visitFrequencyMetricResource.
					getWorkspaceGroupChannelVisitFrequencyMetric(
						groupId, channelId, rangeEnd, rangeKey, rangeStart));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaceGroupChannelVisitorMetric(channelId: ___, groupId: ___, rangeKey: ___, segmentId: ___){averageSessionDuration, knownVisitors, totalSessionDuration, visitors}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Get the visitor totals of a channel (also known as property) for a date range: unique visitors, known visitors (identified by email), and the average and total session duration. Optionally narrow to the individuals in a segment with `segmentId`. For date range filtering pass `rangeKey` as one of LAST_24_HOURS, YESTERDAY, LAST_7_DAYS, LAST_28_DAYS, LAST_30_DAYS, LAST_90_DAYS, LAST_180_DAYS; this tool doesn't support custom `rangeStart`/`rangeEnd` windows. Without `rangeKey`, the last 30 days are used. Use this for 'how many visitors did the site have' style queries. Wide date ranges can take a while. If the response says the query is still running, wait the stated number of seconds and repeat the identical call."
	)
	public VisitorMetric workspaceGroupChannelVisitorMetric(
			@GraphQLName("groupId") Long groupId,
			@GraphQLName("channelId") String channelId,
			@GraphQLName("rangeKey") String rangeKey,
			@GraphQLName("segmentId") String segmentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_visitorMetricResourceComponentServiceObjects,
			this::_populateResourceContext,
			visitorMetricResource ->
				visitorMetricResource.getWorkspaceGroupChannelVisitorMetric(
					groupId, channelId, rangeKey, segmentId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {workspaces{items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "List LDP workspaces accessible to the current user. Use the resulting `groupId` values with other endpoints (e.g. `getWorkspaceGroupChannelAccountsPage`, `getWorkspaceGroupChannelsPage`) to scope requests to a specific workspace."
	)
	public WorkspacePage workspaces() throws Exception {
		return _applyComponentServiceObjects(
			_workspaceResourceComponentServiceObjects,
			this::_populateResourceContext,
			workspaceResource -> new WorkspacePage(
				workspaceResource.getWorkspacesPage()));
	}

	@GraphQLName("AccountPage")
	public class AccountPage {

		public AccountPage(Page accountPage) {
			actions = accountPage.getActions();

			items = accountPage.getItems();
			lastPage = accountPage.getLastPage();
			page = accountPage.getPage();
			pageSize = accountPage.getPageSize();
			totalCount = accountPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Account> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountLifecyclePage")
	public class AccountLifecyclePage {

		public AccountLifecyclePage(Page accountLifecyclePage) {
			actions = accountLifecyclePage.getActions();

			items = accountLifecyclePage.getItems();
			lastPage = accountLifecyclePage.getLastPage();
			page = accountLifecyclePage.getPage();
			pageSize = accountLifecyclePage.getPageSize();
			totalCount = accountLifecyclePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AccountLifecycle> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountLifecycleHistoryPage")
	public class AccountLifecycleHistoryPage {

		public AccountLifecycleHistoryPage(Page accountLifecycleHistoryPage) {
			actions = accountLifecycleHistoryPage.getActions();

			items = accountLifecycleHistoryPage.getItems();
			lastPage = accountLifecycleHistoryPage.getLastPage();
			page = accountLifecycleHistoryPage.getPage();
			pageSize = accountLifecycleHistoryPage.getPageSize();
			totalCount = accountLifecycleHistoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AccountLifecycleHistory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountLifecycleStageMetricPage")
	public class AccountLifecycleStageMetricPage {

		public AccountLifecycleStageMetricPage(
			Page accountLifecycleStageMetricPage) {

			actions = accountLifecycleStageMetricPage.getActions();

			items = accountLifecycleStageMetricPage.getItems();
			lastPage = accountLifecycleStageMetricPage.getLastPage();
			page = accountLifecycleStageMetricPage.getPage();
			pageSize = accountLifecycleStageMetricPage.getPageSize();
			totalCount = accountLifecycleStageMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AccountLifecycleStageMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AccountLifecycleStageTransitionPage")
	public class AccountLifecycleStageTransitionPage {

		public AccountLifecycleStageTransitionPage(
			Page accountLifecycleStageTransitionPage) {

			actions = accountLifecycleStageTransitionPage.getActions();

			items = accountLifecycleStageTransitionPage.getItems();
			lastPage = accountLifecycleStageTransitionPage.getLastPage();
			page = accountLifecycleStageTransitionPage.getPage();
			pageSize = accountLifecycleStageTransitionPage.getPageSize();
			totalCount = accountLifecycleStageTransitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AccountLifecycleStageTransition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AcquisitionPage")
	public class AcquisitionPage {

		public AcquisitionPage(Page acquisitionPage) {
			actions = acquisitionPage.getActions();

			items = acquisitionPage.getItems();
			lastPage = acquisitionPage.getLastPage();
			page = acquisitionPage.getPage();
			pageSize = acquisitionPage.getPageSize();
			totalCount = acquisitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Acquisition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AssetSummaryMetricPage")
	public class AssetSummaryMetricPage {

		public AssetSummaryMetricPage(Page assetSummaryMetricPage) {
			actions = assetSummaryMetricPage.getActions();

			items = assetSummaryMetricPage.getItems();
			lastPage = assetSummaryMetricPage.getLastPage();
			page = assetSummaryMetricPage.getPage();
			pageSize = assetSummaryMetricPage.getPageSize();
			totalCount = assetSummaryMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<AssetSummaryMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ChannelPage")
	public class ChannelPage {

		public ChannelPage(Page channelPage) {
			actions = channelPage.getActions();

			items = channelPage.getItems();
			lastPage = channelPage.getLastPage();
			page = channelPage.getPage();
			pageSize = channelPage.getPageSize();
			totalCount = channelPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Channel> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("EventPage")
	public class EventPage {

		public EventPage(Page eventPage) {
			actions = eventPage.getActions();

			items = eventPage.getItems();
			lastPage = eventPage.getLastPage();
			page = eventPage.getPage();
			pageSize = eventPage.getPageSize();
			totalCount = eventPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Event> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("EventAttributeDefinitionPage")
	public class EventAttributeDefinitionPage {

		public EventAttributeDefinitionPage(Page eventAttributeDefinitionPage) {
			actions = eventAttributeDefinitionPage.getActions();

			items = eventAttributeDefinitionPage.getItems();
			lastPage = eventAttributeDefinitionPage.getLastPage();
			page = eventAttributeDefinitionPage.getPage();
			pageSize = eventAttributeDefinitionPage.getPageSize();
			totalCount = eventAttributeDefinitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<EventAttributeDefinition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("EventDefinitionPage")
	public class EventDefinitionPage {

		public EventDefinitionPage(Page eventDefinitionPage) {
			actions = eventDefinitionPage.getActions();

			items = eventDefinitionPage.getItems();
			lastPage = eventDefinitionPage.getLastPage();
			page = eventDefinitionPage.getPage();
			pageSize = eventDefinitionPage.getPageSize();
			totalCount = eventDefinitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<EventDefinition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("EventMetricPage")
	public class EventMetricPage {

		public EventMetricPage(Page eventMetricPage) {
			actions = eventMetricPage.getActions();

			items = eventMetricPage.getItems();
			lastPage = eventMetricPage.getLastPage();
			page = eventMetricPage.getPage();
			pageSize = eventMetricPage.getPageSize();
			totalCount = eventMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<EventMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("IndividualPage")
	public class IndividualPage {

		public IndividualPage(Page individualPage) {
			actions = individualPage.getActions();

			items = individualPage.getItems();
			lastPage = individualPage.getLastPage();
			page = individualPage.getPage();
			pageSize = individualPage.getPageSize();
			totalCount = individualPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Individual> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("IndividualSegmentPage")
	public class IndividualSegmentPage {

		public IndividualSegmentPage(Page individualSegmentPage) {
			actions = individualSegmentPage.getActions();

			items = individualSegmentPage.getItems();
			lastPage = individualSegmentPage.getLastPage();
			page = individualSegmentPage.getPage();
			pageSize = individualSegmentPage.getPageSize();
			totalCount = individualSegmentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<IndividualSegment> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("IndividualSegmentMembershipPage")
	public class IndividualSegmentMembershipPage {

		public IndividualSegmentMembershipPage(
			Page individualSegmentMembershipPage) {

			actions = individualSegmentMembershipPage.getActions();

			items = individualSegmentMembershipPage.getItems();
			lastPage = individualSegmentMembershipPage.getLastPage();
			page = individualSegmentMembershipPage.getPage();
			pageSize = individualSegmentMembershipPage.getPageSize();
			totalCount = individualSegmentMembershipPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<IndividualSegmentMembership> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("IndividualSegmentMembershipChangePage")
	public class IndividualSegmentMembershipChangePage {

		public IndividualSegmentMembershipChangePage(
			Page individualSegmentMembershipChangePage) {

			actions = individualSegmentMembershipChangePage.getActions();

			items = individualSegmentMembershipChangePage.getItems();
			lastPage = individualSegmentMembershipChangePage.getLastPage();
			page = individualSegmentMembershipChangePage.getPage();
			pageSize = individualSegmentMembershipChangePage.getPageSize();
			totalCount = individualSegmentMembershipChangePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<IndividualSegmentMembershipChange> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("IndividualSegmentMembershipChangeMetricPage")
	public class IndividualSegmentMembershipChangeMetricPage {

		public IndividualSegmentMembershipChangeMetricPage(
			Page individualSegmentMembershipChangeMetricPage) {

			actions = individualSegmentMembershipChangeMetricPage.getActions();

			items = individualSegmentMembershipChangeMetricPage.getItems();
			lastPage =
				individualSegmentMembershipChangeMetricPage.getLastPage();
			page = individualSegmentMembershipChangeMetricPage.getPage();
			pageSize =
				individualSegmentMembershipChangeMetricPage.getPageSize();
			totalCount =
				individualSegmentMembershipChangeMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<IndividualSegmentMembershipChangeMetric>
			items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageMetricPage")
	public class PageMetricPage {

		public PageMetricPage(Page pageMetricPage) {
			actions = pageMetricPage.getActions();

			items = pageMetricPage.getItems();
			lastPage = pageMetricPage.getLastPage();
			page = pageMetricPage.getPage();
			pageSize = pageMetricPage.getPageSize();
			totalCount = pageMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<PageMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SearchTermPage")
	public class SearchTermPage {

		public SearchTermPage(Page searchTermPage) {
			actions = searchTermPage.getActions();

			items = searchTermPage.getItems();
			lastPage = searchTermPage.getLastPage();
			page = searchTermPage.getPage();
			pageSize = searchTermPage.getPageSize();
			totalCount = searchTermPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<SearchTerm> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("UserSessionPage")
	public class UserSessionPage {

		public UserSessionPage(Page userSessionPage) {
			actions = userSessionPage.getActions();

			items = userSessionPage.getItems();
			lastPage = userSessionPage.getLastPage();
			page = userSessionPage.getPage();
			pageSize = userSessionPage.getPageSize();
			totalCount = userSessionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<UserSession> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("VisitFrequencyMetricPage")
	public class VisitFrequencyMetricPage {

		public VisitFrequencyMetricPage(Page visitFrequencyMetricPage) {
			actions = visitFrequencyMetricPage.getActions();

			items = visitFrequencyMetricPage.getItems();
			lastPage = visitFrequencyMetricPage.getLastPage();
			page = visitFrequencyMetricPage.getPage();
			pageSize = visitFrequencyMetricPage.getPageSize();
			totalCount = visitFrequencyMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<VisitFrequencyMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("VisitorMetricPage")
	public class VisitorMetricPage {

		public VisitorMetricPage(Page visitorMetricPage) {
			actions = visitorMetricPage.getActions();

			items = visitorMetricPage.getItems();
			lastPage = visitorMetricPage.getLastPage();
			page = visitorMetricPage.getPage();
			pageSize = visitorMetricPage.getPageSize();
			totalCount = visitorMetricPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<VisitorMetric> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WorkspacePage")
	public class WorkspacePage {

		public WorkspacePage(Page workspacePage) {
			actions = workspacePage.getActions();

			items = workspacePage.getItems();
			lastPage = workspacePage.getLastPage();
			page = workspacePage.getPage();
			pageSize = workspacePage.getPageSize();
			totalCount = workspacePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Workspace> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(AccountResource accountResource)
		throws Exception {

		accountResource.setContextAcceptLanguage(_acceptLanguage);
		accountResource.setContextCompany(_company);
		accountResource.setContextHttpServletRequest(_httpServletRequest);
		accountResource.setContextHttpServletResponse(_httpServletResponse);
		accountResource.setContextUriInfo(_uriInfo);
		accountResource.setContextUser(_user);
		accountResource.setGroupLocalService(_groupLocalService);
		accountResource.setResourceActionLocalService(
			_resourceActionLocalService);
		accountResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		accountResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			AccountLifecycleResource accountLifecycleResource)
		throws Exception {

		accountLifecycleResource.setContextAcceptLanguage(_acceptLanguage);
		accountLifecycleResource.setContextCompany(_company);
		accountLifecycleResource.setContextHttpServletRequest(
			_httpServletRequest);
		accountLifecycleResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountLifecycleResource.setContextUriInfo(_uriInfo);
		accountLifecycleResource.setContextUser(_user);
		accountLifecycleResource.setGroupLocalService(_groupLocalService);
		accountLifecycleResource.setResourceActionLocalService(
			_resourceActionLocalService);
		accountLifecycleResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		accountLifecycleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			AccountLifecycleHistoryResource accountLifecycleHistoryResource)
		throws Exception {

		accountLifecycleHistoryResource.setContextAcceptLanguage(
			_acceptLanguage);
		accountLifecycleHistoryResource.setContextCompany(_company);
		accountLifecycleHistoryResource.setContextHttpServletRequest(
			_httpServletRequest);
		accountLifecycleHistoryResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountLifecycleHistoryResource.setContextUriInfo(_uriInfo);
		accountLifecycleHistoryResource.setContextUser(_user);
		accountLifecycleHistoryResource.setGroupLocalService(
			_groupLocalService);
		accountLifecycleHistoryResource.setResourceActionLocalService(
			_resourceActionLocalService);
		accountLifecycleHistoryResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		accountLifecycleHistoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			AccountLifecycleStageMetricResource
				accountLifecycleStageMetricResource)
		throws Exception {

		accountLifecycleStageMetricResource.setContextAcceptLanguage(
			_acceptLanguage);
		accountLifecycleStageMetricResource.setContextCompany(_company);
		accountLifecycleStageMetricResource.setContextHttpServletRequest(
			_httpServletRequest);
		accountLifecycleStageMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountLifecycleStageMetricResource.setContextUriInfo(_uriInfo);
		accountLifecycleStageMetricResource.setContextUser(_user);
		accountLifecycleStageMetricResource.setGroupLocalService(
			_groupLocalService);
		accountLifecycleStageMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		accountLifecycleStageMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		accountLifecycleStageMetricResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			AccountLifecycleStageTransitionResource
				accountLifecycleStageTransitionResource)
		throws Exception {

		accountLifecycleStageTransitionResource.setContextAcceptLanguage(
			_acceptLanguage);
		accountLifecycleStageTransitionResource.setContextCompany(_company);
		accountLifecycleStageTransitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		accountLifecycleStageTransitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		accountLifecycleStageTransitionResource.setContextUriInfo(_uriInfo);
		accountLifecycleStageTransitionResource.setContextUser(_user);
		accountLifecycleStageTransitionResource.setGroupLocalService(
			_groupLocalService);
		accountLifecycleStageTransitionResource.setResourceActionLocalService(
			_resourceActionLocalService);
		accountLifecycleStageTransitionResource.
			setResourcePermissionLocalService(_resourcePermissionLocalService);
		accountLifecycleStageTransitionResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			AcquisitionResource acquisitionResource)
		throws Exception {

		acquisitionResource.setContextAcceptLanguage(_acceptLanguage);
		acquisitionResource.setContextCompany(_company);
		acquisitionResource.setContextHttpServletRequest(_httpServletRequest);
		acquisitionResource.setContextHttpServletResponse(_httpServletResponse);
		acquisitionResource.setContextUriInfo(_uriInfo);
		acquisitionResource.setContextUser(_user);
		acquisitionResource.setGroupLocalService(_groupLocalService);
		acquisitionResource.setResourceActionLocalService(
			_resourceActionLocalService);
		acquisitionResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		acquisitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			AssetSummaryMetricResource assetSummaryMetricResource)
		throws Exception {

		assetSummaryMetricResource.setContextAcceptLanguage(_acceptLanguage);
		assetSummaryMetricResource.setContextCompany(_company);
		assetSummaryMetricResource.setContextHttpServletRequest(
			_httpServletRequest);
		assetSummaryMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		assetSummaryMetricResource.setContextUriInfo(_uriInfo);
		assetSummaryMetricResource.setContextUser(_user);
		assetSummaryMetricResource.setGroupLocalService(_groupLocalService);
		assetSummaryMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		assetSummaryMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		assetSummaryMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(ChannelResource channelResource)
		throws Exception {

		channelResource.setContextAcceptLanguage(_acceptLanguage);
		channelResource.setContextCompany(_company);
		channelResource.setContextHttpServletRequest(_httpServletRequest);
		channelResource.setContextHttpServletResponse(_httpServletResponse);
		channelResource.setContextUriInfo(_uriInfo);
		channelResource.setContextUser(_user);
		channelResource.setGroupLocalService(_groupLocalService);
		channelResource.setResourceActionLocalService(
			_resourceActionLocalService);
		channelResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		channelResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(EventResource eventResource)
		throws Exception {

		eventResource.setContextAcceptLanguage(_acceptLanguage);
		eventResource.setContextCompany(_company);
		eventResource.setContextHttpServletRequest(_httpServletRequest);
		eventResource.setContextHttpServletResponse(_httpServletResponse);
		eventResource.setContextUriInfo(_uriInfo);
		eventResource.setContextUser(_user);
		eventResource.setGroupLocalService(_groupLocalService);
		eventResource.setResourceActionLocalService(
			_resourceActionLocalService);
		eventResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		eventResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			EventAttributeDefinitionResource eventAttributeDefinitionResource)
		throws Exception {

		eventAttributeDefinitionResource.setContextAcceptLanguage(
			_acceptLanguage);
		eventAttributeDefinitionResource.setContextCompany(_company);
		eventAttributeDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		eventAttributeDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		eventAttributeDefinitionResource.setContextUriInfo(_uriInfo);
		eventAttributeDefinitionResource.setContextUser(_user);
		eventAttributeDefinitionResource.setGroupLocalService(
			_groupLocalService);
		eventAttributeDefinitionResource.setResourceActionLocalService(
			_resourceActionLocalService);
		eventAttributeDefinitionResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		eventAttributeDefinitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			EventDefinitionResource eventDefinitionResource)
		throws Exception {

		eventDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		eventDefinitionResource.setContextCompany(_company);
		eventDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		eventDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		eventDefinitionResource.setContextUriInfo(_uriInfo);
		eventDefinitionResource.setContextUser(_user);
		eventDefinitionResource.setGroupLocalService(_groupLocalService);
		eventDefinitionResource.setResourceActionLocalService(
			_resourceActionLocalService);
		eventDefinitionResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		eventDefinitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			EventMetricResource eventMetricResource)
		throws Exception {

		eventMetricResource.setContextAcceptLanguage(_acceptLanguage);
		eventMetricResource.setContextCompany(_company);
		eventMetricResource.setContextHttpServletRequest(_httpServletRequest);
		eventMetricResource.setContextHttpServletResponse(_httpServletResponse);
		eventMetricResource.setContextUriInfo(_uriInfo);
		eventMetricResource.setContextUser(_user);
		eventMetricResource.setGroupLocalService(_groupLocalService);
		eventMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		eventMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		eventMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(IndividualResource individualResource)
		throws Exception {

		individualResource.setContextAcceptLanguage(_acceptLanguage);
		individualResource.setContextCompany(_company);
		individualResource.setContextHttpServletRequest(_httpServletRequest);
		individualResource.setContextHttpServletResponse(_httpServletResponse);
		individualResource.setContextUriInfo(_uriInfo);
		individualResource.setContextUser(_user);
		individualResource.setGroupLocalService(_groupLocalService);
		individualResource.setResourceActionLocalService(
			_resourceActionLocalService);
		individualResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		individualResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			IndividualSegmentResource individualSegmentResource)
		throws Exception {

		individualSegmentResource.setContextAcceptLanguage(_acceptLanguage);
		individualSegmentResource.setContextCompany(_company);
		individualSegmentResource.setContextHttpServletRequest(
			_httpServletRequest);
		individualSegmentResource.setContextHttpServletResponse(
			_httpServletResponse);
		individualSegmentResource.setContextUriInfo(_uriInfo);
		individualSegmentResource.setContextUser(_user);
		individualSegmentResource.setGroupLocalService(_groupLocalService);
		individualSegmentResource.setResourceActionLocalService(
			_resourceActionLocalService);
		individualSegmentResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		individualSegmentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			IndividualSegmentMembershipResource
				individualSegmentMembershipResource)
		throws Exception {

		individualSegmentMembershipResource.setContextAcceptLanguage(
			_acceptLanguage);
		individualSegmentMembershipResource.setContextCompany(_company);
		individualSegmentMembershipResource.setContextHttpServletRequest(
			_httpServletRequest);
		individualSegmentMembershipResource.setContextHttpServletResponse(
			_httpServletResponse);
		individualSegmentMembershipResource.setContextUriInfo(_uriInfo);
		individualSegmentMembershipResource.setContextUser(_user);
		individualSegmentMembershipResource.setGroupLocalService(
			_groupLocalService);
		individualSegmentMembershipResource.setResourceActionLocalService(
			_resourceActionLocalService);
		individualSegmentMembershipResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		individualSegmentMembershipResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			IndividualSegmentMembershipChangeResource
				individualSegmentMembershipChangeResource)
		throws Exception {

		individualSegmentMembershipChangeResource.setContextAcceptLanguage(
			_acceptLanguage);
		individualSegmentMembershipChangeResource.setContextCompany(_company);
		individualSegmentMembershipChangeResource.setContextHttpServletRequest(
			_httpServletRequest);
		individualSegmentMembershipChangeResource.setContextHttpServletResponse(
			_httpServletResponse);
		individualSegmentMembershipChangeResource.setContextUriInfo(_uriInfo);
		individualSegmentMembershipChangeResource.setContextUser(_user);
		individualSegmentMembershipChangeResource.setGroupLocalService(
			_groupLocalService);
		individualSegmentMembershipChangeResource.setResourceActionLocalService(
			_resourceActionLocalService);
		individualSegmentMembershipChangeResource.
			setResourcePermissionLocalService(_resourcePermissionLocalService);
		individualSegmentMembershipChangeResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			IndividualSegmentMembershipChangeMetricResource
				individualSegmentMembershipChangeMetricResource)
		throws Exception {

		individualSegmentMembershipChangeMetricResource.
			setContextAcceptLanguage(_acceptLanguage);
		individualSegmentMembershipChangeMetricResource.setContextCompany(
			_company);
		individualSegmentMembershipChangeMetricResource.
			setContextHttpServletRequest(_httpServletRequest);
		individualSegmentMembershipChangeMetricResource.
			setContextHttpServletResponse(_httpServletResponse);
		individualSegmentMembershipChangeMetricResource.setContextUriInfo(
			_uriInfo);
		individualSegmentMembershipChangeMetricResource.setContextUser(_user);
		individualSegmentMembershipChangeMetricResource.setGroupLocalService(
			_groupLocalService);
		individualSegmentMembershipChangeMetricResource.
			setResourceActionLocalService(_resourceActionLocalService);
		individualSegmentMembershipChangeMetricResource.
			setResourcePermissionLocalService(_resourcePermissionLocalService);
		individualSegmentMembershipChangeMetricResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(PageMetricResource pageMetricResource)
		throws Exception {

		pageMetricResource.setContextAcceptLanguage(_acceptLanguage);
		pageMetricResource.setContextCompany(_company);
		pageMetricResource.setContextHttpServletRequest(_httpServletRequest);
		pageMetricResource.setContextHttpServletResponse(_httpServletResponse);
		pageMetricResource.setContextUriInfo(_uriInfo);
		pageMetricResource.setContextUser(_user);
		pageMetricResource.setGroupLocalService(_groupLocalService);
		pageMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		pageMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		pageMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SearchTermResource searchTermResource)
		throws Exception {

		searchTermResource.setContextAcceptLanguage(_acceptLanguage);
		searchTermResource.setContextCompany(_company);
		searchTermResource.setContextHttpServletRequest(_httpServletRequest);
		searchTermResource.setContextHttpServletResponse(_httpServletResponse);
		searchTermResource.setContextUriInfo(_uriInfo);
		searchTermResource.setContextUser(_user);
		searchTermResource.setGroupLocalService(_groupLocalService);
		searchTermResource.setResourceActionLocalService(
			_resourceActionLocalService);
		searchTermResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		searchTermResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			UserSessionResource userSessionResource)
		throws Exception {

		userSessionResource.setContextAcceptLanguage(_acceptLanguage);
		userSessionResource.setContextCompany(_company);
		userSessionResource.setContextHttpServletRequest(_httpServletRequest);
		userSessionResource.setContextHttpServletResponse(_httpServletResponse);
		userSessionResource.setContextUriInfo(_uriInfo);
		userSessionResource.setContextUser(_user);
		userSessionResource.setGroupLocalService(_groupLocalService);
		userSessionResource.setResourceActionLocalService(
			_resourceActionLocalService);
		userSessionResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		userSessionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			VisitFrequencyMetricResource visitFrequencyMetricResource)
		throws Exception {

		visitFrequencyMetricResource.setContextAcceptLanguage(_acceptLanguage);
		visitFrequencyMetricResource.setContextCompany(_company);
		visitFrequencyMetricResource.setContextHttpServletRequest(
			_httpServletRequest);
		visitFrequencyMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		visitFrequencyMetricResource.setContextUriInfo(_uriInfo);
		visitFrequencyMetricResource.setContextUser(_user);
		visitFrequencyMetricResource.setGroupLocalService(_groupLocalService);
		visitFrequencyMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		visitFrequencyMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		visitFrequencyMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			VisitorMetricResource visitorMetricResource)
		throws Exception {

		visitorMetricResource.setContextAcceptLanguage(_acceptLanguage);
		visitorMetricResource.setContextCompany(_company);
		visitorMetricResource.setContextHttpServletRequest(_httpServletRequest);
		visitorMetricResource.setContextHttpServletResponse(
			_httpServletResponse);
		visitorMetricResource.setContextUriInfo(_uriInfo);
		visitorMetricResource.setContextUser(_user);
		visitorMetricResource.setGroupLocalService(_groupLocalService);
		visitorMetricResource.setResourceActionLocalService(
			_resourceActionLocalService);
		visitorMetricResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		visitorMetricResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(WorkspaceResource workspaceResource)
		throws Exception {

		workspaceResource.setContextAcceptLanguage(_acceptLanguage);
		workspaceResource.setContextCompany(_company);
		workspaceResource.setContextHttpServletRequest(_httpServletRequest);
		workspaceResource.setContextHttpServletResponse(_httpServletResponse);
		workspaceResource.setContextUriInfo(_uriInfo);
		workspaceResource.setContextUser(_user);
		workspaceResource.setGroupLocalService(_groupLocalService);
		workspaceResource.setResourceActionLocalService(
			_resourceActionLocalService);
		workspaceResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		workspaceResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountLifecycleResource>
		_accountLifecycleResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountLifecycleHistoryResource>
		_accountLifecycleHistoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<AccountLifecycleStageMetricResource>
		_accountLifecycleStageMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects
		<AccountLifecycleStageTransitionResource>
			_accountLifecycleStageTransitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<AcquisitionResource>
		_acquisitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<AssetSummaryMetricResource>
		_assetSummaryMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;
	private static ComponentServiceObjects<EventResource>
		_eventResourceComponentServiceObjects;
	private static ComponentServiceObjects<EventAttributeDefinitionResource>
		_eventAttributeDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<EventDefinitionResource>
		_eventDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<EventMetricResource>
		_eventMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<IndividualResource>
		_individualResourceComponentServiceObjects;
	private static ComponentServiceObjects<IndividualSegmentResource>
		_individualSegmentResourceComponentServiceObjects;
	private static ComponentServiceObjects<IndividualSegmentMembershipResource>
		_individualSegmentMembershipResourceComponentServiceObjects;
	private static ComponentServiceObjects
		<IndividualSegmentMembershipChangeResource>
			_individualSegmentMembershipChangeResourceComponentServiceObjects;
	private static ComponentServiceObjects
		<IndividualSegmentMembershipChangeMetricResource>
			_individualSegmentMembershipChangeMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageMetricResource>
		_pageMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<SearchTermResource>
		_searchTermResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserSessionResource>
		_userSessionResourceComponentServiceObjects;
	private static ComponentServiceObjects<VisitFrequencyMetricResource>
		_visitFrequencyMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<VisitorMetricResource>
		_visitorMetricResourceComponentServiceObjects;
	private static ComponentServiceObjects<WorkspaceResource>
		_workspaceResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}
// LIFERAY-REST-BUILDER-HASH:588831187