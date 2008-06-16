package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.dhemery.gibberizer.StringSplitter;

public class StringSplitterNoSplitTests {

	@Test
	public void listIncludesOnlyRawStringIfRawStringIsNotEmpty() {
		String rawString = "a";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.DO_NOT_SPLIT);

		assertEquals(1, splitStrings.size());
		assertEquals(rawString, splitStrings.get(0));
	}

	@Test
	public void noStringsIfRawStringIsEmpty() {
		String rawString = "";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.DO_NOT_SPLIT);

		assertEquals(0, splitStrings.size());
	}

	@Test
	public void preservesRawStringOfWhiteSpace() {
		String rawString = "\t  \r\r\n\n\t \t \n";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.DO_NOT_SPLIT);

		assertEquals(1, splitStrings.size());
		assertEquals(rawString, splitStrings.get(0));
	}
}
