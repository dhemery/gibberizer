package com.dhemery.gibberizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

public class GibberizerWindow extends Composite {
	private static final int interWidgetMargin = 2;
	private static final int interGroupMargin = 10;
	private Text inputText;
	private Text outputText;
	private Gibberizer gibberizer;

	public GibberizerWindow(Composite parent) {
		super(parent, SWT.NONE);
		initializeGibberizer();
		initializeWindow(this);
	}

	private Button createButton(Composite parent, String name, int type) {
		Button button = new Button(parent, type);
		button.setText(name);
		return button;
	}

	private Button createCheckBoxButton(Composite parent, String name) {
		return createButton(parent, name, SWT.CHECK);
	}

	private Group createFormGroup(Composite parent, String name) {
		return createGroup(parent, name, createFormLayout());
	}

	private FormLayout createFormLayout() {
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 10;
		formLayout.marginWidth = 10;
		return formLayout;
	}

	private Group createGridGroup(Composite parent, String name) {
		return createGroup(parent, name, createGridLayout());
	}

	private GridLayout createGridLayout() {
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = interGroupMargin;
		layout.marginWidth = interGroupMargin;
		return layout;
	}

	private Group createGroup(Composite parent, String name, Layout layout) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(name);
		group.setLayout(layout);
		return group;
	}

	private Label createLabel(Composite parent, String name) {
		Label label = new Label(parent, SWT.SHADOW_IN);
		label.setText(name);
		return label;
	}

	private Button createPushButton(Composite parent, String name) {
		return createButton(parent, name, SWT.PUSH);
	}

	private Button createRadioButton(Composite parent, String name) {
		return createButton(parent, name, SWT.RADIO);
	}

	private Text createTextBox(Group group) {
		Text buildCountText = new Text(group, SWT.BORDER | SWT.SINGLE);
		buildCountText.setEditable(true);
		buildCountText.setBackground(buildCountText.getDisplay()
				.getSystemColor(SWT.COLOR_WHITE));
		buildCountText.setForeground(buildCountText.getDisplay()
				.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		buildCountText.pack();
		return buildCountText;
	}

	private Group initializeBuildParametersGroup(Composite parent) {
		Group group = createGridGroup(parent, "Build");

		Label buildCountLabel = createLabel(group, "Number of Strings:");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.verticalAlignment = SWT.CENTER;
		buildCountLabel.setLayoutData(gridData);

		Text buildCountText = createTextBox(group);
		gridData = new GridData();
		buildCountText.setLayoutData(gridData);

		Label familiarityLabel = createLabel(group, "Familiarity:");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.verticalAlignment = SWT.CENTER;
		familiarityLabel.setLayoutData(gridData);

		Text familiarityText = createTextBox(group);
		gridData = new GridData();
		familiarityText.setLayoutData(gridData);

		Label persistenceLabel = createLabel(group, "Persistence:");
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		gridData.verticalAlignment = SWT.CENTER;
		persistenceLabel.setLayoutData(gridData);

		Text persistenceText = createTextBox(group);
		gridData = new GridData();
		persistenceText.setLayoutData(gridData);

		group.pack();
		return group;
	}

	private Group initializeFilterParametersGroup(Composite parent) {
		Group group = createFormGroup(parent, "Filters");

		Button allowInputEchoButton = createCheckBoxButton(group, "Allow echo");
		Button allowDuplicatesButton = createCheckBoxButton(group,
				"Allow duplicates");

		positionAtTopLeft(allowInputEchoButton);
		positionWidgetBelow(allowDuplicatesButton, allowInputEchoButton);

		group.pack();
		return group;
	}

	private Button initializeGibberizeButton(Composite parent) {
		Button button = createPushButton(parent, "Gibberize");
		button.pack();
		initializeGibberizeButtonListeners(button);
		return button;
	}

	private void initializeGibberizeButtonListeners(Button button) {
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				outputText.setText(gibberizer.gibberize(inputText.getText()));
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				outputText.setText(gibberizer.gibberize(inputText.getText()));
			}
		});
	}

	private void initializeGibberizer() {
		gibberizer = new Gibberizer(this);
	}

	private Group initializeInputFormatParametersGroup(Composite parent) {
		Group group = createFormGroup(parent, "Split input at:");

		Button whiteSpaceButton = createRadioButton(group, "White space");
		Button newLineButton = createRadioButton(group, "New line");
		Button dontSplitButton = createRadioButton(group, "Don't split");

		whiteSpaceButton.setSelection(true);

		positionAtTopLeft(whiteSpaceButton);
		positionWidgetBelow(newLineButton, whiteSpaceButton);
		positionWidgetBelow(dontSplitButton, newLineButton);

		group.pack();
		return group;
	}

	private void initializeInputText(Composite parent) {
		inputText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		inputText.setEditable(true);
		inputText.setBackground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));
		inputText.setForeground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));
		inputText.setBounds(0, 0, 300, 200);
		inputText.pack();
	}

	private Group initializeInputTextGroup(Composite parent) {
		Group group = createGridGroup(parent, "Input");
		initializeInputText(group);
		group.pack();
		return group;
	}

	private Group initializeOutputFormatParametersGroup(Composite parent) {
		Group group = createFormGroup(parent, "Separate output with:");

		Button whiteSpaceButton = createRadioButton(group, "White space");
		Button newLineButton = createRadioButton(group, "New line");
		Button twoNewLinesButton = createRadioButton(group, "2 new lines");

		newLineButton.setSelection(true);

		positionAtTopLeft(whiteSpaceButton);
		positionWidgetBelow(newLineButton, whiteSpaceButton);
		positionWidgetBelow(twoNewLinesButton, newLineButton);

		group.pack();
		return group;
	}

	private Text initializeOutputText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		text.setEditable(false);
		text.setBackground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_INFO_BACKGROUND));
		text.setForeground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));
		text.pack();
		return text;
	}

	private Group initializeOutputTextGroup(Composite parent) {
		Group group = createGridGroup(parent, "Output");
		outputText = initializeOutputText(group);
		GridData gridData = new GridData();
		outputText.setLayoutData(gridData);
		group.pack();
		return group;
	}

	private Group initializeParametersGroup(Composite parent) {
		Group parametersPanel = createFormGroup(parent, "Parameters");

		Group inputFormatParametersGroup = initializeInputFormatParametersGroup(parametersPanel);
		Group buildParametersGroup = initializeBuildParametersGroup(parametersPanel);
		Group filterParametersGroup = initializeFilterParametersGroup(parametersPanel);
		Group outputFormatParametersGroup = initializeOutputFormatParametersGroup(parametersPanel);

		positionAtTopLeft(inputFormatParametersGroup);
		positionGroupToRight(buildParametersGroup, inputFormatParametersGroup);
		positionGroupToRight(filterParametersGroup, buildParametersGroup);
		positionGroupToRight(outputFormatParametersGroup, filterParametersGroup);

		parametersPanel.pack();

		return parametersPanel;
	}

	private void initializeWindow(Composite parent) {
		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = interGroupMargin;
		formLayout.marginWidth = interGroupMargin;
		this.setLayout(formLayout);

		Group parametersGroup = initializeParametersGroup(parent);
		Button gibberizeButton = initializeGibberizeButton(parent);

		Group inputTextGroup = initializeInputTextGroup(parent);
		Group outputTextGroup = initializeOutputTextGroup(parent);

		positionAtTopLeft(parametersGroup);
		positionGroupBelow(gibberizeButton, parametersGroup);
		positionGroupBelow(inputTextGroup, gibberizeButton);
		positionGroupBelow(outputTextGroup, inputTextGroup);
		stretchLeftToRight(inputTextGroup);
		stretchLeftToRight(outputTextGroup);
		
		pack();
	}

	private void stretchLeftToRight(Control control) {
		FormData formData = (FormData) control.getLayoutData();
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
	}

	private void positionAtTopLeft(Control control) {
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.left = new FormAttachment(0, 0);
		control.setLayoutData(formData);
	}

	private void positionGroupBelow(Control composite, Control reference) {
		FormData formData = new FormData();
		formData.top = new FormAttachment(reference, interGroupMargin);
		composite.setLayoutData(formData);
	}

	private void positionGroupToRight(Control composite, Control reference) {
		FormData formData = new FormData();
		formData.left = new FormAttachment(reference, interGroupMargin);
		composite.setLayoutData(formData);
	}

	private void positionWidgetBelow(Control control, Control reference) {
		FormData formData = new FormData();
		formData.top = new FormAttachment(reference, interWidgetMargin);
		control.setLayoutData(formData);
	}
}
