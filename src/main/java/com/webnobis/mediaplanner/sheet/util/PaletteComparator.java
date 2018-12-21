package com.webnobis.mediaplanner.sheet.util;

import java.util.Comparator;

import com.webnobis.mediaplanner.element.Element;

public class PaletteComparator implements Comparator<Element> {

	@Override
	public int compare(Element pElement1, Element pElement2) {
		if (pElement1.isLine() == pElement2.isLine()) {
			return pElement1.getName().compareToIgnoreCase(pElement2.getName()); // alphabetical, because both are the same types
		} else if (pElement1.isLine()) {
			return 1; // a line is always after a module
		} else {
			return -1;
		}
	}

}
