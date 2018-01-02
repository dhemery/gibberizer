package com.dhemery.gibberizer.ui;

import javafx.scene.layout.VBox;

public class GibberizerPane extends VBox {
    public GibberizerPane() {
        OptionsBox optionsBox = new OptionsBox();

        getChildren().addAll(optionsBox);
    }
}
