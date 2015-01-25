package org.braincopy.silbala.samples;

import org.braincopy.silbala.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 
 * @author Hiroaki Tateshita
 * @version 0.4.0
 * 
 */
public class SampleMainFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_main,
				container, false);

		Button sample1Button = (Button) rootView.findViewById(R.id.button1);
		sample1Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), Sample1Activity.class);
				startActivity(intent);

			}
		});

		Button sample2Button = (Button) rootView.findViewById(R.id.button2);
		sample2Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), Sample2Activity.class);
				startActivity(intent);
			}
		});

		Button sample3Button = (Button) rootView.findViewById(R.id.button3);
		sample3Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), Sample3Activity.class);
				startActivity(intent);
			}
		});

		Button sample4Button = (Button) rootView.findViewById(R.id.button4);
		sample4Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), Sample4Activity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
}
