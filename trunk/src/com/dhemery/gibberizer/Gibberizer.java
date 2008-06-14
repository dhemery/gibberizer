package com.dhemery.gibberizer;

import java.util.List;

public class Gibberizer {
	private boolean allowInputEcho = false;
	private boolean allowDuplicates = false;
	private final int delimiter = StringSplitter.NONE;
	private final int minStringLength = 0;
	private final int maxStringLength = 0;
	private final int ngramLength = 7;
	private final int numberOfStringsToBuild = 1;
	private final int persistence = 5;
	private final String outputDelimiter = " ";

	private final StringSplitter splitter = new StringSplitter();
	private final StringParser parser = new StringParser();
	private final StringBuilder builder = new StringBuilder();
	private final StringCombiner combiner = new StringCombiner();

	public Gibberizer(GibberizerWindow gibberizerWindow) {
	}

	public String gibberize(String input) {
		StringFilter filter = new StringFilter(minStringLength, maxStringLength);
		StringBasket basket = new StringBasket(numberOfStringsToBuild, filter,
				persistence);

		List<String> inputStrings = splitter.split(input, delimiter);
		if (!allowInputEcho) filter.addProhibitedStringsList(inputStrings);
		if (!allowDuplicates)
			filter.addProhibitedStringsList(basket.getDeliveredStrings());
		List<Ngram> ngrams = parser.parse(inputStrings, ngramLength);
		builder.buildSequences(ngrams, basket);
		return combiner.combine(basket.getDeliveredStrings(), outputDelimiter);
	}
}
