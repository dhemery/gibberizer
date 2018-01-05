package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class GibberizerController {
    private final IntegerProperty similarity = new SimpleIntegerProperty(4);
    private final IntegerProperty persistence = new SimpleIntegerProperty(20);
    private final IntegerProperty batchSize = new SimpleIntegerProperty(20);

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
        NGramParser parser = new NGramParser(similarity.get());
        List<String> inputStrings = inputSplitter.get().apply(inputText.getValue());
        List<NGram> nGrams = parser.parse(inputStrings);
        List<NGram> starters = nGrams.stream().filter(NGram::isStarter).collect(toList());
        Set<String> distinctInputStrings = new HashSet<>(inputStrings);
        Supplier<NGram> randomStarter = () -> starters.get(new Random().nextInt(starters.size()));

        Function<List<NGram>, NGram> selectRandom = l -> l.get(new Random().nextInt(l.size()));

        Map<String, List<NGram>> nGramsByPrefix = nGrams.stream()
                .collect(groupingBy(NGram::prefix));

        Function<NGram, String> suffix = NGram::suffix;

        Function<NGram, NGram> randomSuccessor = suffix
                .andThen(nGramsByPrefix::get)
                .andThen(selectRandom);

        UnaryOperator<NGram> successorOperator = n -> n.isEnder() ? null : randomSuccessor.apply(n);

        GibberishSupplier gibberizer = new GibberishSupplier(randomStarter, successorOperator);
        List<String> gibberishStrings = Stream.generate(gibberizer)
                .limit(persistence.get() * batchSize.get())
                .filter(s -> !distinctInputStrings.contains(s))
                .distinct()
                .limit(batchSize.get())
                .collect(toList());

        gibberishList.set(FXCollections.observableList(gibberishStrings));
    }
}
