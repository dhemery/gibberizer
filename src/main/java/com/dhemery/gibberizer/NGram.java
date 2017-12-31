package com.dhemery.gibberizer;

import java.util.Optional;

public interface NGram {
    boolean isStartNGram();
    String prefix();
    String lastCharacter();
    Optional<NGram> nextNGram();
}
