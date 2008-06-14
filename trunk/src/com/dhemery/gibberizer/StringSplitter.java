package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringSplitter {
	public static final int NONE = 0;
	public static final int WHITESPACE = 1;
	public static final int END_OF_LINE = 2;

	public List<String> split(String unsplitString, int delimiter) {
		List<String> strings = new ArrayList<String>();
		if(delimiter == NONE) {
			strings.add(unsplitString);
		} else {
			String delimiterExpression = "";
			if(delimiter == WHITESPACE) delimiterExpression = "\\s";
			if(delimiter == END_OF_LINE) delimiterExpression = "[\\n\\r]";

			for(String string : unsplitString.split(delimiterExpression)) {
				strings.add(string);
			}
		}
		return strings;
	}
}
