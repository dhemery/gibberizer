package com.dhemery.gibberizer.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Spinner;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class SpinnerListener implements SelectionListener {

	private final Gibberizer gibberizer;

	protected abstract void execute(int selection);

	public SpinnerListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Spinner spinner = (Spinner)e.widget;
		execute(spinner.getSelection());
	}

	public Gibberizer getGibberizer() {
		return gibberizer;
	}
}