/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import ru.scriptum.view.validator.UniquenessValidator;

//

/**
 * Product backing bean.
 * <p>
 * Used by the createProduct page and editProduct page.
 * 
 * @author <a href="mailto:scriptum@gmail.com">scriptum</a>
 */
public class TextBean extends TemplateBean {
	private static final int nameIndex = 0;
	public void validateProperty(FacesContext context, UIComponent component,
			Object value) {
		if (properties[nameIndex].isUnique()) {
			UniquenessValidator v = new UniquenessValidator();
			v.setIndex(nameIndex);
			v.validate(context, component, value);
		}
	}
}
