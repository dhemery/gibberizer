package com.dhemery.gibberizer.core;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
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
