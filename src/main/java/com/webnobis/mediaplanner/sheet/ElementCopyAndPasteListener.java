package com.webnobis.mediaplanner.sheet;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.ElementException;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.element.util.PaintUtil;
import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.Msg;

public class ElementCopyAndPasteListener extends MouseAdapter {

	private static final Logger sLog = LoggerFactory.getLogger(ElementCopyAndPasteListener.class);

	private Element mElement;

	private Component mComponent;

	private boolean mMove;

	private boolean mLinePart;

	/**
	 * Copies an element from the palette or removes a module from sheet.
	 * 
	 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent pEvent) {
		super.mousePressed(pEvent);
		if (!pEvent.isPopupTrigger() && PaletteService.class.isAssignableFrom(pEvent.getComponent().getClass())) {
			mMove = false;
			mLinePart = false;
			// gets the element
			Element element = ((PaletteService) pEvent.getComponent()).getElement(pEvent.getX(), pEvent.getY());
			if (element != null) {
				mElement = element; // get it
			}
			if (mElement != null) {
				// is a sheet
				if (SheetService.class.isAssignableFrom(pEvent.getComponent().getClass())) {
					// it's a line
					if (mElement.isLine()) {
						mLinePart = true;
						sLog.debug(mElement + " got");
					} else { // removes the module
						((SheetService) pEvent.getComponent()).removeElement(mElement);
						mMove = true;
						sLog.debug(mElement + " removed");
					}
				} else { // is a palette
					sLog.debug(mElement + " copied");
				}
			}
		}
	}

	/**
	 * Shows a rectangle at moving the element.
	 * 
	 * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent pEvent) {
		if ((mElement != null) && (mComponent != null)) {
			int module2 = Constants.MODULE_SIZE / 2;
			Point p = SwingUtilities.convertPoint(pEvent.getComponent(), pEvent.getPoint(), mComponent);
			int x = p.x - module2;
			int y = p.y - module2;
			Graphics graphics = mComponent.getGraphics();
			graphics.drawRect(x, y, Constants.MODULE_SIZE, Constants.MODULE_SIZE);
			mComponent.repaint();
		}
		super.mouseDragged(pEvent);
	}

	/**
	 * Members the current component.
	 * 
	 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent pEvent) {
		super.mouseEntered(pEvent);
		mComponent = pEvent.getComponent(); // set the target
	}

	/**
	 * Kill the membered component.
	 * 
	 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent pEvent) {
		mComponent = null; // reset
		super.mouseExited(pEvent);
	}

	/**
	 * Pastes a new element at the current position, depends on the copied or removed element.
	 * 
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent pEvent) {
		if (!pEvent.isPopupTrigger() && (mElement != null) && (mComponent != null) && SheetService.class.isAssignableFrom(mComponent.getClass())) {
			Point p = SwingUtilities.convertPoint(pEvent.getComponent(), pEvent.getPoint(), mComponent);
			if (mLinePart && pEvent.getComponent().equals(mComponent)) {
				mElement.getPositions().add(new XY(PaintUtil.getRastered(pEvent.getX()), PaintUtil.getRastered(pEvent.getY())));
				sLog.debug(mElement + " " + mElement.getPositions().size() + ". line part added");
				mComponent.repaint();
			} else {
				try {
					Element element = PaintUtil.createFrom(mElement, p.x, p.y, true);
					if (mMove) { // take the properties from the other
						if (element.isTwistable()) {
							element.setDirection(mElement.getDirection());
						}
						if (element.isDescribable()) {
							element.getDescriptions().clear();
							element.getDescriptions().addAll(mElement.getDescriptions());
						}
					}
					if (element != null) {
						if (((SheetService) mComponent).addElement(element)) {
							sLog.debug(element + " pasted");
							if (element.isLine()) {
								mElement = element; // overwrite it for later line parts
							}
							mComponent.repaint();
						} else {
							sLog.debug(element + " not pastable at " + p.x + ':' + p.y);
							JOptionPane.showMessageDialog(pEvent.getComponent().getParent(), Msg.getText("msg.warn.element.free", mElement, p.x, p.y, element), Msg.getText("msg.warn.title"),
									JOptionPane.WARNING_MESSAGE);
						}
					}
				} catch (ElementException e) {
					JOptionPane.showMessageDialog(pEvent.getComponent().getParent(), Msg.getText("msg.error.element.copy", mElement, p.x, p.y, e.getMessage()), Msg.getText("msg.error.title"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
			mElement = null;
		}
		super.mouseReleased(pEvent);
	}

}
