package com.webnobis.mediaplanner.element.palette;

import java.util.Collections;
import java.util.List;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER, Palette.CONTROL })
@Line(wireCount=1)
public class UndescribedLine1Wire extends AbstractLine {

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#getDescriptions()
	 */
	@Override
	public List<Description> getDescriptions() {
		return Collections.emptyList();
	}
	
	

}
