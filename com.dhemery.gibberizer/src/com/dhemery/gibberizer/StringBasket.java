package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringBasket {
	private int attemptCount;
	private final List<String> deliveredStrings = new ArrayList<String>();
	private final StringFilter filter;
	private final int persistence;
	private final int stringCount;

	public StringBasket(int stringCount, StringFilter filter, int persistence) {
		this.stringCount = stringCount;
		this.filter = filter;
		this.persistence = persistence;
	}

	public void deliver(String string) {
		if (isDone()) return;
		if (filter.canAdd(string)) deliveredStrings.add(string);
		attemptCount++;
	}

	public List<String> getDeliveredStrings() {
		return deliveredStrings;
	}

	private boolean giveUp() {
		return attemptCount > stringCount * persistence;
	}

	public boolean isDone() {
		return isFull() || giveUp();
	}

	private boolean isFull() {
		return deliveredStrings.size() >= stringCount;
	}
}