/*
 * Scriptum Project
 */
package ru.scriptum.view.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This Filter class handle the security of the application.
 * <p>
 * It should be configured inside the web.xml.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class SecurityFilter implements Filter {
	// the login page uri
	private static final String LOGIN_PAGE_URI = "login.jsf";

	static final String USER_NAME = "USERNAME";
	static final String USER_PASSWORD = "USERPASSWORD";
    static int IDAGE = 3600 * 24 * 365 * 3; // three years

	// the logger object
	private Log logger = LogFactory.getLog(this.getClass());

	// a set of restricted resources
	private Set <String>restrictedResources;

	private boolean isRestricted(String value, String contextPath) {
		
		for (String res: restrictedResources){
			if (res.equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

	public void destroy() {
	}

	/**
	 * Standard doFilter object.
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		this.logger.debug("doFilter");

		String contextPath = ((HttpServletRequest) req).getContextPath();
		String requestUri = ((HttpServletRequest) req).getRequestURI();

		this.logger.debug("contextPath = " + contextPath);
		this.logger.debug("requestUri = " + requestUri);
		
		String where = requestUri.replace(contextPath,"");

		if (!isRestricted(where, contextPath)){
			//no need to authorize
			chain.doFilter(req, res);
			return;
		}
		if ("/registerUser.jsf".equals(where)||"/login.jsf".equals(where)||"/message.jsf".equals(where)){
			chain.doFilter(req, res);
			return;
		}
		
		if (!FacesUtils.authorize((HttpServletRequest) req, (HttpServletResponse) res)) {
			this.logger.debug("authorization failed");
			((HttpServletRequest) req).getRequestDispatcher(LOGIN_PAGE_URI)
					.forward(req, res);
		} else {
			this.logger.debug("authorization succeeded");
			chain.doFilter(req, res);
		}
	}

	/**
	 * Initializes the Filter.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.restrictedResources = new HashSet<String>();
		this.restrictedResources.add("/graph.jsf");
	}
}
