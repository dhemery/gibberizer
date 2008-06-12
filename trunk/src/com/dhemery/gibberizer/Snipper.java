package com.dhemery.gibberizer;

public class Snipper {
	private int nGraphLength;
	private int snipLength;

	public Snipper(int nGraphLength) {
		this.nGraphLength = nGraphLength;
		snipLength = nGraphLength - 1;
	}

	public String extractChunk(String input, int startPosition, int length) {
		if(input.length() < length) return input;
		return input.substring(startPosition, startPosition + length);
	}

	public String extractEndingSnip(String input) {
		return extractSnip(input, input.length() - snipLength);
	}

	public String extractNGraph(String input, int startPosition) {
		return extractChunk(input, startPosition, nGraphLength);
	}

	public String extractSnip(String input, int startPosition) {
		return extractChunk(input, startPosition, snipLength);
	}

	public String extractStartingNGraph(String input) {
		return extractNGraph(input, 0);
	}

	public String extractStartingSnip(String input) {
		return extractSnip(input, 0);
	}
}
