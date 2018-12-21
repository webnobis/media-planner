package com.webnobis.mediaplanner.sheet;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.Palette;
import com.webnobis.mediaplanner.sheet.util.PaletteUtil;

public class PaletteFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private final PalettePanel mPalettePanel;

	private Palette mPalette;

	/**
	 * Constructor
	 * 
	 * @param pCopyAndPasteListener
	 */
	public PaletteFrame(ElementCopyAndPasteListener pCopyAndPasteListener) {
		super(Msg.getText("palette.empty"), false, true, false, true);
		mPalettePanel = new PalettePanel(pCopyAndPasteListener);

		Container container = super.getContentPane();
		container.setLayout(new GridLayout(1, 1));
		container.add(new JScrollPane(mPalettePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		super.setLayer(JLayeredPane.DRAG_LAYER); // always on top
		super.pack();
		super.setVisible(true);
	}

	public void loadPalette(Palette pPalette) {
		if (pPalette != null) {
			mPalette = pPalette;
			super.setTitle(Msg.getText("menu.palette." + pPalette.name().toLowerCase()));
			mPalettePanel.loadElements(PaletteUtil.getElements(pPalette));

			super.setSize(mPalettePanel.getPreferredSize());
			super.pack();

			super.repaint();
		}
	}

	public boolean isLoadedPalette(Palette pPalette) {
		return (mPalette != null) && mPalette.equals(pPalette);
	}

}
