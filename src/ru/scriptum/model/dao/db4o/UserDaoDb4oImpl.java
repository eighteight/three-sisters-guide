/*
 * Scriptum Project
 */
package ru.scriptum.model.dao.db4o;

import java.util.List;

import ru.scriptum.model.dao.UserDao;
import ru.scriptum.model.domaindata.User;

import com.db4o.query.Predicate;

/**
 * The Hibernate implementation of the <code>UserDao</code>.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see UserDao
 */
public class UserDaoDb4oImpl extends DaoDb4oImpl implements UserDao {
	/**
	 * Default constructor.
	 */
	public UserDaoDb4oImpl() {
		super();
	}

	/**
	 * @see UserDao#getUser(String)
	 */
	public User getUser(final String name) {
		List<User> elements = getObjectContainer().query(new Predicate<User>() {
			public boolean match(User user) {
				return user.getName().equals(name);
			}
		});
		if (elements != null && elements.size() > 0)
			return elements.get(0);
		return null;
	}

	public List getAll() {
		return getAll(User.class);
	}

	private List getAll(Class<User> clazz) {
		return getList(getObjectContainer().get(clazz));
	}
}