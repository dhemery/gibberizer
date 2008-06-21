package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.dhemery.gibberizer.core.SplitStyle;
import com.dhemery.gibberizer.core.StringSplitter;

public class StringSplitterLinesStyleTests {

	@Test
	public void splitsAtSingleLineBreak() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + newLine + string2;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
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
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
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
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
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
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(4, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
		assertEquals(string3, splitStrings.get(2));
		assertEquals(string4, splitStrings.get(3));
	}

	@Test
	public void newLineIsLineBreak() {
		String newLine = "\n";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + newLine + string2;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void carriageReturnIsLineBreak() {
		String carriageReturn = "\r";
		String string1 = "abc";
		String string2 = "def";
		String rawString = string1 + carriageReturn + string2;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(2, splitStrings.size());
		assertEquals(string1, splitStrings.get(0));
		assertEquals(string2, splitStrings.get(1));
	}

	@Test
	public void noStringsIfRawStringIsEmpty() {
		String emptyString = "";
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(emptyString, SplitStyle.LINES);
	
		assertEquals(0, splitStrings.size());
	}

	@Test
	public void noStringsIfRawStringIsAllLineBreaks() {
		String lineBreaks = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(lineBreaks, SplitStyle.LINES);
	
		assertEquals(0, splitStrings.size());
	}

	@Test
	public void trimsLeadingLineBreaks() {
		String newLines = "\n\r\n";
		String nonNewLine = "abcdefg";
		String rawString = newLines + nonNewLine;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(1, splitStrings.size());
		assertEquals(nonNewLine, splitStrings.get(0));
	}

	@Test
	public void trimsTrailingLineBreaks() {
		String newLines = "\r\n\n";
		String nonNewLine = "abcdefg";
		String rawString = nonNewLine + newLines;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(1, splitStrings.size());
		assertEquals(nonNewLine, splitStrings.get(0));
	}

	@Test
	public void preservesLeadingAndTrailingNonLineBreakWhiteSpace() {
		String newLine = "\n";
		String headWithLeadingWhiteSpace = "  \t\t \t abc";
		String tailWithTrailingWhiteSpace = "def  \t\t \t ";
		String rawString = headWithLeadingWhiteSpace + newLine + tailWithTrailingWhiteSpace;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(2, splitStrings.size());
		assertEquals(headWithLeadingWhiteSpace, splitStrings.get(0));
		assertEquals(tailWithTrailingWhiteSpace, splitStrings.get(1));
	}

	@Test
	public void preservesLinesOfNonLineBreakWhiteSpace() {
		String newLine = "\n";
		String firstLineOfNonWhiteSpace = "abc";
		String lineOfEmbeddedWhiteSpace = "  \t\t \t \t";
		String lastLineOfNonWhiteSpace = "def";
		String rawString = firstLineOfNonWhiteSpace + newLine + lineOfEmbeddedWhiteSpace + newLine + lastLineOfNonWhiteSpace;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(3, splitStrings.size());
		assertEquals(firstLineOfNonWhiteSpace, splitStrings.get(0));
		assertEquals(lineOfEmbeddedWhiteSpace, splitStrings.get(1));
		assertEquals(lastLineOfNonWhiteSpace, splitStrings.get(2));
	}

	@Test
	public void preservesWhiteSpaceWithinLines() {
		String newLine = "\n";
		String headWithEmbeddedWhiteSpace = "a\t\tb\t\t\tc";
		String tailWithEmbeddedWhiteSpace = "d e f";
		String rawString = headWithEmbeddedWhiteSpace + newLine + tailWithEmbeddedWhiteSpace;
		StringSplitter splitter = new StringSplitter();
	
		List<String> splitStrings = splitter.split(rawString, SplitStyle.LINES);
	
		assertEquals(2, splitStrings.size());
		assertEquals(headWithEmbeddedWhiteSpace, splitStrings.get(0));
		assertEquals(tailWithEmbeddedWhiteSpace, splitStrings.get(1));
	}
}
