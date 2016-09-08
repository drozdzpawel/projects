package com.graphiceditor;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.graphiceditor.ob.ComplexRect;
import com.graphiceditor.ob.PaintView;

public class MainActivity extends Activity {

	PaintView paintView;
	Context ctx;
	RadioGroup radioGroup;
	AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		paintView = (PaintView) findViewById(R.id.paintView1);
		ctx = getApplicationContext();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {		
		case R.id.item1:			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("Wybor koloru");
			final CharSequence[] items = { "Czerwony", "Zielony", "Niebieski",
					"Zollty", "Czarny", "Rozowy", "Pomaranczowy", "Fioletowy",
					"Brazowy", "Szary"};
			
			alertDialogBuilder.setItems(items,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							switch (item) {
							case 0:								
								paintView.setColor(Color.RED);
								break;
							case 1:
								paintView.setColor(Color.GREEN);
								break;
							case 2:
								paintView.setColor(Color.BLUE);
								break;
							case 3:
								paintView.setColor(Color.YELLOW);
								break;
							case 4:								
								paintView.setColor(Color.BLACK);
								break;
							case 5:
								paintView.setColor(Color.rgb(255, 20, 147));
								break;
							case 6:
								paintView.setColor(Color.rgb(255, 140, 0));
								break;
							case 7:
								paintView.setColor(Color.rgb(139, 0, 139));
								break;
							case 8:
								paintView.setColor(Color.rgb(139, 69, 19));
								break;
							case 9:
								paintView.setColor(Color.rgb(192, 192, 192));
								break;												
							}
						}
					});
			alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			break;
				case R.id.item2:

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
			View layout = inflater.inflate(R.layout.rozmiar, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
					.setView(layout);
			alertDialog = builder.create();
			alertDialog.show();			
			Button bt = (Button) layout.findViewById(R.id.button1);
			bt.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					alertDialog.hide();
				}
			});
		
			SeekBar sb = (SeekBar) layout.findViewById(R.id.seekBar);
			sb.setMax(30);
			sb.setProgress(paintView.getRozmiar() - 20);
			sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

				public void onProgressChanged(SeekBar arg0, int arg1,
						boolean arg2) {
					paintView.setRozmiar(20 + arg1);

				}

				public void onStartTrackingTouch(SeekBar arg0) {

				}

				public void onStopTrackingTouch(SeekBar arg0) {

				}

			});
			break;
	
		case R.id.item4:
			paintView.setColor(paintView.color);
			paintView.setRozmiar(15);
			paintView.setRodzajOperacji(0);
			break;
		case R.id.item5:
			paintView.setColor(paintView.color);
			paintView.setRozmiar(15);
			paintView.setRodzajOperacji(1);
			break;
		case R.id.item6:
			LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout2 = inflater2.inflate(R.layout.ksztaly, null);
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
					.setView(layout2);
			alertDialog = builder2.create();
			alertDialog.show();
			Button prostakat = (Button) layout2.findViewById(R.id.button2);
			prostakat.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					paintView.setColor(paintView.color);
					paintView.setRozmiar(15);
					paintView.setRodzajOperacji(2);
					alertDialog.hide();
				}
			});

			Button okrag = (Button) layout2.findViewById(R.id.button3);
			okrag.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					paintView.setColor(paintView.color);
					paintView.setRozmiar(15);
					paintView.setRodzajOperacji(3);
					alertDialog.hide();
				}
			});

			Button trojkat = (Button) layout2.findViewById(R.id.button4);
			trojkat.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					paintView.setColor(paintView.color);
					paintView.setRozmiar(15);
					paintView.setRodzajOperacji(4);
					alertDialog.hide();
				}
			});
			break;
	
		case R.id.item7:
			paintView.clearPaintView();
			break;
		
		case R.id.item8:
			paintView.setColor(Color.WHITE);
			paintView.setRozmiar(50);
			paintView.setRodzajOperacji(0);
			break;
		
		case R.id.item12:
			paintView.saveToFile();
			Toast.makeText(
					getApplicationContext(),
					"Zapisano do mnt/sdcard/image/image"
							+ new Date().getSeconds() + ".jpg",
					Toast.LENGTH_SHORT).show();
			break;
	
		case R.id.item14:
			paintView.actuSelectionRect = new ComplexRect(Color.YELLOW, 15);
			paintView.setRodzajOperacji(6);
			break;
		
		case R.id.item15:
			try {
				paintView.copy(paintView.actuSelectionRect.clone());
				Toast.makeText(getApplicationContext(),
						"Skopiowano element obrazu", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(),
						"Wybierz miejsce wklejenia na ekranie",
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Nie wybrano zaznaczenia", Toast.LENGTH_SHORT).show();
				break;
			}
			break;
		
		case R.id.item16:
			try {
				if (paintView.selectedBitmap == null) {
					Toast.makeText(getApplicationContext(),
							"Nie wybrano elementu do wklejenia",
							Toast.LENGTH_SHORT).show();
					break;
				}
				paintView.paste();
			} catch (Exception e) {
				Toast.makeText(
						getApplicationContext(),
						"Wybrane miejsce jest nieodpowiednie do wklejenia tego elementu (za mało obszaru roboczego)",
						Toast.LENGTH_LONG).show();
			}
		}

		return true;
	}
}
