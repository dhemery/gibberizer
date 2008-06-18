package com.dhemery.gibberizer;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.dhemery.gibberizer.StringJoiner.JoinStyle;
import com.dhemery.gibberizer.StringSplitter.SplitStyle;

public class GibberizerWindow extends ApplicationWindow {
	private static final int interWidgetMargin = 3;
	private static final int interGroupMargin = 10;

	public static void main(String[] args) {
		GibberizerWindow window = new GibberizerWindow();
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
	}

	private Text inputText;
	private Text outputText;

	private Gibberizer gibberizer;

	public GibberizerWindow() {
		super(null);
	}

	private Button createButton(Composite parent, String name, int type, boolean isSelected, String toolTipText) {
		Button button = new Button(parent, type);
		button.setText(name);
		button.setSelection(isSelected);
		button.setToolTipText(toolTipText);
		return button;
	}

	private Button createCheckBoxButton(Composite parent, String name, boolean isSelected, String toolTipText) {
		return createButton(parent, name, SWT.CHECK, isSelected, toolTipText);
	}

	@Override
	protected Control createContents(Composite parent) {
		getShell().setText("Gibberizer");

		Composite top = new Composite(parent, SWT.NONE);
		initializeGibberizer();
		initializeWindow(top);

		return top;
	}

	private Group createGridGroup(Composite parent, String name, String toolTipText) {
		return createGroup(parent, name, toolTipText);
	}

	private GridLayout createGridLayout(int columnCount, int horizontalSpacing,
			int verticalSpacing) {
		GridLayout layout = new GridLayout(columnCount, false);
		layout.marginHeight = interGroupMargin;
		layout.marginWidth = interGroupMargin;
		layout.horizontalSpacing = horizontalSpacing;
		layout.verticalSpacing = horizontalSpacing;
		return layout;
	}

