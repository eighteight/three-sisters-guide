/*
 * Created on Nov 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ru.scriptum.model.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import ru.scriptum.model.dao.IDao;
import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.IPersistable;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.model.exception.DuplicateBeanIdException;
import ru.scriptum.model.service.IBeanService;

public class BeanServiceImpl implements IBeanService {

	private IDao dao;

	private Log logger = LogFactory.getLog(this.getClass());

	public boolean elementExists(Object value, int index) {
		return (this.dao.getJoin(value, index).size() > 0);
	}

	public boolean elementExists(String elementName) {
		return (this.dao.findByParameter("name", elementName).size() > 0);
	}

	public void delete(ITemplateElement hetero) throws BeanException {

		try {
			this.dao.delete(hetero);
		} catch (Exception e) {
			String msg = "Could not delete bean";
			this.logger.error(msg, e);

			throw new BeanException(msg, e);
		}
	}

	public List<IElement> findByParameter(String paramName, String value) {
		return this.dao.findByParameter(paramName, value);
	}

	public List<IElement> getAll() throws BeanException {
		try {
			return this.dao.getAll();
		} catch (Exception e) {
			String msg = "Could not get all products";
			this.logger.error(msg, e);
			throw new BeanException(msg, e);
		}
	}

	public List<IElement> getAllRoots() {
		return dao.getAllRoots();
	}

	public List<IElement> getAllRoots(User user) {
		return dao.getAllRoots(user);
	}

	public IPersistable getPersistable(long id) {
		try {
			return (IPersistable) this.dao.getFirst(id);
		} catch (Exception e) {
			String msg = "Could not get product for id of " + id;
			this.logger.error(msg, e);
		}
		return null;
	}

	public boolean otherElementExists(Long id, String name) {
		if (!elementExists(name))
			return false;
		Iterator all = this.dao.getAll(name).iterator();
		while (all.hasNext()) {
			if (!((IElement) all.next()).getId().equals(id)) {
				return true;
			}
		}
		return false;

	}

	public IElement save(IElement hetero) throws BeanException {
		try {
			return (IElement) this.dao.save(hetero);
		} catch (DataIntegrityViolationException de) {
			String msg = "Could not save product, duplicate product id";
			this.logger.error(msg, de);

			throw new DuplicateBeanIdException(msg, de);
		} catch (Exception e) {
			String msg = "Could not save product " + e.toString();
			this.logger.error(msg, e);
			throw new BeanException(msg, e);
		}
	}

	public void setDao(IDao dao) {
		this.dao = dao;
	}

	public IElement update(IElement hetero) throws BeanException {
		return (IElement) this.dao.update(hetero);
	}

	public List <ITemplateElement>findByTemplateType(String templateType) {
		return this.dao.findByTemplateType(templateType);
	}

	public IElement findByName(String elementName) {
		List <IElement> ret = this.dao.findByParameter("name", elementName);
		return ret == null||ret.size()==0 ? null: ret.get(0);
	}
}
