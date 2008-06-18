package com.dhemery.gibberizer.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class CheckBoxListener implements SelectionListener {

	private final Gibberizer gibberizer;

	protected abstract void execute(boolean isSelected);
	
	public Gibberizer getGibberizer() {
		return gibberizer;
	}
	
	public CheckBoxListener(Gibberizer gibberizer) {
		this.gibberizer = gibberizer;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Button button = (Button) e.widget;
		execute(button.getSelection());
	}
}