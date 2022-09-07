package it.dodu.jlock;

public class JLockException extends RuntimeException {

	public JLockException(String message) {
		super(message);
	}

	public JLockException(String message, Throwable e) {
		super(message, e);
	}

}
