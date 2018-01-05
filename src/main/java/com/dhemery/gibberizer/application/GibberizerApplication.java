package com.dhemery.gibberizer.application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableObjectValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class GibberizerApplication extends Application {
    private static final double LEFT_COLUMN_WIDTH = 120;

    public static void main(String args[]) {
        launch(GibberizerApplication.class, args);
    }

    private static Spinner<Integer> spinner(int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinner.setMaxWidth(80);
        return spinner;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gibberizer");

        CheckBox splitInputCheckBox = new CheckBox("Split Text");
        RadioButton splitIntoWordsButton = new RadioButton("into Words");
        splitIntoWordsButton.setUserData("\\s+");
        RadioButton splitIntoLinesButton = new RadioButton("into Lines");
        splitIntoLinesButton.setUserData("\\s*\\n\\s*");
        ToggleGroup inputSplitterToggleGroup = new ToggleGroup();
        List.of(splitIntoWordsButton, splitIntoLinesButton)
                .forEach(b -> b.setToggleGroup(inputSplitterToggleGroup));

        splitIntoWordsButton.disableProperty().bind(splitInputCheckBox.selectedProperty().not());
        splitIntoLinesButton.disableProperty().bind(splitInputCheckBox.selectedProperty().not());

        inputSplitterToggleGroup.selectToggle(splitIntoWordsButton);

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

        RadioButton formatAsWordsButton = new RadioButton("Words");
        formatAsWordsButton.setUserData(" ");
        RadioButton formatAsLinesButton = new RadioButton("Lines");
        formatAsLinesButton.setUserData("\n");
        RadioButton formatAsParagraphsButton = new RadioButton("Paragraphs");
        formatAsParagraphsButton.setUserData("\n\n");

        ToggleGroup outputFormatToggleGroup = new ToggleGroup();
        List.of(formatAsWordsButton, formatAsLinesButton, formatAsParagraphsButton)
                .forEach(b -> b.setToggleGroup(outputFormatToggleGroup));
        outputFormatToggleGroup.selectToggle(formatAsWordsButton);
        Label formatBoxLabel = new Label("Display As");
        VBox outputOptionsBox = new VBox(formatBoxLabel, formatAsWordsButton, formatAsLinesButton, formatAsParagraphsButton);
        outputOptionsBox.setMinWidth(LEFT_COLUMN_WIDTH);

        Text outputText = new Text();
        ScrollPane outputTextPane = new ScrollPane(outputText);
        outputTextPane.setFitToWidth(true);
        outputTextPane.setFitToHeight(true);
        outputTextPane.setMinSize(500, 200);
        HBox outputBox = new HBox(outputOptionsBox, outputTextPane);
        TitledPane outputPane = new TitledPane("Gibberish", outputBox);
        outputPane.setCollapsible(false);

        ObservableObjectValue<Toggle> selectedOutputFormatToggle = outputFormatToggleGroup.selectedToggleProperty();
        StringBinding outputDelimiter = Bindings.createStringBinding(() -> selectedOutputFormatToggle.get().getUserData().toString(), selectedOutputFormatToggle);

        ObservableObjectValue<Toggle> selectedInputSplitterToggle = inputSplitterToggleGroup.selectedToggleProperty();
        StringBinding selectedInputSplitterPattern = Bindings.createStringBinding(() -> selectedInputSplitterToggle.get().getUserData().toString(), selectedInputSplitterToggle);

        ObservableObjectValue<Function<String, List<String>>> inputSplitter = Bindings
                .when(splitInputCheckBox.selectedProperty())
                .then((Function<String,List<String>>) s -> Arrays.asList(s.split(selectedInputSplitterPattern.getValue())))
                .otherwise(List::of);

        GibberizerController controller = new GibberizerController(inputTextArea.textProperty(), inputSplitter, outputDelimiter);
        outputText.textProperty().bind(controller.gibberish());
        generateButton.setOnAction(e -> controller.generate());

        VBox root = new VBox(inputPane, generatorPane, outputPane);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
