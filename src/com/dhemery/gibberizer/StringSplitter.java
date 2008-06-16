package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class StringSplitter {
	public enum SplitStyle { WORDS, LINES, ONE_STRING };

	private static final EnumMap<SplitStyle, String> splitterExpressions = createEnumMap();

	private static EnumMap<SplitStyle, String> createEnumMap() {
		EnumMap<SplitStyle, String> map = new EnumMap<SplitStyle, String>(SplitStyle.class);
		map.put(SplitStyle.WORDS, "\\s+");
		map.put(SplitStyle.LINES, "[\\n\\r]+");
		map.put(SplitStyle.ONE_STRING, "[^\\s\\S]+"); // Neither ws nor non-ws.
		return map;
	}

	public List<String> split(String rawString, SplitStyle delimiterStyle) {
		List<String> strings = new ArrayList<String>();

		String splitterExpression = splitterExpressions.get(delimiterStyle);
		String[] arrayStrings = rawString.split(splitterExpression, -1);
		for (String string : arrayStrings) {
			if (!string.isEmpty()) {
				strings.add(string);
			}
		}
		return strings;
	}
}
