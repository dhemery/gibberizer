package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NgramBag {
	private final Map<String, List<Ngram>> ngramsByPrefix = new HashMap<String, List<Ngram>>();
	private final List<Ngram> starters = new ArrayList<Ngram>();
	private final List<Ngram> enders = new ArrayList<Ngram>();

	public void add(Ngram ngram, boolean isStarter, boolean isEnder) {
		getNgramsForPrefix(ngram.getPrefix()).add(ngram);
		if(isStarter) starters.add(ngram);
		if(isEnder) enders.add(ngram);
	}

	public List<Ngram> getByPrefix(String prefix) {
		return ngramsByPrefix.get(prefix);
	}

	private List<Ngram> getNgramsForPrefix(String prefix) {
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
}
