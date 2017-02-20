/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.model.SelectItem;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.view.servicelocator.IServiceLocator;
import ru.scriptum.view.util.FacesUtils;

//

/**
 * ProductList backing bean.
 * <p>
 * It contains a list of <code>ProductBean</code>. It is used for the catalog
 * page and the productList page. It contains the pagination logic for the
 * catalog page.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class ListHandler extends BaseBean {
	// list of all the product backing beans
	private List beanList;

	// the cached product beans
	private Map productBeansMap;

	// the category id for the current catalog selection
	private String currentCategoryId;

	// the category name for the current catalog selection
	private String currentCategoryName;

	private SelectItem[] beanSelectItemList = null;

	/**
	 * Default constructor.
	 */
	public ListHandler() {
		super();
		this.beanList = new ArrayList();
		this.productBeansMap = new HashMap();
		String idd = FacesUtils.getRequestParameter("id");
	}

	/**
	 * Initializes the ProductListBean.
	 * <p>
	 * The following steps are being done:
	 * <ul>
	 * <li>retrieve all the products.
	 * <li>convert all the products to product beans.
	 * <li>pagination logic
	 * </ul>
	 * 
	 * @see BaseBean#init()
	 */
	protected void init() {
		try {
			List heteros = new ArrayList();
//			Long parent = getParentId();
//			if (parent>0){
//				heteros.add(this.serviceLocator.getBeanService().getHetero(parent));
//				
//			} else {
				//heteros = this.serviceLocator.getBeanService().getAll();
//			}

//			List selectItemList = new ArrayList();
			for (int i = 0; i < heteros.size(); i++) {
				IElement hetero = (IElement) heteros.get(i);

				Object elementBean = serviceLocator.getBeanBuilder().createElementBean(hetero);

				this.beanList.add(elementBean);

//				if (excludedId != elementBean.getId())
//					selectItemList.add(new SelectItem(elementBean.getId()
//							.toString(), elementBean.getName()));
			}
//			beanSelectItemList = selectItemList.size() > 0 ? (SelectItem[]) selectItemList
//					.toArray(new SelectItem[selectItemList.size()])
//					: null;
		} catch (Exception e) {
			String msg = "Could not initialize ListHandler";
			this.logger.error("Could not initialize ProductListBean", e);
			throw new FacesException(msg, e);
		}
	}

	/**
	 * Backing bean action to search products by category.
	 * 
	 * @return the navigation result
	 */
	public String searchByCategoryAction() {
		this.currentCategoryId = FacesUtils
				.getRequestParameter(RequestParamNames.TEMPLATE_TYPE);

		if (this.currentCategoryId == null || this.currentCategoryId.equals("")) {
			return NavigationResults.PRODUCT_LIST;
		} else {
			// catalog
			Map categoryProductBeans = (Map) this.productBeansMap
					.get(this.currentCategoryId);

			// set current category name
			this.currentCategoryName = FacesUtils.getApplicationBean()
					.getCategoryName(this.currentCategoryId);

			return NavigationResults.CATALOG;
		}
	}

	public List getBeanList() {
		try {
			List heteros = this.serviceLocator.getBeanService().getAll();
			for (int i = 0; i < heteros.size(); i++) {
				IElement hetero = (IElement) heteros.get(i);

				Object elementBean = serviceLocator.getBeanBuilder().createElementBean(hetero);

				this.beanList.add(elementBean);

//				if (excludedId != elementBean.getId())
//					selectItemList.add(new SelectItem(elementBean.getId()
//							.toString(), elementBean.getName()));
			}

		} catch (BeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.beanList;
	}

	public String getCurrentCategoryId() {
		return this.currentCategoryId;
	}

	public String getCurrentCategoryName() {
		return this.currentCategoryName;
	}

	public void setBeanList(List beanList) {
		this.beanList = beanList;
	}

	public SelectItem[] getBeanSelectItemList() {
		return beanSelectItemList;
	}

	public void setBeanSelectItemList(SelectItem[] beanSelectItemList) {
		this.beanSelectItemList = beanSelectItemList;
	}
	
	public void setServiceLocator(IServiceLocator newServiceLocator) {
		super.setServiceLocator(newServiceLocator);
	}
}
