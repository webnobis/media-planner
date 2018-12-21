/**
 * 
 */
package test.com.webnobis.mediaplanner.sheet.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.webnobis.mediaplanner.sheet.util.Constants;
import com.webnobis.mediaplanner.sheet.util.SheetTransformer;

/**
 * @author steffen
 * @version 1.00
 */
@RunWith(Theories.class)
public class SheetTransformerImgTest {

	@DataPoints
	public static final File[] sImgFiles = new File[SheetTransformer.IMG_FILE_EXTS.length];

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		for (int i = 0; i < sImgFiles.length; i++) {
			sImgFiles[i] = File.createTempFile(SheetTransformerImgTest.class.getSimpleName(), '.' + SheetTransformer.IMG_FILE_EXTS[i]);
			assertTrue(sImgFiles[i].exists());
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (File file : sImgFiles[0].getParentFile().listFiles(new TestFileFilter())) {
			file.delete();
		}
	}

	/**
	 * Test method for {@link com.webnobis.mediaplanner.sheet.util.SheetTransformer#exportSheetAsImage(Component, File)}.
	 * 
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws InterruptedException
	 */
	@Theory
	public void testExportSheetAsImage(File pImgFile) throws NullPointerException, IOException, InterruptedException {
		Component c = new TestComponent();
		Dimension d = c.getPreferredSize();
		SheetTransformer.exportSheetAsImage(c, pImgFile);
		BufferedImage imgIn = ImageIO.read(pImgFile);
		d = new Dimension((int)(d.width * Constants.A4_SCALE), (int)(d.height * Constants.A4_SCALE));
		assertEquals(pImgFile.getName(), d.width, imgIn.getWidth());
		assertEquals(pImgFile.getName(), d.height, imgIn.getHeight());
	}

	@Test(expected = NullPointerException.class)
	public void testExportSheetAsImageComponentNull() throws NullPointerException, IOException {
		SheetTransformer.exportSheetAsImage(null, new File(""));
	}

	@Test(expected = NullPointerException.class)
	public void testExportSheetAsImageFileNull() throws NullPointerException, IOException {
		SheetTransformer.exportSheetAsImage(new TestComponent(), null);
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

	private static class TestFileFilter implements FileFilter {

		@Override
		public boolean accept(File pFile) {
			return pFile.isFile() && pFile.getName().startsWith(SheetTransformerImgTest.class.getSimpleName());
		}

	}

}
