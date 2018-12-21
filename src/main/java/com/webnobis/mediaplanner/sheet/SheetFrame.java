package com.webnobis.mediaplanner.sheet;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;

import com.webnobis.mediaplanner.sheet.util.FileExtensionFilter;
import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.SheetElements;
import com.webnobis.mediaplanner.sheet.util.SheetTransformer;

public class SheetFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static final String DOT = ".";

	private final SheetPanel mSheetPanel;

	private final JScrollPane mScrollPane;

	private final SheetFrameMenu mMenu;

	private File mFile;

	public SheetFrame(ElementCopyAndPasteListener pCopyAndPasteListener, String pTitle, File pFile) {
		super(pTitle, true, true, true, true);
		mSheetPanel = new SheetPanel(pCopyAndPasteListener);
		mFile = pFile;
		mScrollPane = new JScrollPane(mSheetPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		SheetSizeChangeListener sheetSizeChangeListener = new SheetSizeChangeListener(this, mSheetPanel);
		mScrollPane.getViewport().addChangeListener(sheetSizeChangeListener); // for correct raster painting needed
		Container container = super.getContentPane();
		container.setLayout(new GridLayout(1, 1));
		container.add(mScrollPane);
		mMenu = new SheetFrameMenu(this);
		this.setJMenuBar(mMenu);
		super.setVisible(true);
		this.addComponentListener(sheetSizeChangeListener);
	}

	public void loadElements() {
		if (mFile != null) {
			try {
				SheetElements sheetElements = SheetTransformer.loadElements(mFile);
				mSheetPanel.loadElements(sheetElements.getElements());
				mMenu.updateSizeSelection(sheetElements.getSheetSize());
				this.setSheetSize(sheetElements.getSheetSize());
				this.setTitle(mFile.getName());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, Msg.getText("msg.error.sheet.load", mFile.getName(), e.getMessage()), Msg.getText("msg.error.title"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void storeSheet() {
		if (mFile == null) {
			storeSheetAs();
		} else {
			try {
				mFile = SheetTransformer.storeElements(mSheetPanel, mSheetPanel.getElements(), mFile);
				this.setTitle(mFile.getName());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, Msg.getText("msg.error.sheet.store", mFile.getName(), e.getMessage()), Msg.getText("msg.error.title"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void storeSheetAs() {
		JFileChooser fileChooser;
		if (mFile == null) {
			fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		} else {
			fileChooser = new JFileChooser(mFile.getParentFile());
		}
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileExtensionFilter(SheetTransformer.XML_FILE_EXT));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			mFile = fileChooser.getSelectedFile();
			if (mFile != null) {
				if (!mFile.exists()
						|| (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, Msg.getText("msg.question.store.overwrite", mFile), Msg.getText("msg.question.title"),
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE))) {
					storeSheet();
				}
			}
		}
	}

	public void exportSheetAsImage() {
		JFileChooser fileChooser;
		if (mFile == null) {
			fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		} else {
			fileChooser = new JFileChooser(mFile.getParentFile());
			StringBuilder imgFileName = new StringBuilder(mFile.getName());
			imgFileName.replace(imgFileName.indexOf(DOT) + 1, imgFileName.length(), SheetTransformer.IMG_FILE_EXTS[0]); // default: same name with extension "png"
			fileChooser.setSelectedFile(new File(mFile.getParentFile(), imgFileName.toString()));
		}
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileExtensionFilter(SheetTransformer.IMG_FILE_EXTS));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(this)) {
			File imgFile = fileChooser.getSelectedFile();
			if (imgFile != null) {
				if (!imgFile.exists()
						|| (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, Msg.getText("msg.question.store.overwrite", imgFile), Msg.getText("msg.question.title"),
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE))) {
					try {
						boolean raster = mSheetPanel.isRaster();
						mSheetPanel.setRaster(false);
						SheetTransformer.exportSheetAsImage(mSheetPanel, imgFile);
						mSheetPanel.setRaster(raster);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(this, Msg.getText("msg.error.sheet.store", imgFile.getName(), e.getMessage()), Msg.getText("msg.error.title"), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	public void setRaster(boolean pVisible) {
		mSheetPanel.setRaster(pVisible);
		mSheetPanel.invalidate();
		mSheetPanel.repaint();
	}

	public void setSheetSize(Dimension pDimension) {
		mSheetPanel.setPreferredSize(pDimension);
		mSheetPanel.setMinimumSize(pDimension);
		mSheetPanel.setMaximumSize(pDimension);
		this.setSize(pDimension);
		mSheetPanel.revalidate();
	}

	/**
	 * @see java.awt.Component#setSize(java.awt.Dimension)
	 */
	@Override
	public void setSize(Dimension pDimension) {
		Insets insets1 = this.getInsets();
		Insets insets2 = mScrollPane.getInsets();
		int width = insets1.left + insets1.right + insets2.left + insets2.right + mScrollPane.getVerticalScrollBar().getWidth();
		int height = insets1.top + insets1.bottom + insets2.top + insets2.bottom;
		super.setSize(Math.min(this.getWidth(), pDimension.width + width), Math.min(this.getHeight(), pDimension.height + height));
	}

	/**
	 * @see java.awt.Component#setSize(int, int)
	 */
	@Override
	public void setSize(int pWidth, int pHeight) {
		this.setSize(new Dimension(pWidth, pHeight));
	}

}
