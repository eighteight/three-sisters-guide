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

public class SetCharacterEncodingFilter implements Filter {

	protected String encoding;

	protected FilterConfig filterConfig;

	protected boolean ignore;

	public SetCharacterEncodingFilter() {
		encoding = null;
		filterConfig = null;
		ignore = true;
	}

	public void destroy() {
		encoding = null;
		filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (ignore || request.getCharacterEncoding() == null) {
			String encoding = selectEncoding(request);
			if (encoding != null){
				request.setCharacterEncoding(encoding);
				//response.setCharacterEncoding(encoding);
			}
		}
		if (chain != null) chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			ignore = true;
		else if (value.equalsIgnoreCase("true"))
			ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			ignore = true;
		else
			ignore = false;
	}

	protected String selectEncoding(ServletRequest request) {
		return encoding;
	}
}