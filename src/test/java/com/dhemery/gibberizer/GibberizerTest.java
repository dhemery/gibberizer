package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class GibberizerTest {
    private final NGram starter = new NGram("012345678", 0, 3);

    @Test
    public void equalToStarterStringIfNextYieldsNull() {
        Gibberish gibberish = new Gibberish(() -> starter, n -> null);

        assertThat(gibberish.generate()).isEqualTo(starter.toString());
    }

    @Test
    public void appendsLastCharacterFromEachNGramYieldedByNext() {
        String tail = "uvwxyz";

        String expectedGibberish = starter.toString() + String.join("", tail);

        Gibberish gibberish = new Gibberish(() -> starter, next(tail));

        assertThat(gibberish.generate()).isEqualTo(expectedGibberish);
    }

    private UnaryOperator<NGram> next(String lastCharacters) {
        List<NGram> nGrams = Arrays.stream(lastCharacters.split(""))
                .map(s -> new NGram(s, 0, 1))
                .collect(toList());
        nGrams.add(null);
        Iterator<NGram> each = nGrams.iterator();
        return s -> each.next();
    }
}
