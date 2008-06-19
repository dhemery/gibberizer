package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.dhemery.gibberizer.core.SplitStyle;
import com.dhemery.gibberizer.core.StringSplitter;

public class StringSplitterOneStringStyleTests {

	@Test
	public void listIncludesOnlyRawStringIfRawStringIsNotEmpty() {
		String rawString = "a";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.ONE_STRING);

		assertEquals(1, splitStrings.size());
		assertEquals(rawString, splitStrings.get(0));
	}

	@Test
	public void noStringsIfRawStringIsEmpty() {
		String rawString = "";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.ONE_STRING);

		assertEquals(0, splitStrings.size());
	}

	@Test
	public void preservesRawStringOfWhiteSpace() {
		String rawString = "\t  \r\r \n\n\t \t \n";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.ONE_STRING);

		assertEquals(1, splitStrings.size());
		assertEquals(rawString, splitStrings.get(0));
	}
}
