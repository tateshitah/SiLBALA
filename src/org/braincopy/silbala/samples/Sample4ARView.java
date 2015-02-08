package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARObject;
import org.braincopy.silbala.ARView;
import org.braincopy.silbala.Point;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.format.Time;

/**
 * Sample class of extended ARView class.
 * 
 * @author Hiroaki Tateshita
 * @version 0.4.3
 * 
 */
public class Sample4ARView extends ARView {
	ARObject[] arObjs;
	Time time;
	Matrix matrix;
	Point touchedPoint;

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
		eleTarget = -90f + time.second * 0.5f;
		for (int i = 0; i < arObjs.length; i++) {
			arObjs[i].setPoint(this.convertAzElPoint(aziTarget + 90 * i,
					eleTarget + 1 * i));
			point = arObjs[i].getPoint();
			if (point != null) {
				matrix.postTranslate(point.x, point.y);
				canvas.drawBitmap(arObjs[i].getImage(), matrix, paint);
				canvas.drawText("(" + aziTarget + ", " + eleTarget + ")",
						point.x, point.y, paint);
				matrix.postTranslate(-point.x, -point.y);
			}
		}
		if (this.touchedPoint != null) {
			canvas.drawCircle(this.touchedPoint.x, this.touchedPoint.y, 150f,
					paint);
		}
		this.drawAzElLines(canvas, paint, 8);

	}

	public void setARObject(ARObject[] arObjs_) {
		this.arObjs = arObjs_;

	}

	public void setTouchedPoint(Point touchedPoint_) {
		this.touchedPoint = touchedPoint_;
	}

}
