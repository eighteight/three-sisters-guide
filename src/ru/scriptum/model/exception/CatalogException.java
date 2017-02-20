/*
 * Scriptum Project
 */
package ru.scriptum.model.exception;

/**
 * Base checked exception for the Scriptum Project.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class CatalogException extends Exception{
	/**
	 * Constructor with error message.
	 * 
	 * @param msg the error message associated with the exception
	 */
	public CatalogException(String msg) {
		super(msg);
	}
	
	/**
	 * Constructor with error message and root cause.
	 * 
	 * @param msg the error message associated with the exception
	 * @param cause the root cause of the exception
	 */
	public CatalogException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
