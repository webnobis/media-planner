package com.webnobis.mediaplanner.sheet;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.SheetMenuAction;

public class SheetFrameMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private final SheetFrame mSheetFrame;

	private final SheetSizeMenuItem mLandscapeSelector;

	private final SheetSizeMenuItem mPortraitSelector;

	/**
	 * Constructor
	 * 
	 * @param pSheetFrame
	 */
	public SheetFrameMenu(SheetFrame pSheetFrame) {
		super();
		mSheetFrame = pSheetFrame;
		mLandscapeSelector = new SheetSizeMenuItem(Msg.getText("menu.sheet.size.a4.landscape.name"), Constants.PIXEL_SIZE2, Constants.PIXEL_SIZE1);
		mPortraitSelector = new SheetSizeMenuItem(Msg.getText("menu.sheet.size.a4.portrait.name"), Constants.PIXEL_SIZE1, Constants.PIXEL_SIZE2);
		this.add(this.getFileMenu());
		this.add(this.getSheetMenu());
	}

	private JMenu getFileMenu() {
		JMenu menu = new JMenu(Msg.getText("menu.sheet.file"));
		JMenuItem item = new JMenuItem(Msg.getText("menu.sheet.store"));
		item.addActionListener(new SheetFrameMenuListener(SheetMenuAction.STORE, mSheetFrame));
		menu.add(item);
		item = new JMenuItem(Msg.getText("menu.sheet.store.as"));
		item.addActionListener(new SheetFrameMenuListener(SheetMenuAction.STORE_AS, mSheetFrame));
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem(Msg.getText("menu.sheet.export.as.image"));
		item.addActionListener(new SheetFrameMenuListener(SheetMenuAction.EXPORT_AS_IMAGE, mSheetFrame));
		menu.add(item);
		return menu;
	}

	private JMenu getSheetMenu() {
		JMenu menu = new JMenu(Msg.getText("menu.sheet.sheet"));
		JCheckBoxMenuItem rasterItem = new JCheckBoxMenuItem(Msg.getText("menu.sheet.raster"));
		rasterItem.setSelected(Boolean.parseBoolean(Msg.getText("menu.sheet.raster.visible")));
		rasterItem.addActionListener(new SheetFrameRasterMenuListener(rasterItem, mSheetFrame));
		menu.add(rasterItem);
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		mLandscapeSelector.setSelected(true);
		mLandscapeSelector.addActionListener(new SheetFrameSheetSizeMenuListener(mLandscapeSelector, mSheetFrame));
		menu.add(mLandscapeSelector);
		group.add(mLandscapeSelector);
		mPortraitSelector.addActionListener(new SheetFrameSheetSizeMenuListener(mPortraitSelector, mSheetFrame));
		menu.add(mPortraitSelector);
		group.add(mPortraitSelector);
		return menu;
	}

	public void updateSizeSelection(Dimension pDimension) {
		if ((pDimension != null) && (pDimension.height > pDimension.width)) {
			mPortraitSelector.setSelected(true);
		} else {
			mLandscapeSelector.setSelected(true);
		}
	}

}
