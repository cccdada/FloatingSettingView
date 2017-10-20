package com.sheng.preferencefloatingview.floating;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 统一的绘制类的接口
 * @author sheng
 */
public interface IBaseHolder {
    /**
     * 子类需要覆写的方法 目的是将View的画布和画笔传递给 实现对象 在对象类中完成具体的绘制逻辑
     * @param canvas 画布
     * @param paint 画笔
     */
    void updateAndDraw(Canvas canvas,Paint paint);

    /**
     * 暂停/继续
     * @param flag
     */
    void stop(boolean flag);
}
