package com.webnobis.mediaplanner.sheet;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.Palette;
import com.webnobis.mediaplanner.sheet.util.SheetMenuAction;

public class PlannerFrameMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private final PlannerFrame mPlannerFrame;

	/**
	 * Constructor
	 * 
	 * @param pPlannerFrame
	 */
	public PlannerFrameMenu(PlannerFrame pPlannerFrame) {
		super();
		mPlannerFrame = pPlannerFrame;
		this.add(this.getFileMenu());
		this.add(this.getPaletteMenu());
	}

	private JMenu getFileMenu() {
		JMenu menu = new JMenu(Msg.getText("menu.sheet.file"));
		JMenuItem item = new JMenuItem(Msg.getText("menu.sheet.new"));
		item.addActionListener(new PlannerFrameSheetMenuListener(SheetMenuAction.NEW, mPlannerFrame));
		menu.add(item);
		item = new JMenuItem(Msg.getText("menu.sheet.load"));
		item.addActionListener(new PlannerFrameSheetMenuListener(SheetMenuAction.LOAD, mPlannerFrame));
		menu.add(item);
		return menu;
	}

	private JMenu getPaletteMenu() {
		JMenu menu = new JMenu(Msg.getText("menu.palette.palette"));
		PaletteMenuItem paletteItem;
		for (Palette p : Palette.values()) {
			paletteItem = new PaletteMenuItem(Msg.getText("menu.palette." + p.name().toLowerCase()), p);
			paletteItem.addActionListener(new PlannerFramePaletteMenuListener(paletteItem, mPlannerFrame));
			menu.add(paletteItem);
		}
		return menu;
	}
}
