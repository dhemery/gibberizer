package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.core.Ngram;
import com.dhemery.gibberizer.core.NgramBag;
import com.dhemery.gibberizer.core.NgramExtractor;

public class NgramExtractorTests {
	NgramExtractor extractor;
	private List<String> inputStrings;

	@Before
	public void setUp() {
		extractor = new NgramExtractor();
		inputStrings = new ArrayList<String>();
	}

	@Test
	public void extractsOneNgramIfStringIsShorterThanN() {
		String threeCharacterString = "abc";
		inputStrings.add(threeCharacterString);
		int n = threeCharacterString.length() + 1;
	
		NgramBag bag = extractor.extract(inputStrings, n);
	
		assertEquals(1, bag.getNgrams().size());
	}

	@Test
	public void extractedNgramMatchesStringIfStringShorterThanN() {
		String threeCharacterString = "abc";
		inputStrings.add(threeCharacterString);
		int n = threeCharacterString.length() + 1;
	
		NgramBag bag = extractor.extract(inputStrings, n);

		Ngram ngram = bag.getNgrams().get(0);

		assertEquals(threeCharacterString, ngram.toString());
	}

	@Test
	public void extractsOneNgramIfStringIsLengthN() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);
		int n = tenCharacterString.length();
	
		NgramBag bag = extractor.extract(inputStrings, n);
	
		assertEquals(1, bag.getNgrams().size());
	}

	@Test
	public void extractedNgramMatchesStringIfStringIsLengthN() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);
		int n = tenCharacterString.length();
	
		NgramBag bag = extractor.extract(inputStrings, n);

		Ngram ngram = bag.getNgrams().get(0);
		assertEquals(tenCharacterString, ngram.toString());
	}

	@Test
	public void extractsSeven4gramsFromSingleTenCharacterString() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);
	
		NgramBag bag = extractor.extract(inputStrings, 4);
	
		assertEquals(7, bag.getNgrams().size());
	}

	@Test
	public void extractsAnNgramForEachNCharacterSubstringOfSingleString() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);
	
		NgramBag bag = extractor.extract(inputStrings, 4);
	
		assertTrue(containsMatchingNgram(bag, "abcd"));
		assertTrue(containsMatchingNgram(bag, "bcde"));
		assertTrue(containsMatchingNgram(bag, "cdef"));
		assertTrue(containsMatchingNgram(bag, "defg"));
		assertTrue(containsMatchingNgram(bag, "efgh"));
		assertTrue(containsMatchingNgram(bag, "fghi"));
		assertTrue(containsMatchingNgram(bag, "ghij"));
	}

	@Test
	public void extractsNgramsFromEachStringInTheList() {
		inputStrings.add("abcde");
		inputStrings.add("fghij");
		inputStrings.add("klmno");
	
		NgramBag bag = extractor.extract(inputStrings, 4);
	
		assertTrue(containsMatchingNgram(bag, "abcd"));
		assertTrue(containsMatchingNgram(bag, "bcde"));
		assertTrue(containsMatchingNgram(bag, "fghi"));
		assertTrue(containsMatchingNgram(bag, "ghij"));
		assertTrue(containsMatchingNgram(bag, "klmn"));
		assertTrue(containsMatchingNgram(bag, "lmno"));
	}

	@Test
	public void extractsOneNgramForEachInstanceOfAnNCharacterSequence() {
		inputStrings.add("abcdef");
		inputStrings.add("bcdefg");
		inputStrings.add("cdefgh");
		inputStrings.add("zzzzzz");
	
		NgramBag bag = extractor.extract(inputStrings, 4);
	
		assertEquals(2, getMatchingNgramCount(bag, "bcde"));
		assertEquals(3, getMatchingNgramCount(bag, "cdef"));
		assertEquals(2, getMatchingNgramCount(bag, "defg"));
		assertEquals(3, getMatchingNgramCount(bag, "zzzz"));
	}

	@Test
	public void doesNotExtractNgramsAcrossStrings() {
		inputStrings.add("abcde");
		inputStrings.add("fghij");

		NgramBag bag = extractor.extract(inputStrings, 4);

		assertFalse(containsMatchingNgram(bag, "cdef"));
		assertFalse(containsMatchingNgram(bag, "defg"));
		assertFalse(containsMatchingNgram(bag, "efgh"));
	}

	@Test
	public void marksFirstNgramAsStarter() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);

		NgramBag bag = extractor.extract(inputStrings, 4);
		Ngram firstNgram = bag.getNgrams().get(0);

		assertTrue(bag.getStarters().contains(firstNgram));
	}

	@Test
	public void marksNonFirstNgramsAsNotStarters() {
		String tenLetterString = "abcdefghij";
		inputStrings.add(tenLetterString);

		NgramBag bag = extractor.extract(inputStrings, 4);

		List<Ngram> ngrams = bag.getNgrams();
		List<Ngram> starters = bag.getStarters();
		Ngram secondNgram = ngrams.get(1);
		Ngram thirdNgram = ngrams.get(2);
		Ngram lastNgram = ngrams.get(ngrams.size()-1);

		assertFalse(starters.contains(secondNgram));
		assertFalse(starters.contains(thirdNgram));
		assertFalse(starters.contains(lastNgram));
	}

	@Test
	public void marksLastNgramAsEnder() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);
	
		NgramBag bag = extractor.extract(inputStrings, 4);

		List<Ngram> ngrams = bag.getNgrams();
		Ngram lastNgram = ngrams.get(ngrams.size()-1);
		assertTrue(bag.isEnder(lastNgram));
	}

	@Test
	public void marksNonLastNgramsAsNotEnders() {
		String tenCharacterString = "abcdefghij";
		inputStrings.add(tenCharacterString);

		NgramBag bag = extractor.extract(inputStrings, 4);

		List<Ngram> ngrams = bag.getNgrams();
		Ngram firstNgram = ngrams.get(0);
		Ngram lastNgramButTwo = ngrams.get(ngrams.size()-3);
		Ngram lastNgramButOne = ngrams.get(ngrams.size()-2);

		assertFalse(bag.isEnder(lastNgramButOne));
		assertFalse(bag.isEnder(lastNgramButTwo));
		assertFalse(bag.isEnder(firstNgram));
	}

	private Ngram getMatchingNgram(List<Ngram> ngrams, String target) {
		for (Ngram ngram : ngrams)
			if (ngram.toString().equals(target)) return ngram;
		return null;
	}

	private Ngram getMatchingNgram(NgramBag bag, String target) {
		return getMatchingNgram(bag.getNgrams(), target);
	}

	private List<Ngram> getMatchingNgrams(NgramBag bag, String target) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		for(Ngram ngram : bag.getNgrams()) {
			if(ngram.toString().equals(target)) ngrams.add(ngram);
		}
		return ngrams;
	}

	private int getMatchingNgramCount(NgramBag ngrams, String target) {
		return getMatchingNgrams(ngrams, target).size();
	}

	private boolean containsMatchingNgram(NgramBag bag, String target) {
		return getMatchingNgram(bag, target) != null;
	}
}
