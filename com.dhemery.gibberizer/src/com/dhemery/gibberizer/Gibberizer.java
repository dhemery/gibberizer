package com.dhemery.gibberizer;

import java.util.List;

import com.dhemery.gibberizer.StringJoiner.JoinStyle;
import com.dhemery.gibberizer.StringSplitter.SplitStyle;

public class Gibberizer {
	private final int minStringLength = 1;
	private final int maxStringLength = 100000;

	private boolean allowInputEcho = false;
	private boolean allowDuplicates = false;
	private JoinStyle joinStyle = JoinStyle.LINE_BREAK;
	private int ngramLength = 3;
	private int numberOfStringsToBuild = 10;
	private int persistence = 5;
	private SplitStyle splitStyle = SplitStyle.WORDS;

	private final StringSplitter splitter = new StringSplitter();
	private final NgramExtractor extractor = new NgramExtractor();
	private final NgramJoiner builder = new NgramJoiner();
	private final StringJoiner joiner = new StringJoiner();

	public boolean getAllowDuplicates() {
		return allowDuplicates;
	}

	public boolean getAllowInputEcho() {
		return allowInputEcho;
	}

	public JoinStyle getJoinStyle() {
		return joinStyle;
	}

	public int getNgramLength() {
		return ngramLength;
	}

	public int getNumberOfStringsToBuild() {
		return numberOfStringsToBuild;
	}

	public int getPersistence() {
		return persistence;
	}

	public SplitStyle getSplitStyle() {
		return splitStyle;
	}

	public String gibberize(String input) {
		StringFilter filter = new StringFilter(minStringLength, maxStringLength);
		StringBasket basket = new StringBasket(numberOfStringsToBuild, filter,
				persistence);

		List<String> inputStrings = splitter.split(input, splitStyle);
		if (!allowInputEcho) filter.addProhibitedStringsList(inputStrings);
		if (!allowDuplicates)
			filter.addProhibitedStringsList(basket.getDeliveredStrings());
		if(!inputStrings.isEmpty()) {
			NgramBag ngramBag = extractor.extract(inputStrings, ngramLength);
			builder.buildSequences(ngramBag, basket);
		}
		return joiner.combine(basket.getDeliveredStrings(),
				getJoinStyle());
	}

	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	public void setAllowInputEcho(boolean allowInputEcho) {
		this.allowInputEcho = allowInputEcho;
	}

	public void setJoinStyle(JoinStyle newJoinStyle) {
		joinStyle = newJoinStyle;
	}

	public void setNgramLength(int ngramLength) {
		this.ngramLength = ngramLength;
	}

	public void setNumberOfStringsToBuild(int numberOfStringsToBuild) {
		this.numberOfStringsToBuild = numberOfStringsToBuild;
	}

	public void setPersistence(int persistence) {
		this.persistence = persistence;
	}

	public void setSplitStyle(SplitStyle newSplitStyle) {
		splitStyle = newSplitStyle;
	}
}
