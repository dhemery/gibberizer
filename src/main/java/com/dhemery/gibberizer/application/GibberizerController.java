package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

// TODO: Display count of strings
// TODO: Display count of runt strings
// TODO: Display count of nGrams
// TODO: Disable count of distinct nGrams
// TODO: Disable Generate button if no nGrams
// TODO: Display count of strings generated
// TODO: Display count of acceptable strings generated
// TODO: Display count of distinct acceptable strings generated
// TODO: Format split inputs in alternating format
public class GibberizerController {
    private static final Random RANDOM = new Random();
    private final ListProperty<String> gibberishList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Function<String, List<String>>> inputSplitter = new SimpleObjectProperty<>();
    private final ObjectProperty<List<String>> inputStrings = new SimpleObjectProperty<>();
    private final ObjectProperty<Set<String>> distinctInputStrings = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> similarity = new SimpleObjectProperty<>();

    @FXML
    private ToggleGroup inputSplitterToggles;
    @FXML
    private CheckBox splitInputCheckBox;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private Spinner<Integer> batchSizeSpinner;
    @FXML
    private Spinner<Integer> similaritySpinner;
    @FXML
    private Spinner<Integer> persistenceSpinner;
    @FXML
    private CheckBox allowInputsCheckBox;
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

        inputSplitter.bind(Bindings
                .when(splitInputCheckBox.selectedProperty())
                .then(selectedInputSplitter)
                .otherwise(new SimpleObjectProperty<>(List::of)
                ));

        StringProperty inputText = inputTextArea.textProperty();
        similarity.bind(similaritySpinner.valueProperty());

        ObjectBinding<List<String>> rawInputStrings = Bindings.createObjectBinding(
                () -> inputSplitter.get().apply(inputText.get()),
                inputSplitter, inputText
        );
        inputStrings.bind(Bindings.createObjectBinding(
                () -> rawInputStrings.get().stream().filter(s -> s.length() >= similarity.get()).collect(toList()),
                rawInputStrings, similarity
        ));
        distinctInputStrings.bind(Bindings.createObjectBinding(
                () -> new HashSet<>(inputStrings.get()),
                inputStrings
        ));

        StringBinding selectedOutputDelimiter = userDataOf(outputFormatToggles.selectedToggleProperty());
        outputText.textProperty().bind(Bindings.createStringBinding(
                () -> gibberishList.stream().collect(joining((selectedOutputDelimiter.get()))),
                gibberishList, selectedOutputDelimiter
        ));
    }

    public void generate() {
        if (inputStrings.get().isEmpty()) {
            gibberishList.clear();
            return;
        }

        NGramParser parser = new NGramParser(similarity.get());
        List<NGram> nGrams = parser.parse(inputStrings.get());
        List<NGram> starters = nGrams.stream().filter(NGram::isStarter).collect(toList());
        Supplier<NGram> starterSupplier = () -> selectRandom(starters);
        Map<String, List<NGram>> nGramsByPrefix = nGrams.stream().collect(groupingBy(NGram::prefix));
        Function<NGram, String> suffix = NGram::suffix;

        UnaryOperator<NGram> randomSuccessor = n -> n.isEnder() ? null : suffix.andThen(nGramsByPrefix::get).andThen(GibberizerController::selectRandom).apply(n);

        Supplier<String> gibberishSupplier = new GibberishSupplier(starterSupplier, randomSuccessor);

        List<String> gibberishStrings = Stream.generate(gibberishSupplier)
                .limit(persistenceSpinner.getValue() * batchSizeSpinner.getValue())
                .filter(s -> allowInputsCheckBox.isSelected() || !distinctInputStrings.get().contains(s))
                .distinct()
                .limit(batchSizeSpinner.getValue())
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
