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
	private List<String> strings;

	@Before
	public void setUp() {
		extractor = new NgramExtractor();
		strings = new ArrayList<String>();
	}

	@Test
	public void extractsOneNgramIfStringIsShorterThanN() {
		String threeCharacterString = "abc";
		strings.add(threeCharacterString);
		int n = threeCharacterString.length() + 1;
	
		NgramBag ngrams = extractor.extract(strings, n);
	
		assertEquals(1, ngrams.getNgrams().size());
	}

	@Test
	public void extractedNgramMatchesStringIfStringShorterThanN() {
		String threeCharacterString = "abc";
		strings.add(threeCharacterString);
		int n = threeCharacterString.length() + 1;
	
		Ngram ngram = extractor.extract(strings, n).getNgrams().get(0);
	
		assertEquals(threeCharacterString, ngram.toString());
	}

	@Test
	public void extractsOneNgramIfStringIsLengthN() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);
		int n = tenCharacterString.length();
	
		NgramBag ngrams = extractor.extract(strings, n);
	
		assertEquals(1, ngrams.getNgrams().size());
	}

	@Test
	public void extractedNgramMatchesStringIfStringIsLengthN() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);
		int n = tenCharacterString.length();
	
		Ngram ngram = extractor.extract(strings, n).getNgrams().get(0);
	
		assertEquals(tenCharacterString, ngram.toString());
	}

	@Test
	public void extractsAnNgramForEachNCharacterSubstringOfSingleString() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);
	
		NgramBag ngrams = extractor.extract(strings, 4);
	
		assertEquals(1, getInstanceCount("abcd", ngrams));
		assertEquals(1, getInstanceCount("bcde", ngrams));
		assertEquals(1, getInstanceCount("cdef", ngrams));
		assertEquals(1, getInstanceCount("defg", ngrams));
		assertEquals(1, getInstanceCount("efgh", ngrams));
		assertEquals(1, getInstanceCount("fghi", ngrams));
		assertEquals(1, getInstanceCount("ghij", ngrams));
	}

	@Test
	public void extractsSeven4gramsFromSingleTenCharacterString() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);
	
		NgramBag ngrams = extractor.extract(strings, 4);
	
		assertEquals(7, ngrams.getNgrams().size());
	}

	@Test
	public void extractsNgramsFromEachStringInTheList() {
		strings.add("abcde");
		strings.add("fghij");
		strings.add("klmno");
	
		NgramBag ngrams = extractor.extract(strings, 4);
	
		assertEquals(1, getInstanceCount("abcd", ngrams));
		assertEquals(1, getInstanceCount("bcde", ngrams));
		assertEquals(1, getInstanceCount("fghi", ngrams));
		assertEquals(1, getInstanceCount("ghij", ngrams));
		assertEquals(1, getInstanceCount("klmn", ngrams));
		assertEquals(1, getInstanceCount("lmno", ngrams));
	}

	@Test
	public void extractsOneNgramForEachInstanceOfAnNCharacterSequence() {
		strings.add("abcdef");
		strings.add("bcdefg");
		strings.add("cdefgh");
		strings.add("zzzzzz");
	
		NgramBag ngrams = extractor.extract(strings, 4);
	
		assertEquals(2, getInstanceCount("bcde", ngrams));
		assertEquals(3, getInstanceCount("cdef", ngrams));
		assertEquals(2, getInstanceCount("defg", ngrams));
		assertEquals(3, getInstanceCount("zzzz", ngrams));
	}

	@Test
	public void doesNotExtractNgramsAcrossStrings() {
		strings.add("abcde");
		strings.add("fghij");

		NgramBag ngrams = extractor.extract(strings, 4);

		assertEquals(0, getInstanceCount("cdef", ngrams));
		assertEquals(0, getInstanceCount("defg", ngrams));
		assertEquals(0, getInstanceCount("efgh", ngrams));
	}

	private int getInstanceCount(String target, NgramBag ngramBag) {
		int instanceCount = 0;
		for (Ngram ngram : ngramBag.getNgrams())
			if (ngram.toString().equals(target)) instanceCount++;
		return instanceCount;
	}

	private Ngram getNgram(String target, NgramBag ngrams) {
		for (Ngram ngram : ngrams.getNgrams())
			if (ngram.toString().equals(target)) return ngram;
		return null;
	}

	@Test
	public void marksFirstNgramAsStarter() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);

		NgramBag ngrams = extractor.extract(strings, 4);

		assertTrue(ngrams.getStarters().contains(getNgram("abcd", ngrams)));
	}

	@Test
	public void marksLastNgramAsEnder() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);

		NgramBag ngrams = extractor.extract(strings, 4);

		assertTrue(ngrams.isEnder(getNgram("ghij", ngrams)));
	}

	@Test
	public void marksNonFirstNgramsAsNotStarters() {
		String tenLetterString = "abcdefghij";
		strings.add(tenLetterString);

		NgramBag ngrams = extractor.extract(strings, 4);

		assertFalse(ngrams.getStarters().contains(getNgram("bcde", ngrams)));
		assertFalse(ngrams.getStarters().contains(getNgram("cdef", ngrams)));
		assertFalse(ngrams.getStarters().contains(getNgram("ghij", ngrams)));
	}

	@Test
	public void marksNonLastNgramsAsNotEnders() {
		String tenCharacterString = "abcdefghij";
		strings.add(tenCharacterString);

		NgramBag ngrams = extractor.extract(strings, 4);

		assertFalse(ngrams.isEnder(getNgram("abcd", ngrams)));
		assertFalse(ngrams.isEnder(getNgram("efgh", ngrams)));
		assertFalse(ngrams.isEnder(getNgram("fghi", ngrams)));
	}
}
