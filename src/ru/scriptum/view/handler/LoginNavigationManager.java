package ru.scriptum.view.handler;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import ru.scriptum.view.util.FacesUtils;

public class LoginNavigationManager extends NavigationHandler {

	public static final String RETURN_BACK = "!returnBack";

	public static final String LOGIN_GLOBAL = "globalLogin";

	NavigationHandler _base;

	String actionNameCurrent;

	String actionMethodCurrent;

	public LoginNavigationManager(NavigationHandler base) {
		super();
		_base = base;

	}

	public void handleNavigation(FacesContext fc, String actionMethod,
			String actionName) {
		actionNameCurrent = actionName;
		actionMethodCurrent = actionMethod;
		LoginManagerBean loginManager = (LoginManagerBean) FacesUtils
				.getManagedBean("loginManager");

		if (RETURN_BACK.equals(actionNameCurrent)) {
			navigationCase4ReturningBack(fc, loginManager);
		}

		if (NavigationResults.DOES_NOT_EXIST.equals(actionNameCurrent)) {
			//navigationCase4Registration(fc, loginManager);
			_base.handleNavigation(fc, actionMethodCurrent, actionNameCurrent);
			return;
		}

		navigationCase4Login(fc, loginManager);

		_base.handleNavigation(fc, actionMethodCurrent, actionNameCurrent);
	}

	private void navigationCase4Registration(FacesContext fc,
			LoginManagerBean loginManager) {
		// restore the previous navigation position
		actionNameCurrent = loginManager.getPreviousAction();
		actionMethodCurrent = loginManager.getPreviousMethod();
		fc.getViewRoot().setViewId(loginManager.getPreviousMethod());
		loginManager.setReturnRequired(false);
	}

	private void navigationCase4Login(FacesContext fc,
			LoginManagerBean loginManager) {

		boolean loginStatus = loginManager.getLogin();

		if (!loginStatus && actionNameCurrent != null
				&& !actionNameCurrent.equals(LOGIN_GLOBAL)) {

			// store the current navigation position
			loginManager.setPreviousAction(actionNameCurrent);
			loginManager.setPreviousMethod(actionMethodCurrent);
			loginManager.setPreviousMethod(fc.getViewRoot().getViewId());
			loginManager.setReturnRequired(true);

			actionNameCurrent = LOGIN_GLOBAL;
		}
	}

	private void navigationCase4ReturningBack(FacesContext fc,
			LoginManagerBean loginManager) {
		if (loginManager.getLogin()) {
			// restore the previous navigation position
			actionNameCurrent = loginManager.getPreviousAction();
			actionMethodCurrent = loginManager.getPreviousMethod();
			fc.getViewRoot().setViewId(loginManager.getPreviousMethod());
			loginManager.setReturnRequired(false);
		}

	}

}
