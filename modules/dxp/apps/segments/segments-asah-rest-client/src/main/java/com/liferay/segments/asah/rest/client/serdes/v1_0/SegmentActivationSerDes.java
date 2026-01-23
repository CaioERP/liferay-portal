/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.client.serdes.v1_0;

import com.liferay.segments.asah.rest.client.dto.v1_0.SegmentActivation;
import com.liferay.segments.asah.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SegmentActivationSerDes {

	public static SegmentActivation toDTO(String json) {
		SegmentActivationJSONParser segmentActivationJSONParser =
			new SegmentActivationJSONParser();

		return segmentActivationJSONParser.parseToDTO(json);
	}

	public static SegmentActivation[] toDTOs(String json) {
		SegmentActivationJSONParser segmentActivationJSONParser =
			new SegmentActivationJSONParser();

		return segmentActivationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SegmentActivation segmentActivation) {
		if (segmentActivation == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (segmentActivation.getSegment() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"Segment\": ");

			sb.append(String.valueOf(segmentActivation.getSegment()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SegmentActivationJSONParser segmentActivationJSONParser =
			new SegmentActivationJSONParser();

		return segmentActivationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SegmentActivation segmentActivation) {

		if (segmentActivation == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (segmentActivation.getSegment() == null) {
			map.put("Segment", null);
		}
		else {
			map.put("Segment", String.valueOf(segmentActivation.getSegment()));
		}

		return map;
	}

	public static class SegmentActivationJSONParser
		extends BaseJSONParser<SegmentActivation> {

		@Override
		protected SegmentActivation createDTO() {
			return new SegmentActivation();
		}

		@Override
		protected SegmentActivation[] createDTOArray(int size) {
			return new SegmentActivation[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "Segment")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			SegmentActivation segmentActivation, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "Segment")) {
				if (jsonParserFieldValue != null) {
					segmentActivation.setSegment(
						SegmentSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}