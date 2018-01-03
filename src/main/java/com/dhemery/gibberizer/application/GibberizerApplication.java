package com.dhemery.gibberizer.application;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Stream;

public class GibberizerApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Gibberizer");

        CheckBox splitCheckBox = new CheckBox("Split Into");
        RadioButton splitIntoWordsButton = new RadioButton("Words");
        RadioButton splitIntoLinesButton = new RadioButton("Lines");
        ToggleGroup splitterToggleGroup = new ToggleGroup();
        List.of(splitIntoWordsButton, splitIntoLinesButton)
                .forEach(b -> b.setToggleGroup(splitterToggleGroup));

        splitterToggleGroup.selectToggle(splitIntoWordsButton);
        VBox inputOptionsBox = new VBox(splitCheckBox, splitIntoWordsButton, splitIntoLinesButton);

        TextArea inputTextArea = new TextArea();

        Spinner<Integer> batchSizeSpinner = spinner(1, 1000, 1000);
        Spinner<Integer> similaritySpinner = spinner(2, 10, 3);
        Spinner<Integer> persistenceSpinner = spinner(1, 10, 5);
        Label batchSizeLabel = new Label("Batch Size");
        Label similarityLabel = new Label("Similarity");
        Label persistenceLabel = new Label("Persistence");
        CheckBox allowInputEchoCheckBox = new CheckBox("Allow Inputs");

        Button gibberizeButton = new Button("Generate");

        RadioButton separateBySpaceButton = new RadioButton("Spaces");
        RadioButton separateByLinefeedButton = new RadioButton("New Lines");
        RadioButton separateByBlankLineButton = new RadioButton("Blank Lines");
        ToggleGroup outputToggleGroup = new ToggleGroup();
        List.of(separateBySpaceButton, separateByLinefeedButton, separateByBlankLineButton)
                .forEach(b -> b.setToggleGroup(outputToggleGroup));
        Label separateByLabel = new Label("Separate By");
        VBox outputOptionsBox = new VBox(separateByLabel, separateBySpaceButton, separateByLinefeedButton, separateByBlankLineButton);

        ScrollPane outputTextPane = new ScrollPane();
        HBox outputBox = new HBox(outputOptionsBox, outputTextPane);

        GridPane gridPane = new GridPane();
        int row = 0;
        gridPane.add(new Label("Input"), 0, row++, 2, 1);
        gridPane.addRow(row++, inputOptionsBox, inputTextArea);
        GridPane.setColumnSpan(inputTextArea, GridPane.REMAINING);
        gridPane.add(new Label("Gibberish"), 0, row++, 2, 1);
        gridPane.addRow(row++, gibberizeButton, batchSizeSpinner, batchSizeLabel);
        gridPane.addRow(row++, new Label(""), similaritySpinner, similarityLabel);
        gridPane.addRow(row++, new Label(""), persistenceSpinner, persistenceLabel);
        gridPane.addRow(row++, new Label(""), allowInputEchoCheckBox);
        gridPane.add(new Label("Output"), 0, row++, 2, 1);
        gridPane.addRow(row,outputOptionsBox, outputBox);
        GridPane.setColumnSpan(outputBox, GridPane.REMAINING);
        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    public static void main(String args[]) {
        launch(GibberizerApplication.class, args);
    }

    private static Spinner<Integer> spinner(int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.setMaxWidth(100);
        spinner.setEditable(true);
        return spinner;
    }
}
