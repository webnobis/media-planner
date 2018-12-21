package com.webnobis.mediaplanner.element;

public class ElementException extends Exception {

	private static final long serialVersionUID = 1L;

	public ElementException(String pMessage) {
		super(pMessage);
	}

	public ElementException(String pMessage, Throwable pCause) {
		super(pMessage, pCause);
	}

}
