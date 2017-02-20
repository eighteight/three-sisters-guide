/*
 * Created on Apr 13, 2007
 *
 * boba
 */
package ru.scriptum.view.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.view.handler.dnd.DndBean;
import ru.scriptum.view.util.FacesUtils;

public class EditMapHandler extends BaseBean implements DropListener {
	private List<IElement> availableAtoms = new ArrayList<IElement>();

	private DndBean dndBean;

	private List<IElement> mapAtoms = new ArrayList<IElement>();

	private List types = new ArrayList();

	private IElement mapElement;

	public EditMapHandler() {
		super();
	}

	public String dragAction() {
		System.out.println("Bean.dragAction()");
		return null;
	}

	public String dropAction() {
		System.out.println("Bean.dropAction()");
		return null;
	}

	public List getAvailableAtoms() {
		return availableAtoms;
	}

	public List<IElement> getMapAtoms() {
		return mapAtoms;
	}

	public List getTypes() {
		return types;
	}

	public String reset() {

		mapAtoms = new ArrayList<IElement>();
		availableAtoms = new ArrayList<IElement>();
		if (mapElement != null)
			availableAtoms.addAll(mapElement.getChildren());

		return "OK";
	}

	public void save() {
		mapElement.setChildren(mapAtoms);
		try {
			serviceLocator.getBeanService().save(mapElement);
		} catch (BeanException e) {
			e.printStackTrace();
		}
	}

	public void moveAtom(IElement dragValue, Object dropValue) {
		availableAtoms.remove(dragValue);
		mapAtoms.add(dragValue);
	}

	public void processDrag(DragEvent dragEvent) {
		System.out.println("Bean.processDrag()");
	}

	public void processDrop(DropEvent event) {
		Long id = ((ElementBean) event.getDragValue()).getId();
		IElement element;
		element = (IElement) serviceLocator.getBeanService()
				.getPersistable(id);
		availableAtoms.add(element);
	}

	public void setAtoms(List atoms) {
		this.availableAtoms = atoms;
	}

	public void setDndBean(ru.scriptum.view.handler.dnd.DndBean dndBean) {
		this.dndBean = dndBean;
	}

	public void setMapAtoms(List<IElement> mapAtoms) {
		this.mapAtoms = mapAtoms;
	}

	public void actionListener(ActionEvent event) {
		Map attrs = event.getComponent().getAttributes();
		String template = (String) attrs.get("template");
		Long mapId = Long.parseLong(FacesUtils.getRequestParameter("mapId"));
		this.mapElement = (IElement) serviceLocator.getBeanService()
				.getPersistable(mapId);
		this.mapAtoms = this.mapElement.getChildren();
		IElement mapContainersParent = this.mapElement.getParent().getParent();

		for (ITemplateElement te : serviceLocator.getBeanService()
				.findByTemplateType(template)) {
			if (te.getParent().equals(mapContainersParent)) {
				availableAtoms = new ArrayList<IElement>();
				for (IElement el : te.getChildren()) {
					if (!mapAtoms.contains(el))
						availableAtoms.add(el);
				}
				break;
			}
		}
	}
}
