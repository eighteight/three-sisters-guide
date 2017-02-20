/*
 * Created on Nov 17, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ru.scriptum.data.impl;

import java.util.ArrayList;
import java.util.List;

import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ISimplePoint;
import ru.scriptum.model.domaindata.User;

public class Element extends Persistable implements IElement {
	private List<IElement> children = new ArrayList<IElement>();

	private Long id = new Long(0);

	private ISimplePoint location;

	protected String name;

	private IElement parent = null;

	private User user;

	public boolean addChild(IElement child) {
		return children.add(child);
	}

	public List<IElement> getChildren() {
		return children;
	}

	public Long getId() {
		return id;
	}

	public ISimplePoint getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public IElement getParent() {
		return parent;
	}

	public User getUser() {
		return user;
	}

	public boolean hasChildren() {
		return children !=null && children.size()>0;
	}

	public boolean isRoot() {
		return parent==null;
	}

	public void removeChild(IElement element) {
		children.remove(element);
	}

	public void setChildren(List<IElement> children) {
		this.children = children;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocation(ISimplePoint p) {
		this.location = p;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(IElement parentId) {
		this.parent = parentId;
	}

	public void setUser(User thatUser) {
		this.user = thatUser;
	}

	public String toString() {
		return getName();
	}
}
