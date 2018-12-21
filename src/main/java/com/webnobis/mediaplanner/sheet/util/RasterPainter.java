package com.webnobis.mediaplanner.sheet.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RasterPainter {

	public static void paint(Graphics2D pGraphics, Rectangle pArea) {
		pGraphics.setColor(Constants.RASTER_COLOR);
		for (int x = pArea.x + Constants.RASTER; x < pArea.width; x += Constants.RASTER) {
			pGraphics.drawLine(x, pArea.y, x, pArea.height);
		}
		for (int y = pArea.y + Constants.RASTER; y < pArea.height; y += Constants.RASTER) {
			pGraphics.drawLine(pArea.x, y, pArea.width, y);
		}
		pGraphics.setColor(Constants.FOREGROUND_COLOR);
	}

}
