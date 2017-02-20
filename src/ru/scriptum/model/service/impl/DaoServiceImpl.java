/*
 * Scriptum Project
 */
package ru.scriptum.model.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import ru.scriptum.model.dao.IDao;
import ru.scriptum.model.data.IPersistable;
import ru.scriptum.model.exception.DaoException;
import ru.scriptum.model.service.ImageService;

/**
 * The implementation of the <code>CatalogService</code>.
 * <p>
 * Spring Framework is used to manage this service bean.
 * Since this class is not dependend on Spring API, it can be used outside the Spring IOC container.
 * <p>
 * It is not used in the sample application. 
 * The <code>CachedCatalogServiceImpl</code> is used instead.
 * The purpose of this class is to show you by using Spring Framework, 
 * you can use different implementations of the same interface without code changing and factory.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see CatalogService
 * @see CachedCatalogServiceImpl
 */
public class DaoServiceImpl implements ImageService {
	//the CatalogDao used
	private IDao dao;
	
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * @see CatalogService#deleteProduct(Product)
	 */
	public void delete(IPersistable obj) throws DaoException {
		this.logger.debug(("entering method deleteProduct"));
		
		try {
			this.dao.delete(obj);
		} catch (Exception e) {
			String msg = "Could not delete product";
			this.logger.error(msg, e);
			
			throw new DaoException(msg, e);
		}		
	}
	
	/**
	 * @see CatalogService#getProduct(String)
	 */
	public Object get(long id) throws DaoException {
		try {
			return this.dao.getFirst(id);
		} catch (Exception e) {
			String msg = "Could not get product for id of " + id;
			this.logger.error(msg, e);
			
			throw new DaoException(msg, e);
		}
	}

	/**
	 * @see CatalogService#getAllProducts()
	 */
	public List getAll() throws DaoException {
		try {
			return this.dao.getAll();
		} catch (Exception e) {
			String msg = "Could not get all objects";
			this.logger.error(msg, e);
			
			throw new DaoException(msg, e);
		}
	}
	
	/**
	 * @see CatalogService#saveProduct(Product)
	 */
	public Object save(IPersistable obj) throws DaoException {
		this.logger.debug(("entering method saveProduct"));
		
		try {
			Object newObject = this.dao.save(obj);
			
			return newObject;
		} catch (DataIntegrityViolationException de) {
			String msg = "Could not save persistable, duplicate id";
			this.logger.error(msg, de);
			
			throw new DaoException(msg, de);
		} catch (Exception e) {
			String msg = "Could not save product " + e.toString();
			this.logger.error(msg, e);
			
			throw new DaoException(msg, e);
		}
	}
	
	/**
	 * @see CatalogService#updateProduct(Product)
	 */
	public Object update(IPersistable obj) throws DaoException {
		this.logger.debug(("entering method updateProduct"));
		try {
			return this.dao.update(obj);
		} catch (Exception e) {
			String msg = "Could not update object";
			this.logger.error(msg, e);
			throw new DaoException(msg, e);
		}	
	}

	public void setDao(IDao dao) {
		this.dao = dao;
	}
}
