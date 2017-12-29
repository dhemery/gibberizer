package com.dhemery.gibberizer;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class Gibberish implements Supplier<String> {
    private static final int size = 1000;
    private static final Random random = new Random();
    private final int overlap;
    private final String text;
    private final Map<String, List<Integer>> positionsByPrefix;

    public Gibberish(String text, int overlap) {
        this.text = text;
        this.overlap = overlap;
        positionsByPrefix = IntStream.rangeClosed(overlap, text.length())
                .boxed()
                .collect(groupingBy(this::prefixOf));
    }

    private String prefixOf(int i) {
        return text.substring(i - overlap, i);
    }

    private String suffixOf(int i) {
        return text.substring(i, i + 1);
    }

    @Override
    public String get() {
        return positionStream()
                .limit(size)
                .mapToObj(this::suffixOf)
                .collect(joining("", prefixOf(overlap), ""));
    }

    private IntStream positionStream() {
        return IntStream.iterate(overlap, this::hasNext, this::next);
    }

    private int next(int i) {
        String nextPrefix = prefixOf(i + 1);
        List<Integer> positions = positionsByPrefix.get(nextPrefix);
        int selected = random.nextInt(positions.size());
        return positions.get(selected);
    }

    private boolean hasNext(int i) {
        return i < text.length();
    }
}
