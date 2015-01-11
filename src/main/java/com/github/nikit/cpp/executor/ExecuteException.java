package com.github.nikit.cpp.executor;

public class ExecuteException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ExecuteException(String message) {
        super(message);
    }
	
	public ExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
