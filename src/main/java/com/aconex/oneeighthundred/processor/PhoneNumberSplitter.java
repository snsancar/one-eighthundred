package com.aconex.oneeighthundred.processor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

class PhoneNumberSplitter {

	/**
	 * Splits a phone number into parts adhering to the rules defined in the
	 * challenge
	 *
	 * @param number
	 *            The number as a single, sanitised string of digits.
	 * @return A set of all possible splittings as defined by the rules of the
	 *         challenge.
	 */
	public Stream<PhoneNumber> processNumber(String number) {
		return splitPhoneNumberFirstRun(number).stream();
	}

	/*
	 * The inner splitting logic in splitPhoneNumber only considers digits in
	 * the middle. This first run considers, that the digits also can be the
	 * first or the last part.
	 */
	private Set<PhoneNumber> splitPhoneNumberFirstRun(String number) {
		Set<PhoneNumber> result = new HashSet<>();

		Integer firstDigit = Integer.parseInt(number.substring(0, 1));
		Integer lastDigit = Integer.parseInt(number.substring(
				number.length() - 1, number.length()));
		String withFirstDigit = number.substring(1);
		String withLastDigit = number.substring(0, number.length() - 1);
		String withFirstAndLastDigit = number.substring(1, number.length() - 1);

		result.add(new PhoneNumber(firstDigit, withFirstDigit));
		result.add(new PhoneNumber(withLastDigit, lastDigit));
		result.add(new PhoneNumber(firstDigit, withFirstAndLastDigit, lastDigit));
		result.addAll(splitPhoneNumber(number));

		return result;
	}

	/*
	 * A valid split inside the number goes as follows: number => wholeNumber |
	 * firstHalf - secondHalf | firstHalf - middleDigit - secondHalf Recursively
	 * calls itself with the split parts.
	 */
	private Set<PhoneNumber> splitPhoneNumber(String number) {
		Set<PhoneNumber> result = new HashSet<>();

		if (number.length() == 0) {
			return result;
		}

		result.add(new PhoneNumber(number));

		if (number.length() == 1) {
			return result;
		}

		for (int split = 1; split < number.length(); split++) {
			String leftString = number.substring(0, split);
			String rightString = number.substring(split, number.length());
			Set<PhoneNumber> leftParts = splitPhoneNumber(leftString);
			Set<PhoneNumber> rightParts = splitPhoneNumber(rightString);
			result.addAll(merge(leftParts, rightParts, null));

			String leftStringDigit = number.substring(0, split);
			Integer digit = Integer
					.parseInt(number.substring(split, split + 1));
			String rightStringDigit = number.substring(split + 1,
					number.length());
			Set<PhoneNumber> leftPartsDigit = splitPhoneNumber(leftStringDigit);
			Set<PhoneNumber> rightPartsDigit = splitPhoneNumber(rightStringDigit);
			result.addAll(merge(leftPartsDigit, rightPartsDigit, digit));
		}

		return result;
	}

	private Set<PhoneNumber> merge(Set<PhoneNumber> leftParts,
			Set<PhoneNumber> rightParts, Integer digit) {
		Set<PhoneNumber> result = new HashSet<>();
		for (PhoneNumber leftPart : leftParts) {
			for (PhoneNumber rightPart : rightParts) {
				if (digit == null) {
					result.add(new PhoneNumber(leftPart, rightPart));
				} else {
					result.add(new PhoneNumber(leftPart, digit, rightPart));
				}
			}
		}
		return result;
	}

}
