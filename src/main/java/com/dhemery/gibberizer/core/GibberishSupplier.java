package com.dhemery.gibberizer.core;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class GibberishSupplier implements Supplier<String> {
    private static final int DEFAULT_LIMIT = 1000;
    private final Supplier<NGram> starterSupplier;
    private final UnaryOperator<NGram> successorOperator;
    private final int limit;

    /**
     * Creates a supplier that generates strings of gibberish
     * from the {@code NGram} supplied by the starter supplier
     * and the successor {@code NGram}s computed by the successor operator,
     * up to a limit of 1000 {@code NGrams}.
     *
     * @param starterSupplier   supplies an {@code NGram} to start the gibberish
     * @param successorOperator computes a successor {@code NGram} to provide the next character of gibberish
     */
    public GibberishSupplier(Supplier<NGram> starterSupplier, UnaryOperator<NGram> successorOperator) {
        this(starterSupplier, successorOperator, DEFAULT_LIMIT);
    }

    /**
     * Creates a supplier that generates strings of gibberish
     * from the {@code NGram} supplied by the starter supplier
     * and the successor {@code NGram}s computed by the successor operator,
     * up to the given {@code limit} of {@code NGram}.
     *
     * @param starterSupplier   supplies an {@code NGram} to start the gibberish
     * @param successorOperator computes a successor {@code NGram} to provide the next character of gibberish
     * @param limit             the maximum number of {@code NGrams} to include in the gibberish
     */
    public GibberishSupplier(Supplier<NGram> starterSupplier, UnaryOperator<NGram> successorOperator, int limit) {
        this.starterSupplier = starterSupplier;
        this.successorOperator = successorOperator;
        this.limit = limit;
    }

    /**
     * Generates a string of gibberish
     * that begins with the the full text
     * of the {@code NGram} supplied by the starter supplier,
     * and is followed by the last character
     * of each successor {@code NGram}
     * computed by the successor operator.
     * <p>
     * The string of gibberish ends
     * when the successor operator returns {@code null},
     * or when the number of included {@code NGram}s
     * reaches the generator's limit.
     * </p>
     *
     * @return the string of gibberish
     */
    @Override
    public String get() {
        NGram starter = starterSupplier.get();
        StringBuilder gibberish = new StringBuilder()
                .append(starter.toString());
        Stream.iterate(successorOperator.apply(starter), successorOperator)
                .limit(limit - 1)
                .takeWhile(Objects::nonNull)
                .map(NGram::lastCharacter)
                .forEach(gibberish::append);
        return gibberish.toString();
    }
}
