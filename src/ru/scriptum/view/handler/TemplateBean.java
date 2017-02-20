package ru.scriptum.view.handler;

import java.util.List;

import javax.faces.component.html.HtmlMessage;

import org.apache.commons.beanutils.BeanUtils;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.model.properties.HeteroPropertySource;
import ru.scriptum.model.properties.IProperty;
import ru.scriptum.model.service.IBeanService;
import ru.scriptum.view.builder.BeanBuilder;
import ru.scriptum.view.util.FacesUtils;

public class TemplateBean extends ElementBean /* implements ITemplateElement */{

	private HtmlMessage errorMessage;

	protected IProperty[] properties;

	protected String templateType;

	public TemplateBean() {
		super();
		String idd = FacesUtils.getRequestParameter("id");
		if (idd != null && idd.length() > 0) {
			id = Long.parseLong(idd);
		}
	}

	/**
	 * Backing bean action to create a new product.
	 * 
	 * @return the navigation result
	 */
	public String actionCreate() {
		try {
			element = serviceLocator.getBeanService().findByName(getName());
			
			if (element == null){
				element = BeanBuilder.createHetero(this, templateType);
			}
			setParent(element);
			User user = FacesUtils.getLoggedInUser();
			element.setUser(user);
			serviceLocator.getBeanService().save(element);
			this.id = element.getId();
		} catch (BeanException de) {
			String msg = "GmapHandler id already exists";
			this.logger.info(msg);
			FacesUtils.addErrorMessage(msg);

			return NavigationResults.RETRY;
		} catch (Exception e) {
			String msg = "Could not save bean";
			this.logger.error(msg, e);
			FacesUtils.addErrorMessage(msg + ": Internal Error");

			return NavigationResults.FAILURE;
		}
		String msg = "TemplateBean " + getName() + " was created successfully.";

		FacesUtils.addInfoMessage(msg);

		return NavigationResults.SUCCESS;
	}

	/**
	 * Backing bean action to delete product.
	 * 
	 * @return the navigation result
	 */
	public String actionDelete() {
		try {
			IBeanService service = serviceLocator.getBeanService();

			ITemplateElement templateElement = (ITemplateElement) service.getPersistable(getId());

			if (templateElement == null) return NavigationResults.DOES_NOT_EXIST;

			IElement parent = templateElement.getParent();

			if (parent != null) {
				parent.removeChild(templateElement);
				service.save(parent);
			}

			service.delete(templateElement);

			FacesUtils.resetManagedBean(BeanNames.HETERO_BEAN);
		} catch (Exception e) {
			String msg = "Could not delete bean. ";
			this.logger.error(msg, e);
			FacesUtils.addErrorMessage(null, msg + "Internal Error.");

			return NavigationResults.FAILURE;
		}
		String msg = "GmapHandler with id of " + this.id
				+ " was deleted successfully.";
		FacesUtils.addInfoMessage(msg);
		this.id = null;

		return NavigationResults.DELETED;
	}

	/**
	 * Backing bean action to update product.
	 * 
	 * @return the navigation result
	 */
	public String actionUpdate() {
		if (serviceLocator.getBeanService().otherElementExists(id, getName())) {
			FacesUtils.addErrorMessage("Other bean with this name exists");
			return NavigationResults.SUCCESS;
		}

		try {
			ITemplateElement element = (ITemplateElement) serviceLocator.getBeanService().getPersistable(getId());
			//template type is not changeable
			setTemplateType(element.getTemplateType());
			//save the children
			List<IElement> children = element.getChildren();
			BeanUtils.copyProperties(element, this);
			element.setChildren(children);
			serviceLocator.getBeanService().update(element);

		} catch (Exception e) {
			String msg = "Could not update bean";
			this.logger.error(msg, e);
			FacesUtils.addErrorMessage(msg + ": Internal Error.");

			return NavigationResults.FAILURE;
		}

		String msg = "Element id = " + this.id + " updated successfully.";
		FacesUtils.addInfoMessage(msg);

		return NavigationResults.SUCCESS;
	}

	public HtmlMessage getErrorMessage() {
		return errorMessage;
	}

	public String getName() {
		if (name == null || name.length() == 0)
			initName();
		return this.name;
	}

	public IProperty[] getProperties() {
		if (id!=null) {
			ITemplateElement element = (ITemplateElement) serviceLocator.getBeanService().getPersistable(id);
			if (element!= null) properties = element.getProperties();
		} 
		
		if (properties == null) {
			templateType = getParameterValue("templateType");
			if (templateType != null && templateType.length() > 0) {
				IProperty[] properties = ServiceLocatorBean.getInstance()
						.getConfigurationServer(templateType).getProperties();
				setProperties(properties);
			}
		}
		return properties;
	}

	public String getTemplateType() {
		return templateType;
	}

	/**
	 * Initializes ProductBean.
	 * 
	 * @see BaseBean#init()
	 */
	protected void init() {
		try {
			if (this.id != null) {
				this.element = (IElement) serviceLocator.getBeanService().getPersistable(this.id);
				BeanBuilder.populateBean(this, element);
			}
		} catch (BeanException ce) {
			String msg = "Could not retrieve GmapHandler with id of " + this.id;
			this.logger.debug(msg, ce);
			// throw new FacesException(msg, ce);
		}
	}

	public void initName() {
		if (properties != null) {
			name = properties[0].getValue();
		}
	}

	public void onEvent(javax.faces.event.ValueChangeEvent event) {
		templateType = (String) event.getNewValue();
		if (templateType != null) {
			setProperties((new HeteroPropertySource())
					.getProperties(templateType));
		}
	}

	public void removeChild(IElement element) {
		children.remove(element);
	}

	public void setErrorMessage(HtmlMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setProperties(IProperty[] properties) {
		this.properties = properties;
		initName();
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String toString() {
		return "id=" + this.id + " name=" + this.name;
	}
}
