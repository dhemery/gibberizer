package com.dhemery.gibberizer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PositionedNGramTest {
    @Nested
    class Strings {
        String text = "0123456789";
        NGram nGram = new PositionedNGram(text, 2, 3);

        @Test
        public void toStringIsNCharactersAtPosition() {
            assertThat(nGram).hasToString("234");
        }

        @Test
        public void lastCharacterIsCharacterAtPositionPlusNMinusOne() {
            assertThat(nGram.lastCharacter()).isEqualTo('4');
        }

        @Test
        public void prefixIsNMinusOneCharactersStartingAtPosition() {
            assertThat(nGram.prefix()).isEqualTo("23");
        }
    }

    @Nested
    class AtPositionZero {
        private final String text = "0123456789";
        private final NGram nGram = new PositionedNGram(text, 0, 3);

        @Test
        public void isStartStep() {
            assertThat(nGram.isStarter()).isTrue();
        }
    }

    @Nested
    class NotAtPositionZero {
        private final String text = "0123456789";
        private final NGram nGram = new PositionedNGram(text, 2, 3);

        @Test
        public void isNotStartStep() {
            assertThat(nGram.isStarter()).isFalse();
        }
    }

    @Nested
    class Equals {
        String text = "0123456789";
        NGram nGram = new PositionedNGram(text, 2, 3);

        @Test
        public void trueIfEqualTextPositionAndSize() {
            NGram equalNGram = new PositionedNGram(text, 2, 3);

            assertThat(nGram).isEqualTo(equalNGram);
        }

        @Test
        public void falseIfTextsDiffer() {
            NGram nGramWithDifferentText = new PositionedNGram(text + "foo", 2, 3);

            assertThat(nGram).isNotEqualTo(nGramWithDifferentText);
        }

        @Test
        public void falseIfPositionsDiffer() {
            NGram nGramWithDifferentPosition = new PositionedNGram(text, 3, 3);

            assertThat(nGram).isNotEqualTo(nGramWithDifferentPosition);
        }

        @Test
        public void falseIfSizesDiffer() {
            NGram nGramWithDifferentSize = new PositionedNGram(text, 2, 2);

            assertThat(nGram).isNotEqualTo(nGramWithDifferentSize);
        }
    }
}
