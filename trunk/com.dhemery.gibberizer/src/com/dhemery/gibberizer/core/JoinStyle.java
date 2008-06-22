package com.dhemery.gibberizer.core;

public enum JoinStyle {
	SPACE(" "),
	NEW_LINE("\n"),
	BLANK_LINE("\n\n");
	
	private final String delimiter;
	
	JoinStyle(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}
}