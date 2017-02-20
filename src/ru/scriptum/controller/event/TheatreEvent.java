package ru.scriptum.controller.event;

/**
 * Insert the type's description here.
 * Creation date: (5/9/2003 11:04:24 AM)
 * @author: Administrator
 */
public abstract class TheatreEvent extends java.util.EventObject{

	int count = 0;
	private int eventId=  -1;
	public int type;


public TheatreEvent() {
    this(new Object());
}
public TheatreEvent(Object obj) {
    super(obj);
}
public int getCount() {
    return count;
}
/**
 * Insert the method's description here.
 * Creation date: (6/12/2003 11:28:08 AM)
 * @return int
 */
public int getEventId() {
	return eventId;
}
public void setCount(int count) {
    this.count = count;
}
/**
 * Insert the method's description here.
 * Creation date: (6/12/2003 11:28:08 AM)
 * @param newEventId int
 */
public void setEventId(int newEventId) {
	eventId = newEventId;
}
}
