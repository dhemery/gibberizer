package com.dhemery.gibberizer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Gibberish {
    private static final int SIZE = 1000;
    private static final Random RANDOM = new Random();
    private final List<NGram> startNGrams;
    private final Function<NGram, Optional<NGram>> next;

    public Gibberish(int size, String... strings) {
        this(new NGramStream(size).of(strings).collect(toList()));
    }

    public Gibberish(Function<String, Stream<NGram>> nGramizer, String... strings) {
        this(Arrays.stream(strings).flatMap(nGramizer).collect(toList()));
    }

    public Gibberish(List<NGram> nGrams) {
        this(nGrams.stream().filter(NGram::isStartNGram).collect(toList()), new SelectNextNGram(nGrams, Gibberish::selectRandom));
    }

    public Gibberish(List<NGram> startNGrams, Function<NGram, Optional<NGram>> next) {
        this.startNGrams = startNGrams;
        this.next = next;
    }

    public String generate() {
        NGram startNGram = selectRandom(startNGrams);
        return Stream.iterate(Optional.of(startNGram), n -> n.flatMap(next))
                .limit(SIZE)
                .takeWhile(Optional::isPresent)
                .map(Optional::get)
                .map(NGram::lastCharacter)
                .collect(joining("", startNGram.prefix(), ""));
    }

    private static NGram selectRandom(List<NGram> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

}