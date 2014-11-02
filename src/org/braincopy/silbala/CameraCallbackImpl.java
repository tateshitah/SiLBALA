package org.braincopy.silbala;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

	static private int MAX_WIDTH_SIZE = 2000;

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
		Size pictureSize = getOptimalPictureSize(supportedPictureSize);

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

	/**
	 * width should be bigger than height
	 * 
	 * @param sizes
	 * @return
	 */
	private Size getOptimalPictureSize(List<Size> sizes) {
		Size optimalSize = null;
		int diff = MAX_WIDTH_SIZE;
		for (Size size : sizes) {
			if (size.width < MAX_WIDTH_SIZE) {
				if (diff > MAX_WIDTH_SIZE - size.width) {
					diff = MAX_WIDTH_SIZE - size.width;
					optimalSize = size;
				}
			}
		}
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

			strFolder = Environment.getExternalStorageDirectory()
					+ "/DCIM/Camera/";

			Bitmap cameraMap = BitmapFactory.decodeByteArray(data, 0,
					data.length, null);

			Matrix matrix = new Matrix();
			matrix.setRotate(90);
			cameraMap = Bitmap.createBitmap(cameraMap, 0, 0,
					cameraMap.getWidth(), cameraMap.getHeight(), matrix, true);

			Bitmap overlayMap = overlayView.getDrawingCache();
			Bitmap offBitmap = Bitmap.createBitmap(cameraMap.getWidth(),
					cameraMap.getHeight(), Bitmap.Config.ARGB_8888);
			// offBitmap = Bitmap.createBitmap(offBitmap, 0, 0,
			// offBitmap.getWidth(), offBitmap.getHeight(), matrix, true);
			Log.i(TAG,
					"data.length: " + data.length + ", cameraMap h:"
							+ cameraMap.getHeight() + ", w:"
							+ cameraMap.getWidth() + ", overLayMap h:"
							+ overlayMap.getHeight() + ", w:"
							+ overlayMap.getWidth() + ", offBitmap h:"
							+ offBitmap.getHeight() + ", w:"
							+ offBitmap.getWidth());
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

			Bitmap bitmap = offBitmap;
			FileOutputStream fos = null;

			Date today = new Date();
			SimpleDateFormat sdFormat = new SimpleDateFormat(
					"yyyy_MM_dd_hh_mm_ss_SSS", Locale.JAPAN);
			String fileName = sdFormat.format(today) + ".jpg";

			File file = new File(strFolder + fileName);
			try {
				if (file.createNewFile()) {
					fos = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
					fos.close();
				}
			} catch (FileNotFoundException e) {
				Log.e(TAG, e.getMessage());
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}

			Uri uri = Uri.fromFile(file);
			ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, uri.getLastPathSegment());
			values.put(MediaStore.Images.Media.DISPLAY_NAME,
					uri.getLastPathSegment());
			values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
			values.put(MediaStore.Images.Media.DATA, uri.getPath());
			values.put(MediaStore.Images.Media.DATE_TAKEN,
					System.currentTimeMillis());

			this.contentResolver.insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
