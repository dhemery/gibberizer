package com.dhemery.gibberizer;

import java.util.Random;

public class DefaultRandom implements AbstractRandom {
	private final Random random = new Random();

	@Override
	public int nextInt(int range) {
		return random.nextInt(range);
	}
}
