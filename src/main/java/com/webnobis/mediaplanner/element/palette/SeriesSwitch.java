package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class SeriesSwitch extends Switch {

	/**
	 * @see com.webnobis.mediaplanner.element.palette.Switch#paintSwitchCharacteristic(Graphics2D, int, int, int)
	 */
	@Override
	protected void paintSwitchCharacteristic(Graphics2D pGraphics, int pCenterX, int pCenterY, int pDiameter) {
		int x1 = pCenterX + (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y1 = pCenterY - (int) (pDiameter * SWITCH_LINE_Y1_FACTOR);
		int x2 = pCenterX - (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int x3 = x1 + (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y2 = y1 + (int) (pDiameter * SWITCH_LINE_Y2_FACTOR);
		int x4 = x2 - (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		pGraphics.drawLine(x1, y1, pCenterX, pCenterY);
		pGraphics.drawLine(pCenterX, pCenterY, x2, y1);
		pGraphics.drawLine(x1, y1, x3, y2);
		pGraphics.drawLine(x2, y1, x4, y2);
	}

}
