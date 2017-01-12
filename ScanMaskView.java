package io.geek.smartgranarydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import io.geek.smartgranarydemo.R;

/*
* 类似微信扫描二维码的蒙层View
* */
public class ScanMaskView extends View {

    private static final String TAG = "ScanMaskView";
    private Canvas mCanvas;
    private Paint mBorderPaint;
    private Paint mMaskPaint;
    private Paint mTextPaint;
    private Context mContext;
    private static final String DEFAULT_TIPS_TEXT = "请将证件号码放入方框中拍摄"; //提示文字
    private static int sDefaultBorderColor = Color.GREEN; //边框颜色
    private static float sDefaultBorderWidth; 
    private static float sDefaultTipsTextSize;
    private static float sDefaultBorderSize;
    private static float sTipsTextMarginTop;
    private float mWidth;
    private float mHeight;
    private float mFrameWidth;
    private float mFrameHeight;
    public static final float RATIO_FRAME_WIDTH_OF_PARENT_WIDTH = 3f / 5f;
    public static final float RATIO_FRAME_HEIGHT_OF_PARENT_WIDTH = (3f / 5f) * (1f / 4f);

    public ScanMaskView(Context context) {
        this(context, null);
    }

    public ScanMaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanMaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScanMaskView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        sDefaultBorderColor = Color.GREEN;
        sDefaultBorderWidth = context.getResources().getDimensionPixelSize(R.dimen.scan_border_width);
        sDefaultBorderSize = 5 * sDefaultBorderWidth;
        sDefaultTipsTextSize = context.getResources().getDimensionPixelSize(R.dimen.scan_mask_tips_text_size);
        sTipsTextMarginTop = context.getResources().getDimensionPixelSize(R.dimen.scan_mask_tips_margin_top);
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(sDefaultBorderColor);
        mBorderPaint.setDither(true);
        mBorderPaint.setStrokeWidth(sDefaultBorderWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(sDefaultTipsTextSize);
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);

        mMaskPaint = new Paint();
        mMaskPaint.setColor(Color.argb(55, 0, 0, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        //扫描框的尺寸
        mFrameWidth = mWidth * RATIO_FRAME_WIDTH_OF_PARENT_WIDTH;
        mFrameHeight = mWidth * RATIO_FRAME_HEIGHT_OF_PARENT_WIDTH;

        mCanvas = canvas;

        //绘制边框
        mCanvas.drawLine((mWidth - mFrameWidth) / 2, (mHeight - mFrameHeight) / 2, (mWidth - mFrameWidth) / 2 + sDefaultBorderSize, (mHeight - mFrameHeight) / 2, mBorderPaint);
        mCanvas.drawLine((mWidth - mFrameWidth) / 2, (mHeight - mFrameHeight) / 2, (mWidth - mFrameWidth) / 2, (mHeight - mFrameHeight) / 2 + sDefaultBorderSize, mBorderPaint);
        mCanvas.drawLine((mWidth + mFrameWidth) / 2 - sDefaultBorderSize, (mHeight - mFrameHeight) / 2, (mWidth + mFrameWidth) / 2, (mHeight - mFrameHeight) / 2, mBorderPaint);
        mCanvas.drawLine((mWidth + mFrameWidth) / 2, (mHeight - mFrameHeight) / 2, (mWidth + mFrameWidth) / 2, (mHeight - mFrameHeight) / 2 + sDefaultBorderSize, mBorderPaint);
        mCanvas.drawLine((mWidth - mFrameWidth) / 2, (mHeight + mFrameHeight) / 2, (mWidth - mFrameWidth) / 2 + sDefaultBorderSize, (mHeight + mFrameHeight) / 2, mBorderPaint);
        mCanvas.drawLine((mWidth - mFrameWidth) / 2, (mHeight + mFrameHeight) / 2 - sDefaultBorderSize, (mWidth - mFrameWidth) / 2, (mHeight + mFrameHeight) / 2, mBorderPaint);
        mCanvas.drawLine((mWidth + mFrameWidth) / 2 - sDefaultBorderSize, (mHeight + mFrameHeight) / 2, (mWidth + mFrameWidth) / 2, (mHeight + mFrameHeight) / 2, mBorderPaint);
        mCanvas.drawLine((mWidth + mFrameWidth) / 2, (mHeight + mFrameHeight) / 2 - sDefaultBorderSize, (mWidth + mFrameWidth) / 2, (mHeight + mFrameHeight) / 2, mBorderPaint);

        //绘制蒙层
        mCanvas.drawRect(new RectF(0, 0, mWidth, (mHeight - mFrameHeight) / 2), mMaskPaint);
        mCanvas.drawRect(new RectF(0, (mHeight - mFrameHeight) / 2, (mWidth - mFrameWidth) / 2, (mHeight + mFrameHeight) / 2), mMaskPaint);
        mCanvas.drawRect(new RectF((mWidth + mFrameWidth) / 2, (mHeight - mFrameHeight) / 2, mWidth, (mHeight + mFrameHeight) / 2), mMaskPaint);
        mCanvas.drawRect(new RectF(0, (mHeight + mFrameHeight) / 2, mWidth, mHeight), mMaskPaint);

        //绘制文字
        float textSize = mTextPaint.measureText(DEFAULT_TIPS_TEXT);
        mCanvas.drawText(DEFAULT_TIPS_TEXT, (mWidth - textSize) / 2, (mHeight + mFrameHeight) / 2 + sTipsTextMarginTop, mTextPaint);
    }

}
