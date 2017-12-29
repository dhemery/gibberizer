package com.dhemery.gibberizer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StepTest {
    @Test
    public void headIsNCharactersAtPosition() {
        String text = "0123456789";
        Step step = new Step(text, 2, 3);

        assertThat(step.head()).isEqualTo("234");
    }

    @Test
    public void tailIsNCharactersAtPositionPlusOne() {
        String text = "0123456789";
        Step step = new Step(text, 2, 3);

        assertThat(step.tail()).isEqualTo("345");
    }

    @Test
    public void lastCharIsCharacterAtPositionPlusN() {
        String text = "0123456789";
        Step step = new Step(text, 2, 3);

        assertThat(step.lastChar()).isEqualTo('5');
    }

    @Nested
    class StepAtStartOfText {
        private final String text = "0123456789";
        private final Step step = new Step(text, 0, 3);

        @Test
        public void isStartStep() {
            assertThat(step.isStartStep()).isTrue();
        }
    }

    @Nested
    class StepAtEndOfText {
        private final String text = "123456789";
        private final int size = 4;
        private final int position = text.length() - size;
        private final Step step = new Step(text, position, size);

        @Test
        public void isEndStep() {
            assertThat(step.isEndStep()).isTrue();
        }
    }

    @Nested
    class StepAtNeitherEndOfText {
        private final String text = "123456789";
        private final Step step = new Step(text, 2, 3);

        @Test
        public void isNotStartStep() {
            assertThat(step.isStartStep()).isFalse();
        }

        @Test
        public void isNotEndStep() {
            assertThat(step.isEndStep()).isFalse();
        }
    }

    @Nested
    class StepOfEntireText {
        private final String text = "0123";
        private final Step step = new Step(text, 0, text.length());

        @Test
        public void isStartStep() {
            assertThat(step.isStartStep()).isTrue();
        }

        @Test
        public void isEndStep() {
            assertThat(step.isEndStep()).isTrue();
        }
    }
}
