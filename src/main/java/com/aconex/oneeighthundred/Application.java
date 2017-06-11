package com.aconex.oneeighthundred;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aconex.oneeighthundred.input.InputReader;
import com.aconex.oneeighthundred.processor.PhoneNumberProcessor;

public class Application {

	private final static Logger LOGGER = Logger.getLogger(Application.class.getName());

	public static void main(String[] args) {

		String dictionaryFilePath = "";
		String phoneNumberFilePath = "";
		Set<String> dictionary = InputReader.getDictionary(dictionaryFilePath);
		Stream<String> input = InputReader.getInputPhoneNumbers(phoneNumberFilePath);
		PhoneNumberProcessor processor = new PhoneNumberProcessor(dictionary);
		Map<String, Set<String>> processedInput = processInput(input, processor);
		printOutput(processedInput);
	}

	private static Map<String, Set<String>> processInput(Stream<String> inputs,
			PhoneNumberProcessor processor) {
		return inputs.map(
				input -> new AbstractMap.SimpleEntry<>(input, processor
						.processNumber(input))).collect(
				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private static void printOutput(Map<String, Set<String>> processedInput) {
		processedInput
				.forEach((number, wordsNumbers) -> {
					if (wordsNumbers.isEmpty()) {
						return;
					}
					String output = "\n# "
							+ number
							+ ":\n  "
							+ wordsNumbers.stream().collect(
									Collectors.joining("\n  "));
					LOGGER.info(output);
				});
	}
}