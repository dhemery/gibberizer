package com.dhemery.gibberizer.core;

import java.util.List;

public class NgramExtractor {

	public NgramBag extract(List<String> strings, int ngramLength) {
		NgramBag ngramBag = new NgramBag();
		for (String string : strings)
			extract(string, ngramLength, ngramBag);
		return ngramBag;
	}

	private NgramBag extract(String string, int ngramLength, NgramBag ngramBag) {
		int ngramCount = getNgramCount(string, ngramLength);

		for (int pos = 0; pos < ngramCount; pos++) {
			Ngram ngram = new Ngram(string.substring(pos), ngramLength);
			boolean isStarter = (pos == 0);
			boolean isEnder = (pos == (ngramCount - 1));
			ngramBag.add(ngram, isStarter, isEnder);
		}
		return ngramBag;
	}

	private int getNgramCount(String string, int ngramLength) {
		if (string.length() < ngramLength) return 1;
		return string.length() - ngramLength + 1;
	}
}