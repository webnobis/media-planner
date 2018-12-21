package com.webnobis.mediaplanner.sheet;

import com.webnobis.mediaplanner.element.Element;

public interface PaletteService {
	
	public void loadElements(Iterable<Element> pElements);	
	
	public Element getElement(int pX, int pY);

}
