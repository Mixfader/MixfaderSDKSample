/*
 * Copyright (C) 2016 Djit SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.djit.mixfader.sample.mixfader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.djit.mixfader.sample.R;

/**
 * The {@link View} that displays the MixFader with the current progress.
 */
public class MixFaderCrossFaderView extends View {

    private static final float SLIDER_DELTA_X_IN_DIP = 140f;
    private static final float SLIDER_VERTICAL_OFFSET_IN_DIP = -8f;
    private static final float SLIDER_STARTING_X_OFFSET_IN_DIP = 70f;

    /**
     * The current progress.
     * <p/>
     * Range: [0, 1].
     */
    private float mCurrentProgress;

    private Bitmap mBaseBitmap;
    private Bitmap mSliderBitmap;

    private float mMeasuredHeight;
    private float mMeasuredWidth;

    private Paint mBitmapPaint;
    private float mBaseTop;
    private float mBaseLeft;
    private float mSliderTop;

    private float mSliderVerticalOffset;
    private float mSliderStartingX;
    private float mSliderDeltaX;

    private DisplayMetrics mDisplayMetrics;

    public MixFaderCrossFaderView(Context context) {
        super(context);
        initUi(context);
    }

    public MixFaderCrossFaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi(context);
    }

    public MixFaderCrossFaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUi(context);
    }

    private void initUi(Context context) {
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mBitmapPaint = new Paint();
        mBitmapPaint.setFilterBitmap(true);

        mBaseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mixfader_view_bot);
        mSliderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mixfader_view_top);

        mSliderVerticalOffset = dipToPx(mDisplayMetrics, SLIDER_VERTICAL_OFFSET_IN_DIP);
        mSliderDeltaX = dipToPx(mDisplayMetrics, SLIDER_DELTA_X_IN_DIP);
        mCurrentProgress = 0.5f;
    }

    /**
     * Set the {@link #mCurrentProgress}.
     *
     * @param progress Must be between [0, 1].
     */
    /* Package */ void setProgress(float progress) {
        if (progress < 0f || progress > 1f) {
            throw new IllegalArgumentException("Progress must be in range [0,1]");
        }

        mCurrentProgress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();

        final float midX = mMeasuredWidth / 2;
        final float midY = mMeasuredHeight / 2;

        if (mBaseBitmap != null && mSliderBitmap != null) {
            final float realSliderHeight = mSliderBitmap.getHeight() + mSliderVerticalOffset;
            final float bitmapHalfHeight = (mBaseBitmap.getHeight() + realSliderHeight) / 2;
            final float baseTopDelta = realSliderHeight - bitmapHalfHeight;
            final float sliderTopDelta = -bitmapHalfHeight;

            mBaseLeft = midX - mBaseBitmap.getWidth() / 2;
            mBaseTop = midY + baseTopDelta;
            mSliderTop = midY + sliderTopDelta;
            mSliderStartingX = mBaseLeft + dipToPx(mDisplayMetrics, SLIDER_STARTING_X_OFFSET_IN_DIP);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBase(canvas);
        drawTop(canvas);
    }

    private void drawBase(Canvas canvas) {
        // Check for null because some devices fail to decode the bitmap.
        if (mBaseBitmap != null) {
            canvas.drawBitmap(mBaseBitmap,
                    mBaseLeft,
                    mBaseTop,
                    mBitmapPaint);
        }
    }

    private void drawTop(Canvas canvas) {
        // Check for null because some devices fail to decode the bitmap.
        if (mSliderBitmap != null) {
            canvas.drawBitmap(mSliderBitmap,
                    mSliderStartingX + mCurrentProgress * mSliderDeltaX,
                    mSliderTop,
                    mBitmapPaint);
        }
    }

    private static float dipToPx(DisplayMetrics displayMetrics, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }
}