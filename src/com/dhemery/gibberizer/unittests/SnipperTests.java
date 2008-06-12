package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.Snipper;

public class SnipperTests {
	private int n;
	private Snipper snipper;
	private String target;

	@Test
	public void endingSnipEqualsTargetIfTargetIsShorterThanSnipLength() {
		n = target.length() + 100;
		snipper = new Snipper(n);
		String endingSnip = snipper.extractEndingSnip(target);
		assertEquals(target, endingSnip);
	}

	@Test
	public void endingSnipEqualsTargetIfTargetIsSnipLength() {
		int snipLength = target.length();
		n = snipLength + 1;
		snipper = new Snipper(n);
		String endingSnip = snipper.extractEndingSnip(target);
		assertEquals(target, endingSnip);
	}

	@Test
	public void endingSnipIsSuffixOfTarget() {
		String endingSnip = snipper.extractEndingSnip(target);
		assertTrue(target.endsWith(endingSnip));
	}

	@Test
	public void endingSnipLengthIsNMinusOne() {
		String endingSnip = snipper.extractStartingSnip(target);
		assertEquals(n - 1, endingSnip.length());
	}

	@Before
	public void setUp() {
		n = 3;
		target = "abcdefg";
		snipper = new Snipper(n);
	}

	@Test
	public void startingNgramEqualsTargetIfTargetHasLengthN() {
		n = target.length();
		snipper = new Snipper(n);
		String ngram = snipper.extractStartingNgram(target);
		assertEquals(target, ngram);
	}

	@Test
	public void startingNgramEqualsTargetIfTargetIsShorterThanN() {
		n = target.length() + 100;
		snipper = new Snipper(n);
		String ngram = snipper.extractStartingNgram(target);
		assertEquals(target, ngram);
	}

	@Test
	public void startingNgramHasLengthN() {
		String ngram = snipper.extractStartingNgram(target);
		assertEquals(n, ngram.length());
	}

	@Test
	public void startingNgramIsPrefixOfTarget() {
		String ngram = snipper.extractStartingNgram(target);
		assertTrue(target.startsWith(ngram));
	}

	@Test
	public void startingSnipEqualsTargetIfTargetIsShorterThanSnipLength() {
		n = target.length() + 100;
		Snipper snipper = new Snipper(n);
		String startingSnip = snipper.extractStartingSnip(target);
		assertEquals(target, startingSnip);
	}

	@Test
	public void startingSnipEqualsTargetIfTargetIsSnipLength() {
		int snipLength = target.length();
		n = snipLength + 1;
		snipper = new Snipper(n);
		String startingSnip = snipper.extractStartingSnip(target);
		assertEquals(target, startingSnip);
	}

	@Test
	public void startingSnipIsPrefixOfTarget() {
		String startingSnip = snipper.extractStartingSnip(target);
		assertTrue(target.startsWith(startingSnip));
	}

	@Test
	public void startingSnipLengthIsNMinusOne() {
		String startingSnip = snipper.extractStartingSnip(target);
		assertEquals(n - 1, startingSnip.length());
	}

}
