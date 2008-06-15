package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	public static final int DO_NOT_SPLIT = 0;
	public static final int SPLIT_AT_WHITE_SPACE = 1;
	public static final int SPLIT_AT_LINE_BREAKS = 2;

	public List<String> split(String rawString, int delimiterStyle) {
		List<String> strings = new ArrayList<String>();

		if (rawString.isEmpty()) return strings;

		if (delimiterStyle == DO_NOT_SPLIT) {
			strings.add(rawString);
			return strings;
		}

		String regEx = "";

		if (delimiterStyle == SPLIT_AT_WHITE_SPACE) regEx = "\\s+";
		if (delimiterStyle == SPLIT_AT_LINE_BREAKS) regEx = "[\\n\\r]+";

		for (String line : rawString.split(regEx, -1))
			if (!line.isEmpty()) strings.add(line);
		return strings;
	}
}
