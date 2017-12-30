package com.dhemery.gibberizer;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class NGramStream {
    private final int size;

    public NGramStream(int size) {
        this.size = size;
    }

    public Stream<NGram> of(String... strings) {
        return Arrays.stream(strings)
                .flatMap(s -> startingWith(new NGram(s, 0, size)));
    }

    public static Stream<NGram> of(int size, String... strings) {
        return new NGramStream(size).of(strings);
    }

    public static Stream<NGram> startingWith(NGram startNGram) {
        return Stream.iterate(Optional.of(startNGram), n -> n.flatMap(NGram::nextNGram))
                .takeWhile(Optional::isPresent)
                .map(Optional::get);
    }
}