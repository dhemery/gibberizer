package com.dhemery.gibberizer.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class NGramParserTest {
    @Test
    public void emptyIfNoStrings() {
        Assertions.assertThat(new NGramParser(3).parse()).isEmpty();
    }

    @Test
    public void parsesSingleString() {
        String string = "abcde";
        int size = 3;

        List<NGram> expected = List.of(
                new NGram("abc", true, false),
                new NGram("bcd"),
                new NGram("cde", false, true)
        );

        List<NGram> actual = new NGramParser(size).parse(string);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    public void parsesEachStringInArray() {
        String string0 = "abcde";
        String string1 = "fghij";
        int size = 3;

        List<NGram> expected = List.of(
                new NGram("abc", true, false),
                new NGram("bcd"),
                new NGram("cde", false, true),
                new NGram("fgh", true, false),
                new NGram("ghi"),
                new NGram("hij", false, true)
        );

        List<NGram> actual = new NGramParser(size).parse(string0, string1);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    public void parsesEachStringInCollection() {
        String string0 = "abcde";
        String string1 = "fghij";
        int size = 3;
        List<NGram> expected = List.of(
                new NGram("abc", true, false),
                new NGram("bcd"),
                new NGram("cde", false, true),
                new NGram("fgh", true, false),
                new NGram("ghi"),
                new NGram("hij", false, true)
        );

        List<NGram> actual = new NGramParser(size).parse(Set.of(string0, string1));

        assertThat(actual).hasSameElementsAs(expected);
    }
}
