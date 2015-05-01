package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARView;
import org.braincopy.silbala.Point;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Sample class of extended ARView class.
 * 
 * @author Hiroaki Tateshita
 * @version 0.3.0
 * 
 */
public class Sample3ARView extends ARView {

	public Sample3ARView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.setStatus("sample3");
		Point tempPoint = null;

		// sample case 2: convert Latitude, Longitude and altitude to position
		// on display screen
		float latOfTarget, lonOfTarget, altOfTarget;
		// 35.360611, 138.727361, 3775.5: Mount Fuji
		latOfTarget = 35.360611f;
		lonOfTarget = 138.727361f;
		altOfTarget = 3775.5f;// [m]
		tempPoint = this.convertLatLonPoint(latOfTarget, lonOfTarget,
				altOfTarget);
		if (tempPoint != null) {
			canvas.drawText("@ Summit of Mt. Fuji", tempPoint.x, tempPoint.y,
					paint);
		}
		// 36.22528, 140.10667, 877
		latOfTarget = 36.22528f;
		lonOfTarget = 140.10667f;
		altOfTarget = 877f;// [m]
		tempPoint = this.convertLatLonPoint(latOfTarget, lonOfTarget,
				altOfTarget);
		if (tempPoint != null) {
			canvas.drawText("@ Summit of Mt. Tsukuba", tempPoint.x,
					tempPoint.y, paint);
		}

		// sample case 3: draw azimuth and elevation lines on display screen

		// this.drawAzElLines(canvas, paint, 8);

		// sample case 4: draw roof based on latitude and longitude grid on
		// display screen

		this.drawRoof(canvas, paint, 0.1f, 10000, 10);

	}
}
