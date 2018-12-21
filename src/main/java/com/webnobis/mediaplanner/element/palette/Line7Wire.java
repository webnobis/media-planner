package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.POWER })
@Line(wireCount=7)
@Describable(maxCount = 8, allowedKeys = { AbstractLine.LINE_NAME_KEY, "gegn", "sw1", "sw2", "sw3", "sw4", "sw5", "sw6" })
public class Line7Wire extends AbstractLine {

}
