package ru.scriptum.controller.event;

import java.util.Vector;

public class TheatreEventController{

	private Vector wfeHandlers = new Vector(); 

public TheatreEventController(TheatreEvent event) {
    addWfeHandler(new TheatreEventHandler(event));
}
/**
 * Insert the method's description here.
 * Creation date: (6/16/2003 3:25:04 PM)
 * @param newWfeController ru.scriptum.controller.event.TheatreEventController
 */
public void addWfeHandler(TheatreEventHandler newWfeHandler) {
    wfeHandlers.add(newWfeHandler);
}
public void execute() {

    while (wfeHandlers.size() > 0) {
        TheatreEventHandler wh = (TheatreEventHandler) wfeHandlers.remove(0);
        wh.sendEvent(this);
    }
}
/**
 * Insert the method's description here.
 * Creation date: (6/16/2003 3:25:04 PM)
 * @param newWfeController ru.scriptum.controller.event.TheatreEventController
 */
public Vector getWfeHandlers() {
    return wfeHandlers;
}

public static void main(String args[]) {

    TheatreEventController wec =
        new TheatreEventController(
            new CreateStringInstanceEvent("First String Instance"));
    wec.execute();
}
}
