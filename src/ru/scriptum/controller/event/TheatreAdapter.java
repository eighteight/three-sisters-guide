package ru.scriptum.controller.event;

/**
 * Insert the type's description here. Creation date: (6/13/2003 4:41:14 PM)
 * 
 * @author: Administrator
 */
public class TheatreAdapter implements TheatreDelegate, TheatreListener {
	private int classId;

	/**
	 * TheatreAdapter constructor comment.
	 */
	public TheatreAdapter() {
		super();
	}

	/**
	 * TheatreAdapter constructor comment.
	 */
	public TheatreAdapter(int classId) {
		this();
		setClassId(classId);
	}

	/**
	 * Insert the method's description here. Creation date: (6/26/2003 3:56:55
	 * PM)
	 * 
	 * @return int
	 */
	public int getClassId() {
		return classId;
	}

	/**
	 * Insert the method's description here. Creation date: (6/26/2003 3:07:04
	 * PM)
	 * 
	 * @return ru.scriptum.controller.event.TheatreEvent
	 */
	public TheatreEvent[] getTheatreEvents() {
		EventClass[] ec = EventClass.findByDelegate(new Long(getClassId()));

		TheatreEvent[] we = new TheatreEvent[ec.length];
		for (int i = 0; i < ec.length; i++) {
			try {
				we[i] = (TheatreEvent) Class.forName(ec[i].getEventClass())
						.newInstance();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		return we;
	}

	public TheatreEvent[] onEvent(TheatreEvent event) {
		StringBuffer xml = new StringBuffer();
		xml.append("Handling Event " + event.toString() + "<Br>");
		TheatreEvent retEvent = null;
		if (event.getCount() < 3) {
			event.setCount(event.getCount() + 1);
			xml.append("Returning Event " + event.toString() + "<Br>");
			retEvent = event;
		} else {
			xml.append("Returning Stop Event<Br>");
			retEvent = new StopEvent(this);
		}
		return new TheatreEvent[] { retEvent };
	}

	/**
	 * Insert the method's description here. Creation date: (6/26/2003 3:56:55
	 * PM)
	 * 
	 * @param newClassId
	 *            int
	 */
	public void setClassId(int newClassId) {
		classId = newClassId;
	}
}
