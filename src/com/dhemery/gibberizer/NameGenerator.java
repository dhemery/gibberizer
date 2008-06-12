package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NameGenerator {
	private final List<Ngram> nameStarters = new ArrayList<Ngram>();
	private final AbstractRandom random = new DefaultRandom();
	private final Hashtable<String, List<Ngram>> successorsByPrefix = new Hashtable<String, List<Ngram>>();

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

	public String generateName() {
		Ngram ngram = selectRandomNameStarter();
		String generatedName = ngram.getPrefix();
		while (canContinueAfter(ngram)) {
			generatedName += ngram.getLastCharacter();
			ngram = selectRandomSuccessor(ngram);
		}
		return generatedName;
	}

	public List<String> generateNames(List<String> incomingNames, int ngramLength, int targetCount) {
		NameAccumulator accumulator = new NameAccumulator(incomingNames, targetCount);

		List<Ngram> ngrams = getNgrams(incomingNames, ngramLength);

		for (Ngram ngram : ngrams) {
			if (ngram.isNameStarter()) nameStarters.add(ngram);
			else addToSuccessorList(ngram);
		}
		while (!accumulator.isDone()) {
			accumulator.add(generateName());
		}
		return accumulator.getAccumulatedNames();
	}

	private List<Ngram> getNgrams(List<String> incomingNames, int ngramLength) {
		NameAnalyzer analyzer = new NameAnalyzer(ngramLength);
		return analyzer.getNgrams(incomingNames);
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