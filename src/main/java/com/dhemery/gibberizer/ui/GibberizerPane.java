package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GibberizerPane extends HBox {
    public GibberizerPane() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        Node splitParametersBox = new SplitParametersBox();
        Node generationParametersBox = new CreationParametersBox();
        Node filterParametersBox = new FilterParametersBox();
        Node outputParametersBox = new OutputParametersBox();
        getChildren().addAll(splitParametersBox, generationParametersBox, filterParametersBox, outputParametersBox);
    }

    private static void blueBorder(Region region) {

    }
}
