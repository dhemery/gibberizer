package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class NgramExtractor {

	private int getNgramCount(String string, int ngramLength) {
		if (string.length() < ngramLength) return 1;
		return string.length() - ngramLength + 1;
	}

	private List<Ngram> getNgrams(String string, int ngramLength) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		int ngramCount = getNgramCount(string, ngramLength);

		for (int pos = 0; pos < ngramCount; pos++)
			ngrams.add(new Ngram(string.substring(pos), ngramLength));

		ngrams.get(0).setIsStarter(true);
		ngrams.get(ngrams.size() - 1).setIsEnder(true);
		return ngrams;
	}

	public List<Ngram> parse(List<String> strings, int ngramLength) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		for (String string : strings)
			ngrams.addAll(getNgrams(string, ngramLength));
		return ngrams;
	}
}