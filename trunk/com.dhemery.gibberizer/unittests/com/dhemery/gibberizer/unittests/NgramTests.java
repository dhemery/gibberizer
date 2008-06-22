package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dhemery.gibberizer.core.Ngram;

public class NgramTests {

	@Test
	public void prefixMatchesFirstNMinusOneCharactersOfGivenString() {
		int n = 6;
		String string = "abcdefghij";
		String desiredPrefix = string.substring(0, n - 1);
		Ngram ngram = new Ngram(string, n);
		assertEquals(desiredPrefix, ngram.getPrefix());
	}

	@Test
	public void suffixMatchesCharactersOneThroughNOfGivenString() {
		int n = 6;
		String string = "abcdefghij";
		String desiredSuffix = string.substring(1, n);
		Ngram ngram = new Ngram(string, n);
		assertEquals(desiredSuffix, ngram.getSuffix());
	}

	@Test
	public void lastCharacterHasLengthOne() {
		int n = 6;
		String string = "abcdefghij";
		Ngram ngram = new Ngram(string, n);
		assertEquals(1, ngram.getLastCharacter().length());
	}

	@Test
	public void lastCharacterMatchesCharacterAtPositionNMinusOneFromGivenString() {
		int n = 6;
		String string = "abcdefghij";
		Ngram ngram = new Ngram(string, n);
		String desiredSuffix = string.substring(n - 1, n);
		assertEquals(desiredSuffix, ngram.getLastCharacter());
	}

	@Test
	public void prefixEqualsGivenStringIfGivenStringIsShorterThanN() {
		String string = "abcdefghij";
		int n = string.length() + 1;
		Ngram ngram = new Ngram(string, n);
		assertEquals(string, ngram.getPrefix());
	}

	@Test
	public void suffixMatchesTailOfGivenStringIfGivenStringIsShorterThanN() {
		String string = "abcdefghij";
		int n = string.length() + 1;
		Ngram ngram = new Ngram(string, n);
		assertTrue(string.endsWith(ngram.getSuffix()));
	}

	@Test
	public void lastCharacterIsEmptyIfGivenStringIsShorterThanN() {
		String string = "abcdefghij";
		int n = string.length() + 1;
		Ngram ngram = new Ngram(string, n);
		assertTrue(ngram.getLastCharacter().length() == 0);
	}

	@Test
	public void nullNgramHasEmptyPrefix() {
		assertTrue(Ngram.NULL_NGRAM.getPrefix().length() == 0);
	}

	@Test
	public void nullNgramHasEmptySuffix() {
		assertTrue(Ngram.NULL_NGRAM.getSuffix().length() == 0);
	}

	@Test
	public void nullNgramHasEmptyLastCharacter() {
		assertTrue(Ngram.NULL_NGRAM.getLastCharacter().length() == 0);
	}

	@Test
	public void toStringEqualsPrefixPlusLastCharacter() {
		int n = 6;
		String string = "abcdefghij";
		Ngram ngram = new Ngram(string, n);
		assertEquals(ngram.getPrefix() + ngram.getLastCharacter(), ngram
				.toString());
	}
}
