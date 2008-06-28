package com.dhemery.gibberizer.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NgramBag {
	private final HashMap<String, List<Ngram>> ngramsByPrefix = new HashMap<String, List<Ngram>>();
	private final List<Ngram> ngrams = new ArrayList<Ngram>();
	private final List<Ngram> starters = new ArrayList<Ngram>();
	private final List<Ngram> enders = new ArrayList<Ngram>();

	public void add(Ngram ngram, boolean isStarter, boolean isEnder) {
		ngrams.add(ngram);
		getNgramsByPrefix(ngram.getPrefix()).add(ngram);
		if(isStarter) starters.add(ngram);
		if(isEnder) enders.add(ngram);
	}

	// TODO: Kill this method.  It's used only by tests.
	public List<Ngram> getNgrams() {
		return ngrams;
	}

	public List<Ngram> getNgramsByPrefix(String prefix) {
		List<Ngram> ngramsForPrefix = ngramsByPrefix.get(prefix);
		if (ngramsForPrefix == null) {
			ngramsForPrefix = new ArrayList<Ngram>();
			ngramsByPrefix.put(prefix, ngramsForPrefix);
		}
		return ngramsForPrefix;
	}

	public List<Ngram> getStarters() {
		return starters;
	}

	public boolean isEnder(Ngram ngram) {
		return enders.contains(ngram);
	}
}
