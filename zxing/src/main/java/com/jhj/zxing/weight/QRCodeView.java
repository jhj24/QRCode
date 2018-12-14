package com.jhj.zxing.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class QRCodeView extends View {

    private Paint mPaint;
    private int cornerColor = Color.RED;
    private int cornerLineWidth = 12;
    private int cornerLineLength = 50;
    private int sideWidth = 4;
    private Path path;

    public QRCodeView(Context context) {
        this(context, null);
    }

    public QRCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QRCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
        mPaint = new Paint();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //边框
        mPaint.reset();
        mPaint.setColor(cornerColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(sideWidth);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        //四角
        mPaint.reset();
        mPaint.setColor(cornerColor);
        path.addRect(0, cornerLineWidth, cornerLineWidth, cornerLineLength, Path.Direction.CW);
        path.addRect(0, 0, cornerLineLength, cornerLineWidth, Path.Direction.CW);
        path.addRect(getWidth() - cornerLineLength, 0, getWidth(), cornerLineWidth, Path.Direction.CW);
        path.addRect(getWidth() - cornerLineWidth, cornerLineWidth, getWidth(), cornerLineLength, Path.Direction.CW);
        path.addRect(getWidth() - cornerLineWidth, getHeight() - cornerLineLength, getWidth(), getHeight(), Path.Direction.CW);
        path.addRect(getWidth() - cornerLineLength, getHeight() - cornerLineWidth, getWidth() - cornerLineWidth, getHeight(), Path.Direction.CW);
        path.addRect(0, getHeight() - cornerLineWidth, cornerLineLength, getHeight(), Path.Direction.CW);
        path.addRect(0, getHeight() - cornerLineLength, cornerLineWidth, getHeight() - cornerLineWidth, Path.Direction.CW);
        canvas.drawPath(path, mPaint);


        //扫描线



        
    }
}
