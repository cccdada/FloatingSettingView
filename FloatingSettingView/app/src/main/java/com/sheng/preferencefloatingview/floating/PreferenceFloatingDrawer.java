package com.sheng.preferencefloatingview.floating;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.sheng.preferencefloatingview.floating.circle.CircleHolder;
import com.sheng.preferencefloatingview.floating.circle.IBaseHolder;
import com.sheng.preferencefloatingview.floating.circle.ThirdCircle;

import java.util.ArrayList;

/**
 * @author sheng
 */
class PreferenceFloatingDrawer extends BaseDrawer {
    final private ArrayList<IBaseHolder> holders = new ArrayList<>();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private long currentMS;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    PreferenceFloatingDrawer(Context context) {
        super(context);
    }

    @Override
    protected void setSize(int width, int height) {
        super.setSize(width, height);
        if (holders.size() == 0) {
            //这里你可以添加各种自定义的holder 但是如果你需要点击事件的话  你需要在
            holders.add(new CircleHolder.Builder()
                    .setCx(0.35f * width)
                    .setCy(0.3f * width)
                    .setDx(0.06f * width)
                    .setDy(0.022f * width)
                    .setRadius(0.11f * width)
                    .setPercentSpeed(0.0019f)
                    .setColor(Color.parseColor("#FFE2FFF8"))
                    .setName("情感星座")
                    .setRate(0.7f)
                    .setSmallColor(Color.parseColor("#FF59DABC"))
                    .setTranslateX(-20f)
                    .setTranslateY(50f)
                    .setThirdCircleAngle(135)
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE2FFF8"),"星座",0.3f,Color.parseColor("#FF59DABC")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE2FFF8"),"情感",0.4f,Color.parseColor("#FF59DABC")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE2FFF8"),"心理",0.5f,Color.parseColor("#FF59DABC")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE2FFF8"),"两性",0.6f,Color.parseColor("#FF59DABC")))
                    .build());
            holders.add(new CircleHolder.Builder()
                    .setCx(0.75f * width)
                    .setCy(0.32f * width)
                    .setDx(0.06f * width)
                    .setDy(0.022f * width)
                    .setRadius(0.105f * width)
                    .setPercentSpeed(0.0017f)
                    .setColor(Color.parseColor("#FFECFCF3"))
                    .setName("科技数码")
                    .setRate(0.7f)
                    .setSmallColor(Color.parseColor("#FF8BEAB4"))
                    .setTranslateX(0)
                    .setTranslateY(50f)
                    .setThirdCircleAngle(160)
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFECFCF3"),"互联网",0.5f,Color.parseColor("#FF8BEAB4")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFECFCF3"),"科技",0.5f,Color.parseColor("#FF8BEAB4")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFECFCF3"),"数码",0.5f,Color.parseColor("#FF8BEAB4")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFECFCF3"),"手机",0.5f,Color.parseColor("#FF8BEAB4")))
                    .build());
            holders.add(new CircleHolder.Builder()
                    .setCx(0.25f * width)
                    .setCy(0.57f * width)
                    .setDx(-0.05f * width)
                    .setDy(0.05f * width)
                    .setRadius(0.14f * width)
                    .setPercentSpeed(0.002f)
                    .setColor(Color.parseColor("#FFFAF6EE"))
                    .setName("体育赛事")
                    .setRate(0.7f)
                    .setSmallColor(Color.parseColor("#FFFACD74"))
                    .setTranslateX(0)
                    .setTranslateY(50f)
                    .setThirdCircleAngle(60)
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFAF6EE"),"羽毛球",0.5f,Color.parseColor("#FFFACD74")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFAF6EE"),"拳击",0.5f,Color.parseColor("#FFFACD74")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFAF6EE"),"足球",0.5f,Color.parseColor("#FFFACD74")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFAF6EE"),"乒乓球",0.5f,Color.parseColor("#FFFACD74")))
                    .build());
            holders.add(new CircleHolder.Builder()
                    .setCx(0.68f * width)
                    .setCy(0.75f * width)
                    .setDx(0.08f * width)
                    .setDy(-0.035f * width)
                    .setRadius(0.12f * width)
                    .setPercentSpeed(0.0025f)
                    .setColor(Color.parseColor("#FFFDF8F8"))
                    .setName("生活休闲")
                    .setRate(0.7f)
                    .setSmallColor(Color.parseColor("#FFFFB0B0"))
                    .setTranslateX(0)
                    .setTranslateY(50f)
                    .setThirdCircleAngle(270)
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFDF8F8"),"生活",0.5f,Color.parseColor("#FFFFB0B0")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFDF8F8"),"菜谱",0.5f,Color.parseColor("#FFFFB0B0")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFDF8F8"),"旅游",0.5f,Color.parseColor("#FFFFB0B0")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFFDF8F8"),"美食",0.5f,Color.parseColor("#FFFFB0B0")))
                    .build());
            holders.add(new CircleHolder.Builder()
                    .setCx(0.42f * width)
                    .setCy(0.8f * width)
                    .setDx(0.06f * width)
                    .setDy(-0.025f * width)
                    .setRadius(0.1f * width)
                    .setPercentSpeed(0.0020f)
                    .setColor(Color.parseColor("#FFE7F7FA"))
                    .setName("育儿护理")
                    .setRate(0.7f)
                    .setSmallColor(Color.parseColor("#FF6BDEF5"))
                    .setTranslateX(0)
                    .setTranslateY(50f)
                    .setThirdCircleAngle(0)
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE7F7FA"),"育儿",0.5f,Color.parseColor("#FF6BDEF5")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE7F7FA"),"亲子",0.5f,Color.parseColor("#FF6BDEF5")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE7F7FA"),"备孕",0.5f,Color.parseColor("#FF6BDEF5")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFE7F7FA"),"孕期",0.5f,Color.parseColor("#FF6BDEF5")))
                    .build());
            holders.add(new CircleHolder.Builder()
                    .setCx(0.57f * width)
                    .setCy(0.5f * width)
                    .setDx(-0.05f * width)
                    .setDy(0.05f * width)
                    .setRadius(0.13f * width)
                    .setPercentSpeed(0.00185f)
                    .setColor(Color.parseColor("#FFF3E2F7"))
                    .setName("时政资讯")
                    .setRate(0.7f)
                    .setSmallColor(Color.parseColor("#FFCA61E4"))
                    .setTranslateX(0)
                    .setTranslateY(50f)
                    .setThirdCircleAngle(100)
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFF3E2F7"),"社会",0.5f,Color.parseColor("#FFCA61E4")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFF3E2F7"),"军事",0.5f,Color.parseColor("#FFCA61E4")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFF3E2F7"),"法制",0.5f,Color.parseColor("#FFCA61E4")))
                    .addThirdCircle(new ThirdCircle(Color.parseColor("#FFF3E2F7"),"国际",0.5f,Color.parseColor("#FFCA61E4")))
                    .build());
        }
    }

