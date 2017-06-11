package com.aconex.oneeighthundred.input;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility Class used to read the inputs [ Phone numbers and Dictionary Files]
 */
public class InputReader {

	public static Set<String> getDictionary(String filePath) {
		Stream<String> fileContent = FileInputReader.getFileContent(filePath);
		return fileContent.map(InputSanitiser::sanitiseGeneric)
				.filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toSet());
	}

	public static Stream<String> getInputPhoneNumbers(String filePath) {
		Stream<String> fileContent = FileInputReader.getFileContent(filePath);
		return fileContent.map(InputSanitiser::sanitiseGeneric)
				.filter(Optional::isPresent).map(Optional::get);
	}
}
