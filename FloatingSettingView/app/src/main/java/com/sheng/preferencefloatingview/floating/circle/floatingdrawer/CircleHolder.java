package com.sheng.preferencefloatingview.floating.circle.floatingdrawer;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;

import com.sheng.preferencefloatingview.floating.BaseDrawer;
import com.sheng.preferencefloatingview.floating.IBaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 浮动的圆形小球具体的绘制 这个是浮动效果的核心类
 *
 * TODO 浮动效果优化：现在的效果是在一条直线上来回运动，需要优化成在一定范围内运动，在边界弹向其它方向
 * H5效果类似 http://blog.csdn.net/chen_gong1992/article/details/53313383
 * 读者可以将js关键代码翻译成java
 * @author sheng
 */
public class CircleHolder implements IBaseHolder {
    /**
     * 初始圆心坐标 偏移距离
     */
    private final float cx, cy, dx, dy;
    /**
     * 颜色
     */
    private int color;
    /**
     * 圆的移动是增长还是缩减
     */
    private boolean isGrowing = true;
    private float curPercent = 0f;
    /**
     * 速度
     */
    private final float percentSpeed;
    private String name = "虚位以待";
    private int textSize = 40;
    private Rect rect;

    public float curCX,curCY,radius;

    /**
     * 选中的小圆相对于大圆的比率
     */
    public float rate;
    /**
     * 选中圆心的坐标 偏移量
     */
    private final float smallCx,smallCy,smallDx,smallDy;
    /**
     * 选中的小圆填充色
     */
    private int smallColor;
    /**
     * 小圆运动速率
     */
    private final float smallPercentSpeed;
    public float curSmallCX,curSmallCY,smallRadius;
    /**
     * 当前是否是大圆
     */
    private boolean isNowBigCircle = true;
    /**
     * 小圆文字对应的矩形
     */
    private Rect smallRect;

    /**
     * 卫星圆中文字对应的矩形
     */
    private Rect thirdRect;
    /**
     * 初始的角度
     */
    private int angle;
    /**
     * 缓存的卫星对象
     */
    private List<ThirdCircle> thirdCircles;

    private boolean isAnim = false;

    private boolean isStop = true;

    private CircleHolder circleHolder;
    private CircleHolder(Builder builder){
        this.cx = builder.cx;
        this.cy = builder.cy;
        this.dx = builder.dx;
        this.dy = builder.dy;
        this.radius = builder.radius;
        this.percentSpeed = builder.percentSpeed;
        this.color = builder.color;
        this.name = builder.name;
        this.rect = new Rect();

        this.rate = builder.rate;
        this.smallCx = builder.cx+builder.translateX;
        this.smallCy = builder.cy+builder.translateY;
        this.smallDx = builder.dx*rate;
        this.smallDy = builder.dy*rate;
        this.smallRadius = builder.radius*rate;
        this.smallPercentSpeed = builder.percentSpeed*rate;
        this.smallColor = builder.smallColor;
        this.smallRect = new Rect();
        this.thirdRect = new Rect();
        this.angle = builder.angle;
        this.thirdCircles = builder.thirdCircles;

        circleHolder = this;
    }

