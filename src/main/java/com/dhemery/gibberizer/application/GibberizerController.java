package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.beans.value.ObservableNumberValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.*;
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.beans.binding.Bindings.createStringBinding;

// TODO: CheckBox to trim input strings
// TODO: Disable Generate button if no nGrams
// TODO: Format split inputs in alternating format
public class GibberizerController {
    private static final Random RANDOM = new Random();
    private static final Function<NGram, String> SUFFIX_OF = NGram::suffix;

    private final ObjectProperty<List<String>> inputStrings = new SimpleObjectProperty<>();
    private final ObjectProperty<Set<String>> distinctInputStrings = new SimpleObjectProperty<>();

    private final IntegerProperty batchSize = new SimpleIntegerProperty();
    private final IntegerProperty persistence = new SimpleIntegerProperty();
    private final IntegerProperty similarity = new SimpleIntegerProperty();
    private final BooleanProperty allowInputs = new SimpleBooleanProperty();

    private final ObjectProperty<Supplier<String>> gibberishSupplier = new SimpleObjectProperty<>();

    private final ListProperty<String> gibberishStrings = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final IntegerProperty generatedGibberishCount = new SimpleIntegerProperty();
    private final IntegerProperty distinctGibberishCount = new SimpleIntegerProperty();

    @FXML
    private Label rawStringCountLabel;
    @FXML
    private Label runtStringCountLabel;
    @FXML
    private Label nGramCountLabel;
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
    private CheckBox acceptInputsCheckBox;
    @FXML
    private Button generateButton;
    @FXML
    private ToggleGroup outputFormatToggles;
    @FXML
    private Text outputText;
    @FXML
    private Label generatedGibberishCountLabel;
    @FXML
    private Label distinctGibberishCountLabel;
    @FXML
    private Label acceptedGibberishCountLabel;

