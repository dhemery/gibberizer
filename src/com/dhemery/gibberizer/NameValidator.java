package com.dhemery.gibberizer;

import java.util.List;

public class NameValidator {
	private final boolean allowInputEcho;
	private final boolean allowDuplicates;
	private final int maxNameLength;
	private final int minNameLength;
	private final List<String> inputNames;

	public NameValidator(List<String> inputNames, boolean allowInputEcho, boolean allowDuplicates, int minNameLength, int maxNameLength) {
		this.inputNames = inputNames;
		this.allowInputEcho = allowInputEcho;
		this.allowDuplicates = allowDuplicates;
		this.minNameLength = minNameLength;
		this.maxNameLength = maxNameLength;
	}

	private boolean isAcceptableLength(String name) {
		return (minNameLength <= name.length())
				&& (name.length() <= maxNameLength);
	}

	public boolean isAllowed(String name, List<String> outputNames) {
		return isAllowedByInput(name) && isAllowedByOutput(name, outputNames)
				&& isAcceptableLength(name);
	}

	private boolean isAllowed(String name, List<String> list, boolean allowDuplicates) {
		return allowDuplicates || !list.contains(name);
	}

	private boolean isAllowedByInput(String name) {
		return isAllowed(name, inputNames, allowInputEcho);
	}

	private boolean isAllowedByOutput(String name, List<String> outputNames) {
		return isAllowed(name, outputNames, allowDuplicates);
	}
}