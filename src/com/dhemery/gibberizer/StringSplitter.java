package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	public static final int DO_NOT_SPLIT = 0;
	public static final int SPLIT_AT_WHITE_SPACE = 1;
	public static final int SPLIT_AT_LINE_BREAKS = 2;

	private static final String matchNothing = "[^\\s\\S]+"; // Neither ws nor non-ws.
	private static final String matchWhiteSpace = "\\s+";
	private static final String matchLineBreak = "[\\n\\r]+";

	private static String[] delimiters = { matchNothing, matchWhiteSpace, matchLineBreak };

	public List<String> split(String rawString, int delimiterStyle) {
		List<String> strings = new ArrayList<String>();

		String[] arrayStrings = rawString.split(delimiters[delimiterStyle], -1);
		for (String string : arrayStrings) {
			if (!string.isEmpty()) {
				strings.add(string);
			}
		}
		return strings;
	}
}
