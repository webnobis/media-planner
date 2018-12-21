package com.webnobis.mediaplanner.element.util;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.sheet.util.Constants;

public class CirclePainter {

	public static final int MINIMUM_DIAMETER = 2;

	public static void paint(Element pElement, Graphics2D pGraphics, int pDiameter, boolean pFill) {
		if (PaintUtil.isPaintable(pElement)) {
			int diameter = Math.max(MINIMUM_DIAMETER, pDiameter);
			int x = PaintUtil.getCenterX(pElement) - (diameter / 2);
			int y = PaintUtil.getCenterY(pElement) - (diameter / 2);
			if (pFill) {
				pGraphics.fillOval(x, y, diameter, diameter);
			} else {
				pGraphics.drawOval(x, y, diameter, diameter);
			}
		}
	}

	public static void paintWithBackground(Element pElement, Graphics2D pGraphics, int pDiameter) {
		if (PaintUtil.isPaintable(pElement)) {
			pGraphics.setColor(Constants.BACKGROUND_COLOR);
			paint(pElement, pGraphics, pDiameter, true);
			pGraphics.setColor(Constants.FOREGROUND_COLOR);
		}
	}

	public static void paintTopHalf(Element pElement, Graphics2D pGraphics, int pDiameter) {
		if (PaintUtil.isPaintable(pElement)) {
			paint(pElement, pGraphics, pDiameter, false);
			int diameter = Math.max(MINIMUM_DIAMETER, pDiameter);
			int x = PaintUtil.getCenterX(pElement) - diameter;
			int y = PaintUtil.getCenterY(pElement) + (diameter / 3);
			pGraphics.setColor(Constants.BACKGROUND_COLOR);
			pGraphics.fillRect(x, y, diameter * 2, diameter);
			pGraphics.setColor(Constants.FOREGROUND_COLOR);
		}
	}

}
