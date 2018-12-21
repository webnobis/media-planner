package com.webnobis.mediaplanner.sheet;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.webnobis.mediaplanner.element.Description;
import com.webnobis.mediaplanner.sheet.util.Msg;

public class DescriptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String NO_KEY = "";

	private final JComboBox<String> mKeySelectionBox;

	private final JTextField mValueField;

	/**
	 * Constructor
	 * 
	 * @param pKeySelectionBox
	 * @param pValueFiled
	 */
	public DescriptionPanel(List<String> pAllowedKeys) {
		this(pAllowedKeys, null);
	}

	/**
	 * Constructor
	 * 
	 * @param pKeySelectionBox
	 * @param pValueFiled
	 */
	public DescriptionPanel(List<String> pAllowedKeys, Description pDescription) {
		super(new BorderLayout());
		if ((pAllowedKeys == null) || pAllowedKeys.isEmpty()) {
			mKeySelectionBox = new JComboBox<String>();
			mKeySelectionBox.setEnabled(false);
			mValueField = new JTextField();
			mValueField.setEnabled(false);
		} else {
			mKeySelectionBox = new JComboBox<String>(pAllowedKeys.toArray(new String[0]));
			if (pAllowedKeys.size() == 1) {
				mKeySelectionBox.setSelectedItem(0);
			} else {
				mKeySelectionBox.insertItemAt(NO_KEY, 0);
				if ((pDescription != null) && (pDescription.getKey() != null)) {
					mKeySelectionBox.setSelectedItem(pDescription.getKey());
				} else {
					mKeySelectionBox.setSelectedItem(NO_KEY);
				}
			}
			if ((pDescription == null) || (pDescription.getValue() == null)) {
				mValueField = new JTextField();
			} else {
				mValueField = new JTextField(pDescription.getValue());
			}
			mKeySelectionBox.addItemListener(new DescriptionValueListener(mValueField));
			mKeySelectionBox.setToolTipText(Msg.getText("update.description.key.help"));
			mValueField.setToolTipText(Msg.getText("update.description.value.help"));
		}
		this.add(mKeySelectionBox, BorderLayout.WEST);
		this.add(mValueField, BorderLayout.CENTER);
	}

	public boolean isDeleted() {
		return !mValueField.isEnabled();
	}

	public Description getDescription() {
		if (isDeleted()) {
			return null;
		} else {
			return new Description((String) mKeySelectionBox.getSelectedItem(), mValueField.getText());
		}
	}

}
