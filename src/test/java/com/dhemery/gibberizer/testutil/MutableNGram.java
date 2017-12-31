package com.dhemery.gibberizer.testutil;

import com.dhemery.gibberizer.NGram;

public class MutableNGram implements NGram {
    private Character lastCharacter;
    private Boolean isStarter;
    private String prefix;
    private String suffix;

    @Override
    public boolean isStarter() {
        return isStarter;
    }

    @Override
    public String prefix() {
        return prefix;
    }

    @Override
    public String suffix() {
        return suffix;
    }

    @Override
    public char lastCharacter() {
        return lastCharacter;
    }

    public void setLastCharacter(char lastCharacter) {
        this.lastCharacter = lastCharacter;
    }

    public void setIsStarter(boolean isStarter) {
        this.isStarter = isStarter;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
