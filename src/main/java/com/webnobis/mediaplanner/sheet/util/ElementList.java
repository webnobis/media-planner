package com.webnobis.mediaplanner.sheet.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.element.util.PaintUtil;

public class ElementList implements Iterable<Element> {

	private final List<Element> mElements = new ArrayList<Element>();

	public boolean isFree(int pX, int pY) {
		Element element = get(pX, pY);
		return (element == null) || element.isLine();
	}

	public boolean add(Element pElement) {
		if (PaintUtil.isPaintable(pElement)) {
			XY xy = pElement.getPositions().get(0);
			if (pElement.isLine() || isFree(xy.getX(), xy.getY())) {
				return mElements.add(pElement); // always, if a line, otherwise only if it's no element at the same position
			}
		}
		return false;
	}

	public Element get(int pX, int pY) {
		Element element;
		// at first the last
		for (int i = (mElements.size() - 1); i > -1; i--) {
			element = mElements.get(i);
			if (PaintUtil.found(element, pX, pY)) {
				return element;
			}
		}
		return null;
	}

	public Element remove(int pX, int pY) {
		Element element = get(pX, pY);
		if (element != null) {
			mElements.remove(element);
		}
		return element;
	}

	public boolean remove(Element pElement) {
		return mElements.remove(pElement);
	}

	@Override
	public Iterator<Element> iterator() {
		return Collections.unmodifiableList(mElements).iterator();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pObj) {
		if (pObj == null) {
			return false;
		} else if (pObj == this) {
			return true;
		} else if (ElementList.class.equals(pObj.getClass())) {
			ElementList other = (ElementList) pObj;
			if (mElements.size() != other.mElements.size()) {
				return false;
			}
			int count = 0;
			for (Element element : other) {
				if (mElements.contains(element)) {
					count++;
				}
			}
			return (count == mElements.size());
		} else {
			return false;
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = mElements.size();
		for (Element element : mElements) {
			hash ^= element.hashCode();
		}
		return hash;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mElements.toString();
	}

}
