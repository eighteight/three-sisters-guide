/*
 * Scriptum Project
 */
package ru.scriptum.model.service;

import java.util.List;

import ru.scriptum.model.data.IPersistable;
import ru.scriptum.model.exception.DaoException;

public interface ImageService {
	/**
	 * Save the product.
	 * 
	 * @param product the product to be saved
	 * @return the product saved
	 * @throws DaoException
	 */
	public Object save(IPersistable obj) throws DaoException;

	/**
	 * Update product.
	 * 
	 * @param product the product to be updated
	 * @throws DaoException
	 */
	public Object update(IPersistable obj) throws DaoException;
	
	/**
	 * Delete product.
	 * 
	 * @param product the product to be deleted
	 * @throws DaoException
	 */
	public void delete(IPersistable obj) throws DaoException;
	
	/**
	 * Get product by product id.
	 * @param productId the product id
	 * @return the product associated with the product id
	 * @throws DaoException
	 */
	public Object get(long id) throws DaoException;
	
	/**
	 * Get all products.
	 * 
	 * @return a list with all products
	 * @throws DaoException
	 */
	public List getAll() throws DaoException;
}
