/*
 * Created on Nov 15, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ru.scriptum.model.dao.db4o;

import java.util.ArrayList;
import java.util.List;

import ru.scriptum.model.dao.IDao;
import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.IPersistable;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.User;

import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.types.Blob;

public class DaoDb4oImpl extends Db4oDaoSupport implements IDao {

	Blob blob;

	public DaoDb4oImpl() {
		super();
	}

	public void delete(IPersistable obj) {
		deleteObject(obj);
	}

	public List findById(long id) {
		Query query = getObjectContainer().query();
		query.constrain(IElement.class);
		query.descend("id").constrain(id);
		return getList(query.execute());
	}

	public List<IElement> findByParameter(String paramName, final String value) {
		try {
			// List <IPersistable> result = getObjectContainer().query(new
			// Predicate<IPersistable>() {
			// public boolean match(IPersistable persistable) {
			// return persistable.getName().equals(value);
			// }
			// });
			//		
			// return result;

			Query query = getObjectContainer().query();
			query.constrain(IPersistable.class);
			query.descend(paramName).constrain(value).equal();
			return getList(query.execute());
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ArrayList<IElement>();
		}
	}

	public List<ITemplateElement> findByTemplateType(final String templateType) {
		List<ITemplateElement> elements = getObjectContainer().query(
				new Predicate<ITemplateElement>() {
					public boolean match(ITemplateElement element) {
						return element.getTemplateType().equals(templateType);
					}
				});
		return elements;
	}

	public List<IElement> getAll() {
		return getList(getObjectContainer().get(IElement.class));
	}

	public List<IElement> getAll(String name) {
		Query query = getObjectContainer().query();
		query.constrain(IElement.class);
		query.descend("name").constrain(name);
		return getList(query.execute());
	}

	public List<IElement> getAllRoots() {
		List<IElement> elements = getObjectContainer().query(
				new Predicate<IElement>() {
					public boolean match(IElement element) {
						return element.getParent() == null;
					}
				});

		return elements;
	}

	public List<IElement> getAllRoots(final User user) {
		List<IElement> elements = getObjectContainer().query(
				new Predicate<IElement>() {
					public boolean match(IElement element) {
						return element.getParent() == null
								&& user.equals(element.getUser());
					}
				});
		return elements;
	}

	public Object getFirst(long id) {
		return findObjectByInternalIdandActivate(id);
	}

	public Object getFirst(String name) {
		return findByParameter("name", name).get(0);
	}

	public List<ITemplateElement> getJoin(final Object value, final int i) {
		List<ITemplateElement> elements = getObjectContainer().query(
				new Predicate<ITemplateElement>() {
					public boolean match(ITemplateElement templated) {
						return templated.getProperties()[i].getValue().equals(
								value);
					}
				});
		return elements;
	}

	public Object save(IPersistable persistable) {
		return saveObject(persistable);
	}

	public Object update(IPersistable persistable) {
		return updateObject(persistable);
	}
}