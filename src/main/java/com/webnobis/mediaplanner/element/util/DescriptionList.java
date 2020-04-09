package com.webnobis.mediaplanner.element.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		super(pMaxCount);
		mMaxCount = pMaxCount;
		mAllowedKeys = Optional.ofNullable(pAllowedKeys).map(Arrays::asList).orElse(Collections.emptyList());
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
		int size = size();
		add(size, pDescription);
		return size < size();
	}

	/**
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int pIndex, Description pDescription) {
		if (Optional.ofNullable(pDescription).filter(unused -> size() < mMaxCount).map(Description::getKey)
				.filter(mAllowedKeys::contains).isPresent()) {
			super.add(pIndex, pDescription);
		}
	}

	/**
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Description> pCol) {
		return addAll(size(), pCol);
	}

	/**
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int pIndex, Collection<? extends Description> pCol) {
		if (pCol != null) {
			int limit = mMaxCount - size();
			return super.addAll(pIndex, pCol.stream().filter(e -> e != null)
					.filter(e -> mAllowedKeys.contains(e.getKey())).limit(limit).collect(Collectors.toList()));
		} else {
			return false;
		}
	}

}
