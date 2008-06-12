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
		boolean isNameStarter = true;
		for (int pos = 0; pos < ngramCount; pos++) {
			Ngram ngram = new Ngram(ngramLength, name.substring(pos));
			ngram.setIsNameStarter(isNameStarter);
			ngrams.add(ngram);
			isNameStarter = false;
		}
		return ngrams;
	}

	private int getNgramCount(String name) {
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