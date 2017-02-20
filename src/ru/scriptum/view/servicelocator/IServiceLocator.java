/**
 * Scriptum Project
 */
package ru.scriptum.view.servicelocator;

import ru.scriptum.model.service.IBeanService;
import ru.scriptum.model.service.IRandomService;
import ru.scriptum.model.service.IUserService;
import ru.scriptum.model.service.ImageService;
import ru.scriptum.view.builder.BeanBuilder;

/**
 * Interface needs to be implemented by IServiceLocator.
 * <p>
 * IServiceLocator is used to lookup for business services.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public interface IServiceLocator {
	
	final String BEAN_SERVICE_BEAN_NAME = "beanService";
	final String DB4O_CLIENT = "db4oClient";
	final String DB4O_FILE = "db4oFile";
	final String DB4O_SERVER = "db4oServer";
	final String IMAGE_SERVICE_BEAN_NAME = "imageService";
	final String USER_SERVICE_BEAN_NAME = "userService";
	final String BEAN_BUILDER_BEAN_NAME = "beanBuilder";
	final String RANDOM_SERVICE_BEAN_NAME = "randomService";

	/**
	 * Get the <code>UserService</code>.
	 * 
	 * @return the user service
	 */
	public IUserService getUserService();
	
	public ImageService getImageService();

	public IBeanService getBeanService();
	
	public IRandomService getRandomService();

	public BeanBuilder getBeanBuilder();
}
