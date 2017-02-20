/*
 * JCatalog Project
 */
package ru.scriptum.model.exception;

/**
 * Exception thrown when a user tries to use a non-existing username to log in.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see CatalogException
 */
public class UsernameNotExistException extends CatalogException {
	private String username;

	/**
	 * Constructor with non-existing username.
	 * 
	 * @param newUserName the non-existing username
	 */
	public UsernameNotExistException(String newUsername) {
		super("Username " + newUsername + " does not exist");
		this.username = newUsername;
	}
	
	public String getUsername() {
		return this.username;
	}
}
