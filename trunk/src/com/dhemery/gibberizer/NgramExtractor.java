package com.dhemery.gibberizer;

import java.util.List;

public class NgramExtractor {

	private int getNgramCount(String string, int ngramLength) {
		if (string.length() < ngramLength) return 1;
		return string.length() - ngramLength + 1;
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

	public NgramBag extract(List<String> strings, int ngramLength) {
		NgramBag ngramBag = new NgramBag();
		for (String string : strings)
			extract(string, ngramLength, ngramBag);
		return ngramBag;
	}
}