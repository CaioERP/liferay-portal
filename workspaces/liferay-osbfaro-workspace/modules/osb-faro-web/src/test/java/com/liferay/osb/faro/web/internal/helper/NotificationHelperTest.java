/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.helper;

import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.engine.client.model.LifecycleTriggerResult;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.constants.FaroNotificationTemplateConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Caio Pinheiro
 */
public class NotificationHelperTest {

	@Before
	public void setUp() {
		Mockito.when(
			_group.getCompanyId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_group.getGroupId()
		).thenReturn(
			2L
		);

		Mockito.when(
			_groupLocalService.fetchGroup(2L)
		).thenReturn(
			_group
		);

		Mockito.when(
			_faroProjectLocalService.fetchFaroProjectByGroupId(2L)
		).thenReturn(
			_faroProject
		);

		Mockito.when(
			_user.getEmailAddress()
		).thenReturn(
			"test@liferay.com"
		);

		Mockito.when(
			_user.getUserId()
		).thenReturn(
			3L
		);

		Mockito.when(
			_userLocalService.fetchUser(3L)
		).thenReturn(
			_user
		);

		ReflectionTestUtils.setField(
			_notificationHelper, "_contactsEngineClient",
			_contactsEngineClient);
		ReflectionTestUtils.setField(
			_notificationHelper, "_emailReportHelper", _emailReportHelper);
		ReflectionTestUtils.setField(
			_notificationHelper, "_faroProjectLocalService",
			_faroProjectLocalService);
		ReflectionTestUtils.setField(
			_notificationHelper, "_groupLocalService", _groupLocalService);
		ReflectionTestUtils.setField(
			_notificationHelper, "_notificationTemplateLocalService",
			_notificationTemplateLocalService);
		ReflectionTestUtils.setField(
			_notificationHelper, "_userLocalService", _userLocalService);
	}

	@Test
	public void testSendLifecycleNotificationWhenCountIsZero()
		throws Exception {

		Mockito.when(
			_emailReportHelper.getRangeKey("daily")
		).thenReturn(
			1
		);

		Mockito.when(
			_contactsEngineClient.getLifecycleNewAccountsResult(
				_faroProject, "L1", 1)
		).thenReturn(
			null
		);

		_notificationHelper.sendLifecycleNotification(
			2L, "L1", LifecycleTriggerType.NEW_ACCOUNTS, "daily", 3L);

		Mockito.verify(
			_notificationTemplateLocalService, Mockito.never()
		).fetchNotificationTemplateByExternalReferenceCode(
			Mockito.anyString(), Mockito.anyLong()
		);
	}

