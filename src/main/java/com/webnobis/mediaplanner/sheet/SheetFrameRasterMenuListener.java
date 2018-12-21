package com.webnobis.mediaplanner.sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class SheetFrameRasterMenuListener implements ActionListener {

	private final JCheckBoxMenuItem mMenuItem;

	private final SheetFrame mSheetFrame;

	/**
	 * Constructor
	 * 
	 * @param pMenuItem
	 * @param pSheetFrame
	 */
	public SheetFrameRasterMenuListener(JCheckBoxMenuItem pMenuItem, SheetFrame pSheetFrame) {
		super();
		mMenuItem = pMenuItem;
		mSheetFrame = pSheetFrame;
		if (mMenuItem.isSelected()) {
			this.actionPerformed(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent pEvent) {
		mSheetFrame.setRaster(mMenuItem.isSelected());
	}

}
