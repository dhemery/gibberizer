package com.dhemery.gibberizer.core;

import java.util.List;
import java.util.Random;

public class NgramJoiner {
	private final Random random = new Random();

	public String createGib(NgramBag ngramBag) {
		Ngram ngram = selectRandomStarter(ngramBag);
		String gib = ngram.getPrefix();
		while (!ngramBag.isEnder(ngram)) {
			gib += ngram.getLastCharacter();
			ngram = selectRandomSuccessor(ngram, ngramBag);
		}
		return gib + ngram.getLastCharacter();
	}

	public void createGibs(NgramBag ngramBag, StringBasket basket) {
		while (!basket.isDone())
			basket.deliver(createGib(ngramBag));
	}

	private List<Ngram> getPossibleSuccessors(Ngram ngram, NgramBag ngramBag) {
		String successorPrefix = ngram.getSuffix();
		return ngramBag.getNgramsByPrefix(successorPrefix);
	}

	protected int getRandomInt(int range) {
		return random.nextInt(range);
	}

	private Ngram selectRandomNgram(List<Ngram> ngrams) {
		if (ngrams.size() < 1) return Ngram.NULL_NGRAM;
		int randomIndex = getRandomInt(ngrams.size());
		return ngrams.get(randomIndex);
	}

	private Ngram selectRandomStarter(NgramBag ngramBag) {
		return selectRandomNgram(ngramBag.getStarters());
	}

	private Ngram selectRandomSuccessor(Ngram ngram, NgramBag ngramBag) {
		return selectRandomNgram(getPossibleSuccessors(ngram, ngramBag));
	}
}
