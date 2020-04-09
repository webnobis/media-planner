/**
 * 
 */
package test.com.webnobis.mediaplanner.sheet.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private static final ElementList[] sElementLists = new ElementList[10];

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpClass() throws Exception {
		int x = 0;
		int y = 0;
		Element element;
		List<Element> elements;
		for (int i = 0; i < sElementLists.length; i++) {
			sElementLists[i] = new ElementList();
			elements = PaletteUtil.getElements(Palette.POWER);
			while (i >= elements.size()) {
				elements.addAll(PaletteUtil.getElements(Palette.POWER));
			}
			x = (i * 1000);
			y = x;
			for (int j = 0; j < i; j++) {
				x += 7;
				y += 7;
				element = elements.get(j);
				element.getPositions().add(new XY(x, y));
				element.getPositions().add(new XY(x + 2, y + 2));
				if (element.isLine()) {
					element.getPositions().add(new XY(x + 4, y + 4));
				}
				if (element.isTwistable()) {
					element.setDirection(Direction.values()[i % Direction.values().length]);
				}
				if (element.isDescribable()) {
					Describable d = element.getClass().getAnnotation(Describable.class);
					for (String key : d.allowedKeys()) {
						element.getDescriptions().add(new Description(key, String.valueOf(d.maxCount())));
					}
				}
				assertTrue(sElementLists[i].add(element));
			}
		}
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
		Arrays.stream(sElementLists).forEach(pElementList -> {
			try {
				File sXmlFile = File.createTempFile(SheetTransformerXmlTest.class.getSimpleName(), TMP_FILE_EXT);
				sXmlFile = SheetTransformer.storeElements(new TestComponent(), pElementList, sXmlFile);
				assertTrue(sXmlFile.length() > 0);
				testLoadElements(sXmlFile);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
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
		boolean found = false;
		for (int i = 0; i < sElementLists.length; i++) {
			if (sElementLists[i].equals(elementList)) {
				found = true;
				break;
			}
		}
		assertTrue(found);
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
