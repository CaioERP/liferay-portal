/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.engine.client.model.AccountLifecycleStageStatus;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStatus;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleHistory;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageHistory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Nilton Vieira
 */
public class AccountLifecycleHistoryDTOConverterTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHistoryDTOConverter,
			"_accountLifecycleStageHistoryDTOConverter",
			new AccountLifecycleStageHistoryDTOConverter());
	}

	@Test
	public void testToDTO() {
		Assert.assertNull(
			_accountLifecycleHistoryDTOConverter.toDTO(null, null));

		AccountLifecycleStageStatus accountLifecycleStageStatus =
			new AccountLifecycleStageStatus();

		accountLifecycleStageStatus.setId("stage-3");
		accountLifecycleStageStatus.setStageType("PIPELINE");
		accountLifecycleStageStatus.setStartDate(new Date());

		AccountLifecycleStatus accountLifecycleStatus =
			new AccountLifecycleStatus();

		accountLifecycleStatus.setAccountLifecycleStageStatuses(
			Collections.singletonList(accountLifecycleStageStatus));
		accountLifecycleStatus.setId("lifecycle-1");
		accountLifecycleStatus.setName("Sales");

		AccountLifecycleHistory accountLifecycleHistory =
			_accountLifecycleHistoryDTOConverter.toDTO(
				null, accountLifecycleStatus);

		Assert.assertEquals(
			accountLifecycleStatus.getId(), accountLifecycleHistory.getId());
		Assert.assertEquals(
			accountLifecycleStatus.getName(),
			accountLifecycleHistory.getName());

		AccountLifecycleStageHistory[] accountLifecycleStageHistories =
			accountLifecycleHistory.getAccountLifecycleStageHistories();

		Assert.assertEquals(
			accountLifecycleStageHistories.toString(), 1,
			accountLifecycleStageHistories.length);

		AccountLifecycleStageHistory accountLifecycleStageHistory =
			accountLifecycleStageHistories[0];

		Assert.assertNull(accountLifecycleStageHistory.getEndDate());
		Assert.assertEquals(
			accountLifecycleStageStatus.getId(),
			accountLifecycleStageHistory.getId());
		Assert.assertEquals(
			accountLifecycleStageStatus.getStartDate(),
			accountLifecycleStageHistory.getStartDate());
	}

	private final AccountLifecycleHistoryDTOConverter
		_accountLifecycleHistoryDTOConverter =
			new AccountLifecycleHistoryDTOConverter();

}