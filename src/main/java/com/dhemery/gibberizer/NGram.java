package com.dhemery.gibberizer;

import java.util.Objects;

public class NGram {
    private final String string;
    private final boolean isStarter;
    private final boolean isEnder;


    /**
     * Creates an {@code NGram} from the given string,
     * which is taken to represent neither the start nor the end
     * of the string from which it was parsed.
     *
     * @param string the string from which to make an {@code NGram}
     */
    public NGram(String string) {
        this(string, false, false);
    }

    /**
     * Creates an {@code NGram} from the given string.
     *
     * @param string    the string from which to make an {@code NGram}
     * @param isStarter indicates whether this {@code NGram} represents the start of the string from which it was parsed
     * @param isEnder   indicates whether this {@code NGram} represents the end of the string from which it was parsed
     */
    public NGram(String string, boolean isStarter, boolean isEnder) {
        this.string = string;
        this.isStarter = isStarter;
        this.isEnder = isEnder;
    }

    /**
     * Creates an {@code NGram} from a substring extracted from the given string.
     * <p>
     * If {@code position == 0}, the {@code NGram} represents the start of the string.
     * If {@code position + size == string.length()}, the {@code NGram} represents the end of the string.
     * </p>
     *
     * @param string   the string from which to extract an {@code NGram}
     * @param position the position at which the {@code NGram} starts in the string
     * @param size     the size of the {@code NGram}
     * @return the {@code NGram}
     */
    public static NGram fromSubstring(String string, int position, int size) {
        return new NGram(string.substring(position, position + size), position == 0, position + size == string.length());
    }

    /**
     * Returns whether or not this {@code NGram} represents the start of the string from which it was parsed.
     *
     * @return {@code true} if, and only if, this {@code NGram} represents the start of the string from which it was parsed
     */
    public boolean isStarter() {
        return isStarter;
    }

    /**
     * Returns whether or not this {@code NGram} represents the end of the string from which it was parsed.
     *
     * @return {@code true} if, and only if, this {@code NGram} represents the end of the string from which it was parsed
     */
    public boolean isEnder() {
        return isEnder;
    }

    /**
     * Returns a substring that represents all characters of this {@code NGram} but the last.
     *
     * @return a substring that represents all characters of this {@code NGram} but the last
     */
    public String prefix() {
        return string.substring(0, string.length() - 1);
    }

    /**
     * Returns a substring that represents all characters of this {@code NGram} but the first.
     *
     * @return a substring that represents all characters of this {@code NGram} but the first
     */
    public String suffix() {
        return string.substring(1);
    }

    /**
     * Returns a codepoint that represents last character of this {@code NGram}.
     *
     * @return a codepoint that represents last character of this {@code NGram}
     */
    public char lastCharacter() {
        return string.charAt(string.length() - 1);
    }

    /**
     * Returns the string that this {@code NGram} represents.
     *
     * @return the string that this {@code NGram} represents
     */
    @Override
    public String toString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NGram)) return false;
        NGram other = (NGram) o;
        return isStarter == other.isStarter &&
                isEnder == other.isEnder &&
                Objects.equals(string, other.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, isStarter, isEnder);
    }
}
