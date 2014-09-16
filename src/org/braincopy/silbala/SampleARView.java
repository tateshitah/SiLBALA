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
		canvas.drawText("Hello2", 300, 300, this.paint);
		// drawAzElLines(canvas, paint, 5);
		drawRoof(canvas, paint, 0.1f, 10000, 10);
	}

}
