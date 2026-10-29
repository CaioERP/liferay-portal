/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.engine.client.model.AccountLifecycleStageStatus;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageHistory;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nilton Vieira
 */
public class AccountLifecycleStageHistoryDTOConverterTest {

	@Test
	public void testToDTO() {
		Assert.assertNull(
			_accountLifecycleStageHistoryDTOConverter.toDTO(null, null));

		AccountLifecycleStageStatus accountLifecycleStageStatus =
			new AccountLifecycleStageStatus();

		accountLifecycleStageStatus.setDescription("Pipeline");
		accountLifecycleStageStatus.setDisplayOrder(3);
		accountLifecycleStageStatus.setEndDate(new Date());
		accountLifecycleStageStatus.setId("stage-3");
		accountLifecycleStageStatus.setMaxDuration(30);
		accountLifecycleStageStatus.setStageType("PIPELINE");
		accountLifecycleStageStatus.setStartDate(new Date());

		AccountLifecycleStageHistory accountLifecycleStageHistory =
			_accountLifecycleStageHistoryDTOConverter.toDTO(
				null, accountLifecycleStageStatus);

		Assert.assertEquals(
			accountLifecycleStageStatus.getDescription(),
			accountLifecycleStageHistory.getDescription());
		Assert.assertEquals(
			accountLifecycleStageStatus.getDisplayOrder(),
			accountLifecycleStageHistory.getDisplayOrder());
		Assert.assertEquals(
			accountLifecycleStageStatus.getEndDate(),
			accountLifecycleStageHistory.getEndDate());
		Assert.assertEquals(
			accountLifecycleStageStatus.getId(),
			accountLifecycleStageHistory.getId());
		Assert.assertEquals(
			accountLifecycleStageStatus.getMaxDuration(),
			accountLifecycleStageHistory.getMaxDuration());
		Assert.assertEquals(
			accountLifecycleStageStatus.getStageType(),
			accountLifecycleStageHistory.getStageType());
		Assert.assertEquals(
			accountLifecycleStageStatus.getStartDate(),
			accountLifecycleStageHistory.getStartDate());
	}

	private final AccountLifecycleStageHistoryDTOConverter
		_accountLifecycleStageHistoryDTOConverter =
			new AccountLifecycleStageHistoryDTOConverter();

}