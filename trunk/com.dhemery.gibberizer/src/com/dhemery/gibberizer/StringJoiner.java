package com.dhemery.gibberizer;

import java.util.List;

public class StringJoiner {
	enum JoinStyle {
		SPACE(" "),
		LINE_BREAK("\n"),
		TWO_LINE_BREAKS("\n\n");
		
		private final String delimiter;
		
		JoinStyle(String delimiter) {
			this.delimiter = delimiter;
		}
	};

	public String combine(List<String> strings, JoinStyle joinStyle) {
		if (strings.size() == 0)
		{
			makeWarning(strings);
			joinStyle = JoinStyle.LINE_BREAK;
		}

		String combinedStrings = strings.get(0);

		for (String string : strings.subList(1, strings.size())) {
			combinedStrings += joinStyle.delimiter + string;
		}
		return combinedStrings;
	}

	private void makeWarning(List<String> strings) {
		strings.add("*************************************************");
		strings.add("Warning:  Could not generate acceptable gibberish.");
		strings.add("Some things you can try:");
		strings.add("   - Relax the filter.");
		strings.add("   - Adjust familiarity.");
		strings.add("   - Increase persistence.");
		strings.add("   - Increase the number of strings to generate.");
		strings.add("   - Add more input text)");
		strings.add("   - Run again with the same parameters.");
		strings.add("*************************************************");
	}
}
