package com.dhemery.gibberizer.core;

import java.util.List;


public class Gibberizer {
	public static final int MAX_BATCH_SIZE = 1000;
	public static final int MAX_PERSISTENCE = 20;
	public static final int MAX_SIMILARITY = 20;

	private static final String warning = 
		"*************************************************\n" +
		"Could not create acceptable gibberish.\n\n" +
		"Some things you can try:\n" +
		"   - Allow input echo.\n" +
		"   - Allow duplicates.\n" +
		"   - Decrease similarity.\n" +
		"   - Increase persistence.\n" +
		"   - Increase the batch size.\n" +
		"   - Add more input text.\n" +
		"   - Run again with the same parameters.\n" +
		"*************************************************";

	private final int minStringLength = 1;
	private final int maxStringLength = 100000;

	private boolean allowInputEcho = false;
	private boolean allowDuplicates = false;
	private JoinStyle joinStyle = JoinStyle.NEW_LINE;
	private int ngramLength = 3;
	private int batchSize = 10;
	private int persistence = 5;
	private SplitStyle splitStyle = SplitStyle.WORDS;

	private final StringSplitter splitter = new StringSplitter();
	private final NgramExtractor extractor = new NgramExtractor();
	private final NgramJoiner generator = new NgramJoiner();
	private final StringJoiner joiner = new StringJoiner();

	public boolean getAllowDuplicates() {
		return allowDuplicates;
	}

	public boolean getAllowInputEcho() {
		return allowInputEcho;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public JoinStyle getJoinStyle() {
		return joinStyle;
	}

	public int getPersistence() {
		return persistence;
	}

	public int getSimilarity() {
		return ngramLength;
	}

	public SplitStyle getSplitStyle() {
		return splitStyle;
	}

	public String gibberize(String input) {
		StringFilter filter = new StringFilter(minStringLength, maxStringLength);
		StringBasket basket = new StringBasket(batchSize, filter, persistence);

		List<String> inputStrings = splitter.split(input, splitStyle);
		if (!allowInputEcho) filter.addProhibitedStringsList(inputStrings);
		if (!allowDuplicates)
			filter.addProhibitedStringsList(basket.getDeliveredStrings());
		if(!inputStrings.isEmpty()) {
			NgramBag ngramBag = extractor.extract(inputStrings, ngramLength);
			generator.createGibs(ngramBag, basket);
		}
		List<String> gibs = basket.getDeliveredStrings();
		if(gibs.isEmpty()) {
			return warning;
		}
		else {
			return joiner.combine(gibs, joinStyle);
		}
	}

	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	public void setAllowInputEcho(boolean allowInputEcho) {
		this.allowInputEcho = allowInputEcho;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setJoinStyle(JoinStyle newJoinStyle) {
		joinStyle = newJoinStyle;
	}

	public void setPersistence(int persistence) {
		this.persistence = persistence;
	}

	public void setSimilarity(int ngramLength) {
		this.ngramLength = ngramLength;
	}

	public void setSplitStyle(SplitStyle newSplitStyle) {
		splitStyle = newSplitStyle;
	}
}
