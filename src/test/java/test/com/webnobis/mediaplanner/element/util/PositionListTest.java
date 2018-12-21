/**
 * 
 */
package test.com.webnobis.mediaplanner.element.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.element.util.PositionList;

/**
 * @author steffen
 * @version 1.00
 */
@RunWith(Theories.class)
public class PositionListTest {

	private static final XY FIRST = new XY(7, 7);

	@DataPoints
	public static final XY[] sElements = { new XY(0, 0), new XY(9, -88), new XY(Integer.MIN_VALUE, Integer.MAX_VALUE) };

	private PositionList mList;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mList = new PositionList(sElements.length);
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#getMaxCount()}.
	 */
	@Test
	public void testGetMaxCount() {
		assertEquals(sElements.length, mList.getMaxCount());
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#add(XY)}.
	 */
	@Theory
	public void testAddEndXY(XY pXY) {
		assertTrue(mList.add(FIRST));
		assertTrue(mList.add(pXY));
		assertEquals(2, mList.size());
		assertEquals(FIRST, mList.get(0));
		assertEquals(pXY, mList.get(1));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#add(int, XY)}.
	 */
	@Theory
	public void testAddStartXY(XY pXY) {
		mList.add(0, FIRST);
		mList.add(0, pXY);
		assertEquals(2, mList.size());
		assertEquals(FIRST, mList.get(1));
		assertEquals(pXY, mList.get(0));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#addAll(java.util.Collection)}.
	 */
	@Test
	public void testAddAllEndXY() {
		assertTrue(mList.addAll(Arrays.asList(sElements)));
		assertEquals(mList.getMaxCount(), mList.size());
		assertFalse(mList.add(FIRST));
		assertFalse(mList.contains(FIRST));
		assertEquals(mList.getMaxCount(), mList.size());
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#addAll(int, java.util.Collection)}.
	 */
	@Test
	public void testAddAllStartXY() {
		assertTrue(mList.add(FIRST));
		assertTrue(mList.contains(FIRST));
		assertFalse(mList.addAll(0, Arrays.asList(sElements)));
		assertEquals(mList.getMaxCount(), mList.size());
		mList.clear();
		assertTrue(mList.add(FIRST));
		assertTrue(mList.contains(FIRST));
		XY[] part = new XY[sElements.length - 1];
		System.arraycopy(sElements, 0, part, 0, part.length);
		assertTrue(mList.addAll(0, Arrays.asList(part)));
		assertEquals(mList.getMaxCount(), mList.size());
		assertFalse(mList.add(FIRST));
		assertTrue(mList.contains(FIRST));
		assertEquals(mList.getMaxCount(), mList.size());
		assertEquals(FIRST, mList.get(mList.size() - 1));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#set(int, XY)}.
	 */
	@Theory
	public void testSetXY(XY pXY) {
		for (int i = 0; i < sElements.length; i++) {
			mList.add(FIRST);
		}
		assertEquals(FIRST, mList.set(2, pXY));
		assertEquals(sElements.length, mList.size());
		assertEquals(pXY, mList.get(2));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.PositionList#add(XY)} and {@link com.webnobis.mediaplanner.element.util.PositionList#add(int, XY)}.
	 */
	@Test
	public void testAddNull() {
		assertFalse(mList.add(null));
		mList.add(0, null);
		assertTrue(mList.isEmpty());
	}

}
