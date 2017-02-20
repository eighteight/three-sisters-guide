package ru.scriptum.view.util;

public class ClientResolution {

	public static final ClientResolution DEFAULT_RESOLUTION = new ClientResolution(800,600);
	public final int height;
	public final int width;

	public ClientResolution(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
