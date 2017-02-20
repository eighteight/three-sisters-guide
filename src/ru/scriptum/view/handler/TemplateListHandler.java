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

import ru.scriptum.model.data.ITemplateElement;
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
public class TemplateListHandler extends BaseBean {
	// list of all the product backing beans
	protected List <TemplateBean>beanList;

	private SelectItem[] beanSelectItemList = null;

	// the category name for the current catalog selection
	private String currentCategoryName;

	// the cached product beans
	private Map productBeansMap;

	// the category id for the current catalog selection
	protected String templateType;

	/**
	 * Default constructor.
	 */
	public TemplateListHandler() {
		super();
		this.beanList = new ArrayList<TemplateBean>();
		this.productBeansMap = new HashMap();
	}

	public List getBeanList() {
		return this.beanList;
	}

	public SelectItem[] getBeanSelectItemList() {
		return beanSelectItemList;
	}

	public String getCurrentCategoryName() {
		return this.currentCategoryName;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	protected void init() {
		try {
			List<ITemplateElement> templateElements = this.serviceLocator
					.getBeanService().findByTemplateType(this.templateType);
			for (ITemplateElement templateElement : templateElements) {
				BaseBean elementBean = serviceLocator.getBeanBuilder().createElementBean(templateElement);
				this.beanList.add((TemplateBean) elementBean);
			}
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
		this.templateType = FacesUtils
				.getRequestParameter(RequestParamNames.TEMPLATE_TYPE);

		if (this.templateType == null || this.templateType.equals("")) {
			return NavigationResults.PRODUCT_LIST;
		} else {
			// catalog
			Map categoryProductBeans = (Map) this.productBeansMap
					.get(this.templateType);

			// set current category name
			this.currentCategoryName = FacesUtils.getApplicationBean()
					.getCategoryName(this.templateType);

			return NavigationResults.CATALOG;
		}
	}

	public void setBeanList(List beanList) {
		this.beanList = beanList;
	}

	public void setBeanSelectItemList(SelectItem[] beanSelectItemList) {
		this.beanSelectItemList = beanSelectItemList;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
		init();
	}
}
