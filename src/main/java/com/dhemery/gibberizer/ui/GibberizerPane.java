package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GibberizerPane extends VBox {
    public GibberizerPane() {
        Node optionsBox = new OptionsBox();
        Node gibberizeButton = new Button("Gibberize");
        Node inputOutputPane = new InputOutputPane();

        getChildren().addAll(optionsBox, gibberizeButton, inputOutputPane);
    }
}
