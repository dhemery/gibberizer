package com.dhemery.gibberizer.testutil;

import com.dhemery.gibberizer.NGram;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NGrams {
    public static List<NGram> nGramsEndingWith(Character[] lastCharacters) {
        return Stream.of(lastCharacters).map(NGrams::nGramEndingWith).collect(toList());
    }

    public static NGram nGramEndingWith(char lastCharacter) {
        MutableNGram nGram = new MutableNGram();
        nGram.setLastCharacter(lastCharacter);
        return nGram;
    }

    public static UnaryOperator<NGram> sequenceOf(List<NGram> nGrams) {
        Iterator<NGram> each = nGrams.iterator();
        return s -> each.hasNext() ? each.next() : null;
    }
}
