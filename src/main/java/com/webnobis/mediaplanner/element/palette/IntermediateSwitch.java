package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class IntermediateSwitch extends ToggleSwitch {

	/**
	 * @see com.webnobis.mediaplanner.element.palette.Switch#paintSwitchCharacteristic(Graphics2D, int, int, int)
	 */
	@Override
	protected void paintSwitchCharacteristic(Graphics2D pGraphics, int pCenterX, int pCenterY, int pDiameter) {
		super.paintSwitchCharacteristic(pGraphics, pCenterX, pCenterY, pDiameter);
		int x1 = pCenterX - (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y1 = pCenterY - (int) (pDiameter * SWITCH_LINE_Y1_FACTOR);
		int x2 = pCenterX + (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y2 = pCenterY + (int) (pDiameter * SWITCH_LINE_Y1_FACTOR);
		int x3 = x2 + (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y3 = y2 - (int) (pDiameter * SWITCH_LINE_Y2_FACTOR);
		int x4 = x1 - (int) (pDiameter * SWITCH_LINE_X_FACTOR);
		int y4 = y1 + (int) (pDiameter * SWITCH_LINE_Y2_FACTOR);
		pGraphics.drawLine(x1, y1, x2, y2);
		pGraphics.drawLine(x2, y2, x3, y3);
		pGraphics.drawLine(x1, y1, x4, y4);
	}

}
