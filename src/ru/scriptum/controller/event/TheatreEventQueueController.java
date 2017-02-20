package ru.scriptum.controller.event;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class TheatreEventQueueController implements Runnable, PhaseListener {

	private int timeOut = 20000;

	private static Thread thread = null;

	private boolean stop = false;

	private List listeners = new ArrayList();

	private TheatreEventQueue eventQueue = new TheatreEventQueue();

	static private TheatreEventQueueController _instance = null;

	public TheatreEventQueueController() {
		this(1000);
	}

	private TheatreEventQueueController(int timeOut) {
		this.timeOut = timeOut;
	}

	public void addListener(TheatreListener l) {
		listeners.add(l);
	}

	public void broadcastEvents() {
		while (!eventQueue.empty()) {
			TheatreEvent e = (TheatreEvent) eventQueue.pop();
			for (int i = 0; i < listeners.size(); i++) {
				((TheatreListener) listeners.get(i)).onEvent(e);
			}
		}
	}

	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 2:18:10
	 * PM)
	 * 
	 * @return ru.scriptum.controller.event.TheatreEventQueue
	 */
	private TheatreEventQueue getEventQueue() {
		return eventQueue;
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 4:08:47
	 * PM)
	 * 
	 * @return ru.scriptum.controller.event.TheatreEventController
	 */
	public static TheatreEventQueueController instance() {
		if (null == _instance) {
			_instance = new TheatreEventQueueController();
			_instance.startEventController();
		}

		return _instance;
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 2:20:16
	 * PM)
	 * 
	 * @param e
	 *            ru.scriptum.controller.event.TheatreEvent
	 */
	public synchronized void queueEvent(TheatreEvent e) {
		getEventQueue().add(e);
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 2:24:15
	 * PM)
	 */
	public void removeEvent(TheatreEvent e) {
		getEventQueue().remove(e);
	}

	public void removeListener(TheatreListener l) {
		listeners.remove(l);
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 4:41:51
	 * PM)
	 */
	public void requestStop() {
		stop = true;
	}

	public void run() {
		while (!stopRequested()) {
			if (thread != null) {

				try {
					thread.sleep(timeOut);
				} catch (Exception e) {
					System.out.println(e);
				}
				broadcastEvents();
			}
		}

		thread = null;
	}

	public void setTimeOut(int t) {
		timeOut = t;
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 2:18:10
	 * PM)
	 * 
	 * @param newWorkflowEventQueue
	 *            ru.scriptum.controller.event.TheatreEventQueue
	 */
	private void setEventQueue(TheatreEventQueue newWorkflowEventQueue) {
		eventQueue = newWorkflowEventQueue;
	}

	private void startEventController() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stopEventController() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	/**
	 * Insert the method's description here. Creation date: (5/9/2003 4:21:34
	 * PM)
	 * 
	 * @return boolean
	 */
	public boolean stopRequested() {
		return stop;
	}

	public void afterPhase(PhaseEvent event) {
		FacesContext con = event.getFacesContext();
		broadcastEvents();
	}

	public void beforePhase(PhaseEvent event) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.INVOKE_APPLICATION;
	}
}
