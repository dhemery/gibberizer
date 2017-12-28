package com.dhemery.gibberizer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GibberizerTest {
    private final Gibberizer gibberizer = new Gibberizer();

    @Test
    public void onlyOnePossibleGib() {
        String input = "abcde";
        String output = gibberizer.gibberize(input);
        assertThat(output).isEqualTo(input);
    }

    @Test
    public void fixedLengthGib() {
        String input = "aba";
        String output = gibberizer.gibberize(input, 3, 3);
        assertThat(output).isEqualTo(input);
    }


    @Disabled("TBI")
    @Test
    public void gibLengthRange() {
        String input = "aba";
        String output = gibberizer.gibberize(input, 4, 6);
        assertThat(output).isEqualTo("ababa");
    }
}
