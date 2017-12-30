package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GibberizerTest {
    @Test
    public void forced() {
        String text = "abcde";
        Gibberish gibberish = new Gibberish(2, text);
        assertThat(gibberish.generate()).isEqualTo("abcde");
    }

    @Test
    public void repetition() {
        String text = "abbc";
        Gibberish gibberish = new Gibberish(2, text);
        assertThat(gibberish.generate()).matches("ab+c");
    }

    @Test
    public void alternation() {
        String text = "abacadaeafagahai";
        Gibberish gibberish = new Gibberish(2, text);
        assertThat(gibberish.generate()).matches("(a[bcdefghi])+");
    }

    // This one is dangerous. It is not guaranteed to terminate.
    @Test
    public void multipleTexts() {
        String text1 = "ab";
        String text2 = "ac";
        Set<String> generated = new HashSet<>();
        Gibberish gibberish = new Gibberish(2, text1, text2);
        while (generated.size() < 2) {
            generated.add(gibberish.generate());
        }

        assertThat(generated).containsOnly("ab", "ac");
    }
}
