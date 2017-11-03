package com.sheng.preferencefloatingview.floating.circle.splashdrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sheng
 */

public class RotationSweepView extends View {
    private Paint mPaint;
    private RotationDrawer rotationDrawer;
    private SweepDrawer sweepDrawer;
    private TransitionDrawer transitionDrawer;

    private IOnAnimEndListener IOnAnimEndListener;

    private boolean isStartSweep = false;
    private boolean isStartTrans = false;

    public void setIOnAnimEndListener(IOnAnimEndListener IOnAnimEndListener) {
        this.IOnAnimEndListener = IOnAnimEndListener;
    }

    public RotationSweepView(Context context) {
        super(context);
        init();
    }

    public RotationSweepView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotationSweepView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        rotationDrawer = new RotationDrawer(this, new IOnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                sweepDrawer.startAnim();
                isStartSweep = true;
            }
        });

        sweepDrawer = new SweepDrawer(this, new IOnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                isStartTrans = true;
                transitionDrawer.setCircles(sweepDrawer.getCircles());
                transitionDrawer.startAnim();
            }
        });
        transitionDrawer = new TransitionDrawer(this, new IOnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                IOnAnimEndListener.onAnimEnd();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rotationDrawer.draw(canvas,mPaint);
        if (isStartSweep) {
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
            canvas.drawColor(Color.WHITE);
            sweepDrawer.draw(canvas,mPaint);
        }
        if (isStartTrans) {
            transitionDrawer.draw(canvas,mPaint);
        }
    }

    public void setDistanceXYR(int i,float x,float y,float r){
        sweepDrawer.setDistanceXYR(i,x,y,r);
    }

    public void setCenterXY(int x,int y){
        rotationDrawer.setCenterXY(x,y);
        sweepDrawer.setCenterXY(x,y);
        transitionDrawer.setCenterXY(x,y);
        rotationDrawer.startAnim();
    }

}
