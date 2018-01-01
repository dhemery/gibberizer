package com.dhemery.gibberizer.testutil;

import com.dhemery.gibberizer.NGram;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;

public class NGrams {
    public static UnaryOperator<NGram> sequenceOf(NGram... nGrams) {
        return sequenceOf(Arrays.asList(nGrams));
    }

    public static UnaryOperator<NGram> sequenceOf(List<NGram> nGrams) {
        Iterator<NGram> each = nGrams.iterator();
        return s -> each.hasNext() ? each.next() : null;
    }
}
