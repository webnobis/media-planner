package com.webnobis.mediaplanner.sheet.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.XY;

@XmlRootElement(name = "sheet", namespace = "http://www.webnobis.com/mediaplanner/sheet")
public class XmlSheet {

	@XmlAttribute(name = "width")
	private int mWidth;

	@XmlAttribute(name = "height")
	private int mHeight;

	@XmlElement(name = "element")
	@XmlElementWrapper(name = "elements")
	private ArrayList<XmlSheetElement> mSheetElements = new ArrayList<XmlSheetElement>();

	@XmlElement(name = "lastStored")
	private Calendar mLastStored;

	/**
	 * Constructor, only for JAXB
	 * 
	 */
	XmlSheet() {
	}

	/**
	 * Constructor
	 * 
	 * @param pPalette
	 * @param pWidth
	 * @param pHeight
	 * @param pSheetElements
	 */
	public XmlSheet(int pWidth, int pHeight, Iterable<Element> pElements) {
		mWidth = pWidth;
		mHeight = pHeight;
		for (Element element : pElements) {
			mSheetElements.add(new XmlSheetElement(element));
		}
		mLastStored = Calendar.getInstance();
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return mWidth;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return mHeight;
	}

	/**
	 * @return the sheetElements
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Iterable<Element> getElements() throws InstantiationException, IllegalAccessException {
		Element element;
		List<Element> elements = new ArrayList<Element>(mSheetElements.size());
		for (XmlSheetElement xmlElement : mSheetElements) {
			element = xmlElement.getElementClass().newInstance();
			for (XmlPosition position : xmlElement.getPositions()) {
				element.getPositions().add(Math.min(position.getId(), element.getPositions().size()), new XY(position.getX(), position.getY()));
			}
			if (element.isDescribable()) {
				element.getDescriptions().clear();
				for (XmlDescription description : xmlElement.getDescriptions()) {
					element.getDescriptions().add(Math.min(description.getId(), element.getDescriptions().size()), new Description(description.getKey(), description.getValue()));
				}
			}
			if (element.isTwistable()) {
				element.setDirection(xmlElement.getDirection());
			}
			elements.add(element);
		}
		return elements;
	}

	/**
	 * @return the lastStored
	 */
	public Calendar getLastStored() {
		return mLastStored;
	}

}
