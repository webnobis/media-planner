package com.webnobis.mediaplanner.sheet;

import com.webnobis.mediaplanner.element.Element;

public interface SheetService extends PaletteService {
	
	public Iterable<Element> getElements();
	
	public boolean addElement(Element pElement);
	
	public Element removeElement(int pX, int pY);
	
	public void removeElement(Element pElement);
	
	public void removeLastLinePart(Element pElement);
	
	public void updateElement(Element pElement);
	
	public void twistElement(Element pElement);
	
	public void setRaster(boolean pVisible);

}
