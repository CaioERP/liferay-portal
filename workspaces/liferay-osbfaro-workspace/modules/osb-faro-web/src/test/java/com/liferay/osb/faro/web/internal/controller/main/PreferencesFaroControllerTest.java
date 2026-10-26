/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroPreferencesLocalService;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.model.preferences.LifecycleNotificationPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.WorkspacePreferences;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Caio Pinheiro
 */
public class PreferencesFaroControllerTest {

	@Before
	public void setUp() throws Exception {
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getUserId()
		).thenReturn(
			10L
		);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		ReflectionTestUtils.setField(
			_preferencesFaroController, "_faroPreferencesLocalService",
			_faroPreferencesLocalService);
		ReflectionTestUtils.setField(
			_preferencesFaroController, "contactsEngineClient",
			_contactsEngineClient);
		ReflectionTestUtils.setField(
			_preferencesFaroController, "faroProjectLocalService",
			_faroProjectLocalService);

		Mockito.when(
			_faroProjectLocalService.getFaroProjectByGroupId(1L)
		).thenReturn(
			_faroProject
		);

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setExternalReferenceCode("erc-1");
		accountLifecycle.setId("100");

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenReturn(
			accountLifecycle
		);
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	@Test
	public void testAddLifecycleNotificationPreference() throws Exception {
		_mockWorkspacePreferences("100");

		_preferencesFaroController.addLifecycleNotificationPreference(
			1L, true, RandomTestUtil.randomString(), "100", false, false, false,
			false);

		Assert.assertEquals(Set.of("erc-1"), _getSavedKeys());
	}

	@Test
	public void testAddLifecycleNotificationPreferenceWithOlderVersionKey()
		throws Exception {

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setExternalReferenceCode("erc-1");
		accountLifecycle.setId("90");

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "90")
		).thenReturn(
			accountLifecycle
		);

		_mockWorkspacePreferences("90");

		_preferencesFaroController.addLifecycleNotificationPreference(
			1L, true, RandomTestUtil.randomString(), "100", false, false, false,
			false);

		Assert.assertEquals(Set.of("erc-1"), _getSavedKeys());
	}

	@Test
	public void testGetLifecycleNotificationPreferencesWithLegacyKey()
		throws Exception {

		_mockWorkspacePreferences("100");

		Map<String, LifecycleNotificationPreferences>
			lifecycleNotificationPreferences =
				_preferencesFaroController.getLifecycleNotificationPreferences(
					1L, "100");

		LifecycleNotificationPreferences lifecycleNotificationPreference =
			lifecycleNotificationPreferences.get("100");

		Assert.assertEquals(
			"daily", lifecycleNotificationPreference.getEmailFrequency());
	}

	@Test
	public void testGetLifecycleNotificationPreferencesWithOlderVersionKey()
		throws Exception {

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setExternalReferenceCode("erc-1");
		accountLifecycle.setId("90");

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "90")
		).thenReturn(
			accountLifecycle
		);

		_mockWorkspacePreferences("90");

		Map<String, LifecycleNotificationPreferences>
			lifecycleNotificationPreferences =
				_preferencesFaroController.getLifecycleNotificationPreferences(
					1L, "100");

		LifecycleNotificationPreferences lifecycleNotificationPreference =
			lifecycleNotificationPreferences.get("100");

		Assert.assertEquals(
			"daily", lifecycleNotificationPreference.getEmailFrequency());
	}

	@Test
	public void testGetLifecycleNotificationPreferencesWithUnavailableLifecycle()
		throws Exception {

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenThrow(
			new FaroEngineClientException(RandomTestUtil.randomString())
		);

		_mockWorkspacePreferences("100");

		Map<String, LifecycleNotificationPreferences>
			lifecycleNotificationPreferences =
				_preferencesFaroController.getLifecycleNotificationPreferences(
					1L, "100");

		LifecycleNotificationPreferences lifecycleNotificationPreference =
			lifecycleNotificationPreferences.get("100");

		Assert.assertEquals(
			"daily", lifecycleNotificationPreference.getEmailFrequency());
	}

	private Set<String> _getSavedKeys() throws Exception {
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			_faroPreferencesLocalService
		).savePreferences(
			Mockito.eq(10L), Mockito.eq(1L), Mockito.eq(10L),
			argumentCaptor.capture()
		);

		WorkspacePreferences workspacePreferences = JSONUtil.readValue(
			argumentCaptor.getValue(), WorkspacePreferences.class);

		Map<String, LifecycleNotificationPreferences>
			lifecycleNotificationPreferences =
				workspacePreferences.getLifecycleNotificationPreferences(null);

		return lifecycleNotificationPreferences.keySet();
	}

	private void _mockWorkspacePreferences(String key) throws Exception {
		WorkspacePreferences workspacePreferences = new WorkspacePreferences();

		workspacePreferences.addLifecycleNotificationPreference(
			true, "daily", key, false, false, false, false);

		FaroPreferences faroPreferences = Mockito.mock(FaroPreferences.class);

		Mockito.when(
			faroPreferences.getPreferences()
		).thenReturn(
			JSONUtil.writeValueAsString(workspacePreferences)
		);

		Mockito.when(
			_faroPreferencesLocalService.fetchFaroPreferences(1L, 10L)
		).thenReturn(
			faroPreferences
		);
	}

	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroPreferencesLocalService _faroPreferencesLocalService =
		Mockito.mock(FaroPreferencesLocalService.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);
	private PermissionChecker _permissionChecker;
	private final PreferencesFaroController _preferencesFaroController =
		new PreferencesFaroController();

}