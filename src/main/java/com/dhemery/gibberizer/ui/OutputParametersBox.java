package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.stream.Stream;

public class OutputParametersBox extends TitledPane {
    public OutputParametersBox() {
        super("Separate By", content());
        setCollapsible(false);
    }

    private static Node content() {
        RadioButton separateBySpace = new RadioButton("Space");
        RadioButton separateByNewLine = new RadioButton("New line");
        RadioButton separateByBlankLine = new RadioButton("Blank line");
        separateBySpace.setDisable(true);

        ToggleGroup separationToggleGroup = new ToggleGroup();

        VBox box = new VBox();

        Stream.of(separateBySpace, separateByNewLine, separateByBlankLine)
                .peek(b -> b.setToggleGroup(separationToggleGroup))
                .forEach(box.getChildren()::add);

        separationToggleGroup.selectToggle(separateByNewLine);

        return box;
    }
}
