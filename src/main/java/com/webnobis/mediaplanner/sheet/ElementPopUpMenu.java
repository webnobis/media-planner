/**
 * 
 */
package com.webnobis.mediaplanner.sheet;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.PopUpMenuAction;

/**
 * @author steffen
 * @version 1.00
 */
public class ElementPopUpMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;

	private final SheetService mSheetService;

	/**
	 * Constructor
	 * 
	 * @param pSheetService
	 */
	public ElementPopUpMenu(SheetService pSheetService) {
		super();
		mSheetService = pSheetService;
	}

	/**
	 * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
	 */
	@Override
	public void show(Component pInvoker, int pX, int pY) {
		Element element = mSheetService.getElement(pX, pY);
		if ((element != null) && !element.isReadonly()) {
			addRelevantItems(element);
			super.show(pInvoker, pX, pY);
		}
	}

	private void addRelevantItems(Element pElement) {
		JMenuItem updateItem = new JMenuItem(Msg.getText("popup.element.update"));
		if (pElement.isDescribable()) {
			addListener(PopUpMenuAction.UPDATE, pElement, updateItem);
		} else {
			updateItem.setEnabled(false);
		}
		super.add(updateItem);
		JMenuItem twistItem = new JMenuItem(Msg.getText("popup.element.twist"));
		if (pElement.isTwistable()) {
			addListener(PopUpMenuAction.TWIST, pElement, twistItem);
		} else {
			twistItem.setEnabled(false);
		}
		super.add(twistItem);
		JMenuItem removeItem = new JMenuItem(Msg.getText("popup.element.remove"));
		addListener(PopUpMenuAction.REMOVE, pElement, removeItem);
		super.add(removeItem);
		JMenuItem removeLastLinePartItem = new JMenuItem(Msg.getText("popup.element.remove.lastlinepart"));
		if (pElement.isLine()) {
			addListener(PopUpMenuAction.REMOVE_LAST_LINE_PART, pElement, removeLastLinePartItem);
		} else {
			removeLastLinePartItem.setEnabled(false);
		}
		super.add(removeLastLinePartItem);
	}

	private void addListener(PopUpMenuAction pMenuAction, Element pElement, JMenuItem pMenuItem) {
		pMenuItem.addActionListener(new SheetServicePopUpMenuListener(pMenuAction, pElement, mSheetService));
	}

}
