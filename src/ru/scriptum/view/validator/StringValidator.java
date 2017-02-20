/*
 * Created on Nov 24, 2005
 *
 * boba
 */
package ru.scriptum.view.validator;

import javax.faces.FacesException;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public abstract class StringValidator implements Validator {
	
	protected String getValidatedString(Object value) throws ValidatorException{
		String valueString = null;
		
		try {
			valueString = (String) value;
		} catch (Exception e) {
			throw new FacesException("value (email) not of type String");
		}
		
		return valueString;
	}
}
