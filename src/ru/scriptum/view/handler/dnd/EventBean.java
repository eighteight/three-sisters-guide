package ru.scriptum.view.handler.dnd;

import org.richfaces.component.Dropzone;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

import ru.scriptum.model.data.IElement;
import ru.scriptum.view.handler.EditMapHandler;

public class EventBean implements DropListener {
	private EditMapHandler editMapHandler;

	public EventBean(){
		super();
	}
	public void processDrop(DropEvent dropEvent) {
		Dropzone dropzone = (Dropzone) dropEvent.getComponent();
		editMapHandler
				.moveAtom((IElement) dropEvent.getDragValue(), dropzone
						.getDropValue());
	}
	public EditMapHandler getEditMapHandler() {
		return editMapHandler;
	}
	public void setEditMapHandler(EditMapHandler editMapHandler) {
		this.editMapHandler = editMapHandler;
	}
}