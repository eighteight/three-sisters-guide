/*
 * Created on Nov 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ru.scriptum.model.exception;

public class BeanException extends Exception {
	/**
	 * Constructor with error message.
	 * 
	 * @param msg the error message associated with the exception
	 */
	public BeanException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor with error message and root cause.
	 * 
	 * @param msg the error message associated with the exception
	 * @param cause the root cause of the exception
	 */
	public BeanException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
