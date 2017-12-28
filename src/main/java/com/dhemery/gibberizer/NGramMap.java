package com.dhemery.gibberizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NGramMap {
    private final Set<NGram> starters = new HashSet<>();
    private Set<NGram> enders = new HashSet<>();

    public void add(List<NGram> nGrams) {
        starters.add(nGrams.get(0));
        enders.add(nGrams.get(nGrams.size()-1));
    }

    public List<NGram> starters() {
        return new ArrayList<>(starters);
    }

    public List<NGram> enders() {
        return new ArrayList<>(enders);
    }
}
