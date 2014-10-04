package org.braincopy.silbala;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraCallbackImpl implements SurfaceHolder.Callback,
		ShutterCallback, PictureCallback {
	private static final String TAG = "CameraViewHandler";

	private Camera mCam;
	private List<Size> supportedPreviewSize;
	private List<Size> supportedPictureSize;
	private byte[] mFrame;
	private Size mOptimalSize;

	private ARView overlayView;

	private ContentResolver contentResolver;

	public CameraCallbackImpl() {
		try {
			mCam = Camera.open();
			mCam.setDisplayOrientation(90);
		} catch (Exception e) {
			Log.d(TAG, "Error: failed to open Camera > " + e.getMessage());
		}

		mCam.setPreviewCallback(new PreviewCallback() {

			public void onPreviewFrame(byte[] data, Camera camera) {

				if (mFrame == null || mFrame.length != data.length) {
					mFrame = new byte[data.length];
				}
				for (int i = 0; i < data.length; i++) {
					mFrame[i] = data[i];
				}
			}

		});
		Camera.Parameters params = mCam.getParameters();
		supportedPreviewSize = params.getSupportedPreviewSizes();
		supportedPictureSize = params.getSupportedPictureSizes();

	}

	public void getFrame(byte[] frame) {
		synchronized (mFrame) {
			for (int i = 0; i < mFrame.length; i++) {
				frame[i] = mFrame[i];
			}
		}

	}

	public int getWidth() {
		return mOptimalSize.width;
	}

	public int getHeight() {
		return mOptimalSize.height;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCam.setPreviewDisplay(holder);
			mCam.startPreview();
		} catch (IOException e) {
			Log.d(TAG, "Error: setting Camera preview > " + e.getMessage());
		}

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		mOptimalSize = getOptimalPreviewSize(supportedPreviewSize, width,
				height);
		Size pictureSize = supportedPictureSize.get(2);

		if (holder.getSurface() == null) {
			return;
		}

		try {
			mCam.stopPreview();
		} catch (Exception e) {
			Log.d(TAG, "Error: failed to stop preview > " + e.getMessage());
		}

		try {
			Camera.Parameters parameters = mCam.getParameters();
			parameters.setPreviewSize(mOptimalSize.width, mOptimalSize.height);
			parameters.setPictureSize(pictureSize.width, pictureSize.height);

			mCam.setParameters(parameters);
			mCam.setPreviewDisplay(holder);
			mCam.startPreview();
		} catch (Exception e) {
			Log.i(TAG,
					"Error: failed to start camera preview > " + e.getMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		mCam.stopPreview();
		mCam.release();
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {

		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		// return sizes.get(4);
		return optimalSize;
	}

	public void takePicture() {
		mCam.takePicture(this, null, this);

	}

	@Override
	public void onShutter() {

	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		if (data != null) {

			String strFolder;
			String strFile;

			strFolder = Environment.getExternalStorageDirectory()
					+ "/DCIM/Camera/";

			strFile = strFolder + "test013.jpg";
			try {
				// 撮影画像保存（dataがすでにjpg画像になっているので、これをそのままファイルに落とすだけ）
				FileOutputStream cFile = new FileOutputStream(strFile);
				cFile.write(data);
				cFile.close();
				long nDate;
				ContentValues values = new ContentValues();

				nDate = System.currentTimeMillis();
				values.put(Images.Media.MIME_TYPE, "image/jpeg"); // 必須
				values.put(Images.Media.DATA, strFile); // 必須：ファイルパス（uriからストリーム作るなら不要）
				values.put(Images.Media.SIZE, new File(strFile).length()); // 必須：ファイルサイズ（同上）
				// values.put(Images.Media.TITLE,strFile);
				// values.put(Images.Media.DISPLAY_NAME,strFile);
				values.put(Images.Media.DATE_ADDED, nDate);
				values.put(Images.Media.DATE_TAKEN, nDate);
				values.put(Images.Media.DATE_MODIFIED, nDate);
				// values.put(Images.Media.DESCRIPTION,"");
				// values.put(Images.Media.LATITUDE,0.0);
				// values.put(Images.Media.LONGITUDE,0.0);
				// values.put(Images.Media.ORIENTATION,"");

				contentResolver.insert(Media.EXTERNAL_CONTENT_URI, values);
			} catch (Exception e) {
				Log.i(TAG, "" + e.getMessage());
			}

			Bitmap cameraMap = BitmapFactory.decodeByteArray(data, 0,
					data.length, null);
			// MediaStore.Images.Media.insertImage(this.contentResolver,
			// cameraMap, "sample1", "description");

			Matrix matrix = new Matrix();
			matrix.setRotate(90);
			Log.i(TAG, "data.length: " + data.length + ", cameraMap h:"
					+ cameraMap.getHeight() + ", w:" + cameraMap.getWidth());
			cameraMap = Bitmap.createBitmap(cameraMap, 0, 0,
					cameraMap.getWidth(), cameraMap.getHeight(), matrix, true);

			Bitmap overlayMap = overlayView.getDrawingCache();
			Bitmap offBitmap = Bitmap.createBitmap(cameraMap.getWidth(),
					cameraMap.getHeight(), Bitmap.Config.ARGB_8888);
			offBitmap = Bitmap.createBitmap(offBitmap, 0, 0,
					offBitmap.getWidth(), offBitmap.getHeight(), matrix, true);
			Canvas offScreen = new Canvas(offBitmap);
			offScreen
					.drawBitmap(
							cameraMap,
							null,
							new Rect(0, 0, cameraMap.getWidth(), cameraMap
									.getHeight()), null);
			offScreen
					.drawBitmap(
							overlayMap,
							null,
							new Rect(0, 0, cameraMap.getWidth(), cameraMap
									.getHeight()), null);

			MediaStore.Images.Media.insertImage(this.contentResolver,
					cameraMap, "sample2", "description");

			camera.startPreview();

		}

	}

	public void setOverlayView(ARView arView) {
		this.overlayView = arView;

	}

	public void setContentResolver(ContentResolver contentResolver_) {
		this.contentResolver = contentResolver_;

	}
}
