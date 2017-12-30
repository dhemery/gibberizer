package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramStreamTest {
    String text = "abcde";
    int size = 3;
    NGram nGram0 = new NGram(text, 0, size);
    NGram nGram1 = new NGram(text, 1, size);
    NGram nGram2 = new NGram(text, 2, size);

    @Test
    public void streamFromText() {
        Stream<NGram> nGrams = NGramStream.of(size, text);

        assertThat(nGrams).containsOnly(nGram0, nGram1, nGram2);
    }

    @Test
    public void streamStartingFromNGram() {
        Stream<NGram> nGrams = NGramStream.startingWith(nGram0);

        assertThat(nGrams).containsOnly(nGram0, nGram1, nGram2);
    }
}
