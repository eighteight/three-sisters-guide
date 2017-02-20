/*
 * Created on Oct 24, 2006
 *
 * boba
 */
package ru.scriptum.view.handler;

import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

public class TemplateTreeNodeBase extends TreeNodeBase implements TreeNode {

	private String templateType;

	public TemplateTreeNodeBase(String type, String name, boolean b) {
		super(type, name, b);
	}

	public TemplateTreeNodeBase(String type, String name, String id,
			String thatTemplateType, boolean b) {
		super(type, name, id, b);
		this.templateType = thatTemplateType;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

}
