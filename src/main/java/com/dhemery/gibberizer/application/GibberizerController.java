package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class GibberizerController {
    private static final Random RANDOM = new Random();
    private final ListProperty<String> gibberishList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Function<String, List<String>>> inputSplitter = new SimpleObjectProperty<>();

    @FXML
    private ToggleGroup inputSplitterToggles;
    @FXML
    private CheckBox splitInput;
    @FXML
    private TextArea inputText;
    @FXML
    private Spinner<Integer> batchSize;
    @FXML
    private Spinner<Integer> similarity;
    @FXML
    private Spinner<Integer> persistence;
    @FXML
    private CheckBox allowInputs;
    @FXML
    private ToggleGroup outputFormatToggles;
    @FXML
    private Text outputText;

    public void initialize() {
        StringBinding selectedInputSplitterPattern = userDataOf(inputSplitterToggles.selectedToggleProperty());
        ObjectBinding<Function<String, List<String>>> selectedInputSplitter = Bindings.createObjectBinding(
                () -> t -> List.of(t.split(selectedInputSplitterPattern.get())),
                selectedInputSplitterPattern
        );

        inputSplitter.bind(
                Bindings.when(splitInput.selectedProperty())
                .then(selectedInputSplitter)
                .otherwise(new SimpleObjectProperty<>(List::of)));

        StringBinding selectedOutputDelimiter = userDataOf(outputFormatToggles.selectedToggleProperty());
        outputText.textProperty().bind(Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((selectedOutputDelimiter.get()))),
                gibberishList, selectedOutputDelimiter
        ));
    }

    public void generate() {
        List<String> inputStrings = new ArrayList<>(inputSplitter.get().apply(inputText.textProperty().get()));
        if (inputStrings.removeIf(s -> s.length() < similarity.getValue())) ;
        if (inputStrings.isEmpty()) {
            gibberishList.clear();
            return;
        }
        Set<String> distinctInputStrings = new HashSet<>(inputStrings);
        NGramParser parser = new NGramParser(similarity.getValue());
        List<NGram> nGrams = parser.parse(inputStrings);
        List<NGram> starters = nGrams.stream().filter(NGram::isStarter).collect(toList());
        Supplier<NGram> starterSupplier = () -> selectRandom(starters);
        Map<String, List<NGram>> nGramsByPrefix = nGrams.stream().collect(groupingBy(NGram::prefix));
        Function<NGram, String> suffix = NGram::suffix;

        UnaryOperator<NGram> randomSuccessor = n -> n.isEnder() ? null : suffix.andThen(nGramsByPrefix::get).andThen(GibberizerController::selectRandom).apply(n);

        Supplier<String> gibberishSupplier = new GibberishSupplier(starterSupplier, randomSuccessor);

        List<String> gibberishStrings = Stream.generate(gibberishSupplier)
                .limit(persistence.getValue() * batchSize.getValue())
                .filter(s -> allowInputs.isSelected() || !distinctInputStrings.contains(s))
                .distinct()
                .limit(batchSize.getValue())
                .collect(toList());

        gibberishList.setAll(gibberishStrings);
    }

    private static <T> T selectRandom(List<? extends T> list) {
        if (list.isEmpty()) return null;
        return list.get(RANDOM.nextInt(list.size()));
    }

    private static StringBinding userDataOf(ReadOnlyObjectProperty<Toggle> toggle) {
        return Bindings.createStringBinding(() -> toggle.get().getUserData().toString(), toggle);
    }
}
