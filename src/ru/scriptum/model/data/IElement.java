/*
 * Created on Nov 17, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ru.scriptum.model.data;

import java.util.List;

import ru.scriptum.model.domaindata.User;

public interface IElement extends IPersistable {

	boolean addChild(IElement child);

	List<IElement> getChildren();

	ISimplePoint getLocation();

	IElement getParent();

	User getUser();

	boolean hasChildren();

	boolean isRoot();

	void removeChild(IElement element);

	void setChildren(List<IElement> children);

	void setLocation(ISimplePoint p);

	void setParent(IElement parent);

	void setUser(User user);

}
