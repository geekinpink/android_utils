package io.geek.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import io.geek.myapplication.R;

/**
 *自定义圆形ImageView，支持设置边框，使用了xfermode来绘制重叠图形来实现
 */

public class CircleImageView extends View {

/*
    <declare-styleable name="CircleImageView">
        <attr name="border_width" format="dimension"/>
        <attr name="border_color" format="color"/>
        <attr name="src" format="reference"/>
    </declare-styleable>

 */

    @ColorInt
    private int mBorderColor;
    private int mBorderWidth;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_BORDER_WIDTH = 1;

    private Paint mPaint;
    private Bitmap mSrc;

    private int mSize;//控件的尺寸
    private Paint mBorderPaint;


    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);

        mBorderColor = a.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH);
        mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.CircleImageView_src, 0));

        a.recycle();
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(createCircleBitmap(Bitmap.createScaledBitmap(mSrc, mSize - getPaddingLeft() - getPaddingRight(),
                mSize - getPaddingTop() - getPaddingBottom(), false)), 0, 0, null);

        canvas.drawCircle(mSize / 2 + mBorderWidth, mSize / 2 + mBorderWidth, mSize / 2 + mBorderWidth, mBorderPaint);
    }

    @NonNull
    private Bitmap createCircleBitmap(Bitmap srcBitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //创建一个bitmap和一个canvas关联起来，然后再canvas上绘图的内容会绘制在bitmap上
        Bitmap targetBitmap = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(targetBitmap);

        //首先在canvas上绘制一个圆
        c.drawCircle(mSize / 2, mSize / 2, mSize / 2, paint);
        //然后设置paint的xfermode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        c.drawBitmap(srcBitmap, 0, 0, paint);
        return targetBitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int resultWidthSize = 0;
        int resultHeightSize = 0;
        switch (widthMode) {
            case MeasureSpec.AT_MOST: //wrap_content
                resultWidthSize = mSrc.getWidth() + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.EXACTLY: //match_parent dp/px:
            default:
                resultWidthSize = widthSize;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST: //wrap_content

                resultHeightSize = mSrc.getHeight() + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY: //match_parent dp/px:
            default:
                resultHeightSize = heightSize;
                break;
        }


        mSize = Math.min(resultHeightSize, resultWidthSize);
        setMeasuredDimension(mSize + 2 * mBorderWidth, mSize + 2 * mBorderWidth);
    }
}
