package com.webnobis.mediaplanner.sheet;

import javax.swing.JMenuItem;

import com.webnobis.mediaplanner.sheet.util.Palette;

public class PaletteMenuItem extends JMenuItem {

	private static final long serialVersionUID = 1L;

	private final Palette mPalette;

	/**
	 * Constructor
	 * 
	 * @param pWidth
	 * @param pHeight
	 */
	public PaletteMenuItem(String pName, Palette pPalette) {
		super(pName);
		mPalette = pPalette;
		;
	}

	/**
	 * @return the size
	 */
	public Palette getPalette() {
		return mPalette;
	}
}
