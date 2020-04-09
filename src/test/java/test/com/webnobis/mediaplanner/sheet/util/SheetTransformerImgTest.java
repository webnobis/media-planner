/**
 * 
 */
package test.com.webnobis.mediaplanner.sheet.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.SheetTransformer;

/**
 * @author steffen
 * @version 1.00
 */
class SheetTransformerImgTest {

	private static final List<Path> sImgFiles = new ArrayList<>(SheetTransformer.IMG_FILE_EXTS.length);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpClass() throws Exception {
		for (int i = 0; i < SheetTransformer.IMG_FILE_EXTS.length; i++) {
			sImgFiles.add(0, Files.createTempFile(SheetTransformerImgTest.class.getSimpleName(),
					'.' + SheetTransformer.IMG_FILE_EXTS[i]));
			assertTrue(Files.exists(sImgFiles.get(0)));
		}
	}

	@AfterAll
	static void tearDownClass() throws Exception {
		sImgFiles.forEach(file -> {
			try {
				Files.delete(file);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}

	/**
	 * Test method for
	 * {@link com.webnobis.mediaplanner.sheet.util.SheetTransformer#exportSheetAsImage(Component, File)}.
	 */
	@Test
	void testExportSheetAsImage() {
		sImgFiles.stream().map(Path::toFile).forEach(file -> {
			Component c = new TestComponent();
			Dimension d = c.getPreferredSize();
			try {
				SheetTransformer.exportSheetAsImage(c, file);
				BufferedImage imgIn = ImageIO.read(file);
				d = new Dimension((int) (d.width * Constants.A4_SCALE), (int) (d.height * Constants.A4_SCALE));
				assertEquals(d.width, imgIn.getWidth());
				assertEquals(d.height, imgIn.getHeight());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
	}

	@Test
	void testExportSheetAsImageComponentNull() {
		assertThrows(NullPointerException.class, () -> SheetTransformer.exportSheetAsImage(null, new File("")));
	}

	@Test
	void testExportSheetAsImageFileNull() {
		assertThrows(NullPointerException.class, () -> SheetTransformer.exportSheetAsImage(new TestComponent(), null));
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

		/**
		 * @see java.awt.Component#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics pGraphics) {
			super.paint(pGraphics);
			pGraphics.fillRoundRect(20, 40, 200, 150, 5, 10);
		}

		/**
		 * @see java.awt.Component#getGraphics()
		 */
		@Override
		public Graphics getGraphics() {
			return (new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY)).createGraphics();
		}

	}

}
