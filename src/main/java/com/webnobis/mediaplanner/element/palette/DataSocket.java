package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.CONTROL })
public class DataSocket extends AbstractElement {

	public static final float SOCKET_DIAMETER_FACTOR = 1f / 3;

	public static final float SOCKET_WIDTH_FACTOR = SOCKET_DIAMETER_FACTOR;

	public static final float SOCKET_HEIGHT_FACTOR = SOCKET_WIDTH_FACTOR / 1.5f;

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
		width *= SOCKET_WIDTH_FACTOR;
		height *= SOCKET_HEIGHT_FACTOR;
		int x1 = PaintUtil.getCenterX(this);
		int y1 = PaintUtil.getY(this) + (int) (diameter * SOCKET_LINE_FACTOR);
		int y2 = PaintUtil.getCenterY(this) - (height / 2);
		int x2 = x1 - (width / 2);
		int y3 = y2 + height;
		int x3 = x2 + width;
		pGraphics.drawLine(x1, y1, x1, y2);
		pGraphics.drawLine(x2, y3, x2, y2);
		pGraphics.drawLine(x2, y2, x3, y2);
		pGraphics.drawLine(x3, y2, x3, y3);
	}

}
