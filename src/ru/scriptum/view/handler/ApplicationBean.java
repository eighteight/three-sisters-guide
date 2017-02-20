/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The managed bean with application scope. 
 * <p>
 * It is used as an application scope cache.
 * In JSF, the properties are set by bean management facility.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class ApplicationBean extends BaseBean {
	//the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());

	//list of SelectItems for all categories
	private List categorySelectItems;

	//the number of products per page
	private int productsPerPage;

	//the uri for product image directory
	private static String productImageDirUri;

	//the uri for image upload result page
	private static String fileUploadResultPage;

	/**
	 * Default Constructor.
	 * 
	 * @throws FacesException If internal error occurs while retrieves categories
	 */
	public ApplicationBean() {
		this.categorySelectItems = new ArrayList();
		this.logger.debug("ApplicationBean is created");
	}
	
	/**
	 * Initializes the ApplicationBean.
	 * <p>
	 * load all the categories.
	 * 
	 * @see BaseBean#init()
	 */
	protected void init() {

	}
	
	public List getCategorySelectItems() {
		return this.categorySelectItems;
	}
	
	public int getProductsPerPage() {
		return this.productsPerPage;
	}
	
	public void setProductsPerPage(int newProductsPerPage) {
		this.productsPerPage = newProductsPerPage;
	}
	
	public static String getProductImageDirUri() {
		return productImageDirUri;
	}
	
	public void setProductImageDirUri(String newProductImageDirUri) {
		productImageDirUri = newProductImageDirUri;
		this.logger.debug("productImageUri is set with value of " + newProductImageDirUri);
	}
	
	public static String getFileUploadResultPage() {
		return fileUploadResultPage;
	}
	
	public void setFileUploadResultPage(String newfileUploadResultPage) {
		fileUploadResultPage = newfileUploadResultPage;
		this.logger.debug("fileUploadResultPage is set with value of " + newfileUploadResultPage);
	}
	
	public String getDummyVariable() {
		return null;
	}
	
	/**
	 * Get the category name by category id.
	 * 
	 * @param id the category id
	 * @return the category name associated with the category id
	 */
	public String getCategoryName(String id) {
		if (id != null && this.categorySelectItems != null) {
			Iterator ite = this.categorySelectItems.iterator();
			
			while(ite.hasNext()) {
				SelectItem i = (SelectItem)ite.next();
				if (id.equals(i.getValue())) {
					return i.getLabel();
				}
			}
		}
		
		return null;
	}
}
