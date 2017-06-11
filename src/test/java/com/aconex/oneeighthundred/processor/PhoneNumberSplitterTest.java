package com.aconex.oneeighthundred.processor;

import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class PhoneNumberSplitterTest {
	private PhoneNumberSplitter splitter;

	@Before
	public void setUp() throws Exception {
		this.splitter = new PhoneNumberSplitter();
	}

	@Test
	public void containsChallengeInput() throws Exception {
		String input = "225563";
		Set<PhoneNumber> actual = splitter.processNumber(input).collect(
				Collectors.toSet());
		assertTrue(actual.contains(new PhoneNumber("2255", "63")));
	}
}