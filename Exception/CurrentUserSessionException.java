package com.spring.cab.Exception;

public class CurrentUserSessionException extends Exception{

	private static final long serialVersionUID = 1L;

	public CurrentUserSessionException(String msg) {
		super(msg);
	}
}