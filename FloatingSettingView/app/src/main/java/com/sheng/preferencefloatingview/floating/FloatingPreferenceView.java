package com.sheng.preferencefloatingview.floating;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;

import com.sheng.preferencefloatingview.floating.BaseDrawer.Type;

public class FloatingPreferenceView extends SurfaceView implements SurfaceHolder.Callback {

    static final String TAG = FloatingPreferenceView.class.getSimpleName();
    private DrawThread mDrawThread;
    private PreferenceFloatingDrawer mDrawer;
    private long currentMS;
    private float DownX;
    private float DownY;
    private float moveX;
    private float moveY;

    public FloatingPreferenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private BaseDrawer preDrawer, curDrawer;
    private float curDrawerAlpha = 0.5f;
    private Type curType = Type.DEFAULT;
    private int mWidth, mHeight;

    private void init() {
        curDrawerAlpha = 0f;
        mDrawThread = new DrawThread();
        final SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        mDrawThread.start();
    }

    private void setDrawer(BaseDrawer baseDrawer) {
        if (baseDrawer == null) {
            return;
        }
        curDrawerAlpha = 0f;
        if (this.curDrawer != null) {
            this.preDrawer = curDrawer;
        }
        this.curDrawer = baseDrawer;
        mDrawer = ((PreferenceFloatingDrawer) curDrawer);
        updateDrawerSize(getWidth(), getHeight());
        invalidate();
    }

