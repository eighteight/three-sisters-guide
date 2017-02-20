package ru.scriptum.view.handler;

public class LoginManagerBean {

	
	private String previousAction;
	private String previousMethod;
	private String previousViewId;
	
	private boolean returnRequired;

	public boolean getLogin() {
		return true;
	}
	
	public LoginManagerBean(){
		System.out.println("initialized "+this);

	}

	public String getPreviousAction() {
		return previousAction;
	}

	public String getPreviousMethod() {
		return previousMethod;
	}

	public String getPreviousViewId() {
		return previousViewId;
	}

	public boolean isReturnRequired() {
		return returnRequired;
	}

	public void setPreviousAction(String actionNameCurrent) {
		previousAction = actionNameCurrent;
	}

	public void setPreviousMethod(String actionMethodCurrent) {
		previousMethod = actionMethodCurrent;
	}

	public void setPreviousViewId(String previousViewId) {
		this.previousViewId = previousViewId;
	}

	public void setReturnRequired(boolean b) {
		returnRequired = b ;
	}
}
