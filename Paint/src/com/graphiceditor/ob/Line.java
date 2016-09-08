package com.graphiceditor.ob;

public class Line {
	public float startX, startY, endX, endY;
	public int color;
	public int rozmiar;
	public int rodzaj;

	public Line() {

	}

	public Line(float startX, float startY, float endX, float endY, int color,
			int rozmiar, int rodzaj) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
		this.rozmiar = rozmiar;
		this.rodzaj = rodzaj;
	}

	public Line(int color, int rozmiar, int rodzaj) {
		this.color = color;
		this.rozmiar = rozmiar;
		this.rodzaj = rodzaj;
	}

	public float[] getLine() {
		return new float[] { startX, startY, endX, endY };
	}

}
