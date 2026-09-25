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
	id = "com.liferay.osb.faro.web.internal.configuration.SegmentNotificationGroupServiceConfiguration",
	localization = "content/Language",
	name = "segment-notification-configuration-name"
)
public interface SegmentNotificationGroupServiceConfiguration {

	@Meta.AD(name = "email-segment-notification-body", required = false)
	public LocalizedValuesMap emailBody();

	@Meta.AD(name = "email-segment-notification-subject", required = false)
	public LocalizedValuesMap emailSubject();

}