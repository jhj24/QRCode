package com.jhj.zxing.camera.a;

import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

import java.util.*;

public class CameraConfigurationUtil {

    public static Point findBestPreviewSizeValue(Camera.Parameters parameters, Point screenResolution) {

        // 获取当前手机支持的屏幕预览尺寸
        List<Camera.Size> rawSupportedSizes = parameters.getSupportedPreviewSizes();
        if (rawSupportedSizes == null) {
           // Log.w(TAG, "Device returned no supported preview sizes; using default");
            Camera.Size defaultSize = parameters.getPreviewSize();
            if (defaultSize == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            return new Point(defaultSize.width, defaultSize.height);
        }

        // 对这些尺寸根据像素值（即宽乘高的值）进行重小到大排序
        List<Camera.Size> supportedPreviewSizes = new ArrayList<>(rawSupportedSizes);
        Collections.sort(supportedPreviewSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size a, Camera.Size b) {
                int aPixels = a.height * a.width;
                int bPixels = b.height * b.width;
                if (bPixels < aPixels) {
                    return -1;
                }
                if (bPixels > aPixels) {
                    return 1;
                }
                return 0;
            }
        });

        double screenAspectRatio = (double) screenResolution.x / (double) screenResolution.y;

        Iterator<Camera.Size> it = supportedPreviewSizes.iterator();
        while (it.hasNext()) {
            Camera.Size supportedPreviewSize = it.next();
            int realWidth = supportedPreviewSize.width;
            int realHeight = supportedPreviewSize.height;
            // 首先把不符合最小预览像素值的尺寸排除
           /* if (realWidth * realHeight < MIN_PREVIEW_PIXELS) {
                it.remove();
                continue;
            }*/

            boolean isCandidatePortrait = realWidth < realHeight;
            int maybeFlippedWidth = isCandidatePortrait ? realHeight : realWidth;
            int maybeFlippedHeight = isCandidatePortrait ? realWidth : realHeight;
            double aspectRatio = (double) maybeFlippedWidth / (double) maybeFlippedHeight;
            double distortion = Math.abs(aspectRatio - screenAspectRatio);
            // 根据宽高比判断是否满足最大误差要求（默认最大值为0.15，即宽高比默认不能超过给定比例的15%）
          /*  if (distortion > MAX_ASPECT_DISTORTION) {
                it.remove();
                continue;
            }*/

            if (maybeFlippedWidth == screenResolution.x && maybeFlippedHeight == screenResolution.y) {
                Point exactPoint = new Point(realWidth, realHeight);
               // Log.i(TAG, "Found preview size exactly matching screen size: " + exactPoint);
                return exactPoint;
            }
        }

        // 如果没有精确匹配到合适的尺寸，则使用最大的尺寸，这样设置便是预览图像可能产生拉伸的根本原因。
        if (!supportedPreviewSizes.isEmpty()) {
            Camera.Size largestPreview = supportedPreviewSizes.get(0);
            Point largestSize = new Point(largestPreview.width, largestPreview.height);
//            Log.i(TAG, "Using largest suitable preview size: " + largestSize);
            return largestSize;
        }

        // 如果没有找到合适的尺寸，就返回默认设定的尺寸
        Camera.Size defaultPreview = parameters.getPreviewSize();
        if (defaultPreview == null) {
            throw new IllegalStateException("Parameters contained no preview size!");
        }
        Point defaultSize = new Point(defaultPreview.width, defaultPreview.height);
       // Log.i(TAG, "No suitable preview sizes, using default: " + defaultSize);
        return defaultSize;
    }
}
