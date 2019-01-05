package com.webnobis.mediaplanner.sheet.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webnobis.mediaplanner.element.Element;
import com.webnobis.mediaplanner.element.palette.Group;

public abstract class PaletteUtil {

	private static final Logger sLog = LoggerFactory.getLogger(PaletteUtil.class);

	private static final Comparator<Element> sComparator = new PaletteComparator();

	private static final Map<Palette, Collection<Class<Element>>> sElementClassCache = new HashMap<Palette, Collection<Class<Element>>>();

	static {
		try {
			loadElements();
		} catch (IOException e) {
			sLog.error(e.getMessage(), e);
		}
	}

	private PaletteUtil() {
	}

	@SuppressWarnings("unchecked")
	private static void loadElements() throws IOException {
		Class<?> c;
		Group group;
		for (String className : ClassFinder.getAllClassNames(Group.class.getPackage())) {
			try {
				c = Class.forName(className);
				if (c.isAnnotationPresent(Group.class) && Element.class.isAssignableFrom(c)) {
					group = c.getAnnotation(Group.class);
					for (Palette p : group.palette()) {
						if (!sElementClassCache.containsKey(p)) {
							sElementClassCache.put(p, new HashSet<Class<Element>>());
						}
						sElementClassCache.get(p).add((Class<Element>) c);
					}
					if (sLog.isDebugEnabled()) {
						sLog.debug(c.getSimpleName() + " added to palette " + Arrays.asList(group.palette()));
					}
				}
			} catch (ClassNotFoundException e) {
				sLog.error(e.getMessage(), e);
			}
		}
	}

	public static List<Element> getElements(Palette pPalette) {
		List<Element> elements = new ArrayList<Element>();
		if (pPalette != null) {
			Collection<Class<Element>> classes = sElementClassCache.get(pPalette);
			if ((classes == null) || classes.isEmpty()) {
				sLog.warn("no elements found for palette " + pPalette);
				return elements;
			}
			for (Class<Element> c : classes) {
				try {
					elements.add(c.getConstructor().newInstance());
				} catch (Exception e) {
					sLog.error("couldn't create " + pPalette.name().toLowerCase() + " element from class " + c.getName(), e);
				}
			}
			Collections.sort(elements, sComparator);
		}
		return elements;
	}

}
