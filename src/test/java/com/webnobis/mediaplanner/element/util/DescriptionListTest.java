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

import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.util.DescriptionList;

/**
 * @author steffen
 * @version 1.00
 */
class DescriptionListTest {

	private static final String[] KEYS = { "-8", String.valueOf(Integer.MIN_VALUE), "9", "key", null };

	private static final Description FIRST = new Description("-8", "?");

	private static final Description OTHER = new Description("key", "? ?");

	private static final Description NOT_ALLOWED = new Description("0", "has a not allowed key");

	private DescriptionList mList;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mList = new DescriptionList(KEYS.length, KEYS);
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#getMaxCount()}.
	 */
	@Test
	void testGetMaxCount() {
		assertEquals(KEYS.length, mList.getMaxCount());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#getAllowedKeys()}.
	 */
	@Test
	void testGetAllowedKeys() {
		assertEquals(KEYS.length, mList.getAllowedKeys().size());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(Description)}.
	 */
	@Test
	void testAddEndDescription() {
		assertTrue(mList.add(FIRST));
		assertTrue(mList.add(OTHER));
		assertEquals(2, mList.size());
		assertEquals(FIRST, mList.get(0));
		assertEquals(OTHER, mList.get(1));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(int, Description)}.
	 */
	@Test
	void testAddStartDescription() {
		mList.add(0, FIRST);
		mList.add(0, OTHER);
		assertEquals(2, mList.size());
		assertEquals(FIRST, mList.get(1));
		assertEquals(OTHER, mList.get(0));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#addAll(java.util.Collection)}.
	 */
	@Test
	void testAddAllEndDescription() {
		assertTrue(mList.addAll(Arrays.asList(FIRST, OTHER)));
		assertEquals(2, mList.size());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#addAll(int, java.util.Collection)}.
	 */
	@Test
	void testAddAllStartDescription() {
		assertTrue(mList.addAll(0, Arrays.asList(FIRST, OTHER)));
		assertEquals(2, mList.size());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#set(int, Description)}.
	 */
	@Test
	void testSetDescription() {
		mList.add(OTHER);
		mList.add(OTHER);
		mList.set(1, FIRST);
		assertEquals(FIRST, mList.get(1));
		assertEquals(FIRST, mList.set(1, OTHER));
		assertEquals(OTHER, mList.get(1));
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(Description)
	 * and
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(int, Description)}.
	 */
	@Test
	void testAddNull() {
		assertFalse(mList.add(null));
		mList.add(0, null);
		assertTrue(mList.isEmpty());
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(Description)
	 * and
	 * {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(int, Description)}.
	 */
	@Test
	void testAddNotAllowed() {
		assertFalse(mList.add(NOT_ALLOWED));
		assertTrue(mList.isEmpty());
	}

}
