package com.dhemery.gibberizer.application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GibberizerController {
    private static final List<String> DEMO_GIBBERISH_LIST = IntStream.range(0, 40)
            .mapToObj(i -> "String " + i)
            .collect(toList());
    private final StringExpression gibberish;
    private final ObservableList<String> gibberishList;

    public GibberizerController(ObservableStringValue outputSeparator) {
        gibberishList = FXCollections.observableList(DEMO_GIBBERISH_LIST);

        gibberish = Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((outputSeparator.get()))),
                gibberishList, outputSeparator);
    }

    public StringExpression gibberish() {
        return gibberish;
    }
}
