package com.webnobis.mediaplanner.sheet.util;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.XY;

public class XmlSheetElement {

	@XmlElement(name = "elementClass")
	private Class<? extends Element> mElementClass;

	@XmlElement(name = "position")
	@XmlElementWrapper(name = "positions")
	private ArrayList<XmlPosition> mPositions;

	@XmlElement(name = "description")
	@XmlElementWrapper(name = "descriptions")
	private ArrayList<XmlDescription> mDescriptions;

	@XmlElement(name = "direction")
	private Direction mDirection;

	/**
	 * Constructor only for JAXB
	 * 
	 */
	XmlSheetElement() {
	}

	/**
	 * Constructor
	 * 
	 * @param pElementClass
	 * @param pPositions
	 * @param pDescriptions
	 * @param pDirection
	 */
	public XmlSheetElement(Element pElement) {
		mElementClass = pElement.getClass();
		mPositions = new ArrayList<XmlPosition>();
		int id = 0;
		for (XY xy : pElement.getPositions()) {
			mPositions.add(new XmlPosition(id++, xy.getX(), xy.getY()));
		}
		if (pElement.isDescribable()) {
			mDescriptions = new ArrayList<XmlDescription>();
			id = 0;
			for (Description description : pElement.getDescriptions()) {
				mDescriptions.add(new XmlDescription(id++, description.getKey(), description.getValue()));
			}
		} else {
			mDescriptions = null;
		}
		if (pElement.isTwistable()) {
			mDirection = pElement.getDirection();
		} else {
			mDirection = null;
		}
	}

	/**
	 * @return the elementClass
	 */
	public Class<? extends Element> getElementClass() {
		return mElementClass;
	}

	/**
	 * @return the positions
	 */
	public ArrayList<XmlPosition> getPositions() {
		return mPositions;
	}

	/**
	 * @return the descriptions
	 */
	public ArrayList<XmlDescription> getDescriptions() {
		return mDescriptions;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return mDirection;
	}

}
