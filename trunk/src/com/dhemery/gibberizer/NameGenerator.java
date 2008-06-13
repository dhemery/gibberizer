package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NameGenerator {
	private final List<Ngram> nameStarters = new ArrayList<Ngram>();
	private final AbstractRandom random = new DefaultRandom();
	private final Hashtable<String, List<Ngram>> ngramsByPrefix = new Hashtable<String, List<Ngram>>();

	public NameGenerator() {
	}

	private void addToNgramsByPrefix(Ngram ngram) {
		String prefix = ngram.getPrefix();
		List<Ngram> ngramsForPrefix = getNgramsForPrefix(prefix);
		ngramsForPrefix.add(ngram);
	}

	private void distributeNgramsToStarterAndSuccessorLists(List<Ngram> ngrams) {
		// TODO: This distribution might better be done elsewhere. But where?
		for (Ngram ngram : ngrams) {
			if (ngram.isNameStarter()) nameStarters.add(ngram);
			addToNgramsByPrefix(ngram);
		}
	}

	public String generateName() {
		Ngram ngram = selectRandomNameStarter();
		String generatedName = ngram.getPrefix();
		while (!ngram.isNameEnder()) {
			generatedName += ngram.getLastCharacter();
			ngram = selectRandomSuccessor(ngram);
		}
		return generatedName + ngram.getLastCharacter();
	}

	public List<String> generateNames(List<Ngram> ngrams,
			NameValidator validator, int nameCount) {
		distributeNgramsToStarterAndSuccessorLists(ngrams);
		return generateNames(validator, nameCount);
	}

	private List<String> generateNames(NameValidator validator, int nameCount) {
		NameAccumulator accumulator = new NameAccumulator(validator, nameCount);
		while (!accumulator.isDone()) {
			String name = generateName();
			accumulator.add(name);
		}
		return accumulator.getAccumulatedNames();
	}

	public List<Ngram> getNgramsForPrefix(String prefix) {
		List<Ngram> ngramsForPrefix = ngramsByPrefix.get(prefix);
		if (ngramsForPrefix == null) {
			ngramsForPrefix = new ArrayList<Ngram>();
			ngramsByPrefix.put(prefix, ngramsForPrefix);
		}
		return ngramsForPrefix;
	}

	private List<Ngram> getPossibleSuccessors(Ngram ngram) {
		String successorPrefix = ngram.getSuffix();
		return getNgramsForPrefix(successorPrefix);
	}

	private Ngram selectRandomNameStarter() {
		return selectRandomNgram(nameStarters);
	}

	private Ngram selectRandomNgram(List<Ngram> ngrams) {
		if(ngrams.size() < 1) return Ngram.NULL_NGRAM;
		int randomIndex = random.nextInt(ngrams.size());
		return ngrams.get(randomIndex);
	}

	private Ngram selectRandomSuccessor(Ngram ngram) {
		return selectRandomNgram(getPossibleSuccessors(ngram));
	}
}
