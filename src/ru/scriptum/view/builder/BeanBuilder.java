/*
 * Scriptum Project
 */
package ru.scriptum.view.builder;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import ru.scriptum.data.impl.TemplateElement;
import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.model.exception.CatalogException;
import ru.scriptum.view.handler.BaseBean;
import ru.scriptum.view.handler.TemplateBean;

//

/**
 * The builder class for <code>ProductBean</code> and <code>Product</code>.
 * <p>
 * The backing beans are used view objects in the presentation tier. The
 * business objects are used in the business logic tier. This class is used to
 * convert in between the backing beans and business objects.
 * <p>
 * Commons BeanUtils is used.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see <a href="http://jakarta.apache.org/commons/beanutils/">Commons BeanUtils</a>
 */
public class BeanBuilder {

	private Map<String, String> elementBeanMap;

	public BeanBuilder() {
	}

	public static void populateBean(BaseBean heteroBean, IElement hetero)
			throws BeanException {
		try {
			BeanUtils.copyProperties(heteroBean, hetero);
		} catch (Exception e) {
			throw new BeanException("Could not populate GmapHandler "
					+ e.toString(), e);
		}
	}

	/**
	 * Create a new <code>ProductBean</code> based on the <code>Product</code>
	 * business object.
	 * 
	 * @param product
	 *            the product business object
	 * @return the new product bean
	 * @throws CatalogException
	 */
	public BaseBean createElementBean(IElement element) throws BeanException {
		BaseBean elementBean = createBean(element);

		try {
			BeanUtils.copyProperties(elementBean, element);
		} catch (Exception e) {
			throw new BeanException("Could not build bean " + e.toString(), e);
		}
		if (elementBean instanceof TemplateBean)
			((TemplateBean) elementBean).initName();
		return elementBean;
	}

	private BaseBean createBean(IElement element) {
		try {
			String className = elementBeanMap.get(element.getClass().getName());
			Class clasz = Class.forName(className);
			BaseBean bean = (BaseBean) clasz.newInstance();
			return bean;
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create a new <code>Product</code> based on the <code>ProductBean</code>
	 * 
	 * @param productBean
	 *            the product managed bean
	 * @return the new product business object
	 * @throws CatalogException
	 */
	public static ITemplateElement createHetero(BaseBean heteroBean,
			String templateType) throws BeanException {
		ITemplateElement templateElement = new TemplateElement(templateType);
		// IProperty[] properties = ServiceLocatorBean.getInstance()
		// .getConfigurationServer(templateType).getProperties();
		// templateElement.setProperties(properties);
		try {
			BeanUtils.copyProperties(templateElement, heteroBean);
		} catch (Exception e) {
			throw new BeanException("Could not build Hetero " + e.toString(), e);
		}
		return templateElement;
	}

	public Map getElementBeanMap() {
		return elementBeanMap;
	}

	public void setElementBeanMap(Map elementBeanMap) {
		this.elementBeanMap = elementBeanMap;
	}
}
