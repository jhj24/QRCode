package com.jhj.zxing.camera.a;

import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 预览尺寸与给定的宽高尺寸比较器。首先比较宽高的比例，在宽高比相同的情况下，根据宽和高的最小差进行比较。
 */
class SizeComparator implements Comparator<Camera.Size> {

    private final int width;
    private final int height;
    private final float ratio;

    SizeComparator(int width, int height) {
        if (width < height) {
            this.width = height;
            this.height = width;
        } else {
            this.width = width;
            this.height = height;
        }
        this.ratio = (float) this.height / this.width;
    }

    @Override
    public int compare(Camera.Size size1, Camera.Size size2) {
        int width1 = size1.width;
        int height1 = size1.height;
        int width2 = size2.width;
        int height2 = size2.height;

        float ratio1 = Math.abs((float) height1 / width1 - ratio);
        float ratio2 = Math.abs((float) height2 / width2 - ratio);
        int result = Float.compare(ratio1, ratio2);
        if (result != 0) {
            return result;
        } else {
            int minGap1 = Math.abs(width - width1) + Math.abs(height - height1);
            int minGap2 = Math.abs(width - width2) + Math.abs(height - height2);
            return minGap1 - minGap2;
        }
    }

    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth 需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList 需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    protected Camera.Size findCloselySize(int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList) {
        Collections.sort(preSizeList, new SizeComparator(surfaceWidth, surfaceHeight));
        return preSizeList.get(0);
    }


   /* void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        mCameraResolution = findCloselySize(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext),
                parameters.getSupportedPreviewSizes());
        Log.e(TAG, "Setting preview size: " + mCameraResolution.width + "-" + mCameraResolution.height);
        mPictureResolution = findCloselySize(ScreenUtils.getScreenWidth(mContext),
                ScreenUtils.getScreenHeight(mContext), parameters.getSupportedPictureSizes());
        Log.e(TAG, "Setting picture size: " + mPictureResolution.width + "-" + mPictureResolution.height);
    }*/
}