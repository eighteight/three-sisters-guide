/*
 * Created on Jun 29, 2006
 *
 * boba
 */
package ru.scriptum.data.impl;

import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.properties.IProperty;

public class TemplateElement extends Element implements ITemplateElement {

	private IProperty[] properties;

	private String templateType;

	public TemplateElement(String templateType) {
		this.templateType = templateType;
	}

	public boolean equals(Object other) {
		if (!(other instanceof ITemplateElement))
			return false;
		ITemplateElement otherElement = (ITemplateElement) other;
		return (getId().equals(otherElement.getId()));
	}

	public IProperty[] getProperties() {
		return properties;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void initName() {
	}

	public void setProperties(IProperty[] properties) {
		this.properties = properties;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
}
