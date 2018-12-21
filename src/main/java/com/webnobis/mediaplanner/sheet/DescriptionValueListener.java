package com.webnobis.mediaplanner.sheet;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.text.JTextComponent;

public class DescriptionValueListener implements ItemListener {

	private final JTextComponent mValueField;

	/**
	 * Constructor
	 * 
	 * @param pValueField
	 */
	public DescriptionValueListener(JTextComponent pValueField) {
		mValueField = pValueField;
	}

	@Override
	public void itemStateChanged(ItemEvent pEvent) {
		mValueField.setEnabled(!DescriptionPanel.NO_KEY.equals(pEvent.getItem()));
	}

}
