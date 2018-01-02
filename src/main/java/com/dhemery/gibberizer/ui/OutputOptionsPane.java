package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.stream.Stream;

public class OutputOptionsPane extends TitledPane {
    public OutputOptionsPane() {
        super("Separate By", outputOptionsBox());
        setCollapsible(false);
    }

    private static Node outputOptionsBox() {
        RadioButton separateBySpace = new RadioButton("Space");
        RadioButton separateByNewLine = new RadioButton("New line");
        RadioButton separateByBlankLine = new RadioButton("Blank line");

        ToggleGroup outputToggleGroup = new ToggleGroup();

        VBox box = new VBox();

        Stream.of(separateBySpace, separateByNewLine, separateByBlankLine)
                .peek(b -> b.setToggleGroup(outputToggleGroup))
                .forEach(box.getChildren()::add);

        outputToggleGroup.selectToggle(separateByNewLine);

        return box;
    }
}
