/*
 * Scriptum Project
 */
package ru.scriptum.model.dao;

import java.util.List;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.IPersistable;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.User;

/**
 * Catalog DAO interface.
 * <p>
 * This class contains catalog management related data access logic.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public interface IDao {
	void delete(IPersistable obj);

	List findById(long id);

	List<IElement> findByParameter(String paramName, String value);

	/**
	 * Get all the beans.
	 * 
	 * @return a list of all beans
	 */
	List<IElement> getAll();

	List getAll(String name);

	List<IElement> getAllRoots();

	List<IElement> getAllRoots(User user);

	Object getFirst(long id);

	Object getFirst(String name);

	List getJoin(Object value, int index);

	Object save(IPersistable obj);

	Object update(IPersistable obj);

	List<ITemplateElement> findByTemplateType(String templateType);
}
