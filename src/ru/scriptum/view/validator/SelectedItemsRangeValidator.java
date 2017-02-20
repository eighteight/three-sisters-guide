/*
 * Scriptum Project
 */
package ru.scriptum.view.validator;

import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * The JSF validator class for SelectedItemRange validator.
 * <p>
 * This validator is to validate the number of the selected items.
 * Two parameters are associated with this validator:
 * <ul>
 * <li>minNum: the minimum number of the items to be selected.
 * <li>maxNum: the maximum number of the items to be selected.
 * </ul>
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class SelectedItemsRangeValidator implements Validator {
	//the minimum number of the items to be selected
	private int minNum = Integer.MIN_VALUE;
	
	//the maximum number of the items to be selected
	private int maxNum = Integer.MAX_VALUE;
	
	/**
	 * Main method to implement for <code>Validator</code>
	 */
	public void validate(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return;
		}
		
		List valueList = null;
		
		try {
			valueList = (List)value;
		} catch (Exception e) {
			throw new FacesException("UISelectManyValidator can only be attached to component UISelectMany.");
		}
		
		String msg = null;
		
		if (valueList.size() < this.minNum) {
			msg = "At least " + this.minNum + " items should be selected.";
		}
		else if (valueList.size() > this.maxNum) {
			msg = "At most " + this.maxNum + " items should be selected.";
		}
		
		if (msg != null) {
			FacesMessage message = 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
			throw new ValidatorException(message);
		}
	}
	
	public void setMinNum(int newMinNum) {
		this.minNum = newMinNum;
	}
	
	public void setMaxNum(int newMaxNum) {
		this.maxNum = newMaxNum;
	}
}
