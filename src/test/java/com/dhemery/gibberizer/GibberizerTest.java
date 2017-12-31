package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GibberizerTest {
    @Test
    public void equalToStarterPrefixIfNextYieldsNull() {
        NGram starter = new NGram("foo", 0, 2);

        Gibberish gibberish = new Gibberish(() -> starter, n -> null);

        assertThat(gibberish.generate()).isEqualTo("fo");
    }
}
