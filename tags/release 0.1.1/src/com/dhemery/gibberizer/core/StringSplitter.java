package com.dhemery.gibberizer.core;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	public List<String> split(String rawString, SplitStyle splitStyle) {
		List<String> strings = new ArrayList<String>();

		String[] arrayStrings = rawString.split(splitStyle.delimiterExpression, -1);
		for (String string : arrayStrings) {
			if (string.length() > 0) {
				strings.add(string);
			}
		}
		return strings;
	}
}
