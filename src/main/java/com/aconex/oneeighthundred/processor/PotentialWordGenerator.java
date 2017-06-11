package com.aconex.oneeighthundred.processor;

import static com.aconex.oneeighthundred.constants.AppConstants.REPLACEABLE_NUMBERS;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

class PotentialWordGenerator {
	private final static Logger LOGGER = Logger
			.getLogger(PotentialWordGenerator.class.getName());
	private static final Map<Character, Set<Character>> KEYPAD = new HashMap<>();

	static {
		KEYPAD.put('2', new HashSet<>(Arrays.asList('A', 'B', 'C')));
		KEYPAD.put('3', new HashSet<>(Arrays.asList('D', 'E', 'F')));
		KEYPAD.put('4', new HashSet<>(Arrays.asList('G', 'H', 'I')));
		KEYPAD.put('5', new HashSet<>(Arrays.asList('J', 'K', 'L')));
		KEYPAD.put('6', new HashSet<>(Arrays.asList('M', 'N', 'O')));
		KEYPAD.put('7', new HashSet<>(Arrays.asList('P', 'Q', 'R', 'S')));
		KEYPAD.put('8', new HashSet<>(Arrays.asList('T', 'U', 'V')));
		KEYPAD.put('9', new HashSet<>(Arrays.asList('W', 'X', 'Y', 'Z')));
	}

	/**
	 * Delivers a set of letter combinations (aka "potential words"), that can
	 * be composed of the numbers using a keypad. These potential words still
	 * have to be validated against a dictionary! Note: If the input contains
	 * invalid characters, meaning characters other than 2-9 which represent the
	 * keypad, the result is an empty set.
	 *
	 * @param number
	 *            A string of digits to use on the virtual keypad
	 * @return A set of potential words or an empty set if input invalid.
	 */
	public Stream<String> processNumber(String number) {
		if (!number.matches(REPLACEABLE_NUMBERS)) {
			LOGGER.warning("Invalid input for keypad: " + number);
			return Stream.empty();
		}

		return replaceNumbers(number).stream();
	}

	public Set<String> replaceNumbers(String number) {
		Set<String> potentialWords = new HashSet<>(
				Collections.singletonList(""));
		for (char digit : number.toCharArray()) {
			Set<String> newPotentialWords = new HashSet<>();
			for (String potentialWord : potentialWords) {
				for (char replaceable : KEYPAD.get(digit)) {
					newPotentialWords.add(potentialWord + replaceable);
				}
			}
			potentialWords = newPotentialWords;
		}

		return potentialWords;
	}
}
