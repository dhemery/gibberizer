package com.dhemery.gibberizer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.dhemery.gibberizer.NGram.Type.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NGramTest {
    @Test
    public void toStringIsGivenText() {
        assertThat(new NGram("pickles")).hasToString("pickles");
    }

    @Test
    public void equalIfGivenTextIsEqual() {
        assertThat(new NGram("same text")).isEqualTo(new NGram("same text"));
    }

    @Test
    public void notEqualIfGivenTextDiffers() {
        assertThat(new NGram("this text")).isNotEqualTo(new NGram("different text"));
    }

    @Test
    public void remembersIfStarter() {
        assertThat(new NGram("pickles", STARTER)).matches(NGram::isStarter, "is starter");

    }

    @Test
    public void remembersIfEnder() {
        assertThat(new NGram("pickles", ENDER)).matches(NGram::isEnder, "is ender");

    }

    @Test
    public void remembersIfOuter() {
        assertThat(new NGram("pickles", OUTER))
                .matches(NGram::isStarter, "is starter")
                .matches(NGram::isEnder, "is ender");
    }

    @Disabled("WIP")
    @Test
    public void remembersIfNormal() {
//        assertThat(new NGram("pickles", NORMAL)).isNot(NGram::isEnder);
    }
}
