package com.dhemery.gibberizer;

import java.util.List;

public class StringCombiner {
	public static final int INSERT_SPACE = 0;
	public static final int INSERT_LINE_BREAK = 1;
	public static final int INSERT_2_LINE_BREAKS = 3;

	public String combine(List<String> strings, int delimiterStyle) {
		if (strings.size() == 0)
		{
			strings.add("*************************************************");
			strings.add("Warning:  Could not generate acceptable gibberish.");
			strings.add("Some things you can try:");
			strings.add("   - Relax the filter.");
			strings.add("   - Adjust familiarity.");
			strings.add("   - Increase persistence.");
			strings.add("   - Increase the number of strings to generate.");
			strings.add("   - Run again with the same parameters.");
			strings.add("*************************************************");
			delimiterStyle = INSERT_LINE_BREAK;
		}

		String delimiter = getDelimiter(delimiterStyle);

		String combinedStrings = "";
		boolean insertDelimiter = false;
		for (String string : strings) {
			if (insertDelimiter) combinedStrings += delimiter;
			combinedStrings += string;
			insertDelimiter = true;
		}
		return combinedStrings;
	}

	private String getDelimiter(int delimiterStyle) {
		switch (delimiterStyle) {
		case INSERT_SPACE:
			return " ";
		case INSERT_LINE_BREAK:
			return "\n";
		case INSERT_2_LINE_BREAKS:
			return "\n\n";
		default:
			return "";
		}
	}
}
