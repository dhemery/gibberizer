package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.StringParser;
import com.dhemery.gibberizer.Ngram;

public class StringParserTests {
	StringParser parser;
	private List<String> strings;

	@Before
	public void setUp() {
		parser = new StringParser();
		strings = new ArrayList<String>();
	}

	@Test
	public void createsOneNgramForSingleStringShorterThanN() {
		String threeLetterString = "abc";
		strings.add(threeLetterString);
		int n = threeLetterString.length() + 1;

		List<Ngram> ngrams = parser.parse(strings, n);

		assertEquals(1, ngrams.size());
	}

	@Test
	public void createsNgramThatMatchesStringIfStringShorterThanN() {
		String threeLetterString = "abc";
		strings.add(threeLetterString);
		int n = threeLetterString.length() + 1;

		Ngram ngram = parser.parse(strings, n).get(0);

		assertEquals(threeLetterString, ngram.toString());
	}

	@Test
	public void createsOneNgramForSingleNLetterString() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);
		int n = tenLetterString.length();

		List<Ngram> ngrams = parser.parse(strings, n);

		assertEquals(1, ngrams.size());
	}

	@Test
	public void createsNgramThatMatchesNLetterString() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);
		int n = tenLetterString.length();

		Ngram ngram = parser.parse(strings, n).get(0);

		assertEquals(tenLetterString, ngram.toString());
	}

	@Test
	public void createsSeven4gramsForSingleTenLetterString() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertEquals(7, ngrams.size());
	}

	@Test
	public void createsNgramForEachNLetterSubstringOfSingleString() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertEquals(1, getInstanceCount("abcd", ngrams));
		assertEquals(1, getInstanceCount("bcde", ngrams));
		assertEquals(1, getInstanceCount("cdef", ngrams));
		assertEquals(1, getInstanceCount("defg", ngrams));
		assertEquals(1, getInstanceCount("efgh", ngrams));
		assertEquals(1, getInstanceCount("fghi", ngrams));
		assertEquals(1, getInstanceCount("ghij", ngrams));
	}

	@Test
	public void marksFirstNgramAsStringStarter() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertTrue(getNgram("abcd", ngrams).isStarter());
	}

	@Test
	public void marksNonFirstNgramsAsNotStringStarters() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertFalse(getNgram("bcde", ngrams).isStarter());
		assertFalse(getNgram("cdef", ngrams).isStarter());
		assertFalse(getNgram("ghij", ngrams).isStarter());
	}

	@Test
	public void marksLastNgramAsStringEnder() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertTrue(getNgram("ghij", ngrams).isEnder());
	}

	@Test
	public void marksNonLastNgramsAsNotStringEnders() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertFalse(getNgram("abcd", ngrams).isEnder());
		assertFalse(getNgram("efgh", ngrams).isEnder());
		assertFalse(getNgram("fghi", ngrams).isEnder());
	}

	@Test
	public void createsNgramsForEachString() {
		strings.add("abcde");
		strings.add("fghij");
		strings.add("klmno");

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertEquals(1, getInstanceCount("abcd", ngrams));
		assertEquals(1, getInstanceCount("bcde", ngrams));
		assertEquals(1, getInstanceCount("fghi", ngrams));
		assertEquals(1, getInstanceCount("ghij", ngrams));
		assertEquals(1, getInstanceCount("klmn", ngrams));
		assertEquals(1, getInstanceCount("lmno", ngrams));
	}

	@Test
	public void createdNgramsDoNotSpanWords() {
		strings.add("abcde");
		strings.add("fghij");

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertEquals(0, getInstanceCount("cdef", ngrams));
		assertEquals(0, getInstanceCount("defg", ngrams));
		assertEquals(0, getInstanceCount("efgh", ngrams));
	}
	
	@Test
	public void generatesOneNgramForEachInstanceOfAnNLetterSequence() {
		strings.add("abcdef");
		strings.add("bcdefg");
		strings.add("cdefgh");
		strings.add("zzzzzz");

		List<Ngram> ngrams = parser.parse(strings, 4);

		assertEquals(2, getInstanceCount("bcde", ngrams));
		assertEquals(3, getInstanceCount("cdef", ngrams));
		assertEquals(2, getInstanceCount("defg", ngrams));
		assertEquals(3, getInstanceCount("zzzz", ngrams));
	}

	private int getInstanceCount(String target, List<Ngram> ngrams) {
		int instanceCount = 0;
		for(Ngram ngram : ngrams) {
			if(ngram.toString().equals(target)) instanceCount++;
		}
		return instanceCount;
	}

	private Ngram getNgram(String target, List<Ngram> ngrams) {
		for(Ngram ngram : ngrams) {
			if(ngram.toString().equals(target)) return ngram;
		}
		return null;
	}
}
