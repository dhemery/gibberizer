package com.dhemery.gibberizer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import com.dhemery.gibberizer.StringSplitter.SplitStyle;

public class InputFormatRadioButtonListener implements SelectionListener {

	private final Gibberizer gibberizer;

	public InputFormatRadioButtonListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Button button = (Button) e.widget;
		if(!button.getSelection()) return;
		SplitStyle data = (SplitStyle) button.getData();
		gibberizer.setSplitStyle(data);
	}

}
