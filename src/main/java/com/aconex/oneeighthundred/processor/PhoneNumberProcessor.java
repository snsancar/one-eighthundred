package com.aconex.oneeighthundred.processor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aconex.oneeighthundred.constants.AppConstants;

public class PhoneNumberProcessor {
	private final static Logger LOGGER = Logger.getLogger(PhoneNumberProcessor.class
			.getName());

	private final Set<String> dictionary;
	private final PotentialWordGenerator potentialWordGenerator;
	private final PhoneNumberSplitter phoneNumberSplitter;

	public PhoneNumberProcessor(Set<String> dictionary) {
		this.dictionary = dictionary;
		this.potentialWordGenerator = new PotentialWordGenerator();
		this.phoneNumberSplitter = new PhoneNumberSplitter();
	}

	/**
	 * Processes a sanitised phone number to produce a set of word-numbers.
	 * 
	 * @param number
	 *            A sanitised phone number.
	 * @return A set of possible words, that can be made from the supplied
	 *         number.
	 */
	public Set<String> processNumber(String number) {
		return phoneNumberSplitter.processNumber(number)
				.flatMap(this::getWordNumbers).collect(Collectors.toSet());

	}

	private Stream<String> getWordNumbers(PhoneNumber phoneNumber) {

		Set<String> potentialWords = new HashSet<>(
				Collections.singletonList(""));
		for (Object numberPart : phoneNumber.getParts()) {
			Set<String> potentialWordsNew = new HashSet<>();
			Set<String> wordParts = getWordsForPart(numberPart);
			for (String potentialWord : potentialWords) {
				for (String wordPart : wordParts) {
					potentialWordsNew
							.add(potentialWord
									+ (potentialWord.length() > 0 ? AppConstants.SEPARATOR
											: "") + wordPart);
				}
			}
			potentialWords = potentialWordsNew;
		}

		return potentialWords.stream();
	}

	private Set<String> getWordsForPart(Object numberPart) {
		Set<String> result;
		if (numberPart instanceof String) {
			result = potentialWordGenerator.processNumber((String) numberPart)
					.filter(dictionary::contains).collect(Collectors.toSet());
		} else if (numberPart instanceof Integer) {
			result = new HashSet<>(Collections.singletonList(numberPart
					.toString()));
			return result;
		} else {
			LOGGER.warning("Invalid type in phone number parts of processor: "
					+ numberPart.getClass());
			result = Collections.emptySet();
		}
		return result;
	}
}