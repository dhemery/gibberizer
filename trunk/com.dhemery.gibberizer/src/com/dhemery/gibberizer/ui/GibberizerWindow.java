package com.dhemery.gibberizer.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.dhemery.gibberizer.core.Gibberizer;
import com.dhemery.gibberizer.core.JoinStyle;
import com.dhemery.gibberizer.core.SplitStyle;
import com.dhemery.gibberizer.widgets.AllowDuplicatesCheckBox;
import com.dhemery.gibberizer.widgets.AllowInputEchoCheckBox;
import com.dhemery.gibberizer.widgets.BatchSizeSpinner;
import com.dhemery.gibberizer.widgets.GibberizeButton;
import com.dhemery.gibberizer.widgets.JoinStyleRadioButton;
import com.dhemery.gibberizer.widgets.PersistenceSpinner;
import com.dhemery.gibberizer.widgets.SimilaritySpinner;
import com.dhemery.gibberizer.widgets.SplitStyleRadioButton;

public class GibberizerWindow extends JFrame implements Runnable {
	private static final int parameterPanePadding = 10;
	private static final long serialVersionUID = 1L;
	private static final int spinnerPadding = parameterPanePadding / 2;
	private static final int windowPadding = 4;
	private static final String version = "0.1.1";

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

        SwingUtilities.invokeLater(new GibberizerWindow());
    }

    private JTextArea inputTextArea;
	private JTextArea outputTextArea;
	private Gibberizer gibberizer;

	public GibberizerWindow() {
		super("Gibberizer " + version);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGibberizer();
        setContentPane(initializeWindow());
        pack();
    }

	private JLabel createLabel(String name, String toolTipText) {
		JLabel label = new JLabel(name);
		label.setName(name);
		label.setToolTipText(toolTipText);
		return label;
	}

	private JPanel createPanel(String name, String toolTipText) {
		JPanel panel = new JPanel();
		panel.setName(name);
		panel.setToolTipText(toolTipText);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setBorder(BorderFactory.createTitledBorder(name));
		return panel;
	}

	private JScrollPane createScrollPane(Component component, String name, String toolTipText) {
		JScrollPane scrollPane = new JScrollPane(component);
		scrollPane.setName(name);
		scrollPane.setToolTipText(toolTipText);
		scrollPane.setBorder(BorderFactory.createTitledBorder(name));
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrollPane;
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

		positionSpinner(panel,
				new BatchSizeSpinner("BatchSize:",
				"The number of gibs to create.",
				gibberizer));

		positionSpinner(panel,
				new SimilaritySpinner("Similarity:",
				"The similarity of the gibs to the input strings.",
				gibberizer));

		positionSpinner(panel,
				new PersistenceSpinner("Persistence:",
				"How hard Gibberizer tries to create gibs\nthat pass the filters before giving up.",
				gibberizer));

		return panel;
	}

	private JPanel initializeFilterParametersPane() {
		JPanel panel = createPanel("Filters",
				"Tell Gibberizer what gibs it is allowed to create.");
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(new AllowInputEchoCheckBox("Allow input echo",
				"Check to allow Gibberizer to create gibs that match input strings.\nUncheck to force Gibberizer to createss gibs that do not appear in your text.",
				gibberizer));

		panel.add(new AllowDuplicatesCheckBox("Allow duplicates",
				"Check to allow Gibberizer to create multiple instances of a gib.\nUncheck to force Gibberizer to create unique gibs.",
				gibberizer));

		return panel;
	}

	private void initializeGibberizer() {
		gibberizer = new Gibberizer();
	}

	private JPanel initializeInputFormatParametersPane() {
		ButtonGroup group = new ButtonGroup();

		group.add(new SplitStyleRadioButton("Words",
				"Gibberizer reads your text as words.\nThat is, as strings of characters separated by white space.",
				gibberizer,
				SplitStyle.WORDS));
		group.add(new SplitStyleRadioButton("Lines",
				"Gibberizer reads your text as lines.",
				gibberizer,
				SplitStyle.LINES));
		group.add(new SplitStyleRadioButton("One String",
				"Gibberizer reads your text as a single string.",
				gibberizer,
				SplitStyle.ONE_STRING));

		return createRadioButtonPanel("Read input as:",
				"Tell Gibberizer how to divide your input text into strings.",
				group);
	}

	private JScrollPane initializeInputTextPane() {
		inputTextArea = createTextArea(true);
		return createScrollPane(inputTextArea,
				"Input:",
				"Gibberizer will create gibberish that is 'similar' to the text you enter here.");
	}

	private JPanel initializeOutputFormatParametersPane() {
		ButtonGroup group = new ButtonGroup();
		group.add(new JoinStyleRadioButton("Space",
				"Gibberizer inserts a space between gibs.",
				gibberizer,
				JoinStyle.SPACE));
		group.add(new JoinStyleRadioButton("New line",
				"Gibberizer starts a new line before each gib.",
				gibberizer,
				JoinStyle.NEW_LINE));
		group.add(new JoinStyleRadioButton("Blank Line",
				"Gibberizer inserts a blank line between gibs.",
				gibberizer,
				JoinStyle.BLANK_LINE));

		return createRadioButtonPanel("Separate gibs by:",
				"Tell Gibberizer how to format the gibs it creates.",
				group);
	}

	private JPanel createRadioButtonPanel(String name, String toolTipText, ButtonGroup group) {
		JPanel panel = createPanel(name, toolTipText);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		Enumeration<AbstractButton> buttons = group.getElements();
		while (buttons.hasMoreElements()) {
			panel.add((AbstractButton) buttons.nextElement());
		}
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
		
		panel.add(new GibberizeButton("Gibberize", "Create gibs (strings of gibberish).", gibberizer, this), constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.anchor = GridBagConstraints.PAGE_END;

		panel.add(initializeTextPanes(), constraints);

		return panel;
	}

	public void run() {
        setVisible(true);
	}

	private void positionSpinner(JPanel panel, JSpinner spinner) {
		GridBagConstraints constraints = new GridBagConstraints();

		JLabel label = createLabel(spinner.getName(), spinner.getToolTipText());
		constraints.gridx = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.LINE_END;
		panel.add(label, constraints);

		constraints.gridx = 1;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.insets = new Insets(0, spinnerPadding, spinnerPadding, 0);
		panel.add(spinner, constraints);
	}

	public void setOutputText(String text) {
		outputTextArea.setText(text);

	}
}
