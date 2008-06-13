package com.dhemery.gibberizer;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Gibberizer {
	private static Text inputText;
	private static Text outputText;

	private static void gibberize() {
		boolean allowInputEcho = false;
		boolean allowDuplicates = false;
		int delimiter = StringSplitter.END_OF_LINE;
		int minStringLength = 1;
		int maxStringLength = 1000;
		int ngramLength = 7;
		int persistence = 5;
		int stringCount = 3;

		StringSplitter splitter = new StringSplitter(delimiter);
		StringParser parser = new StringParser(ngramLength);
		StringFilter filter = new StringFilter(minStringLength, maxStringLength);
		StringBasket basket = new StringBasket(stringCount, filter, persistence);
		StringBuilder builder = new StringBuilder();
		StringCombiner combiner = new StringCombiner();

		String input = inputText.getText();
		List<String> inputStrings = splitter.split(input);
		if (!allowInputEcho) filter.addProhibitedStringsList(inputStrings);
		if (!allowDuplicates)
			filter.addProhibitedStringsList(basket.getDeliveredStrings());
		List<Ngram> ngrams = parser.parseNgrams(inputStrings);
		builder.buildSequences(ngrams, basket);
		String output = combiner.combine(basket.getDeliveredStrings());
		outputText.setText(output);
	}

	private static void initializeWindow(Shell top) {
		GridLayout layout = new GridLayout(2, false);
		top.setLayout(layout);

		Label inputLabel = new Label(top, SWT.CENTER);
		inputLabel.setLayoutData(new GridData());
		inputLabel.setText("Input:");

		Button gibberizeButton = new Button(top, SWT.PUSH);
		gibberizeButton.setLayoutData(new GridData());
		gibberizeButton.setText("Gibberize");

		inputText = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		inputText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));
		inputText.setEditable(true);
		inputText.setBackground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));
		inputText.setForeground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));

		Label outputLabel = new Label(top, SWT.CENTER);
		outputLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true,
				false));
		outputLabel.setText("Gibberized:");

		outputText = new Text(top, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.WRAP);
		outputText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1));
		outputText.setEditable(false);
		outputText.setBackground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_INFO_BACKGROUND));
		outputText.setForeground(inputText.getDisplay().getSystemColor(
				SWT.COLOR_INFO_FOREGROUND));

		gibberizeButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				gibberize();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				gibberize();
			}
		});
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(500, 400);
		shell.setText("Gibberizer");
		initializeWindow(shell);
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch()) display.sleep();
		display.dispose();
	}
}
