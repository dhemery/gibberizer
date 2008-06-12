package com.dhemery.gibberizer.unittests;

import com.dhemery.gibberizer.NGraphAbstractRandom;

public class RandomConstant implements NGraphAbstractRandom {
	int constant;
	
	RandomConstant(int constant) {
		this.constant = constant;
	}

	@Override
	public int nextInt(int range) {
		return constant;
	}
}
