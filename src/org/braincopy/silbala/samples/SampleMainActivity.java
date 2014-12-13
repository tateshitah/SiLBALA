package org.braincopy.silbala.samples;

import org.braincopy.silbala.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * BTW I like the songs, "Call me maybe", "Royals", and "Grace Kelly". the Movie
 * "source code" is great. so impressive.
 * 
 * @author Hiroaki Tateshita
 * @version 0.3.0
 * 
 */
public class SampleMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			// getFragmentManager().beginTransaction()
			// .add(R.id.container, new PlaceholderFragment()).commit();
			getFragmentManager().beginTransaction()
					.add(R.id.container, new SampleMainFragment()).commit();
		}

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
			getFragmentManager().beginTransaction()
					.add(R.id.container, new SampleMainFragment()).commit();
			return true;
		} else if (id == R.id.action_quit) {
			this.moveTaskToBack(true);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * locationManager = (LocationManager)
		 * getSystemService(Context.LOCATION_SERVICE);
		 * locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		 * 0, 0, this); sensorManager.registerListener(this, listMag.get(0),
		 * SensorManager.SENSOR_DELAY_NORMAL);
		 * sensorManager.registerListener(this, listAcc.get(0),
		 * SensorManager.SENSOR_DELAY_NORMAL);
		 */
	}

	@Override
	public void onStop() {
		super.onStop();
		// locationManager.removeUpdates(this);
		// sensorManager.unregisterListener(this);

	}

}
