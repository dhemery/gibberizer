package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NameGenerator {
	private final List<Ngram> nameStarters = new ArrayList<Ngram>();
	private final AbstractRandom random = new DefaultRandom();
	private final Hashtable<String, List<Ngram>> successorsByPrefix = new Hashtable<String, List<Ngram>>();

	public NameGenerator() {
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

	public String generateName() {
		Ngram ngram = selectRandomNameStarter();
		String generatedName = ngram.getPrefix();
		while (canContinueAfter(ngram)) {
			generatedName += ngram.getLastCharacter();
			ngram = selectRandomSuccessor(ngram);
		}
		return generatedName;
	}

	public List<String> generateNames(List<Ngram> ngrams, NameValidator validator, int nameCount) {
		NameAccumulator accumulator = new NameAccumulator(validator, nameCount);

		for (Ngram ngram : ngrams) {
			if (ngram.isNameStarter()) nameStarters.add(ngram);
			else addToSuccessorList(ngram);
		}

		while (!accumulator.isDone()) {
			String name = generateName();
			accumulator.add(name);
		}
		return accumulator.getAccumulatedNames();
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