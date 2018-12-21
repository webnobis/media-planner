package com.webnobis.mediaplanner.sheet.util;

import java.awt.Color;
import java.awt.Font;

public abstract class Constants {

	private Constants() {}
	
	public static final int RASTER = 25;
	
	public static final int LINE_FOUND_SIZE = Constants.RASTER / 2;
	
	public static final int MODULE_SIZE = Constants.RASTER * 2;
	
	public static final int PALETTE_COLS = 3;
	
	public static final int PIXEL_SIZE1 = 1000;
	
	public static final int PIXEL_SIZE2 = Math.round(PIXEL_SIZE1 * 11.69f / 8.27f);
	
	public static final float A4_SCALE = 3507f / PIXEL_SIZE2; // 300 dpi
	
	public static final Color RASTER_COLOR = new Color(230, 230, 230);
	
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	public static final Color FOREGROUND_COLOR = Color.BLACK;
	
	public static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 16);
	
}
