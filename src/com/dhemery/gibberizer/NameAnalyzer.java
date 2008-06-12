package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.List;

public class NameAnalyzer {
	private final List<Ngram> ngrams = new ArrayList<Ngram>();
	private final List<String> names = new ArrayList<String>();
	private final int ngramLength;

	public NameAnalyzer(String input, int ngramLength) {
		this.ngramLength = ngramLength;

		for (String name : splitOnWhiteSpace(input)) {
			names.add(name);
			analyzeNgrams(name);
		}
	}

	public void analyzeNgrams(String name) {
		int ngramCount = getNgramCount(name);
		for (int pos = 0; pos < ngramCount; pos++) {
			Ngram ngram = new Ngram(ngramLength, name.substring(pos));
			if (pos == 0) ngram.setIsNameStarter(true);
			ngrams.add(ngram);
		}
	}

	public List<String> getNames() {
		return names;
	}

	private int getNgramCount(String name) {
		return name.length() - ngramLength + 1;
	}

	public List<Ngram> getNgrams() {
		return ngrams;
	}

	private String[] splitOnWhiteSpace(String input) {
		return input.split("\\s+");
	}
}