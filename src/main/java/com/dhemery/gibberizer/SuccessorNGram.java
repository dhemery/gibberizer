package com.dhemery.gibberizer;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.groupingBy;

class SuccessorNGram implements UnaryOperator<NGram> {
    private final Function<NGram, String> successorKey;
    private final Map<String, List<NGram>> successorsByKey;
    private final Function<List<NGram>, NGram> selector;

    public SuccessorNGram(Function<NGram, String> successorKey, Map<String, List<NGram>> successorsByKey, Function<List<NGram>, NGram> selector) {
        this.successorKey = successorKey;
        this.successorsByKey = successorsByKey;
        this.selector = selector;
    }

    @Override
    public NGram apply(NGram predecessor) {
        return successorKey
                .andThen(successorsByKey::get)
                .andThen(selector)
                .apply(predecessor);
    }
}
