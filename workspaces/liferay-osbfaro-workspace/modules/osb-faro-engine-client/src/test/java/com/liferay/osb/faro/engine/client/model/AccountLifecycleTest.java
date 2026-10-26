/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleTest {

	@Test
	public void testGetAccountLifecycleStage() {
		AccountLifecycle accountLifecycle = new AccountLifecycle();

		AccountLifecycleStage awareAccountLifecycleStage =
			_createAccountLifecycleStage("Aware");
		AccountLifecycleStage pipelineAccountLifecycleStage =
			_createAccountLifecycleStage("Pipeline ");

		accountLifecycle.setStages(
			List.of(awareAccountLifecycleStage, pipelineAccountLifecycleStage));

		Assert.assertSame(
			pipelineAccountLifecycleStage,
			accountLifecycle.getAccountLifecycleStage(" pipeline"));

		Assert.assertNull(accountLifecycle.getAccountLifecycleStage("Renewal"));
		Assert.assertNull(accountLifecycle.getAccountLifecycleStage(null));
	}

	@Test
	public void testGetAccountLifecycleStageWithLegacyStageType() {
		AccountLifecycle accountLifecycle = new AccountLifecycle();

		AccountLifecycleStage atRiskAccountLifecycleStage =
			_createAccountLifecycleStage("At Risk");

		accountLifecycle.setStages(List.of(atRiskAccountLifecycleStage));

		Assert.assertSame(
			atRiskAccountLifecycleStage,
			accountLifecycle.getAccountLifecycleStage("AT_RISK"));
	}

	private AccountLifecycleStage _createAccountLifecycleStage(String name) {
		AccountLifecycleStage accountLifecycleStage =
			new AccountLifecycleStage();

		accountLifecycleStage.setId(RandomTestUtil.randomString());
		accountLifecycleStage.setName(name);

		return accountLifecycleStage;
	}

}