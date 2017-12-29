package com.dhemery.gibberizer;

public class Step {
    private final String text;
    private final int position;
    private final int size;

    public Step(String text, int position, int size) {
        this.text = text;
        this.position = position;
        this.size = size;
    }

    public String head() {
        return substring(position, size);
    }

    public String tail() {
        return substring(position + 1, size);
    }

    public String lastChar() {
        return substring(position + size, 1);
    }

    public boolean isStartStep() {
        return position == 0;
    }

    public boolean isEndStep() {
        return position + size == text.length();
    }

    private String substring(int index, int length) {
        return text.substring(index, index + length);
    }
}
