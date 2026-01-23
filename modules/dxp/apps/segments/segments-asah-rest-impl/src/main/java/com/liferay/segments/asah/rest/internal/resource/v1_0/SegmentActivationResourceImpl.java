/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.internal.resource.v1_0;

import com.liferay.segments.asah.connector.client.model.IndividualSegment;
import com.liferay.segments.asah.connector.helper.CheckIndividualSegmentsHelper;
import com.liferay.segments.asah.rest.dto.v1_0.Segment;
import com.liferay.segments.asah.rest.dto.v1_0.SegmentActivation;
import com.liferay.segments.asah.rest.resource.v1_0.SegmentActivationResource;
import com.liferay.segments.service.SegmentsEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/segment-activation.properties",
	scope = ServiceScope.PROTOTYPE, service = SegmentActivationResource.class
)
public class SegmentActivationResourceImpl
	extends BaseSegmentActivationResourceImpl {

	@Override
	public SegmentActivation postSegmentActivation(
			SegmentActivation segmentActivation)
		throws Exception {

		Segment segment = segmentActivation.getSegment();

		_checkIndividualSegmentsHelper.addSegmentsEntry(
			contextCompany.getCompanyId(), _toIndividualSegment(segment));
		_checkIndividualSegmentsHelper.checkIndividualSegmentMemberships(
			_segmentsEntryLocalService.getSegmentsEntry(segment.getId()));

		return segmentActivation;
	}

	private IndividualSegment _toIndividualSegment(Segment segment) {
		return new IndividualSegment() {
			{
				setId(String.valueOf(segment.getId()));
				setName(segment.getName());
			}
		};
	}

	@Reference
	private CheckIndividualSegmentsHelper _checkIndividualSegmentsHelper;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}