package com.dhemery.gibberizer;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class NGramStream {
    public static Stream<NGram> of(int size, String... strings) {
        return Arrays.stream(strings)
                .flatMap(s -> startingWith(new NGram(s, 0, size)));
    }

    public static Stream<NGram> startingWith(NGram startNGram) {
        return Stream.iterate(Optional.of(startNGram), n -> n.flatMap(NGram::nextNGram))
                .takeWhile(Optional::isPresent)
                .map(Optional::get);
    }
}