/**
 * 
 */
package com.webnobis.mediaplanner.element.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.mediaplanner.element.XY;

/**
 * @author steffen
 * @version 1.00
 */
class PositionListTest {

	private static final XY[] sElements = { new XY(0, 0), new XY(9, -88), new XY(Integer.MIN_VALUE, Integer.MAX_VALUE),
			new XY(-1111, -1111111111) };

	private PositionList mList;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mList = new PositionList(sElements.length);
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#getMaxCount()}.
	 */
	@Test
	void testGetMaxCount() {
		assertEquals(sElements.length, mList.getMaxCount());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#add(XY)}.
	 */
	@Test
	void testAddEndXY() {
		assertTrue(mList.add(sElements[2]));
		assertTrue(mList.add(sElements[0]));
		assertEquals(2, mList.size());
		assertEquals(sElements[2], mList.get(0));
		assertEquals(sElements[0], mList.get(1));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#add(int, XY)}.
	 */
	@Test
	void testAddStartXY() {
		mList.add(0, sElements[3]);
		mList.add(0, sElements[1]);
		assertEquals(2, mList.size());
		assertEquals(sElements[1], mList.get(0));
		assertEquals(sElements[3], mList.get(1));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#addAll(java.util.Collection)}.
	 */
	@Test
	void testAddAllEndXY() {
		assertTrue(mList.addAll(Arrays.asList(sElements)));
		assertEquals(mList.getMaxCount(), mList.size());
		assertTrue(mList.contains(sElements[0]));
		assertFalse(mList.add(sElements[0]));
		assertEquals(mList.getMaxCount(), mList.size());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#addAll(int, java.util.Collection)}.
	 */
	@Test
	void testAddAllStartXY() {
		assertTrue(mList.add(sElements[1]));
		assertTrue(mList.contains(sElements[1]));
		assertTrue(mList.addAll(0, Arrays.asList(sElements)));
		assertEquals(mList.getMaxCount(), mList.size());
		assertTrue(mList.contains(sElements[1]));
		assertEquals(sElements[1], mList.get(mList.size() - 1));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#set(int, XY)}.
	 */
	@Test
	void testSetXY() {
		mList.addAll(Arrays.asList(sElements));
		assertEquals(sElements[2], mList.set(2, sElements[0]));
		assertEquals(sElements.length, mList.size());
		assertEquals(sElements[0], mList.get(2));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#add(XY)} and
	 * {@link com.webnobis.mediaplanner.element.util.PositionList#add(int, XY)}.
	 */
	@Test
	void testAddNull() {
		assertFalse(mList.add(null));
		mList.add(0, null);
		assertTrue(mList.isEmpty());
	}

}
