package com.webnobis.mediaplanner.element.util;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Element;

public class DescriptionPainter {

	public static void paint(Element pElement, Graphics2D pGraphics, String pDescription, Direction pDirection) {
		if (PaintUtil.isPaintable(pElement) && (pDescription != null) && (pDirection != null) && (!pElement.isReadonly() || Direction.CENTER.equals(pDirection))) {
			int x = getX(pElement, getTextWidth(pGraphics, pDescription), pDirection);
			int y = getY(pElement, getTextHeight(pGraphics, pDescription), pDirection);
			pGraphics.drawString(pDescription, x, y);
		}
	}

	private static int getTextWidth(Graphics2D pGraphics, String pDescription) {
		return (int) pGraphics.getFont().getStringBounds(pDescription, pGraphics.getFontRenderContext()).getWidth();
	}

	private static int getX(Element pElement, int pTextWidth, Direction pDirection) {
		switch (pDirection) {
		case EAST:
			return PaintUtil.getX(pElement) + PaintUtil.getWidth(pElement);
		case WEST:
			return PaintUtil.getX(pElement) - pTextWidth;
		default:
			return PaintUtil.getCenterX(pElement) - (pTextWidth / 2);
		}
	}

	private static int getTextHeight(Graphics2D pGraphics, String pDescription) {
		return (int) pGraphics.getFont().getStringBounds(pDescription, pGraphics.getFontRenderContext()).getHeight();
	}

	private static int getY(Element pElement, int pTextHeight, Direction pDirection) {
		switch (pDirection) {
		case NORTH:
			return PaintUtil.getY(pElement);
		case SOUTH:
			return PaintUtil.getY(pElement) + PaintUtil.getHeight(pElement) + pTextHeight;
		default:
			return PaintUtil.getCenterY(pElement) + (pTextHeight / 2);
		}
	}

}
