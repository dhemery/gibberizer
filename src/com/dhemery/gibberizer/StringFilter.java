package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringFilter {
	private final List<List<String>> prohibitedStringsLists = new ArrayList<List<String>>();
	private final int maxStringLength;
	private final int minStringLength;

	public StringFilter(int minStringLength, int maxStringLength) {
		this.minStringLength = minStringLength;
		this.maxStringLength = maxStringLength;
	}

	public void addProhibitedStringsList(List<String> prohibitedStringsList) {
		prohibitedStringsLists.add(prohibitedStringsList);
	}

	public boolean canAdd(String string) {
		return isWithinLengthRange(string) && !isOnProhibitedStringsList(string);
	}

	private boolean isLongEnough(String string) {
		if(minStringLength < 1) return true;
		return string.length() >= minStringLength;
	}

	private boolean isOnProhibitedStringsList(String string) {
		for(List<String> prohibitedStringsList : prohibitedStringsLists) {
			if(prohibitedStringsList.contains(string)) return true;
		}
		return false;
	}

	private boolean isShortEnough(String string) {
		if(maxStringLength < 1) return true;
		return string.length() <= maxStringLength;
	}

	private boolean isWithinLengthRange(String string) {
		return (isLongEnough(string)) && (isShortEnough(string));
	}
}