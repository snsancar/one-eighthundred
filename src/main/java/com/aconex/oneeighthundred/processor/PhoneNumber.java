package com.aconex.oneeighthundred.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.aconex.oneeighthundred.constants.AppConstants;

/**
 * Data object. Represents a phone number in splitted parts. Parts can either be
 * strings or single digit Integers E.g. {"2255", "63"} or {"123", 4, "56"}
 * Note: {"1", "2"} != {"12"} != {1, "2"} != {"1", 2}
 */
class PhoneNumber {

	private final List<Object> phoneNumber;

	private PhoneNumber(List<Object> parts) {
		this.phoneNumber = new ArrayList<>();
		parts.forEach(this::addPart);
	}

	PhoneNumber(Object... parts) {
		this(Arrays.asList(parts));
	}

	PhoneNumber(String number) {
		this(Collections.singletonList(number));
	}

	private void addPart(Object part) {
		if (part instanceof PhoneNumber) {
			this.phoneNumber.addAll(((PhoneNumber) part).phoneNumber);
		} else if ((part instanceof String) || (part instanceof Integer)) {
			this.phoneNumber.add(part);
		} else {
			throw new IllegalArgumentException(
					"Unsupported phone number part type: " + part.getClass());
		}
	}

	List<Object> getParts() {
		return this.phoneNumber;
	}

	@Override
	public int hashCode() {
		return this.phoneNumber.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PhoneNumber)) {
			return false;
		}
		PhoneNumber pn = (PhoneNumber) obj;
		return this.phoneNumber.equals(pn.phoneNumber);
	}

	@Override
	public String toString() {
		return this.phoneNumber
				.stream()
				.map(o -> {
					if (o instanceof String) {
						return "'" + o + "'";
					} else if (o instanceof Integer) {
						return o.toString();
					} else {
						throw new UnsupportedOperationException(
								"This should never happen: " + o.getClass());
					}
				}).collect(Collectors.joining(AppConstants.SEPARATOR));
	}
}
