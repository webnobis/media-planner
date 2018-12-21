package com.webnobis.mediaplanner.element;

public class Description {

	protected static final String PART = "/";

	private final String mKey;

	private final String mValue;

	/**
	 * Constructor
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public Description(String pKey, String pValue) {
		mKey = pKey;
		mValue = pValue;
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

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pObj) {
		if (pObj == null) {
			return false;
		} else if (pObj == this) {
			return true;
		} else if (Description.class.equals(pObj.getClass())) {
			Description other = (Description) pObj;
			if ((this.getKey() == null) != (other.getKey() == null)) {
				return false;
			}
			if ((this.getValue() == null) != (other.getValue() == null)) {
				return false;
			}
			if (this.getKey() != null) {
				if (!this.getKey().equalsIgnoreCase(other.getKey())) {
					return false;
				}
			}
			if (this.getValue() != null) {
				if (!this.getValue().equalsIgnoreCase(other.getValue())) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 13;
		if (this.getKey() != null) {
			hash ^= this.getKey().toLowerCase().hashCode();
		}
		if (this.getValue() != null) {
			hash ^= this.getValue().toLowerCase().hashCode();
		}
		return hash;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mKey + PART + mValue;
	}

}
