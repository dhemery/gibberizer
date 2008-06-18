package com.dhemery.gibberizer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import com.dhemery.gibberizer.StringJoiner.JoinStyle;

public class JoinStyleListener implements SelectionListener {

	private final Gibberizer gibberizer;

	public JoinStyleListener(Gibberizer gibberizer) {
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
		JoinStyle data = (JoinStyle) button.getData();
		gibberizer.setJoinStyle(data);
	}
}
