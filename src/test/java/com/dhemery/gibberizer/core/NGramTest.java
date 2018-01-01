package com.dhemery.gibberizer.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramTest {
    @Nested
    class Text {
        private NGram nGram = new NGram("234");

        @Test
        public void toStringIsGivenString() {
            assertThat(nGram).hasToString("234");
        }

        @Test
        public void lastCharacterIsLastCharacterOfString() {
            assertThat(nGram.lastCharacter()).isEqualTo('4');
        }

        @Test
        public void prefixIsStringMinusLastCharacter() {
            assertThat(nGram.prefix()).isEqualTo("23");
        }

        @Test
        public void suffixIsStringMinusFirstCharacter() {
            assertThat(nGram.suffix()).isEqualTo("34");
        }
    }

    @Nested
    class IsStarter {
        @Test
        public void byDefaultIsNotStarter() {
            assertThat(new NGram("abc").isStarter()).isFalse();
        }

        @Test
        public void remembersWhetherIsStarter() {
            assertThat(new NGram("abc", false, false).isStarter()).isFalse();
            assertThat(new NGram("abc", true, false).isStarter()).isTrue();
        }
    }


    @Nested
    class IsEnder {
        @Test
        public void byDefaultIsNotEnder() {
            assertThat(new NGram("abc").isEnder()).isFalse();
        }

        @Test
        public void remembersWhetherIsEnder() {
            assertThat(new NGram("abc", false, false).isEnder()).isFalse();
            assertThat(new NGram("abc", false, true).isEnder()).isTrue();
        }
    }

    @Nested
    class Equals {
        NGram abcStarter = new NGram("abc", true, false);
        NGram xyzStarter = new NGram("xyz", true, false);
        NGram abcEnder = new NGram("abc", false, true);
        NGram xyzEnder = new NGram("xyz", false, true);
        NGram abcInner = new NGram("abc");
        NGram xyzInner = new NGram("xyz");
        NGram abcOuter = new NGram("abc", true, true);
        NGram xyzOuter = new NGram("xyz", true, true);
        NGram anotherAbcStarter = new NGram("abc", true, false);
        NGram anotherAbcInner = new NGram("abc");
        NGram anotherAbcEnder = new NGram("abc", false, true);
        NGram anotherAbcOuter = new NGram("abc", true, true);

        @Test
        public void trueIfEqualStringEqualIsStarterAndEqualIsEnder() {
            assertThat(abcStarter).isEqualTo(anotherAbcStarter);
            assertThat(anotherAbcStarter).isEqualTo(abcStarter);

            assertThat(abcEnder).isEqualTo(anotherAbcEnder);
            assertThat(anotherAbcEnder).isEqualTo(abcEnder);

            assertThat(abcInner).isEqualTo(anotherAbcInner);
            assertThat(anotherAbcInner).isEqualTo(abcInner);

            assertThat(abcOuter).isEqualTo(anotherAbcOuter);
            assertThat(anotherAbcOuter).isEqualTo(abcOuter);
        }

        @Test
        public void falseIfStringDiffers() {
            assertThat(abcStarter).isNotEqualTo(xyzStarter);
            assertThat(xyzStarter).isNotEqualTo(abcStarter);

            assertThat(abcEnder).isNotEqualTo(xyzEnder);
            assertThat(xyzEnder).isNotEqualTo(abcEnder);

            assertThat(abcInner).isNotEqualTo(xyzInner);
            assertThat(xyzInner).isNotEqualTo(abcInner);

            assertThat(abcOuter).isNotEqualTo(xyzOuter);
            assertThat(xyzOuter).isNotEqualTo(abcOuter);
        }

        @Test
        public void falseIfIsStarterDiffers() {
            assertThat(abcStarter).isNotEqualTo(abcInner);
            assertThat(abcInner).isNotEqualTo(abcStarter);
        }

        @Test
        public void falseIfIsEnderDiffers() {
            assertThat(abcEnder).isNotEqualTo(abcInner);
            assertThat(abcInner).isNotEqualTo(abcEnder);
        }
    }

    @Nested
    class FromSubstring {
        @Test
        public void toStringIsSubstringAtIndexAndLengthSize() {
            NGram nGram = NGram.fromSubstring("0123456789", 2, 3);
            assertThat(nGram).hasToString("234");
        }

        @Test
        public void lastCharacterIsCharacterAtIndexPlusLengthMinusOne() {
            NGram nGram = NGram.fromSubstring("0123456789", 2, 3);

            assertThat(nGram.lastCharacter()).isEqualTo('4');
        }

        @Test
        public void prefixIsLengthMinusOneCharactersStartingWithCharacterAtIndex() {
            NGram nGram = NGram.fromSubstring("0123456789", 2, 3);
            assertThat(nGram.prefix()).isEqualTo("23");
        }

        @Test
        public void suffixIsLengthMinusOneCharactersStartingWithCharacterAfterIndex() {
            NGram nGram = NGram.fromSubstring("0123456789", 2, 3);
            assertThat(nGram.suffix()).isEqualTo("34");
        }

        @Test
        public void substringAtIndexZeroIsStarter() {
            NGram nGram = NGram.fromSubstring("0123456789", 0, 3);
            assertThat(nGram.isStarter()).isTrue();
        }

        @Test
        public void substringNotAtIndexZeroIsNotStarter() {
            NGram nGram = NGram.fromSubstring("0123456789", 1, 3);

            assertThat(nGram.isStarter()).isFalse();
        }

        @Test
        public void substringAtEndOfStringIsEnder() {
            NGram nGram = NGram.fromSubstring("0123456789", 7, 3);

            assertThat(nGram.isEnder()).isTrue();
        }

        @Test
        public void substringNotAtEndOfStringIsNotEnder() {
            NGram nGram = NGram.fromSubstring("0123456789", 6, 3);

            assertThat(nGram.isEnder()).isFalse();
        }
    }
}
