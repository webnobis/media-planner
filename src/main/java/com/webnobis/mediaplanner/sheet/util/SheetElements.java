package com.webnobis.mediaplanner.sheet.util;

import java.awt.Dimension;

import com.webnobis.mediaplanner.element.Element;

public class SheetElements {

	private final Iterable<Element> mElements;

	private final Dimension mSheetSize;

	/**
	 * Constructor
	 * 
	 * @param pElements
	 * @param pSheetSize
	 */
	public SheetElements(Iterable<Element> pElements, int pWidth, int pHeight) {
		mElements = pElements;
		mSheetSize = new Dimension(pWidth, pHeight);
	}

	/**
	 * @return the elements
	 */
	public Iterable<Element> getElements() {
		return mElements;
	}

	/**
	 * @return the sheetSize
	 */
	public Dimension getSheetSize() {
		return mSheetSize;
	}

}
