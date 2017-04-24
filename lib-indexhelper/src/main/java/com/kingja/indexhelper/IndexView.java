package com.kingja.indexhelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description:TODO
 * Create Time:2017/4/24 9:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IndexView extends View {
    private final String[] mIndexLetters = {"A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    private Paint mLetterPaint;
    private float mLetterX;
    private float mLetterY;
    private float mCellHeight;
    private float mTextHeightOffset;
    private OnIndexSelectedListener onIndexSelectedListener;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initIndexView();
    }

    private void initIndexView() {
        mLetterPaint = new Paint();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float letterWidth = mLetterPaint.measureText("A");
        mCellHeight = measuredHeight * 1.0f / (mIndexLetters.length * 1.0f);
        mTextHeightOffset = -(mLetterPaint.ascent() + mLetterPaint.descent()) * 0.5f +
                mCellHeight * 0.5f;
        mLetterX = measuredWidth * 0.5f - letterWidth * 0.5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mIndexLetters.length; i++) {
            canvas.drawText(mIndexLetters[i], mLetterX, mCellHeight * i + mTextHeightOffset, mLetterPaint);
        }
    }

    private int mLastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float clickY = event.getY();
                int clickIndex = (int) (clickY / mCellHeight);
                Log.e("onTouchEvent", "clickIndex: " + mIndexLetters[clickIndex]);
                mLastIndex = clickIndex;
                if (onIndexSelectedListener != null) {
                    onIndexSelectedListener.onIndexSelected(clickIndex,mIndexLetters[clickIndex]);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                int moveIndex = (int) (moveY / mCellHeight);
                if (moveIndex != mLastIndex) {
                    mLastIndex = moveIndex;
                    if (onIndexSelectedListener != null) {
                        onIndexSelectedListener.onIndexSelected(moveIndex,mIndexLetters[moveIndex]);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                mLastIndex = -1;
                break;

        }
        return true;
    }

    public interface OnIndexSelectedListener {
        void onIndexSelected(int index,String letter);
    }

    public void setOnIndexSelectedListener(OnIndexSelectedListener onIndexSelectedListener) {
        this.onIndexSelectedListener = onIndexSelectedListener;
    }
}
