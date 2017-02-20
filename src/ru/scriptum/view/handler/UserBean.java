/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.beanutils.BeanUtils;

import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.UsernameNotExistException;
import ru.scriptum.model.service.IUserService;
import ru.scriptum.view.util.ClientResolution;
import ru.scriptum.view.util.FacesUtils;

/**
 * User backing bean.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class UserBean extends ElementBean {
	private static final String STATUS_USER_CONFIRMED = "Confirmed";

	private static final String STATUS_USER_EMAIL_FAILED = "Email failed";

	private static final String STATUS_USER_EMAIL_SENT = "Unconfirmed";

	// the email of the current user
	private String email;

	private String locale;

	// whether or not the user is logged in
	private boolean loggedIn;

	// the name of the current user
	private String name;

	// the password of the current user
	private String password;

	// the confirmation password of the current user
	private String password2;

	private UIInput passwordInput;

	private Date regDate;

	private String signature;

	private User user;

	// the username of the current user
	private String username;

	private boolean justLoggedOut = false;

	private ClientResolution clientScreenResolution = ClientResolution.DEFAULT_RESOLUTION;

	/**
	 * Default constructor.
	 */
	public UserBean() {
		super();
		this.logger.debug("UserBean is created");
		//locale = FacesUtils.getLocale().toString();
	}

	private void clear() {
		this.username = null;
		this.password = null;
		this.password2 = null;
		this.name = null;
		this.email = null;
		this.loggedIn = false;
		FacesUtils.deleteUserCookie();
		FacesUtils.getSessionBean().setUser(null);
		justLoggedOut  = true;
	}
	
	public ClientResolution getClientResolution(){
		return this.clientScreenResolution;
	}

	public void setClientScreenResolution(String clientScreenResolution) {
		if (clientScreenResolution == null || "".equals(clientScreenResolution)) {
			this.clientScreenResolution = ClientResolution.DEFAULT_RESOLUTION;
		} else {
			String[] res = clientScreenResolution.split("#");
			this.clientScreenResolution = new ClientResolution(Integer
					.parseInt(res[0]), Integer.parseInt(res[1]));
		}
	}
	
	public Object getConfirmKey() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String key = (String) fc.getExternalContext().getRequestParameterMap()
				.get("key");
		if (key == null) return null;
		try {
			String keyDecoded = URLDecoder.decode(key, "UTF-8");

			String userName = (String) fc.getExternalContext().getRequestParameterMap().get("user");
			IUserService service = this.serviceLocator.getUserService();
			User user = service.getUser(userName);
			if (user == null) return null;
			String userSignature = user.getSignature();
			if (keyDecoded.equals(userSignature)) {
				user.setStatus(STATUS_USER_CONFIRMED);
				service.updateUser(user);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return key;
	}

	private String getDigitalSignature(Date regDate) {
		String urlConfirm = regDate.getTime()+""
				+ this.serviceLocator.getRandomService().nextDouble();
		return urlConfirm;
//		MessageDigest md = null;
//		try {
//			md = MessageDigest.getInstance("MD5");
//			byte[] dig = md.digest(urlConfirm.getBytes());
//			return new String(dig, "UTF-8");
//		} catch (NoSuchAlgorithmException e1) {
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} finally {
//			md.reset();
//		}
//		return null;
	}

	public String getEmail() {
		return this.email;
	}

	public String getLocale() {
		if (locale != null) return locale;
		locale = FacesUtils.getLocale().toString();
		return locale;
	}

	public boolean getLoggedIn() {
		if (!loggedIn && !justLoggedOut){
			//try to login using cookies
			String [] info = FacesUtils.getCookiesUserInfo();
			if (info == null) return false;
			this.username = info[0];
			this.password = info[1];
			loginAction();
			
		}
		return this.loggedIn;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPassword2() {
		return this.password2;
	}

	public UIInput getPasswordInput() {
		return passwordInput;
	}

	public Date getRegDate() {
		return regDate;
	}

	public String getSignature() {
		return signature;
	}

	public String getTemplateType() {
		return "";
	}

	public User getUser() {
		return user;
	}

	public String getUsername() {
		return this.username;
	}

	@SuppressWarnings("unchecked")
	public String loginAction() {

		try {
			user = serviceLocator.getUserService().login(this.username,	this.password);

			if (user != null) {
				this.loggedIn = true;
				BeanUtils.copyProperties(this, user);

				FacesUtils.getSessionBean().setUser(user);
				
				FacesUtils.setUserCookie(username, password);
				justLoggedOut = false;
				return NavigationResults.SUCCESS;
			} else {
				this.loggedIn = false;

				String msg = "Incorrect password ";
				FacesUtils.addErrorMessage(msg + ", please try again.");
				this.logger.debug(msg);

				return NavigationResults.RETRY;
			}
		} catch (UsernameNotExistException ue) {
			if (this.serviceLocator.getUserService().getAllUsers().size() == 0) {
				return NavigationResults.DOES_NOT_EXIST;
			}
			String msg = "Non-existing username ";
			FacesUtils.addErrorMessage(msg + ", please try again.");
			return NavigationResults.RETRY;
		} catch (Exception e) {
			FacesUtils.addInfoMessage("Could not log in user: Internal Error: "+e.getMessage());
			return NavigationResults.FAILURE;
		}
	}

	/**
	 * The backing bean action to logout a user.
	 * 
	 * @return the navigation result
	 */
	public String logoutAction() {
		this.clear();
		this.logger.debug("Logout successfully.");

		return NavigationResults.HOME;
	}

	
	public String manageAction(){
		IUserService userService = this.serviceLocator.getUserService();
		User user = userService.getUser(this.username);
		
		try {
			BeanUtils.copyProperties(user, this);
			serviceLocator.getBeanService().update(user);
		} catch (Exception e) {
			String msg = "Could not update user";
			this.logger.error(msg, e);
			FacesUtils.addErrorMessage(msg + ": Internal Error.");

			return NavigationResults.FAILURE;
		}

		return NavigationResults.SUCCESS;
	}

	public String registerAction() {

		Calendar calendar = new GregorianCalendar();
		regDate = calendar.getTime();
		IUserService userService = this.serviceLocator.getUserService();

		User user = userService.createUser(this);

		String sig = getDigitalSignature(regDate);

		try {
			userService.sendRegistrationEmail(user.getEmail(), user.getName(),user.getUsername(), "Registration confirmation", sig);
			user.setSignature(sig);
			user.setStatus(STATUS_USER_EMAIL_SENT);
			userService.updateUser(user);
		} catch (Exception e) {
			user.setStatus(STATUS_USER_EMAIL_FAILED);
			String msg = "Could not send email: internal error."+e.getMessage();
			FacesUtils.addErrorMessage(msg);
			return NavigationResults.FAILURE;
		}

		return NavigationResults.SUCCESS;
	}

	public void setConfirmKey(Object o) {

	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setKey(Object key) {
	}

	public void setLocale(String userLocale) {
		this.locale = userLocale;
	}

	public void setLoggedIn(boolean newLoggedIn) {
		this.loggedIn = newLoggedIn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public void setPasswordInput(UIInput passwordInput) {
		this.passwordInput = passwordInput;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public void validatePassword(FacesContext context, UIComponent component,
			Object value) {
		if (!passwordInput.getLocalValue().toString().equals(value.toString())) {
			FacesMessage message = FacesUtils.getMessage("passwordsDoNotMatch");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}
}
