package com.kingja.indexhelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
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
    private float mCellHeight;
    private float mTextHeightOffset;
    private OnIndexSelectedListener onIndexSelectedListener;
    private static final int DEFALUT_INDEX_NORMAL_COLOR = 0XFF0096CC;
    private static final int DEFALUT_INDEX_SELECTED_COLOR = 0XFFB9B9B9;
    private static final int DEFALUT_INDEX_TEXT_SIZE = 15;
    private static final int DEFALUT_INDEX_WIDTH = 24;
    private int mIndexNormalColor;
    private int mIndexSelectedColor;
    private float mIndexTextSize;
    private int touchIndex = -1;
    private int mLastIndex = -1;
    private Handler mHandler;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        mIndexNormalColor = typedArray.getColor(R.styleable.IndexView_indexColorNor, DEFALUT_INDEX_NORMAL_COLOR);
        mIndexSelectedColor = typedArray.getColor(R.styleable.IndexView_indexColorSel, DEFALUT_INDEX_SELECTED_COLOR);
        mIndexTextSize = dp2px(typedArray.getDimension(R.styleable.IndexView_indexTextSize, DEFALUT_INDEX_TEXT_SIZE));
        initIndexView();
    }

    private void initIndexView() {
        mLetterPaint = new Paint();
        mHandler = new Handler();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getExpectSize(dp2px(DEFALUT_INDEX_WIDTH), widthMeasureSpec), getMeasuredHeight());
    }

    private int getExpectSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
        }
        return result;
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
            mLetterPaint.setColor(touchIndex == i ? mIndexSelectedColor : mIndexNormalColor);
            canvas.drawText(mIndexLetters[i], mLetterX, mCellHeight * i + mTextHeightOffset, mLetterPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float clickY = event.getY();
                touchIndex = (int) (clickY / mCellHeight);
                callback();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                touchIndex = (int) (moveY / mCellHeight);
                if (touchIndex != mLastIndex) {
                    callback();
                }

                break;
            case MotionEvent.ACTION_UP:
                mLastIndex = -1;
                break;

        }
        return true;
    }

    private void callback() {
        mLastIndex = touchIndex;
        if (onIndexSelectedListener != null) {
            mHandler.removeCallbacksAndMessages(null);
            onIndexSelectedListener.onIndexSelected(touchIndex, mIndexLetters[touchIndex]);
            invalidate();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onIndexSelectedListener.onIndexSelectedCompleted(touchIndex, mIndexLetters[touchIndex]);
                }
            },1000);
        }
    }

    public interface OnIndexSelectedListener {
        void onIndexSelected(int index, String letter);
        void onIndexSelectedCompleted(int index, String letter);
    }

    public void setOnIndexSelectedListener(OnIndexSelectedListener onIndexSelectedListener) {
        this.onIndexSelectedListener = onIndexSelectedListener;
    }

    protected int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
