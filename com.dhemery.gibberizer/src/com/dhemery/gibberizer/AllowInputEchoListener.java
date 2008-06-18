package com.dhemery.gibberizer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

public class AllowInputEchoListener implements SelectionListener {

	private final Gibberizer gibberizer;

	public AllowInputEchoListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Button button = (Button) e.widget;
		gibberizer.setAllowInputEcho(button.getSelection());
	}

}
