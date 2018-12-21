package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.ANTENNA })
@Line(wireCount = 1)
@Describable(allowedKeys = { AbstractLine.LINE_NAME_KEY })
public class CoaxLine extends AbstractLine {

}
