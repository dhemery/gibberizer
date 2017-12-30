package com.dhemery.gibberizer;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class Gibberish {
    private static final int SIZE = 1000;
    private static final Random RANDOM = new Random();
    private final List<NGram> startNGrams = new ArrayList<>();
    private final Map<String, List<NGram>> nGramsGroupedByStringValue;

    public Gibberish(int size, String text) {
        NGram startNGram = new NGram(text, 0, size);
        startNGrams.add(startNGram);
        nGramsGroupedByStringValue = NGramStream.of(size, text)
                .collect(groupingBy(NGram::toString));
    }

    public String generate() {
        NGram startNGram = selectRandom(startNGrams);
        return Stream.iterate(Optional.of(startNGram), n -> n.flatMap(this::randomNextNGram))
                .limit(SIZE)
                .takeWhile(Optional::isPresent)
                .map(Optional::get)
                .map(NGram::lastCharacter)
                .collect(joining("", startNGram.prefix(), ""));
    }

    private Optional<NGram> randomNextNGram(NGram nGram) {
        List<NGram> candidates = nGramsGroupedByStringValue.get(nGram.toString());
        return selectRandom(candidates).nextNGram();
    }

    private static NGram selectRandom(List<NGram> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
