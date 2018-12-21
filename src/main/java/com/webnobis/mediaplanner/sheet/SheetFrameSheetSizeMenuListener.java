package com.webnobis.mediaplanner.sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SheetFrameSheetSizeMenuListener implements ActionListener {

	private final SheetSizeMenuItem mMenuItem;

	private final SheetFrame mSheetFrame;

	/**
	 * Constructor
	 * 
	 * @param pMenuItem
	 * @param pSheetFrame
	 */
	public SheetFrameSheetSizeMenuListener(SheetSizeMenuItem pMenuItem, SheetFrame pSheetFrame) {
		super();
		mMenuItem = pMenuItem;
		mSheetFrame = pSheetFrame;
		if (mMenuItem.isSelected()) {
			this.actionPerformed(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent pEvent) {
		mSheetFrame.setSheetSize(mMenuItem.getSheetSize());
	}

}
