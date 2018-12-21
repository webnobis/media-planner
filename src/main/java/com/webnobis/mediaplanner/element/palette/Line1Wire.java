package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
@Line(wireCount=1)
@Describable(maxCount = 2, allowedKeys = { AbstractLine.LINE_NAME_KEY, "gegn", "bl", "sw", "br", "gr", "cu" })
public class Line1Wire extends AbstractLine {

}
