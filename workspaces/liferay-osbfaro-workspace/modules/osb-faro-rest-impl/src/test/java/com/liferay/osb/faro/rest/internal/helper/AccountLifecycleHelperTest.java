/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.helper;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;
import com.liferay.osb.faro.engine.client.model.AccountLifecycle;
import com.liferay.osb.faro.engine.client.model.IndividualSegment;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Caio Pinheiro
 */
public class AccountLifecycleHelperTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_accountLifecycleHelper, "_contactsEngineClient",
			_contactsEngineClient);
	}

	@Test
	public void testGetAccountLifecycle() {
		AccountLifecycle accountLifecycle = _mockAccountLifecycle(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertSame(
			accountLifecycle,
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, _CHANNEL_ID, accountLifecycle.getId()));
	}

	@Test
	public void testGetAccountLifecycleFromAnotherChannel() {
		AccountLifecycle accountLifecycle = _mockAccountLifecycle(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_assertNotFoundException(
			accountLifecycle.getId(), RandomTestUtil.randomString());
	}

	@Test
	public void testGetAccountLifecycleNotFound() {
		String accountLifecycleId1 = RandomTestUtil.randomString();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId1)
		).thenThrow(
			new NoSuchEntryException(RandomTestUtil.randomString())
		);

		_assertNotFoundException(accountLifecycleId1, _CHANNEL_ID);

		String accountLifecycleId2 = RandomTestUtil.randomString();

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId2)
		).thenThrow(
			new FaroEngineClientException(RandomTestUtil.randomString(), 400)
		);

		_assertNotFoundException(accountLifecycleId2, _CHANNEL_ID);

		String segmentId = RandomTestUtil.randomString();

		AccountLifecycle accountLifecycle = _mockAccountLifecycle(
			RandomTestUtil.randomString(), segmentId);

		Mockito.when(
			_contactsEngineClient.getIndividualSegment(
				_faroProject, segmentId, false)
		).thenThrow(
			new NoSuchEntryException(RandomTestUtil.randomString())
		);

		_assertNotFoundException(accountLifecycle.getId(), _CHANNEL_ID);
	}

	@Test
	public void testGetAccountLifecycleWithEngineError() {
		String accountLifecycleId = RandomTestUtil.randomString();
		FaroEngineClientException faroEngineClientException1 =
			new FaroEngineClientException(RandomTestUtil.randomString(), 500);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId)
		).thenThrow(
			faroEngineClientException1
		);

		try {
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, _CHANNEL_ID, accountLifecycleId);

			Assert.fail();
		}
		catch (FaroEngineClientException faroEngineClientException2) {
			Assert.assertSame(
				faroEngineClientException1, faroEngineClientException2);
		}
	}

	@Test
	public void testGetAccountLifecycleWithoutSegment() {
		AccountLifecycle accountLifecycle = _mockAccountLifecycle(
			RandomTestUtil.randomString(), null);

		_assertNotFoundException(accountLifecycle.getId(), _CHANNEL_ID);

		Mockito.verify(
			_contactsEngineClient, Mockito.never()
		).getIndividualSegment(
			Mockito.any(), Mockito.any(), Mockito.anyBoolean()
		);
	}

	private void _assertNotFoundException(
		String accountLifecycleId, String channelId) {

		try {
			_accountLifecycleHelper.getAccountLifecycle(
				_faroProject, channelId, accountLifecycleId);

			Assert.fail();
		}
		catch (NotFoundException notFoundException) {
			String expectedTitle =
				"No account lifecycle was found with ID " + accountLifecycleId;

			Assert.assertEquals(expectedTitle, notFoundException.getMessage());

			Response response = notFoundException.getResponse();

			Problem problem = (Problem)response.getEntity();

			Assert.assertEquals(Response.Status.NOT_FOUND, problem.getStatus());
			Assert.assertEquals(expectedTitle, problem.getTitle());
		}
	}

	private AccountLifecycle _mockAccountLifecycle(
		String accountLifecycleId, String segmentId) {

		AccountLifecycle accountLifecycle = new AccountLifecycle();

		accountLifecycle.setId(accountLifecycleId);
		accountLifecycle.setSegmentId(segmentId);

		Mockito.when(
			_contactsEngineClient.getAccountLifecycle(
				_faroProject, accountLifecycleId)
		).thenReturn(
			accountLifecycle
		);

		if (segmentId == null) {
			return accountLifecycle;
		}

		IndividualSegment individualSegment = new IndividualSegment();

		individualSegment.setChannelId(_CHANNEL_ID);
		individualSegment.setId(segmentId);

		Mockito.when(
			_contactsEngineClient.getIndividualSegment(
				_faroProject, segmentId, false)
		).thenReturn(
			individualSegment
		);

		return accountLifecycle;
	}

	private static final String _CHANNEL_ID = RandomTestUtil.randomString();

	private final AccountLifecycleHelper _accountLifecycleHelper =
		new AccountLifecycleHelper();
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final FaroProject _faroProject = Mockito.mock(FaroProject.class);

}