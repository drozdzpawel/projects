package com.graphiceditor.ob;

import android.graphics.RectF;

public class ComplexRect extends RectF implements Cloneable {

	public float startX, startY, endX, endY;
	public int color;
	public int rozmiar;
	public int type;

	public ComplexRect(float startX, float startY, float endX, float endY,
			int color, int rozmiar) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
		this.rozmiar = rozmiar;
	}

	public ComplexRect(int color, int rozmiar) {
		super();
		this.color = color;
		this.rozmiar = rozmiar;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
		left = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
		top = startY;
	}

	public float getEndX() {
		return endX;
	}

	public void setEndX(float endX) {
		this.endX = endX;
		right = endX;
	}

	public float getEndY() {
		return endY;
	}

	public void setEndY(float endY) {
		this.endY = endY;
		bottom = endY;
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

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
