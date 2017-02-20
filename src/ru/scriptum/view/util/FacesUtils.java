/*
 * Scriptum Project
 */
package ru.scriptum.view.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.NoSuchMessageException;

import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.UsernameNotExistException;
import ru.scriptum.model.service.IUserService;
import ru.scriptum.view.handler.ApplicationBean;
import ru.scriptum.view.handler.BeanNames;
import ru.scriptum.view.handler.ServiceLocatorBean;
import ru.scriptum.view.handler.SessionBean;
import ru.scriptum.view.handler.UserBean;
import ru.scriptum.view.servicelocator.IServiceLocator;

//

/**
 * Utility class for JavaServer Faces.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class FacesUtils {

	private static final String COOKIE_PARAMETER_WIDTH = "width";
	private static final String COOKIE_PARAMETER_HEIGHT = "height";
	public static final String CLIENT_RESOLUTION = "clientResolution";

	/**
	 * Add error message.
	 * 
	 * @param msg
	 *            the error message
	 */
	public static void addErrorMessage(String msg) {
		addErrorMessage(null, msg);
	}

	/**
	 * Add error message to a sepcific client.
	 * 
	 * @param clientId
	 *            the client id
	 * @param msg
	 *            the error message
	 */
	public static void addErrorMessage(String clientId, String msg) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
	}

	/**
	 * Add information message.
	 * 
	 * @param msg
	 *            the information message
	 */
	public static void addInfoMessage(String msg) {
		addInfoMessage(null, msg);
	}

	/**
	 * Add information message to a sepcific client.
	 * 
	 * @param clientId
	 *            the client id
	 * @param msg
	 *            the information message
	 */
	public static void addInfoMessage(String clientId, String msg) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
	}

	static boolean authorize(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		UserBean userBean = (UserBean) session.getAttribute(BeanNames.USER_BEAN);
	
		if (userBean != null && userBean.getLoggedIn()) {
			return true;
		}
		String [] userInfo = getCookiesUserInfo(req);
		if (userInfo == null) return false;
		
		if (userBean == null){
			userBean = new UserBean();
			session.setAttribute(BeanNames.USER_BEAN, userBean);
		}
		try {
			IUserService service = ServiceLocatorBean.getInstance().getUserService();
			if (service == null) return false;
			User user = service.login(userInfo[0], userInfo[1]);
			if (user == null) return false;
			BeanUtils.copyProperties(userBean, user);
			userBean.setLoggedIn(true);
		} catch (UsernameNotExistException e) {
			return false;
		} catch (IllegalAccessException e) {
			return false;
		} catch (InvocationTargetException e) {
			return false;
		}
	
		return true;
	}

	public static void deleteUserCookie() {
		HttpServletResponse httpResponse = getResponse();
		httpResponse.setContentType("text/html");
		HttpServletRequest httpRequest = getRequest();
		Cookie userCookie = getCookie(httpRequest, SecurityFilter.USER_NAME);
		userCookie.setMaxAge(0);
		userCookie.setDomain(httpRequest.getHeader("host"));
		userCookie.setPath(httpRequest.getContextPath());
		httpResponse.addCookie(userCookie);
	
		Cookie passwordCookie = getCookie(httpRequest, SecurityFilter.USER_PASSWORD);
		Object doma = passwordCookie.getDomain();
		passwordCookie.setMaxAge(0);
		passwordCookie.setDomain(httpRequest.getHeader("host"));
		passwordCookie.setPath(httpRequest.getContextPath());
		httpResponse.addCookie(passwordCookie);
	}

	/**
	 * Evaluate the integer value of a JSF expression.
	 * 
	 * @param el
	 *            the JSF expression
	 * @return the integer value associated with the JSF expression
	 */
	public static Integer evalInt(String el) {
		if (el == null) {
			return null;
		}

		if (isValueReference(el)) {
			Object value = getElValue(el);

			if (value == null) {
				return null;
			} else if (value instanceof Integer) {
				return (Integer) value;
			} else {
				return new Integer(value.toString());
			}
		} else {
			return new Integer(el);
		}
	}

	private static boolean isValueReference(String value) {
			if (value == null) return false;
			int start = value.indexOf("#{");
		if (start < 0) return false;
		
		int end = value.lastIndexOf('}');
			return (end >=0 && start < end);
	}


	public static Object getActionAttribute(ActionEvent event, String name) {
		return event.getComponent().getAttributes().get(name);
	}

	static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory) FactoryFinder
				.getFactory(FactoryFinder.APPLICATION_FACTORY);
		return appFactory.getApplication();
	}

	/**
	 * Get <code>ApplicationBean</code>.
	 * <p>
	 * Specific for this application.
	 * 
	 * @return the application bean
	 */
	public static ApplicationBean getApplicationBean() {
		return (ApplicationBean) getManagedBean(BeanNames.APPLICATION_BEAN);
	}

	public static String getApplicationRoot() {
		return getServletContext().getRealPath("/");
	}

	public static ClientResolution getClientResolution(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object cr = session.getAttribute(FacesUtils.CLIENT_RESOLUTION);
		if (cr != null && cr instanceof ClientResolution) {
			return (ClientResolution) cr;
		}
		Cookie cw = getCookie(req, COOKIE_PARAMETER_WIDTH);
		
		if (cw == null) return null;
		String width = cw.getValue();
		
		Cookie ch = getCookie(req, COOKIE_PARAMETER_HEIGHT);
		if (ch == null) return null;
		String height = cw.getValue();
		
		if (width == null || height == null){
			return null;
		}
		ClientResolution res = new ClientResolution(Integer.parseInt(width), Integer.parseInt(height));
		session.setAttribute(CLIENT_RESOLUTION, res);
		return res;
	}

	private static Cookie getCookie(HttpServletRequest req,
			String name) {
		Cookie cookies[] = req.getCookies();
		if (cookies == null) return null;
		for (int i = 0; i < cookies.length; i++) {
			String cookieName = cookies[i].getName();
			if (name.equals(cookieName)) {
				return cookies[i];
			}
		}
		return null;
	}

	public static String getContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	public static String[] getCookiesUserInfo() {
		HttpServletRequest request = getRequest();
		if (request == null) return null;
		return getCookiesUserInfo(request);
	}

	public static String[] getCookiesUserInfo(HttpServletRequest httpRequest) {
		Cookie uName = getCookie(httpRequest, SecurityFilter.USER_NAME);
		Cookie uPassword = getCookie(httpRequest, SecurityFilter.USER_PASSWORD);
		
		if (uName != null && uPassword != null /*&& uName.getMaxAge()>0 && uPassword.getMaxAge()>0*/) {
			String userName = uName.getValue();
			
			String userPassword = uPassword.getValue();
			
			if (userName != null && userPassword != null){
				return new String [] {userName, userPassword};
			}
		}

		return null;
	}

	private static Object getElValue(String el) {
		return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
	}

	private static String getJsfEl(String value) {
		return "#{" + value + "}";
	}

	public static Locale getLocale() {
		// first try to get it from the user profile
		User user = FacesUtils.getSessionBean().getUser();
		if (user != null)
			return new Locale(user.getLocale());

		// second, get it from the application context
		Locale locale = ServiceLocatorBean.getInstance().getLocaleContext().getLocale();
		if (locale != null)
			return locale;

		// last, get it from the users' browser
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIViewRoot viewRoot = context.getViewRoot();
			if (viewRoot != null)
				locale = viewRoot.getLocale();
		}
		return locale == null ? Locale.getDefault() : locale;
	}

	public static User getLoggedInUser() {
		User user =  FacesUtils.getSessionBean().getUser();
		if (user == null){
			UserBean userBean = (UserBean) FacesUtils.getRequest().getSession().getAttribute(BeanNames.USER_BEAN);
			if (!userBean.getLoggedIn()) return null;
			try {
				user = new User();
				BeanUtils.copyProperties(user, userBean);
				FacesUtils.getSessionBean().setUser(user);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}

	/**
	 * Get managed bean based on the bean name.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the managed bean associated with the bean name
	 */
	public static Object getManagedBean(String beanName) {
		Object o = getValueBinding(getJsfEl(beanName)).getValue(
				FacesContext.getCurrentInstance());

		return o;
	}

	public static FacesMessage getMessage(String messageId) {
		return new FacesMessage(getStringMessage(messageId));
	}

	public static HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context == null) return null;
		ExternalContext externalContext = context.getExternalContext();
		if (externalContext == null) return null;
		return (HttpServletRequest) externalContext.getRequest();
	}

	/**
	 * Get parameter value from request scope.
	 * 
	 * @param name
	 *            the name of the parameter
	 * @return the parameter value
	 */
	public static String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	public static Map getRequestParameters() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public static IServiceLocator getServiceLocatorBean() {
		return (IServiceLocator) getManagedBean(BeanNames.SERVICE_LOCATOR_BEAN);
	}

	/**
	 * Get servlet context.
	 * 
	 * @return the servlet context
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	/**
	 * Get <code>SessionBean</code>.
	 * <p>
	 * Specific for this applicaiton.
	 * 
	 * @return the session bean
	 */
	public static SessionBean getSessionBean() {
		return (SessionBean) getManagedBean(BeanNames.SESSION_BEAN);
	}

	public static String getStringMessage(String messageId) {
		String ret;
		try {
			ret = ServiceLocatorBean.getInstance().getApplicationContext()
					.getMessage(messageId, null, getLocale());
		} catch (NoSuchMessageException e) {
			ret = messageId;
		}
		return ret;
	}

	private static ValueBinding getValueBinding(String el) {
		return getApplication().createValueBinding(el);
	}

	/**
	 * Remove the managed bean based on the bean name.
	 * 
	 * @param beanName
	 *            the bean name of the managed bean to be removed
	 */
	public static void resetManagedBean(String beanName) {
		getValueBinding(getJsfEl(beanName)).setValue(
				FacesContext.getCurrentInstance(), null);
	}

	/**
	 * Store the managed bean inside the session scope.
	 * 
	 * @param beanName
	 *            the name of the managed bean to be stored
	 * @param managedBean
	 *            the managed bean to be stored
	 */
	public static void setManagedBeanInSession(String beanName,
			Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(beanName, managedBean);
	}

	public static void setUserCookie(String username, String password) {
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getRequest();
		setUserCookie(new String [] {username,password}, request,response);
	}

	public static void setUserCookie(String[] userInfo, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Cookie userCookie = new Cookie(SecurityFilter.USER_NAME, userInfo[0]);
		userCookie.setMaxAge(SecurityFilter.IDAGE);
		userCookie.setPath(httpRequest.getContextPath());
		httpResponse.addCookie(userCookie);
	
		Cookie passwordCookie = new Cookie(SecurityFilter.USER_PASSWORD, userInfo[1]);
		passwordCookie.setMaxAge(SecurityFilter.IDAGE);
		passwordCookie.setPath(httpRequest.getContextPath());
		httpResponse.addCookie(passwordCookie);
	}
}
