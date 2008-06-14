package com.dhemery.gibberizer;

public class Ngram {
	public static final Ngram NULL_NGRAM = new Ngram("", "", "");
	private boolean isEnder;
	private boolean isStarter;
	private String lastCharacter;
	private String prefix;
	private String suffix;

	private Ngram(String prefix, String suffix, String lastCharacter) {
		initialize(prefix, suffix, lastCharacter);
	}

	public Ngram(String string, int n) {
		// TODO: Can this be refactored?
		if(string.isEmpty()) initialize("", "", "");
		else if(string.length() < n) initialize(string, string.substring(1), "");
		else initialize(string.substring(0, n - 1), string.substring(1, n), string.substring(n - 1, n));
	}

	@Override
	public boolean equals(Object o) {
		Ngram other = (Ngram)o;
		return this.prefix.equalsIgnoreCase(other.prefix)
			&& this.lastCharacter.equalsIgnoreCase(other.lastCharacter);
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

	public boolean isEnder() {
		return isEnder;
	}

	public boolean isStarter() {
		return isStarter;
	}

	public void setIsEnder(boolean isEnder) {
		this.isEnder = isEnder;
	}

	public void setIsStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}

	@Override
	public String toString() {
		return prefix + lastCharacter;
	}
}