    public void initialize() {
        batchSize.bind(batchSizeSpinner.valueProperty());
        persistence.bind(persistenceSpinner.valueProperty());
        similarity.bind(similaritySpinner.valueProperty());
        allowInputs.bind(acceptInputsCheckBox.selectedProperty());

        ReadOnlyObjectProperty<Toggle> selectedInputSplitterToggle = inputSplitterToggles.selectedToggleProperty();
        StringBinding selectedInputSplitterPattern = userDataOf(selectedInputSplitterToggle);
        ObjectBinding<Function<String, List<String>>> selectedInputSplitter = createObjectBinding(
                () -> t -> List.of(t.split(selectedInputSplitterPattern.get())).stream().map(String::trim).filter(s -> !s.isEmpty()).collect(toList()),
                selectedInputSplitterPattern
        );

        ObjectBinding<Function<String, List<String>>> inputSplitter = Bindings
                .when(splitInputCheckBox.selectedProperty())
                .then(selectedInputSplitter)
                .otherwise(new SimpleObjectProperty<>(t -> t.trim().isEmpty() ? Collections.emptyList() : List.of(t.trim())));

        StringProperty inputText = inputTextArea.textProperty();
        ObjectBinding<List<String>> rawInputStrings = createObjectBinding(
                () -> inputSplitter.get().apply(inputText.get()),
                inputSplitter, inputText
        );
        inputStrings.bind(createObjectBinding(
                () -> rawInputStrings.get().stream().filter(s -> s.length() >= similarity.get()).collect(toList()),
                rawInputStrings, similarity
        ));
        distinctInputStrings.bind(createObjectBinding(
                () -> new HashSet<>(inputStrings.get()),
                inputStrings
        ));

        ObjectBinding<NGramParser> parser = createObjectBinding(
                () -> new NGramParser(similarity.get()),
                similarity
        );
        ObjectBinding<List<NGram>> nGrams = createObjectBinding(
                () -> parser.get().parse(inputStrings.get()),
                parser, inputStrings
        );
        ObjectBinding<List<NGram>> starters = createObjectBinding(
                () -> nGrams.get().stream().filter(NGram::isStarter).collect(toList()),
                nGrams
        );
        ObjectBinding<Supplier<NGram>> starterSupplier = createObjectBinding(
                () -> () -> selectRandom(starters.get()),
                starters
        );
        ObjectBinding<Map<String, List<NGram>>> nGramsByPrefix = createObjectBinding(
                () -> nGrams.get().stream().collect(groupingBy(NGram::prefix)),
                nGrams
        );
        ObjectBinding<UnaryOperator<NGram>> successorOperator = createObjectBinding(
                () -> n -> n.isEnder() ? null : SUFFIX_OF.andThen(nGramsByPrefix.get()::get).andThen(GibberizerController::selectRandom).apply(n),
                nGramsByPrefix
        );
        gibberishSupplier.bind(createObjectBinding(
                () -> new GibberishSupplier(starterSupplier.get(), successorOperator.get()),
                starterSupplier, successorOperator
        ));

        StringBinding selectedOutputDelimiter = userDataOf(outputFormatToggles.selectedToggleProperty());

        IntegerBinding inputStringCount = Bindings.createIntegerBinding(
                () -> inputStrings.get().size(),
                inputStrings
        );

        IntegerBinding nGramCount = Bindings.createIntegerBinding(
                () -> nGrams.get().size(),
                nGrams
        );

        IntegerBinding rawInputStringCount = Bindings.createIntegerBinding(
                () -> rawInputStrings.get().size(),
                rawInputStrings
        );

        StringBinding selectedInputSplitterText = createStringBinding(
                () -> ((RadioButton) selectedInputSplitterToggle.get()).textProperty().get(),
                selectedInputSplitterToggle);
        StringBinding rawStringTypeLabel = Bindings
                .when(splitInputCheckBox.selectedProperty())
                .then(selectedInputSplitterText)
                .otherwise("Strings");
        rawStringCountLabel.textProperty().bind(createStringBinding(
                () -> rawStringTypeLabel.get() + ": " + rawInputStringCount.intValue(),
                rawStringTypeLabel, rawInputStringCount
        ));
        ObservableNumberValue runtStringCount = rawInputStringCount.subtract(inputStringCount);
        runtStringCountLabel.textProperty().bind(createStringBinding(
                () -> "Runts: " + runtStringCount.intValue(),
                runtStringCount
        ));
        nGramCountLabel.textProperty().bind(createStringBinding(
                () -> format("%d-Grams: %d", similarity.get(), nGramCount.intValue()),
                similarity, nGramCount
        ));

        generateButton.disableProperty().bind(nGramCount.lessThan(1));

        generatedGibberishCountLabel.textProperty().bind(createStringBinding(
                () -> "Generated: " + generatedGibberishCount.get(),
                generatedGibberishCount
        ));
        distinctGibberishCountLabel.textProperty().bind(createStringBinding(
                () -> "Distinct: " + distinctGibberishCount.get(),
                distinctGibberishCount
        ));
        IntegerBinding acceptedGibberishCount = Bindings.createIntegerBinding(
                () -> gibberishStrings.get().size(),
                gibberishStrings
        );
        acceptedGibberishCountLabel.textProperty().bind(createStringBinding(
                () -> "Accepted: " + acceptedGibberishCount.get(),
                acceptedGibberishCount
        ));
        outputText.textProperty().bind(createStringBinding(
                () -> gibberishStrings.stream().collect(joining((selectedOutputDelimiter.get()))),
                gibberishStrings, selectedOutputDelimiter
        ));
    }

    public void generate() {
        gibberishStrings.clear();
        generatedGibberishCount.set(0);
        distinctGibberishCount.set(0);
        if (inputStrings.get().isEmpty()) return;

        Stream.generate(gibberishSupplier.get())
                .peek(s -> generatedGibberishCount.set(generatedGibberishCount.get()+1))
                .limit(persistence.get() * batchSize.get())
                .distinct()
                .peek(s -> distinctGibberishCount.set(distinctGibberishCount.get()+1))
                .filter(s -> allowInputs.get() || !distinctInputStrings.get().contains(s))
                .limit(batchSize.get())
                .forEach(gibberishStrings::add);
    }

    private static <T> T selectRandom(List<? extends T> list) {
        return list.isEmpty() ? null : list.get(RANDOM.nextInt(list.size()));
    }

    private static StringBinding userDataOf(ReadOnlyObjectProperty<Toggle> toggle) {
        return createStringBinding(() -> toggle.get().getUserData().toString(), toggle);
    }
}
