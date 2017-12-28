package com.dhemery.gibberizer;

import java.util.List;
import java.util.Random;

public class Gib {
    public static String from(NGramMap nGrams) {
        List<NGram> starters = nGrams.starters();
        NGram starter = starters.get(new Random().nextInt(starters.size()));
        return starter.toString();
    }
}
