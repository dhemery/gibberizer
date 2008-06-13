package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class StringParser {
	private int ngramLength;

	public StringParser(int ngramLength) {
		this.ngramLength = ngramLength;
	}

	private List<Ngram> getNgrams(String string) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		int ngramCount = getNgramCount(string);

		for (int pos = 0; pos < ngramCount; pos++) {
			ngrams.add(new Ngram(ngramLength, string.substring(pos)));
		}

		ngrams.get(0).setIsStarter(true);
		ngrams.get(ngramCount - 1).setIsEnder(true);
		return ngrams;
	}

	private int getNgramCount(String string) {
		if(string.length() < ngramLength) return 1;
		return string.length() - ngramLength + 1;
	}

	public List<Ngram> parseNgrams(List<String> strings) {
		List<Ngram> ngrams = new ArrayList<Ngram>();
		for (String string : strings) {
			ngrams.addAll(getNgrams(string));
		}
		return ngrams;
	}
}