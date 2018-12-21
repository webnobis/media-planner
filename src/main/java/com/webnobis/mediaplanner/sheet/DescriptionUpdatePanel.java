package com.webnobis.mediaplanner.sheet;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.element.Element;

public class DescriptionUpdatePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Element mElement;

	private final List<DescriptionPanel> mDescriptionPanels;

	/**
	 * Constructor
	 * 
	 * @param pElement
	 */
	public DescriptionUpdatePanel(Element pElement) {
		super(new GridLayout(Math.max(0, pElement.getDescriptionMaxCount()), 1));
		mElement = pElement;
		if (pElement.isDescribable()) {
			mDescriptionPanels = new ArrayList<DescriptionPanel>(Math.max(0, pElement.getDescriptionMaxCount()));
			for (Description description : pElement.getDescriptions()) {
				mDescriptionPanels.add(new DescriptionPanel(pElement.getAllowedDescriptions(), description)); // all available descriptions
			}
			while (mDescriptionPanels.size() < pElement.getDescriptionMaxCount()) {
				mDescriptionPanels.add(new DescriptionPanel(pElement.getAllowedDescriptions())); // until maximal count
			}
			for (DescriptionPanel descriptionPanel : mDescriptionPanels) {
				this.add(descriptionPanel);
			}
		} else {
			mDescriptionPanels = null;
		}
	}

	public void updateDescriptions() {
		mElement.getDescriptions().clear();
		for (DescriptionPanel descriptionPanel : mDescriptionPanels) {
			if (descriptionPanel.isDeleted()) {
				continue;
			}
			mElement.getDescriptions().add(descriptionPanel.getDescription());
		}
	}

}
