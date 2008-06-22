package com.dhemery.gibberizer.core;

public enum SplitStyle {
	WORDS("\\s+"), // Split at white space.
	LINES("[\\n\\r]+"), // Split at line break.
	ONE_STRING("[^\\s\\S]+");	// Don't split (r.e. matches sequence of characters that
								// are neither white space nor non-white space)

	private final String delimiterExpression;

	SplitStyle(String delimiterExpression) {
		this.delimiterExpression = delimiterExpression;
	}

	String getDelimiterExpression() {
		return delimiterExpression;
	}
}