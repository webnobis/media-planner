package com.webnobis.mediaplanner.sheet.util;

import javax.xml.bind.annotation.XmlAttribute;

public class XmlPosition {

	@XmlAttribute(name = "id")
	private final int mId;

	@XmlAttribute(name = "x")
	private final int mX;

	@XmlAttribute(name = "y")
	private final int mY;

	/**
	 * Constructor, only for JAXB
	 * 
	 */
	XmlPosition() {
		this(0, 0, 0);
	}

	/**
	 * Constructor
	 * 
	 * @param pId
	 * @param pX
	 * @param pY
	 */
	public XmlPosition(int pId, int pX, int pY) {
		mId = pId;
		mX = pX;
		mY = pY;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return mX;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return mY;
	}

}
