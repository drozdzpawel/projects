package com.graphiceditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class PhotoActivity extends Activity {
	private ImageView imageView;
	private AlertDialog alertDialog;
	private Bitmap photo;
	private Bitmap photoAfterEffects;
	private int lastSeekVal = 255;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		this.imageView = (ImageView) this.findViewById(R.id.imageView1);
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					makePhoto();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					choosePhoto();
					break;
				}
			}
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Wybierz opcje")
				.setPositiveButton("Zrob zdjecie", dialogClickListener)
				.setNegativeButton("Zaladuj z telefonu", dialogClickListener)
				.show();
	}

	private void makePhoto() {
		startActivityForResult(new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE), 333);
	}

	private void choosePhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Wybierz zdjecie"),
				444);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.photo, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {		
		switch (item.getItemId()) {
		case R.id.brightness:
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.rozmiar, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setView(layout);
			alertDialog = builder.create();
			alertDialog.show();

			final SeekBar sb = (SeekBar) layout.findViewById(R.id.seekBar);
			sb.setMax(510);
			sb.setProgress(lastSeekVal);
			Button bt = (Button) layout.findViewById(R.id.button1);
			bt.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					recycle();
					photoAfterEffects = brightness(sb.getProgress());
					imageView.setImageBitmap(photoAfterEffects);
					imageView.invalidate();
					alertDialog.hide();
				}
			});
			break;
		case R.id.sepia:
			recycle();
			photoAfterEffects = sephia();
			imageView.setImageBitmap(photoAfterEffects);
			imageView.invalidate();
			break;
		case R.id.saveToFile:
			saveToFile(photo);
			break;
		case R.id.rotate:
			LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout1 = inflater1.inflate(R.layout.rozmiar, null);
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
					.setView(layout1);
			alertDialog = builder1.create();
			alertDialog.show();

			final SeekBar sb1 = (SeekBar) layout1.findViewById(R.id.seekBar);
			sb1.setMax(360);
			sb1.setProgress(0);
			Button bt1 = (Button) layout1.findViewById(R.id.button1);
			bt1.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					recycle();
					photoAfterEffects = rotate(photo, sb1.getProgress());
					imageView.setImageBitmap(photoAfterEffects);
					imageView.invalidate();
					alertDialog.hide();
				}
			});
			break;
		case R.id.invert:
			recycle();
			photoAfterEffects = invert(photo);
			imageView.setImageBitmap(photoAfterEffects);
			imageView.invalidate();
			break;
		case R.id.grey:
			recycle();
			photoAfterEffects = greyscale(photo);
			imageView.setImageBitmap(photoAfterEffects);
			imageView.invalidate();
			break;
		}
		return true;
	}

	private void recycle() {
		if (photoAfterEffects != null && photoAfterEffects != photo) {
			photoAfterEffects.recycle();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 333:
				photo = (Bitmap) data.getExtras().get("data");
				photoAfterEffects = photo;
				imageView.setImageBitmap(photo);
				imageView.setScaleType(ScaleType.FIT_XY);
				break;
			case 444:
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();
				photo = BitmapFactory.decodeFile(filePath);
				imageView.setImageBitmap(photo);
				imageView.setScaleType(ScaleType.FIT_XY);
				break;
			}
		}
	}

	public Bitmap invert(Bitmap src) {
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		int A, R, G, B;
		int pixelColor;
		int height = src.getHeight();
		int width = src.getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixelColor = photo.getPixel(x, y);
				A = Color.alpha(pixelColor);
				R = 255 - Color.red(pixelColor);
				G = 255 - Color.green(pixelColor);
				B = 255 - Color.blue(pixelColor);
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		return bmOut;
	}

	public Bitmap brightness(int value) {
		lastSeekVal = value;
		value = value > 255 ? value / 2 : value - 255 / 2;
		int width = photo.getWidth();
		int height = photo.getHeight();
		Bitmap bmOut = Bitmap.createBitmap(width, height, photo.getConfig());
		int A, R, G, B;
		int pixel;
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {

				pixel = photo.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				R += value;
				if (R > 255) {
					R = 255;
				} else if (R < 0) {
					R = 0;
				}
				G += value;
				if (G > 255) {
					G = 255;
				} else if (G < 0) {
					G = 0;
				}
				B += value;
				if (B > 255) {
					B = 255;
				} else if (B < 0) {
					B = 0;
				}
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}
		return bmOut;
	}

	public Bitmap rotate(Bitmap src, float degree) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				matrix, true);
	}

	public Bitmap sephia() {
		int width, height, r, g, b, c, gry;
		height = photo.getHeight();
		width = photo.getWidth();
		int depth = 20;

		Bitmap bmpSephia = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmpSephia);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setScale(.3f, .3f, .3f, 1.0f);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		canvas.drawBitmap(photo, 0, 0, paint);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				c = photo.getPixel(x, y);

				r = Color.red(c);
				g = Color.green(c);
				b = Color.blue(c);

				gry = (r + g + b) / 3;
				r = g = b = gry;

				r = r + (depth * 2);
				g = g + depth;

				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				bmpSephia.setPixel(x, y, Color.rgb(r, g, b));
			}
		}
		return bmpSephia;
	}
	
	public static Bitmap greyscale(Bitmap src) {
		final double GS_RED = 0.299;
	    final double GS_GREEN = 0.587;
	    final double GS_BLUE = 0.114;
	 
	    Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
	   
	    int A, R, G, B;
	    int pixel;
	 
	    int width = src.getWidth();
	    int height = src.getHeight();
	 
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            R = Color.red(pixel);
	            G = Color.green(pixel);
	            B = Color.blue(pixel);
	            R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	    return bmOut;
	}

	private void saveToFile(Bitmap bmp) {
		File myDir = new File("mnt/sdcard/image");
		File file = new File(myDir, "image" + new Date().getSeconds() + ".jpg");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			photoAfterEffects.compress(CompressFormat.JPEG, 95, fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Toast.makeText(
				getApplicationContext(),
				"Zapisano do mnt/sdcard/image/image" + new Date().getSeconds()
						+ ".jpg", Toast.LENGTH_SHORT).show();
	}

}
