package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class Lamp extends AbstractElement {

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
		CirclePainter.paint(this, pGraphics, Math.min(width, height), false);
		int xPI = (int) (width / Math.PI);
		int yPI = (int) (height / Math.PI);
		int x1 = PaintUtil.getX(this) + (xPI / 2);
		int y1 = PaintUtil.getY(this) + (yPI / 2);
		int x2 = x1 - xPI + width;
		int y2 = y1 - yPI + height;
		pGraphics.drawLine(x1, y1, x2, y2);
		pGraphics.drawLine(x2, y1, x1, y2);
	}

}
