package ru.scriptum.view.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import ru.scriptum.view.handler.ServiceLocatorBean;

public class EmailValidator extends ValidationExceptionCreator {
	
	private boolean emailExists(String email) {
		return ServiceLocatorBean.getInstance().getUserService().emailExists(email);
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		String valueString = getValidatedString(value);
		
		if (!valueString.matches(".+@.+\\.[a-z]+")) {
			createValidationException("invalid_email", component);
		}
		
		if (emailExists(valueString)) {
			createValidationException("email_found", component);
		}
	}
}
