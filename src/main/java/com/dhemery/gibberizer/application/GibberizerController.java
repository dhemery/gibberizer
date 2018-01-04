package com.dhemery.gibberizer.application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GibberizerController {
    private static final List<String> DEMO_GIBBERISH_LIST = IntStream.range(0, 40)
            .mapToObj(i -> "String " + i)
            .collect(toList());
    private final StringProperty gibberish = new SimpleStringProperty();
    private final ListProperty<String> gibberishList;

    public GibberizerController(ObservableStringValue outputSeparator) {
        gibberishList = new SimpleListProperty<>(FXCollections.observableList(DEMO_GIBBERISH_LIST));

        StringBinding concatenatedGibberishBinding = Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((outputSeparator.get()))),
                gibberishList, outputSeparator);

        gibberish.bind(concatenatedGibberishBinding);
    }

    public StringProperty gibberishProperty() {
        return gibberish;
    }
}
