package com.webnobis.mediaplanner.sheet;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ElementPopUpMenuListener extends MouseAdapter {

	/**
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent pEvent) {
		super.mousePressed(pEvent);
		showPopup(pEvent);
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent pEvent) {
		super.mouseReleased(pEvent);
		showPopup(pEvent);
	}

	private void showPopup(MouseEvent pEvent) {
		if (pEvent.isPopupTrigger()) {
			Component component = pEvent.getComponent();
			if (SheetService.class.isAssignableFrom(component.getClass())) {
				ElementPopUpMenu popUpMenu = new ElementPopUpMenu((SheetService) component);
				popUpMenu.show(component, pEvent.getX(), pEvent.getY());
				component.repaint();
			}
		}
	}

}
