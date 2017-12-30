package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GibberizerTest {
    @Test
    public void forced() {
        String text = "abcde";
        Gibberish gibberish = new Gibberish(1, text);
        assertThat(gibberish.generate()).isEqualTo("abcde");
    }

    @Test
    public void repetition() {
        String text = "abbc";
        Gibberish gibberish = new Gibberish(1, text);
        assertThat(gibberish.generate()).matches("ab+c");
    }

    @Test
    public void alternation() {
        String text = "abacadaeafagahai";
        Gibberish gibberish = new Gibberish(1, text);
        assertThat(gibberish.generate()).matches("(a[bcdefghi])+");
    }
}
