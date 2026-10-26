/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.helper;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.service.FaroPreferencesLocalService;
import com.liferay.osb.faro.web.internal.model.preferences.WorkspacePreferences;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Caio Pinheiro
 */
public class NotificationPreferencesHelperTest {

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(
			_notificationPreferencesHelper, "_faroPreferencesLocalService",
			_faroPreferencesLocalService);
	}

	@Test
	public void testRemoveLifecycleNotificationPreferences() throws Exception {
		FaroPreferences faroPreferences = _createFaroPreferences(10L, "erc-1");
		FaroPreferences otherFaroPreferences = _createFaroPreferences(
			11L, "erc-2");

		Mockito.when(
			_faroPreferencesLocalService.getFaroPreferencesByGroupId(1L)
		).thenReturn(
			List.of(faroPreferences, otherFaroPreferences)
		);

		_notificationPreferencesHelper.removeLifecycleNotificationPreferences(
			1L, Collections.singleton("erc-1"));

		Mockito.verify(
			_faroPreferencesLocalService
		).savePreferences(
			Mockito.eq(10L), Mockito.eq(1L), Mockito.eq(10L),
			Mockito.anyString()
		);

		Mockito.verify(
			_faroPreferencesLocalService, Mockito.never()
		).savePreferences(
			Mockito.eq(11L), Mockito.anyLong(), Mockito.anyLong(),
			Mockito.anyString()
		);
	}

	private FaroPreferences _createFaroPreferences(long userId, String key)
		throws Exception {

		WorkspacePreferences workspacePreferences = new WorkspacePreferences();

		workspacePreferences.addLifecycleNotificationPreference(
			true, RandomTestUtil.randomString(), key, false, false, false,
			false);

		FaroPreferences faroPreferences = Mockito.mock(FaroPreferences.class);

		Mockito.when(
			faroPreferences.getOwnerId()
		).thenReturn(
			userId
		);

		Mockito.when(
			faroPreferences.getPreferences()
		).thenReturn(
			JSONUtil.writeValueAsString(workspacePreferences)
		);

		Mockito.when(
			faroPreferences.getUserId()
		).thenReturn(
			userId
		);

		return faroPreferences;
	}

	private final FaroPreferencesLocalService _faroPreferencesLocalService =
		Mockito.mock(FaroPreferencesLocalService.class);
	private final NotificationPreferencesHelper _notificationPreferencesHelper =
		new NotificationPreferencesHelper();

}