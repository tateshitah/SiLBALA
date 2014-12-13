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
		return rootView;
	}
}
