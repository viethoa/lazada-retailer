package com.viethoa.lazadaretailer.screens.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by VietHoa on 27/04/16.
 */
public class DrawableWithText extends Drawable {

    private Paint mPaint;
    private String mText;
    private float mFontSize;
    private int mColor;
    private boolean isAllCaps;
    private Drawable mDrawable;

    public DrawableWithText() {
        this.mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = new Rect();

        int text_height;
        int text_width;

        text_height = bounds.height();
        text_width = bounds.width();

        if (mText != null && TextUtils.isEmpty(mText)) {
            if (isAllCaps)
                mText = mText.toUpperCase();
            else
                mText = mText.toLowerCase();
            mPaint.setTypeface(Typeface.DEFAULT);
            mPaint.setTextSize(mFontSize);
            mPaint.setColor(mColor);
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            canvas.drawText(mText, -text_width, text_height / 2, mPaint);
        } else if (mDrawable != null) {
            Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
            canvas.drawBitmap(bitmap, -bitmap.getWidth(), 0, mPaint);
        }
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public DrawableWithText setmText(String mText) {
        this.mText = mText;
        return this;
    }

    public DrawableWithText setmFontSize(float mFontSize) {
        this.mFontSize = mFontSize;
        return this;
    }

    public DrawableWithText setmColor(int mColor) {
        this.mColor = mColor;
        return this;
    }

    public DrawableWithText allCaps(boolean isAllCaps) {
        this.isAllCaps = isAllCaps;
        return this;
    }

    public float getStringWidth() {
        return mPaint.measureText(mText);
    }

    public DrawableWithText setmDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        return this;
    }
}
