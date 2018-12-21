package com.webnobis.mediaplanner.element.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.webnobis.mediaplanner.element.Description;

public class DescriptionList extends ArrayList<Description> {

	private static final long serialVersionUID = 1L;

	private final int mMaxCount;

	private final List<String> mAllowedKeys;

	/**
	 * Constructor
	 * 
	 * @param pMaxCount
	 * @param pAllowedKeys
	 */
	public DescriptionList(int pMaxCount, String[] pAllowedKeys) {
		super();
		mMaxCount = pMaxCount;
		if (pAllowedKeys == null || pAllowedKeys.length < 1) {
			mAllowedKeys = null;
		} else {
			mAllowedKeys = Arrays.asList(pAllowedKeys);
		}
	}

	/**
	 * @return the maxCount
	 */
	public int getMaxCount() {
		return mMaxCount;
	}

	/**
	 * @return the allowedKeys
	 */
	public List<String> getAllowedKeys() {
		return mAllowedKeys;
	}

	/**
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(Description pDescription) {
		return this.addAll(Arrays.asList(new Description[] { pDescription }));
	}

	/**
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int pIndex, Description pDescription) {
		this.addAll(pIndex, Arrays.asList(new Description[] { pDescription }));
	}

	/**
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Description> pCol) {
		return this.addAll(super.size(), pCol);
	}

	/**
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int pIndex, Collection<? extends Description> pCol) {
		boolean added = true;
		int index = pIndex;
		for (Description d : pCol) {
			if (d == null || mMaxCount <= super.size() || (mAllowedKeys != null && !mAllowedKeys.contains(d.getKey()))) {
				added = false;
				break;
			}
			super.add(index++, d);
		}
		return added;
	}

	/**
	 * @see java.util.ArrayList#set(int, java.lang.Object)
	 */
	@Override
	public Description set(int pIndex, Description pDescription) {
		Description d = super.remove(pIndex);
		if (this.addAll(pIndex, Arrays.asList(new Description[] { pDescription }))) {
			return d;
		} else {
			super.set(pIndex, d); // roll back
			return null;
		}
	}

}
