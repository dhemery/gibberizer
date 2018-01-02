package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;

public class GibberishPane extends TitledPane {
    public GibberishPane() {
        super("Gibberish", outputScrollPane());
        setCollapsible(false);
    }

    private static Node outputScrollPane() {
        return new ScrollPane();
    }
}
