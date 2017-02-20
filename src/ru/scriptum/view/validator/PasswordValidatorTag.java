/*
 * Scriptum Project
 */
package ru.scriptum.view.validator;

import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;

/**
 * Custom tag implementation class for the SelectedItemRangeValidator.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see SelectedItemsRangeValidator
 */
public class PasswordValidatorTag extends ValidatorTag {
	//the validator id registered in JSF
	private static String VALIDATOR_ID = "ru.scriptum.view.validator.Password";
	
	//the minimum number of items to be selected
	private String otherPassword;
	/**
	 * Default constructor.
	 */
	public PasswordValidatorTag() {
		this.setValidatorId(VALIDATOR_ID);
	}
	
	public void setOtherPassword(String newOtherPassword) {
		this.otherPassword = newOtherPassword;
	}	
	/**
	 * Create the validator associated with the tag.
	 * 
	 * @return the validator associated with the tag
	 */
	public Validator createValidator() throws JspException {
		PasswordValidator validator = (PasswordValidator)super.createValidator();
		
		if (otherPassword != null) {
			validator.setOtherPassword(otherPassword);
		}
		
		return validator;
	}
	
	public void release() {
		this.otherPassword = null;
	}
}
