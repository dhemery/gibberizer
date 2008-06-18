package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	public enum SplitStyle {
		WORDS("\\s+"), // Split at white space.
		LINES("[\\n\\r]+"), // Split at line break.
		ONE_STRING("[^\\s\\S]+");	// Don't split (r.e. matches sequence of characters that
									// are neither white space nor non-white space)

		private final String delimiterExpression;

		SplitStyle(String delimiterExpression) {
			this.delimiterExpression = delimiterExpression;
		}
	};

	public List<String> split(String rawString, SplitStyle splitStyle) {
		List<String> strings = new ArrayList<String>();
		String normalizedString = rawString.replaceAll("\r\n", "\n");

		String[] arrayStrings = normalizedString.split(splitStyle.delimiterExpression, -1);
		for (String string : arrayStrings) {
			if (!string.isEmpty()) {
				strings.add(string);
			}
		}
		return strings;
	}
}
