package com.dhemery.gibberizer;

import java.util.List;

public class Gibberizer {
	private boolean allowInputEcho = false;
	private boolean allowDuplicates = false;
	private int inputDelimiterStyle;
	private final int minStringLength = 1;
	private final int maxStringLength = 100000;
	private final int ngramLength = 3;
	private final int numberOfStringsToBuild = 10;
	private final int persistence = 5;
	private int outputDelimiterStyle;

	private final StringSplitter splitter = new StringSplitter();
	private final StringParser parser = new StringParser();
	private final StringBuilder builder = new StringBuilder();
	private final StringCombiner combiner = new StringCombiner();

	public Gibberizer() {
	}

	public String gibberize(String input) {
		StringFilter filter = new StringFilter(minStringLength, maxStringLength);
		StringBasket basket = new StringBasket(numberOfStringsToBuild, filter,
				persistence);
		basket.deliver("Input Delimiter: " + inputDelimiterStyle);
		basket.deliver("Output Delimiter: " + outputDelimiterStyle);

		List<String> inputStrings = splitter.split(input, inputDelimiterStyle);
		if (!allowInputEcho) filter.addProhibitedStringsList(inputStrings);
		if (!allowDuplicates)
			filter.addProhibitedStringsList(basket.getDeliveredStrings());
		if(!inputStrings.isEmpty()) {
			List<Ngram> ngrams = parser.parse(inputStrings, ngramLength);
			builder.buildSequences(ngrams, basket);
		}
		return combiner.combine(basket.getDeliveredStrings(),
				outputDelimiterStyle);
	}

	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	public void setAllowInputEcho(boolean allowInputEcho) {
		this.allowInputEcho = allowInputEcho;
	}

	public void setInputDelimiterStyle(int newInputDelimiterStyle) {
		inputDelimiterStyle = newInputDelimiterStyle;
	}

	public void setOutputDelimiterStyle(int newOutputDelimiterStyle) {
		outputDelimiterStyle = newOutputDelimiterStyle;
	}
}
