package com.graphiceditor.ob;

import android.graphics.Path;

public class Triangle {

	public float startX, startY, endX, endY;
	public int color;
	public int rozmiar;
	public int type;

	public Triangle(float startX, float startY, float endX, float endY,
			int color, int rozmiar) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
		this.rozmiar = rozmiar;
	}

	public Triangle(int color, int rozmiar) {
		super();
		this.color = color;
		this.rozmiar = rozmiar;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public float getEndX() {
		return endX;
	}

	public void setEndX(float endX) {
		this.endX = endX;
	}

	public float getEndY() {
		return endY;
	}

	public void setEndY(float endY) {
		this.endY = endY;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getRozmiar() {
		return rozmiar;
	}

	public void setRozmiar(int rozmiar) {
		this.rozmiar = rozmiar;
	}

	public Path getTriangle() {

		Path path = new Path();
		path.moveTo(startX, startY);
		path.lineTo(endX, endY);
		path.lineTo(endX - getDistance(), endY);
		path.lineTo(startX + 4, startY + 4);

		return path;
	}

	public float getDistance() {
		return (float) Math.sqrt(Math.pow(startX - endX, 2)
				+ Math.pow(startY - endY, 2));
	}

}
