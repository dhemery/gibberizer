package com.dhemery.gibberizer;

public interface NGram {
    boolean isStarter();

    String prefix();

    String suffix();

    char lastCharacter();
}
