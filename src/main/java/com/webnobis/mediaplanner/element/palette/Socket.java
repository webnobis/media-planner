package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class Socket extends AbstractElement {

	public static final float SOCKET_DIAMETER_FACTOR = 1f / 3;

	public static final float SOCKET_LINE_FACTOR = 0.8f;

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#clearPaintElement(java.awt.Graphics2D)
	 */
	@Override
	protected void clearPaintElement(Graphics2D pGraphics) {
		CirclePainter.paint(this, pGraphics, Math.min(PaintUtil.getWidth(this), PaintUtil.getHeight(this)), true);
	}

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		int width = PaintUtil.getWidth(this);
		int height = PaintUtil.getHeight(this);
		int diameter = Math.min(width, height);
		CirclePainter.paint(this, pGraphics, diameter, false);

		diameter *= SOCKET_DIAMETER_FACTOR;
		int x1 = PaintUtil.getCenterX(this);
		int y1 = PaintUtil.getY(this) + (int) (diameter * SOCKET_LINE_FACTOR);
		int y2 = PaintUtil.getCenterY(this);
		pGraphics.drawLine(x1, y1, x1, y2);
		x1 = x1 - (diameter / 2);
		int x2 = x1 + diameter;
		y1 = y2 - (diameter / 2);
		pGraphics.drawLine(x1, y1, x2, y1);

		CirclePainter.paintWithBackground(this, pGraphics, diameter);
		CirclePainter.paintTopHalf(this, pGraphics, diameter);
	}

}
