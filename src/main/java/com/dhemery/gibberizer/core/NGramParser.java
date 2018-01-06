package com.dhemery.gibberizer.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NGramParser {
    private final int size;

    public NGramParser(int size) {
        this.size = size;
    }

    public List<NGram> parse(String... strings) {
        return parse(Arrays.asList(strings));
    }

    public List<NGram> parse(Collection<String> strings) {
        return strings.stream()
                .filter(s -> s.length() >= size)
                .flatMap(this::parse)
                .collect(toList());
    }

    private Stream<NGram> parse(String string) {
        return IntStream.rangeClosed(0, string.length() - size)
                .mapToObj(i -> NGram.fromSubstring(string, i, size));
    }
}
