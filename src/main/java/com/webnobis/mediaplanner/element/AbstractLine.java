package com.webnobis.mediaplanner.element;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.util.LinePainter;

public abstract class AbstractLine extends AbstractElement {

	public static final int DEFAULT_WIRE_COUNT = 1;

	public static final String LINE_NAME_KEY = "lineName";

	private final int mWireCount;

	/**
	 * Constructor
	 * 
	 */
	public AbstractLine() {
		super();
		if (this.isLine()) {
			Line line = this.getClass().getAnnotation(Line.class);
			mWireCount = line.wireCount();
		} else {
			mWireCount = DEFAULT_WIRE_COUNT;
		}
	}

	/**
	 * @return the wireCount
	 */
	public int getWireCount() {
		return mWireCount;
	}

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		LinePainter.paint(this, pGraphics);
	}

}
