/*
 * Scriptum Project
 */
package ru.scriptum.model.service;

import java.util.List;

import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.CatalogException;
import ru.scriptum.model.exception.UsernameNotExistException;
import ru.scriptum.view.handler.UserBean;

/**
 * The user business service interface.
 * <p>
 * It contains all user management related business logic.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public interface IUserService {
	public User createUser(UserBean userBean);
	
	public boolean emailExists(String email);

	public List getAllUsers();
	
	public User getUser(String username);

	/**
	 * Login a user using username and password.
	 * 
	 * @param username the username to be used
	 * @param password the password to be used
	 * @return the user associated with the username and password
	 * @throws CatalogException
	 */
	public User login(String name, String password) throws UsernameNotExistException;

	/**
	 * Send email to the administrator.
	 * 
	 * @param senderAddress the email address of the sender
	 * @param senderName the name of the email sender
	 * @param sub the subject of the email
	 * @param msg the email message
	 * @throws CatalogException
	 */
	public void sendRegistrationEmail(String senderAddress, String senderName, String userName, String sub, String msg) throws CatalogException;

	public void updateUser(User user);

	public boolean userExists(String username);
}
