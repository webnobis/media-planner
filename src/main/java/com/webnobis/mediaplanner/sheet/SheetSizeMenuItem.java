package com.webnobis.mediaplanner.sheet;

import java.awt.Dimension;

import javax.swing.JRadioButtonMenuItem;

public class SheetSizeMenuItem extends JRadioButtonMenuItem {

	private static final long serialVersionUID = 1L;

	private final Dimension mSheetSize;

	/**
	 * Constructor
	 * 
	 * @param pWidth
	 * @param pHeight
	 */
	public SheetSizeMenuItem(String pName, int pWidth, int pHeight) {
		super(pName);
		mSheetSize = new Dimension(pWidth, pHeight);
	}

	/**
	 * @return the size
	 */
	public Dimension getSheetSize() {
		return mSheetSize;
	}
}
