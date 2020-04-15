package com.webnobis.mediaplanner.element.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
		super(Math.min(10, pMaxCount));
		mMaxCount = pMaxCount;
	}

	/**
	 * @return the maxCount
	 */
	public int getMaxCount() {
		return mMaxCount;
	}

	@Override
	public boolean add(XY element) {
		int size = size();
		add(size, element);
		return size < size();
	}

	@Override
	public void add(int index, XY element) {
		if (element != null && size() < mMaxCount) {
			super.add(index, element);
		}
	}

	@Override
	public boolean addAll(Collection<? extends XY> c) {
		return addAll(size(), c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends XY> c) {
		if (c != null) {
			int limit = mMaxCount - size();
			return super.addAll(index, c.stream().filter(e -> e != null).limit(limit).collect(Collectors.toList()));
		} else {
			return false;
		}
	}

}
