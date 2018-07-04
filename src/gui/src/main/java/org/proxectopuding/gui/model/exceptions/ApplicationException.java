package org.proxectopuding.gui.model.exceptions;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = -5296358882337729993L;

	private ErrorKey errorKey;
	
	public ApplicationException(ErrorKey errorKey) {
		
		this.errorKey = errorKey;
	}

	public ErrorKey getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(ErrorKey errorKey) {
		this.errorKey = errorKey;
	}
}
