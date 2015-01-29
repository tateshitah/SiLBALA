package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARActivity;
import org.braincopy.silbala.ARObject;
import org.braincopy.silbala.ARView;
import org.braincopy.silbala.Point;
import org.braincopy.silbala.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

/**
 * Sample of Touch Event of ARObject
 * 
 * @author Hiroaki Tateshita
 * @version 0.4.1
 * 
 */
public class Sample4Activity extends ARActivity {

	private final float TOUCH_AREA_SIZE = 150;
	private boolean touched;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ARView arview = new Sample4ARView(this);
		ARObject[] arObjs = new ARObject[4];
		for (int i = 0; i < 4; i++) {
			arObjs[i] = new ARObject();
			arObjs[i].setImage(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.touchme));
		}
		((Sample4ARView) arview).setARObject(arObjs);
		this.setARView(arview);
		this.touched = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_main) {
			Intent intent = new Intent(this.getApplicationContext(),
					SampleMainActivity.class);
			this.startActivity(intent);
			return true;
		} else if (id == R.id.action_quit) {
			this.moveTaskToBack(true);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x, y;
		x = event.getX();
		y = event.getY();
		for (int i = 0; i < 4; i++) {
			Point point = ((Sample4ARView) this.getARView()).arObjs[i]
					.getPoint();
			if (point != null) {
				if (Math.abs(x - point.x) < TOUCH_AREA_SIZE
						&& Math.abs(y - point.y) < TOUCH_AREA_SIZE && !touched) {
					touched = true;

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							this);
					alertDialogBuilder.setTitle("Sample 4 AlertDialog");
					alertDialogBuilder
							.setMessage("This is sample 4 alert dialog! "
									+ "ARObjs[" + i + "] was at (" + point.x
									+ ", " + point.y + ") and you touched ("
									+ x + ", " + y + ")");
					alertDialogBuilder.setPositiveButton("OK",
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									touched = false;
									dialog.cancel();
								}
							});

					Dialog dialog = alertDialogBuilder.create();
					// dialog.setTitle("Sample 4 dialog");
					// dialog.setContentView(R.layout.dialog);

					dialog.show();
					// popupWindow.showAsDropDown(this.getARView());
				}
			}

		}
		return super.onTouchEvent(event);
	}
}
