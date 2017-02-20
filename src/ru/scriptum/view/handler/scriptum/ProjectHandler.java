/*
 * Scriptum Project
 */
package ru.scriptum.view.handler.scriptum;

import java.util.ArrayList;
import java.util.List;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.view.handler.TemplateBean;

//

/**
 * Product backing bean.
 * <p>
 * Used by the createProduct page and editProduct page.
 * 
 * @author <a href="mailto:scriptum@gmail.com">scriptum</a>
 */
public class ProjectHandler extends TemplateBean {
	
	public ProjectHandler() {
		super();
	}

	List <IElement> atoms = new ArrayList<IElement>();

	public List<IElement> getAtoms() {
		for (IElement child: getChildren()){
			if (((ITemplateElement)child).getTemplateType().equals("mapContainerTemplate")){
				for (IElement grandChild: child.getChildren()){
					atoms.addAll(grandChild.getChildren());
					break;
				}
			}
		}
		return atoms;
	}

	public void setAtoms(List<IElement> atoms) {
		this.atoms = atoms;
	}

}
