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
}
