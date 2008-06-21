package com.dhemery.gibberizer.core;

import java.util.List;

public class StringJoiner {
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
