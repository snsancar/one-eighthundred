package com.aconex.oneeighthundred.processor;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class PotentialWordGeneratorTest {

	private PotentialWordGenerator generator;

	@Before
	public void setUp() throws Exception {
		this.generator = new PotentialWordGenerator();
	}

	@Test
	public void testTwoDigits() throws Exception {
		String input = "22";
		Set<String> expected = new HashSet<>();
		expected.add("AA");
		expected.add("AB");
		expected.add("AC");
		expected.add("BA");
		expected.add("BB");
		expected.add("BC");
		expected.add("CA");
		expected.add("CB");
		expected.add("CC");
		Set<String> actual = generator.processNumber(input).collect(
				Collectors.toSet());
		assertEquals(expected, actual);
	}
}
