/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.VisitorMetric;
import com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelVisitorMetricResponse;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Caio Pinheiro
 */
@Component(
	property = "dto.class.name=com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelVisitorMetricResponse$VisitorMetric",
	service = DTOConverter.class
)
public class VisitorMetricDTOConverter
	implements DTOConverter
		<GetWorkspaceGroupChannelVisitorMetricResponse.VisitorMetric,
		 VisitorMetric> {

	@Override
	public String getContentType() {
		return VisitorMetric.class.getSimpleName();
	}

	@Override
	public VisitorMetric toDTO(
		DTOConverterContext dtoConverterContext,
		GetWorkspaceGroupChannelVisitorMetricResponse.VisitorMetric
			visitorMetric) {

		if (visitorMetric == null) {
			return null;
		}

		return new VisitorMetric() {
			{
				setAverageSessionDuration(
					visitorMetric::getAverageSessionDuration);
				setKnownVisitors(visitorMetric::getKnownVisitors);
				setTotalSessionDuration(
					() -> _toLong(visitorMetric.getTotalSessionDuration()));
				setVisitors(visitorMetric::getVisitors);
			}
		};
	}

	private Long _toLong(Double value) {

		// Asah sends totalSessionDuration as a GraphQL Float because the sum
		// of session durations in milliseconds can exceed the Int range

		if (value == null) {
			return null;
		}

		return value.longValue();
	}

}