package com.webnobis.mediaplanner.element.palette;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Line;
import com.webnobis.mediaplanner.sheet.util.Palette;

@Group(palette = { Palette.CONTROL })
@Line(wireCount = 16)
@Describable(maxCount = 17, allowedKeys = { AbstractLine.LINE_NAME_KEY, "sw", "ge", "rt", "ws", "br", "gn", "bl", "wssw", "wsge", "wsbr", "wsgn" })
public class ControlLine16Wire extends AbstractLine {

}
