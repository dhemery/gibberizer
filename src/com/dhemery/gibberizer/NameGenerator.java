package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NameGenerator {
	private final boolean allowInputEcho = false;
	private final boolean allowDuplicates = false;
	private final int maxNameLength = 10;
	private final int minNameLength = 5;
	private final List<String> names;
	private final List<Ngram> nameStarters = new ArrayList<Ngram>();
	private final int persistence = 5;
	private final AbstractRandom random = new DefaultRandom();
	private final Hashtable<String, List<Ngram>> successorsByPrefix = new Hashtable<String, List<Ngram>>();

	public NameGenerator(List<String> names, List<Ngram> ngrams) {
		this.names = names;
		for (Ngram ngram : ngrams)
			if (ngram.isNameStarter()) nameStarters.add(ngram);
			else addToSuccessorList(ngram);
	}

	private void addToSuccessorList(Ngram ngram) {
		String prefix = ngram.getPrefix();
		List<Ngram> ngramsWithMatchingPrefix = successorsByPrefix.get(prefix);
		if (ngramsWithMatchingPrefix == null) {
			ngramsWithMatchingPrefix = new ArrayList<Ngram>();
			successorsByPrefix.put(prefix, ngramsWithMatchingPrefix);
		}
		ngramsWithMatchingPrefix.add(ngram);
	}

	private boolean canContinueAfter(Ngram ngram) {
		return (ngram != null) && !ngram.getLastCharacter().isEmpty();
	}

	private boolean generateMoreNames(List<String> generatedNames,
			int targetNameCount, int attemptCount) {
		return (notEnoughNames(generatedNames, targetNameCount))
				&& !(giveUp(targetNameCount, attemptCount));
	}

	public String generateName() {
		Ngram ngram = selectRandomNameStarter();
		String generatedName = ngram.getPrefix();
		while (canContinueAfter(ngram)) {
			generatedName += ngram.getLastCharacter();
			ngram = selectRandomSuccessor(ngram);
		}
		return generatedName;
	}

	public List<String> generateNames(int targetNameCount) {
		int attemptCount = 0;
		List<String> generatedNames = new ArrayList<String>();
		while (generateMoreNames(generatedNames, targetNameCount, attemptCount)) {
			String name = generateName();
			if (isValidName(generatedNames, name)) generatedNames.add(name);
			attemptCount++;
		}
		return generatedNames;
	}

	private boolean giveUp(int targetNameCount, int attemptCount) {
		return attemptCount >= targetNameCount * persistence;
	}

	private boolean hasAcceptableLength(String name) {
		return (minNameLength <= name.length())
				&& (name.length() <= maxNameLength);
	}

	private boolean isDisallowedDuplicate(List<String> generatedNames,
			String name) {
		return !allowDuplicates && isDuplicate(generatedNames, name);
	}

	private boolean isDisallowedInputEcho(String name) {
		return !allowInputEcho && isInputEcho(name);
	}

	private boolean isDuplicate(List<String> generatedNames, String name) {
		return generatedNames.contains(name);
	}

	private boolean isInputEcho(String name) {
		return names.contains(name);
	}

	public boolean isValidName(List<String> generatedNames, String name) {
		return !isDisallowedInputEcho(name)
				&& !isDisallowedDuplicate(generatedNames, name)
				&& hasAcceptableLength(name);
	}

	private boolean notEnoughNames(List<String> generatedNames,
			int targetNameCount) {
		return generatedNames.size() < targetNameCount;
	}

	private Ngram selectRandomNameStarter() {
		int randomNameStarterIndex = random.nextInt(nameStarters.size());
		return nameStarters.get(randomNameStarterIndex);
	}

	private Ngram selectRandomSuccessor(Ngram ngram) {
		List<Ngram> successors = successorsByPrefix.get(ngram.getSuffix());
		if (successors == null) return null;
		int randomSuccessorIndex = random.nextInt(successors.size());
		return successors.get(randomSuccessorIndex);
	}
}