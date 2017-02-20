package ru.scriptum.view.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import ru.scriptum.view.handler.ServiceLocatorBean;

public class UniquenessValidator extends ValidationExceptionCreator {

	private int index = -1;

	public UniquenessValidator() {
	}

	public void setIndex(int newIndex) {
		this.index = newIndex;
	}

	public boolean beanWithPropertyExists(String value) {
		if (index == -1)
			return ServiceLocatorBean.getInstance().getBeanService()
					.elementExists(value);
		else
			return ServiceLocatorBean.getInstance().getBeanService()
					.elementExists(value, index);
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		String valueString = getValidatedString(value);
		if (beanWithPropertyExists(valueString)) {
			createValidationException("nameExists", component);
		}
	}
}