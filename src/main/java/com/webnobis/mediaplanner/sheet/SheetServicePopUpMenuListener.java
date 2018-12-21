package com.webnobis.mediaplanner.sheet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.sheet.util.PopUpMenuAction;

public class SheetServicePopUpMenuListener implements ActionListener {

	private final PopUpMenuAction mMenuAction;

	private final Element mElement;

	private final SheetService mSheetService;

	/**
	 * Constructor
	 * 
	 * @param pMenuAction
	 * @param pElement
	 * @param pSheetService
	 */
	public SheetServicePopUpMenuListener(PopUpMenuAction pMenuAction, Element pElement, SheetService pSheetService) {
		mMenuAction = pMenuAction;
		mElement = pElement;
		mSheetService = pSheetService;
	}

	@Override
	public void actionPerformed(ActionEvent pEvent) {
		switch (mMenuAction) {
		case UPDATE:
			mSheetService.updateElement(mElement);
			break;
		case TWIST:
			mSheetService.twistElement(mElement);
			break;
		case REMOVE:
			mSheetService.removeElement(mElement);
			break;
		case REMOVE_LAST_LINE_PART:
			mSheetService.removeLastLinePart(mElement);
			break;
		}
		if (Component.class.isAssignableFrom(mSheetService.getClass())) {
			((Component)mSheetService).repaint();
		}
	}

}
