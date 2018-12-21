package com.webnobis.mediaplanner.sheet;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.ElementList;
import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.RasterPainter;

public class SheetPanel extends JPanel implements SheetService {

	private static final long serialVersionUID = 1L;

	private static final Logger sLog = Logger.getLogger(SheetPanel.class);

	private final ElementList mSheetElements;

	private volatile boolean mRaster;

	/**
	 * Constructor
	 * 
	 * @param pCopyAndPasteListener
	 */
	public SheetPanel(ElementCopyAndPasteListener pCopyAndPasteListener) {
		super(null);
		mSheetElements = new ElementList();
		this.setBackground(Constants.BACKGROUND_COLOR);
		this.setFont(Constants.TEXT_FONT);
		super.addMouseListener(pCopyAndPasteListener);
		super.addMouseMotionListener(pCopyAndPasteListener);
		super.addMouseListener(new ElementPopUpMenuListener());
	}

	public void loadElements(Iterable<Element> pElements) {
		if (pElements != null) {
			boolean notAllLoaded = false;
			CharArrayWriter errWriter = new CharArrayWriter();
			PrintWriter errPrintWriter = new PrintWriter(errWriter);
			for (Element e : pElements) {
				if (mSheetElements.add(e)) {
					continue;
				}
				if (!notAllLoaded) {
					notAllLoaded = true;
					errPrintWriter.println(Msg.getText("msg.error.element.load"));
				}
				errPrintWriter.println(e);
			}
			errPrintWriter.flush();
			errPrintWriter.close();
			if (notAllLoaded) {
				sLog.error(errWriter.toString());
				JOptionPane.showMessageDialog(this, errWriter.toString(), Msg.getText("msg.error.title"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public Iterable<Element> getElements() {
		return mSheetElements;
	}

	public boolean addElement(Element pElement) {
		return mSheetElements.add(pElement);
	}

	public Element removeElement(int pX, int pY) {
		return mSheetElements.remove(pX, pY);
	}

	public Element getElement(int pX, int pY) {
		return mSheetElements.get(pX, pY);
	}

	/**
	 * @see com.webnobis.mediaplanner.sheet.SheetService#removeElement(com.webnobis.mediaplanner.element.Element)
	 */
	@Override
	public void removeElement(Element pElement) {
		mSheetElements.remove(pElement);
	}

	/**
	 * @see com.webnobis.mediaplanner.sheet.SheetService#removeLastLinePart(com.webnobis.mediaplanner.element.Element)
	 */
	@Override
	public void removeLastLinePart(Element pElement) {
		if (pElement.isLine() && (pElement.getPositions().size() > 1)) {
			pElement.getPositions().remove(pElement.getPositions().size() - 1);
		}
	}

	/**
	 * @see com.webnobis.mediaplanner.sheet.SheetService#setRaster(boolean)
	 */
	@Override
	public void setRaster(boolean pVisible) {
		mRaster = pVisible;
	}

	/**
	 * @return the raster
	 */
	public boolean isRaster() {
		return mRaster;
	}

	/**
	 * @see com.webnobis.mediaplanner.sheet.SheetService#twistElement(com.webnobis.mediaplanner.element.Element)
	 */
	@Override
	public void twistElement(Element pElement) {
		if (pElement.isTwistable()) {
			int nextIndex = Math.max(0, pElement.getAllowedDirections().indexOf(pElement.getDirection()) + 1);
			if (nextIndex >= pElement.getAllowedDirections().size()) {
				nextIndex = 0;
			}
			pElement.setDirection(pElement.getAllowedDirections().get(nextIndex));
		}
	}

	/**
	 * @see com.webnobis.mediaplanner.sheet.SheetService#updateElement(com.webnobis.mediaplanner.element.Element)
	 */
	@Override
	public void updateElement(Element pElement) {
		if (pElement.isDescribable()) {
			DescriptionUpdatePanel descriptionUpdatePanel = new DescriptionUpdatePanel(pElement);
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this.getParent(), descriptionUpdatePanel, Msg.getText("msg.question.title"), JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE)) {
				descriptionUpdatePanel.updateDescriptions();
			}
		}
	}

	/**
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics pGraphics) {
		super.paint(pGraphics);

		Graphics2D graphics = (Graphics2D) pGraphics;
		if (mRaster) {
			Dimension size = super.getMaximumSize();
			RasterPainter.paint(graphics, new Rectangle(0, 0, size.width, size.height));
		}

		for (Element e : mSheetElements) {
			if (e.isLine()) {
				e.paintElement(graphics, this);
			}
		}
		for (Element e : mSheetElements) {
			if (!e.isLine()) {
				e.paintElement(graphics, this);
			}
		}
	}
}
