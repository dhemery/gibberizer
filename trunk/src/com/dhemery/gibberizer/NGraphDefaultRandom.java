package com.dhemery.gibberizer;

import java.util.Random;

public class NGraphDefaultRandom implements NGraphAbstractRandom {
	private Random random = new Random();

	@Override
	public int nextInt(int range) {
		return random.nextInt(range);
	}
}
