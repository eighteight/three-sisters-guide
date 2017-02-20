/*
 * Scriptum Project
 */
package ru.scriptum.view.handler.scriptum;

import java.util.List;

import ru.scriptum.view.handler.TemplateListHandler;


public class ProjectListHandler extends TemplateListHandler {
	
	public ProjectListHandler() {
		super();
	}

	@Override
	public List getBeanList() {
		this.templateType = "projectTemplate";
		init();
		return super.getBeanList();
	}
}
