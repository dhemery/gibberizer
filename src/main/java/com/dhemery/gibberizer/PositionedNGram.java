package com.dhemery.gibberizer;

import java.util.Objects;

public class PositionedNGram implements NGram {
    private final String text;
    private final int position;
    private final int size;

    public PositionedNGram(String text, int position, int size) {
        this.text = text;
        this.position = position;
        this.size = size;
    }

    @Override
    public boolean isStarter() {
        return position == 0;
    }

    @Override
    public String prefix() {
        return substring(position, size - 1);
    }

    @Override
    public String suffix() {
        return substring(position + 1, size - 1);
    }

    @Override
    public char lastCharacter() {
        return text.charAt(position + size - 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, position, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionedNGram other = (PositionedNGram) o;
        return position == other.position &&
                size == other.size &&
                Objects.equals(text, other.text);
    }

    @Override
    public String toString() {
        return substring(position, size);
    }

    private String substring(int index, int length) {
        return text.substring(index, index + length);
    }
}
