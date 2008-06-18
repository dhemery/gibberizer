package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.dhemery.gibberizer.core.SplitStyle;
import com.dhemery.gibberizer.core.StringSplitter;

public class StringSplitterWordsStyleTests {

	@Test
	public void carriageReturnIsWhiteSpace() {
		String carriageReturn = "\r";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + carriageReturn + string2;

		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void newLineIsWhiteSpace() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + newLine + string2;

		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void noStringsIfRawStringIsAllWhiteSpace() {
		String rawString = "  \n \r \t\t    ";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(0, splitStrings.size());
	}

	@Test
	public void noStringsIfRawStringIsEmpty() {
		String rawString = "";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(0, splitStrings.size());
	}

	@Test
	public void oneStringIfRawStringIsAllNonWhiteSpace() {
		String rawString = "abcdefg";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(1, splitStrings.size());
		assertEquals(rawString, splitStrings.get(0));
	}

	@Test
	public void splitsAtEachSingleWhiteSpace() {
		String white = " ";
		String string1 = "abc";
		String string2 = "def";
		String string3 = "ghi";
		String string4 = "jlk";
		String rawString = string1 + white + string2 + white + string3 + white
				+ string4;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(4, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
		assertEquals(string3, splitStrings.get(2));
		assertEquals(string4, splitStrings.get(3));
	}

	@Test
	public void splitsAtEachWhiteSpaceSequence() {
		String white = " \n\n   \r\n\r \r ";
		String string1 = "abc";
		String string2 = "def";
		String string3 = "ghi";
		String string4 = "jlk";
		String rawString = string1 + white + string2 + white + string3 + white
				+ string4;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(4, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
		assertEquals(string3, splitStrings.get(2));
		assertEquals(string4, splitStrings.get(3));
	}

	@Test
	public void splitsAtSingleWhiteSpace() {
		String white = " ";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + white + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void splitsAtWhiteSpaceSequence() {
		String white = "  \n  \r  \n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + white + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void tabIsWhiteSpace() {
		String newLine = "\t";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + newLine + string2;

		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void trimsLeadingWhiteSpace() {
		String white = "    ";
		String nonWhite = "abcdef";
		String rawString = white + nonWhite;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(1, splitStrings.size());
		assertEquals(nonWhite, splitStrings.get(0));
	}

	@Test
	public void trimsTrailingWhiteSpace() {
		String white = "    ";
		String nonWhite = "abcdef";
		String rawString = nonWhite + white;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString, SplitStyle.WORDS);

		assertEquals(1, splitStrings.size());
		assertEquals(nonWhite, splitStrings.get(0));
	}
}