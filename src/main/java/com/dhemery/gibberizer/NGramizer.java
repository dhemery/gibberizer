package com.dhemery.gibberizer;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class NGramizer implements BiFunction<String, Integer, List<String>> {
    @Override
    public List<String> apply(String text, Integer nGramSize) {
        return IntStream.rangeClosed(0, text.length() - nGramSize)
                .mapToObj(i -> text.substring(i, i + nGramSize))
                .collect(toList());
    }
}
