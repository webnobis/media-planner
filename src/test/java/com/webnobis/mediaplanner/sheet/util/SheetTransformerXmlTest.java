/**
 * 
 */
package com.webnobis.mediaplanner.sheet.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.webnobis.mediaplanner.element.Describable;
import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Direction;
import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.XY;
import com.webnobis.mediaplanner.sheet.util.ElementList;
import com.webnobis.mediaplanner.sheet.util.Palette;
import com.webnobis.mediaplanner.sheet.util.PaletteUtil;
import com.webnobis.mediaplanner.sheet.util.SheetTransformer;

/**
 * @author steffen
 * @version 1.00
 */
class SheetTransformerXmlTest {

	public static final String TMP_FILE_EXT = ".tmp";

	private static ElementList sElementList;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpClass() throws Exception {
		sElementList = new ElementList();

		AtomicInteger x = new AtomicInteger();
		AtomicInteger y = new AtomicInteger();
		EnumSet.allOf(Palette.class).stream().map(PaletteUtil::getElements).flatMap(List::stream).map(element -> {
			element.getPositions().add(new XY(x.getAndUpdate(i -> i + 2), y.getAndUpdate(i -> i + 3)));
			element.getPositions().add(new XY(x.getAndUpdate(i -> i + 2), y.getAndUpdate(i -> i + 4)));
			if (element.isLine()) {
				element.getPositions().add(new XY(x.getAndUpdate(i -> i + 2), y.getAndUpdate(i -> i + 5)));
			}
			if (element.isTwistable()) {
				element.setDirection(Direction.SOUTH);
			}
			if (element.isDescribable()) {
				Describable d = element.getClass().getAnnotation(Describable.class);
				for (String key : d.allowedKeys()) {
					element.getDescriptions().add(new Description(key, UUID.randomUUID().toString()));
				}
			}
			return element;
		}).forEach(sElementList::add);
	}

	@AfterAll
	static void tearDownClass() throws Exception {
		for (File file : File.createTempFile(SheetTransformerXmlTest.class.getSimpleName(), TMP_FILE_EXT)
				.getParentFile().listFiles(new TestFileFilter())) {
			file.delete();
		}
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.sheet.util.SheetTransformer#storeElements(com.webnobis.mediaplanner.sheet.util.ElementList, java.io.File)}.
	 */
	@Test
	void testStoreElements() {
		try {
			File sXmlFile = File.createTempFile(SheetTransformerXmlTest.class.getSimpleName(), TMP_FILE_EXT);
			sXmlFile = SheetTransformer.storeElements(new TestComponent(), sElementList, sXmlFile);
			assertTrue(sXmlFile.length() > 0);
			testLoadElements(sXmlFile);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.sheet.util.SheetTransformer#loadElements(java.io.File)}.
	 * 
	 * @throws IOException
	 * @throws NullPointerException
	 */
	private void testLoadElements(File pXmlFile) throws NullPointerException, IOException {
		ElementList elementList = new ElementList();
		for (Element element : SheetTransformer.loadElements(pXmlFile).getElements()) {
			elementList.add(element);
		}
		assertEquals(sElementList, elementList);
	}

	@Test
	void testLoadElementsNull() {
		assertThrows(NullPointerException.class, () -> SheetTransformer.loadElements(null));
	}

	@Test
	void testStoreElementsComponentNull() {
		assertThrows(NullPointerException.class,
				() -> SheetTransformer.storeElements(null, new ArrayList<Element>(), new File("")));
	}

	@Test
	void testStoreElementsElementsNull() {
		assertThrows(NullPointerException.class,
				() -> SheetTransformer.storeElements(new TestComponent(), null, new File("")));
	}

	@Test
	void testStoreElementsFileNull() throws NullPointerException, IOException {
		assertThrows(NullPointerException.class,
				() -> SheetTransformer.storeElements(new TestComponent(), new ArrayList<Element>(), null));
	}

	@SuppressWarnings("serial")
	private static class TestComponent extends Component {

		/**
		 * @see java.awt.Component#getPreferredSize()
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(300, 400);
		}

	}

	private static class TestFileFilter implements FileFilter {

		@Override
		public boolean accept(File pFile) {
			return pFile.isFile() && pFile.getName().startsWith(SheetTransformerXmlTest.class.getSimpleName());
		}

	}

}
