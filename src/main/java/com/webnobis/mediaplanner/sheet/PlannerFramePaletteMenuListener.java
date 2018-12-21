package com.webnobis.mediaplanner.sheet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlannerFramePaletteMenuListener implements ActionListener {

	private final PaletteMenuItem mMenuItem;

	private final PlannerFrame mPlannerFrame;

	/**
	 * Constructor
	 * 
	 * @param pMenuItem
	 * @param pPlannerFrame
	 */
	public PlannerFramePaletteMenuListener(PaletteMenuItem pMenuItem, PlannerFrame pPlannerFrame) {
		super();
		mMenuItem = pMenuItem;
		mPlannerFrame = pPlannerFrame;
	}

	@Override
	public void actionPerformed(ActionEvent pEvent) {
		mPlannerFrame.loadPalette(mMenuItem.getPalette());
	}

}
