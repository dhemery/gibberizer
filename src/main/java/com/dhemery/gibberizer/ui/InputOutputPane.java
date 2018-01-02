package com.dhemery.gibberizer.ui;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

public class InputOutputPane extends SplitPane {
    public InputOutputPane() {
        setOrientation(Orientation.VERTICAL);

        Node inputPane = new InputPane();
        Node outputPane = new GibberishPane();

        getItems().addAll(inputPane, outputPane);
    }
}
