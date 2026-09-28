/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.helper;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStageTransition;
import com.liferay.osb.faro.engine.client.model.LifecycleTriggerResult;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.util.EmailUtil;
import com.liferay.osb.faro.web.internal.constants.FaroNotificationTemplateConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Claude
 */
@Component(service = NotificationHelper.class)
public class NotificationHelper {

	public void sendLifecycleNotification(
			long groupId, String lifecycleId,
			LifecycleTriggerType lifecycleTriggerType, String frequency,
			long userId)
		throws Exception {

		NotificationContext notificationContext = _resolveContext(
			groupId, userId);

		if (notificationContext == null) {
			return;
		}

		FaroProject faroProject = notificationContext._faroProject;

		int rangeKey = _emailReportHelper.getRangeKey(frequency);

		long count = 0;
		String accountName = null;

		if (lifecycleTriggerType == LifecycleTriggerType.NEW_ACCOUNTS) {
			LifecycleTriggerResult lifecycleTriggerResult =
				_contactsEngineClient.getLifecycleNewAccountsResult(
					faroProject, lifecycleId, rangeKey);

			if (lifecycleTriggerResult != null) {
				count = lifecycleTriggerResult.getCount();
				accountName = lifecycleTriggerResult.getAccountName();
			}
		}
		else if (lifecycleTriggerType ==
					LifecycleTriggerType.NEW_STALLED_ACCOUNTS) {

			LifecycleTriggerResult lifecycleTriggerResult =
				_contactsEngineClient.getLifecycleStalledAccountsResult(
					faroProject, lifecycleId, rangeKey);

			if (lifecycleTriggerResult != null) {
				count = lifecycleTriggerResult.getCount();
				accountName = lifecycleTriggerResult.getAccountName();
			}
		}
		else {
			Results<AccountLifecycleStageTransition> results =
				_contactsEngineClient.getAccountLifecycleStageTransitions(
					faroProject, null, null, lifecycleId, null, null, rangeKey,
					null, null, lifecycleTriggerType.getToLifecycleStage(), 0,
					2, null);

			count = results.getTotal();

			List<AccountLifecycleStageTransition>
				accountLifecycleStageTransitions = results.getItems();

			if ((count == 1) && !accountLifecycleStageTransitions.isEmpty()) {
				AccountLifecycleStageTransition
					accountLifecycleStageTransition =
						accountLifecycleStageTransitions.get(0);

				accountName = accountLifecycleStageTransition.getAccountName();
			}
		}

		if (count == 0) {
			return;
		}

		AccountLifecycle accountLifecycle =
			_contactsEngineClient.getAccountLifecycle(faroProject, lifecycleId);

		if (accountLifecycle == null) {
			return;
		}

		Group group = notificationContext._group;

		String resultText;

		if ((count == 1) && (accountName != null)) {
			resultText = accountName;
		}
		else {
			resultText = count + " accounts";
		}

		NotificationTemplate notificationTemplate =
			_fetchOrCreateNotificationTemplate(
				FaroNotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_LIFECYCLE_NOTIFICATION,
				group.getCompanyId(), userId);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute(
			"[%LIFECYCLE_NAME%]", accountLifecycle.getName(), false);
		subscriptionSender.setContextAttribute(
			"[%LIFECYCLE_RESULT_TEXT%]", resultText, false);
		subscriptionSender.setLocalizedContextAttributeWithFunction(
			"[%LIFECYCLE_TRIGGER_LABEL%]",
			locale -> LanguageUtil.get(
				locale, lifecycleTriggerType.getLanguageKey()));
		subscriptionSender.setContextAttribute(
			"[%LIFECYCLE_URL%]", _getLifecycleURL(group), false);
		subscriptionSender.setContextAttribute(
			"[%SENDER_NAME%]", EmailUtil.getSenderName(faroProject), false);
		subscriptionSender.setLocalizedBodyMap(
			notificationTemplate.getBodyMap());
		subscriptionSender.setLocalizedSubjectMap(
			notificationTemplate.getSubjectMap());

		User user = notificationContext._user;

		_flushSubscriptionSender(subscriptionSender, faroProject, group, user);
	}

