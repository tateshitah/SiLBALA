package org.braincopy.silbala.samples;

import org.braincopy.silbala.ARObject;
import org.braincopy.silbala.ARView;
import org.braincopy.silbala.Point;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.text.format.Time;

/**
 * Sample class of extended ARView class. The point of this sample is
 * "touch event"!
 * 
 * @author Hiroaki Tateshita
 * @version 0.4.7
 * 
 */
public class Sample4ARView extends ARView {
	// ARObject[] arObjs;
	Time time;
	Matrix matrix;
	Point touchedPoint;
	float scale = 0f;;

	public Sample4ARView(Context context) {
		super(context);
		time = new Time("Asia/Tokyo");
		matrix = new Matrix();
		scale = 1.0f;
		matrix.postScale(scale, scale);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.setStatus("sample4");

		time.setToNow();
		float aziTarget, eleTarget, dx, dy;
		aziTarget = 315f + time.second;
		eleTarget = -90f + time.second * 0.5f;
		for (int i = 0; i < arObjs.length; i++) {
			point = this
					.convertAzElPoint(aziTarget + 90 * i, eleTarget + 1 * i);
			dx = arObjs[i].getImage().getWidth() / 2 * scale;
			dy = arObjs[i].getImage().getHeight() / 2 * scale;
			if (point != null) {
				arObjs[i].setPoint(point);
				matrix.postTranslate(point.x - dx, point.y - dy);
				canvas.drawBitmap(arObjs[i].getImage(), matrix, paint);
				canvas.drawText("(" + aziTarget + ", " + eleTarget + ")",
						point.x, point.y, paint);
				matrix.postTranslate(-point.x + dx, -point.y + dy);
				canvas.drawCircle(point.x, point.y, 150f, paint);
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
