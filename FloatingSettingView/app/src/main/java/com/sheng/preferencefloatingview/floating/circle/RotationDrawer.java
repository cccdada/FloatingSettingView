package com.sheng.preferencefloatingview.floating.circle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * 绘制小圆的旋转动画
 * @author sheng
 */
public class RotationDrawer extends BaseSplashDrawer {
    private int[] mCircleColors;
    private ValueAnimator mAnimator;

    private float mCurrentRotationAngle;

    private View view;

    private OnAnimEndListener listener;

    public RotationDrawer(final View view, final OnAnimEndListener listener) {
        this.view = view;
        this.listener = listener;
        mCircleColors = new int[]{Color.parseColor("#FF59DABC"),Color.parseColor("#FF8BEAB4"),Color.parseColor("#FFFACD74")
        ,Color.parseColor("#FFFFB0B0"),Color.parseColor("#FF6BDEF5"),Color.parseColor("#FFCA61E4")};
    }

    public void startAnim(){
        // 属性动画
        mAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 10);
        mAnimator.setDuration(1500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 不断获取值 当前大圆旋转的角度
                mCurrentRotationAngle = (float) animation.getAnimatedValue();
                // 提醒View重新绘制
                view.invalidate();
            }
        });
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        // 开始计算
        mAnimator.start();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // 绘制六个小圆 坐标
        float preAngle = (float) (2 * Math.PI / mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++) {
            paint.setColor(mCircleColors[i]);
            // 初始角度 + 当前旋转的角度
            double angle = i * preAngle + mCurrentRotationAngle;
            float cx = (float) (getMetricsWidth(view.getContext())/2 + 32 * Math.cos(angle));
            float cy = (float) (getMetricsHeight(view.getContext())/2 + 32 * Math.sin(angle));
            canvas.drawCircle(cx, cy, 8, paint);
        }
    }

    @Override
    public void setCenterXY(int x, int y) {

    }

    public void cancelAnimator() {
        mAnimator.cancel();
        mAnimator = null;
    }

    public static int getMetricsWidth(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getMetricsHeight(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}