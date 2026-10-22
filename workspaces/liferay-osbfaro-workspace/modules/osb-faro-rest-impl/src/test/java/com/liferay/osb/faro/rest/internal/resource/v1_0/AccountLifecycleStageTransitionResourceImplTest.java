/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStage;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleStageTransitionResourceImplTest {

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleStageTransitionResourceImpl,
			"_contactsEngineClient", _contactsEngineClient);
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleStageTransitionResourceImpl,
			"_faroProjectLocalService", _faroProjectLocalService);

		Mockito.when(
			_faroProjectLocalService.getFaroProjectByGroupId(1L)
		).thenReturn(
			_faroProject
		);

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setStages(
			List.of(
				_createAccountLifecycleStage("1", "Aware"),
				_createAccountLifecycleStage("3", "Pipeline")));

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(_faroProject, "100")
		).thenReturn(
			accountLifecycle
		);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycleStageTransitions(
				Mockito.eq(_faroProject), Mockito.any(), Mockito.any(),
				Mockito.eq("100"), Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.any())
		).thenReturn(
			new Results<>(Collections.emptyList(), 0)
		);
	}

	@Test
	public void testGetWorkspaceGroupAccountLifecycleStageTransitionsPage()
		throws Exception {

		_accountLifecycleStageTransitionResourceImpl.
			getWorkspaceGroupAccountLifecycleStageTransitionsPage(
				1L, "100", null, " aware", null, null, null, null, null,
				"PIPELINE", Pagination.of(1, 20), null);

		Mockito.verify(
			_contactsEngineClient
		).getAccountLifecycleStageTransitions(
			Mockito.eq(_faroProject), Mockito.isNull(), Mockito.eq("1"),
			Mockito.eq("100"), Mockito.isNull(), Mockito.isNull(),
			Mockito.any(), Mockito.isNull(), Mockito.isNull(), Mockito.eq("3"),
			Mockito.anyInt(), Mockito.anyInt(), Mockito.any()
		);
	}

	@Test
	public void testGetWorkspaceGroupAccountLifecycleStageTransitionsPageWithUnknownStage()
		throws Exception {

		IllegalArgumentException illegalArgumentException = Assert.assertThrows(
			IllegalArgumentException.class,
			() ->
				_accountLifecycleStageTransitionResourceImpl.
					getWorkspaceGroupAccountLifecycleStageTransitionsPage(
						1L, "100", null, null, null, null, null, null, null,
						"Renewal", Pagination.of(1, 20), null));

		Assert.assertEquals(
			"No lifecycle stage was found with the name \"Renewal\"",
			illegalArgumentException.getMessage());
	}

	private AccountLifecycleStage _createAccountLifecycleStage(
		String id, String name) {

		AccountLifecycleStage accountLifecycleStage =
			new AccountLifecycleStage();

		accountLifecycleStage.setId(id);
		accountLifecycleStage.setName(name);

		return accountLifecycleStage;
	}

	private final AccountLifecycleStageTransitionResourceImpl
		_accountLifecycleStageTransitionResourceImpl =
			new AccountLifecycleStageTransitionResourceImpl();
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);
	private final FaroProjectLocalService _faroProjectLocalService =
		Mockito.mock(FaroProjectLocalService.class);

}