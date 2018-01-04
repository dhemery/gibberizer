package com.dhemery.gibberizer.application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GibberizerApplication extends Application {
    private static final double LEFT_COLUMN_WIDTH = 120;

    public static void main(String args[]) {
        launch(GibberizerApplication.class, args);
    }

    private static Spinner<Integer> spinner(int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.setMaxWidth(80);
//        spinner.setEditable(true);
        return spinner;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gibberizer");

        CheckBox splitInputCheckBox = new CheckBox("Split Text");
        RadioButton splitIntoWordsButton = new RadioButton("into Words");
        RadioButton splitIntoLinesButton = new RadioButton("into Lines");
        ToggleGroup splitterToggleGroup = new ToggleGroup();
        List.of(splitIntoWordsButton, splitIntoLinesButton)
                .forEach(b -> b.setToggleGroup(splitterToggleGroup));

        splitIntoWordsButton.disableProperty().bind(splitInputCheckBox.selectedProperty().not());
        splitIntoLinesButton.disableProperty().bind(splitInputCheckBox.selectedProperty().not());

        splitterToggleGroup.selectToggle(splitIntoWordsButton);

        VBox inputOptionsBox = new VBox(splitInputCheckBox, splitIntoWordsButton, splitIntoLinesButton);
        inputOptionsBox.setMinWidth(LEFT_COLUMN_WIDTH);

        TextArea inputTextArea = new TextArea();
        HBox inputBox = new HBox(inputOptionsBox, inputTextArea);
        TitledPane inputPane = new TitledPane("Input", inputBox);
        inputPane.setCollapsible(false);

        Spinner<Integer> batchSizeSpinner = spinner(1, 99, 10);
        Spinner<Integer> similaritySpinner = spinner(2, 10, 3);
        Spinner<Integer> persistenceSpinner = spinner(1, 10, 5);
        HBox batchSizeBox = new HBox(batchSizeSpinner, new Label("Batch Size"));
        HBox similarityBox = new HBox(similaritySpinner, new Label("Similarity"));
        HBox persistenceBox = new HBox(persistenceSpinner, new Label("Persistence"));
        CheckBox allowInputEchoCheckBox = new CheckBox("Allow Inputs in Output");
        VBox generatorOptionsBox = new VBox(batchSizeBox, similarityBox, persistenceBox, allowInputEchoCheckBox);

        Button generateButton = new Button("Generate");
        VBox generateButtonBox = new VBox(generateButton);
        generateButtonBox.setMinWidth(LEFT_COLUMN_WIDTH);
        generateButtonBox.setAlignment(Pos.CENTER_LEFT);

        HBox generatorBox = new HBox(generateButtonBox, generatorOptionsBox);
        TitledPane generatorPane = new TitledPane("Generator", generatorBox);
        generatorPane.setCollapsible(false);

        RadioButton separateBySpaceButton = new RadioButton("Words");
        RadioButton separateByLinefeedButton = new RadioButton("Lines");
        RadioButton separateByBlankLineButton = new RadioButton("Paragraphs");

        ToggleGroup outputToggleGroup = new ToggleGroup();
        List.of(separateBySpaceButton, separateByLinefeedButton, separateByBlankLineButton)
                .forEach(b -> b.setToggleGroup(outputToggleGroup));
        Label separateByLabel = new Label("Display As");
        VBox outputOptionsBox = new VBox(separateByLabel, separateBySpaceButton, separateByLinefeedButton, separateByBlankLineButton);
        outputOptionsBox.setMinWidth(LEFT_COLUMN_WIDTH);

        ScrollPane outputTextPane = new ScrollPane();
        HBox outputBox = new HBox(outputOptionsBox, outputTextPane);
        TitledPane outputPane = new TitledPane("Gibberish", outputBox);
        outputPane.setCollapsible(false);

        VBox root = new VBox(inputPane, generatorPane, outputPane);
        stage.setScene(new Scene(root));
        stage.show();
    }
}