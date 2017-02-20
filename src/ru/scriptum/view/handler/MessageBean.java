/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import ru.scriptum.view.util.FacesUtils;

/**
 * Message backing bean.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class MessageBean extends BaseBean {
	//the default message subject
	private static final String MESSAGE_SUBJECT = "Customer Mail";
	
	//the response message to the message sender
	private static final String RESPONSE_MESSAGE = "Thank you for sending us message!";
	
	//the sender firstname
	private String firstName;
	
	//the sender lastname
	private String lastName;
	
	//the sender email address
	private String emailAddress;
	
	//the message content
	private String content;
	
	/**
	 * Default constructor.
	 */
	public MessageBean() {
		super();
		this.logger.debug("MessageBean is created");
	}
	
	/**
	 * Backing bean action to submit the message.
	 * 
	 * @return the navigation result
	 */
	public String submitAction() {
		this.logger.debug("submitAction is invoked");
		
		try {
			//get UserService using IServiceLocator
			this.serviceLocator.getUserService().sendRegistrationEmail(this.emailAddress, this.firstName + " " + this.lastName, this.toString(), MESSAGE_SUBJECT, content);
		} catch (Exception e) {
			String msg = "Could not send email: internal error.";
			FacesUtils.addErrorMessage(msg);
			
			return NavigationResults.FAILURE;
		}
		
		FacesUtils.addInfoMessage(RESPONSE_MESSAGE);
		
		return NavigationResults.SUCCESS;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}
	
	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	public void setEmailAddress(String newEmailAddress) {
		this.emailAddress = newEmailAddress;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String newContent) {
		this.content = newContent;
	}
}
