/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.instance.lifecycle;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationRecipientConstants;
import com.liferay.notification.constants.NotificationRecipientSettingConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Caio Pinheiro
 */
public class AddFaroNotificationTemplatesPortalInstanceLifecycleListenerTest {

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_company.getCompanyId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_user.getUserId()
		).thenReturn(
			10L
		);

		Mockito.when(
			_userLocalService.getUsersByRoleName(
				1L, RoleConstants.ADMINISTRATOR, 0, 1)
		).thenReturn(
			Collections.singletonList(_user)
		);

		Mockito.when(
			_notificationTemplateLocalService.createNotificationTemplate(0)
		).thenReturn(
			Mockito.mock(NotificationTemplate.class),
			Mockito.mock(NotificationTemplate.class)
		);

		Mockito.when(
			_notificationRecipientLocalService.createNotificationRecipient(0)
		).thenReturn(
			Mockito.mock(NotificationRecipient.class),
			Mockito.mock(NotificationRecipient.class)
		);

		Mockito.when(
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0)
		).thenAnswer(
			invocation -> Mockito.mock(NotificationRecipientSetting.class)
		);

		ReflectionTestUtils.setField(
			_listener, "_notificationRecipientLocalService",
			_notificationRecipientLocalService);
		ReflectionTestUtils.setField(
			_listener, "_notificationRecipientSettingLocalService",
			_notificationRecipientSettingLocalService);
		ReflectionTestUtils.setField(
			_listener, "_notificationTemplateLocalService",
			_notificationTemplateLocalService);
		ReflectionTestUtils.setField(
			_listener, "_userLocalService", _userLocalService);
	}

	@Test
	public void testPortalInstanceRegisteredWhenNoAdminUserExists()
		throws Exception {

		Mockito.when(
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					Mockito.anyString(), Mockito.eq(1L))
		).thenReturn(
			null
		);

		Mockito.when(
			_userLocalService.getUsersByRoleName(
				1L, RoleConstants.ADMINISTRATOR, 0, 1)
		).thenReturn(
			Collections.emptyList()
		);

		_listener.portalInstanceRegistered(_company);

		Mockito.verify(
			_notificationTemplateLocalService, Mockito.never()
		).addNotificationTemplate(
			Mockito.any(NotificationContext.class)
		);
	}

	@Test
	public void testPortalInstanceRegisteredWhenNotificationTemplatesDoNotExist()
		throws Exception {

		Mockito.when(
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					Mockito.anyString(), Mockito.eq(1L))
		).thenReturn(
			null
		);

		_listener.portalInstanceRegistered(_company);

		ArgumentCaptor<NotificationContext> notificationContextArgumentCaptor =
			ArgumentCaptor.forClass(NotificationContext.class);

		Mockito.verify(
			_notificationTemplateLocalService, Mockito.times(2)
		).addNotificationTemplate(
			notificationContextArgumentCaptor.capture()
		);

		List<NotificationContext> notificationContexts =
			notificationContextArgumentCaptor.getAllValues();

		for (NotificationContext notificationContext : notificationContexts) {
			Assert.assertEquals(
				NotificationConstants.TYPE_EMAIL,
				notificationContext.getType());
			Assert.assertNotNull(
				notificationContext.getNotificationRecipient());
			Assert.assertEquals(
				3,
				notificationContext.getNotificationRecipientSettings(
				).size());
		}
	}

	@Test
	public void testPortalInstanceRegisteredWhenNotificationTemplatesExist()
		throws Exception {

		Mockito.when(
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					Mockito.anyString(), Mockito.eq(1L))
		).thenReturn(
			Mockito.mock(NotificationTemplate.class)
		);

		_listener.portalInstanceRegistered(_company);

		Mockito.verify(
			_notificationTemplateLocalService, Mockito.never()
		).addNotificationTemplate(
			Mockito.any(NotificationContext.class)
		);
	}

	@Test
	public void testPortalInstanceRegisteredWritesFromAndToTypeSettings()
		throws Exception {

		Mockito.when(
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					Mockito.anyString(), Mockito.eq(1L))
		).thenReturn(
			null
		);

		NotificationRecipientSetting fromNotificationRecipientSetting =
			Mockito.mock(NotificationRecipientSetting.class);
		NotificationRecipientSetting fromNameNotificationRecipientSetting =
			Mockito.mock(NotificationRecipientSetting.class);
		NotificationRecipientSetting toTypeNotificationRecipientSetting =
			Mockito.mock(NotificationRecipientSetting.class);

		Mockito.when(
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(0)
		).thenReturn(
			fromNotificationRecipientSetting,
			fromNameNotificationRecipientSetting,
			toTypeNotificationRecipientSetting,
			Mockito.mock(NotificationRecipientSetting.class),
			Mockito.mock(NotificationRecipientSetting.class),
			Mockito.mock(NotificationRecipientSetting.class)
		);

		_listener.portalInstanceRegistered(_company);

		Mockito.verify(
			fromNotificationRecipientSetting
		).setName(
			NotificationRecipientSettingConstants.NAME_FROM
		);

		Mockito.verify(
			fromNameNotificationRecipientSetting
		).setName(
			NotificationRecipientSettingConstants.NAME_FROM_NAME
		);

		Mockito.verify(
			toTypeNotificationRecipientSetting
		).setName(
			NotificationRecipientSettingConstants.NAME_TO_TYPE
		);

		Mockito.verify(
			toTypeNotificationRecipientSetting
		).setValue(
			NotificationRecipientConstants.TYPE_SUBSCRIBERS
		);
	}

	private final Company _company = Mockito.mock(Company.class);
	private final AddFaroNotificationTemplatesPortalInstanceLifecycleListener
		_listener =
			new AddFaroNotificationTemplatesPortalInstanceLifecycleListener();
	private final NotificationRecipientLocalService
		_notificationRecipientLocalService = Mockito.mock(
			NotificationRecipientLocalService.class);
	private final NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService = Mockito.mock(
			NotificationRecipientSettingLocalService.class);
	private final NotificationTemplateLocalService
		_notificationTemplateLocalService = Mockito.mock(
			NotificationTemplateLocalService.class);
	private final User _user = Mockito.mock(User.class);
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}