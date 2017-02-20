/*
 * Scriptum Project
 */
package ru.scriptum.view.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This Filter class handles obtaining the client screen resolution.
 * <p>
 * It should be configured inside the web.xml.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class ClientResolutionFilter implements Filter {
	private static final String USER_RESOLUTION_COOKIE_SETUP_URI = "screenResolution.jsf";
	// the logger object
	private Log logger = LogFactory.getLog(this.getClass());


	public void destroy() {
	}

	/**
	 * Standard doFilter object.
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		this.logger.debug("doFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		ClientResolution clientResolution = FacesUtils.getClientResolution(httpRequest);

		if (clientResolution != null){
			chain.doFilter(req, res);
			return;
		} else {
			((HttpServletRequest) req).getRequestDispatcher(USER_RESOLUTION_COOKIE_SETUP_URI).forward(req, res);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
