package com.webnobis.mediaplanner.sheet.util;

import java.util.ResourceBundle;

public abstract class Msg {

	public static final String PARAMETER_KEY = "$";

	public static final String MESSAGE_RESOURCE = "message";

	private Msg() {
	}

	public static String getText(String pKey) {
		return getText(pKey, (Object) null);
	}

	public static String getText(String pKey, Object... pParameters) {
		String value = ResourceBundle.getBundle(MESSAGE_RESOURCE).getString(pKey);
		if (pParameters == null) {
			return value;
		}
		String pk;
		StringBuilder sb = new StringBuilder(value);
		for (int index, i = 0; i < pParameters.length; i++) {
			pk = PARAMETER_KEY + i;
			index = sb.indexOf(pk);
			if (index < 0) {
				continue;
			}
			sb.replace(index, index + pk.length(), pParameters[i].toString());
		}
		return sb.toString();
	}

	public static int getNumber(String pKey) {
		try {
			return Integer.parseInt(getText(pKey));
		} catch (Exception e) {
			return 0;
		}
	}

}
