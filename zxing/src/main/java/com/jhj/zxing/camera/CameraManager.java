/*
package com.jhj.zxing.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.IOException;
import java.util.Collections;

public class CameraManager {

    private Context mContext;
    private Camera mCamera;
    private Boolean mPreviewing = false;
    //默认是后置摄像头
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private CameraConfigurationManager mCameraConfigurationManager;


    public CameraManager(Context mContext) {
        this.mContext = mContext;
        mCamera = Camera.open(mCameraId);
        mCameraConfigurationManager = new CameraConfigurationManager(mContext);
        mCameraConfigurationManager.initFromCameraParameters(mCamera);
    }


    public void setCameraId(int cameraId) {
        mCameraId = cameraId;
        mCamera = Camera.open(mCameraId);
    }

    public void openDriver(SurfaceHolder holder) throws IOException {
        Camera.Parameters parameters = mCamera.getParameters();
        String parametersFlattened = parameters == null ? null : parameters.flatten(); // Save these, temporarily
        try {
            mCameraConfigurationManager.setDesiredCameraParameters(mCamera);
        } catch (RuntimeException re) {
            if (parametersFlattened != null) {
                parameters = mCamera.getParameters();
                parameters.unflatten(parametersFlattened);
                try {
                    mCamera.setParameters(parameters);
                    mCameraConfigurationManager.setDesiredCameraParameters(mCamera);
                } catch (RuntimeException re2) {
                    // Well, darn. Give up
                }
            }
        }
        mCamera.setPreviewDisplay(holder);
    }

    public void closeDriver() {
        if (mCamera != null) {
            mCamera.getCamera().release();
            mCamera = null;
            // Make sure to clear these each time we close the camera, so that any scanning rect
            // requested by intent is forgotten.
            */
/*framingRect = null;
            framingRectInPreview = null;*//*

        }
    }

    public boolean isOpen() {
        return mCamera != null;
    }

    private void showCameraPreview(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            try {
                mPreviewing = true;
                surfaceHolder.setKeepScreenOn(true);
                mCamera.setPreviewDisplay(surfaceHolder);

                mCameraConfigurationManager.setDesiredCameraParameters(mCamera);
                mCamera.startPreview();
                if (mDelegate != null) {
                    mDelegate.onStartPreview();
                }
                startContinuousAutoFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void stopCameraPreview() {
        if (mCamera != null) {
            try {
                mPreviewing = false;
                mCamera.cancelAutoFocus();
                mCamera.setOneShotPreviewCallback(null);
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    boolean isPreviewing() {
        return mCamera != null && mPreviewing && mSurfaceCreated;
    }

    */
/**
     * 散光灯是否可用
     *
     * @return boolean
     *//*

    private boolean flashLightAvailable() {
        return isPreviewing() && mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    void openFlashlight() {
        if (flashLightAvailable()) {
            mCameraConfigurationManager.openFlashlight(mCamera);
        }
    }

    void closeFlashlight() {
        if (flashLightAvailable()) {
            mCameraConfigurationManager.closeFlashlight(mCamera);
        }
    }

    */
