package com.dhemery.gibberizer.testutil;

import com.dhemery.gibberizer.NGram;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NGrams {
    public static List<NGram> nGramsEndingWith(Character[] lastCharacters) {
        return Stream.of(lastCharacters).map(NGrams::nGramEndingWith).collect(toList());
    }

    public static NGram nGramEndingWith(Character lastCharacter) {
        return new NGram() {
            @Override
            public boolean isStartNGram() {
                return false;
            }

            @Override
            public String prefix() {
                return null;
            }

            @Override
            public String lastCharacter() {
                return String.valueOf(lastCharacter);
            }

            @Override
            public Optional<NGram> nextNGram() {
                return Optional.empty();
            }
        };
    }

    public static UnaryOperator<NGram> sequenceOf(List<NGram> nGrams) {
        Iterator<NGram> each = nGrams.iterator();
        return s -> each.hasNext() ? each.next() : null;
    }
}
