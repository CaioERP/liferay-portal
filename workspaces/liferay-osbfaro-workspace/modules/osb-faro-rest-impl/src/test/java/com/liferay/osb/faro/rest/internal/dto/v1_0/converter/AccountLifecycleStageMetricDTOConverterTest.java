/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleStageMetric;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nilton Vieira
 */
public class AccountLifecycleStageMetricDTOConverterTest {

	@Test
	public void testToDTO() {
		Assert.assertNull(
			_accountLifecycleStageMetricDTOConverter.toDTO(null, null));

		com.liferay.osb.faro.engine.client.model.AccountLifecycleStageMetric
			engineClientAccountLifecycleStageMetric =
				new com.liferay.osb.faro.engine.client.model.
					AccountLifecycleStageMetric();

		engineClientAccountLifecycleStageMetric.setAccountsCount(12);
		engineClientAccountLifecycleStageMetric.setAverageStageDuration(4.5);
		engineClientAccountLifecycleStageMetric.setConversionRateToNextStage(
			0.25);
		engineClientAccountLifecycleStageMetric.setDescription("Pipeline");
		engineClientAccountLifecycleStageMetric.setPercentage(40.0);
		engineClientAccountLifecycleStageMetric.setStageType("PIPELINE");

		AccountLifecycleStageMetric accountLifecycleStageMetric =
			_accountLifecycleStageMetricDTOConverter.toDTO(
				null, engineClientAccountLifecycleStageMetric);

		Assert.assertEquals(
			Long.valueOf(
				engineClientAccountLifecycleStageMetric.getAccountsCount()),
			accountLifecycleStageMetric.getAccountsCount());
		Assert.assertEquals(
			engineClientAccountLifecycleStageMetric.getAverageStageDuration(),
			accountLifecycleStageMetric.getAverageStageDuration(), 0);
		Assert.assertEquals(
			engineClientAccountLifecycleStageMetric.
				getConversionRateToNextStage(),
			accountLifecycleStageMetric.getConversionRateToNextStage());
		Assert.assertEquals(
			engineClientAccountLifecycleStageMetric.getDescription(),
			accountLifecycleStageMetric.getDescription());
		Assert.assertEquals(
			engineClientAccountLifecycleStageMetric.getPercentage(),
			accountLifecycleStageMetric.getPercentage(), 0);
		Assert.assertEquals(
			engineClientAccountLifecycleStageMetric.getStageType(),
			accountLifecycleStageMetric.getStageType());
	}

	private final AccountLifecycleStageMetricDTOConverter
		_accountLifecycleStageMetricDTOConverter =
			new AccountLifecycleStageMetricDTOConverter();

}