/*
 * Created on Nov 24, 2005
 *
 * boba
 */
package ru.scriptum.view.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class CaptchaValidator extends ValidationExceptionCreator {

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String valueString = getValidatedString(value);
		String o = (String) context.getExternalContext().getSessionMap().get(
				nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY);

		if (!o.equals(valueString)) {
			createValidationException("turingTestFailed", component);
		}
	}
}
