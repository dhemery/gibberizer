package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static com.dhemery.gibberizer.testutil.NGrams.nGramsEndingWith;
import static com.dhemery.gibberizer.testutil.NGrams.sequenceOf;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

public class GibberizerTest {
    private final NGram starter = new PositionedNGram("012345678", 0, 3);

    @Test
    public void equalToStarterStringIfNextYieldsNull() {
        Gibberish gibberish = new Gibberish(() -> starter, n -> null);

        assertThat(gibberish.generate()).isEqualTo(starter.toString());
    }

    @Test
    public void appendsLastCharacterFromEachNGramYieldedByNext() {
        Character[] lastCharacters = {'u', 'v', 'w', 'x', 'y', 'z'};
        String tail = Stream.of(lastCharacters).map(String::valueOf).collect(joining());
        String expected = starter.toString() + tail;

        Gibberish gibberish = new Gibberish(() -> starter, sequenceOf(nGramsEndingWith(lastCharacters)));

        assertThat(gibberish.generate()).isEqualTo(expected);
    }
}
