/**
 *
 */
package com.wuji.learn.jpa.model;

/**
 * @author Yayun
 *
 */
public class SystemException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -1995982424991803590L;

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

}
