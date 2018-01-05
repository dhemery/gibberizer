package com.dhemery.gibberizer.application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class GibberizerController {
    private final StringExpression gibberish;
    private final ListProperty<String> gibberishList = new SimpleListProperty<>();
    private final StringProperty inputSeparator;
    private final ObservableStringValue inputText;

    public GibberizerController(ObservableStringValue inputText, ObservableStringValue outputSeparator) {
        this.inputText = inputText;
        inputSeparator = new SimpleStringProperty("\\W+");

        gibberish = Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((outputSeparator.get()))),
                gibberishList, outputSeparator);
    }

    public StringExpression gibberish() {
        return gibberish;
    }

    public void generate() {
        List<String> inputStrings = Arrays.asList(inputText.getValue().split(inputSeparator.getValue()));
        gibberishList.set(FXCollections.observableList(inputStrings));
    }
}
