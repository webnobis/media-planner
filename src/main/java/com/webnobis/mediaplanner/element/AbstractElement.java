/**
 * 
 */
package com.webnobis.mediaplanner.element;

import java.awt.Container;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;

import com.webnobis.mediaplanner.element.palette.Break;
import com.webnobis.mediaplanner.element.util.DescriptionList;
import com.webnobis.mediaplanner.element.util.PositionList;
import com.webnobis.mediaplanner.sheet.Readonly;
import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.Msg;

/**
 * @author steffen
 * @version 1.00
 */
public abstract class AbstractElement implements Element {

	/**
	 * If true, the element is a line, otherwise it's a module.
	 */
	private final boolean mLine;

	/**
	 * If true, the element is a break module.
	 */
	private final boolean mBreak;

	/**
	 * Contains all positions. Only 2 positions are allowed for modules.
	 */
	private final List<XY> mPositions;

	/**
	 * Contains all descriptions, if it's a describable element, otherwise it's null.
	 */
	private final DescriptionList mDescriptions;

	/**
	 * Contains all allowed directions, if it's a twistable element, otherwise it's null.
	 */
	private final List<Direction> mAllowedDirections;

	/**
	 * The current direction, if it's a twistable element, otherwise it's null.
	 */
	private Direction mDirection;

	/**
	 * If true, the element is read only.
	 */
	private boolean mReadonly;

	/**
	 * Constructor, which's set all attributes.<br>
	 * Sets line=true, if the annotation Line is found.<br>
	 * Sets break=true, if it's no line and a break.<br>
	 * Initializes the positions.<br>
	 * Initializes the descriptions.<br>
	 */
	protected AbstractElement() {
		Class<?> c = this.getClass();
		mLine = c.isAnnotationPresent(Line.class);
		mBreak = !mLine && Break.class.isAssignableFrom(this.getClass());
		if (mLine) {
			mPositions = new PositionList(Integer.MAX_VALUE);
		} else {
			mPositions = new PositionList(2); // a module
		}
		if (c.isAnnotationPresent(Describable.class)) {
			Describable d = c.getAnnotation(Describable.class);
			mDescriptions = new DescriptionList(d.maxCount(), d.allowedKeys());
			mDescriptions.addAll(getDefaultDescriptions(d.allowedKeys())); // set defaults
		} else {
			mDescriptions = null;
		}
		if (c.isAnnotationPresent(Twistable.class)) {
			Twistable t = c.getAnnotation(Twistable.class);
			mAllowedDirections = Arrays.asList(t.allowedDirections());
			this.setDirection(t.defaultDirection());
		} else {
			mAllowedDirections = null;
		}
	}

	protected List<Description> getDefaultDescriptions(String[] pKeys) {
		List<Description> descriptions = new ArrayList<Description>();
		if (isDescribable()) {
			for (String key : pKeys) {
				try {
					String value = Msg.getText("element." + this.getClass().getSimpleName().toLowerCase() + ".default." + key);
					descriptions.add(new Description(key, value));
				} catch (MissingResourceException e) {
					continue; // no default found
				}
			}
		}
		return descriptions;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getDescriptions()
	 */
	@Override
	public List<Description> getDescriptions() {
		return mDescriptions;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getAllowedDescriptions()
	 */
	@Override
	public List<String> getAllowedDescriptions() {
		if (mDescriptions == null) {
			return null;
		} else {
			return mDescriptions.getAllowedKeys();
		}
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getDescriptionMaxCount()
	 */
	@Override
	public int getDescriptionMaxCount() {
		if (mDescriptions == null) {
			return -1;
		} else {
			return mDescriptions.getMaxCount();
		}
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getDirection()
	 */
	@Override
	public Direction getDirection() {
		return mDirection;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getAllowedDirections()
	 */
	@Override
	public List<Direction> getAllowedDirections() {
		return mAllowedDirections;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#setDirection(com.webnobis.mediaplanner.element.Direction)
	 */
	@Override
	public void setDirection(Direction pDirection) {
		if (pDirection != null && mAllowedDirections != null && mAllowedDirections.contains(pDirection)) {
			mDirection = pDirection;
		}
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getName()
	 */
	@Override
	public String getName() {
		return Msg.getText("element." + this.getClass().getSimpleName().toLowerCase() + ".name");
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#getPositions()
	 */
	@Override
	public List<XY> getPositions() {
		return mPositions;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#isDescribable()
	 */
	@Override
	public boolean isDescribable() {
		return mDescriptions != null;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#isLine()
	 */
	@Override
	public boolean isLine() {
		return mLine;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#isReadonly()
	 */
	@Override
	public boolean isReadonly() {
		return mReadonly;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#isBreak()
	 */
	@Override
	public boolean isBreak() {
		return mBreak;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#isTwistable()
	 */
	@Override
	public boolean isTwistable() {
		return mAllowedDirections != null;
	}

	/**
	 * @see com.webnobis.mediaplanner.element.Element#paintElement(Graphics2D, Container)
	 */
	@Override
	public void paintElement(Graphics2D pGraphics, Container pParent) {
		mReadonly = (pParent != null) && pParent.getClass().isAnnotationPresent(Readonly.class);
		pGraphics.setColor(Constants.BACKGROUND_COLOR);
		this.clearPaintElement(pGraphics);
		pGraphics.setColor(Constants.FOREGROUND_COLOR);
		this.paintElement(pGraphics);
	}

	protected void clearPaintElement(Graphics2D pGraphics) {
	}

	protected abstract void paintElement(Graphics2D pGraphics);

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pObj) {
		if (pObj == null) {
			return false;
		} else if (pObj == this) {
			return true;
		} else if (this.getClass().equals(pObj.getClass())) {
			Element other = (Element) pObj;
			if (this.getClass().equals(other.getClass()))
				if (this.isDescribable() != other.isDescribable()) {
					return false;
				}
			if (this.isLine() != other.isLine()) {
				return false;
			}
			if (this.isReadonly() != other.isReadonly()) {
				return false;
			}
			if (this.isTwistable() != other.isTwistable()) {
				return false;
			}
			if ((this.getDescriptions() == null) != (other.getDescriptions() == null)) {
				return false;
			}
			if ((this.getDirection() == null) != (other.getDirection() == null)) {
				return false;
			}
			if (this.getPositions().size() != other.getPositions().size()) {
				return false;
			}
			int count = 0;
			for (XY xy : other.getPositions()) {
				if (this.getPositions().contains(xy)) {
					count++;
				}
			}
			if (count != this.getPositions().size()) {
				return false;
			}
			if (this.getDirection() != null) {
				if (!this.getDirection().equals(other.getDirection())) {
					return false;
				}
			}
			if ((this.getDescriptions() != null) && (other.getDescriptions() != null)) {
				count = 0;
				for (Description description : other.getDescriptions()) {
					if (this.getDescriptions().contains(description)) {
						count++;
					}
				}
				if (count != this.getDescriptions().size()) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = this.getPositions().size();
		for (XY xy : this.getPositions()) {
			hash ^= xy.hashCode();
		}
		if (this.getDirection() != null) {
			hash ^= this.getDirection().hashCode();
		}
		if (this.getDescriptions() != null) {
			for (Description description : this.getDescriptions()) {
				hash ^= description.hashCode();
			}
		}
		return hash;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (mPositions.isEmpty()) {
			return getName();
		} else {
			return getName() + '[' + mPositions.get(0) + ']';
		}
	}

}
