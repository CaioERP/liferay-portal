/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycle;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStage;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nilton Vieira
 */
public class AccountLifecycleDTOConverterTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleDTOConverter, "_accountLifecycleStageDTOConverter",
			new AccountLifecycleStageDTOConverter());
	}

	@Test
	public void testToDTO() {
		Assert.assertNull(_accountLifecycleDTOConverter.toDTO(null, null));

		com.liferay.osb.faro.engine.client.model.AccountLifecycleStage
			engineClientAccountLifecycleStage =
				new com.liferay.osb.faro.engine.client.model.
					AccountLifecycleStage();

		engineClientAccountLifecycleStage.setDescription("Pipeline");
		engineClientAccountLifecycleStage.setDisplayOrder(3);
		engineClientAccountLifecycleStage.setId("stage-3");
		engineClientAccountLifecycleStage.setMaxDuration(30);
		engineClientAccountLifecycleStage.setStageType("PIPELINE");

		com.liferay.osb.faro.engine.client.model.AccountLifecycle
			engineClientAccountLifecycle =
				new com.liferay.osb.faro.engine.client.model.AccountLifecycle();

		engineClientAccountLifecycle.setDefaultAccountLifecycle(true);
		engineClientAccountLifecycle.setDescription("Sales lifecycle");
		engineClientAccountLifecycle.setId("lifecycle-1");
		engineClientAccountLifecycle.setModifiedDate(new Date());
		engineClientAccountLifecycle.setName("Sales");
		engineClientAccountLifecycle.setStages(
			Collections.singletonList(engineClientAccountLifecycleStage));

		AccountLifecycle accountLifecycle = _accountLifecycleDTOConverter.toDTO(
			null, engineClientAccountLifecycle);

		Assert.assertEquals(
			engineClientAccountLifecycle.getModifiedDate(),
			accountLifecycle.getDateModified());
		Assert.assertEquals(
			engineClientAccountLifecycle.isDefaultAccountLifecycle(),
			accountLifecycle.getDefaultAccountLifecycle());
		Assert.assertEquals(
			engineClientAccountLifecycle.getDescription(),
			accountLifecycle.getDescription());
		Assert.assertEquals(
			engineClientAccountLifecycle.getId(), accountLifecycle.getId());
		Assert.assertEquals(
			engineClientAccountLifecycle.getName(), accountLifecycle.getName());

		AccountLifecycleStage[] accountLifecycleStages =
			accountLifecycle.getAccountLifecycleStages();

		Assert.assertEquals(
			accountLifecycleStages.toString(), 1,
			accountLifecycleStages.length);

		AccountLifecycleStage accountLifecycleStage = accountLifecycleStages[0];

		Assert.assertEquals(
			engineClientAccountLifecycleStage.getId(),
			accountLifecycleStage.getId());
		Assert.assertEquals(
			engineClientAccountLifecycleStage.getStageType(),
			accountLifecycleStage.getStageType());
	}

	private final AccountLifecycleDTOConverter _accountLifecycleDTOConverter =
		new AccountLifecycleDTOConverter();

}