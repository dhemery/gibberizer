package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.Snipper;


public class SnipperTests {

	int nGraphLength;
	private Snipper snipper;
	private String target;

	@Test
	public void endingSnipEqualsTargetIfTargetIsShorterThanSnipLength() {
		String target = "abcdef";
		Snipper snipper = new Snipper(target.length() + 100);
		String endingSnip = snipper.extractEndingSnip(target);
		assertEquals(target, endingSnip);
	}

	@Test
	public void endingSnipEqualsTargetIfTargetIsSnipLength() {
		String target = "abcdef";
		Snipper snipper = new Snipper(target.length() + 1);
		String endingSnip = snipper.extractEndingSnip(target);
		assertEquals(target, endingSnip);
	}

	@Test
	public void endingSnipIsSuffixOfTarget() {
		String endingSnip = snipper.extractEndingSnip(target);
		assertTrue(target.endsWith(endingSnip));
	}

	@Test
	public void endingSnipLengthIsOneLessThanNGraphLength() {
		String endingSnip = snipper.extractStartingSnip(target);
		assertEquals(nGraphLength - 1, endingSnip.length());
	}

	@Before
	public void setUp() {
		nGraphLength = 3;
		target = "abcdefg";
		snipper = new Snipper(nGraphLength);
	}
	
	@Test
	public void startingNGraphEqualsTargetIfTargetIsNGraphLength() {
		String target = "abcdef";
		Snipper snipper = new Snipper(target.length());
		String nGraph = snipper.extractStartingNGraph(target);
		assertEquals(target, nGraph);
	}

	@Test
	public void startingNGraphEqualsTargetIfTargetIsShorterThanNGraphLength() {
		String target = "abcdef";
		Snipper snipper = new Snipper(target.length() + 100);
		String nGraph = snipper.extractStartingNGraph(target);
		assertEquals(target, nGraph);
	}

	@Test
	public void startingNGraphHasSpecifiedLength() {
		String nGraph = snipper.extractStartingNGraph(target);
		assertEquals(nGraphLength, nGraph.length());
	}

	@Test
	public void startingNGraphIsPrefixOfTarget() {
		String nGraph = snipper.extractStartingNGraph(target);
		assertTrue(target.startsWith(nGraph));
	}

	@Test
	public void startingSnipEqualsTargetIfTargetIsShorterThanSnipLength() {
		String target = "abcdef";
		Snipper snipper = new Snipper(target.length() + 100);
		String startingSnip = snipper.extractStartingSnip(target);
		assertEquals(target, startingSnip);
	}

	@Test
	public void startingSnipEqualsTargetIfTargetIsSnipLength() {
		String target = "abcdef";
		Snipper snipper = new Snipper(target.length() + 1);
		String startingSnip = snipper.extractStartingSnip(target);
		assertEquals(target, startingSnip);
	}

	@Test
	public void startingSnipIsPrefixOfTarget() {
		String startingSnip = snipper.extractStartingSnip(target);
		assertTrue(target.startsWith(startingSnip));
	}

	@Test
	public void startingSnipLengthIsOneLessThanNGraphLength() {
		String startingSnip = snipper.extractStartingSnip(target);
		assertEquals(nGraphLength - 1, startingSnip.length());
	}
}