	@Test
	public void testSendLifecycleNotificationWhenNotificationTemplateDoesNotExist()
		throws Exception {

		Mockito.when(
			_emailReportHelper.getRangeKey("daily")
		).thenReturn(
			1
		);

		LifecycleTriggerResult lifecycleTriggerResult = Mockito.mock(
			LifecycleTriggerResult.class);

		Mockito.when(
			lifecycleTriggerResult.getCount()
		).thenReturn(
			5L
		);

		Mockito.when(
			_contactsEngineClient.getLifecycleNewAccountsResult(
				_faroProject, "L1", 1)
		).thenReturn(
			lifecycleTriggerResult
		);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "L1")
		).thenReturn(
			Mockito.mock(AccountLifecycle.class)
		);

		NotificationTemplate notificationTemplate = Mockito.mock(
			NotificationTemplate.class);

		Mockito.when(
			_notificationTemplateLocalService.createNotificationTemplate(0)
		).thenReturn(
			notificationTemplate
		);

		try {
			_notificationHelper.sendLifecycleNotification(
				2L, "L1", LifecycleTriggerType.NEW_ACCOUNTS, "daily", 3L);
		}
		catch (Exception exception) {

			// See note in the segment-path test above.

		}

		Mockito.verify(
			_notificationTemplateLocalService
		).fetchNotificationTemplateByExternalReferenceCode(
			FaroNotificationTemplateConstants.
				EXTERNAL_REFERENCE_CODE_LIFECYCLE_NOTIFICATION,
			1L
		);

		String defaultSubject =
			FaroNotificationTemplateConstants.getDefaultNotificationTemplates(
			).get(
				FaroNotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_LIFECYCLE_NOTIFICATION
			).get(
				"subject"
			);

		Mockito.verify(
			notificationTemplate
		).setSubjectMap(
			Collections.singletonMap(LocaleUtil.getDefault(), defaultSubject)
		);
	}

	@Test
	public void testSendSegmentNotificationWhenCountIsZero() throws Exception {
		Mockito.when(
			_emailReportHelper.getRangeKey("daily")
		).thenReturn(
			1
		);

		Mockito.when(
			_contactsEngineClient.getSegmentNewMembersCount(
				_faroProject, "123", 1)
		).thenReturn(
			0L
		);

		_notificationHelper.sendSegmentNotification(2L, "123", "daily", 3L);

		Mockito.verify(
			_notificationTemplateLocalService, Mockito.never()
		).fetchNotificationTemplateByExternalReferenceCode(
			Mockito.anyString(), Mockito.anyLong()
		);
	}

	@Test
	public void testSendSegmentNotificationWhenNotificationTemplateDoesNotExist()
		throws Exception {

		Mockito.when(
			_emailReportHelper.getRangeKey("daily")
		).thenReturn(
			1
		);

		Mockito.when(
			_contactsEngineClient.getSegmentNewMembersCount(
				_faroProject, "123", 1)
		).thenReturn(
			5L
		);

		Mockito.when(
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					FaroNotificationTemplateConstants.
						EXTERNAL_REFERENCE_CODE_SEGMENT_NOTIFICATION,
					1L)
		).thenReturn(
			null
		);

		NotificationTemplate notificationTemplate = Mockito.mock(
			NotificationTemplate.class);

		Mockito.when(
			_notificationTemplateLocalService.createNotificationTemplate(0)
		).thenReturn(
			notificationTemplate
		);

		try {
			_notificationHelper.sendSegmentNotification(2L, "123", "daily", 3L);
		}
		catch (Exception exception) {

			// SubscriptionSender#flushNotifications requires a running
			// portal instance and is expected to fail in this plain unit
			// test; only the notification-template fetch/fallback above it
			// is under test here.

		}

		Mockito.verify(
			_notificationTemplateLocalService
		).fetchNotificationTemplateByExternalReferenceCode(
			FaroNotificationTemplateConstants.
				EXTERNAL_REFERENCE_CODE_SEGMENT_NOTIFICATION,
			1L
		);

		String defaultSubject =
			FaroNotificationTemplateConstants.getDefaultNotificationTemplates(
			).get(
				FaroNotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_SEGMENT_NOTIFICATION
			).get(
				"subject"
			);

		Mockito.verify(
			notificationTemplate
		).setSubjectMap(
			Collections.singletonMap(LocaleUtil.getDefault(), defaultSubject)
		);
	}

	@Test
	public void testSendSegmentNotificationWhenNotificationTemplateExists()
		throws Exception {

		Mockito.when(
			_emailReportHelper.getRangeKey("daily")
		).thenReturn(
			1
		);

		Mockito.when(
			_contactsEngineClient.getSegmentNewMembersCount(
				_faroProject, "123", 1)
		).thenReturn(
			5L
		);

		NotificationTemplate notificationTemplate = Mockito.mock(
			NotificationTemplate.class);

		Mockito.when(
			_notificationTemplateLocalService.
				fetchNotificationTemplateByExternalReferenceCode(
					FaroNotificationTemplateConstants.
						EXTERNAL_REFERENCE_CODE_SEGMENT_NOTIFICATION,
					1L)
		).thenReturn(
			notificationTemplate
		);

		try {
			_notificationHelper.sendSegmentNotification(2L, "123", "daily", 3L);
		}
		catch (Exception exception) {

			// See note in the sibling test above.

		}

		Mockito.verify(
			_notificationTemplateLocalService, Mockito.never()
		).createNotificationTemplate(
			Mockito.anyLong()
		);
	}

	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final EmailReportHelper _emailReportHelper = Mockito.mock(
		EmailReportHelper.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final NotificationHelper _notificationHelper =
		new NotificationHelper();
	private final NotificationTemplateLocalService
		_notificationTemplateLocalService = Mockito.mock(
			NotificationTemplateLocalService.class);
	private final User _user = Mockito.mock(User.class);
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}