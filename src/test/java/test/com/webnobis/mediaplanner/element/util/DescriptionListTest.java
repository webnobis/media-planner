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

import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.util.DescriptionList;

/**
 * @author steffen
 * @version 1.00
 */
@RunWith(Theories.class)
public class DescriptionListTest {

	private static final String[] KEYS = { "-8", String.valueOf(Integer.MIN_VALUE), "9", "key", null };

	private static final Description FIRST = new Description("-8", "?");

	private static final Description NOT_ALLOWED = new Description("0", "has a not allowed key");

	@DataPoints
	public static final Description[] sElements = { new Description(String.valueOf(Integer.MIN_VALUE), "wßi"), new Description("9", null), new Description("key", "value"), new Description(null, ""),
			new Description(null, null) };

	private DescriptionList mList;

	private DescriptionList mListAllKeys;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mList = new DescriptionList(sElements.length, KEYS);
		mListAllKeys = new DescriptionList(sElements.length, new String[0]);
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#getMaxCount()}.
	 */
	@Test
	public void testGetMaxCount() {
		assertEquals(sElements.length, mList.getMaxCount());
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#getAllowedKeys()}.
	 */
	@Test
	public void testGetAllowedKeys() {
		assertEquals(KEYS.length, mList.getAllowedKeys().size());
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(Description)}.
	 */
	@Theory
	public void testAddEndDescription(Description pDescription) {
		assertTrue(mList.add(FIRST));
		assertTrue(mList.add(pDescription));
		assertEquals(2, mList.size());
		assertEquals(FIRST, mList.get(0));
		assertEquals(pDescription, mList.get(1));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(int, Description)}.
	 */
	@Theory
	public void testAddStartDescription(Description pDescription) {
		mList.add(0, FIRST);
		mList.add(0, pDescription);
		assertEquals(2, mList.size());
		assertEquals(FIRST, mList.get(1));
		assertEquals(pDescription, mList.get(0));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#addAll(java.util.Collection)}.
	 */
	@Test
	public void testAddAllEndDescription() {
		assertTrue(mList.addAll(Arrays.asList(sElements)));
		assertEquals(mList.getMaxCount(), mList.size());
		assertFalse(mList.add(FIRST));
		assertFalse(mList.contains(FIRST));
		assertEquals(mList.getMaxCount(), mList.size());
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#addAll(int, java.util.Collection)}.
	 */
	@Test
	public void testAddAllStartDescription() {
		assertTrue(mList.add(FIRST));
		assertTrue(mList.contains(FIRST));
		assertFalse(mList.addAll(0, Arrays.asList(sElements)));
		assertEquals(mList.getMaxCount(), mList.size());
		mList.clear();
		assertTrue(mList.add(FIRST));
		assertTrue(mList.contains(FIRST));
		Description[] part = new Description[sElements.length - 1];
		System.arraycopy(sElements, 0, part, 0, part.length);
		assertTrue(mList.addAll(0, Arrays.asList(part)));
		assertEquals(mList.getMaxCount(), mList.size());
		assertFalse(mList.add(FIRST));
		assertTrue(mList.contains(FIRST));
		assertEquals(mList.getMaxCount(), mList.size());
		assertEquals(FIRST, mList.get(mList.size() - 1));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#set(int, Description)}.
	 */
	@Theory
	public void testSetDescription(Description pDescription) {
		for (int i = 0; i < sElements.length; i++) {
			mList.add(FIRST);
		}
		assertEquals(FIRST, mList.set(2, pDescription));
		assertEquals(sElements.length, mList.size());
		assertEquals(pDescription, mList.get(2));
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(Description) and {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(int, Description)}.
	 */
	@Test
	public void testAddNull() {
		assertFalse(mList.add(null));
		mList.add(0, null);
		assertTrue(mList.isEmpty());
		assertFalse(mListAllKeys.add(null));
		mListAllKeys.add(0, null);
		assertTrue(mListAllKeys.isEmpty());
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(Description) and {@link com.webnobis.mediaplanner.element.util.DescriptionList#add(int, Description)}.
	 */
	@Test
	public void testAddNotAllowed() {
		assertFalse(mList.add(NOT_ALLOWED));
		assertTrue(mList.isEmpty());
		assertTrue(mListAllKeys.add(NOT_ALLOWED));
		assertTrue(mListAllKeys.contains(NOT_ALLOWED));
	}

}
