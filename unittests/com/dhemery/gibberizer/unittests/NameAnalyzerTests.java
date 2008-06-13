package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.NameAnalyzer;
import com.dhemery.gibberizer.Ngram;

public class NameAnalyzerTests {
	private List<String> names;

	@Before
	public void setUp() {
		names = new ArrayList<String>();
	}

	@Test
	public void createsOneNgramForSingleNLetterName() {
		String tenLetterName = "abcdefghij";
		names.add(tenLetterName);
		int n = tenLetterName.length();
		NameAnalyzer analyzer = new NameAnalyzer(n);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertEquals(1, ngrams.size());
	}

	@Test
	public void createsNgramThatMatchesNLetterName() {
		String tenLetterName = "abcdefghij";
		names.add(tenLetterName);
		int n = tenLetterName.length();
		NameAnalyzer analyzer = new NameAnalyzer(n);

		Ngram ngram = analyzer.getNgrams(names).get(0);

		assertEquals(tenLetterName, ngram.toString());
	}

	@Test
	public void createsSeven4gramsForSingleTenLetterName() {
		String tenLetterName = "abcdefghij";
		names.add(tenLetterName);
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertEquals(7, ngrams.size());
	}

	@Test
	public void createsNgramForEachNLetterSubstringOfSingleName() {
		String tenLetterName = "abcdefghij";
		names.add(tenLetterName);
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertEquals(1, getInstanceCount("abcd", ngrams));
		assertEquals(1, getInstanceCount("bcde", ngrams));
		assertEquals(1, getInstanceCount("cdef", ngrams));
		assertEquals(1, getInstanceCount("defg", ngrams));
		assertEquals(1, getInstanceCount("efgh", ngrams));
		assertEquals(1, getInstanceCount("fghi", ngrams));
		assertEquals(1, getInstanceCount("ghij", ngrams));
	}

	@Test
	public void marksFirstNgramAsNameStarter() {
		String tenLetterName = "abcdefghij";
		names.add(tenLetterName);
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertTrue(getNgram("abcd", ngrams).isNameStarter());
	}

	@Test
	public void marksNonFirstNgramsAsNotNameStarters() {
		String tenLetterName = "abcdefghij";
		names.add(tenLetterName);
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertFalse(getNgram("bcde", ngrams).isNameStarter());
		assertFalse(getNgram("cdef", ngrams).isNameStarter());
		assertFalse(getNgram("ghij", ngrams).isNameStarter());
	}

	@Test
	public void createsNgramsForEachName() {
		names.add("abcde");
		names.add("fghij");
		names.add("klmno");
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertEquals(1, getInstanceCount("abcd", ngrams));
		assertEquals(1, getInstanceCount("bcde", ngrams));
		assertEquals(1, getInstanceCount("fghi", ngrams));
		assertEquals(1, getInstanceCount("ghij", ngrams));
		assertEquals(1, getInstanceCount("klmn", ngrams));
		assertEquals(1, getInstanceCount("lmno", ngrams));
	}

	@Test
	public void createdNgramsDoNotSpanWords() {
		names.add("abcde");
		names.add("fghij");
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

		assertEquals(0, getInstanceCount("cdef", ngrams));
		assertEquals(0, getInstanceCount("defg", ngrams));
		assertEquals(0, getInstanceCount("efgh", ngrams));
	}
	
	@Test
	public void generatesOneNgramForEachInstanceOfAnNLetterSequence() {
		names.add("abcdef");
		names.add("bcdefg");
		names.add("cdefgh");
		names.add("zzzzzz");
		NameAnalyzer analyzer = new NameAnalyzer(4);

		List<Ngram> ngrams = analyzer.getNgrams(names);

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
