package com.webnobis.mediaplanner.element.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.webnobis.mediaplanner.element.XY;

public class PositionList extends ArrayList<XY> {

	private static final long serialVersionUID = 1L;

	private final int mMaxCount;

	/**
	 * Constructor
	 * 
	 * @param pMaxCount
	 */
	public PositionList(int pMaxCount) {
		super();
		mMaxCount = pMaxCount;
	}

	/**
	 * @return the maxCount
	 */
	public int getMaxCount() {
		return mMaxCount;
	}

	/**
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(XY pXY) {
		return this.addAll(Arrays.asList(new XY[] { pXY }));
	}

	/**
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int pIndex, XY pXY) {
		this.addAll(pIndex, Arrays.asList(new XY[] { pXY }));
	}

	/**
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends XY> pCol) {
		return this.addAll(super.size(), pCol);
	}

	/**
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int pIndex, Collection<? extends XY> pCol) {
		boolean added = true;
		for (XY xy : pCol) {
			if (xy == null || mMaxCount <= super.size()) {
				added = false;
				break;
			}
			super.add(pIndex, xy);
		}
		return added;
	}

	/**
	 * @see java.util.ArrayList#set(int, java.lang.Object)
	 */
	@Override
	public XY set(int pIndex, XY pXY) {
		XY xy = super.remove(pIndex);
		if (this.addAll(pIndex, Arrays.asList(new XY[] { pXY }))) {
			return xy;
		} else {
			super.set(pIndex, xy); // roll back
			return null;
		}
	}

}
