package com.webnobis.mediaplanner.element.util;

import org.apache.log4j.Logger;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.ElementException;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.element.palette.Break;
import com.webnobis.mediaplanner.sheet.util.Constants;

public class PaintUtil {

	private static final Logger sLog = Logger.getLogger(PaintUtil.class);

	private static enum Parameter {
		X, Y, WIDTH, HEIGHT, X_CENTER, Y_CENTER
	};

	private static int get(Element pElement, Parameter pParameter) {
		if (isPaintable(pElement)) {
			XY start = pElement.getPositions().get(0);
			switch (pParameter) {
			case X:
				return start.getX();
			case Y:
				return start.getY();
			default:
				// next choose
			}
			if (pElement.getPositions().size() > 1) {
				XY end = pElement.getPositions().get(1);
				switch (pParameter) {
				case WIDTH:
					return Math.abs(end.getX() - start.getX());
				case HEIGHT:
					return Math.abs(end.getY() - start.getY());
				case X_CENTER:
					return Math.min(start.getX(), end.getX()) + (Math.abs(end.getX() - start.getX()) / 2);
				case Y_CENTER:
					return Math.min(start.getY(), end.getY()) + (Math.abs(end.getY() - start.getY()) / 2);
				default:
					// nothing to choose
				}
			}
		}
		return 0;
	}

	public static boolean isPaintable(Element pElement) {
		return (pElement != null) && ((pElement.isLine() && !pElement.getPositions().isEmpty()) || (pElement.getPositions().size() > 1));
	}

	public static int getX(Element pElement) {
		return get(pElement, Parameter.X);
	}

	public static int getY(Element pElement) {
		return get(pElement, Parameter.Y);
	}

	public static int getWidth(Element pElement) {
		return get(pElement, Parameter.WIDTH);
	}

	public static int getHeight(Element pElement) {
		return get(pElement, Parameter.HEIGHT);
	}

	public static int getCenterX(Element pElement) {
		return get(pElement, Parameter.X_CENTER);
	}

	public static int getCenterY(Element pElement) {
		return get(pElement, Parameter.Y_CENTER);
	}

	public static int getRastered(int pInt) {
		return (pInt / Constants.RASTER) * Constants.RASTER;
	}

	public static Element createFrom(Element pElement, int pX, int pY, boolean pRastered) throws ElementException {
		if (isPaintable(pElement)) {
			try {
				Element element = pElement.getClass().newInstance();
				int x;
				int y;
				if (pRastered) {
					x = getRastered(pX);
					y = getRastered(pY);
				} else {
					x = pX;
					y = pY;
				}
				if (element.isLine()) {
					element.getPositions().add(new XY(x, y)); // the first point
				} else {
					int width = getWidth(pElement);
					int height = getHeight(pElement);
					XY xy1 = new XY(Math.max(0, x - (width / 2)), Math.max(0, y - (height / 2)));
					element.getPositions().add(xy1);
					XY xy2 = new XY(xy1.getX() + width, xy1.getY() + height);
					element.getPositions().add(xy2);
				}
				return element;
			} catch (Exception e) {
				sLog.error(e.getMessage(), e);
				throw new ElementException(e.getMessage(), e);
			}
		}
		return null;
	}

	public static boolean found(Element pElement, int pX, int pY) {
		if (isPaintable(pElement)) {
			if (pElement.isLine()) {
				if (pElement.getPositions().size() > 1) {
					for (int i1 = 0, i2 = 1; i2 < pElement.getPositions().size(); i1++, i2++) {
						if (foundLinePart(pElement.getPositions().get(i1), pElement.getPositions().get(i2), pX, pY)) {
							return true;
						}
					}
				} else if (pElement.getPositions().size() > 0) {
					if (foundLinePart(pElement.getPositions().get(0), pElement.getPositions().get(0), pX, pY)) {
						return true;
					}
				}
			} else if (pElement.isBreak()) {
				return foundBreak(pElement, pX, pY);
			} else {
				return foundModule(pElement, pX, pY);
			}
		}
		return false;
	}

	private static boolean foundModule(Element pElement, int pX, int pY) {
		int x1 = getX(pElement);
		int y1 = getY(pElement);
		int x2 = x1 + getWidth(pElement);
		int y2 = y1 + getHeight(pElement);
		return matched(x1, y1, x2, y2, pX, pY);
	}

	private static boolean foundBreak(Element pElement, int pX, int pY) {
		int x1 = getX(pElement) + Break.LINE_LENGTH;
		int y1 = getY(pElement) + Break.LINE_LENGTH;
		int x2 = x1 + getWidth(pElement) - Break.LINE_LENGTH;
		int y2 = y1 + getHeight(pElement) - Break.LINE_LENGTH;
		return matched(x1, y1, x2, y2, pX, pY);
	}

	private static boolean foundLinePart(XY pFrom, XY pTo, int pX, int pY) {
		int x1 = Math.min(pFrom.getX(), pTo.getX()) - Constants.LINE_FOUND_SIZE;
		int y1 = Math.min(pFrom.getY(), pTo.getY()) - Constants.LINE_FOUND_SIZE;
		int x2 = Math.max(pFrom.getX(), pTo.getX()) + Constants.LINE_FOUND_SIZE;
		int y2 = Math.max(pFrom.getY(), pTo.getY()) + Constants.LINE_FOUND_SIZE;
		return matched(x1, y1, x2, y2, pX, pY);
	}

	private static boolean matched(int x1, int y1, int x2, int y2, int pX, int pY) {
		boolean hMatched = (Math.max(pX, x1) == pX) && (Math.min(pX, x2) == pX);
		boolean vMatched = (Math.max(pY, y1) == pY) && (Math.min(pY, y2) == pY);
		if (sLog.isTraceEnabled()) {
			sLog.trace(x1 + "<=" + pX + "<=" + x2 + " = " + hMatched + " and " + y1 + "<=" + pY + "<=" + y2 + " = " + vMatched + ", matched = " + (hMatched & vMatched));
		}
		return hMatched & vMatched;
	}

}
