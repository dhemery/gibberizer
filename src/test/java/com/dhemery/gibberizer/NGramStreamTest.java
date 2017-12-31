package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramStreamTest {
    @Test
    public void streamFromSingleString() {
        String string = "abcde";
        int size = 3;
        NGram nGram0 = new PositionedNGram(string, 0, size);
        NGram nGram1 = new PositionedNGram(string, 1, size);
        NGram nGram2 = new PositionedNGram(string, 2, size);

        Stream<NGram> nGrams = new NGramStream(size).of(string);

        assertThat(nGrams).containsOnly(nGram0, nGram1, nGram2);
    }

    @Test
    public void streamFromSeveralStrings() {
        String string0 = "abcde";
        String string1 = "fghij";
        int size = 3;
        NGram nGram00 = new PositionedNGram(string0, 0, size);
        NGram nGram01 = new PositionedNGram(string0, 1, size);
        NGram nGram02 = new PositionedNGram(string0, 2, size);
        NGram nGram10 = new PositionedNGram(string1, 0, size);
        NGram nGram11 = new PositionedNGram(string1, 1, size);
        NGram nGram12 = new PositionedNGram(string1, 2, size);

        Stream<NGram> nGrams = new NGramStream(size).of(string0, string1);

        assertThat(nGrams).containsOnly(nGram00, nGram01, nGram02, nGram10, nGram11, nGram12);
    }
}
