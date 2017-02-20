/*
 * Scriptum Project
 */
package ru.scriptum.model.dao;

import ru.scriptum.model.domaindata.User;

/**
 * The user DAO interface.
 * <p>
 * This class contains user management related data access logic.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public interface UserDao extends IDao {
	/**
	 * Get user by username.
	 * 
	 * @param username the username
	 * @return the user associated with the username
	 */
	public User getUser(String name);
	
}
