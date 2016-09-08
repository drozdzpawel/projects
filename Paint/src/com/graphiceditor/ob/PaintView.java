package com.graphiceditor.ob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {

	Paint paint = new Paint();
	public int color;
	private int rozmiar;
	private int rodzajOperacji;
	private int rodzajLini;

	private ArrayList<Object> objectList = new ArrayList<Object>();

	private Path actualPath = new Path();
	private Line actualLine;
	private PathEffect normalEffect;
	private ComplexRect actuComplexRect;
	private Triangle actualTriangle;
	private Point actuPoint;
	private DashPathEffect dashPath = new DashPathEffect(new float[] { 5, 5 },
			1);

	public ComplexRect actuSelectionRect;
	public ComplexRect selectionRectCpy;
	public Bitmap selectedBitmap;

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDrawingCacheEnabled(true);
		color = Color.BLUE;
		rozmiar = 20;
		rodzajOperacji = 0;
		rodzajLini = 0;
		paint.setAntiAlias(true);
		paint.setColor(color);
		paint.setStyle(Paint.Style.STROKE);
		normalEffect = paint.getPathEffect();
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeWidth(rozmiar);
		objectList.add(new ColorPath(actualPath, color, rozmiar));
		actualLine = new Line(color, rozmiar, rodzajLini);
		actuComplexRect = new ComplexRect(color, rozmiar);
		actualTriangle = new Triangle(color, rozmiar);
		actuSelectionRect = new ComplexRect(Color.YELLOW, rozmiar);
		actuPoint = new Point(0, 0);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float pointX = event.getX();
		float pointY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (rodzajOperacji == 0)
				actualPath.moveTo(pointX, pointY);
			else if (rodzajOperacji == 1) {
				actualLine.startX = pointX;
				actualLine.startY = pointY;
			} else if (rodzajOperacji == 2) {
				actuComplexRect.setStartX(pointX);
				actuComplexRect.setStartY(pointY);
				actuComplexRect.type = 0;
			} else if (rodzajOperacji == 3) {
				actuComplexRect.setStartX(pointX);
				actuComplexRect.setStartY(pointY);
				actuComplexRect.type = 1;
			} else if (rodzajOperacji == 4) {
				actualTriangle.setStartX(pointX);
				actualTriangle.setStartY(pointY);
				actualTriangle.type = 1;
			} else if (rodzajOperacji == 5) {
				actualTriangle.setStartX(pointX);
				actualTriangle.setStartY(pointY);
				actualTriangle.type = 0;
			} else if (rodzajOperacji == 6 && actuSelectionRect != null) {
				actuSelectionRect.setStartX(pointX);
				actuSelectionRect.setStartY(pointY);
				actuComplexRect.type = 0;
			} else if (rodzajOperacji == 7) {
				actuPoint.set((int) pointX, (int) pointY);
			}
			return true;
			
		case MotionEvent.ACTION_MOVE:
			if (rodzajOperacji == 0)
				actualPath.lineTo(pointX, pointY);
			else if (rodzajOperacji == 1) {
				actualLine.endX = pointX;
				actualLine.endY = pointY;
			} else if (rodzajOperacji == 2) {
				actuComplexRect.setEndX(pointX);
				actuComplexRect.setEndY(pointY);
			} else if (rodzajOperacji == 3) {
				actuComplexRect.setEndX(pointX);
				actuComplexRect.setEndY(pointY);
			} else if (rodzajOperacji == 4 || rodzajOperacji == 5) {
				actualTriangle.setEndX(pointX);
				actualTriangle.setEndY(pointY);
			} else if (rodzajOperacji == 6 && actuSelectionRect != null) {
				actuSelectionRect.setEndX(pointX);
				actuSelectionRect.setEndY(pointY);
			} else if (rodzajOperacji == 7) {
				actuPoint.set((int) pointX, (int) pointY);
			}
			break;
		
		case MotionEvent.ACTION_UP:
			if (rodzajOperacji == 0) {
				objectList.add(new ColorPath(actualPath, color, rozmiar));
				actualPath = new Path();
			} else if (rodzajOperacji == 1) {
				actualLine.color = color;
				objectList.add(actualLine);
				actualLine = new Line(color, rozmiar, rodzajLini);
			} else if (rodzajOperacji == 2) {
				actuComplexRect.color = color;
				objectList.add(actuComplexRect);
				actuComplexRect = new ComplexRect(color, rozmiar);
			} else if (rodzajOperacji == 3) {
				actuComplexRect.color = color;
				objectList.add(actuComplexRect);
				actuComplexRect = new ComplexRect(color, rozmiar);
			} else if (rodzajOperacji == 4 || rodzajOperacji == 5) {
				actualTriangle.color = color;
				objectList.add(actualTriangle);
				actualTriangle = new Triangle(color, rozmiar);
			}
			break;

		default:
			return false;
		}

		postInvalidate();
		return true;

	}

	@Override
	protected void onDraw(Canvas canvas) {
	
		for (Object ob : objectList) {
			paint.setPathEffect(normalEffect);
		
			if (ob instanceof ColorPath) {
				ColorPath tmpPath = (ColorPath) ob;
				paint.setColor(tmpPath.color);
				paint.setStrokeWidth(tmpPath.rozmiar);
				canvas.drawPath(tmpPath.path, paint);
			}
		
			else if (ob instanceof Line) {
				Line ln = (Line) ob;
				paint.setColor(ln.color);
				paint.setStrokeWidth(ln.rozmiar);
				if (ln.rodzaj == 0)
					paint.setPathEffect(normalEffect);
				else
					paint.setPathEffect(dashPath);

				canvas.drawLines(ln.getLine(), paint);
			} else if (ob instanceof ComplexRect) {
				ComplexRect complexRect = (ComplexRect) ob;
				paint.setColor(complexRect.getColor());
				paint.setStrokeWidth(complexRect.getRozmiar());
			
				if (complexRect.type == 0)
					canvas.drawRect(complexRect, paint);
				else if (complexRect.type == 1)
					canvas.drawOval(complexRect, paint);
			} else if (ob instanceof Triangle) {
				Triangle triangle = (Triangle) ob;
				paint.setColor(triangle.getColor());
				paint.setStrokeWidth(triangle.getRozmiar());
				canvas.drawPath(triangle.getTriangle(), paint);
			} else if (ob instanceof Bitmap) {
				canvas.drawBitmap((Bitmap) ob, 0, 0, paint);
			}
			
			paint.setColor(color);
			paint.setStrokeWidth(rozmiar);
			paint.setPathEffect(normalEffect);
			canvas.drawPath(actualPath, paint);
		
			if (actualLine.rodzaj == 0)
				paint.setPathEffect(normalEffect);
			else
				paint.setPathEffect(dashPath);
			canvas.drawLines(actualLine.getLine(), paint);
			paint.setPathEffect(normalEffect);
			if (actuComplexRect.type == 0)
				canvas.drawRect(actuComplexRect, paint);
			else if (actuComplexRect.type == 1)
				canvas.drawOval(actuComplexRect, paint);

			if (actualTriangle.type == 1)
				canvas.drawPath(actualTriangle.getTriangle(), paint);
			if (actuSelectionRect != null) {
				ComplexRect complexRect = actuSelectionRect;
				paint.setColor(complexRect.getColor());
				paint.setStrokeWidth(complexRect.getRozmiar());
				canvas.drawRect(complexRect, paint);
			}
			if (rodzajOperacji == 7) {
				paint.setColor(Color.YELLOW);
				canvas.drawPoint(actuPoint.x, actuPoint.y, paint);
			}
		}
	}


	public void setColor(int color) {
		this.color = color;
		paint.setColor(color);
		switch (rodzajOperacji) {
		case 0:
			actualPath = new Path();
			objectList.add(new ColorPath(actualPath, color, rozmiar));
			break;
		case 1:
			actualLine = new Line(color, rozmiar, rodzajLini);
			break;

		default:
			actuComplexRect = new ComplexRect(color, rozmiar);
			break;
		}
		postInvalidate();
	}


	public void setRozmiar(int rozmiar) {
		this.rozmiar = rozmiar;
		paint.setStrokeWidth(rozmiar);
		switch (rodzajOperacji) {
		case 0:
			actualPath = new Path();
			objectList.add(new ColorPath(actualPath, color, rozmiar));
			break;
		case 1:
			actualLine = new Line(color, rozmiar, rodzajLini);
			objectList.add(new Line(color, rozmiar, rodzajLini));
			break;
		default:
			break;
		}

		postInvalidate();

	}

	public void setRodzajLini(int rodzajLini) {
		this.rodzajLini = rodzajLini;
		actualLine = new Line(color, rozmiar, rodzajLini);
	}

	public int getRozmiar() {
		return rozmiar;
	}

	public int getRodzajOperacji() {
		return rodzajOperacji;
	}

	public void setRodzajOperacji(int rodzajOperacji) {
		this.rodzajOperacji = rodzajOperacji;
	}


	public void clearPaintView() {
		objectList = new ArrayList<Object>();
		actualPath = new Path();
		actualLine = new Line(color, rozmiar, rodzajLini);
		actualTriangle = new Triangle(color, rozmiar);
		actuComplexRect = new ComplexRect(color, rozmiar);
		postInvalidate();
	}


	public void saveToFile() {

		Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(b);
		Drawable bgDrawable = getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		draw(canvas);
		File myDir = new File("mnt/sdcard/image");
		File file = new File(myDir, "image" + new Date().getSeconds() + ".jpg");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			b.compress(CompressFormat.JPEG, 95, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap saveBackground() {
		Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(b);
		Drawable bgDrawable = getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		draw(canvas);
		clearPaintView();
		objectList.add(b);
		postInvalidate();
		return b;
	}

	public void copy(Object rect) {
		selectionRectCpy = (ComplexRect) rect;
		actuSelectionRect = null;
		postInvalidate();
		Bitmap bmp = saveBackground();
		selectedBitmap = Bitmap
				.createBitmap(bmp, (int) selectionRectCpy.getStartX(),
						(int) selectionRectCpy.getStartY(),
						(int) selectionRectCpy.width(),
						(int) selectionRectCpy.height());

		clearPaintView();
		objectList.add(bmp);
		postInvalidate();
		rodzajOperacji = 7;
	}

	private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
		Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(),
				bmp1.getHeight(), bmp1.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		canvas.drawBitmap(bmp1, new Matrix(), null);
		canvas.drawBitmap(bmp2, actuPoint.x, actuPoint.y, null);
		return bmOverlay;
	}

	public void paste() {
		rodzajOperacji = 0;
		postInvalidate();
		Bitmap bmp = saveBackground();
		clearPaintView();
		objectList.add(overlay(bmp, selectedBitmap));
		postInvalidate();
		rodzajOperacji = 7;
	}
}