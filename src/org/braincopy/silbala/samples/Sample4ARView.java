package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARObject;
import org.braincopy.silbala.ARView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.format.Time;

/**
 * Sample class of extended ARView class.
 * 
 * @author Hiroaki Tateshita
 * @version 0.4.1
 * 
 */
public class Sample4ARView extends ARView {
	ARObject[] arObjs;
	Time time;
	Matrix matrix;

	public Sample4ARView(Context context) {
		super(context);
		time = new Time("Asia/Tokyo");
		matrix = new Matrix();
		float scale = 2.0f;
		matrix.postScale(scale, scale);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.setStatus("sample4");

		time.setToNow();
		float aziTarget, eleTarget;
		aziTarget = 315f + time.second;
		eleTarget = -30f + time.second * 0.5f;
		for (int i = 0; i < arObjs.length; i++) {
			arObjs[i].setPoint(this.convertAzElPoint(aziTarget + 30 * i,
					eleTarget + 5 * i));
			point = arObjs[i].getPoint();
			if (point != null) {
				matrix.postTranslate(point.x, point.y);
				canvas.drawBitmap(arObjs[i].getImage(), matrix, paint);
				canvas.drawText("(" + aziTarget + ", " + eleTarget + ")",
						point.x, point.y, paint);
				matrix.postTranslate(-point.x, -point.y);
			}
		}
		this.drawAzElLines(canvas, paint, 8);

	}

	public void setARObject(ARObject[] arObjs_) {
		this.arObjs = arObjs_;

	}
}
