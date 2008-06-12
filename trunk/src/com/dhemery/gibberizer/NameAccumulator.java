package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class NameAccumulator {
	private final List<String> accumulatedNames = new ArrayList<String>();
	private final boolean allowInputEcho = false;
	private final boolean allowDuplicates = false;
	private int attemptCount;
	private final int maxNameLength = 10;
	private final int minNameLength = 5;
	private final List<String> inputNames;
	private final int persistence = 5;
	private final int targetCount;

	public NameAccumulator(List<String> inputNames, int targetCount) {
		this.inputNames = inputNames;
		this.targetCount = targetCount;
	}

	public void add(String name) {
		if (canAdd(name)) accumulatedNames.add(name);
		attemptCount++;
	}

	private boolean alreadyAccumulated(String name) {
		return accumulatedNames.contains(name);
	}

	private boolean canAdd(String name) {
		return !haveEnoughNames() && isValid(name);
	}

	public List<String> getAccumulatedNames() {
		return accumulatedNames;
	}

	private boolean giveUp() {
		return attemptCount >= targetCount * persistence;
	}

	private boolean hasAcceptableLength(String name) {
		return (minNameLength <= name.length())
				&& (name.length() <= maxNameLength);
	}

	private boolean haveEnoughNames() {
		return accumulatedNames.size() >= targetCount;
	}

	private boolean isDisallowedDuplicate(String name) {
		return !allowDuplicates && alreadyAccumulated(name);
	}

	private boolean isDisallowedInputEcho(String name) {
		return !allowInputEcho && isInputEcho(name);
	}

	public boolean isDone() {
		return haveEnoughNames() || giveUp();
	}

	private boolean isInputEcho(String name) {
		return inputNames.contains(name);
	}

	private boolean isValid(String name) {
		return !isDisallowedInputEcho(name) && !isDisallowedDuplicate(name)
				&& hasAcceptableLength(name);
	}
}