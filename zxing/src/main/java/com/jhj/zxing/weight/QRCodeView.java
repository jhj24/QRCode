package com.jhj.zxing.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.jhj.zxing.R;

public class QRCodeView extends View {


    private int mainColor;
    private int cornerLineWidth;
    private int cornerLineLength;
    private int sideWidth;
    private int scanLineWidth;
    private float scanLineLength;
    private int gridSideLength;
    private int gridChangedSpeed;
    private int gridSideWidth;


    private Paint mPaint;
    private Path cornerPath;
    private Path gridPath;
    private Matrix mMatrix;
    private int mTransLate;
    private LinearGradient mGradient;
    private int[] mColors;
    private float[] mPercent;
    private boolean isFirstLoad = true;

    public QRCodeView(Context context) {
        this(context, null);
    }

    public QRCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QRCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QRCodeView, defStyleAttr, 0);
        mainColor = typedArray.getColor(R.styleable.QRCodeView_main_color, Color.GREEN);
        cornerLineLength = typedArray.getInteger(R.styleable.QRCodeView_corner_line_length, 50);
        cornerLineWidth = typedArray.getInteger(R.styleable.QRCodeView_corner_line_width, 12);
        scanLineWidth = typedArray.getInteger(R.styleable.QRCodeView_grid_bottom_line_width, 4);
        scanLineLength = typedArray.getFloat(R.styleable.QRCodeView_grid_bottom_line_length, 1f);
        gridSideLength = typedArray.getInteger(R.styleable.QRCodeView_grid_side_length, 15);
        gridSideWidth = typedArray.getInteger(R.styleable.QRCodeView_grid_side_width, 2);
        sideWidth = typedArray.getInteger(R.styleable.QRCodeView_side_width, 4);
        gridChangedSpeed = typedArray.getInteger(R.styleable.QRCodeView_grid_changed_speed, 100);

        cornerPath = new Path();
        gridPath = new Path();
        mMatrix = new Matrix();
        mPaint = new Paint();
        mColors = new int[]{Color.TRANSPARENT, mainColor};
        mPercent = new float[]{0.75f, 1f};
        typedArray.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //边框
        mPaint.reset();
        mPaint.setColor(mainColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(sideWidth);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        //四角
        mPaint.reset();
        mPaint.setColor(mainColor);
        cornerPath.addRect(0, cornerLineWidth, cornerLineWidth, cornerLineLength, Path.Direction.CW);
        cornerPath.addRect(0, 0, cornerLineLength, cornerLineWidth, Path.Direction.CW);
        cornerPath.addRect(getWidth() - cornerLineLength, 0, getWidth(), cornerLineWidth, Path.Direction.CW);
        cornerPath.addRect(getWidth() - cornerLineWidth, cornerLineWidth, getWidth(), cornerLineLength, Path.Direction.CW);
        cornerPath.addRect(getWidth() - cornerLineWidth, getHeight() - cornerLineLength, getWidth(), getHeight(), Path.Direction.CW);
        cornerPath.addRect(getWidth() - cornerLineLength, getHeight() - cornerLineWidth, getWidth() - cornerLineWidth, getHeight(), Path.Direction.CW);
        cornerPath.addRect(0, getHeight() - cornerLineWidth, cornerLineLength, getHeight(), Path.Direction.CW);
        cornerPath.addRect(0, getHeight() - cornerLineLength, cornerLineWidth, getHeight() - cornerLineWidth, Path.Direction.CW);
        canvas.drawPath(cornerPath, mPaint);

        //网格下面的粗线
        int yPoint;
        if (mTransLate > 0) {
            yPoint = mTransLate;
        } else {
            yPoint = getHeight() + mTransLate;
        }
        mPaint.reset();
        mPaint.setColor(mainColor);
        int extra = (int) (getWidth() - scanLineLength * getWidth());
        canvas.drawRect(extra / 2, yPoint, getWidth() - extra / 2, yPoint + scanLineWidth, mPaint);


        //扫描网格
        matrixScan();
        mPaint.reset();
        mPaint.setStrokeWidth(gridSideWidth);
        mPaint.setColor(mainColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setShader(mGradient);
        if (isFirstLoad) {
            //横线
            for (int i = 0; i < getHeight(); i += gridSideLength) {
                if (i < cornerLineLength || i > getHeight() - cornerLineLength) {
                    gridPath.moveTo(cornerLineWidth, i);
                    gridPath.lineTo(getWidth() - cornerLineWidth, i);
                } else {
                    gridPath.moveTo(sideWidth / 2, i);
                    gridPath.lineTo(getWidth() - sideWidth / 2, i);
                }
            }

            //竖线
            for (int i = 0; i < getWidth(); i += gridSideLength) {
                if (i < cornerLineLength || i > getWidth() - cornerLineLength) {
                    gridPath.moveTo(i, cornerLineWidth);
                    gridPath.lineTo(i, getHeight() - cornerLineWidth);
                } else {
                    gridPath.moveTo(i, sideWidth / 2);
                    gridPath.lineTo(i, getHeight() - sideWidth / 2);
                }
            }
        }
        canvas.drawPath(gridPath, mPaint);
        postInvalidateDelayed(gridChangedSpeed);
        isFirstLoad = false;

    }

    private void matrixScan() {
        //渐变矩阵
        if (mMatrix != null) {
            if (mTransLate > getHeight()) { //渐变平移走到头，需要重新从头开始
                mTransLate = -getHeight();
            }
            mMatrix.setTranslate(0, mTransLate);
            //从0开始，每次递增网格的宽度，每次向下平移一个网格宽度
            mTransLate += gridSideLength * 2;
        }
        mGradient = new LinearGradient(0, 0, 0, getHeight(), mColors, mPercent, Shader.TileMode.REPEAT);
        mGradient.setLocalMatrix(mMatrix);
    }

}
