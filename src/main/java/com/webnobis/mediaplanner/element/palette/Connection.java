package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Twistable;
import com.webnobis.mediaplanner.element.util.CirclePainter;
import com.webnobis.mediaplanner.element.util.DescriptionPainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Describable(allowedKeys = { "name" })
@Twistable(defaultDirection = Direction.NORTH)
@Group(palette = { Palette.POWER, Palette.CONTROL, Palette.ANTENNA, Palette.TUBE })
public class Connection extends AbstractElement {

	public static final float CONNECTION_DIAMETER_FACTOR = 0.4f;

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#clearPaintElement(java.awt.Graphics2D)
	 */
	@Override
	protected void clearPaintElement(Graphics2D pGraphics) {
		int diameter = (int) (Math.min(PaintUtil.getWidth(this), PaintUtil.getHeight(this)) * CONNECTION_DIAMETER_FACTOR);
		CirclePainter.paint(this, pGraphics, diameter, true);
	}

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		int diameter = (int) (Math.min(PaintUtil.getWidth(this), PaintUtil.getHeight(this)) * CONNECTION_DIAMETER_FACTOR);
		CirclePainter.paint(this, pGraphics, diameter, isPaintFilled());

		if (!super.getDescriptions().isEmpty()) {
			Description description = super.getDescriptions().get(0);
			if (description != null) {
				DescriptionPainter.paint(this, pGraphics, description.getValue(), super.getDirection());
			}
		}
	}
	
	protected boolean isPaintFilled() {
		return false;
	}

}
