package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
@Line(wireCount=3)
@Describable(maxCount = 4, allowedKeys = { AbstractLine.LINE_NAME_KEY, "gegn", "bl", "br", "sw" })
public class Line3Wire extends AbstractLine {

}
