package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.util.DescriptionPainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.element.util.RectanglePainter;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Describable(allowedKeys = "type")
@Group(palette = { Palette.POWER, Palette.CONTROL, Palette.ANTENNA })
public class Appliance extends AbstractElement {

	public static final String DEFAULT_TYPE = "E";

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#clearPaintElement(java.awt.Graphics2D)
	 */
	@Override
	protected void clearPaintElement(Graphics2D pGraphics) {
		RectanglePainter.paint(this, pGraphics, PaintUtil.getWidth(this), PaintUtil.getHeight(this), true);
	}

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		RectanglePainter.paint(this, pGraphics, PaintUtil.getWidth(this), PaintUtil.getHeight(this), false);
		if (super.getDescriptions().isEmpty()) {
			DescriptionPainter.paint(this, pGraphics, DEFAULT_TYPE, Direction.CENTER);
		} else {
			Description description = super.getDescriptions().get(0);
			if (description != null) {
				DescriptionPainter.paint(this, pGraphics, description.getValue(), Direction.CENTER);
			}
		}
	}

}
