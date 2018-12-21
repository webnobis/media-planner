package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Twistable;
import com.webnobis.mediaplanner.element.util.DescriptionPainter;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER, Palette.CONTROL, Palette.ANTENNA, Palette.TUBE })
@Twistable(defaultDirection = Direction.EAST)
@Describable(allowedKeys = { "text" })
public class Break extends AbstractElement {

	public static final int LINE_LENGTH = Constants.MODULE_SIZE / 3;

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		int x1 = PaintUtil.getCenterX(this);
		int y1 = PaintUtil.getCenterY(this);
		int x2, y2;
		switch (super.getDirection()) {
		case EAST:
		case WEST:
			x2 = x1;
			y1 -= (LINE_LENGTH / 2);
			y2 = y1 + LINE_LENGTH;
			break;
		default:
			x1 -= (LINE_LENGTH / 2);
			x2 = x1 + LINE_LENGTH;
			y2 = y1;
		}
		pGraphics.drawLine(x1, y1, x2, y2);
		if (!super.getDescriptions().isEmpty()) {
			Description description = super.getDescriptions().get(0);
			if (description != null) {
				DescriptionPainter.paint(this, pGraphics, description.getValue(), super.getDirection());
			}
		}
	}

}
