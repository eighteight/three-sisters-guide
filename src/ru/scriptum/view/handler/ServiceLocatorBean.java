/**
 * Scriptum Project
 */
package ru.scriptum.view.handler;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ru.scriptum.model.properties.PropertiesServerMapWrapper;
import ru.scriptum.model.properties.PropertyTemplateFactoryBean;
import ru.scriptum.model.service.IBeanService;
import ru.scriptum.model.service.IRandomService;
import ru.scriptum.model.service.IUserService;
import ru.scriptum.model.service.ImageService;
import ru.scriptum.view.builder.BeanBuilder;
import ru.scriptum.view.servicelocator.IServiceLocator;
import ru.scriptum.view.util.FacesUtils;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;

/**
 * The implementation of <code>IServiceLocator</code>.
 * <p>
 * This class is managed by the JSF managed bean facility, and is set with
 * application scope.
 * 
 * @author <a href="mailto:info@scriptum.ru">scriptum.ru</a>
 * @see IServiceLocator
 */
public class ServiceLocatorBean implements IServiceLocator {

	private static ServiceLocatorBean instance;

	public static ServiceLocatorBean getInstance() {
		if (instance == null) {
			instance = new ServiceLocatorBean();
		}
		return instance;
	}

	// the Spring application context
	private ApplicationContext applicationContext;

	private BeanBuilder beanBuilder;

	private IBeanService beanService;

	private ImageService imageService;

	// the cached user service
	private IUserService userService;

	private LocaleContext localeContext;

	// the logger for this class
	private Log logger = LogFactory.getLog(this.getClass());

	private ObjectContainer objectContainer;

	private String objectContainerType;

	private Map propertiesServerMap;

	private IRandomService randomService;

	/**
	 * Constructor.
	 * <p>
	 * The following steps being done:
	 * <ul>
	 * <li>retrieve Spring application context from servlet context.
	 * <li>look up <code>CatalogService</code> from Spring application
	 * context.
	 * <li>look up <code>UserService</code> from Spring application context.
	 * </ul>
	 */
	public ServiceLocatorBean() {
		instance = this;
		ServletContext context = FacesUtils.getServletContext();
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		this.userService = (IUserService) this
				.lookupService(USER_SERVICE_BEAN_NAME);
		this.imageService = (ImageService) this
				.lookupService(IMAGE_SERVICE_BEAN_NAME);
		this.beanService = (IBeanService) this
				.lookupService(BEAN_SERVICE_BEAN_NAME);
		// beanService.
		this.beanBuilder = (BeanBuilder) this
				.lookupService(BEAN_BUILDER_BEAN_NAME);

		this.randomService = (IRandomService) this
				.lookupService(RANDOM_SERVICE_BEAN_NAME);

		this.localeContext = (LocaleContext) this.lookupService("localeContext");
		initTemplates();
		this.logger.info("Service locator bean is initialized");
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public BeanBuilder getBeanBuilder() {
		return beanBuilder;
	}

	public IBeanService getBeanService() {
		return this.beanService;
	}

	public PropertyTemplateFactoryBean getConfigurationServer(String configName) {
		return (PropertyTemplateFactoryBean) propertiesServerMap
				.get(configName);
	}

	public ImageService getImageService() {
		return this.imageService;
	}

	public LocaleContext getLocaleContext() {
		return localeContext;
	}

	public ObjectContainer getObjectContainer() {
		return objectContainer;
	}

	public Map getPropertiesServerMap() {
		return propertiesServerMap;
	}

	public IRandomService getRandomService() {
		return randomService;
	}

	/**
	 * Get the <code>UserService</code>
	 * 
	 * @return the user service
	 */
	public IUserService getUserService() {
		return this.userService;
	}

	public void init() {
	}

	private void initObjectContainer() {
		if (objectContainerType.equals(DB4O_CLIENT)) {
			// use this for embedded API
			this.objectContainer = (ObjectContainer) this
					.lookupService(DB4O_CLIENT);
			return;
		}
		if (objectContainerType.equals(DB4O_FILE)) {
			this.objectContainer = (ObjectContainer) this
					.lookupService(DB4O_FILE);
			return;
		}
		if (objectContainerType.equals(DB4O_SERVER)) {
			// use this for embedded server
			this.objectContainer = ((ObjectServer) this
					.lookupService(DB4O_SERVER)).openClient();
		}
	}

	private void initTemplates() {
		Map<String, PropertyTemplateFactoryBean> templateBeans = getApplicationContext()
				.getBeansOfType(
						ru.scriptum.model.properties.PropertyTemplateFactoryBean.class);
		for (PropertyTemplateFactoryBean template : templateBeans.values()) {
			template.setLocale(FacesUtils.getLocale());
			template.setMessageSource(getApplicationContext());
			template.init();
		}
	}

	/**
	 * Lookup service based on service bean name.
	 * 
	 * @param serviceBeanName
	 *            the service bean name
	 * @return the service bean
	 */
	public Object lookupService(String serviceBeanName) {
		return applicationContext.getBean(serviceBeanName);
	}

	public void setObjectContainerType(String objectContainerType) {
		this.objectContainerType = objectContainerType;
		initObjectContainer();
	}

	public void setPropertiesServerMapWrapper(
			PropertiesServerMapWrapper newPropertiesServerMapWrapper) {
		newPropertiesServerMapWrapper.setApplicationContext(applicationContext);
		newPropertiesServerMapWrapper.init();
		this.propertiesServerMap = newPropertiesServerMapWrapper
				.getPropertiesServerMap();
	}
}
