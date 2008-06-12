package com.dhemery.gibberizer;

public class Snipper {
	private final int ngramLength;
	private final int snipLength;

	public Snipper(int ngramLength) {
		this.ngramLength = ngramLength;
		snipLength = ngramLength - 1;
	}

	public String extractChunk(String input, int chunkStartPosition,
			int chunkLength) {
		if (input.length() < chunkLength)
			return input;
		return input.substring(chunkStartPosition, chunkStartPosition
				+ chunkLength);
	}

	public String extractEndingSnip(String input) {
		return extractSnip(input, input.length() - snipLength);
	}

	public String extractNgram(String input, int startPosition) {
		return extractChunk(input, startPosition, ngramLength);
	}

	public String extractSnip(String input, int startPosition) {
		return extractChunk(input, startPosition, snipLength);
	}

	public String extractStartingNgram(String input) {
		return extractNgram(input, 0);
	}

	public String extractStartingSnip(String input) {
		return extractSnip(input, 0);
	}
}
