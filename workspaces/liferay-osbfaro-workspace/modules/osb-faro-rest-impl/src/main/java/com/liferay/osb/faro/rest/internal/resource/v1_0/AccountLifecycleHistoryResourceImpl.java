/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.exception.FaroEngineClientException;
import com.liferay.osb.faro.engine.client.exception.NoSuchEntryException;
import com.liferay.osb.faro.engine.client.model.AccountLifecycleStatus;
import com.liferay.osb.faro.rest.dto.v1_0.AccountLifecycleHistory;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.resource.v1_0.AccountLifecycleHistoryResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account-lifecycle-history.properties",
	scope = ServiceScope.PROTOTYPE,
	service = AccountLifecycleHistoryResource.class
)
public class AccountLifecycleHistoryResourceImpl
	extends BaseAccountLifecycleHistoryResourceImpl {

	@Override
	public AccountLifecycleHistory
			getWorkspaceGroupAccountAccountLifecycleHistory(
				Long groupId, String accountId, String accountLifecycleId)
		throws Exception {

		AccountLifecycleStatus accountLifecycleStatus = null;

		try {
			accountLifecycleStatus =
				_contactsEngineClient.getAccountLifecycleStatus(
					_faroProjectLocalService.getFaroProjectByGroupId(groupId),
					accountLifecycleId, accountId);
		}
		catch (FaroEngineClientException faroEngineClientException) {
			if (faroEngineClientException.getStatus() ==
					HttpServletResponse.SC_BAD_REQUEST) {

				throw new NoSuchEntryException(faroEngineClientException);
			}

			throw faroEngineClientException;
		}

		return _accountLifecycleHistoryDTOConverter.toDTO(
			new FaroDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				accountLifecycleId, contextAcceptLanguage.getPreferredLocale()),
			accountLifecycleStatus);
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.AccountLifecycleHistoryDTOConverter)"
	)
	private DTOConverter<AccountLifecycleStatus, AccountLifecycleHistory>
		_accountLifecycleHistoryDTOConverter;

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}