    public void setDrawerType(Type type) {
        if (type == null) {
            return;
        }
        if (type != curType) {
            curType = type;
            setDrawer(BaseDrawer.makeDrawerByType(getContext(), curType));
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDrawerSize(w, h);
        mWidth = w;
        mHeight = h;
    }

    private void updateDrawerSize(int w, int h) {
        if (w == 0 || h == 0) {
            return;
        }// 必须加锁，因为在DrawThread.drawSurface的时候调用的是各种Drawer的绘制方法
        // 绘制的时候会遍历内部的各种holder
        if (this.curDrawer != null) {
            synchronized (curDrawer) {
                if (this.curDrawer != null) {
                    curDrawer.setSize(w, h);
                }
            }
        }
        if (this.preDrawer != null) {
            synchronized (preDrawer) {
                if (this.preDrawer != null) {
                    preDrawer.setSize(w, h);
                }
            }
        }

    }

    private boolean drawSurface(Canvas canvas) {
        final int w = mWidth;
        final int h = mHeight;
        if (w == 0 || h == 0) {
            return true;
        }
        boolean needDrawNextFrame = false;
        if (curDrawer != null) {
            curDrawer.setSize(w, h);
            needDrawNextFrame = curDrawer.draw(canvas, curDrawerAlpha);
        }
        if (preDrawer != null && curDrawerAlpha < 1f) {
            needDrawNextFrame = true;
            preDrawer.setSize(w, h);
            preDrawer.draw(canvas, 1f - curDrawerAlpha);
        }
        if (curDrawerAlpha < 1f) {
            curDrawerAlpha += 0.04f;
            if (curDrawerAlpha > 1) {
                curDrawerAlpha = 1f;
                preDrawer = null;
            }
        }
        return needDrawNextFrame;
    }

    public void onResume() {
        // Let the drawing thread resume running.
        synchronized (mDrawThread) {
            mDrawThread.mRunning = true;
            mDrawThread.notify();
        }
        Log.i(TAG, "onResume");
    }

    public void onPause() {
        // Make sure the drawing thread is not running while we are paused.
        synchronized (mDrawThread) {
            mDrawThread.mRunning = false;
            mDrawThread.notify();
        }
        Log.i(TAG, "onPause");
    }

    public void onDestroy() {
        // Make sure the drawing thread goes away.
        synchronized (mDrawThread) {
            mDrawThread.mQuit = true;
            mDrawThread.notify();
        }
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                DownX = e.getX();
                DownY = e.getY();
                moveX = 0;
                moveY = 0;
                currentMS = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX += Math.abs(e.getX() - DownX);//X轴距离
                moveY += Math.abs(e.getY() - DownY);//y轴距离
                DownX = e.getX();
                DownY = e.getY();
                break;
            case MotionEvent.ACTION_UP:
                long moveTime = System.currentTimeMillis() - currentMS;//移动时间
                //判断是否继续传递信号
                if (moveTime > ViewConfiguration.getTapTimeout() || (moveX > 20 || moveY > 20)) {
                    //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
                    break;
                } else {
                    for (IBaseHolder hold : mDrawer.getHolders()) {
                        if (hold instanceof CircleHolder) {
                            CircleHolder holder = ((CircleHolder) hold);
                            //点击位置x坐标与圆心的x坐标的距离
                            int distanceX = (int) Math.abs(holder.isBigCircle() ? holder.curCX - DownX : holder.curSmallCX - DownX);
                            //点击位置y坐标与圆心的y坐标的距离
                            int distanceY = (int) Math.abs(holder.isBigCircle() ? holder.curCY - DownY : holder.curSmallCY - DownY);
                            //点击位置与圆心的直线距离
                            int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                            //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
                            if (distanceZ <= (holder.isBigCircle() ? holder.radius : holder.radius * holder.rate)) {
                                holder.circleClick(!holder.isBigCircle());
                            } else {
                                for (ThirdCircle circle : holder.getThirdCircles()) {
                                    int distanceTX = (int) Math.abs(circle.getCurCX() - DownX);
                                    int distanceTY = (int) Math.abs(circle.getCurCY() - DownY);
                                    int distanceTZ = (int) Math.sqrt(Math.pow(distanceTX, 2) + Math.pow(distanceTY, 2));
                                    if (distanceTZ <= circle.getRadius()) {
                                        circle.circleClick(!circle.isSelected());
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(e);//继续执行后面的代码
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Tell the drawing thread that a surface is available.
        synchronized (mDrawThread) {
            mDrawThread.mSurface = holder;
            mDrawThread.notify();
        }
        Log.i(TAG, "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // We need to tell the drawing thread to stop, and block until
        // it has done so.
        synchronized (mDrawThread) {
            mDrawThread.mSurface = holder;
            mDrawThread.notify();
            while (mDrawThread.mActive) {
                try {
                    mDrawThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        holder.removeCallback(this);
        Log.i(TAG, "surfaceDestroyed");
    }

    private class DrawThread extends Thread {
        // These are protected by the Thread's lock.
        SurfaceHolder mSurface;
        boolean mRunning;
        boolean mActive;
        boolean mQuit;

        @Override
        public void run() {
            while (true) {
                // Log.i(TAG, "DrawThread run..");
                // Synchronize with activity: block until the activity is ready
                // and we have a surface; report whether we are active or
                // inactive
                // at this point; exit thread when asked to quit.
                synchronized (this) {
                    while (mSurface == null || !mRunning) {
                        if (mActive) {
                            mActive = false;
                            notify();
                        }
                        if (mQuit) {
                            return;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }

                    if (!mActive) {
                        mActive = true;
                        notify();
                    }
                    final long startTime = AnimationUtils.currentAnimationTimeMillis();
                    //TimingLogger logger = new TimingLogger("DrawThread");
                    // Lock the canvas for drawing.
                    Canvas canvas = mSurface.lockCanvas();
                    //logger.addSplit("lockCanvas");

                    if (canvas != null) {
                        canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
                        // Update graphics.

                        drawSurface(canvas);
                        //logger.addSplit("drawSurface");
                        // All done!
                        mSurface.unlockCanvasAndPost(canvas);
                        //logger.addSplit("unlockCanvasAndPost");
                        //logger.dumpToLog();
                    } else {
                        Log.i(TAG, "Failure locking canvas");
                    }
                    final long drawTime = AnimationUtils.currentAnimationTimeMillis() - startTime;
                    final long needSleepTime = 16 - drawTime;
                    //Log.i(TAG, "drawSurface drawTime->" + drawTime + " needSleepTime->" + Math.max(0, needSleepTime));// needSleepTime);
                    if (needSleepTime > 0) {
                        try {
                            Thread.sleep(needSleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

}
