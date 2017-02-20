package ru.scriptum.controller.event;

/**
 * Insert the type's description here.
 * Creation date: (6/16/2003 1:46:09 PM)
 * @author: Administrator
 */
public class CreateStringInstanceEvent extends TheatreEvent {
private final static int eventId = 1662;
/**
 * CreateStringInstance constructor comment.
 * @param obj java.lang.Object
 */
public CreateStringInstanceEvent(Object obj) {
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
