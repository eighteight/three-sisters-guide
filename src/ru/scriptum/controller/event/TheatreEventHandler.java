package ru.scriptum.controller.event;

import java.util.List;
import java.util.Vector;

public class TheatreEventHandler {

	private List listeners = new Vector();

	private TheatreEvent event;

	public TheatreEventHandler(TheatreEvent e) {
		event = e;
		 EventClass er = EventClass.findByEventId(new Long(e
				.getEventId()));

		listeners = er.getWorkflowListeners();
	}

	public void sendEvent(TheatreEventController c) {
		for (int i = 0; i < listeners.size(); i++) {
//			try {
//				DomainClass dc = (DomainClass) listeners.get(i);
//				TheatreAdapter l = (TheatreAdapter) Class.forName(
//						dc.getName()).newInstance();
//				l.setClassId(dc.getClassId());
//
//				TheatreEvent[] we = l.handleTheatreEvent(event);
//				if (we != null && we.length > 0) {
//					for (int j = 0; j < we.length; j++) {
//						TheatreEventHandler wh = new TheatreEventHandler(
//								we[j], getSession());
//						c.getWfeHandlers().add(wh);
//					}
//				}
//			} catch (Exception e) {
//				System.out.println("Exception " + e.getMessage());
//			}
		}
	}
}
