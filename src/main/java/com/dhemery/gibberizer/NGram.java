
package com.dhemery.gibberizer;

public class NGram {
    public enum Type {
        NORMAL, STARTER, ENDER, OUTER;

        public boolean isEnder() {
            return equals(ENDER) || equals(OUTER);
        }

        public boolean isStarter() {
            return equals(STARTER) || equals(OUTER);
        }

    }

    private final String text;

    private final Type type;

    public NGram(String text) {
        this(text, Type.NORMAL);
    }

    public NGram(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public boolean isStarter() {
        return type.isStarter();
    }

    public boolean isEnder() {
        return type.isEnder();
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NGram nGram = (NGram) o;

        return text.equals(nGram.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
