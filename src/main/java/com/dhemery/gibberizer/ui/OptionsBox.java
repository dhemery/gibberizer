package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.layout.*;

public class OptionsBox extends HBox {
    public OptionsBox() {
        Node inputOptionsBox = new InputOptionsPane();
        Node creationOptionsBox = new CreationOptionsPane();
        Node filterOptionsBox = new FilterOptionsPane();
        Node outputOptionsBox = new OutputOptionsPane();

        getChildren().addAll(inputOptionsBox, creationOptionsBox, filterOptionsBox, outputOptionsBox);
    }
}
