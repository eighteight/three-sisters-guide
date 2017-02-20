/*
 * Scriptum Project
 */
package ru.scriptum.view.validator;

import javax.faces.validator.Validator;
import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;

import ru.scriptum.view.util.FacesUtils;

/**
 * Custom tag implementation class for the SelectedItemRangeValidator.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see SelectedItemsRangeValidator
 */
public class SelectedItemsRangeValidatorTag extends ValidatorTag {
	//the validator id registered in JSF
	private static String VALIDATOR_ID = "ru.scriptum.view.validator.SelectedItemsRange";
	
	//the minimum number of items to be selected
	private String minNum;
	
	//the maximum number of items to be selected
	private String maxNum;
	
	/**
	 * Default constructor.
	 */
	public SelectedItemsRangeValidatorTag() {
		this.setValidatorId(VALIDATOR_ID);
	}
	
	public void setMinNum(String newMinNum) {
		this.minNum = newMinNum;
	}
	
	public void setMaxNum(String newMaxNum) {
		this.maxNum = newMaxNum;
	}
	
	/**
	 * Create the validator associated with the tag.
	 * 
	 * @return the validator associated with the tag
	 */
	public Validator createValidator() throws JspException {
		SelectedItemsRangeValidator validator = (SelectedItemsRangeValidator)super.createValidator();
		
		Integer minValue = FacesUtils.evalInt(this.minNum);
		if (minValue != null) {
			validator.setMinNum(minValue.intValue());
		}
		
		Integer maxValue = FacesUtils.evalInt(this.maxNum);
		if (maxValue != null) {
			validator.setMaxNum(maxValue.intValue());
		}
		
		return validator;
	}
	
	public void release() {
		this.minNum = null;
		this.maxNum = null;
	}
}
