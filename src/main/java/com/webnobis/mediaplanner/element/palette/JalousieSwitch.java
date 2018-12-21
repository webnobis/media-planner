package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class JalousieSwitch extends SeriesSwitch {

	private static final String J = "J";

	/**
	 * @see com.webnobis.mediaplanner.element.palette.Switch#paintSwitchCharacteristic(Graphics2D, int, int, int)
	 */
	@Override
	protected void paintSwitchCharacteristic(Graphics2D pGraphics, int pCenterX, int pCenterY, int pDiameter) {
		super.paintSwitchCharacteristic(pGraphics, pCenterX, pCenterY, pDiameter);
		pGraphics.drawString(J, pCenterX - (int) (pDiameter * SWITCH_LINE_X_FACTOR * 2), pCenterY);
	}

}
