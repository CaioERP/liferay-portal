/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.client.dto.v1_0;

import com.liferay.segments.asah.rest.client.function.UnsafeSupplier;
import com.liferay.segments.asah.rest.client.serdes.v1_0.SegmentActivationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SegmentActivation implements Cloneable, Serializable {

	public static SegmentActivation toDTO(String json) {
		return SegmentActivationSerDes.toDTO(json);
	}

	public Segment getSegment() {
		return Segment;
	}

	public void setSegment(Segment Segment) {
		this.Segment = Segment;
	}

	public void setSegment(
		UnsafeSupplier<Segment, Exception> SegmentUnsafeSupplier) {

		try {
			Segment = SegmentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Segment Segment;

	@Override
	public SegmentActivation clone() throws CloneNotSupportedException {
		return (SegmentActivation)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SegmentActivation)) {
			return false;
		}

		SegmentActivation segmentActivation = (SegmentActivation)object;

		return Objects.equals(toString(), segmentActivation.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SegmentActivationSerDes.toJSON(this);
	}

}