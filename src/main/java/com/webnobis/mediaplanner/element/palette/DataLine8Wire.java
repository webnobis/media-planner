package com.webnobis.mediaplanner.element.palette;

import java.util.List;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.CONTROL })
@Line(wireCount = 8)
@Describable(maxCount = 9, allowedKeys = { AbstractLine.LINE_NAME_KEY, "orws", "or", "gnws", "bl", "blws", "gn", "brws", "br" })
public class DataLine8Wire extends AbstractLine {

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#getDefaultDescriptions(java.lang.String[])
	 */
	@Override
	protected List<Description> getDefaultDescriptions(String[] pKeys) {
		List<Description> descriptions = super.getDefaultDescriptions(pKeys);
		for (int i = 1; i < pKeys.length; i++) {
			descriptions.add(new Description(pKeys[i], String.valueOf(i)));
		}
		return descriptions;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.AbstractElement#getDescriptions()
	 */
	@Override
	public List<Description> getDescriptions() {
		if (super.isReadonly()) {
			return super.getDescriptions().subList(0, 1);
		} else {
			return super.getDescriptions();
		}
	}

}
