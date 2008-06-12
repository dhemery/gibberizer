package com.dhemery.gibberizer.unittests;

import com.dhemery.gibberizer.AbstractRandom;

public class RandomConstantOffsetFromMax implements AbstractRandom {
	private final int offset;

	RandomConstantOffsetFromMax(int offset) {
		this.offset = offset;
	}

	@Override
	public int nextInt(int range) {
		return range - offset;
	}
}
