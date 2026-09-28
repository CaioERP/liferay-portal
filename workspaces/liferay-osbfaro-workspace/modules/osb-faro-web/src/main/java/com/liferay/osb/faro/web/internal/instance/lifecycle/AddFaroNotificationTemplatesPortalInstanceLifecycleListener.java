/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.instance.lifecycle;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.osb.faro.web.internal.constants.FaroNotificationTemplateConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Caio Pinheiro
 */
@Component(
	property = "service.ranking:Integer=" + Integer.MIN_VALUE,
	service = PortalInstanceLifecycleListener.class
)
public class AddFaroNotificationTemplatesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long companyId = company.getCompanyId();

		for (String externalReferenceCode :
				FaroNotificationTemplateConstants.
					getDefaultNotificationTemplates(
					).keySet()) {

			try {
				_verifyNotificationTemplate(externalReferenceCode, companyId);
			}
			catch (Exception exception) {
				_log.error(
					String.format(
						"Unable to provision notification template %s for " +
							"company %s",
						externalReferenceCode, companyId),
					exception);
			}
		}
	}

	private List<NotificationRecipientSetting>
		_createNotificationRecipientSettings() {

		NotificationRecipientSetting fromNotificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0);

		fromNotificationRecipientSetting.setName(
			NotificationRecipientSettingConstants.NAME_FROM);
		fromNotificationRecipientSetting.setValue("noreply@liferay.com");

		NotificationRecipientSetting fromNameNotificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0);

		fromNameNotificationRecipientSetting.setName(
			NotificationRecipientSettingConstants.NAME_FROM_NAME);
		fromNameNotificationRecipientSetting.setValue(
			"Liferay Analytics Cloud");

		NotificationRecipientSetting toTypeNotificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0);

		toTypeNotificationRecipientSetting.setName(
			NotificationRecipientSettingConstants.NAME_TO_TYPE);
		toTypeNotificationRecipientSetting.setValue(
			NotificationRecipientConstants.TYPE_SUBSCRIBERS);

		return Arrays.asList(
			fromNotificationRecipientSetting,
			fromNameNotificationRecipientSetting,
			toTypeNotificationRecipientSetting);
	}

	private User _getAdminUser(long companyId) throws Exception {
		List<User> users = _userLocalService.getUsersByRoleName(
			companyId, RoleConstants.ADMINISTRATOR, 0, 1);

		if (users.isEmpty()) {
			throw new NoSuchUserException(
				"No user exists in company " + companyId + " with role " +
					RoleConstants.ADMINISTRATOR);
		}

		return users.get(0);
	}

	private void _verifyNotificationTemplate(
			String externalReferenceCode, long companyId)
		throws Exception {

		NotificationTemplate existingNotificationTemplate =
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					externalReferenceCode, companyId);

		if (existingNotificationTemplate != null) {
			return;
		}

		Map<String, String> defaultNotificationTemplate =
			FaroNotificationTemplateConstants.getDefaultNotificationTemplates(
			).get(
				externalReferenceCode
			);

		User user = _getAdminUser(companyId);

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.createNotificationTemplate(0);

		notificationTemplate.setExternalReferenceCode(externalReferenceCode);
		notificationTemplate.setCompanyId(companyId);
		notificationTemplate.setUserId(user.getUserId());
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

		NotificationContext notificationContext =
			new NotificationContextBuilder(
			).companyId(
				companyId
			).notificationTemplate(
				notificationTemplate
			).userId(
				user.getUserId()
			).build();

		notificationContext.setType(NotificationConstants.TYPE_EMAIL);

		notificationContext.setNotificationRecipient(
			_notificationRecipientLocalService.createNotificationRecipient(0));

		notificationContext.setNotificationRecipientSettings(
			_createNotificationRecipientSettings());

		_notificationTemplateLocalService.addNotificationTemplate(
			notificationContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddFaroNotificationTemplatesPortalInstanceLifecycleListener.class);

	@Reference
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

	@Reference
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private UserLocalService _userLocalService;

}