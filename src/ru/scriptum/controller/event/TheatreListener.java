package ru.scriptum.controller.event;

import java.util.EventListener;
/**
 * Insert the type's description here.
 * Creation date: (5/9/2003 10:19:21 AM)
 * @author: Administrator
 */
public interface TheatreListener extends EventListener{
/**
 * Insert the method's description here.
 * Creation date: (6/11/2003 3:38:15 PM)
 */
TheatreEvent [] onEvent(TheatreEvent event);
}
