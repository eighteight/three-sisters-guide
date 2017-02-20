package ru.scriptum.view.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.view.util.FacesUtils;

public class TemplateNavigationBean extends BaseBean implements
		javax.faces.event.ActionListener {

	private Map<String, String> navigationMap;

	private Map<String, String> paramMap = new HashMap<String, String>();

	private SelectItem[] templateSelectItemList = null;

	private String templateType;

	private UICommand uiCommand;

	public Map<String, List> getChildTemplates() {
		Map<String, List> ret = new HashMap<String, List>();

		for (String templateType : navigationMap.keySet()) {
			ret.put(templateType, ServiceLocatorBean.getInstance()
					.getConfigurationServer(templateType).getChildTemplates());
		}
		return ret;
	}

	public Map<String, String> getNavigationMap() {
		return navigationMap;
	}

	public String getParameterValue(String paramName) {
		return paramMap.get(paramName);
	}

	public SelectItem[] getTemplateSelectItemList() {
		return templateSelectItemList;
	}

	public String getTemplateType() {
		return templateType;
	}

	public UICommand getUiCommand() {
		return uiCommand;
	}

	public void init() {
		templateSelectItemList = new SelectItem[navigationMap.size()];
		Iterator<String> templates = navigationMap.keySet().iterator();
		int i = 0;
		while (templates.hasNext()) {
			templateSelectItemList[i] = new SelectItem(FacesUtils
					.getStringMessage(templates.next()));
			i++;
		}
	}

	public String navigate() {
		String idStr = FacesUtils.getRequestParameter("id");
		String templateType = getParameterValue("templateType");
		String ret = navigationMap.get(templateType);
		if (idStr != null && idStr.length() > 0) {
			Long id = Long.parseLong(idStr);
			IElement element = (IElement) serviceLocator.getBeanService().getPersistable(id);
			if (element instanceof ITemplateElement) {
				templateType = ((ITemplateElement) element).getTemplateType();
				ret = navigationMap.get(templateType);
			}
		}
		return ret;
	}

	public void onEvent(javax.faces.event.ValueChangeEvent event) {
		templateType = (String) event.getNewValue();
	}

	private void parseCommandParameters() {
		if (uiCommand == null)
			return;
		paramMap = new HashMap<String, String>();
		Iterator params = uiCommand.getChildren().iterator();
		while (params.hasNext()) {
			UIParameter param = (UIParameter) params.next();
			String name = param.getName();
			String value = (String) param.getValue();
			paramMap.put(name, value);
			System.out.println("parse " + name + "="+ value + this);
		}
	}

	public void processAction(ActionEvent event)
			throws AbortProcessingException {

		UIComponent component = (UIComponent) event.getSource();
		setUiCommand((UICommand) component);
		parseCommandParameters();
	}

	public void setNavigationMap(Map<String, String> navigationMap) {
		this.navigationMap = navigationMap;
		init();
	}

	public void setTemplateSelectItemList(SelectItem[] beanSelectItemList) {
		this.templateSelectItemList = beanSelectItemList;
	}

	public void setUiCommand(UICommand uiCommand) {
		this.uiCommand = uiCommand;
	}
}
