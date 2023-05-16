package com.timwe.tti2sdk.ui.customViews;

import static com.timwe.utils.ExtensionsKt.convertDpToPixel;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.timwe.tti2sdk.R;


public class ScratchCard extends View {

    private Drawable drawable;
    private float scratchWidth;
    private Bitmap bitmap;
    //A `Canvas` is a class holds the "draw" calls. To draw something, you
    // need 4 basic components: 1. A Bitmap to hold the pixels, 2. a Canvas
    // to host the draw calls (writing into the bitmap), 3. a drawing
    // primitive (e.g. Rect, Path, text, Bitmap), and 4. a paint
    // (to describe the colors and styles for the drawing).
    private Canvas canvas;
    //`Path` is a class that encapsulates compound geometric paths
    // comprising straight line segments, quadratic curves, and cubic curves.
    private Path path;
    //`Paint` is a class that holds the style and color information about
    // how to draw geometries, text and bitmaps.
    private Paint innerPaint;
    private Paint outerPaint;
    private OnScratchListener listener;

    public void setCanScratch(boolean canScratch) {
        this.canScratch = canScratch;
    }

    private boolean canScratch = true;

    private boolean gameStarted = false;

    public interface OnScratchListener {
        void onStartedScratch(float visiblePercent);

        void onScratch(ScratchCard scratchCard, float visiblePercent);
    }

    public ScratchCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        resolveAttr(context, attrs);
    }

    public ScratchCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        resolveAttr(context, attrs);
    }

    public ScratchCard(Context context) {
        super(context);
        resolveAttr(context, null);
    }

    private void resolveAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchCard);
        drawable = typedArray.getDrawable(R.styleable.ScratchCard_scratchDrawable);
        scratchWidth = typedArray.getDimension(R.styleable.ScratchCard_scratchWidth, convertDpToPixel(context, 20));
        typedArray.recycle();
    }

    public void setScratchDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setScratchWidth(float width) {
        scratchWidth = width;
    }

    public void setOnScratchListener(OnScratchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        if (bitmap != null)
            bitmap.recycle();

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        if (drawable != null) {
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            drawable.draw(canvas);
        } else {
            canvas.drawColor(0xFFC0C0C0);
        }

        if (path == null) {
            path = new Path();
        }

        if (innerPaint == null) {
            innerPaint = new Paint();
            innerPaint.setAntiAlias(true);
            innerPaint.setDither(true);
            innerPaint.setStyle(Paint.Style.STROKE);
            innerPaint.setFilterBitmap(true);
            innerPaint.setStrokeJoin(Paint.Join.ROUND);
            innerPaint.setStrokeCap(Paint.Cap.ROUND);
            innerPaint.setStrokeWidth(scratchWidth);
            innerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }

        if (outerPaint == null) {
            outerPaint = new Paint();
        }
    }

    private float mLastTouchX;
    private float mLastTouchY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canScratch) return true;
        float currentTouchX = event.getX();
        float currentTouchY = event.getY();

        if (!gameStarted) {
            if (listener != null) {
                float visiblePercentage = getVisiblePercentage();
                listener.onStartedScratch(visiblePercentage);
            }
            gameStarted = true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(currentTouchX - mLastTouchX);
                float dy = Math.abs(currentTouchY - mLastTouchY);
                if (dx >= 4 || dy >= 4) {
                    float x1 = mLastTouchX;
                    float y1 = mLastTouchY;
                    float x2 = (currentTouchX + mLastTouchX) / 2;
                    float y2 = (currentTouchY + mLastTouchY) / 2;
                    path.quadTo(x1, y1, x2, y2);
                }
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(currentTouchX, currentTouchY);
                if (listener != null) {
                    float visiblePercentage = getVisiblePercentage();
                    listener.onScratch(this, visiblePercentage * 100);
                }
                break;
        }

        canvas.drawPath(path, innerPaint);

        mLastTouchX = currentTouchX;
        mLastTouchY = currentTouchY;

        //Invalidate the view.
        invalidate();
        return true;
    }

    private float getVisiblePercentage() {
        float visiblePercentage;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int total = width * height;
        int count = 0;
        for (int i = 0; i < width; i += 3) {
            for (int j = 0; j < height; j += 3) {
                if (bitmap.getPixel(i, j) == 0x00000000)
                    count++;
            }
        }
        visiblePercentage = ((float) count) / total * 9;
        return visiblePercentage;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, outerPaint);

        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

}

