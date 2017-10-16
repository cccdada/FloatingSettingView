package com.sheng.preferencefloatingview.floating.circle;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author sheng
 */

public abstract class BaseSplashDrawer {
    public static final float START_RADIUS = 8;

    /**
     *
     * @param canvas
     */
    public abstract void draw(Canvas canvas, Paint paint);
    public abstract void setCenterXY(int x, int y);
}
