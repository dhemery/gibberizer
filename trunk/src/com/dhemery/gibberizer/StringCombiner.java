package com.dhemery.gibberizer;

import java.util.List;

public class StringCombiner {

	public String combine(List<String> strings) {
		String combinedStrings = "";
		for (String string : strings) combinedStrings += string + "\n";
		return combinedStrings;
	}
}
