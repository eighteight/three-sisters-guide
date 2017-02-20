package ru.scriptum.view.handler;

import java.util.ArrayList;
import java.util.List;

import ru.scriptum.data.impl.TemplateElement;
import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.view.util.FacesUtils;

public abstract class ElementBean extends BaseBean {

	protected List <TemplateElement>children = new ArrayList<TemplateElement>();

	protected IElement element;

	protected Long id;

	protected String name;

	protected Long parentId = null;

	public ElementBean() {
		super();
	}

	public List<TemplateElement> getChildren() {
		return children;
	}

	public Long getId() {
		if (id == null) {
			String idd = getParameterValue("id");
			if (idd == null) idd = 
				((TemplateNavigationBean) FacesUtils
					.getManagedBean("navigationBean")).getParameterValue("id");
			if (idd != null && idd.length() > 0)
				id = Long.parseLong(idd);
		}
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Long getParentId() {
		if (parentId != null) return parentId;
		String parentIdStr = ((TemplateNavigationBean) FacesUtils
				.getManagedBean("navigationBean")).getParameterValue("parentId");
		parentIdStr = FacesUtils.getRequestParameter("parentId");
		if (parentIdStr != null && parentIdStr.length() > 0){
			parentId = Long.parseLong(parentIdStr);
			return parentId;
		}
		if (element != null && element.getParent() != null){
			return element.getParent().getId();
		}
		return parentId;
	}

	public String getParentName() {
		parentId = getParentId();
		if (parentId != null) {
			IElement parent = (IElement) serviceLocator.getBeanService()
					.getPersistable(parentId);
			return parent == null ? "" : parent.getName();
		} else
			return "root";
	}

	public boolean isPersists() {
		if (id==null || id == 0) return false;
		IElement element = (IElement) serviceLocator.getBeanService().getPersistable(id);
		return (element == null ? false : element.getId() > 0);
	}

	public void removeChild(ITemplateElement templateElement) {
		children.remove(templateElement);
	}

	public void setChildren(List <TemplateElement>children) {
		this.children = children;
	}

	public void setId(Long newId) {
		if (this.id == newId) return;
		this.id = newId;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected void setParent(IElement element) {
		parentId = getParentId();

		if (parentId > 0) {
			IElement parent;
			parent = (IElement) serviceLocator.getBeanService().getPersistable(parentId);
			if (parent != null) {
				parent.addChild(element);
				element.setParent(parent);
			}
		}
	}

	public void setParentId(Long parent) {
		this.parentId = parent;
	}

	public void setParentName() {
	}
}
