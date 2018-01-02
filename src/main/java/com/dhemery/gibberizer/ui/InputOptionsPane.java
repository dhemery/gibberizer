package com.dhemery.gibberizer.ui;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.stream.Stream;

public class InputOptionsPane extends TitledPane {
    public InputOptionsPane() {
        super("Read Input As", inputOptionsBox());
        setCollapsible(false);

    }

    private static Node inputOptionsBox() {
        RadioButton splitIntoWords = new RadioButton("Words");
        RadioButton splitIntoLines = new RadioButton("Lines");
        RadioButton doNotSplit = new RadioButton("One String");

        ToggleGroup splitToggleGroup = new ToggleGroup();


        VBox box = new VBox();
        Stream.of(splitIntoWords, splitIntoLines, doNotSplit)
                .peek(b -> b.setToggleGroup(splitToggleGroup))
                .forEach(box.getChildren()::add);

        splitToggleGroup.selectToggle(splitIntoWords);

        return box;
    }
}
