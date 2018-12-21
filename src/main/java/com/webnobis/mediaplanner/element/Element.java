package com.webnobis.mediaplanner.element;

import java.awt.Container;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Each element, to use at sheets.
 * 
 * @author steffen
 * @version 1.00
 */
public interface Element {

	/**
	 * 
	 * @return true, if it's a line
	 */
	public boolean isLine();

	/**
	 * 
	 * @return true, if it's describable
	 */
	public boolean isDescribable();

	/**
	 * 
	 * @return true, if it's twistable
	 */
	public boolean isTwistable();

	/**
	 * 
	 * @return true, if it's readonly
	 */
	public boolean isReadonly();

	/**
	 * 
	 * @return true, if it's a line end or sheet change
	 */
	public boolean isBreak();

	/**
	 * 
	 * @return elements name
	 */
	public String getName();

	/**
	 * A line has 1..n positions, other elements 2.
	 * 
	 * @return the positions
	 */
	public List<XY> getPositions();

	/**
	 * It's empty without descriptions.<br>
	 * It's null, if describable is false.
	 * 
	 * @return the descriptions
	 */
	public List<Description> getDescriptions();

	/**
	 * It's empty without allowed descriptions.<br>
	 * It's null, if describable is false.
	 * 
	 * @return the allowed descriptions
	 */
	public List<String> getAllowedDescriptions();

	/**
	 * 
	 * @return the maximum count of descriptions
	 */
	public int getDescriptionMaxCount();

	/**
	 * It's null, if twistable is false.
	 * 
	 * @return the direction
	 */
	public Direction getDirection();

	/**
	 * It's empty without allowed directions.<br>
	 * It's null, if twistable is false.
	 * 
	 * @return the allowed directions
	 */
	public List<Direction> getAllowedDirections();

	/**
	 * 
	 * @param pDirection
	 *            the direction
	 */
	public void setDirection(Direction pDirection);

	/**
	 * Paints the underlying element.
	 * 
	 * @param pGraphics
	 *            the graphics of the sheet
	 * @param pParent
	 *            the sheets parent component
	 */
	public void paintElement(Graphics2D pGraphics, Container pParent);

}
