package com.dhemery.gibberizer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;

class SelectNextNGram implements Function<NGram, Optional<NGram>> {
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
    public Optional<NGram> apply(NGram nGram) {
        return Optional.of(nGram)
                .map(classifier)
                .map(nGramsGroupedByString::get)
                .map(selector)
                .flatMap(NGram::nextNGram);
    }
}
