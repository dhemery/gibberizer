package com.dhemery.gibberizer.widgets;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dhemery.gibberizer.core.Gibberizer;

public abstract class GibberizerSpinner extends JSpinner implements ChangeListener {
	private static final long serialVersionUID = 1L;

	private final Gibberizer gibberizer;

	public GibberizerSpinner(String name, String toolTipText, Gibberizer gibberizer,
			int maxValue, int initialValue) {
		super(new SpinnerNumberModel(initialValue, 1, maxValue, 1));
		setName(name);
		setToolTipText(toolTipText);
		this.gibberizer = gibberizer;
		addChangeListener(this);
	}

	public Gibberizer getGibberizer() {
		return gibberizer;
	}

	protected abstract void setSelection(int selection);

	public void stateChanged(ChangeEvent e) {
		JSpinner spinner = (JSpinner)e.getSource();
		SpinnerNumberModel model = (SpinnerNumberModel)spinner.getModel();
		setSelection(model.getNumber().intValue());
	}
}