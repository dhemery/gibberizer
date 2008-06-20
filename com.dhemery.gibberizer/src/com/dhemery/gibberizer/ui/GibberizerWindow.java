package com.dhemery.gibberizer.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.core.JoinStyle;
import com.dhemery.gibberizer.core.SplitStyle;
import com.dhemery.gibberizer.listeners.AllowDuplicatesListener;
import com.dhemery.gibberizer.listeners.AllowInputEchoListener;
import com.dhemery.gibberizer.listeners.BatchSizeListener;
import com.dhemery.gibberizer.listeners.GibberizeListener;
import com.dhemery.gibberizer.listeners.JoinStyleListener;
import com.dhemery.gibberizer.listeners.PersistenceListener;
import com.dhemery.gibberizer.listeners.SimilarityListener;
import com.dhemery.gibberizer.listeners.SpinnerListener;
import com.dhemery.gibberizer.listeners.SplitStyleListener;

public class GibberizerWindow {
	private static final int parameterPanePadding = 10;
	private static final int spinnerPadding = parameterPanePadding / 2;
	private static final int windowPadding = 4;
	private static final String version = "0.1.0";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
           // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GibberizerWindow();
            }

        });
    }

    private JTextArea inputTextArea;

	private JTextArea outputTextArea;
	private Gibberizer gibberizer;

	public GibberizerWindow() {
		initializeGibberizer();

		JFrame frame = new JFrame("Gibberizer " + version);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(initializeWindow());
        frame.pack();
        frame.setVisible(true);
    }

	private JCheckBox createCheckBox(String name, String toolTipText,
			ItemListener listener, boolean isSelected) {
		JCheckBox checkBox = new JCheckBox(name, isSelected);
		checkBox.setToolTipText(toolTipText);
		checkBox.addItemListener(listener);
		return checkBox;
	}

	private JRadioButton createJoinStyleButton(String name, String toolTipText,
			JoinStyleListener listener,
			JoinStyle style) {
		boolean isSelected = (gibberizer.getJoinStyle() == style);
		return createRadioButton(name, toolTipText, listener, isSelected, style);
	}

	private JLabel createLabel(String name, String toolTipText) {
		JLabel label = new JLabel(name);
		label.setToolTipText(toolTipText);
		return label;
	}

	private JPanel createPanel(String name, String toolTipText) {
		JPanel panel = new JPanel();
		Border border = BorderFactory.createTitledBorder(name);
		panel.setBorder(border);
		panel.setName(name);
		panel.setToolTipText(toolTipText);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		return panel;
	}

	private JButton createPushButton(String name, String toolTipText,
			ActionListener listener) {
		JButton button = new JButton(name);
		button.setToolTipText(toolTipText);
		button.addActionListener(listener);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.setAlignmentY(Component.TOP_ALIGNMENT);
		return button;
	}

	private JRadioButton createRadioButton(String name, String toolTipText,
			ItemListener listener,
			boolean isSelected, Object data) {
		JRadioButton button = new JRadioButton(name, isSelected);
		button.setToolTipText(toolTipText);
		button.addItemListener(listener);
		button.putClientProperty("style", data);
		return button;
	}

	private JScrollPane createScrollPane(Component component, String name, String toolTipText) {
		JScrollPane scrollPane = new JScrollPane(component);
		scrollPane.setBorder(BorderFactory.createTitledBorder(name));
		scrollPane.setToolTipText(toolTipText);
		scrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
	}

	private JSpinner createSpinner(String name, String toolTipText, SpinnerListener listener,
			int maxValue, int initialValue) {

		SpinnerNumberModel model = new SpinnerNumberModel(initialValue, 1, maxValue, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setName(name);
		spinner.setToolTipText(toolTipText);
		spinner.addChangeListener(listener);
		return spinner;
	}

	private void createSpinnerPanel(JPanel panel, String name, String toolTipText,
			SpinnerListener listener,
			int maxValue, int initialValue) {
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel label = createLabel(name, toolTipText);
		constraints.gridx = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.LINE_END;
		panel.add(label, constraints);

		JSpinner spinner = createSpinner(name, toolTipText,
				listener, maxValue, initialValue);
		constraints.gridx = 1;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.insets = new Insets(0, spinnerPadding, spinnerPadding, 0);
		panel.add(spinner, constraints);
	}

	private JRadioButton createSplitStyleRadioButton(String name, String toolTipText,
			SplitStyle style, SplitStyleListener listener) {
		boolean isSelected = (gibberizer.getSplitStyle() == style);
		return createRadioButton(name, toolTipText, listener, isSelected, style);
	}

	private JTextArea createTextArea(boolean isEditable) {
		JTextArea textArea = new JTextArea();
		textArea.setEditable(isEditable);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		return textArea;
	}
	
	public String getInputText() {
		return inputTextArea.getText();
	}

	private JPanel initializeBuildParametersPane() {
		JPanel panel = createPanel("Create", "Tell Gibberizer how to create gibs.");
		panel.setLayout(new GridBagLayout());

		createSpinnerPanel(panel,
				"BatchSize:",
				"The number of gibs to create.",
				new BatchSizeListener(gibberizer),
				1000, gibberizer.getBatchSize());

		createSpinnerPanel(panel,
				"Similarity:",
				"The similarity of the gibs to the input strings.",
				new SimilarityListener(gibberizer),
				 20, gibberizer.getSimilarity());

		createSpinnerPanel(panel,
				"Persistence:",
				"How hard Gibberizer tries to create gibs\nthat pass the filters before giving up.",
				new PersistenceListener(gibberizer),
				10,gibberizer.getPersistence());

		return panel;
	}

	private JPanel initializeFilterParametersPane() {
		JPanel panel = createPanel("Filters",
				"Tell Gibberizer what gibs it is allowed to create.");
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(createCheckBox("Allow input echo",
				"Check to allow Gibberizer to create gibs that match input strings.\nUncheck to force Gibberizer to createss gibs that do not appear in your text.",
				new AllowInputEchoListener(gibberizer),
				gibberizer.getAllowInputEcho()));
		panel.add(createCheckBox("Allow duplicates",
				"Check to allow Gibberizer to create multiple instances of a gib.\nUncheck to force Gibberizer to create unique gibs.",
				new AllowDuplicatesListener(gibberizer),
				gibberizer.getAllowDuplicates()));

		return panel;
	}

	private JButton initializeGibberizeButton() {
		return createPushButton("Gibberize", "Create gibs (strings of gibberish).",
				new GibberizeListener(gibberizer, this));
	}

	private void initializeGibberizer() {
		gibberizer = new Gibberizer();
	}

	private JPanel initializeInputFormatParametersPane() {
		JPanel panel = createPanel("Read input as:",
				"Tell Gibberizer how to divide your input text into strings.");
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		SplitStyleListener listener = new SplitStyleListener(gibberizer);

		JRadioButton wordsSplitStyleButton = createSplitStyleRadioButton("Words",
				"Gibberizer reads your text as words.\nThat is, as strings of characters separated by white space.",
				SplitStyle.WORDS, listener);
		JRadioButton linesSplitStyleButton = createSplitStyleRadioButton("Lines",
				"Gibberizer reads your text as lines.",
				SplitStyle.LINES,
				listener);
		JRadioButton oneStringSplitStyleButton = createSplitStyleRadioButton("One String",
				"Gibberizer reads your text as a single string.",
				SplitStyle.ONE_STRING,
				listener);

		ButtonGroup group = new ButtonGroup();
		group.add(wordsSplitStyleButton);
		group.add(linesSplitStyleButton);
		group.add(oneStringSplitStyleButton);

		panel.add(wordsSplitStyleButton);
		panel.add(linesSplitStyleButton);
		panel.add(oneStringSplitStyleButton);

		return panel;
	}

	private JScrollPane initializeInputTextPane() {
		inputTextArea = createTextArea(true);
		return createScrollPane(inputTextArea,
				"Input:",
				"Gibberizer will create gibberish that is 'similar' to the text you enter here.");
	}

	private JPanel initializeOutputFormatParametersPane() {
		JPanel panel = createPanel("Separate gibs by:",
				"Tell Gibberizer how to format the gibs it creates.");
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JoinStyleListener joinStyleListener = new JoinStyleListener(gibberizer);

		JRadioButton spaceJoinStyleButton = createJoinStyleButton("Space",
				"Gibberizer inserts a space between gibs.",
				joinStyleListener,
				JoinStyle.SPACE);
		JRadioButton newLineJoinStyleButton = createJoinStyleButton("New line",
				"Gibberizer starts a new line before each gib.",
				joinStyleListener,
				JoinStyle.NEW_LINE);
		JRadioButton blankLineJoinStyleButton = createJoinStyleButton("Blank Line",
				"Gibberizer inserts a blank line between gibs.",
				joinStyleListener,
				JoinStyle.BLANK_LINE);

		ButtonGroup group = new ButtonGroup();
		group.add(spaceJoinStyleButton);
		group.add(newLineJoinStyleButton);
		group.add(blankLineJoinStyleButton);

		panel.add(spaceJoinStyleButton);
		panel.add(newLineJoinStyleButton);
		panel.add(blankLineJoinStyleButton);

		return panel;
	}

	private JScrollPane initializeOutputTextPane() {
		outputTextArea = createTextArea(false);
//		outputTextArea.setBackground(null);

		return createScrollPane(outputTextArea,
				"Gibberish:",
				"This area displays the gibs (strings of gibberish) that Gibberizer creates.");
	}

	private JPanel initializeParameterPane() {
		JPanel panel = createPanel("Parameters", "Tell Gibberizer how to operate.");
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = GridBagConstraints.RELATIVE;
		constraints.gridy = 0;
		constraints.insets = new Insets(windowPadding, windowPadding, windowPadding, windowPadding);
		constraints.ipadx = parameterPanePadding;
		constraints.ipady = parameterPanePadding;
		
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.NONE;

		panel.add(initializeInputFormatParametersPane(), constraints);
		panel.add(initializeBuildParametersPane(), constraints);
		panel.add(initializeFilterParametersPane(), constraints);
		panel.add(initializeOutputFormatParametersPane(), constraints);
		return panel;
	}

	private JSplitPane initializeTextPanes() {
		JScrollPane inputTextPane = initializeInputTextPane();
		JScrollPane outputTextPane = initializeOutputTextPane();

		JSplitPane textAreasPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				inputTextPane, outputTextPane);
		textAreasPane.setOneTouchExpandable(true);
		textAreasPane.setDividerSize(7);
		textAreasPane.setResizeWeight(0.3f);
		textAreasPane.setPreferredSize(new Dimension(100, 400));

		return textAreasPane;
	}

	private JPanel initializeWindow() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(windowPadding, windowPadding, windowPadding, windowPadding);

		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.NONE;
		panel.add(initializeParameterPane(), constraints);
		
		panel.add(initializeGibberizeButton(), constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.anchor = GridBagConstraints.PAGE_END;

		panel.add(initializeTextPanes(), constraints);

		return panel;
	}

	public void setOutputText(String text) {
		outputTextArea.setText(text);

	}
}
