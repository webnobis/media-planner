package com.webnobis.mediaplanner.sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.webnobis.mediaplanner.sheet.util.SheetMenuAction;

public class SheetFrameMenuListener implements ActionListener {

	private final SheetMenuAction mMenuAction;

	private final SheetFrame mSheetFrame;

	/**
	 * Constructor
	 * 
	 * @param pMenuAction
	 * @param pSheetFrame
	 */
	public SheetFrameMenuListener(SheetMenuAction pMenuAction, SheetFrame pSheetFrame) {
		mMenuAction = pMenuAction;
		mSheetFrame = pSheetFrame;
	}

	@Override
	public void actionPerformed(ActionEvent pEvent) {
		switch (mMenuAction) {
		case STORE:
			mSheetFrame.storeSheet();
			break;
		case STORE_AS:
			mSheetFrame.storeSheetAs();
			break;
		case EXPORT_AS_IMAGE:
			mSheetFrame.exportSheetAsImage();
			break;
		default:
			// nothing to do
		}
	}

}
