package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NGraphs {
	private static final String terminator = " ";
	private boolean allowInputEcho = false;
	private boolean allowDuplicates = false;
	private Hashtable<String, List<String>> hash = new Hashtable<String, List<String>>();
	private int maxNameLength = 10;
	private int minNameLength = 5;
	private List<String> names = new ArrayList<String>();
	private int nGraphLength;
	private int persistence = 5;
	private NGraphAbstractRandom random = new NGraphDefaultRandom();
	private Snipper snipper;

	public NGraphs(String input, int nGraphLength) {
		this.nGraphLength = nGraphLength;
		snipper = new Snipper(nGraphLength);

		String[] splitNames = input.split("\\s+");

		for(String name : splitNames) {
			names.add(name);
			parse(name);
		}
	}

	public String generateName() {
		String generatedName = grabRandomStartingSnip();
		String continuation = getContinuation(generatedName);
		while(!continuation.equals(terminator)) {
			generatedName += continuation;
			continuation = getContinuation(generatedName);
		}
		return generatedName;
	}

	public List<String> generateNames(int nameCount) {
		int attemptCount = 0;
		List<String> generatedNames = new ArrayList<String>();
		while(generatedNames.size() < nameCount) {
			String name = generateName().trim();
			if(isValidName(generatedNames, name))
				generatedNames.add(name);
			if(attemptCount > nameCount * persistence) break;
			attemptCount++;
		}
		return generatedNames;
	}

	public String getContinuation(List<String> possibleContinuations) {
		if(possibleContinuations == null) return terminator;
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

	public boolean isValidName(List<String> generatedNames, String name) {
		return !isDisallowedInputEcho(name)
			&& !isDisallowedDuplicate(generatedNames, name)
			&& isInLengthRange(name);
	}

	private boolean isInLengthRange(String name) {
		return (minNameLength <= name.length())
				&& (name.length() <= maxNameLength);
	}

	private boolean isDisallowedDuplicate(List<String> generatedNames, String name) {
		return !allowDuplicates && isDuplicate(generatedNames, name);
	}

	private boolean isDisallowedInputEcho(String name) {
		return !allowInputEcho && isInputEcho(name);
	}

	public void parse(String name) {
		String terminatedName = name + terminator;
		for(int pos = 0 ; pos <= terminatedName.length()-nGraphLength; pos++) {
			String nGraph = snipper.extractNGraph(terminatedName, pos);
			String nGraphStartingSnip = snipper.extractStartingSnip(nGraph);
			List<String> list = hash.get(nGraphStartingSnip);
			if(list == null) {
				list = new ArrayList<String>();
				hash.put(nGraphStartingSnip, list);
			}
			list.add(nGraph.substring(nGraphLength-1));
		}
	}

	private boolean isDuplicate(List<String> generatedNames, String name) {
		return generatedNames.contains(name);
	}

	private boolean isInputEcho(String name) {
		return names.contains(name);
	}
}