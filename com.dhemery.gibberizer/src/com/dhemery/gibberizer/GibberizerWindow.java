package com.dhemery.gibberizer;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionListener;
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

	private Button createButton(Composite parent, int type,
			String name, String toolTipText,
			SelectionListener listener,
			boolean isSelected) {
		Button button = new Button(parent, type);
		button.setText(name);
		button.setToolTipText(toolTipText);
		button.setSelection(isSelected);
		button.addSelectionListener(listener);
		return button;
	}

	private Button createCheckBoxButton(Composite parent,
			String name, String toolTipText,
			SelectionListener listener,
			boolean isSelected) {
		return createButton(parent, SWT.CHECK, name, toolTipText, listener, isSelected);
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

	private Text createInputText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		text.setEditable(true);
		text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		text.setForeground(text.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));

		return text;
	}

	private Button createJoinStyleButton(Group group,
			String name, String toolTipText,
			JoinStyleListener listener,
			JoinStyle style) {
		boolean isSelected = (gibberizer.getJoinStyle() == style);
		return createRadioButton(group, name, toolTipText, listener, isSelected, style);
	}

	private Label createLabel(Composite parent, String name, String toolTipText) {
		Label label = new Label(parent, SWT.SHADOW_IN);
		label.setText(name);
		label.setToolTipText(toolTipText);
		return label;
	}

	private Button createPushButton(Composite parent,
			String name, String toolTipText,
			SelectionListener listener) {
		return createButton(parent, SWT.PUSH, name, toolTipText, listener, false);
	}

	private Button createRadioButton(Composite parent,
			String name, String toolTipText,
			SelectionListener listener,
			boolean isSelected, Object data) {
		Button button = createButton(parent, SWT.RADIO, name, toolTipText, listener, isSelected);
		button.setData(data);
		return button;
	}

	private Spinner createSpinner(Group group,
			String name, String toolTipText,
			SelectionListener listener,
			int maxValue, int initialValue) {

		Label label = createLabel(group, name, toolTipText);
		label.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		Spinner spinner = new Spinner(group, SWT.BORDER);
		spinner.setValues(initialValue, 1, maxValue, 0, 1, 1);
		spinner.setToolTipText(toolTipText);
		spinner.addSelectionListener(listener);
		return spinner;
	}

	private Button createSplitStyleRadioButton(Group group, String name,
			SplitStyle style, String toolTipText,
			SplitStyleListener listener) {
		boolean isSelected = (gibberizer.getSplitStyle() == style);
		return createRadioButton(group, name, toolTipText, listener, isSelected, style);
	}

	public String getInputText() {
		return inputText.getText();
	}

	private Group initializeBuildParametersGroup(Composite parent) {
		Group group = createGridGroup(parent,
				"Create",
				"Tell Gibberizer how to create gibs.");
		group.setLayout(createGridLayout(2, interWidgetMargin,
				interWidgetMargin));

		createSpinner(group,
				"Batch size:", "The number of gibs to create.",
				new BatchSizeListener(gibberizer),
				1000, gibberizer.getBatchSize());

		createSpinner(group,
				"Similarity:", "The similarity of the gibs to the input strings.",
				new SimilarityListener(gibberizer),
				 20, gibberizer.getSimilarity());

		createSpinner(group,
				"Persistence:",
				"How hard Gibberizer tries to create gibs\nthat pass the filters before giving up.",
				new PersistenceListener(gibberizer),
				10,gibberizer.getPersistence());

		return group;
	}

	private Group initializeFilterParametersGroup(Composite parent) {
		Group group = createGridGroup(parent,
				"Filters",
				"Tell Gibberizer what gibs it is allowed to create.");
		group.setLayout(createGridLayout(1, interWidgetMargin,
				interWidgetMargin));

		createCheckBoxButton(group,
				"Allow input echo", "Check to allow Gibberizer to create gibs that match input strings.\nUncheck to force Gibberizer to createss gibs that do not appear in your text.",
				new AllowInputEchoListener(gibberizer),
				gibberizer.getAllowInputEcho());
		createCheckBoxButton(group,
				"Allow duplicates", "Check to allow Gibberizer to create multiple instances of a gib.\nUncheck to force Gibberizer to create unique gibs.",
				new AllowDuplicatesListener(gibberizer),
				gibberizer.getAllowDuplicates());

		return group;
	}

	private Button initializeGibberizeButton(Composite parent) {
		Button button = createPushButton(parent,
				"Gibberize", "Create gibs (strings of gibberish).",
				new GibberizeListener(gibberizer, this));

		return button;
	}

	private void initializeGibberizer() {
		gibberizer = new Gibberizer();
	}

	private Group initializeInputFormatParametersGroup(Composite parent) {
		Group group = createGridGroup(parent,
				"Read input as:",
				"Tell Gibberizer how to divide your input text into strings.");
		group.setLayout(createGridLayout(1, interWidgetMargin,
				interWidgetMargin));

		SplitStyleListener listener = new SplitStyleListener(gibberizer);
		createSplitStyleRadioButton(group,
				"Words", SplitStyle.WORDS,
				"Gibberizer reads your text as words.\nThat is, as strings of characters separated by white space.",
				listener);
		createSplitStyleRadioButton(group,
				"Lines", SplitStyle.LINES,
				"Gibberizer reads your text as lines.",
				listener);
		createSplitStyleRadioButton(group,
				"One String", SplitStyle.ONE_STRING,
				"Gibberizer reads your text as a single string.",
				listener);

		return group;
	}

	private Group initializeInputTextGroup(Composite parent) {
		Group group = createGridGroup(parent, "Input:", "");
		group.setLayout(createGridLayout(1, 0, 0));

		inputText = createInputText(group);

		inputText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		return group;
	}

	private Group initializeOutputFormatParametersGroup(Composite parent) {
		Group group = createGridGroup(parent,
				"Separate gibs by:", "Tell Gibberizer how to format the gibs it creates.");
		group.setLayout(createGridLayout(1, interWidgetMargin,
				interWidgetMargin));

		JoinStyleListener joinStyleListener = new JoinStyleListener(gibberizer);

		createJoinStyleButton(group,
				"Space", "Gibberizer inserts a space between gibs.",
				joinStyleListener,
				JoinStyle.SPACE);
		createJoinStyleButton(group,
				"New line", "Gibberizer starts a new line before each gib.",
				joinStyleListener,
				JoinStyle.NEW_LINE);
		createJoinStyleButton(group,
				"Blank Line", "Gibberizer inserts a blank line between gibs.",
				joinStyleListener,
				JoinStyle.BLANK_LINE);

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
		Group group = createGridGroup(parent,
				"Parameters", "Tell Gibberizer how to operate.");
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
		inputTextGroup.setToolTipText("Gibberizer will create gibberish that is 'similar' to the text you enter here.");
		Group outputTextGroup = initializeOutputTextGroup(sashForm);
		outputTextGroup.setToolTipText("This area displays the gibs (strings of gibberish) that Gibberizer creates.");

		parametersGroup.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		gibberizeButton.setLayoutData(new GridData(SWT.BEGINNING,
				SWT.BEGINNING, false, false));
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sashForm.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
	}

	public void setOutputText(String text) {
		outputText.setText(text);

	}
}