    @Override
    public void updateAndDraw(Canvas canvas, Paint paint) {
        if (isStop) {
            curCX = cx;
            curCY = cy;
            return;
        }else {
            float randomPercentSpeed = isNowBigCircle ? BaseDrawer.getRandom(percentSpeed * 0.7f, percentSpeed * 1.3f):BaseDrawer.getRandom(smallPercentSpeed * 0.7f, smallPercentSpeed * 1.3f);
            if (isGrowing) {
                curPercent += randomPercentSpeed;
                if (curPercent > 1f) {
                    curPercent = 1f;
                    isGrowing = false;
                }
            } else {
                curPercent -= randomPercentSpeed;
                if (curPercent < 0f) {
                    curPercent = 0f;
                    isGrowing = true;
                }
            }
            curCX = cx + dx * curPercent;
            curCY = cy + dy * curPercent;
        }

        if (isNowBigCircle) {
            paint.setColor(smallColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(new DashPathEffect(new float[]{2, 2}, 0));
            paint.setStrokeWidth(1);
            canvas.drawCircle(curCX, curCY, radius+1, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setPathEffect(null);
            paint.setColor(color);
            canvas.drawCircle(curCX, curCY, radius, paint);

            paint.setTextSize(textSize);
            paint.getTextBounds(name, 0, name.length(), rect);
            paint.setColor(smallColor);
            canvas.drawText(name, curCX - rect.width() / 2, curCY + rect.height() / 2, paint);
        }else{
            if (null==thirdCircles) {
                return;
            }
            paint.setColor(smallColor);
            //绘制两个背景圆圈
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1.5F);
            canvas.drawCircle(cx+10 + dx * curPercent*1.3f, cy+20 + dy * curPercent*1.5f, radius, paint);
            canvas.drawCircle(cx+20 + dx * curPercent*1.4f, cy+10 + dy * curPercent*1.1f, radius, paint);
            paint.setStyle(Paint.Style.FILL);

            //斜边长度为 小圆的半径+间距+最小圆的半径
            //已经斜边和角度
            //角度的对边=斜边*sin角度
            //角度邻边=斜边*cos角度
            for (int i = 0; i < thirdCircles.size(); i++) {
                ThirdCircle circle = thirdCircles.get(i);
                if (!circle.isAnim()) {
                    circle.setRadius(radius*rate*circle.getRate());
                }
                //斜边
                float xieLine = circle.getRadius()+radius*rate+15;
                //角度=弧长/半径
                //转过的角度 l=nπr/180  n=l*180/πr
                int dAngle = (int) ((circle.getRadius()*2+15)*i*180/(Math.PI*xieLine));
                //卫星实际的角度
                int realAngle = (angle-dAngle);

                float duiLine = (float) (xieLine*Math.sin(realAngle*Math.PI/180));
                float  lingLine= (float) (xieLine*Math.cos(realAngle*Math.PI/180));

                circle.setXieLine(radius*rate+15);
                circle.setRealAngle(realAngle);
                if (!circle.isSweepAnim()) {
                    circle.setCx(curSmallCX-lingLine);
                    circle.setCy(curSmallCY-duiLine);
                }
                circle.setDx(smallDx*circle.getRate());
                circle.setDy(smallDy*circle.getRate());
                circle.setCurCX(circle.getCx()+circle.getDx()*curPercent);
                circle.setCurCY(circle.getCy()+circle.getDy()*curPercent);
                circle.draw(paint, canvas, thirdRect);

            }

            //当前小圆的坐标
            curSmallCX = smallCx + smallDx * curPercent;
            curSmallCY = smallCy + smallDy * curPercent;

            paint.setColor(smallColor);
            canvas.drawCircle(curSmallCX, curSmallCY, smallRadius, paint);

            paint.setTextSize(40*rate);
            paint.getTextBounds(name, 0, name.length(), smallRect);
            paint.setColor(Color.WHITE);
            canvas.drawText(name, curSmallCX - smallRect.width() / 2, curSmallCY + smallRect.height() / 2, paint);
        }
    }

    @Override
    public void stop(boolean flag) {
        isStop = flag;
    }

    /**
     * 构建者模式 设定相关属性
     */
    public static class Builder{
        private float cx, cy, dx, dy, radius;
        private int color;
        private float percentSpeed;
        private String name;

        private float rate;
        private int smallColor;
        /**
         * 小圆圆心相对于大圆的圆心移动的距离
         */
        private float translateX;
        private float translateY;
        private int angle;
        private List<ThirdCircle> thirdCircles = new ArrayList<>();

        public Builder setCx(float cx) {
            this.cx = cx;
            return this;
        }

        public Builder setCy(float cy) {
            this.cy = cy;
            return this;
        }

        public Builder setDx(float dx) {
            this.dx = dx;
            return this;
        }

        public Builder setDy(float dy) {
            this.dy = dy;
            return this;
        }

        public Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setSmallColor(int color) {
            this.smallColor = color;
            return this;
        }

        public Builder setRate(float rate) {
            this.rate = rate;
            return this;
        }

        public Builder setTranslateX(float x) {
            this.translateX = x;
            return this;
        }

        public Builder setTranslateY(float y) {
            this.translateY = y;
            return this;
        }

        public Builder addThirdCircle(ThirdCircle circle) {
            thirdCircles.add(circle);
            return this;
        }

        public Builder setThirdCircleAngle(int angle) {
            this.angle=angle;
            return this;
        }

        public Builder setPercentSpeed(float percentSpeed) {
            this.percentSpeed = percentSpeed;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public CircleHolder build(){
            return new CircleHolder(this);
        }
    }

    public void circleClick(boolean flag){
        if (isAnim) {
            return;
        }
        isNowBigCircle = flag;
        animClick();
        for (ThirdCircle circle:
                thirdCircles) {
            thirdSweepAnimStart(circle);
        }
    }

    public boolean isNowBigCircle(){
        return isNowBigCircle;
    }

    public List<ThirdCircle> getThirdCircles() {
        return thirdCircles;
    }

    /**
     * 点击之后的动画效果 isNowBigCircle ＝ false 大圆缩小->小圆 isNowBigCircle = true 小圆扩散->大圆 中间配上颜色的渐变
     */
    private void animClick(){
        final float ra = radius;
        final int startColor = circleHolder.color;
        final int endColor = smallColor;
        final ArgbEvaluator evaluator = new ArgbEvaluator();
        ValueAnimator animator = ValueAnimator.ofFloat(isNowBigCircle ?rate:1, isNowBigCircle ?1:rate);
        animator.setDuration(300);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rate = (float) animation.getAnimatedValue();
                int color = (int) evaluator.evaluate(rate, isNowBigCircle ?endColor:startColor, isNowBigCircle ?startColor:endColor);
                if (isNowBigCircle) {
                    circleHolder.radius = rate*ra;
                    circleHolder.color = color;
                }else {
                    smallRadius =  rate*ra;
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void thirdSweepAnimStart(final ThirdCircle circle){
        if (null==thirdCircles) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, circle.getXieLine());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float xieLine = (float) animation.getAnimatedValue();
                float duiLine = (float) (xieLine*Math.sin(circle.getRealAngle()*Math.PI/180));
                float  lingLine= (float) (xieLine*Math.cos(circle.getRealAngle()*Math.PI/180));
                circle.setCx((curSmallCX-lingLine));
                circle.setCy(curSmallCY-duiLine);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                circle.setSweepAnim(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circle.setSweepAnim(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}