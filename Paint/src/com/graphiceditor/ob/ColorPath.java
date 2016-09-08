package com.graphiceditor.ob;

import android.graphics.Path;

public class ColorPath {

	public Path path;
	public int color;
	public int rozmiar;

	public ColorPath() {

	}

	public ColorPath(Path path, int color, int rozmiar) {
		this.path = path;
		this.color = color;
		this.rozmiar = rozmiar;
	}

}
