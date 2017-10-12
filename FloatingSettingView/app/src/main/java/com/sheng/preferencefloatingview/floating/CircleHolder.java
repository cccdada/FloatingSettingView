package com.sheng.preferencefloatingview.floating;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AnticipateOvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sheng
 */
class CircleHolder implements IBaseHolder{
    //初始圆心坐标 偏移距离
    private final float cx, cy, dx, dy;
    //颜色
    private final int color;
    private boolean isGrowing = true;
    private float curPercent = 0f;
    private final float percentSpeed;
    private String name = "虚位以待";
    private int textSize = 40;
    private Rect rect;

    float curCX,curCY,radius;

    //选中圆心的坐标
    //除了smallColor 和 小圆对大圆的比例 外 其它的属性都是由大圆决定的
    float rate;
    private final float smallCx,smallCy,smallDx,smallDy;
    private final int smallColor;//颜色
    private final float smallPercentSpeed;//速率
    float curSmallCX,curSmallCY,smallRadius;
    private boolean isBigCircle = true;
    private Rect smallRect;

    private Rect thirdRect;
    private int angle;
    private List<ThirdCircle> thirdCircles;

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
        float randomPercentSpeed = isBigCircle?BaseDrawer.getRandom(percentSpeed * 0.7f, percentSpeed * 1.3f):BaseDrawer.getRandom(smallPercentSpeed * 0.7f, smallPercentSpeed * 1.3f);
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

        if (isBigCircle) {
//            int curColor = BaseDrawer.convertAlphaColor(alpha * (Color.alpha(color) / 255f), color);
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
            paint.setColor(smallColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1.5F);
            canvas.drawCircle(cx+10 + dx * curPercent*1.3f, cy+20 + dy * curPercent*1.5f, radius, paint);
            canvas.drawCircle(cx+20 + dx * curPercent*1.4f, cy+10 + dy * curPercent*1.1f, radius, paint);
            paint.setStyle(Paint.Style.FILL);

            curSmallCX = smallCx + smallDx * curPercent;
            curSmallCY = smallCy + smallDy * curPercent;

//            int curColor = BaseDrawer.convertAlphaColor(alpha * (Color.alpha(color) / 255f), smallColor);
            paint.setColor(smallColor);
            canvas.drawCircle(curSmallCX, curSmallCY, smallRadius, paint);

            paint.setTextSize(40*rate);
            paint.getTextBounds(name, 0, name.length(), smallRect);
            paint.setColor(Color.WHITE);
            canvas.drawText(name, curSmallCX - smallRect.width() / 2, curSmallCY + smallRect.height() / 2, paint);
            if (null==thirdCircles) {
                return;
            }
            //斜边长度为 小圆的半径+间距+最小圆的半径
            //已经斜边和角度
            //角度的对边=斜边*sin角度
            //角度邻边=斜边*cos角度
            for (int i = 0; i < thirdCircles.size(); i++) {
                ThirdCircle circle = thirdCircles.get(i);
                if (!circle.isAnim()) {
                    circle.setRadius(smallRadius*circle.getRate());
                }
                float xieLine = circle.getRadius()+smallRadius+15;
                //角度=弧长/半径
                //转过的角度 l=nπr/180  n=l*180/πr

                int dAngle = (int) ((circle.getRadius()*2+15)*i*180/(Math.PI*xieLine));
                int realAngle = (angle-dAngle);

                float duiLine = (float) (xieLine*Math.sin(realAngle*Math.PI/180));
                float  lingLine= (float) (xieLine*Math.cos(realAngle*Math.PI/180));


                circle.setCx(curSmallCX-lingLine);
                circle.setCy(curSmallCY-duiLine);
                circle.setDx(smallDx*circle.getRate());
                circle.setDy(smallDy*circle.getRate());
                circle.setCurCX(circle.getCx()+circle.getDx()*curPercent);
                circle.setCurCY(circle.getCy()+circle.getDy()*curPercent);
                circle.draw(paint,canvas,thirdRect);
            }
        }
    }

    static class Builder{
        private float cx, cy, dx, dy, radius;
        private int color;
        private float percentSpeed;
        private String name;

        private float rate;
        private int smallColor;
        private float translateX;
        private float translateY;
        private int angle;
        private List<ThirdCircle> thirdCircles = new ArrayList<>();

        Builder setCx(float cx) {
            this.cx = cx;
            return this;
        }

        Builder setCy(float cy) {
            this.cy = cy;
            return this;
        }

        Builder setDx(float dx) {
            this.dx = dx;
            return this;
        }

        Builder setDy(float dy) {
            this.dy = dy;
            return this;
        }

        Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        Builder setColor(int color) {
            this.color = color;
            return this;
        }

        Builder setSmallColor(int color) {
            this.smallColor = color;
            return this;
        }

        Builder setRate(float rate) {
            this.rate = rate;
            return this;
        }

        Builder setTranslateX(float x) {
            this.translateX = x;
            return this;
        }

        Builder setTranslateY(float y) {
            this.translateY = y;
            return this;
        }

        Builder addThirdCircle(ThirdCircle circle) {
            thirdCircles.add(circle);
            return this;
        }

        Builder setThirdCircleAngle(int angle) {
            this.angle=angle;
            return this;
        }

        Builder setPercentSpeed(float percentSpeed) {
            this.percentSpeed = percentSpeed;
            return this;
        }

        Builder setName(String name) {
            this.name = name;
            return this;
        }

        CircleHolder build(){
            return new CircleHolder(this);
        }
    }

    void circleClick(boolean flag){
        isBigCircle = flag;
        animClick(isBigCircle);
    }

    boolean isBigCircle(){
        return isBigCircle;
    }

    List<ThirdCircle> getThirdCircles() {
        return thirdCircles;
    }

    void animClick(boolean flag){
        final float ra = radius;
        ValueAnimator animator = ValueAnimator.ofFloat(rate,1);
        animator.setDuration(200);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleHolder.radius = (float) animation.getAnimatedValue()*ra;
            }
        });
        animator.start();
    }

}