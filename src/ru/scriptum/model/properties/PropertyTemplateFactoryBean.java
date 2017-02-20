package ru.scriptum.model.properties;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Constructs templated bean servers.
 */
public class PropertyTemplateFactoryBean {

	private List<String> childTemplates;

	private Locale locale;

	private MessageSource messageSource;

	private IProperty[] properties;

	protected Object createInstance() throws Exception {
		return this;
	}

	public List<String> getChildTemplates() {
		return childTemplates;
	}

	public Locale getLocale() {
		return locale;
	}

	public IProperty[] getProperties() {
		IProperty[] ret = properties.clone();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = properties[i].clone();
		}
		return ret;
	}

	public void init() {
		if (properties == null)
			return;
		for (IProperty property : properties) {
			try {
				String name = messageSource.getMessage(property.getName(),
						null, getLocale());
				property.setName(name);
			} catch (NoSuchMessageException e) {
			}
		}
	}

	public void setChildTemplates(List<String> childTemplates) {
		this.childTemplates = childTemplates;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setProperties(IProperty[] properties) {
		this.properties = properties;
	}

	public void setProperty(IProperty property) {
		this.properties = new IProperty[] { property };
	}
}