/*
 * Scriptum Project
 */
package ru.scriptum.view.builder;

import org.apache.commons.beanutils.BeanUtils;

import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.CatalogException;
import ru.scriptum.view.handler.UserBean;

/**
 * The builder class for <code>UserBean</code>.
 * <p>
 * The backing beans are used view objects in the presentation tier,
 * and the business objects are used in the business logic tier.
 * This builder class is used to convert in between the backing beans and business objects.
 * <p>
 * Commons BeanUtils is used.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see <a href="http://jakarta.apache.org/commons/beanutils/">Commons BeanUtils</a>
 */
public class UserBuilder {
	/**
	 * Create a new <code>UserBean</code> based on a <code>User</code> business object.
	 * 
	 * @param user the user business object
	 * @return the new user backing bean
	 * @throws CatalogException
	 */
	public static UserBean createUserBean(User user) throws CatalogException {
		UserBean userBean = new UserBean();
		
		try {
			BeanUtils.copyProperties(userBean, user);
		} catch (Exception e) {
			throw new CatalogException("Could not build UserBean " + e.toString(), e);
		}
		
		return userBean;
	}
}