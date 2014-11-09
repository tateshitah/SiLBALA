package org.braincopy.silbala;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Sample class of extended ARView class.
 * 
 * @author Hiroaki Tateshita
 * @version 0.2.0
 * 
 */
public class SampleARView extends ARView {

	public SampleARView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.setStatus("sample");
		Point tempPoint = null;
		// sample case 1: convert Azimuth and Elevation to position on display
		// screen
		float azimuth, elevation;
		azimuth = 270;// north
		elevation = 30;
		tempPoint = this.convertAzElPoint(azimuth, elevation);
		if (tempPoint != null) {
			canvas.drawText("@ (" + azimuth + "[deg], " + elevation + "[deg])",
					tempPoint.x, tempPoint.y, paint);
		}
		azimuth = 280;
		elevation = 20;
		tempPoint = this.convertAzElPoint(azimuth, elevation);
		if (tempPoint != null) {
			canvas.drawText("@ (" + azimuth + "[deg], " + elevation + "[deg])",
					tempPoint.x, tempPoint.y, paint);
		}
		azimuth = 290;
		elevation = 10;
		tempPoint = this.convertAzElPoint(azimuth, elevation);
		if (tempPoint != null) {
			canvas.drawText("@ (" + azimuth + "[deg], " + elevation + "[deg])",
					tempPoint.x, tempPoint.y, paint);
		}

		// sample case 2: convert Latitude, Longitude and altitude to position
		// on display screen
		float latOfTarget, lonOfTarget, altOfTarget;
		latOfTarget = this.lat - 0.1f;
		lonOfTarget = this.lon;
		altOfTarget = 10000;// [m]
		tempPoint = this.convertLatLonPoint(latOfTarget, lonOfTarget,
				altOfTarget);
		if (tempPoint != null) {
			canvas.drawText(
					"@ current latitude - 0.1[deg] and 10km height point",
					tempPoint.x, tempPoint.y, paint);
		}

		// sample case 3: draw azimuth and elevation lines on display screen

		this.drawAzElLines(canvas, paint, 8);

		// sample case 4: draw roof based on latitude and longitude grid on
		// display screen

		// this.drawRoof(canvas, paint, 0.1f, 10000, 10);

	}

}
