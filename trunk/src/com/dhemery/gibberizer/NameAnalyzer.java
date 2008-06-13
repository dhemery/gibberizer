package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class NameAnalyzer {
	private int ngramLength;

	public NameAnalyzer(int ngramLength) {
		this.ngramLength = ngramLength;
	}

	private List<Ngram> getNgrams(String name) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		int ngramCount = getNgramCount(name);
		
		for (int pos = 0; pos < ngramCount; pos++) {
			ngrams.add(new Ngram(ngramLength, name.substring(pos)));
		}

		ngrams.get(0).setIsNameStarter(true);
		ngrams.get(ngramCount - 1).setIsNameEnder(true);
		return ngrams;
	}

	private int getNgramCount(String name) {
		if(name.length() < ngramLength) return 1;
		return name.length() - ngramLength + 1;
	}

	public List<Ngram> getNgrams(List<String> names) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		for (String name : names) {
			ngrams.addAll(getNgrams(name));
		}
		return ngrams;
	}
}