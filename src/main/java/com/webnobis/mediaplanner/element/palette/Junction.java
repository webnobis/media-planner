package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER, Palette.CONTROL })
public class Junction extends AbstractElement {

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#clearPaintElement(java.awt.Graphics2D)
	 */
	@Override
	protected void clearPaintElement(Graphics2D pGraphics) {
		CirclePainter.paint(this, pGraphics, Math.min(PaintUtil.getWidth(this), PaintUtil.getHeight(this)), true);
	}

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		CirclePainter.paint(this, pGraphics, Math.min(PaintUtil.getWidth(this), PaintUtil.getHeight(this)), false);
	}

}