    @Override
    public boolean drawGraphics(Canvas canvas, float alpha) {
        for (IBaseHolder holder : this.holders) {
            holder.updateAndDraw(canvas, paint);
        }
        return true;
    }

    @Override
    protected int[] getFloatingBackgroundGradient() {
        return FloatingBackground.TRANS;
    }

    @Override
    protected void onTouch(MotionEvent e) {
        super.onTouch(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                downY = e.getY();
                moveX = 0;
                moveY = 0;
                currentMS = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * X轴距离
                 */
                moveX += Math.abs(e.getX() - downX);
                /**
                 * y轴距离
                 */
                moveY += Math.abs(e.getY() - downY);
                downX = e.getX();
                downY = e.getY();
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 移动时间
                 */
                long moveTime = System.currentTimeMillis() - currentMS;
                //判断是否继续传递信号
                if (isClick(moveTime)) {
                    //不再执行后面的事件，在这句前可写要执行的触摸相关代码。点击事件是发生在触摸弹起后
                    break;
                } else {
                    for (IBaseHolder hold : getHolders()) {
                        if (hold instanceof CircleHolder) {
                            CircleHolder holder = ((CircleHolder) hold);
                            //点击位置x坐标与圆心的x坐标的距离
                            int distanceX = (int) Math.abs(holder.isBigCircle() ? holder.curCX - downX : holder.curSmallCX - downX);
                            //点击位置y坐标与圆心的y坐标的距离
                            int distanceY = (int) Math.abs(holder.isBigCircle() ? holder.curCY - downY : holder.curSmallCY - downY);
                            //点击位置与圆心的直线距离
                            int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                            //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
                            if (distanceZ <= (holder.isBigCircle() ? holder.radius : holder.radius * holder.rate)) {
                                holder.circleClick(!holder.isBigCircle());
                            } else {
                                for (ThirdCircle circle : holder.getThirdCircles()) {
                                    int distanceTX = (int) Math.abs(circle.getCurCX() - downX);
                                    int distanceTY = (int) Math.abs(circle.getCurCY() - downY);
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
            default:
                break;
        }
    }

    ArrayList<IBaseHolder> getHolders(){
        return holders;
    }

    private boolean isClick(long moveTime){
        return moveTime > ViewConfiguration.getTapTimeout() || (moveX > 20 || moveY > 20);
    }

    @Override
    public boolean setStop(boolean stop) {
        for (IBaseHolder holder : holders) {
            holder.stop(stop);
        }
        return super.setStop(stop);
    }
}
