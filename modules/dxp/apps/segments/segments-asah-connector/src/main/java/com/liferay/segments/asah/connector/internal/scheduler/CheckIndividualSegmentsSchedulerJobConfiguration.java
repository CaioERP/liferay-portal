/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.scheduler;

import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClient;
import com.liferay.segments.asah.connector.internal.client.AsahFaroBackendClientImpl;
import com.liferay.segments.asah.connector.internal.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.internal.client.model.Results;
import com.liferay.segments.asah.connector.internal.client.util.OrderByField;
import com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahConfiguration;
import com.liferay.segments.asah.connector.internal.helper.CheckIndividualSegmentsHelper;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	configurationPid = "com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckIndividualSegmentsSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return this::_checkIndividualSegments;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_asahFaroBackendClient = new AsahFaroBackendClientImpl(
			_analyticsSettingsManager, _http);

		modified(properties);
	}

	@Deactivate
	protected void deactivate() {
		_asahFaroBackendClient = null;
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		SegmentsAsahConfiguration segmentsAsahConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsAsahConfiguration.class, properties);

		_triggerConfiguration = TriggerConfiguration.createTriggerConfiguration(
			segmentsAsahConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	private void _checkIndividualSegments() throws Exception {
		_initialCheckIndividualSegments();
		_checkIndividualSegmentsMemberships();
	}

	private void _checkIndividualSegments(long companyId) {
		Results<IndividualSegment> individualSegmentResults = new Results<>();

		try {
			individualSegmentResults =
				_asahFaroBackendClient.getIndividualSegmentResults(
					companyId, 1, _DELTA,
					Collections.singletonList(
						OrderByField.desc("dateModified")));

			_deleteSegmentsEntries(individualSegmentResults);
		}
		catch (RuntimeException runtimeException) {
			_log.error(
				"Unable to retrieve individual segments", runtimeException);

			return;
		}
		catch (PortalException portalException) {
			_log.error("Unable to delete segments", portalException);

			return;
		}

		int totalElements = individualSegmentResults.getTotal();

		if (_log.isDebugEnabled()) {
			_log.debug(totalElements + " active individual segments found");
		}

		if (totalElements == 0) {
			return;
		}

		List<IndividualSegment> individualSegments =
			individualSegmentResults.getItems();

		individualSegments.forEach(
			individualSegment ->
				_checkIndividualSegmentsHelper.addSegmentsEntry(
					companyId, individualSegment));
	}

	private void _checkIndividualSegmentsMemberships() throws Exception {
		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntriesBySource(
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			_checkIndividualSegmentsHelper.checkIndividualSegmentMemberships(
				segmentsEntry);
		}
	}

	private void _deleteSegmentsEntries(
			Results<IndividualSegment> individualSegmentResults)
		throws PortalException {

		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryLocalService.getSegmentsEntriesBySource(
				SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND, 0, _DELTA,
				null);

		if (individualSegmentResults.getTotal() > 0) {
			segmentsEntries = ListUtil.filter(
				segmentsEntries,
				segmentsEntry -> !ListUtil.exists(
					individualSegmentResults.getItems(),
					individualSegment -> StringUtil.equals(
						individualSegment.getId(),
						segmentsEntry.getSegmentsEntryKey())));
		}

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			_segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry);
		}
	}

	private void _initialCheckIndividualSegments() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (_analyticsSettingsManager.isAnalyticsEnabled(companyId)) {
					_checkIndividualSegments(companyId);
				}
			});
	}

	private static final int _DELTA = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		CheckIndividualSegmentsSchedulerJobConfiguration.class);

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	private volatile AsahFaroBackendClient _asahFaroBackendClient;

	@Reference
	private CheckIndividualSegmentsHelper _checkIndividualSegmentsHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	private volatile TriggerConfiguration _triggerConfiguration;

}