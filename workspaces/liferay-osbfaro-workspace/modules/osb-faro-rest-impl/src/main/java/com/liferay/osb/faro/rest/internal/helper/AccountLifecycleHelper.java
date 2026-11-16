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
import com.liferay.osb.faro.rest.internal.jaxrs.util.ProblemUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Caio Pinheiro
 */
@Component(service = AccountLifecycleHelper.class)
public class AccountLifecycleHelper {

	public AccountLifecycle getAccountLifecycle(
		FaroProject faroProject, String channelId, String accountLifecycleId) {

		try {
			AccountLifecycle accountLifecycle =
				_contactsEngineClient.getAccountLifecycle(
					faroProject, accountLifecycleId);

			String segmentId = accountLifecycle.getSegmentId();

			if (Validator.isNull(segmentId)) {
				throw ProblemUtil.getNotFoundException(
					_getNotFoundExceptionTitle(accountLifecycleId));
			}

			IndividualSegment individualSegment =
				_contactsEngineClient.getIndividualSegment(
					faroProject, segmentId, false);

			if (!Objects.equals(individualSegment.getChannelId(), channelId)) {
				throw ProblemUtil.getNotFoundException(
					_getNotFoundExceptionTitle(accountLifecycleId));
			}

			return accountLifecycle;
		}
		catch (FaroEngineClientException faroEngineClientException) {
			if ((faroEngineClientException instanceof NoSuchEntryException) ||
				(faroEngineClientException.getStatus() ==
					HttpServletResponse.SC_BAD_REQUEST)) {

				throw ProblemUtil.getNotFoundException(
					_getNotFoundExceptionTitle(accountLifecycleId));
			}

			throw faroEngineClientException;
		}
	}

	private String _getNotFoundExceptionTitle(String accountLifecycleId) {
		return "No account lifecycle was found with ID " + accountLifecycleId;
	}

	@Reference
	private ContactsEngineClient _contactsEngineClient;

}