	private Group createGroup(Composite parent, String name, String toolTipText) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(name);
		group.setToolTipText(toolTipText);
		return group;
	}

	private Label createLabel(Composite parent, String name) {
		Label label = new Label(parent, SWT.SHADOW_IN);
		label.setText(name);
		return label;
	}

	private Button createPushButton(Composite parent, String name, String toolTipText) {
		return createButton(parent, name, SWT.PUSH, false, toolTipText);
	}

	private Button createRadioButton(Composite parent, String name, Object data, boolean isSelected, String toolTipText) {
		Button button = createButton(parent, name, SWT.RADIO, isSelected, toolTipText);
		button.setData(data);
		return button;
	}

	public String getInputText() {
		return inputText.getText();
	}

	private Group initializeBuildParametersGroup(Composite parent) {
		Group group = createGridGroup(parent, "Build", "These parameters tell Gibberizer how to make strings of gibberish.");
		group.setLayout(createGridLayout(2, interWidgetMargin,
				interWidgetMargin));

		Label buildCountLabel = createLabel(group, "Number of Gibs:");
		Spinner buildCountSpinner = new Spinner(group, SWT.DEFAULT);
		buildCountSpinner.setValues(gibberizer.getNumberOfStringsToBuild(), 1, 1000, 0, 1, 10);
		buildCountSpinner.addSelectionListener(new BuildCountListener(gibberizer));

		Label familiarityLabel = createLabel(group, "Familiarity:");
		Spinner familiaritySpinner = new Spinner(group, SWT.DEFAULT);
		familiaritySpinner.setValues(gibberizer.getNgramLength(), 1, 20, 0, 1, 10);
		familiaritySpinner.addSelectionListener(new NgramLengthListener(gibberizer));

		Label persistenceLabel = createLabel(group, "Persistence:");
		Spinner persistenceSpinner = new Spinner(group, SWT.DEFAULT);
		persistenceSpinner.setValues(gibberizer.getPersistence(), 1, 10, 0, 1, 1);
		persistenceSpinner.addSelectionListener(new PersistenceListener(gibberizer));

		buildCountLabel.setLayoutData(new GridData(SWT.END, SWT.CENTER, false,
				false));
		familiarityLabel.setLayoutData(new GridData(SWT.END, SWT.CENTER, false,
				false));
		persistenceLabel.setLayoutData(new GridData(SWT.END, SWT.CENTER, false,
				false));

		return group;
	}

	private Group initializeFilterParametersGroup(Composite parent) {
		Group group = createGridGroup(parent, "Filters", "These parameters tell Gibberizer what kinds of strings it is allowed to generate.");
		group.setLayout(createGridLayout(1, interWidgetMargin,
				interWidgetMargin));

		Button allowInputEchoButton = createCheckBoxButton(group,
				"Allow input echo", gibberizer.getAllowInputEcho(), "Check to allow Gibberizer to generate a string that matches an input string.\nUncheck to force Gibberizer to generate strings that do not appear in the input.");
		Button allowDuplicatesButton = createCheckBoxButton(group,
				"Allow duplicates", gibberizer.getAllowDuplicates(), "Check to allow Gibberizer to generate multiple copies of a string.\nUncheck to force Gibberizer to generate unique strings.");

		allowInputEchoButton
				.addSelectionListener(new AllowInputEchoListener(
						gibberizer));
		allowDuplicatesButton
				.addSelectionListener(new AllowDuplicatesListener(
						gibberizer));

		return group;
	}

	private Button initializeGibberizeButton(Composite parent) {
		Button button = createPushButton(parent, "Gibberize", "Generate strings of gibberish.");

		return button;
	}

	private void initializeGibberizer() {
		gibberizer = new Gibberizer();
	}

	private Group initializeInputFormatParametersGroup(Composite parent) {
		Group group = createGridGroup(parent, "Read input as:", "Select how Gibberizer will translate your input text into strings.");
		group.setLayout(createGridLayout(1, interWidgetMargin,
				interWidgetMargin));

		Button whiteSpaceButton = createRadioButton(group, "Words",
				SplitStyle.WORDS, gibberizer.getSplitStyle() == SplitStyle.WORDS, "Gibberizer will read the input as words.\nThat is, as strings of characters separated by white space.");
		Button newLineButton = createRadioButton(group, "Lines",
				SplitStyle.LINES, gibberizer.getSplitStyle() == SplitStyle.LINES, "Gibberizer will read the input as lines.");
		Button dontSplitButton = createRadioButton(group, "One String",
				SplitStyle.ONE_STRING, gibberizer.getSplitStyle() == SplitStyle.ONE_STRING, "Gibberizer will read the input as a single string.");

		SplitStyleListener listener = new SplitStyleListener(
				gibberizer);
		whiteSpaceButton.addSelectionListener(listener);
		newLineButton.addSelectionListener(listener);
		dontSplitButton.addSelectionListener(listener);

		whiteSpaceButton.setSelection(true);
		gibberizer.setSplitStyle((SplitStyle) whiteSpaceButton.getData());

		return group;
	}

	private Text createInputText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		text.setEditable(true);
		text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		text.setForeground(text.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));

		return text;
	}

	private Group initializeInputTextGroup(Composite parent) {
		Group group = createGridGroup(parent, "Input:", "");
		group.setLayout(createGridLayout(1, 0, 0));

		inputText = createInputText(group);

		inputText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		return group;
	}

	private Group initializeOutputFormatParametersGroup(Composite parent) {
		Group group = createGridGroup(parent, "Separate output strings with:", "Select how Gibberizer will format its output.");
		group.setLayout(createGridLayout(1, interWidgetMargin,
				interWidgetMargin));

		Button insertSpaceButton = createRadioButton(group, "Space",
				JoinStyle.SPACE, gibberizer.getJoinStyle() == JoinStyle.SPACE, "Gibberizer will insert a space between strings.");
		Button insertNewLineButton = createRadioButton(group, "Line break",
				JoinStyle.LINE_BREAK, gibberizer.getJoinStyle() == JoinStyle.LINE_BREAK, "Gibberizer will insert a line break between strings.");
		Button insertTwoNewLinesButton = createRadioButton(group,
				"2 line breaks", JoinStyle.TWO_LINE_BREAKS, gibberizer.getJoinStyle() == JoinStyle.TWO_LINE_BREAKS, "Gibberizer will insert two line breaks between strings.");

		JoinStyleListener listener = new JoinStyleListener(
				gibberizer);
		insertSpaceButton.addSelectionListener(listener);
		insertNewLineButton.addSelectionListener(listener);
		insertTwoNewLinesButton.addSelectionListener(listener);

		insertNewLineButton.setSelection(true);
		gibberizer.setJoinStyle((JoinStyle) insertNewLineButton.getData());

		return group;
	}

	private Text initializeOutputText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		text.setEditable(false);
		text.setBackground(text.getDisplay().getSystemColor(
				SWT.COLOR_INFO_BACKGROUND));
		text.setForeground(text.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));

		return text;
	}

	private Group initializeOutputTextGroup(Composite parent) {
		Group group = createGridGroup(parent, "Gibberish:", "");
		group.setLayout(createGridLayout(1, 0, 0));

		outputText = initializeOutputText(group);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = 200;
		outputText.setLayoutData(gridData);

		return group;
	}

	private Group initializeParametersGroup(Composite parent) {
		Group group = createGridGroup(parent, "Parameters", "These parameters tell Gibberizer how to operate.");
		group
				.setLayout(createGridLayout(4, interGroupMargin,
						interGroupMargin));

		Group inputFormatParametersGroup = initializeInputFormatParametersGroup(group);
		Group buildParametersGroup = initializeBuildParametersGroup(group);
		Group filterParametersGroup = initializeFilterParametersGroup(group);
		Group outputFormatParametersGroup = initializeOutputFormatParametersGroup(group);

		inputFormatParametersGroup.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		buildParametersGroup.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		filterParametersGroup.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		outputFormatParametersGroup.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));

		return group;
	}

	private void initializeWindow(Composite parent) {
		GridLayout layout = createGridLayout(1, interGroupMargin, interGroupMargin);
		parent.setLayout(layout);

		Group parametersGroup = initializeParametersGroup(parent);
		Button gibberizeButton = initializeGibberizeButton(parent);
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		Group inputTextGroup = initializeInputTextGroup(sashForm);
		inputTextGroup.setToolTipText("Gibberizer will produce gibberish that is 'similar' to the text you enter here.");
		Group outputTextGroup = initializeOutputTextGroup(sashForm);
		outputTextGroup.setToolTipText("This area displays the strings of gibberish that Gibberizer generates");

		parametersGroup.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		gibberizeButton.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sashForm.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));

		gibberizeButton.addSelectionListener(new GibberizeListener(
				gibberizer, this));
	}

	public void setOutputText(String text) {
		outputText.setText(text);

	}
}
