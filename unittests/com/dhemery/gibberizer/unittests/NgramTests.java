package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.Ngram;

public class NgramTests {

	private String string;
	private int n;

	@Test
	public void equalIfSamePrefixAndLastLetter() {
		Ngram ngram1 = new Ngram(string, n);
		Ngram ngram2 = new Ngram(string, n);
		assertTrue(ngram1.equals(ngram2));
	}

	@Test
	public void equalityIsCaseInsensive() {
		Ngram ngramLower = new Ngram("abcd", 4);
		Ngram ngramUpper = new Ngram("ABCD", 4);
		assertTrue(ngramLower.equals(ngramUpper));
	}

	@Test
	public void equalStringConversionDoesNotImplyEqualNgrams() {
		Ngram ngram1 = new Ngram("abcd", 4); // Prefix "abc", Last char "d"
		Ngram ngram2 = new Ngram("abcd", 5); // Prefix "abcd", Last char ""
		assertEquals(ngram1.toString(), ngram2.toString()); // To demonstrate
															// equal toString()
		assertFalse(ngram1.equals(ngram2));
	}

	@Test
	public void lastCharacterHasLengthOne() {
		Ngram ngram = new Ngram(string, n);
		assertEquals(1, ngram.getLastCharacter().length());
	}

	@Test
	public void lastCharacterIsEmptyIfGivenStringIsShorterThanN() {
		n = string.length() + 1;
		Ngram ngram = new Ngram(string, n);
		assertTrue(ngram.getLastCharacter().isEmpty());
	}

	@Test
	public void lastCharacterMatchesCharacterAtPositionNMinusOneFromGivenString() {
		Ngram ngram = new Ngram(string, n);
		String desiredSuffix = string.substring(n - 1, n);
		assertEquals(desiredSuffix, ngram.getLastCharacter());
	}

	@Test
	public void notEqualIfDifferentLastLetter() {
		Ngram ngram1 = new Ngram("abcdefg", 4);
		Ngram ngram2 = new Ngram("abceefg", 4);
		assertFalse(ngram1.equals(ngram2));
	}

	@Test
	public void notEqualIfDifferentPrefix() {
		Ngram ngram1 = new Ngram("abcdefg", 4);
		Ngram ngram2 = new Ngram("bbcdefg", 4);
		assertFalse(ngram1.equals(ngram2));
	}

	@Test
	public void nullNgramHasEmptyLastCharacter() {
		assertTrue(Ngram.NULL_NGRAM.getLastCharacter().isEmpty());
	}

	@Test
	public void nullNgramHasEmptyPrefix() {
		assertTrue(Ngram.NULL_NGRAM.getPrefix().isEmpty());
	}

	@Test
	public void nullNgramHasEmptySuffix() {
		assertTrue(Ngram.NULL_NGRAM.getSuffix().isEmpty());
	}

	@Test
	public void prefixEqualsGivenStringIfGivenStringIsShorterThanN() {
		n = string.length() + 1;
		Ngram ngram = new Ngram(string, n);
		assertEquals(string, ngram.getPrefix());
	}

	@Test
	public void prefixMatchesFirstNMinusOneCharactersOfGivenString() {
		String desiredPrefix = string.substring(0, n - 1);
		Ngram ngram = new Ngram(string, n);
		assertEquals(desiredPrefix, ngram.getPrefix());
	}

	@Before
	public void setUp() {
		n = 6;
		string = "abcdefghij";
	}

	@Test
	public void suffixMatchesCharactersOneThroughNOfGivenString() {
		String desiredSuffix = string.substring(1, n);
		Ngram ngram = new Ngram(string, n);
		assertEquals(desiredSuffix, ngram.getSuffix());
	}

	@Test
	public void suffixMatchesTailOfGivenStringIfGivenStringIsShorterThanN() {
		n = string.length() + 1;
		Ngram ngram = new Ngram(string, n);
		assertTrue(string.endsWith(ngram.getSuffix()));
	}

	@Test
	public void toStringEqualsPrefixPlusLastCharacter() {
		Ngram ngram = new Ngram(string, n);
		assertEquals(ngram.getPrefix() + ngram.getLastCharacter(), ngram
				.toString());
	}
}
