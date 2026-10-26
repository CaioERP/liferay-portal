/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.preferences;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Caio Pinheiro
 */
public class WorkspacePreferencesTest {

	@Test
	public void testRemoveLifecycleNotificationPreferences() {
		WorkspacePreferences workspacePreferences = new WorkspacePreferences();

		workspacePreferences.addLifecycleNotificationPreference(
			true, RandomTestUtil.randomString(), "erc-1", false, false, false,
			false);
		workspacePreferences.addLifecycleNotificationPreference(
			true, RandomTestUtil.randomString(), "erc-2", false, false, false,
			false);

		Assert.assertTrue(
			workspacePreferences.removeLifecycleNotificationPreferences(
				Collections.singleton("erc-1")));
		Assert.assertFalse(
			workspacePreferences.removeLifecycleNotificationPreferences(
				Collections.singleton("erc-1")));

		Map<String, LifecycleNotificationPreferences>
			lifecycleNotificationPreferences =
				workspacePreferences.getLifecycleNotificationPreferences(null);

		Assert.assertEquals(
			Collections.singleton("erc-2"),
			lifecycleNotificationPreferences.keySet());
	}

}