package com.dhemery.gibberizer.unittests;

import com.dhemery.gibberizer.NGraphAbstractRandom;

public class RandomHalfOfRange implements NGraphAbstractRandom {

	@Override
	public int nextInt(int range) {
		return range / 2;
	}

}
