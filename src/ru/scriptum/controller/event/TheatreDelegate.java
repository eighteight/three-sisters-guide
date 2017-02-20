package ru.scriptum.controller.event;

import java.util.EventListener;
/**
 * Insert the type's description here.
 * Creation date: (5/9/2003 10:19:21 AM)
 * @author: Administrator
 */
public interface TheatreDelegate extends EventListener{
/**
 * Insert the method's description here.
 * Creation date: (6/26/2003 3:06:18 PM)
 * @return ru.scriptum.controller.event.TheatreEvent
 */
TheatreEvent [] getTheatreEvents();
}
