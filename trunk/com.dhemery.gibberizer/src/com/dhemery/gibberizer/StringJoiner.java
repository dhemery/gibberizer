package com.dhemery.gibberizer;

import java.util.List;

public class StringJoiner {
	enum JoinStyle {
		SPACE(" "),
		NEW_LINE("\n"),
		BLANK_LINE("\n\n");
		
		private final String delimiter;
		
		JoinStyle(String delimiter) {
			this.delimiter = delimiter;
		}
	};

	public String combine(List<String> strings, JoinStyle joinStyle) {
		if (strings.size() == 0)
		{
			joinStyle = JoinStyle.NEW_LINE;
		}

		String combinedStrings = strings.get(0);

		for (String string : strings.subList(1, strings.size())) {
			combinedStrings += joinStyle.delimiter + string;
		}
		return combinedStrings;
	}
}
