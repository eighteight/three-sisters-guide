/*
 * Scriptum Project
 */
package ru.scriptum.view.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Utility class for the presentation tier.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class ViewUtils {
	/**
	 * Convert a list to a set.
	 * 
	 * @param orig the list to be converted
	 * @return the set converted from the list
	 */
	public static Set convertToSet(List orig) {
		Set result = new HashSet();
		
		if (orig != null) {
			Iterator ite = orig.iterator();
			
			while (ite.hasNext()) {
				result.add(ite.next());
			}
		}
		
		return result;
	}
	
	/**
	 * Convert a set to a list.
	 * @param orig the set to be converted
	 * @return the list converted from the set
	 */
	public static List convertToList(Set orig) {
		List result = new ArrayList();
		
		if (orig != null) {
			Iterator ite = orig.iterator();
			
			while (ite.hasNext()) {
				result.add(ite.next());
			}
		}
		
		return result;
	}
}
