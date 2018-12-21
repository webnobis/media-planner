package com.webnobis.mediaplanner.sheet.util;

import javax.xml.bind.annotation.XmlAttribute;

public class XmlDescription {

	@XmlAttribute(name = "id")
	private final int mId;

	@XmlAttribute(name = "key")
	private final String mKey;

	@XmlAttribute(name = "value")
	private final String mValue;

	/**
	 * Constructor, only for JAXB
	 * 
	 */
	XmlDescription() {
		this(0, null, null);
	}

	/**
	 * Constructor
	 * 
	 * @param pId
	 * @param pKey
	 * @param pValue
	 */
	public XmlDescription(int pId, String pKey, String pValue) {
		mId = pId;
		mKey = pKey;
		mValue = pValue;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return mId;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return mKey;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return mValue;
	}

}
