package com.aconex.oneeighthundred.processor;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ProcessorTest {

	private PhoneNumberProcessor processor;

	@Before
	public void setUp() throws Exception {
		this.processor = new PhoneNumberProcessor(new HashSet<>(Arrays.asList("CALL",
				"HELLO", "ME", "WORLD")));
	}

	@Test
	public void testChallenge() throws Exception {
		String input = "225563";
		Set<String> actual = processor.processNumber(input);
		assertTrue(actual.contains("CALL-ME"));
	}

	@Test
	public void testHelloWorld() throws Exception {
		String input = "4355696753";
		Set<String> actual = processor.processNumber(input);
		assertTrue(actual.contains("HELLO-WORLD"));
	}
}
