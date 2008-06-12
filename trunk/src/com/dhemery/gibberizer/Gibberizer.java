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
		String input = inputText.getText();
		NameGenerator gibberizer = new NameGenerator(input, 3);
		List<String> nameList = gibberizer.generateNames(100);
		String output = "";
		for (String name : nameList)
			output += name + "\n";
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
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
