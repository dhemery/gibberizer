package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.Ngram;

public class NgramTests {

	private String given;
	private int n;

	@Before
	public void setUp() {
		n = 6;
		given = "abcdefghij";
	}

	@Test
	public void prefixMatchesFirstNMinusOneCharactersOfGivenString() {
		String desiredPrefix = given.substring(0, n - 1);
		Ngram ngram = new Ngram(n, given);
		assertEquals(desiredPrefix, ngram.getPrefix());
	}

	@Test
	public void suffixMatchesCharactersOneThroughNOfGivenString() {
		String desiredSuffix = given.substring(1, n);
		Ngram ngram = new Ngram(n, given);
		assertEquals(desiredSuffix, ngram.getSuffix());
	}

	@Test
	public void lastCharacterHasLengthOne() {
		Ngram ngram = new Ngram(n, given);
		assertEquals(1, ngram.getLastCharacter().length());
	}

	@Test
	public void lastCharacterMatchesCharacterAtPositionNMinusOneFromGivenString() {
		Ngram ngram = new Ngram(n, given);
		String desiredSuffix = given.substring(n - 1, n);
		assertEquals(desiredSuffix, ngram.getLastCharacter());
	}

	@Test
	public void prefixEqualsGivenStringIfGivenStringIsShorterThanN() {
		n = given.length() + 1;
		Ngram ngram = new Ngram(n, given);
		assertEquals(given, ngram.getPrefix());
	}

	@Test
	public void suffixMatchesTailOfGivenStringIfGivenStringIsShorterThanN() {
		n = given.length() + 1;
		Ngram ngram = new Ngram(n, given);
		assertTrue(given.endsWith(ngram.getSuffix()));
	}

	@Test
	public void lastCharacterIsEmptyIfGivenStringIsShorterThanN() {
		n = given.length() + 1;
		Ngram ngram = new Ngram(n, given);
		assertTrue(ngram.getLastCharacter().isEmpty());
	}
	
	@Test
	public void toStringEqualsPrefixPlusLastCharacter() {
		Ngram ngram = new Ngram(n, given);
		assertEquals(ngram.getPrefix() + ngram.getLastCharacter(), ngram.toString());
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
	public void nullNgramHasEmptyLastCharacter() {
		assertTrue(Ngram.NULL_NGRAM.getLastCharacter().isEmpty());
	}
	
	@Test
	public void equalIfSamePrefixAndLastLetter() {
		Ngram ngram1 = new Ngram(4, given);
		Ngram ngram2 = new Ngram(4, given);
		assertTrue(ngram1.equals(ngram2));
	}
	
	@Test
	public void notEqualIfDifferentPrefix() {
		Ngram ngram1 = new Ngram(4, "abcdefg");
		Ngram ngram2 = new Ngram(4, "bbcdefg");
		assertFalse(ngram1.equals(ngram2));
	}
	
	@Test
	public void notEqualIfDifferentLastLetter() {
		Ngram ngram1 = new Ngram(4, "abcdefg");
		Ngram ngram2 = new Ngram(4, "abceefg");
		assertFalse(ngram1.equals(ngram2));
	}
	
	@Test
	public void equalStringConversionDoesNotImplyEqualNgrams() {
		Ngram ngram1 = new Ngram(4, "abcd"); // Prefix "abc", Last char "d"
		Ngram ngram2 = new Ngram(5, "abcd"); // Prefix "abcd", Last char ""
		assertEquals(ngram1.toString(), ngram2.toString()); // To demonstrate equal toString()
		assertFalse(ngram1.equals(ngram2));
	}
	
	@Test
	public void equalityIsCaseInsensive() {
		Ngram ngramLower = new Ngram(4, "abcd");
		Ngram ngramUpper = new Ngram(4, "ABCD");
		assertTrue(ngramLower.equals(ngramUpper));
	}
}
