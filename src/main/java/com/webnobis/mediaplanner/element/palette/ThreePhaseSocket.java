package com.webnobis.mediaplanner.element.palette;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
public class ThreePhaseSocket extends Socket {

	public static final String SOCKET_TYPE_NAME = "3~";

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		super.paintElement(pGraphics);
		FontMetrics fm = pGraphics.getFontMetrics();
		int textWidth = fm.stringWidth(SOCKET_TYPE_NAME);
		int x = PaintUtil.getCenterX(this) - (textWidth / 2);
		int y = PaintUtil.getCenterY(this) + fm.getAscent();
		pGraphics.drawString(SOCKET_TYPE_NAME, x, y);
	}

}
