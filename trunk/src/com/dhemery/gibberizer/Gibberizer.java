package com.dhemery.gibberizer;

import java.util.List;

import com.dhemery.gibberizer.StringJoiner.JoinStyle;
import com.dhemery.gibberizer.StringSplitter.SplitStyle;

public class Gibberizer {
	private boolean allowInputEcho = false;
	private boolean allowDuplicates = false;
	private JoinStyle joinStyle;
	private final int minStringLength = 1;
	private final int maxStringLength = 100000;
	private final int ngramLength = 3;
	private final int numberOfStringsToBuild = 10;
	private final int persistence = 5;
	private SplitStyle splitStyle;

	private final StringSplitter splitter = new StringSplitter();
	private final NgramExtractor extractor = new NgramExtractor();
	private final NgramJoiner builder = new NgramJoiner();
	private final StringJoiner joiner = new StringJoiner();

	public String gibberize(String input) {
		StringFilter filter = new StringFilter(minStringLength, maxStringLength);
		StringBasket basket = new StringBasket(numberOfStringsToBuild, filter,
				persistence);
		basket.deliver("Input JoinStyle: " + splitStyle);
		basket.deliver("Output JoinStyle: " + joinStyle);

		List<String> inputStrings = splitter.split(input, splitStyle);
		if (!allowInputEcho) filter.addProhibitedStringsList(inputStrings);
		if (!allowDuplicates)
			filter.addProhibitedStringsList(basket.getDeliveredStrings());
		if(!inputStrings.isEmpty()) {
			NgramBag ngramBag = extractor.extract(inputStrings, ngramLength);
			builder.buildSequences(ngramBag, basket);
		}
		return joiner.combine(basket.getDeliveredStrings(),
				joinStyle);
	}

	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
	}

	public void setAllowInputEcho(boolean allowInputEcho) {
		this.allowInputEcho = allowInputEcho;
	}

	public void setSplitStyle(SplitStyle newSplitStyle) {
		splitStyle = newSplitStyle;
	}

	public void setJoinStyle(JoinStyle newJoinStyle) {
		joinStyle = newJoinStyle;
	}
}
