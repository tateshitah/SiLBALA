package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARActivity;
import org.braincopy.silbala.ARObject;
import org.braincopy.silbala.ARObjectDialog;
import org.braincopy.silbala.ARView;
import org.braincopy.silbala.Point;
import org.braincopy.silbala.R;

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
 * @version 0.4.4
 * 
 */
public class Sample4Activity extends ARActivity {

	// public boolean[] touchedFlags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ARView arview = new Sample4ARView(this);
		ARObject[] arObjs = new ARObject[8];
		touchedFlags = new boolean[arObjs.length];
		for (int i = 0; i < arObjs.length; i++) {
			arObjs[i] = new ARObject();
			arObjs[i].setImage(BitmapFactory.decodeResource(
					this.getResources(), R.drawable.touchme));
			touchedFlags[i] = false;
		}
		((Sample4ARView) arview).setARObject(arObjs);
		this.setARView(arview);
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
		ARObject[] arObjs_ = ((Sample4ARView) this.getARView()).arObjs;
		for (int i = 0; i < arObjs_.length; i++) {
			Point point = arObjs_[i].getPoint();
			if (point != null) {
				if (Math.abs(x - point.x) < TOUCH_AREA_SIZE
						&& Math.abs(y - point.y) < TOUCH_AREA_SIZE
						&& !touchedFlags[i]) {
					touchedFlags[i] = true;
					/*
					 * AlertDialog.Builder alertDialogBuilder = new
					 * AlertDialog.Builder( this);
					 * alertDialogBuilder.setTitle("Sample 4 AlertDialog");
					 */
					String message = "This is sample 4 alert dialog! "
							+ "ARObjs[" + i + "] was at (" + point.x + ", "
							+ point.y + ") and you touched (" + x + ", " + y
							+ ")";
					/*
					 * alertDialogBuilder.setMessage(message);
					 * alertDialogBuilder.setPositiveButton("OK", new
					 * ARObjOnClickListener(i));
					 * 
					 * Dialog dialog = alertDialogBuilder.create();
					 * 
					 * dialog.show(); //
					 * popupWindow.showAsDropDown(this.getARView());
					 */ARObjectDialog dialog2 = new ARObjectDialog();
					Bundle args = new Bundle();
					args.putString("message", message);
					args.putInt("index", i + 1);
					dialog2.setArguments(args);
					dialog2.show(getFragmentManager(), "tag?");
				}
			}

		}
		return super.onTouchEvent(event);
	}

	/*
	 * private class ARObjOnClickListener implements OnClickListener { int
	 * index;
	 * 
	 * public ARObjOnClickListener(int index_) { this.index = index_; }
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * touchedFlags[index] = false; dialog.cancel();
	 * 
	 * }
	 * 
	 * }
	 */
}