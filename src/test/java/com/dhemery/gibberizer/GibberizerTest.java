package com.dhemery.gibberizer;

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
}
