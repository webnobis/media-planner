package com.webnobis.mediaplanner.sheet.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileExtensionFilter extends FileFilter {

	private static final String NAME_PART = "*.";

	private static final String SPACE_PART = ", ";

	private final String[] mAllowedExtensions;

	private final String mDescription;

	/**
	 * Constructor
	 * 
	 * @param pAllowedExtensions
	 */
	public FileExtensionFilter(String... pAllowedExtensions) {
		mAllowedExtensions = pAllowedExtensions;
		StringBuilder sb = new StringBuilder();
		for (String ext : mAllowedExtensions) {
			sb.append(NAME_PART).append(ext).append(SPACE_PART);
		}
		if (sb.length() < SPACE_PART.length()) {
			mDescription = sb.toString();
		} else {
			mDescription = sb.substring(0, sb.length() - SPACE_PART.length());
		}
	}

	@Override
	public boolean accept(File pFile) {
		if (pFile.isDirectory()) {
			return true;
		}
		for (String ext : mAllowedExtensions) {
			if (pFile.getName().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return mDescription;
	}

}
