package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.DescriptionPainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Describable(allowedKeys = "count")
@Group(palette = { Palette.ANTENNA })
public class Splitter extends Junction {

	public static final char DEFAULT_COUNT = '4';

	public static final char ADDITIONAL_LETTER = '>';

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
		if (super.getDescriptions().isEmpty()) {
			DescriptionPainter.paint(this, pGraphics, String.valueOf(new char[] { DEFAULT_COUNT, ADDITIONAL_LETTER }), Direction.CENTER);
		} else {
			Description description = super.getDescriptions().get(0);
			if (description != null) {
				DescriptionPainter.paint(this, pGraphics, description.getValue() + ADDITIONAL_LETTER, Direction.CENTER);
			}
		}
	}

}
