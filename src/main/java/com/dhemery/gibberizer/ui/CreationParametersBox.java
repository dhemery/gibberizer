package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;

public class CreationParametersBox extends TitledPane {
    public CreationParametersBox() {
        super("Create", content());
        setCollapsible(false);
    }

    private static Node content() {
        Node batchSizeBox = spinnerBox("Count", 1, 20, 10);
        Node similarityBox = spinnerBox("Similarity", 2, 10, 3);
        Node persistenceBox = spinnerBox("Persistence", 1, 10, 5);

        VBox box = new VBox();

        box.getChildren().addAll(batchSizeBox, similarityBox, persistenceBox);

        return box;
    }

    private static Node spinnerBox(String name, int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.setEditable(true);

        Label label = new Label(name);

        HBox box = new HBox();
        box.getChildren().addAll(spinner, label);

        return box;
    }
}
