/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.messaging;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.service.FaroPreferencesLocalService;
import com.liferay.osb.faro.web.internal.helper.EmailReportHelper;
import com.liferay.osb.faro.web.internal.helper.LifecycleTriggerType;
import com.liferay.osb.faro.web.internal.helper.NotificationHelper;
import com.liferay.osb.faro.web.internal.model.preferences.EmailReportPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.LifecycleNotificationPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.SegmentNotificationPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.WorkspacePreferences;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
public abstract class BaseEmailReportMessageListener
	extends BaseMessageListener {

	protected abstract void activate(BundleContext bundleContext);

	protected abstract void deactivate();

	@Override
	protected void doReceive(Message message) throws Exception {
		for (FaroPreferences faroPreferences :
				faroPreferencesLocalService.getFaroPreferenceses(-1, -1)) {

			WorkspacePreferences workspacePreferences = JSONUtil.readValue(
				faroPreferences.getPreferences(), WorkspacePreferences.class);

			Map<String, EmailReportPreferences> emailReportPreferencesMap =
				workspacePreferences.getEmailReportPreferences(null);

			for (Map.Entry<String, EmailReportPreferences> entry :
					emailReportPreferencesMap.entrySet()) {

				EmailReportPreferences emailReportPreferences =
					entry.getValue();

				if (emailReportPreferences.getEnabled() &&
					Objects.equals(
						emailReportPreferences.getFrequency(),
						getFrequency())) {

					try {
						emailReportHelper.sendEmail(
							entry.getKey(), getFrequency(),
							faroPreferences.getGroupId(),
							faroPreferences.getUserId());
					}
					catch (Exception exception) {
						_log.error(
							String.format(
								"Unable to send %s email for channel ID %s " +
									"and user ID %s",
								getFrequency(), entry.getKey(),
								faroPreferences.getUserId()),
							exception);
					}
				}
			}

			_sendLifecycleNotifications(faroPreferences, workspacePreferences);
			_sendSegmentNotifications(faroPreferences, workspacePreferences);
		}
	}

	protected abstract String getFrequency();

	@Reference
	protected EmailReportHelper emailReportHelper;

	@Reference
	protected FaroPreferencesLocalService faroPreferencesLocalService;

	@Reference
	protected NotificationHelper notificationHelper;

	@Reference
	protected SchedulerEngineHelper schedulerEngineHelper;

	@Reference
	protected TriggerFactory triggerFactory;

	private boolean _isEnabled(
		LifecycleNotificationPreferences lifecycleNotificationPreferences,
		LifecycleTriggerType lifecycleTriggerType) {

		if (lifecycleTriggerType ==
				LifecycleTriggerType.ACCOUNT_STAGE_CHANGES) {

			return GetterUtil.getBoolean(
				lifecycleNotificationPreferences.getAccountStageChanges());
		}
		else if (lifecycleTriggerType ==
					LifecycleTriggerType.NET_NEW_PIPELINE_ACCOUNTS) {

			return GetterUtil.getBoolean(
				lifecycleNotificationPreferences.getNetNewPipelineAccounts());
		}
		else if (lifecycleTriggerType == LifecycleTriggerType.NEW_ACCOUNTS) {
			return GetterUtil.getBoolean(
				lifecycleNotificationPreferences.getNewAccounts());
		}
		else if (lifecycleTriggerType ==
					LifecycleTriggerType.NEW_AT_RISK_ACCOUNTS) {

			return GetterUtil.getBoolean(
				lifecycleNotificationPreferences.getNewAtRiskAccounts());
		}
		else if (lifecycleTriggerType ==
					LifecycleTriggerType.NEW_STALLED_ACCOUNTS) {

			return GetterUtil.getBoolean(
				lifecycleNotificationPreferences.getNewStalledAccounts());
		}

		return false;
	}

	private void _sendLifecycleNotifications(
		FaroPreferences faroPreferences,
		WorkspacePreferences workspacePreferences) {

		Map<String, LifecycleNotificationPreferences>
			lifecycleNotificationPreferencesMap =
				workspacePreferences.getLifecycleNotificationPreferences(null);

		for (Map.Entry<String, LifecycleNotificationPreferences> entry :
				lifecycleNotificationPreferencesMap.entrySet()) {

			LifecycleNotificationPreferences lifecycleNotificationPreferences =
				entry.getValue();

			if (!Objects.equals(
					lifecycleNotificationPreferences.getEmailFrequency(),
					getFrequency())) {

				continue;
			}

			for (LifecycleTriggerType lifecycleTriggerType :
					LifecycleTriggerType.values()) {

				if (!_isEnabled(
						lifecycleNotificationPreferences,
						lifecycleTriggerType)) {

					continue;
				}

				_sendNotification(
					String.format(
						"%s trigger, lifecycle ID %s, and user ID %s",
						lifecycleTriggerType.getKey(), entry.getKey(),
						faroPreferences.getUserId()),
					() -> notificationHelper.sendLifecycleNotification(
						faroPreferences.getGroupId(), entry.getKey(),
						lifecycleTriggerType, getFrequency(),
						faroPreferences.getUserId()));
			}
		}
	}

	private void _sendNotification(
		String context, UnsafeRunnable<Exception> unsafeRunnable) {

		try {
			unsafeRunnable.run();
		}
		catch (Exception exception) {
			_log.error(
				String.format(
					"Unable to send %s notification for %s", getFrequency(),
					context),
				exception);
		}
	}

	private void _sendSegmentNotifications(
		FaroPreferences faroPreferences,
		WorkspacePreferences workspacePreferences) {

		Map<String, SegmentNotificationPreferences>
			segmentNotificationPreferencesMap =
				workspacePreferences.getSegmentNotificationPreferences(null);

		for (Map.Entry<String, SegmentNotificationPreferences> entry :
				segmentNotificationPreferencesMap.entrySet()) {

			SegmentNotificationPreferences segmentNotificationPreferences =
				entry.getValue();

			if (!segmentNotificationPreferences.getNewMemberAdded() ||
				!Objects.equals(
					segmentNotificationPreferences.getEmailFrequency(),
					getFrequency())) {

				continue;
			}

			_sendNotification(
				String.format(
					"segment ID %s and user ID %s", entry.getKey(),
					faroPreferences.getUserId()),
				() -> notificationHelper.sendSegmentNotification(
					faroPreferences.getGroupId(), entry.getKey(),
					getFrequency(), faroPreferences.getUserId()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEmailReportMessageListener.class);

}