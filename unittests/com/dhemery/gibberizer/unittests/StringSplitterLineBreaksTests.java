package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.dhemery.gibberizer.StringSplitter;

public class StringSplitterLineBreaksTests {

	@Test
	public void carriageReturnIsLineBreak() {
		String carriageReturn = "\r";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + carriageReturn + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void newLineReturnIsLineBreak() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + newLine + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void noStringsIfRawStringIsAllLineBreaks() {
		String lineBreaks = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(lineBreaks,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(0, splitStrings.size());
	}

	@Test
	public void noStringsIfRawStringIsEmpty() {
		String emptyString = "";
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(emptyString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(0, splitStrings.size());
	}

	@Test
	public void preservesLeadingAndTrailingNonLineBreakWhiteSpace() {
		String newLine = "\n";
		String string1 = "  \t\t \t abc";
		String string2 = "def  \t\t \t ";
		String rawString = string1 + newLine + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void preservesLinesOfNonLineBreakWhiteSpace() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "  \t\t \t \t";
		String string3 = "def";
		String rawString = string1 + newLine + string2 + newLine + string3;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(3, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
		assertEquals(string3, splitStrings.get(2));
	}

	@Test
	public void preservesWhiteSpaceWithinLines() {
		String newLine = "\n";
		String string1 = "a\t\tb\t\t\tc";
		String string2 = "d e f";
		String rawString = string1 + newLine + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void splitsAtEachLineBreakSequence() {
		String lineBreaks = "\n\n\r\n\r\r";
		String string1 = "abc";
		String string2 = "def";
		String string3 = "ghi";
		String string4 = "jlk";
		String rawString = string1 + lineBreaks + string2 + lineBreaks
				+ string3 + lineBreaks + string4;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(4, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
		assertEquals(string3, splitStrings.get(2));
		assertEquals(string4, splitStrings.get(3));
	}

	@Test
	public void splitsAtEachSingleLineBreak() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "def";
		String string3 = "ghi";
		String string4 = "jlk";
		String rawString = string1 + newLine + string2 + newLine + string3
				+ newLine + string4;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(4, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
		assertEquals(string3, splitStrings.get(2));
		assertEquals(string4, splitStrings.get(3));
	}

	@Test
	public void splitsAtLineBreakSequence() {
		String lineBreaks = "\n\n\r\r\n\r\n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + lineBreaks + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void splitsAtSingleLineBreak() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + newLine + string2;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void trimsLeadingLineBreaks() {
		String newLines = "\n\r\n";
		String nonNewLine = "abcdefg";
		String rawString = newLines + nonNewLine;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(1, splitStrings.size());
		assertEquals(nonNewLine, splitStrings.get(0));
	}

	@Test
	public void trimsTrailingLineBreaks() {
		String newLines = "\r\n\n";
		String nonNewLine = "abcdefg";
		String rawString = nonNewLine + newLines;
		StringSplitter splitter = new StringSplitter();

		List<String> splitStrings = splitter.split(rawString,
				StringSplitter.SPLIT_AT_LINE_BREAKS);

		assertEquals(1, splitStrings.size());
		assertEquals(nonNewLine, splitStrings.get(0));
	}
}
