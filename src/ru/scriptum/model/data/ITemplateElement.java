package ru.scriptum.model.data;

import ru.scriptum.model.properties.IProperty;

public interface ITemplateElement extends IElement{
	void initName();
	IProperty [] getProperties();
	
	void setProperties(IProperty[] properties);
	String getTemplateType();
}
