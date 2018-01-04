package com.dhemery.gibberizer.application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Toggle;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GibberizerController {
    private static final List<String> DEMO_GIBBERISH_LIST = IntStream.range(0, 40)
            .mapToObj(i -> "String " + i)
            .collect(toList());
    private final StringProperty gibberish = new SimpleStringProperty();
    private final ListProperty<String> gibberishListProperty;

    public GibberizerController(ReadOnlyObjectProperty<Toggle> outputFormatProperty) {
        gibberishListProperty = new SimpleListProperty<>(FXCollections.observableList(DEMO_GIBBERISH_LIST));

        StringBinding concatenatedGibberishBinding = Bindings.createStringBinding(
                () -> gibberishListProperty.stream().collect(joining((outputFormatProperty.get().getUserData().toString()))),
                gibberishListProperty, outputFormatProperty);

        gibberish.bind(concatenatedGibberishBinding);
    }

    public StringProperty gibberishProperty() {
        return gibberish;
    }
}
