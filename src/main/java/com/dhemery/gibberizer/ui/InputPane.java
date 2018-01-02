package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

public class InputPane extends TitledPane {
    public InputPane() {
        super("Input", inputTextArea());
        setCollapsible(false);
    }

    private static Node inputTextArea() {
        return new TextArea();
    }
}
