package com.webnobis.mediaplanner.sheet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.ElementList;

@Readonly
public class PalettePanel extends JPanel implements PaletteService {

	private static final long serialVersionUID = 1L;

	private static final Logger sLog = Logger.getLogger(PalettePanel.class);

	private final ElementList mPaletteElements;

	/**
	 * Constructor
	 * 
	 * @param pCopyAndPasteListener
	 */
	public PalettePanel(ElementCopyAndPasteListener pCopyAndPasteListener) {
		super(null);
		mPaletteElements = new ElementList();
		this.setFont(Constants.TEXT_FONT);
		super.addMouseListener(pCopyAndPasteListener);
		super.addMouseMotionListener(pCopyAndPasteListener);
	}

	public void loadElements(Iterable<Element> pElements) {
		if (pElements != null) {
			int xMax = Constants.MODULE_SIZE * Constants.PALETTE_COLS;
			int x1 = 1;
			int y1 = 1;
			int x2 = Constants.MODULE_SIZE;
			int y2 = Constants.MODULE_SIZE;
			boolean fullLine = false;
			for (Element e : pElements) {
				e.getPositions().add(new XY(x1, y1));
				e.getPositions().add(new XY(x2, y2));
				x2 += Constants.MODULE_SIZE;
				if (x2 > xMax) {
					x1 = 1;
					y1 += Constants.MODULE_SIZE;
					x2 = Constants.MODULE_SIZE;
					y2 += Constants.MODULE_SIZE;
					fullLine = true;
				} else {
					x1 += Constants.MODULE_SIZE;
					fullLine = false;
				}
				if (!mPaletteElements.add(e)) {
					sLog.warn("couldn't add element " + e + " to palette");
				}
			}
			if (fullLine) {
				y2 -= Constants.MODULE_SIZE;
			}
			super.setPreferredSize(new Dimension(Constants.MODULE_SIZE * Constants.PALETTE_COLS, y2));
		}
	}

	public Element getElement(int pX, int pY) {
		return mPaletteElements.get(pX, pY);
	}

	/**
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics pGraphics) {
		super.paint(pGraphics);

		Graphics2D graphics = (Graphics2D) pGraphics;
		for (Element e : mPaletteElements) {
			e.paintElement(graphics, this);
		}
	}

}
