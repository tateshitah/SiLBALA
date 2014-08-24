package org.braincopy.silbala;

import android.content.Context;
import android.graphics.Canvas;

public class SampleARView extends ARView {

	public SampleARView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText("Hello", 300, 300, this.paint);
		drawAzElLines(canvas, paint, 5);
		point = convertLatLonPoint(lat, lon, 10000f);
		if (point != null) {
			canvas.drawText("should be on top", point.x, point.y, paint);
		}
	}

}
