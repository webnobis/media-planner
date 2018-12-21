package com.webnobis.mediaplanner.sheet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import com.webnobis.mediaplanner.sheet.util.FileExtensionFilter;
import com.webnobis.mediaplanner.sheet.util.Msg;
import com.webnobis.mediaplanner.sheet.util.Palette;
import com.webnobis.mediaplanner.sheet.util.SheetTransformer;

public class PlannerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final AtomicInteger sNewSheetCount = new AtomicInteger();

	private final ElementCopyAndPasteListener mCopyAndPasteListener;

	private final JDesktopPane mDesktop;

	private File mFile;

	public PlannerFrame(String pTitle) throws HeadlessException {
		super(pTitle);
		mCopyAndPasteListener = new ElementCopyAndPasteListener();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		super.setSize(screen);

		mDesktop = new JDesktopPane();
		mDesktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		super.setContentPane(mDesktop);

		super.setJMenuBar(new PlannerFrameMenu(this));
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
	}

	public void loadPalette(Palette pPalette) {
		PaletteFrame paletteFrame = null;
		for (Component component : mDesktop.getComponents()) {
			if (PaletteFrame.class.isAssignableFrom(component.getClass())) {
				paletteFrame = (PaletteFrame) component;
				if (paletteFrame.isLoadedPalette(pPalette)) {
					mDesktop.setComponentZOrder(paletteFrame, 0);
					break;
				}
			}
			paletteFrame = null;
		}
		if (paletteFrame == null) {
			paletteFrame = new PaletteFrame(mCopyAndPasteListener);
			paletteFrame.loadPalette(pPalette);
			mDesktop.add(paletteFrame);
			mDesktop.setComponentZOrder(paletteFrame, 0);
		}
		mDesktop.repaint();
	}

	public void newSheet() {
		initializeSheet(null);
	}

	public void loadSheet() {
		JFileChooser fileChooser;
		if (mFile == null) {
			fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		} else {
			fileChooser = new JFileChooser(mFile.getParentFile());
		}
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(new FileExtensionFilter(SheetTransformer.XML_FILE_EXT));
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
			mFile = fileChooser.getSelectedFile();
			if (mFile != null) {
				initializeSheet(mFile);
			}
		}
	}

	private void initializeSheet(File pFile) {
		SheetFrame sheetFrame;
		if (pFile == null) {
			sheetFrame = new SheetFrame(mCopyAndPasteListener, Msg.getText("sheet.new.name", sNewSheetCount.incrementAndGet()), null); // a new sheet
		} else {
			sheetFrame = new SheetFrame(mCopyAndPasteListener, mFile.getName(), mFile); // the loaded sheet
			sheetFrame.loadElements();
		}
		sheetFrame.setSize(this.getSize());
		mDesktop.add(sheetFrame);
		int zOrder = 0;
		for (Component component : mDesktop.getComponents()) {
			if (PaletteFrame.class.isAssignableFrom(component.getClass())) {
				zOrder++;
			}
		}
		mDesktop.setComponentZOrder(sheetFrame, zOrder);
		mDesktop.getDesktopManager().maximizeFrame(sheetFrame);
	}

}
