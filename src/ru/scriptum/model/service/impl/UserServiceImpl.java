/*
 * Scriptum Project
 */
package ru.scriptum.model.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.mail.javamail.JavaMailSender;

import ru.scriptum.model.dao.UserDao;
import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.CatalogException;
import ru.scriptum.model.exception.UsernameNotExistException;
import ru.scriptum.model.service.IUserService;
import ru.scriptum.model.util.EmailUtil;
import ru.scriptum.view.handler.UserBean;

//

/**
 * The implementation of the <code>UserService</code>.
 * </p>
 * Spring Framework is used to manage this service bean. Since this class is not
 * dependend on Spring API, it can be used outside the Spring IOC container.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 * @see IUserService
 */
public class UserServiceImpl implements IUserService {
	// the default sender email address
	private String defaultSenderAddress;

	// the default sender name
	private String defaultSenderName;

	private JavaMailSender mailSender;
	// smtpauth flag
	private boolean smtpAuthRequired;

	// the smtp server host address
	private String smtpHost;

	// smtpauth password
	private String smtpPassword;

	private String smtpPort;
	
	// smtpauth username
	private String smtpUsername;

	// the UserDao used
	private UserDao userDao;

	private Properties mailProperties;

	public User createUser(UserBean userBean) {
		User user = new User();
		try {
			BeanUtils.copyProperties(user, userBean);
			this.userDao.save(user);
			Object o = userDao.findByParameter("name", user.getName());
			return user;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean emailExists(String email) {
		return false;
	}

	public List getAllUsers() {
		return this.userDao.getAll();
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public User getUser(String username) {
		return userDao.getUser(username);
	}

	public boolean isSmtpAuthRequired() {
		return smtpAuthRequired;
	}

	/**
	 * @see IUserService#login(String, String)
	 */
	public User login(String name, String password) throws UsernameNotExistException {
		User user = this.userDao.getUser(name);

		if (user != null) {
			if (!user.getPassword().equals(password)) {
				user = null;
			}
		} else {
			throw new UsernameNotExistException("User does not exists");
		}
		return user;
	}

	/**
	 * @see IUserService#sendRegistrationEmail(String, String, String, String)
	 */
	public void sendRegistrationEmail(String receiverAddress,
			String receiverName, String userName, String sub, String signature)
			throws CatalogException {
		if (receiverAddress == null || receiverAddress.trim().equals("")) {
			receiverAddress = this.defaultSenderAddress;
		}

		if (receiverName == null || receiverName.trim().equals("")) {
			receiverName = this.defaultSenderName;
		}
		FacesContext context = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String requestUrl = request.getRequestURL().toString();
		int i = requestUrl.lastIndexOf("/");
		String requestBase = requestUrl.substring(0, i);

		String confirmationUrl;
		try {
			confirmationUrl = requestBase + "/login.jsf?user="
					+ URLEncoder.encode(userName, "UTF-8") + "&key="
					+ URLEncoder.encode(signature, "UTF-8");
			
			EmailUtil.sendHtmlEmail(this.mailSender, this.smtpHost, this.smtpUsername,
					this.smtpPassword, this.smtpAuthRequired, receiverAddress,
					receiverName, this.defaultSenderAddress,
					this.defaultSenderName, this.smtpPort, sub, confirmationUrl);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void setDefaultSenderAddress(String newDefaultSenderAddress) {
		this.defaultSenderAddress = newDefaultSenderAddress;
	}

	public void setDefaultSenderName(String newDefaultSenderName) {
		this.defaultSenderName = newDefaultSenderName;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setReceiverAddresses(List newDefaultReceiverAddresses) {
	}

	public void setSmtpAuthRequired(boolean smtpAuthRequired) {
		this.smtpAuthRequired = smtpAuthRequired;
	}

	public void setSmtpHost(String host) {
		this.smtpHost = host;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public void setSmtpPort(String smptPort) {
		this.smtpPort = smptPort;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public void setUserDao(UserDao newUserDao) {
		if (this.userDao == null)
			this.userDao = newUserDao;
	}

	public void updateUser(User user) {
		this.userDao.save(user);
	}

	public boolean userExists(String username) {
		if (this.userDao.getUser(username) == null) {
			return false;
		} else {
			return true;
		}
	}

	public Properties getMailProperties() {
		return mailProperties;
	}

	public void setMailProperties(Properties mailProperties) {
		this.mailProperties = mailProperties;
	}
}
