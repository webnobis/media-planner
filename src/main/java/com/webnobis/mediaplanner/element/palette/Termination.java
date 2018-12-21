package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Twistable;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Describable(allowedKeys = { "name" })
@Twistable(defaultDirection = Direction.NORTH)
@Group(palette = { Palette.ANTENNA, Palette.CONTROL })
public class Termination extends Connection {

	/**
	 * @see com.webnobis.mediaplanner.element.palette.Connection#isPaintFilled()
	 */
	@Override
	protected boolean isPaintFilled() {
		return true;
	}

}
