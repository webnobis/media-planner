package com.webnobis.mediaplanner.sheet;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SheetSizeChangeListener extends ComponentAdapter implements ChangeListener {

	private final SheetFrame mSheetFrame;

	private final SheetPanel mSheetPanel;

	/**
	 * Constructor
	 * 
	 * @param pSheetFrame
	 * @param pSheetPanel
	 */
	public SheetSizeChangeListener(SheetFrame pSheetFrame, SheetPanel pSheetPanel) {
		super();
		mSheetFrame = pSheetFrame;
		mSheetPanel = pSheetPanel;
	}

	@Override
	public void stateChanged(ChangeEvent pEvent) {
		mSheetPanel.invalidate();
		mSheetPanel.repaint();
	}

	/**
	 * @see java.awt.event.ComponentAdapter#componentResized(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent pEvent) {
		mSheetFrame.setSize(mSheetPanel.getPreferredSize());
	}

}
