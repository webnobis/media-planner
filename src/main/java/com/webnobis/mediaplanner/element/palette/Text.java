package com.webnobis.mediaplanner.element.palette;

import java.awt.Graphics2D;

import com.webnobis.mediaplanner.element.AbstractElement;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Twistable;
import com.webnobis.mediaplanner.element.util.DescriptionPainter;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER, Palette.CONTROL, Palette.ANTENNA, Palette.TUBE })
@Describable(allowedKeys = { "text" })
@Twistable(defaultDirection=Direction.CENTER, allowedDirections={Direction.WEST, Direction.CENTER, Direction.EAST})
public class Text extends AbstractElement {

	@Override
	protected void paintElement(Graphics2D pGraphics) {
		if (super.getDescriptions().isEmpty()) {
			DescriptionPainter.paint(this, pGraphics, super.getName(), Direction.CENTER);
		} else {
			Description description = super.getDescriptions().get(0);
			if (description != null) {
				DescriptionPainter.paint(this, pGraphics, description.getValue(), super.getDirection());
			}
		}
	}

}
