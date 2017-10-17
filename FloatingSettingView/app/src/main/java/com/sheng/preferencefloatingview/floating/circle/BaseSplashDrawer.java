package com.sheng.preferencefloatingview.floating.circle;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * @author sheng
 */

public abstract class BaseSplashDrawer {
    public static final float START_RADIUS = 20;

    /**
     * 绘制
     * @param canvas
     * @param paint
     */
    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * 设置中心点
     * @param x
     * @param y
     */
    public abstract void setCenterXY(int x, int y);
}
