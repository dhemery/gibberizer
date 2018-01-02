package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class FilterOptionsPane extends TitledPane {
    public FilterOptionsPane() {
        super("Allow", filterOptionsBox());
        setCollapsible(false);

    }

    private static Node filterOptionsBox() {
        CheckBox inputEchoCheckbox = new CheckBox("Input Echo");
        CheckBox allowDuplicatesCheckbox = new CheckBox("Duplicates");

        VBox box = new VBox();
        box.getChildren().addAll(inputEchoCheckbox, allowDuplicatesCheckbox);
        return box;
    }
}
