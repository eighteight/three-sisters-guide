package ru.scriptum.controller.event;

/**
 * Insert the type's description here.
 * Creation date: (6/16/2003 1:46:09 PM)
 * @author: Administrator
 */
public class StopEvent extends TheatreEvent {
private final static int eventId = 1664;
/**
 * Insert the method's description here.
 * Creation date: (6/30/2003 3:32:16 PM)
 */
public StopEvent() {
    super();
}
/**
 * CreateStringInstance constructor comment.
 * @param obj java.lang.Object
 */
public StopEvent(Object obj) {
	super(obj);
}
/**
 * Insert the method's description here.
 * Creation date: (6/16/2003 2:12:26 PM)
 * @return int
 */
public int getEventId() {
	return eventId;
}
}
