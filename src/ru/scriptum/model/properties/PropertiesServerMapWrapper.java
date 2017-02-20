package ru.scriptum.model.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("serial")
public class PropertiesServerMapWrapper {
	ApplicationContext applicationContext;

	private Map<String, List<String>> parentChildTemplateTypeMap = new HashMap<String, List<String>>();

	Map<String, PropertyTemplateFactoryBean> propertiesServerMap = new HashMap<String, PropertyTemplateFactoryBean>();

	public PropertiesServerMapWrapper() {
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Map<String, List<String>> getParentChildTemplateTypeMap() {
		return parentChildTemplateTypeMap;
	}

	public Map<String, PropertyTemplateFactoryBean> getPropertiesServerMap() {
		return propertiesServerMap;
	}

	public void init() {
		for (String key : getApplicationContext().getBeanNamesForType(
				PropertyTemplateFactoryBean.class)) {
			PropertyTemplateFactoryBean value = (PropertyTemplateFactoryBean) lookupService(key);
			propertiesServerMap.put(key, value);
			parentChildTemplateTypeMap.put(key, value.getChildTemplates());
		}
	}

	private Object lookupService(String serviceBeanName) {
		return applicationContext.getBean(serviceBeanName);
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setPropertiesServerMap(
			Map<String, PropertyTemplateFactoryBean> propertiesServerMap) {
		this.propertiesServerMap = propertiesServerMap;
	}
}
