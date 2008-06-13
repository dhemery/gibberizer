package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	public static final int NONE = 0;
	public static final int WHITESPACE = 0;
	public static final int END_OF_LINE = 0;

	private String delimiterExpression = "";

	public StringSplitter(int delimiter) {
		if(delimiter == WHITESPACE) delimiterExpression = "\\s+";
		if(delimiter == END_OF_LINE) delimiterExpression = "\\n+";
	}

	public List<String> split(String input) {
		List<String> strings = new ArrayList<String>();
		for(String string : input.split(delimiterExpression)) {
			strings.add(string);
		}
		return strings;
	}
}
