package com.dhemery.gibberizer;

import java.util.Objects;
import java.util.Optional;

public class NGram {
    private final String text;
    private final int position;
    private final int size;

    public NGram(String text, int position, int size) {
        this.text = text;
        this.position = position;
        this.size = size;
    }

    @Override
    public String toString() {
        return substring(position, size);
    }

    public String lastCharacter() {
        return substring(position + size - 1, 1);
    }

    public String prefix() {
        return substring(position, size - 1);
    }

    public boolean isStartNGram() {
        return position == 0;
    }

    public Optional<NGram> nextNGram() {
        if (position + size == text.length()) return Optional.empty();
        return Optional.of(new NGram(text, position + 1, size));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NGram other = (NGram) o;
        return position == other.position &&
                size == other.size &&
                Objects.equals(text, other.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, position, size);
    }

    private String substring(int index, int length) {
        return text.substring(index, index + length);
    }
}
