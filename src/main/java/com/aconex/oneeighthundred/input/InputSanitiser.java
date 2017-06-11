package com.aconex.oneeighthundred.input;

import java.util.Optional;
import java.util.logging.Logger;


public class InputSanitiser {

	private final static Logger LOGGER = Logger.getLogger(InputSanitiser.class.getName());

    private static final String PUNCTUATION = "\\p{Punct}";
    private static final String WHITESPACE = "\\s";
    private static final String DIGIT = "\\d+";

    Optional<String>  sanitiseGeneric(String original) {
        String sanitised = original.replaceAll(PUNCTUATION, "").replaceAll(WHITESPACE, "");
        if (!sanitised.matches(DIGIT)) {
            LOGGER.warning("Could not sanitise input: " + original + "; best shot: " + sanitised);
            return Optional.empty();
        }
        return Optional.of(sanitised);
    }
}
