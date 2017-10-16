package com.sheng.preferencefloatingview.floating.circle;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 绘制实体圆过渡SurfaceView中的圆
 * @author sheng
 */
public class TransitionDrawer extends BaseSplashDrawer {
    private SweepDrawer.Point[] mCircles;
    private ValueAnimator mAnimator;
    private ArgbEvaluator evaluator;
    private View view;

    private OnAnimEndListener listener;
    private float mCurrentRate;

    public TransitionDrawer(final View view, final OnAnimEndListener listener) {
        this.view = view;
        this.listener = listener;
        evaluator = new ArgbEvaluator();
    }

    public void startAnim(){
        mAnimator = ValueAnimator.ofFloat(0.5f,1f);
        mAnimator.setDuration(400);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取当前的距离百分比
                mCurrentRate = (float) animation.getAnimatedValue();
                Log.d("rate",mCurrentRate+"");
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
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        // 开始计算
        mAnimator.start();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < mCircles.length; i++) {
            SweepDrawer.Point p = mCircles[i];
            p.color = (int) evaluator.evaluate(mCurrentRate,p.startColor,p.toColor);
            paint.setColor(p.color);
            canvas.drawCircle(p.x, p.y, p.r, paint);
        }
    }

    @Override
    public void setCenterXY(int x, int y) {

    }

    public void setCircles(SweepDrawer.Point[] mCircles) {
        this.mCircles = mCircles;
    }
}