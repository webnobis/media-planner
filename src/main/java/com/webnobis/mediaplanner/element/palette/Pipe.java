package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.TUBE })
@Line(wireCount = 1)
@Describable(maxCount = 3, allowedKeys = { AbstractLine.LINE_NAME_KEY, "size", "type" })
public class Pipe extends AbstractLine {

}
