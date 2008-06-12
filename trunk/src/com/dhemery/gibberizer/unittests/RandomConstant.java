package com.dhemery.gibberizer.unittests;

import com.dhemery.gibberizer.AbstractRandom;

public class RandomConstant implements AbstractRandom {
	private final int constant;

	RandomConstant(int constant) {
		this.constant = constant;
	}

	@Override
	public int nextInt(int range) {
		return constant;
	}
}
