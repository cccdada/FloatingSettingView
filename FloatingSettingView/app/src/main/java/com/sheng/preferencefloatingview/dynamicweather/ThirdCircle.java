package com.sheng.preferencefloatingview.dynamicweather;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.BounceInterpolator;

/**
 * Created by sheng
 */
public class ThirdCircle {
    private float cx, cy, dx, dy, radius;
    private int color;
    private float percentSpeed;
    private String name;
    private float rateT;
    private float curCX, curCY;
    private boolean selected = false;
    private int selectedColor = Color.BLUE;

    private ThirdCircle thirdCircle;

    private boolean isAnim = false;

    public ThirdCircle(int color, String name, float rate, int selectedColor) {
        this.color = color;
        this.name = name;
        this.rateT = rate;
        this.selectedColor = selectedColor;
        thirdCircle = this;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getColor() {
        return selected ? selectedColor : color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPercentSpeed() {
        return percentSpeed;
    }

    public void setPercentSpeed(float percentSpeed) {
        this.percentSpeed = percentSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRate() {
        return rateT;
    }

    public void setRate(float rate) {
        this.rateT = rate;
    }

    public float getCurCX() {
        return curCX;
    }

    public void setCurCX(float curCX) {
        this.curCX = curCX;
    }

    public float getCurCY() {
        return curCY;
    }

    public void setCurCY(float curCY) {
        this.curCY = curCY;
    }

    public boolean isSelected() {
        return selected;
    }

    public void draw(Paint paint, Canvas canvas, Rect thirdRect) {
        paint.setColor(this.selectedColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{2, 2}, 0));
        paint.setStrokeWidth(1);
        canvas.drawCircle(this.getCurCX(), this.getCurCY(), this.getRadius(), paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
        paint.setColor(isSelected()?selectedColor:color);
        canvas.drawCircle(this.getCurCX(), this.getCurCY(), this.getRadius() - 1, paint);

        paint.setTextSize(40 * getRate());
        paint.getTextBounds(this.getName(), 0, this.getName().length(), thirdRect);
        paint.setColor(isSelected()?Color.WHITE:selectedColor);
        canvas.drawText(this.getName(), this.getCurCX() - thirdRect.width() / 2, this.getCurCY() + thirdRect.height() / 2, paint);
    }

    public void circleClick(boolean selected) {
        this.selected = selected;
        animClickThird();
    }

    public void animClickThird(){
        ValueAnimator animator = ValueAnimator.ofFloat(thirdCircle.getRadius()*0.7f,thirdCircle.getRadius());
        animator.setDuration(200);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float radius = (float) animation.getAnimatedValue();
                thirdCircle.setRadius(radius);
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

    public boolean isAnim() {
        return isAnim;
    }
}