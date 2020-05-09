package com.webnobis.mediaplanner.element.util;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webnobis.mediaplanner.element.AbstractLine;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.XY;

public class LinePainter {

	protected static final String FIRST_SPLIT = "  ";

	protected static final char SECOND_SPLIT = '-';

	protected static final char NEXT_SPLIT = ',';

	protected static final int LINE_DESCRIPTION_SPACE = 3;

	protected static final String LINE_CROSS = "//";

	private static final Logger sLog = LoggerFactory.getLogger(LinePainter.class);

	public static void paint(AbstractLine pLine, Graphics2D pGraphics) {
		if (PaintUtil.isPaintable(pLine) && pLine.isLine()) {
			if (pLine.isReadonly()) {
				int y = PaintUtil.getCenterY(pLine);
				XY xy1 = pLine.getPositions().get(0);
				XY xy2 = pLine.getPositions().get(1);
				pLine.getPositions().set(0, new XY(xy1.getX(), y));
				pLine.getPositions().set(1, new XY(xy2.getX(), y));
			}
			if (pLine.getPositions().size() == 1) {
				paintCross(pLine.getPositions().get(0), pGraphics);
			} else {
				for (int i = 0, j = 1; j < pLine.getPositions().size(); i++, j++) {
					paintLinePart(pGraphics, pLine.getPositions().get(i), pLine.getPositions().get(j));
				}
			}
			if (pLine.isDescribable()) {
				String description = getLineDescription(pLine);
				paintLineDescription(pLine.getPositions(), pGraphics, description, pLine.isReadonly());
			}
		}
	}

	private static void paintLinePart(Graphics2D pGraphics, XY pXY1, XY pXY2) {
		pGraphics.drawLine(pXY1.getX(), pXY1.getY(), pXY2.getX(), pXY2.getY());
	}

	private static String getLineDescription(AbstractLine pLine) {
		StringBuilder sb = new StringBuilder();
		for (Description description : pLine.getDescriptions()) {
			if ((description.getKey() == null) || (description.getValue() == null)) {
				sLog.warn("incorrect description found: " + description);
				continue;
			}
			if (AbstractLine.LINE_NAME_KEY.equals(description.getKey())) {
				sb.insert(0, SECOND_SPLIT).insert(0, description.getValue());
			} else {
				sb.append(description).append(NEXT_SPLIT);
			}
		}
		if (sb.length() < 1) {
			return String.valueOf(pLine.getWireCount());
		} else {
			sb.insert(0, FIRST_SPLIT).insert(0, pLine.getWireCount());
			return sb.substring(0, sb.length() - 1);
		}
	}

	private static int getTextWidth(Graphics2D pGraphics, String pDescription) {
		FontMetrics fm = pGraphics.getFontMetrics();
		return fm.stringWidth(pDescription);
	}

	private static int getTextHeight(Graphics2D pGraphics) {
		FontMetrics fm = pGraphics.getFontMetrics();
		return fm.getHeight();
	}

	private static void paintLineDescription(List<XY> pXYs, Graphics2D pGraphics, String pDescription, boolean pReadonly) {
		int lineCrossWidth = getTextWidth(pGraphics, LINE_CROSS);
		int textWidth = getTextWidth(pGraphics, pDescription);
		int textHeight = getTextHeight(pGraphics);
		XY xy = getFirstLinePartStartPositionForDescription(pXYs, textWidth + LINE_DESCRIPTION_SPACE, textHeight + LINE_DESCRIPTION_SPACE);
		int x = xy.getX();
		int y = xy.getY();
		pGraphics.drawString(LINE_CROSS, x, y);
		x += (LINE_DESCRIPTION_SPACE + lineCrossWidth);
		y -= (LINE_DESCRIPTION_SPACE * 2);
		pGraphics.drawString(pDescription, x, y);
	}

	private static XY getFirstLinePartStartPositionForDescription(List<XY> pXYs, int pTextWidth, int pTextHeight) {
		int w1 = pTextWidth;
		int h1 = pTextHeight;
		int w;
		int h;
		int x = pXYs.get(0).getX();
		int y = pXYs.get(0).getY();
		for (int i = 0, j = 1; j < pXYs.size(); i++, j++) {
			w = Math.abs(pXYs.get(i).getX() - pXYs.get(j).getX());
			h = Math.abs(pXYs.get(i).getY() - pXYs.get(j).getY());
			if (w > w1) {
				w1 = w;
				x = Math.min(pXYs.get(i).getX(), pXYs.get(j).getX()) + LINE_DESCRIPTION_SPACE;
				y = Math.min(pXYs.get(i).getY(), pXYs.get(j).getY()) - (LINE_DESCRIPTION_SPACE * 2) + (pTextHeight / 2);
			} else if ((h > w1) && (h > h1)) {
				h1 = h;
				x = Math.min(pXYs.get(i).getX(), pXYs.get(j).getX()) - LINE_DESCRIPTION_SPACE;
				y = Math.min(pXYs.get(i).getY(), pXYs.get(j).getY()) + (h1 / 2) - LINE_DESCRIPTION_SPACE;
			}
		}
		return new XY(x, y);
	}

	private static void paintCross(XY pXY, Graphics2D pGraphics) {
		int x1 = pXY.getX() - LINE_DESCRIPTION_SPACE;
		int y1 = pXY.getY() - LINE_DESCRIPTION_SPACE;
		int x2 = pXY.getX() + LINE_DESCRIPTION_SPACE;
		int y2 = pXY.getY() + LINE_DESCRIPTION_SPACE;
		pGraphics.drawLine(x1, y1, x2, y2);
		pGraphics.drawLine(x1, y2, x2, y1);
	}

}
