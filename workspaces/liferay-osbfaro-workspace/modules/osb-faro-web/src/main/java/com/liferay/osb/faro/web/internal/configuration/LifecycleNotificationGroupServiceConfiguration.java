/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Claude
 */
@ExtendedObjectClassDefinition(
	category = "notifications",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.osb.faro.web.internal.configuration.LifecycleNotificationGroupServiceConfiguration",
	localization = "content/Language",
	name = "lifecycle-notification-configuration-name"
)
public interface LifecycleNotificationGroupServiceConfiguration {

	@Meta.AD(name = "email-lifecycle-notification-body", required = false)
	public LocalizedValuesMap emailBody();

	@Meta.AD(name = "email-lifecycle-notification-subject", required = false)
	public LocalizedValuesMap emailSubject();

}