	public void sendSegmentNotification(
			long groupId, String segmentId, String frequency, long userId)
		throws Exception {

		NotificationContext notificationContext = _resolveContext(
			groupId, userId);

		if (notificationContext == null) {
			return;
		}

		FaroProject faroProject = notificationContext._faroProject;

		long count = _contactsEngineClient.getSegmentNewMembersCount(
			faroProject, segmentId, _emailReportHelper.getRangeKey(frequency));

		if (count == 0) {
			return;
		}

		Group group = notificationContext._group;

		NotificationTemplate notificationTemplate =
			_fetchOrCreateNotificationTemplate(
				FaroNotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_SEGMENT_NOTIFICATION,
				group.getCompanyId(), userId);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setContextAttribute(
			"[%SEGMENT_NEW_MEMBERS_COUNT%]", String.valueOf(count), false);
		subscriptionSender.setContextAttribute(
			"[%SEGMENT_URL%]", _getSegmentURL(group, segmentId), false);
		subscriptionSender.setContextAttribute(
			"[%SENDER_NAME%]", EmailUtil.getSenderName(faroProject), false);
		subscriptionSender.setLocalizedBodyMap(
			notificationTemplate.getBodyMap());
		subscriptionSender.setLocalizedSubjectMap(
			notificationTemplate.getSubjectMap());

		User user = notificationContext._user;

		_flushSubscriptionSender(subscriptionSender, faroProject, group, user);
	}

	private NotificationTemplate _fetchOrCreateNotificationTemplate(
			String externalReferenceCode, long companyId, long userId)
		throws Exception {

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (notificationTemplate != null) {
			return notificationTemplate;
		}

		Map<String, Map<String, String>> defaultNotificationTemplates =
			FaroNotificationTemplateConstants.getDefaultNotificationTemplates();

		Map<String, String> defaultNotificationTemplate =
			defaultNotificationTemplates.get(externalReferenceCode);

		notificationTemplate =
			_notificationTemplateLocalService.createNotificationTemplate(0);

		notificationTemplate.setExternalReferenceCode(externalReferenceCode);
		notificationTemplate.setCompanyId(companyId);
		notificationTemplate.setUserId(userId);
		notificationTemplate.setBodyMap(
			Collections.singletonMap(
				LocaleUtil.getDefault(),
				MapUtil.getString(defaultNotificationTemplate, "body")));
		notificationTemplate.setDescription(
			MapUtil.getString(defaultNotificationTemplate, "name"));
		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setNameMap(
			Collections.singletonMap(
				LocaleUtil.getDefault(),
				MapUtil.getString(defaultNotificationTemplate, "name")));
		notificationTemplate.setSubjectMap(
			Collections.singletonMap(
				LocaleUtil.getDefault(),
				MapUtil.getString(defaultNotificationTemplate, "subject")));
		notificationTemplate.setSystem(false);
		notificationTemplate.setType(NotificationConstants.TYPE_EMAIL);

		return _notificationTemplateLocalService.addNotificationTemplate(
			new NotificationContextBuilder(
			).companyId(
				companyId
			).notificationTemplate(
				notificationTemplate
			).userId(
				userId
			).build());
	}

	private void _flushSubscriptionSender(
			SubscriptionSender subscriptionSender, FaroProject faroProject,
			Group group, User user)
		throws Exception {

		subscriptionSender.setCompanyId(group.getCompanyId());
		subscriptionSender.setFrom(
			EmailUtil.getSenderEmailAddress(faroProject),
			EmailUtil.getSenderName(faroProject));
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId(
			"faro_notification", group.getGroupId(),
			System.currentTimeMillis());

		subscriptionSender.addRuntimeSubscribers(
			user.getEmailAddress(), user.getFullName());

		subscriptionSender.flushNotifications();
	}

	private String _getLifecycleURL(Group group) {

		// No per-lifecycle view route exists in the frontend today (only
		// LIFECYCLE_EDIT, /lifecycle/:lifecycleId/edit) — links to the
		// channel-scoped Lifecycle page without an ID. See LPD-105607 spec,
		// Known gaps.

		return EmailUtil.getWorkspaceURL(group) + "/lifecycle";
	}

	private String _getSegmentURL(Group group, String segmentId) {
		return EmailUtil.getWorkspaceURL(group) + "/contacts/segments/" +
			segmentId;
	}

	private NotificationContext _resolveContext(long groupId, long userId) {
		FaroProject faroProject =
			_faroProjectLocalService.fetchFaroProjectByGroupId(groupId);

		if (faroProject == null) {
			return null;
		}

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return new NotificationContext(faroProject, group, user);
	}

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference
	private EmailReportHelper _emailReportHelper;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private UserLocalService _userLocalService;

	private static class NotificationContext {

		private NotificationContext(
			FaroProject faroProject, Group group, User user) {

			_faroProject = faroProject;
			_group = group;
			_user = user;
		}

		private final FaroProject _faroProject;
		private final Group _group;
		private final User _user;

	}

}