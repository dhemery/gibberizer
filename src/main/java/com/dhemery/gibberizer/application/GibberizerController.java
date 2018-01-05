package com.dhemery.gibberizer.application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public class GibberizerController {
    private final ObservableStringValue inputText;
    private final ObservableObjectValue<Function<String, List<String>>> inputSplitter;
    private final StringExpression gibberish;
    private final ListProperty<String> gibberishList = new SimpleListProperty<>();

    public GibberizerController(ObservableStringValue inputText, ObservableObjectValue<Function<String, List<String>>> inputSplitter, ObservableStringValue outputSeparator) {
        this.inputText = inputText;
        this.inputSplitter = inputSplitter;

        gibberish = Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((outputSeparator.get()))),
                gibberishList, outputSeparator);
    }

    public StringExpression gibberish() {
        return gibberish;
    }

    public void generate() {
        List<String> inputStrings = inputSplitter.get().apply(inputText.getValue());
        gibberishList.set(FXCollections.observableList(inputStrings));
    }
}
