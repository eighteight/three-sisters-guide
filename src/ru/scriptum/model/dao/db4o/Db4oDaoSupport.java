package ru.scriptum.model.dao.db4o;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.beanutils.BeanUtils;

import ru.scriptum.model.dao.IDao;
import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.IPersistable;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Db4oDaoSupport implements PhaseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ObjectContainer objectContainer;

	/**
	 * @see IDao#deleteObject(IPersistable)
	 */
	protected void deleteObject(IPersistable persistable) {
		// /Object o = findObjectByInternalId(persistable.getId());
		getObjectContainer().delete(persistable);
		getObjectContainer().commit();
	}

	protected Object findObjectById(Class class1, long id) {
		Object object;
		try {
			object = class1.newInstance();
			((IPersistable) object).setId(id);
			return retrieveObject(object);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object findObjectById(IPersistable persistable) {
		return findObjectById(persistable.getClass(), persistable.getId());
	}

	protected Object findObjectByInternalId(long id) {
		return getObjectContainer().ext().getByID(id);
	}

	protected Object findObjectByInternalIdandActivate(long id) {
		Object obj = findObjectByInternalId(id);
		getObjectContainer().activate(obj, 5);
		return obj;
	}

	protected Object findPersistableByName(Class class1, String name) {
		Object object;
		try {
			object = class1.newInstance();
			((IPersistable) object).setName(name);
			return retrieveObject(object);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected long findObjectInternalId(Object object) {
		return getObjectContainer().ext().getID(object);
	}

	protected List<IElement> getList(ObjectSet result) {
		List<IElement> ret = new ArrayList<IElement>();
		while (result.hasNext()) {
			ret.add((IElement) result.next());
		}
		return ret;
	}

	protected ObjectContainer getObjectContainer() {
		return objectContainer;
	}

	private Object retrieveObject(Object object) {
		return this.getObjectContainer().get(object).next();
	}

	public Object saveObject(IPersistable persistable) {
		getObjectContainer().set(persistable);
		getObjectContainer().commit();
		return persistable;
	}

	public void setObjectContainer(ObjectContainer objectContainer) {
		this.objectContainer = objectContainer;
	}

	protected Object updateObject(IPersistable persistable) {
		Object o = findObjectByInternalId(persistable.getId());
		try {
			BeanUtils.copyProperties(o, persistable);
			getObjectContainer().set(o);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		getObjectContainer().commit();
		return o;
	}

	public void afterPhase(PhaseEvent event) {
//		Object severity = event.getFacesContext().getMaximumSeverity();
//		if (FacesMessage.SEVERITY_INFO.equals(severity) || severity == null) {
//			getObjectContainer().commit();
//		} else {
//			// TODO refresh live objects before rollback
//			getObjectContainer().rollback();
//		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.INVOKE_APPLICATION;
	}

	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}
}
