package com.dhemery.gibberizer;

public class Ngram {
	private boolean isNameStarter;
	private String lastCharacter;
	private String prefix;
	private String suffix;

	public Ngram(int n, String input) {
		if(input.length() < n) {
			prefix = input;
			suffix = input.substring(1);
			lastCharacter = "";
		}
		else {
			prefix = input.substring(0, n - 1);
			suffix = input.substring(1, n);
			lastCharacter = input.substring(n - 1, n);
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public String getLastCharacter() {
		return lastCharacter;
	}

	public String getSuffix() {
		return suffix;
	}

	@Override
	public String toString() {
		return prefix + lastCharacter;
	}

	public boolean isNameStarter() {
		return isNameStarter;
	}

	public void setIsNameStarter(boolean isNameStarter) {
		this.isNameStarter = isNameStarter;
	}
}
