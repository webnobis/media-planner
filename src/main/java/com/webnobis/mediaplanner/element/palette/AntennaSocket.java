package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.ANTENNA })
public class AntennaSocket extends DataSocket {

	public static final int SOCKET_LINE_SPACE = 3;

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		super.paintElement(pGraphics);
		int width = (int) (PaintUtil.getWidth(this) * DataSocket.SOCKET_WIDTH_FACTOR) - (SOCKET_LINE_SPACE * 2);
		int height = (int) (PaintUtil.getHeight(this) * DataSocket.SOCKET_HEIGHT_FACTOR) - SOCKET_LINE_SPACE;
		int x1 = PaintUtil.getCenterX(this) - (width / 2);
		int y1 = PaintUtil.getCenterY(this) - (height / 2);
		int x2 = x1 + width;
		int y2 = y1 + height;
		pGraphics.drawLine(x1, y2, x1, y1);
		pGraphics.drawLine(x1, y1, x2, y1);
		pGraphics.drawLine(x2, y1, x2, y2);
	}

}
