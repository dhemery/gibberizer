package com.dhemery.gibberizer;

import java.util.List;

public class StringCombiner {

	public String combine(List<String> strings, String outputDelimiter) {
		if (strings.size() == 0)
			return "<Could not create allowable gibberish.>";
		String combinedStrings = "";
		for (String string : strings)
			combinedStrings += string + outputDelimiter;
		return combinedStrings;
	}
}
