package com.dhemery.gibberizer;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GibberizerApplication {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(800, 600);
		shell.setText("Gibberizer");
		new GibberizerWindow(shell);
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch()) display.sleep();
		display.dispose();
	}
}