/**
     * 连续对焦
     *//*

    private void startContinuousAutoFocus() {
        // mIsTouchFocusing = false;
        if (mCamera == null) {
            return;
        }
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            // 连续对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(parameters);
            // 要实现连续的自动对焦，这一句必须加上
            mCamera.cancelAutoFocus();
        } catch (Exception e) {
            Log.e("Zxing", "连续对焦失败");
        }
    }

    void onScanBoxRectChanged(Rect scanRect) {
        if (mCamera == null || scanRect == null || scanRect.left <= 0 || scanRect.top <= 0) {
            return;
        }
        int centerX = scanRect.centerX();
        int centerY = scanRect.centerY();
        int rectHalfWidth = scanRect.width() / 2;
        int rectHalfHeight = scanRect.height() / 2;

        //转换前

        if (isPortrait(mContext)) {
            int temp = centerX;
            centerX = centerY;
            centerY = temp;

            temp = rectHalfWidth;
            rectHalfWidth = rectHalfHeight;
            rectHalfHeight = temp;
        }
        scanRect = new Rect(centerX - rectHalfWidth, centerY - rectHalfHeight, centerX + rectHalfWidth, centerY + rectHalfHeight);
        //转换后", scanRect);

        //扫码框发生变化触发对焦测光");
        handleFocusMetering(scanRect.centerX(), scanRect.centerY(), scanRect.width(), scanRect.height());
    }

    private void handleFocusMetering(float originFocusCenterX, float originFocusCenterY,
                                     int originFocusWidth, int originFocusHeight) {
        try {
            boolean isNeedUpdate = false;
            Camera.Parameters focusMeteringParameters = mCamera.getParameters();
            Camera.Size size = focusMeteringParameters.getPreviewSize();
            if (focusMeteringParameters.getMaxNumFocusAreas() > 0) {
                //支持设置对焦区域");
                isNeedUpdate = true;
                Rect focusRect = calculateFocusMeteringArea(1f,
                        originFocusCenterX, originFocusCenterY,
                        originFocusWidth, originFocusHeight,
                        size.width, size.height);
                //对焦区域", focusRect);
                focusMeteringParameters.setFocusAreas(Collections.singletonList(new Camera.Area(focusRect, 1000)));
                focusMeteringParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
            } else {
                //不支持设置对焦区域");
            }

            if (focusMeteringParameters.getMaxNumMeteringAreas() > 0) {
                //支持设置测光区域");
                isNeedUpdate = true;
                Rect meteringRect = calculateFocusMeteringArea(1.5f,
                        originFocusCenterX, originFocusCenterY,
                        originFocusWidth, originFocusHeight,
                        size.width, size.height);
                //测光区域", meteringRect);
                focusMeteringParameters.setMeteringAreas(Collections.singletonList(new Camera.Area(meteringRect, 1000)));
            } else {
                //不支持设置测光区域");
            }

            if (isNeedUpdate) {
                mCamera.cancelAutoFocus();
                mCamera.setParameters(focusMeteringParameters);
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            //对焦测光成功
                        } else {
                            //对焦测光失败
                        }
                        startContinuousAutoFocus();
                    }
                });
            } else {
                //  mIsTouchFocusing = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //对焦测光失败
            startContinuousAutoFocus();
        }
    }


    */
/**
     * 是否为竖屏
     *//*

    private boolean isPortrait(Context context) {
        Point screenResolution = getScreenResolution(context);
        return screenResolution.y > screenResolution.x;
    }

    private Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        display.getSize(screenResolution);
        return screenResolution;
    }

    */
/**
     * 计算对焦和测光区域
     *
     * @param coefficient        比率
     * @param originFocusCenterX 对焦中心点X
     * @param originFocusCenterY 对焦中心点Y
     * @param originFocusWidth   对焦宽度
     * @param originFocusHeight  对焦高度
     * @param previewViewWidth   预览宽度
     * @param previewViewHeight  预览高度
     *//*

    private Rect calculateFocusMeteringArea(float coefficient,
                                            float originFocusCenterX, float originFocusCenterY,
                                            int originFocusWidth, int originFocusHeight,
                                            int previewViewWidth, int previewViewHeight) {

        int halfFocusAreaWidth = (int) (originFocusWidth * coefficient / 2);
        int halfFocusAreaHeight = (int) (originFocusHeight * coefficient / 2);

        int centerX = (int) (originFocusCenterX / previewViewWidth * 2000 - 1000);
        int centerY = (int) (originFocusCenterY / previewViewHeight * 2000 - 1000);

        RectF rectF = new RectF(clamp(centerX - halfFocusAreaWidth, -1000, 1000),
                clamp(centerY - halfFocusAreaHeight, -1000, 1000),
                clamp(centerX + halfFocusAreaWidth, -1000, 1000),
                clamp(centerY + halfFocusAreaHeight, -1000, 1000));
        return new Rect(Math.round(rectF.left), Math.round(rectF.top),
                Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }


}
*/
