package com.dhemery.gibberizer.application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GibberizerController {
    private final IntegerProperty similarity = new SimpleIntegerProperty(2);
    private final IntegerProperty persistence = new SimpleIntegerProperty(3);
    private final IntegerProperty batchSize = new SimpleIntegerProperty(4);
    private final SetProperty<String> distinctInputStrings = new SimpleSetProperty<>(FXCollections.emptyObservableSet());

    private final ObservableStringValue inputText;
    private final ObservableObjectValue<Function<String, List<String>>> inputSplitter;
    private final StringExpression gibberish;
    private final ListProperty<String> gibberishList = new SimpleListProperty<>();

    public GibberizerController(ObservableStringValue inputText, ObservableObjectValue<Function<String, List<String>>> inputSplitter, ObservableStringValue outputSeparator) {
        this.inputText = inputText;
        this.inputSplitter = inputSplitter;
        // TODO: save batchSize parameter
        // TODO: save persistence parameter
        // TODO: take similarity parameter

        gibberish = Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((outputSeparator.get()))),
                gibberishList, outputSeparator);

        // TODO: inputStrings property: (inputText, inputSplitter) -> List<String>
        // TODO: distinctInputStrings property: (inputStrings) -> Set<String>
        // TODO: nGrams property: (inputStrings, similarity) -> List<NGram>
        // TODO: starterSupplier property: (nGramList) -> Supplier<NGram>
        // TODO: successorOperator property: (nGramList) -> UnaryOperator<NGram>
        // TODO: gibberizer property: (starterSupplier, successorOperator) -> Supplier<String>
    }

    public StringExpression gibberish() {
        return gibberish;
    }

    public void generate() {
        List<String> inputStrings = inputSplitter.get().apply(inputText.getValue());
        Random random = new Random();
        Supplier<String> generator = () -> inputStrings.get(random.nextInt(inputStrings.size()));
        // TODO: Use real gibberizer to generate gibberish
        List<String> gibberishStrings = Stream.generate(generator)
                .limit(persistence.get() * batchSize.get())
                // TODO: Apply filter only if inputs are disallowed in output
                // .filter(s -> !distinctInputStrings.getValue().contains(s))
                .distinct()
                .limit(batchSize.get())
                .map(s -> s + "foo")
                .collect(toList());

        gibberishList.set(FXCollections.observableList(gibberishStrings));
    }
}
