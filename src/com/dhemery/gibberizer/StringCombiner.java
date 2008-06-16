package com.dhemery.gibberizer;

import java.util.List;

public class StringCombiner {
	public static final int INSERT_SPACE = 0;
	public static final int INSERT_LINE_BREAK = 1;
	public static final int INSERT_2_LINE_BREAKS = 2;

	private static final String[] delimiters = { " ", "\n", "\n\n" };

	public String combine(List<String> strings, int delimiterStyle) {
		if (strings.size() == 0)
		{
			makeWarning(strings);
			delimiterStyle = INSERT_LINE_BREAK;
		}

		String delimiter = delimiters[delimiterStyle];

		String combinedStrings = "";
		boolean insertDelimiter = false;
		for (String string : strings) {
			if (insertDelimiter) combinedStrings += delimiter;
			combinedStrings += string;
			insertDelimiter = true;
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
		strings.add("   - Run again with the same parameters.");
		strings.add("*************************************************");
	}
}
