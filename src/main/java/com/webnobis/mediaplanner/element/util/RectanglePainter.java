package com.webnobis.mediaplanner.element.util;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.Element;

public class RectanglePainter {

	public static final int MINIMUM_WIDTH_AND_HEIGHT = 2;

	public static void paint(Element pElement, Graphics2D pGraphics, int pWidth, int pHeight, boolean pFill) {
		if (PaintUtil.isPaintable(pElement)) {
			int w = Math.max(MINIMUM_WIDTH_AND_HEIGHT, pWidth);
			int h = Math.max(MINIMUM_WIDTH_AND_HEIGHT, pHeight);
			int x = PaintUtil.getCenterX(pElement) - (w / 2);
			int y = PaintUtil.getCenterY(pElement) - (h / 2);
			if (pFill) {
				pGraphics.fillRect(x, y, w, h);
			} else {
				pGraphics.drawRect(x, y, w, h);
			}
		}
	}

}
