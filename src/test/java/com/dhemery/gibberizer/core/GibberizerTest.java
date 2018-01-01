package com.dhemery.gibberizer.core;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.dhemery.gibberizer.testutil.NGrams.sequenceOf;
import static com.google.common.truth.Truth.assertThat;
import static java.util.stream.Collectors.joining;

public class GibberizerTest {
    private final NGram starter = new NGram("abc", true, false);

    @Test
    public void textOfStarterIfNoSuccessors() {
        Gibberish gibberish = new Gibberish(() -> starter, n -> null);

        assertThat(gibberish.get()).isEqualTo(starter.toString());
    }

    @Test
    public void textOfStarterFollowedByLastCharacterOfEachSuccessor() {
        List<NGram> successors = List.of(
                new NGram("--u"),
                new NGram("--v"),
                new NGram("--w"),
                new NGram("--x"),
                new NGram("--y"),
                new NGram("--z"));

        String lastCharacterOfEachSuccessor = successors.stream()
                .map(NGram::lastCharacter)
                .map(String::valueOf)
                .collect(joining());

        String expected = starter.toString() + lastCharacterOfEachSuccessor;

        Gibberish gibberish = new Gibberish(() -> starter, sequenceOf(successors));

        assertThat(gibberish.get()).isEqualTo(expected);
    }
}
