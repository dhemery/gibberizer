package com.dhemery.gibberizer;

public class Ngram {
	public static final Ngram NULL_NGRAM = new Ngram("", "", "");
	private boolean isNameStarter;
	private String lastCharacter;
	private String prefix;
	private String suffix;

	private Ngram(String prefix, String suffix, String lastCharacter) {
		initialize(prefix, suffix, lastCharacter);
	}

	public Ngram(int n, String input) {
		// TODO: Can this be refactored?
		if (input.length() < n) 
			initialize(input, input.substring(1), "");
		else
			initialize(input.substring(0, n - 1), input.substring(1, n), input.substring(n - 1, n));
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

	public boolean isNameEnder() {
		return lastCharacter.isEmpty();
	}

	public boolean isNameStarter() {
		return isNameStarter;
	}

	public void setIsNameStarter(boolean isNameStarter) {
		this.isNameStarter = isNameStarter;
	}

	@Override
	public String toString() {
		return prefix + lastCharacter;
	}
}
