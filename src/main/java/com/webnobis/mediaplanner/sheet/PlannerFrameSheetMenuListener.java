package com.webnobis.mediaplanner.sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.webnobis.mediaplanner.sheet.util.SheetMenuAction;

public class PlannerFrameSheetMenuListener implements ActionListener {

	private final SheetMenuAction mMenuAction;

	private final PlannerFrame mPlannerFrame;

	/**
	 * Constructor
	 * 
	 * @param pMenuAction
	 * @param pPlannerFrame
	 */
	public PlannerFrameSheetMenuListener(SheetMenuAction pMenuAction, PlannerFrame pPlannerFrame) {
		mMenuAction = pMenuAction;
		mPlannerFrame = pPlannerFrame;
	}

	@Override
	public void actionPerformed(ActionEvent pEvent) {
		switch (mMenuAction) {
		case LOAD:
			mPlannerFrame.loadSheet();
			break;
		case NEW:
			mPlannerFrame.newSheet();
			break;
		default:
			// nothing to do
		}
	}

}
