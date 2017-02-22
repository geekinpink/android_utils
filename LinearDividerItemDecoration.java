import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import cn.com.ssii.android.util.DisplayUtil;
import cn.com.ssii.geoannotation.thirdsurvey.R;

/**
 * Created by Administrator on 2017/2/21.
 */

public class LinearDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    public static final int ORIENTATION_HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int ORIENTATION_VETICAL = LinearLayout.VERTICAL;

    private int mOrientation;
    private Drawable mDivider;
    private static final int[] attrs = new int[]{
            android.R.attr.listDivider
    };

    public LinearDividerItemDecoration(Context context, int orientation) {
        mContext = context;
        setOrientation(orientation);
        final TypedArray a = mContext.obtainStyledAttributes(attrs);
        mDivider = a.getDrawable(0);
    }

    public void setOrientation(int orientation) {
        if (orientation != ORIENTATION_HORIZONTAL && orientation != ORIENTATION_VETICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == ORIENTATION_VETICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
            int top = v.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);

            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        for (int i = 0; i < childCount; i++) {
            View v = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) v.getLayoutParams();
            int left = v.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);

            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == ORIENTATION_VETICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicHeight(), 0);
        }
    }
}
