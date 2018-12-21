package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class Switch extends AbstractElement {

	public static final float SWITCH_DIAMETER_FACTOR = 0.25f;

	public static final float SWITCH_LINE_X_FACTOR = SWITCH_DIAMETER_FACTOR / 3 * 2;

	public static final float SWITCH_LINE_Y1_FACTOR = SWITCH_LINE_X_FACTOR * 2;

	public static final float SWITCH_LINE_Y2_FACTOR = SWITCH_LINE_X_FACTOR / 2;

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

		paintSwitchCharacteristic(pGraphics, PaintUtil.getCenterX(this), PaintUtil.getCenterY(this), diameter); // separate call to overwrite for other switches

		diameter *= SWITCH_DIAMETER_FACTOR;
		CirclePainter.paintWithBackground(this, pGraphics, diameter);
		CirclePainter.paint(this, pGraphics, diameter, false);
	}

	protected void paintSwitchCharacteristic(Graphics2D pGraphics, int pCenterX, int pCenterY, int pDiameter) {
		int x1 = pCenterX + (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y1 = pCenterY - (int) (pDiameter * SWITCH_LINE_Y1_FACTOR);
		int x2 = x1 + (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y2 = y1 + (int) (pDiameter * SWITCH_LINE_Y2_FACTOR);
		pGraphics.drawLine(pCenterX, pCenterY, x1, y1);
		pGraphics.drawLine(x1, y1, x2, y2);
	}

}
