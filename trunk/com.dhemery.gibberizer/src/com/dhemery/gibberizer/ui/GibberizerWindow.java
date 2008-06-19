package com.dhemery.gibberizer.ui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeListener;

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
import com.dhemery.gibberizer.listeners.SplitStyleListener;

public class GibberizerWindow {
	private static final int interWidgetMargin = 3;
	private static final int interGroupMargin = 10;

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

    public GibberizerWindow() {
        JFrame frame = new JFrame("Gibberizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeGibberizer();
        frame.setContentPane(initializeWindow());
        frame.pack();
        frame.setVisible(true);
    }

	private JTextArea inputTextArea;
	private JTextArea outputTextArea;

	private Gibberizer gibberizer;

	private JCheckBox createCheckBoxButton(String name, String toolTipText,
			ItemListener listener, boolean isSelected) {
		JCheckBox checkBox = new JCheckBox(name, isSelected);
		checkBox.setToolTipText(toolTipText);
		checkBox.addItemListener(listener);
		return checkBox;
	}

	private JTextArea createTextArea(boolean isEditable) {
		JTextArea textArea = new JTextArea(10, 40);
		textArea.setEditable(isEditable);
		return textArea;
	}

	private JRadioButton createJoinStyleButton(String name, String toolTipText,
			JoinStyleListener listener,
			JoinStyle style) {
		boolean isSelected = (gibberizer.getJoinStyle() == style);
		return createRadioButton(name, toolTipText, listener, isSelected, style);
	}

	private JLabel createLabel(String name, String toolTipText) {
		JLabel label = new JLabel();
		label.setName(name);
		label.setToolTipText(toolTipText);
		return label;
	}

	private JButton createPushButton(String name, String toolTipText,
			ActionListener listener) {
		JButton button = new JButton(name);
		button.addActionListener(listener);
		return button;
	}

	private JRadioButton createRadioButton(String name, String toolTipText,
			ItemListener listener,
			boolean isSelected, Object data) {
		JRadioButton button = new JRadioButton(name, isSelected);
		button.addItemListener(listener);
		return button;
	}

	private JSpinner createSpinner(String name, String toolTipText,
			ChangeListener listener,
			int maxValue, int initialValue) {

		JLabel label = createLabel(name, toolTipText);

		SpinnerNumberModel model = new SpinnerNumberModel(initialValue, 1, maxValue, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setToolTipText(toolTipText);
		spinner.addChangeListener(listener);
		return spinner;
	}

	private JRadioButton createSplitStyleRadioButton(String name, String toolTipText,
			SplitStyle style, SplitStyleListener listener) {
		boolean isSelected = (gibberizer.getSplitStyle() == style);
		return createRadioButton(name, toolTipText, listener, isSelected, style);
	}

	public String getInputText() {
		return inputTextArea.getText();
	}

	private JPanel initializeBuildParametersPanel() {
		JPanel panel = createPanel("Create", "Tell Gibberizer how to create gibs.");

		panel.add(createSpinner("Batch size:",
				"The number of gibs to create.",
				new BatchSizeListener(gibberizer),
				1000, gibberizer.getBatchSize()));

		panel.add(createSpinner("Similarity:",
				"The similarity of the gibs to the input strings.",
				new SimilarityListener(gibberizer),
				 20, gibberizer.getSimilarity()));

		panel.add(createSpinner("Persistence:",
				"How hard Gibberizer tries to create gibs\nthat pass the filters before giving up.",
				new PersistenceListener(gibberizer),
				10,gibberizer.getPersistence()));

		return panel;
	}

	private JPanel initializeFilterParametersPanel() {
		JPanel panel = createPanel("Filters",
				"Tell Gibberizer what gibs it is allowed to create.");

		panel.add(createCheckBoxButton("Allow input echo",
				"Check to allow Gibberizer to create gibs that match input strings.\nUncheck to force Gibberizer to createss gibs that do not appear in your text.",
				new AllowInputEchoListener(gibberizer),
				gibberizer.getAllowInputEcho()));
		panel.add(createCheckBoxButton("Allow duplicates",
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

	private JPanel initializeInputFormatParametersPanel() {
		JPanel panel = createPanel("Read input as:",
				"Tell Gibberizer how to divide your input text into strings.");

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

	private JPanel createPanel(String name, String toolTipText) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setName(name);
		panel.setToolTipText(toolTipText);
		return panel;
	}

	private JPanel initializeInputTextGroup() {
		JPanel panel = createPanel("Input:",
				"Gibberizer will create gibberish that is 'similar' to the text you enter here.");

		inputTextArea = createTextArea(true);
		panel.add(inputTextArea);

		return panel;
	}

	private JPanel initializeOutputFormatParametersPanel() {
		JPanel panel = createPanel("Separate gibs by:",
				"Tell Gibberizer how to format the gibs it creates.");

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

	private JPanel initializeOutputTextGroup() {
		JPanel panel = createPanel("Gibberish:",
				"This area displays the gibs (strings of gibberish) that Gibberizer creates.");

		outputTextArea = createTextArea(false);
		panel.add(outputTextArea);

		return panel;
	}

	private JPanel initializeParametersGroup() {
		JPanel panel = createPanel("Parameters", "Tell Gibberizer how to operate.");

		panel.add(initializeInputFormatParametersPanel());
		panel.add(initializeBuildParametersPanel());
		panel.add(initializeFilterParametersPanel());
		panel.add(initializeOutputFormatParametersPanel());
		return panel;
	}

	private JPanel initializeWindow() {
		JPanel panel = new JPanel();
		panel.add(initializeParametersGroup());
		panel.add(initializeGibberizeButton());
		panel.add(initializeInputTextGroup());
		panel.add(initializeOutputTextGroup());
		return panel;
	}

	public void setOutputText(String text) {
		outputTextArea.setText(text);

	}
}
