package com.aconex.oneeighthundred.input;

import static com.aconex.oneeighthundred.constants.AppConstants.DIGIT;
import static com.aconex.oneeighthundred.constants.AppConstants.PUNCTUATION;
import static com.aconex.oneeighthundred.constants.AppConstants.WHITESPACE;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Class used to Sanitise the inputs [ Phone numbers and Dictionary]
 */
public class InputSanitiser {

	private final static Logger LOGGER = Logger.getLogger(InputSanitiser.class
			.getName());

	public static Optional<String> sanitiseGeneric(String original) {
		String sanitised = original.replaceAll(PUNCTUATION, "").replaceAll(
				WHITESPACE, "");
		if (!sanitised.matches(DIGIT)) {
			LOGGER.warning("Could not sanitise input: " + original
					+ "; best shot: " + sanitised);
			return Optional.empty();
		}
		return Optional.of(sanitised);
	}
}
