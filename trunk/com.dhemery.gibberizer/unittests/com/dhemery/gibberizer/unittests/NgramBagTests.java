package com.dhemery.gibberizer.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dhemery.gibberizer.core.NgramBag;


public class NgramBagTests {

	@Test
	public void newBagHasNoNgrams() {
		NgramBag bag = new NgramBag();
		assertEquals(0, bag.getNgrams().size());
	}

	@Test
	public void newBagHasNoStarters() {
		NgramBag bag = new NgramBag();
		assertEquals(0, bag.getNgrams().size());
	}
}
