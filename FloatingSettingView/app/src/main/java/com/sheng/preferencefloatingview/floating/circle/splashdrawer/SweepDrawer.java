package com.sheng.preferencefloatingview.floating.circle.splashdrawer;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 绘制小圆的扩散动画
 * @author sheng
 */
public class SweepDrawer extends BaseSplashDrawer {
    private Point[] mCircles;
    private ValueAnimator mAnimator;

    private float mCurrentRate;

    private View view;

    private IOnAnimEndListener listener;

    private final float START_RADIUS = 8;

    private int centerX,centerY;

    public SweepDrawer(final View view, final IOnAnimEndListener listener) {
        this.view = view;
        this.listener = listener;

        mCircles = new Point[]{new Point(getX(0),getY(0),START_RADIUS,Color.parseColor("#FF59DABC"),Color.parseColor("#FFE2FFF8")),new Point(getX(1),getY(1),START_RADIUS,Color.parseColor("#FF8BEAB4"),Color.parseColor("#FFECFCF3"))
                ,new Point(getX(2),getY(2),START_RADIUS,Color.parseColor("#FFFACD74"),Color.parseColor("#FFFAF6EE")),new Point(getX(3),getY(3),START_RADIUS,Color.parseColor("#FFFFB0B0"),Color.parseColor("#FFFDF8F8"))
                ,new Point(getX(4),getY(4),START_RADIUS,Color.parseColor("#FF6BDEF5"),Color.parseColor("#FFE7F7FA")),new Point(getX(5),getY(5),START_RADIUS,Color.parseColor("#FFCA61E4"),Color.parseColor("#FFF3E2F7"))};
    }

    private float getX(int i){
        double angle = i * (float) (2 * Math.PI / 6);
        float cx = (float) (getMetricsWidth(view.getContext())/2 + 32 * Math.cos(angle));
        return cx;
    }
    private float getY(int i){
        double angle = i * (float) (2 * Math.PI / 6);
        float cy = (float) (getMetricsHeight(view.getContext())/2 + 32 * Math.sin(angle));
        return cy;
    }
    
    public void setDistanceXYR(int i,float x,float y,float r){
        for (int j = 0; j < mCircles.length; j++) {
            Point p = mCircles[i];
            p.setDistanceX(x-p.x);
            p.setDistanceY(y-p.y);
            p.setDesR(r);
        }
    }

    public void startAnim(){
        mAnimator = ValueAnimator.ofFloat(0, 1f);
        mAnimator.setDuration(500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取当前的距离百分比
                mCurrentRate = (float) animation.getAnimatedValue();
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
            Point p = mCircles[i];
            paint.setColor(p.color);
            p.x = getX(i)+p.distanceX*mCurrentRate;
            p.y = getY(i)+p.distanceY*mCurrentRate;
            p.r = START_RADIUS+p.disR*mCurrentRate;
            canvas.drawCircle(p.x, p.y, p.r, paint);
        }
    }

    @Override
    public void setCenterXY(int x, int y) {
        centerX = x;
        centerY = y;
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

    public class Point{
        public float x;
        public float y;
        public float r;
        public int color;
        public float distanceY;
        public float distanceX;
        public float desR;
        public float disR;
        public int startColor;
        public int toColor;

        public Point(float x, float y, float r,int color,int toColor) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.color = this.startColor = color;
            this.toColor = toColor;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void setR(float r) {
            this.r = r;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void setDistanceY(float distanceY) {
            this.distanceY = distanceY;
        }

        public void setDistanceX(float distanceX) {
            this.distanceX = distanceX;
        }

        public void setDesR(float desR) {
            this.desR = desR;
            disR = desR-START_RADIUS;
        }

        public void setToColor(int toColor) {
            this.toColor = toColor;
        }
    }

    public Point[] getCircles() {
        return mCircles;
    }
}