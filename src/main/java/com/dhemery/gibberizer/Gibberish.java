package com.dhemery.gibberizer;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Gibberish {
    private static final int SIZE = 1000;
    private final Supplier<NGram> starterSupplier;
    private final UnaryOperator<NGram> next;

    public Gibberish(Supplier<NGram> starterSupplier, UnaryOperator<NGram> next) {
        this.starterSupplier = starterSupplier;
        this.next = next;
    }

    public String generate() {
        NGram starter = starterSupplier.get();
        StringBuilder gibberish = new StringBuilder()
                .append(starter.prefix());
        Stream.iterate(starter, next)
                .limit(SIZE)
                .takeWhile(Objects::nonNull)
                .map(NGram::lastCharacter)
                .forEach(gibberish::append);
        return gibberish.toString();
    }
}
