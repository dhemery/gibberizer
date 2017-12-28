package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramizerTest {
    private final NGramizer splitter = new NGramizer();

    @Test
    public void createsMultipleNgramsIfTextLengthExceedsNGramSize() {
        assertThat(splitter.apply("abcde", 2)).containsExactly("ab", "bc", "cd", "de");
    }

    @Test
    public void createsOneNgramIfTextLengthIsNGramSize() {
        String text = "abcde";
        assertThat(splitter.apply(text, text.length())).containsExactly(text);
    }
}
