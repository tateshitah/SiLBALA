package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARActivity;
import org.braincopy.silbala.R;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The coordinate system of actual_orientation has been adjusted as follows:
 * <ol>
 * <li>right-handed coordinate system</li>
 * <li>when the camera directs east without any lean and incline, azimuth,
 * pitch, and roll will be 0 (zero).</li>
 * <li>x axis is direction of moving,</li>
 * <li>y axis is horizontal right direction,</li>
 * <li>z axis is vertical down direction,</li>
 * <li>angle increases for clockwise for all axis. The coordinate system should
 * be adjusted for each devices.</li>
 * </ol>
 * 
 * @author Hiroaki Tateshita
 * 
 */
public class Sample3Activity extends ARActivity implements SensorEventListener,
		LocationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setARView(new Sample2ARView(this));

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
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

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
	public void onSensorChanged(SensorEvent event) {
		super.onSensorChanged(event);

	}

	@Override
	public void onLocationChanged(Location arg0) {
		super.onLocationChanged(arg0);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
