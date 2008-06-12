package com.dhemery.gibberizer.unittests;

import com.dhemery.gibberizer.NGraphAbstractRandom;

public class RandomConstantOffsetFromMax implements NGraphAbstractRandom {
	int offset;
	
	RandomConstantOffsetFromMax(int offset) {
		this.offset = offset;
	}

	@Override
	public int nextInt(int range) {
		return range - offset;
	}
}
