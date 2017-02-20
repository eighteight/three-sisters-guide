package ru.scriptum.data.impl;

import ru.scriptum.model.data.IPersistable;

public abstract class Persistable implements IPersistable {

	public void objectOnNew(com.db4o.ObjectContainer objectContainer) {
		setId(objectContainer.ext().getID(this));
	}

	public void objectOnActivate(com.db4o.ObjectContainer objectContainer){
		if (getId() > 0) return;
		setId(objectContainer.ext().getID(this));
	}
}
