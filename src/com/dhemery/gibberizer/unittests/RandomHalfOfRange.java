package com.dhemery.gibberizer.unittests;

import com.dhemery.gibberizer.AbstractRandom;

public class RandomHalfOfRange implements AbstractRandom {

	@Override
	public int nextInt(int range) {
		return range / 2;
	}

}
