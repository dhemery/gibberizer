package com.dhemery.gibberizer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramTest {
    @Nested
    class Strings {
        String text = "0123456789";
        NGram nGram = new NGram(text, 2, 3);

        @Test
        public void toStringIsNCharactersAtPosition() {
            assertThat(nGram).hasToString("234");
        }

        @Test
        public void lastCharacterIsCharacterAtPositionPlusNMinusOne() {
            assertThat(nGram.lastCharacter()).isEqualTo("4");
        }

        @Test
        public void prefixIsNMinusOneCharactersStartingAtPosition() {
            assertThat(nGram.prefix()).isEqualTo("23");
        }
    }

    @Nested
    class NGramAtStartOfText {
        private final String text = "0123456789";
        private final NGram nGram = new NGram(text, 0, 3);

        @Test
        public void isStartStep() {
            assertThat(nGram.isStartNGram()).isTrue();
        }
    }

    @Nested
    class NGramAtEndOfText {
        private final String text = "0123456789";
        private final int size = 4;
        private final int position = text.length() - size;
        private final NGram nGram = new NGram(text, position, size);

        @Test
        public void hasNoNext() {
            assertThat(nGram.nextNGram()).isNotPresent();
        }
    }

    @Nested
    class NGramAtNeitherEndOfText {
        private final String text = "0123456789";
        private final NGram nGram = new NGram(text, 2, 3);

        @Test
        public void isNotStartStep() {
            assertThat(nGram.isStartNGram()).isFalse();
        }

        @Test
        public void hasNextOfSameLengthStartingAtNextPosition() {
            assertThat(nGram.nextNGram()).contains(new NGram(text, 3, 3));
        }
    }

    @Nested
    class NGramOfEntireText {
        private final String text = "0123";
        private final NGram step = new NGram(text, 0, text.length());

        @Test
        public void isStartStep() {
            assertThat(step.isStartNGram()).isTrue();
        }


        @Test
        public void hasNoNext() {
            assertThat(step.nextNGram()).isNotPresent();
        }
    }

    @Nested
    class Equals {
        String text = "0123456789";
        NGram nGram = new NGram(text, 2, 3);

        @Test
        public void trueIfEqualTextPositionAndSize() {
            NGram equalNGram = new NGram(text, 2, 3);

            assertThat(nGram).isEqualTo(equalNGram);
        }

        @Test
        public void falseIfTextsDiffer() {
            NGram nGramWithDifferentText = new NGram(text + "foo", 2, 3);

            assertThat(nGram).isNotEqualTo(nGramWithDifferentText);
        }

        @Test
        public void falseIfPositionsDiffer() {
            NGram nGramWithDifferentPosition = new NGram(text, 3, 3);

            assertThat(nGram).isNotEqualTo(nGramWithDifferentPosition);
        }

        @Test
        public void falseIfSizesDiffer() {
            NGram nGramWithDifferentSize = new NGram(text, 2, 2);

            assertThat(nGram).isNotEqualTo(nGramWithDifferentSize);
        }
    }
}
