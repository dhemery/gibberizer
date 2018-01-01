package com.dhemery.gibberizer;

import java.util.Objects;

/**
 * Represents a contiguous sequence of characters extracted from a source string.
 */
public class NGram {
    private final String string;
    private final boolean isStarter;
    private final boolean isEnder;

    /**
     * Creates an n-gram that represents the given string,
     * which is taken to represent neither the start nor the end
     * of the string from which it was parsed.
     *
     * @param string the string from which to make an n-gram
     */
    public NGram(String string) {
        this(string, false, false);
    }

    /**
     * Creates an n-gram that represents the given string.
     *
     * @param string    the string from which to make an n-gram
     * @param isStarter indicates whether this n-gram represents the start of the string from which it was parsed
     * @param isEnder   indicates whether this n-gram represents the end of the string from which it was parsed
     */
    public NGram(String string, boolean isStarter, boolean isEnder) {
        this.string = string;
        this.isStarter = isStarter;
        this.isEnder = isEnder;
    }

    /**
     * Returns whether or not this n-gram represents the start of the string from which it was parsed.
     *
     * @return {@code true} if, and only if, this n-gram represents the start of the string from which it was parsed
     */
    public boolean isStarter() {
        return isStarter;
    }

    /**
     * Returns whether or not this n-gram represents the end of the string from which it was parsed.
     *
     * @return {@code true} if, and only if, this n-gram represents the end of the string from which it was parsed
     */
    public boolean isEnder() {
        return isEnder;
    }

    /**
     * Returns the substring of this n-gram that excludes the last character.
     *
     * @return the substring of this n-gram that excludes the last character
     */
    public String prefix() {
        return string.substring(0, string.length() - 1);
    }

    /**
     * Returns the substring of this n-gram that excludes the first character.
     *
     * @return the substring of this n-gram that excludes the first character
     */
    public String suffix() {
        return string.substring(1);
    }

    /**
     * Returns a code point that represents last character of this n-gram.
     *
     * @return a code point that represents last character of this n-gram
     */
    public int lastCharacter() {
        return string.codePointAt(string.length() - 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string, isStarter, isEnder);
    }

    /**
     * Tests this n-gram for equality with the given object.
     *
     * @param other the object to which this n-gram is to be compared
     * @return true if, and only if, the given object is an {@code NGram} that is identical to this NGram
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof NGram)) return false;
        NGram otherNGram = (NGram) other;
        return isStarter == otherNGram.isStarter &&
                isEnder == otherNGram.isEnder &&
                Objects.equals(string, otherNGram.string);
    }

    /**
     * Returns the string that this n-gram represents.
     *
     * @return the string that this n-gram represents
     */
    @Override
    public String toString() {
        return string;
    }

    /**
     * Creates an n-gram that represents a substring extracted from the given string.
     * <p>
     * If {@code index == 0}, the n-gram represents the start of the string.
     * If {@code index + length == string.length()}, the n-gram represents the end of the string.
     * </p>
     *
     * @param string the string from which to extract the n-gram
     * @param index  the index at which the n-gram starts in the string
     * @param length the length of the n-gram
     * @return the created {@code NGram}
     */
    public static NGram fromSubstring(String string, int index, int length) {
        return new NGram(string.substring(index, index + length), index == 0, index + length == string.length());
    }
}
