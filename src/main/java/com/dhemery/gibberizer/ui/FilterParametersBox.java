package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class FilterParametersBox extends TitledPane {
    public FilterParametersBox() {
        super("Allow", content());
        setCollapsible(false);

    }

    private static Node content() {
        VBox box = new VBox();
        CheckBox inputEchoCheckbox = new CheckBox("Input Echo");
        CheckBox allowDuplicatesCheckbox = new CheckBox("Duplicates");

        box.getChildren().addAll(inputEchoCheckbox, allowDuplicatesCheckbox);

        return box;
    }
}
