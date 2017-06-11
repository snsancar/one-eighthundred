package com.aconex.oneeighthundred.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;


public class InputSanitiserTest {

	private InputSanitiser sanitiser;

    @Before
    public void setUp() throws Exception {
        this.sanitiser = new InputSanitiser();
    }
    
    @Test
    public void removesDots() throws Exception {
        String input = "2255.63";
        String expected = "225563";
        Optional<String> actual = sanitiser.sanitiseGeneric(input);
        assertTrue("Input could not be converted", actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void removesDashes() throws Exception {
        String input = "2255-63";
        String expected = "225563";
        Optional<String> actual = sanitiser.sanitiseGeneric(input);
        assertTrue("Input could not be converted", actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void removesWhitespace() throws Exception {
        String input = "2255 63";
        String expected = "225563";
        Optional<String> actual = sanitiser.sanitiseGeneric(input);
        assertTrue("Input could not be converted", actual.isPresent());
        assertEquals(expected, actual.get());
    }
}
