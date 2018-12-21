package com.webnobis.mediaplanner.sheet.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.bind.JAXB;

import org.apache.log4j.Logger;

import com.webnobis.mediaplanner.element.Element;

/**
 * Transforms the sheet to xml relevant data or an image.
 * 
 * @author steffen
 * @version 1.00
 */
public class SheetTransformer {

	/**
	 * File extension: "xml"
	 */
	public static final String XML_FILE_EXT = "xml";

	/**
	 * Image file extensions: "png", "gif" and "jpg"
	 */
	public static final String[] IMG_FILE_EXTS = { "png", "gif", "jpg" };

	/**
	 * Printable image dpi: 300
	 */
	public static final int IMG_DPI = 300;

	/**
	 * Images background: white
	 */
	public static final Color IMG_BACKGROUND = Color.WHITE;

	private static final Logger sLog = Logger.getLogger(SheetTransformer.class);

	private static final char DOT = '.';

	/**
	 * Exports the sheet as an image. The type depends on the image file name. Unknown name results in "gif".
	 * 
	 * @param pSheet
	 *            the sheet
	 * @param pImgFile
	 *            the image file
	 * @return the written image file
	 * @throws IOException
	 *             on each transform or written problems
	 * @throws NullPointerException
	 *             if the sheet or the image file is null
	 */
	public static File exportSheetAsImage(Component pSheet, File pImgFile) throws IOException, NullPointerException {
		if (pSheet == null) {
			throw new NullPointerException("sheet is null");
		}
		if (pImgFile == null) {
			throw new NullPointerException("image file is null");
		}
		File file = toCorrectNamedFile(pImgFile, IMG_FILE_EXTS);
		String fileExt = file.getName().substring(file.getName().lastIndexOf(DOT) + 1).toLowerCase();
		boolean gif = IMG_FILE_EXTS[0].equals(fileExt);
		boolean jpg = IMG_FILE_EXTS[2].equals(fileExt);

		Dimension size = pSheet.getPreferredSize();
		int width = Math.round(size.width * Constants.A4_SCALE);
		int height = Math.round(size.height * Constants.A4_SCALE);
		BufferedImage img = new BufferedImage(width, height, (jpg) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		graphics.scale(Constants.A4_SCALE, Constants.A4_SCALE);
		graphics.setColor(IMG_BACKGROUND);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(pSheet.getGraphics().getColor());
		pSheet.paint(graphics);

		ImageOutputStream imgOutStream = null;
		try {
			ImageWriter imgOutWriter = ImageIO.getImageWritersByFormatName(fileExt).next();
			if (imgOutWriter == null) {
				throw new NullPointerException("imgOutWriter is null");
			}
			imgOutStream = ImageIO.createImageOutputStream(pImgFile);
			imgOutWriter.setOutput(imgOutStream);
			if (gif) {
				imgOutWriter.write(img);
			} else {
				ImageWriteParam param = imgOutWriter.getDefaultWriteParam();
				IIOMetadata data = imgOutWriter.getDefaultImageMetadata(new ImageTypeSpecifier(img), param);
				if (jpg) {
					setJPGDpi(data);
					param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
					param.setCompressionQuality(1f);
				} else {
					setPNGDpi(data);
				}
				imgOutWriter.write(null, new IIOImage(img, null, data), param);
			}
			imgOutStream.flush();
			imgOutWriter.dispose();
			sLog.info('\'' + file.getPath() + "' with " + img.getWidth() + 'x' + img.getHeight() + " pixels stored");
			return file;
		} finally {
			if (imgOutStream != null) {
				imgOutStream.close();
			} else {
				throw new IOException("No image writer found for suffix '" + fileExt + '\'');
			}
		}
	}

	private static void setPNGDpi(IIOMetadata pData) throws IIOInvalidTreeException {
		float dpi = SheetTransformer.IMG_DPI / 25.4f; // density is dots per mm
		IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
		horiz.setAttribute("value", String.valueOf(dpi));
		IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
		vert.setAttribute("value", String.valueOf(dpi));
		IIOMetadataNode dim = new IIOMetadataNode("Dimension");
		dim.appendChild(horiz);
		dim.appendChild(vert);
		IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
		root.appendChild(dim);
		pData.mergeTree("javax_imageio_1.0", root);
	}

	private static void setJPGDpi(IIOMetadata pData) throws IIOInvalidTreeException {
		int dpi = SheetTransformer.IMG_DPI;
		org.w3c.dom.Element tree = (org.w3c.dom.Element) pData.getAsTree("javax_imageio_jpeg_image_1.0");
		org.w3c.dom.Element jfif = (org.w3c.dom.Element) tree.getElementsByTagName("app0JFIF").item(0);
		jfif.setAttribute("Xdensity", Integer.toString(dpi));
		jfif.setAttribute("Ydensity", Integer.toString(dpi));
		jfif.setAttribute("resUnits", "1"); // density is dots per inch
		pData.setFromTree("javax_imageio_jpeg_image_1.0", tree);
	}

	public static File storeElements(Component pSheet, Iterable<Element> pElements, File pXmlFile) throws IOException, NullPointerException {
		if (pElements == null) {
			throw new NullPointerException("element list is null");
		}
		if (pXmlFile == null) {
			throw new NullPointerException("xml file is null");
		}
		File file = toCorrectNamedFile(pXmlFile, XML_FILE_EXT);
		Dimension size = pSheet.getPreferredSize();
		JAXB.marshal(new XmlSheet(size.width, size.height, pElements), file);
		return file;
	}

	public static SheetElements loadElements(File pXmlFile) throws IOException, NullPointerException {
		if (pXmlFile == null) {
			throw new NullPointerException("xml file is null");
		}
		XmlSheet xmlSheet = JAXB.unmarshal(pXmlFile, XmlSheet.class);
		try {
			return new SheetElements(xmlSheet.getElements(), xmlSheet.getWidth(), xmlSheet.getHeight());
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
		}
	}

	private static File toCorrectNamedFile(File pFile, String... pExts) {
		if (pFile.isDirectory() && pFile.exists()) {
			return new File(pFile, pFile.getName() + DOT + pExts[0]);
		}
		File parent = pFile.getParentFile();
		if ((parent != null) && !parent.exists()) {
			parent.mkdirs();
		}
		int index = pFile.getName().lastIndexOf(DOT);
		if (index > -1) {
			for (String ext : pExts) {
				if (pFile.getName().toLowerCase().endsWith(ext)) {
					return pFile;
				}
			}
		}
		return new File(parent, pFile.getName() + DOT + pExts[0]);
	}

}
