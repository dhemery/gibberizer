package com.dhemery.gibberizer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

class SelectNextNGram implements UnaryOperator<NGram> {
    private final Function<NGram, String> classifier;
    private final Map<String, List<NGram>> nGramsGroupedByString;
    private final Function<List<NGram>, NGram> selector;

    public SelectNextNGram(List<NGram> nGrams, Function<List<NGram>, NGram> selector) {
        this(nGrams, Object::toString, selector);
    }

    public SelectNextNGram(List<NGram> nGrams, Function<NGram,String> classifier, Function<List<NGram>, NGram> selector) {
        this.selector = selector;
        this.classifier = classifier;
        nGramsGroupedByString = nGrams.stream().collect(groupingBy(classifier));
    }

    @Override
    public NGram apply(NGram nGram) {
        return classifier
                .andThen(nGramsGroupedByString::get)
                .andThen(selector)
                .andThen(NGram::nextNGram)
                .apply(nGram)
                .orElse(null);
    }
}
