package com.dhemery.gibberizer;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NGramStream {
    private final int size;

    public NGramStream(int size) {
        this.size = size;
    }

    public Stream<NGram> of(String... strings) {
        return Arrays.stream(strings).flatMap(this::of);
    }

    public Stream<NGram> of(String string) {
        return IntStream.rangeClosed(0, string.length() - size)
                .mapToObj(i -> new PositionedNGram(string, i, size));
    }
}
