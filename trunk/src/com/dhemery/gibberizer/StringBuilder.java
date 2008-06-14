package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class StringBuilder {
	private final Hashtable<String, List<Ngram>> ngramsByPrefix = new Hashtable<String, List<Ngram>>();
	private final AbstractRandom random = new DefaultRandom();
	private final List<Ngram> starters = new ArrayList<Ngram>();

	private void addToNgramsByPrefix(Ngram ngram) {
		String prefix = ngram.getPrefix();
		List<Ngram> ngramsForPrefix = getNgramsForPrefix(prefix);
		ngramsForPrefix.add(ngram);
	}

	public void buildSequences(List<Ngram> ngrams, StringBasket basket) {
		distributeNgramsToStarterAndSuccessorLists(ngrams);
		while (!basket.isDone())
			basket.deliver(buildString());
	}

	public String buildString() {
		Ngram ngram = selectRandomStarter();
		String generatedString = ngram.getPrefix();
		while (!ngram.isEnder()) {
			generatedString += ngram.getLastCharacter();
			ngram = selectRandomSuccessor(ngram);
		}
		return generatedString + ngram.getLastCharacter();
	}

	private void distributeNgramsToStarterAndSuccessorLists(List<Ngram> ngrams) {
		for (Ngram ngram : ngrams) {
			addToNgramsByPrefix(ngram);
			if (ngram.isStarter()) starters.add(ngram);
		}
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

	private Ngram selectRandomNgram(List<Ngram> ngrams) {
		if (ngrams.size() < 1) return Ngram.NULL_NGRAM;
		int randomIndex = random.nextInt(ngrams.size());
		return ngrams.get(randomIndex);
	}

	private Ngram selectRandomStarter() {
		return selectRandomNgram(starters);
	}

	private Ngram selectRandomSuccessor(Ngram ngram) {
		return selectRandomNgram(getPossibleSuccessors(ngram));
	}
}
