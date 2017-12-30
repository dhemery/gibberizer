package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramStreamTest {
    @Test
    public void streamFromSingleString() {
        String string = "abcde";
        int size = 3;
        NGram nGram0 = new NGram(string, 0, size);
        NGram nGram1 = new NGram(string, 1, size);
        NGram nGram2 = new NGram(string, 2, size);

        Stream<NGram> nGrams = NGramStream.of(size, string);

        assertThat(nGrams).containsOnly(nGram0, nGram1, nGram2);
    }

    @Test
    public void streamStartingFromNGram() {
        String string = "abcde";
        int size = 3;
        NGram nGram0 = new NGram(string, 0, size);
        NGram nGram1 = new NGram(string, 1, size);
        NGram nGram2 = new NGram(string, 2, size);

        Stream<NGram> nGrams = NGramStream.startingWith(nGram0);

        assertThat(nGrams).containsOnly(nGram0, nGram1, nGram2);
    }


    @Test
    public void streamFromSeveralStrings() {
        String string0 = "abcde";
        String string1 = "fghij";
        int size = 3;
        NGram nGram00 = new NGram(string0, 0, size);
        NGram nGram01 = new NGram(string0, 1, size);
        NGram nGram02 = new NGram(string0, 2, size);
        NGram nGram10 = new NGram(string1, 0, size);
        NGram nGram11 = new NGram(string1, 1, size);
        NGram nGram12 = new NGram(string1, 2, size);

        Stream<NGram> nGrams = NGramStream.of(size, string0, string1);

        assertThat(nGrams).containsOnly(nGram00, nGram01, nGram02, nGram10, nGram11, nGram12);
    }
}
