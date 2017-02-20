/*
 * Created on Oct 3, 2006
 *
 * boba
 */
package ru.scriptum.model.data;

public class SimplePoint implements ISimplePoint {

	private int x;

	private int y;

	public SimplePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
