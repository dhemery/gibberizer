package com.dhemery.gibberizer.core;

public class Ngram {
	public static final Ngram NULL_NGRAM = new Ngram("", "", "");
	private String lastCharacter;
	private String prefix;
	private String suffix;

	public Ngram(String string, int n) {
		// TODO: Can this be refactored?
		if (string.isEmpty() || n < 1) initialize("", "", "");
		else if (string.length() < n) initialize(string, string.substring(1),
				"");
		else initialize(string.substring(0, n - 1), string.substring(1, n),
				string.substring(n - 1, n));
	}

	private Ngram(String prefix, String suffix, String lastCharacter) {
		initialize(prefix, suffix, lastCharacter);
	}

	@Override
	public boolean equals(Object o) {
		Ngram other = (Ngram) o;
		return prefix.equalsIgnoreCase(other.prefix)
				&& lastCharacter.equalsIgnoreCase(other.lastCharacter);
	}

	public String getLastCharacter() {
		return lastCharacter;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	private void initialize(String prefix, String suffix, String lastCharacter) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.lastCharacter = lastCharacter;
	}

	@Override
	public String toString() {
		return prefix + lastCharacter;
	}
}
