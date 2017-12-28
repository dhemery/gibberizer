package com.dhemery.gibberizer.utils;

import com.dhemery.gibberizer.NGram;
import com.dhemery.gibberizer.NGramMap;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestUtils {
    public static List<NGram> nGramsOf(String... strings) {
        return Arrays.stream(strings).map(NGram::new).collect(toList());
    }

    public static NGramMap nGramMapOf(String... strings) {
        NGramMap nGramMap = new NGramMap();
        nGramMap.add(nGramsOf(strings));
        return nGramMap;
    }
}
