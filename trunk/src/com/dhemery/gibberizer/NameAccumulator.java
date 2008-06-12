package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class NameAccumulator {
	private final List<String> accumulatedNames = new ArrayList<String>();
	private int attemptCount;
	private final int nameCount;
	private final int persistence = 5;
	private final NameValidator validator;

	public NameAccumulator(NameValidator validator, int nameCount) {
		this.nameCount = nameCount;
		this.validator = validator;
	}

	public void add(String name) {
		if (canAdd(name)) accumulatedNames.add(name);
		attemptCount++;
	}

	private boolean canAdd(String name) {
		return !haveEnoughNames() && validator.isAllowed(name, accumulatedNames);
	}

	public List<String> getAccumulatedNames() {
		return accumulatedNames;
	}

	private boolean giveUp() {
		return attemptCount >= nameCount * persistence;
	}

	private boolean haveEnoughNames() {
		return accumulatedNames.size() >= nameCount;
	}

	public boolean isDone() {
		return haveEnoughNames() || giveUp();
	}
}