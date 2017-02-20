/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlMessage;
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
public class HeteroBean extends TemplateBean {
	private static final int INPUT_COLUMN_NUMBER = 0;

	private UIData uiData;

	public String getDataId() {
		UIComponent component = (UIComponent) ((UIColumn) uiData.getChildren()
				.get(INPUT_COLUMN_NUMBER)).getChildren().get(0);
		return component.getId();
	}

	public UIData getUiData() {
		return uiData;
	}

	public void setUiData(UIData table) {
		this.uiData = table;
	}

	public void validateProperty(FacesContext context, UIComponent component,
			Object value) {
		int i = uiData.getRowIndex();
		if (properties[i].isUnique()) {
			UniquenessValidator v = new UniquenessValidator();
			v.setIndex(i);
			v.validate(context, component, value);
		}
	}

	public void setErrorMessage(HtmlMessage errorMessage) {
		super.setErrorMessage(errorMessage);
		if (getErrorMessage().getFor() == null)
			getErrorMessage().setFor(getDataId());
	}
}
