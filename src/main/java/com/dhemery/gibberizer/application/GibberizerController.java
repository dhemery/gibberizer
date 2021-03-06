package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static com.dhemery.gibberizer.application.Binders.createListBinding;
import static com.dhemery.gibberizer.application.Binders.createSetBinding;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;
import static javafx.beans.binding.Bindings.*;

// TODO: Input parameter: CheckBox to trim input strings
// TODO: Input text area: Color adjacent strings to indicate where splitting occurs
// TODO: Allow editing spinner values
// TODO: Validate spinner value edits
public class GibberizerController {
    private static final Random RANDOM = new Random();
    private static final Predicate<NGram> HAS_SUCCESSOR = n -> !n.isEnder();
    private static final Function<NGram, String> SUFFIX = NGram::suffix;

    private final ListProperty<String> inputStrings = new SimpleListProperty<>();
    private final SetProperty<String> distinctInputStrings = new SimpleSetProperty<>();

    private final IntegerProperty batchSize = new SimpleIntegerProperty();
    private final IntegerProperty persistence = new SimpleIntegerProperty();
    private final IntegerProperty nGramSize = new SimpleIntegerProperty();
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
    private TextArea inputTextArea;
    @FXML
    private Spinner<Integer> batchSizeSpinner;
    @FXML
    private Spinner<Integer> nGramSizeSpinner;
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
        nGramSize.bind(nGramSizeSpinner.valueProperty());
        allowInputs.bind(acceptInputsCheckBox.selectedProperty());
        StringExpression inputSplitterPattern = property(inputSplitterToggles.selectedToggleProperty(), "splitterPattern");
        StringExpression inputSplitterName = property(inputSplitterToggles.selectedToggleProperty(), "splitterName");

        ObjectExpression<Function<String, List<String>>> inputSplitter = createObjectBinding(
                () -> t -> split(t, inputSplitterPattern.get()),
                inputSplitterPattern
        );
        StringExpression inputText = inputTextArea.textProperty();

        ListExpression<String> rawInputStrings = createListBinding(
                () -> FXCollections.observableList(inputSplitter.get().apply(inputText.get())),
                inputText, inputSplitter
        );

        inputStrings.bind(createListBinding(
                () -> rawInputStrings.filtered(s -> s.length() >= nGramSize.get()),
                rawInputStrings, nGramSize
        ));

        distinctInputStrings.bind(createSetBinding(
                () -> FXCollections.observableSet(new HashSet<>(rawInputStrings.get())),
                rawInputStrings
        ));

        ObjectExpression<NGramParser> parser = createObjectBinding(
                () -> new NGramParser(nGramSize.get()),
                nGramSize
        );

        ListExpression<NGram> nGrams = createListBinding(
                () -> FXCollections.observableList(parser.get().parse(inputStrings.get())),
                inputStrings, parser
        );

        NumberExpression distinctNGramCount = createLongBinding(
                () -> nGrams.stream().map(String::valueOf).distinct().count(),
                nGrams
        );

        IntegerExpression rawInputStringCount = rawInputStrings.sizeProperty();

        StringExpression nGramTypeName = createStringBinding(
                () -> format("%d-Grams", nGramSize.get()),
                nGramSize
        );

        showCounter(rawStringCountLabel, inputSplitterName, rawInputStringCount);
        showCounter(runtStringCountLabel, "Runts", rawInputStringCount.subtract(inputStrings.sizeProperty()));
        showCounter(nGramCountLabel, nGramTypeName, distinctNGramCount);

        generateButton.disableProperty().bind(nGrams.sizeProperty().lessThan(1));

        gibberishSupplier.bind(createObjectBinding(() -> gibberishSupplierFor(nGrams), nGrams));

        IntegerExpression acceptedGibberishCount = gibberishStrings.sizeProperty();

        showCounter(generatedGibberishCountLabel, "Generated", generatedGibberishCount);
        showCounter(distinctGibberishCountLabel, "Distinct", distinctGibberishCount);
        showCounter(acceptedGibberishCountLabel, "Accepted", acceptedGibberishCount);

        StringExpression selectedOutputDelimiter = property(outputFormatToggles.selectedToggleProperty(), "delimiter");

        outputText.textProperty().bind(createStringBinding(
                () -> gibberishStrings.stream().collect(joining((selectedOutputDelimiter.get()))),
                gibberishStrings, selectedOutputDelimiter
        ));
    }

    private Supplier<String> gibberishSupplierFor(List<NGram> nGrams) {
        List<NGram> starters = nGrams.stream().filter(NGram::isStarter).collect(toList());
        Supplier<NGram> starterSupplier = () -> selectRandom(starters);
        return new GibberishSupplier(starterSupplier, successorOperatorFor(nGrams));
    }

    public void generate() {
        gibberishStrings.clear();
        generatedGibberishCount.set(0);
        distinctGibberishCount.set(0);
        if (inputStrings.get().isEmpty()) return;

        Stream.generate(gibberishSupplier.get())
                .peek(s -> generatedGibberishCount.set(generatedGibberishCount.get() + 1))
                .limit(persistence.get() * batchSize.get())
                .distinct()
                .peek(s -> distinctGibberishCount.set(distinctGibberishCount.get() + 1))
                .filter(s -> allowInputs.get() || !distinctInputStrings.get().contains(s))
                .limit(batchSize.get())
                .forEach(gibberishStrings::add);
    }

    private static StringExpression property(ObservableObjectValue<Toggle> toggle, String propertyName) {
        return createStringBinding(
                () -> String.valueOf(toggle.get().getProperties().get(propertyName)),
                toggle);
    }

    private static <T> T selectRandom(List<? extends T> list) {
        return list.isEmpty() ? null : list.get(RANDOM.nextInt(list.size()));
    }

    private static void showCounter(Label label, String name, ObservableNumberValue counter) {
        showCounter(label, createStringBinding(() -> name), counter);
    }

    private static void showCounter(Label label, ObservableStringValue name, ObservableNumberValue counter) {
        label.textProperty().bind(createStringBinding(
                () -> format("%s: %d", name.get(), counter.intValue()),
                name, counter
        ));
    }

    private static List<String> split(String text, String pattern) {
        return Arrays.stream(text.split(pattern))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(toList());
    }

    private static UnaryOperator<NGram> successorOperatorFor(List<NGram> nGrams) {
        Map<String, List<NGram>> nGramsByPrefix = nGrams.stream().collect(groupingBy(NGram::prefix));
        return n -> Optional.of(n)
                .filter(HAS_SUCCESSOR)
                .map(SUFFIX)
                .map(nGramsByPrefix::get)
                .map(GibberizerController::selectRandom)
                .orElse(null);
    }
}
