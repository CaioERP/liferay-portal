/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.helper;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStage;
import com.liferay.osb.faro.engine.client.model.LifecycleTriggerResult;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Caio Pinheiro
 */
public class NotificationHelperTest {

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtils.setField(
			_notificationHelper, "_contactsEngineClient",
			_contactsEngineClient);
		ReflectionTestUtils.setField(
			_notificationHelper, "_faroProjectLocalService",
			_faroProjectLocalService);
		ReflectionTestUtils.setField(
			_notificationHelper, "_groupLocalService", _groupLocalService);
		ReflectionTestUtils.setField(
			_notificationHelper, "_notificationPreferencesHelper",
			_notificationPreferencesHelper);

		Mockito.when(
			_faroProjectLocalService.fetchFaroProjectByGroupId(1L)
		).thenReturn(
			_faroProject
		);

		Mockito.when(
			_group.getGroupId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_groupLocalService.fetchGroup(1L)
		).thenReturn(
			_group
		);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStageTransitions(
				Mockito.eq(_faroProject), Mockito.any(), Mockito.any(),
				Mockito.eq("100"), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.any())
		).thenReturn(
			new Results<>(Collections.emptyList(), 3)
		);
	}

	@Test
	public void testGetLifecycleId() throws Exception {
		_mockCurrentAccountLifecycle("erc-1", "200");

		Assert.assertEquals("200", _getLifecycleId("erc-1"));
	}

	@Test
	public void testGetLifecycleIdWithLegacyId() throws Exception {
		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setExternalReferenceCode("erc-1");
		accountLifecycle.setId("100");

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenReturn(
			accountLifecycle
		);

		_mockCurrentAccountLifecycle("erc-1", "200");

		Assert.assertEquals("200", _getLifecycleId("100"));
	}

	@Test
	public void testGetLifecycleIdWithLegacyIdWithoutExternalReferenceCode()
		throws Exception {

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setId("100");

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenReturn(
			accountLifecycle
		);

		Assert.assertEquals("100", _getLifecycleId("100"));

		Mockito.verify(
			_contactsEngineClient, Mockito.never()
		).getAccountLifecycles(
			Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
			Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any()
		);
	}

	@Test
	public void testGetLifecycleIdWithUnknownExternalReferenceCode()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycles(
				_faroProject, null, null, "erc-9", null, 1, 1, null)
		).thenReturn(
			new Results<>(Collections.emptyList(), 0)
		);

		Assert.assertNull(_getLifecycleId("erc-9"));
	}

	@Test
	public void testGetLifecycleTriggerResult() throws Exception {
		_mockAccountLifecycle("Aware", "pipeline ");

		LifecycleTriggerResult lifecycleTriggerResult =
			_getLifecycleTriggerResult(
				LifecycleTriggerType.NET_NEW_PIPELINE_ACCOUNTS);

		Assert.assertEquals(3, lifecycleTriggerResult.getCount());

		Mockito.verify(
			_contactsEngineClient
		).getAccountLifecycleStageTransitions(
			Mockito.eq(_faroProject), Mockito.isNull(), Mockito.isNull(),
			Mockito.eq("100"), Mockito.isNull(), Mockito.isNull(),
			Mockito.any(), Mockito.isNull(), Mockito.isNull(), Mockito.eq("2"),
			Mockito.anyInt(), Mockito.anyInt(), Mockito.any()
		);
	}

	@Test
	public void testGetLifecycleTriggerResultWithRenamedStage()
		throws Exception {

		_mockAccountLifecycle("Aware", "Deal");

		LifecycleTriggerResult lifecycleTriggerResult =
			_getLifecycleTriggerResult(
				LifecycleTriggerType.NET_NEW_PIPELINE_ACCOUNTS);

		Assert.assertEquals(0, lifecycleTriggerResult.getCount());

		Mockito.verify(
			_contactsEngineClient, Mockito.never()
		).getAccountLifecycleStageTransitions(
			Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
			Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
			Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(),
			Mockito.any()
		);
	}

	@Test
	public void testSendLifecycleNotificationsWithDeletedLifecycle()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenThrow(
			new NoSuchEntryException()
		);

		_notificationHelper.sendLifecycleNotifications(
			RandomTestUtil.randomString(), "100",
			Collections.singletonMap(
				10L, List.of(LifecycleTriggerType.NET_NEW_PIPELINE_ACCOUNTS)),
			_notificationHelper.getNotificationContext(1L));

		Mockito.verify(
			_notificationPreferencesHelper
		).removeLifecycleNotificationPreferences(
			1L, Collections.singleton("100")
		);
	}

	@Test
	public void testSendLifecycleNotificationsWithEngineFailure()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenThrow(
			new FaroEngineClientException(RandomTestUtil.randomString())
		);

		Assert.assertThrows(
			FaroEngineClientException.class,
			() -> _notificationHelper.sendLifecycleNotifications(
				RandomTestUtil.randomString(), "100",
				Collections.singletonMap(
					10L,
					List.of(LifecycleTriggerType.NET_NEW_PIPELINE_ACCOUNTS)),
				_notificationHelper.getNotificationContext(1L)));

		Mockito.verify(
			_notificationPreferencesHelper, Mockito.never()
		).removeLifecycleNotificationPreferences(
			Mockito.anyLong(), Mockito.any()
		);
	}

	@Test
	public void testSendLifecycleNotificationsWithUnresolvableLifecycle()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycles(
				_faroProject, null, null, "erc-9", null, 1, 1, null)
		).thenReturn(
			new Results<>(Collections.emptyList(), 0)
		);

		_notificationHelper.sendLifecycleNotifications(
			RandomTestUtil.randomString(), "erc-9",
			Collections.singletonMap(
				10L, List.of(LifecycleTriggerType.NET_NEW_PIPELINE_ACCOUNTS)),
			_notificationHelper.getNotificationContext(1L));

		Mockito.verify(
			_notificationPreferencesHelper
		).removeLifecycleNotificationPreferences(
			1L, Collections.singleton("erc-9")
		);
	}

	private String _getLifecycleId(String lifecycleKey) {
		return ReflectionTestUtils.invokeMethod(
			_notificationHelper, "_getLifecycleId", _faroProject, lifecycleKey);
	}

	private LifecycleTriggerResult _getLifecycleTriggerResult(
		LifecycleTriggerType lifecycleTriggerType) {

		return ReflectionTestUtils.invokeMethod(
			_notificationHelper, "_getLifecycleTriggerResult", _faroProject,
			"100", lifecycleTriggerType, 7);
	}

	private void _mockAccountLifecycle(String firstName, String secondName)
		throws Exception {

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		AccountLifecycleStage firstAccountLifecycleStage =
			new AccountLifecycleStage();

		firstAccountLifecycleStage.setId(RandomTestUtil.randomString());
		firstAccountLifecycleStage.setName(firstName);

		AccountLifecycleStage secondAccountLifecycleStage =
			new AccountLifecycleStage();

		secondAccountLifecycleStage.setId("2");
		secondAccountLifecycleStage.setName(secondName);

		accountLifecycle.setStages(
			List.of(firstAccountLifecycleStage, secondAccountLifecycleStage));

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenReturn(
			accountLifecycle
		);
	}

	private void _mockCurrentAccountLifecycle(
			String externalReferenceCode, String id)
		throws Exception {

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setExternalReferenceCode(externalReferenceCode);
		accountLifecycle.setId(id);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycles(
				_faroProject, null, null, externalReferenceCode, null, 1, 1,
				null)
		).thenReturn(
			new Results<>(List.of(accountLifecycle), 1)
		);
	}

	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);
	private final Group _group = Mockito.mock(Group.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final NotificationHelper _notificationHelper =
		new NotificationHelper();
	private final NotificationPreferencesHelper _notificationPreferencesHelper =
		Mockito.mock(NotificationPreferencesHelper.class);

}