package com.dhemery.gibberizer.application;

import com.dhemery.gibberizer.core.GibberishSupplier;
import com.dhemery.gibberizer.core.NGram;
import com.dhemery.gibberizer.core.NGramParser;
import javafx.beans.binding.ListBinding;
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
    private static final Function<String, List<String>> PARSE_INPUT_AS_WORDS = s -> Arrays.asList(s.split("\\s+"));
    private static final Function<String, List<String>> PARSE_INPUT_AS_LINES = s -> Arrays.asList(s.split("\\s*\\n\\s*"));
    private static final Function<String, List<String>> PARSE_INPUT_AS_STRING = Arrays::asList;
    private static final Random RANDOM = new Random();
    private final ListProperty<String> gibberishList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<Function<String, List<String>>> inputSplitter = new SimpleObjectProperty<>();

    @FXML
    private ToggleGroup inputSplitterToggles;
    @FXML
    private CheckBox splitInput;
    @FXML
    private Toggle splitIntoWords;
    @FXML
    private Toggle splitIntoLines;
    @FXML
    private TextArea inputText;
    @FXML
    private Button generateButton;
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
        Map<Toggle, Function<String, List<String>>> splittersByToggle = new HashMap<>();
        splittersByToggle.put(splitIntoWords, PARSE_INPUT_AS_WORDS);
        splittersByToggle.put(splitIntoLines, PARSE_INPUT_AS_LINES);

        inputSplitter.bind(inputSplitterBinding(splitInput.selectedProperty(), inputSplitterToggles.selectedToggleProperty(), splittersByToggle));
        outputText.textProperty().bind(outputTextBinding(gibberishList, outputFormatToggles.selectedToggleProperty()));
    }

    public void generate() {
        List<String> errorLines = new ArrayList<>();
        List<String> inputStrings = new ArrayList<>(inputSplitter.get().apply(inputText.textProperty().get()));
        if (inputStrings.removeIf(s -> s.length() < similarity.getValue())) ;
        if (inputStrings.isEmpty()) {
            errorLines.add("Gibberizer could not generate gibberish from such short strings.");
            errorLines.add("Try:");
            if (similarity.getValue() > 2) errorLines.add("- decreasing the similarity");
            errorLines.add("- writing more text");
            if (splitInput.isSelected()) {
                if (splitIntoWords.isSelected()) errorLines.add("- splitting the text into lines instead of words");
                errorLines.add("- not splitting the text");
            }
            gibberishList.setAll(errorLines.stream().collect(joining("\n")));
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

        if (gibberishStrings.isEmpty()) {
            gibberishList.setAll("Could not generate gibberish from the given text and settings.");
            return;
        }

        gibberishList.setAll(gibberishStrings);
    }

    private void warn(String s) {
    }

    private static <T> T selectRandom(List<? extends T> list) {
        if (list.isEmpty()) return null;
        return list.get(RANDOM.nextInt(list.size()));
    }

    private static ListBinding<String> inputStringsBinding(StringProperty text, ObjectProperty<Function<String, List<String>>> inputSplitter) {
        return new ListBinding<>() {
            {
                super.bind(text, inputSplitter);
            }

            @Override
            protected ObservableList<String> computeValue() {
                return FXCollections.observableList(inputSplitter.get().apply(text.get()));
            }
        };
    }

    private static ObjectBinding<? extends Function<String, List<String>>> inputSplitterBinding(BooleanProperty shouldSplit, ReadOnlyObjectProperty<Toggle> selectedSplitterToggle, Map<Toggle, Function<String, List<String>>> splittersByToggle) {
        return new ObjectBinding<>() {
            {
                super.bind(shouldSplit, selectedSplitterToggle);
            }

            @Override
            protected Function<String, List<String>> computeValue() {
                if (shouldSplit.get()) {
                    return splittersByToggle.get(selectedSplitterToggle.get());
                }
                return PARSE_INPUT_AS_STRING;
            }
        };
    }

    private static StringBinding outputTextBinding(ObservableList<String> gibberishList, ReadOnlyObjectProperty<Toggle> selectedOutputFormatToggle) {
        return new StringBinding() {
            {
                super.bind(gibberishList, selectedOutputFormatToggle);
            }

            @Override
            protected String computeValue() {
                return gibberishList.stream().collect(joining((selectedOutputFormatToggle.get().getUserData().toString())));
            }
        };
    }
}
