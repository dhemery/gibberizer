package com.dhemery.gibberizer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.dhemery.gibberizer.utils.TestUtils.nGramMapOf;
import static org.assertj.core.api.Assertions.assertThat;

public class GibTest {
    @Disabled("WIP")
    @Test
    public void gib() {
        assertThat(Gib.from(nGramMapOf("fo", "ob"))).isEqualTo("fob");
    }
}
