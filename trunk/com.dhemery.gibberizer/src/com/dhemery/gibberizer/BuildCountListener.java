package com.dhemery.gibberizer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Spinner;

public class BuildCountListener implements SelectionListener {
	private final Gibberizer gibberizer;

	public BuildCountListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Spinner spinner = (Spinner)e.widget;
		gibberizer.setNumberOfStringsToBuild(spinner.getSelection());
	}
}
