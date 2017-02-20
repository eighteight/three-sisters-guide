/*
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.scriptum.view.servicelocator.IServiceLocator;
import ru.scriptum.view.util.FacesUtils;

/**
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class BaseBean {
	public static final String NODE = "node";

	public static final String PROJECT = "project";

	public static final String TEXT = "text";

	public static final String IMAGE = "image";

	// the logger for this class
	protected final Log logger = LogFactory.getLog(this.getClass());

	// the service locator of the business services
	protected IServiceLocator serviceLocator;

	public BaseBean() {
		//serviceLocator = FacesUtils.getServiceLocatorBean();
	}

	public String getUrlBase(){
		HttpServletRequest request = FacesUtils.getRequest();
		int port = request.getServerPort();
		return "http://"+request.getServerName()+(port==80?"":":"+port);
	}

	protected String getParameterValue(String parameterName) {
		String ret = ((TemplateNavigationBean) FacesUtils.getManagedBean("navigationBean"))
				.getParameterValue(parameterName);
		if (ret== null) ret = FacesUtils.getRequestParameter(parameterName);
		return ret;
	}

	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	/**
	 * Used to initialize the managed bean.
	 * <p>
	 * Called after the service locator is set. It is a workaround.
	 * <p>
	 * Once the JSF bean management facility can support init method, the init
	 * method can be configured and called from the JSF implementation directly.
	 */
	protected void init() {
	}
	
	public void setServiceLocator(IServiceLocator newServiceLocator) {
		this.serviceLocator = newServiceLocator;
	}
}
