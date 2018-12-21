package com.webnobis.mediaplanner.element;

public class XY {

	private static final String PART = ":";

	private final int mX;

	private final int mY;

	/**
	 * Constructor
	 * 
	 * @param pX
	 * @param pY
	 */
	public XY(int pX, int pY) {
		mX = pX;
		mY = pY;
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

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pObj) {
		if (pObj == null) {
			return false;
		} else if (pObj == this) {
			return true;
		} else if (XY.class.equals(pObj.getClass())) {
			XY other = (XY) pObj;
			return (this.getX() == other.getX()) && (this.getY() == other.getY());
		} else {
			return false;
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getX() ^ this.getY();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mX + PART + mY;
	}

}
