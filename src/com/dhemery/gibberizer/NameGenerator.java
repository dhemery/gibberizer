package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NameGenerator {
	private static final String terminator = " ";
	private final boolean allowInputEcho = false;
	private final boolean allowDuplicates = false;
	private final Hashtable<String, List<String>> hash = new Hashtable<String, List<String>>();
	private final int maxNameLength = 10;
	private final int minNameLength = 5;
	private final List<String> names = new ArrayList<String>();
	private final int ngramLength;
	private final int persistence = 5;
	private final AbstractRandom random = new DefaultRandom();
	private final Snipper snipper;

	public NameGenerator(String input, int ngramLength) {
		this.ngramLength = ngramLength;
		snipper = new Snipper(ngramLength);

		String[] splitNames = input.split("\\s+");

		for (String name : splitNames) {
			names.add(name);
			analyzeNgrams(name);
		}
	}

	public void analyzeNgrams(String name) {
		String terminatedName = name + terminator;
		for (int pos = 0; pos <= terminatedName.length() - ngramLength; pos++) {
			String ngram = snipper.extractNgram(terminatedName, pos);
			String ngramStartingSnip = snipper.extractStartingSnip(ngram);
			List<String> list = hash.get(ngramStartingSnip);
			if (list == null) {
				list = new ArrayList<String>();
				hash.put(ngramStartingSnip, list);
			}
			list.add(ngram.substring(ngramLength - 1));
		}
	}

	public String generateName() {
		String generatedName = grabRandomStartingSnip();
		String continuation = getContinuation(generatedName);
		while (!continuation.equals(terminator)) {
			generatedName += continuation;
			continuation = getContinuation(generatedName);
		}
		return generatedName;
	}

	public List<String> generateNames(int nameCount) {
		int attemptCount = 0;
		List<String> generatedNames = new ArrayList<String>();
		while (generatedNames.size() < nameCount) {
			String name = generateName().trim();
			if (isValidName(generatedNames, name))
				generatedNames.add(name);
			if (attemptCount > nameCount * persistence)
				break;
			attemptCount++;
		}
		return generatedNames;
	}

	public String getContinuation(List<String> possibleContinuations) {
		if (possibleContinuations == null)
			return terminator;
		int randomIndex = random.nextInt(possibleContinuations.size());
		return possibleContinuations.get(randomIndex);
	}

	public String getContinuation(String target) {
		String endingSnip = snipper.extractEndingSnip(target);
		List<String> matches = getMatches(endingSnip);
		return getContinuation(matches);
	}

	public List<String> getMatches(String snip) {
		return hash.get(snip);
	}

	public String grabRandomStartingSnip() {
		int randomKeyIndex = random.nextInt(names.size());
		String randomName = names.get(randomKeyIndex);
		String randomKey = snipper.extractStartingSnip(randomName);
		return randomKey;
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

	private boolean isInLengthRange(String name) {
		return (minNameLength <= name.length())
				&& (name.length() <= maxNameLength);
	}

	private boolean isInputEcho(String name) {
		return names.contains(name);
	}

	public boolean isValidName(List<String> generatedNames, String name) {
		return !isDisallowedInputEcho(name)
				&& !isDisallowedDuplicate(generatedNames, name)
				&& isInLengthRange(name);
	}
}