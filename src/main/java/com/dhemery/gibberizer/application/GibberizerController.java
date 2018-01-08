package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.Binding;
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
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.beans.binding.Bindings.createStringBinding;

// TODO: CheckBox to trim input strings
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

    private final ObjectProperty<List<String>> inputStrings = new SimpleObjectProperty<>();
    private final ObjectProperty<Set<String>> distinctInputStrings = new SimpleObjectProperty<>();

    private final IntegerProperty batchSize = new SimpleIntegerProperty();
    private final IntegerProperty persistence = new SimpleIntegerProperty();
    private final IntegerProperty similarity = new SimpleIntegerProperty();
    private final BooleanProperty allowInputs = new SimpleBooleanProperty();

    private final ObjectProperty<Supplier<String>> gibberishSupplier = new SimpleObjectProperty<>();
    private final ListProperty<String> gibberishStrings = new SimpleListProperty<>(FXCollections.observableArrayList());

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
    private Function<NGram, String> SUFFIX_OF = NGram::suffix;

    public void initialize() {
        batchSize.bind(batchSizeSpinner.valueProperty());
        persistence.bind(persistenceSpinner.valueProperty());
        similarity.bind(similaritySpinner.valueProperty());
        allowInputs.bind(allowInputsCheckBox.selectedProperty());

        StringBinding selectedInputSplitterPattern = userDataOf(inputSplitterToggles.selectedToggleProperty());
        ObjectBinding<Function<String, List<String>>> selectedInputSplitter = createObjectBinding(
                () -> t -> List.of(t.split(selectedInputSplitterPattern.get())),
                selectedInputSplitterPattern
        );

        ObjectBinding<Function<String, List<String>>> inputSplitter = Bindings
                .when(splitInputCheckBox.selectedProperty())
                .then(selectedInputSplitter)
                .otherwise(new SimpleObjectProperty<>(List::of));

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
        outputText.textProperty().bind(createStringBinding(
                () -> gibberishStrings.stream().collect(joining((selectedOutputDelimiter.get()))),
                gibberishStrings, selectedOutputDelimiter
        ));
    }

    public void generate() {
        gibberishStrings.clear();
        if (inputStrings.get().isEmpty()) return;

        List<String> gibberishStrings = Stream.generate(gibberishSupplier.get())
                .limit(persistence.get() * batchSize.get())
                .filter(s -> allowInputs.get() || !distinctInputStrings.get().contains(s))
                .distinct()
                .limit(batchSize.get())
                .collect(toList());
        this.gibberishStrings.setAll(gibberishStrings);
    }

    private static <T> T selectRandom(List<? extends T> list) {
        return list.isEmpty() ? null : list.get(RANDOM.nextInt(list.size()));
    }

    private static StringBinding userDataOf(ReadOnlyObjectProperty<Toggle> toggle) {
        return createStringBinding(() -> toggle.get().getUserData().toString(), toggle);
    }
}
