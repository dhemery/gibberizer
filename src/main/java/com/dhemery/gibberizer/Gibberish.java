package com.dhemery.gibberizer;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Gibberish implements Supplier<String> {
    private static final int DEFAULT_LIMIT = 1000;
    private final Supplier<NGram> starterSupplier;
    private final UnaryOperator<NGram> successor;
    private final int limit;

    public Gibberish(Supplier<NGram> starterSupplier, UnaryOperator<NGram> successor) {
        this(starterSupplier, successor, DEFAULT_LIMIT);
    }

    public Gibberish(Supplier<NGram> starterSupplier, UnaryOperator<NGram> successor, int limit) {
        this.starterSupplier = starterSupplier;
        this.successor = successor;
        this.limit = limit;
    }

    @Override
    public String get() {
        NGram starter = starterSupplier.get();
        StringBuilder gibberish = new StringBuilder()
                .append(starter.prefix());
        Stream.iterate(starter, successor)
                .limit(limit)
                .takeWhile(Objects::nonNull)
                .map(NGram::lastCharacter)
                .forEach(gibberish::append);
        return gibberish.toString();
    }
}
