package com.sheng.preferencefloatingview.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by sheng
 */

class PreferenceFloatingDrawer extends BaseDrawer {
    final private ArrayList<IBaseHolder> holders = new ArrayList<>();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
    public boolean drawWeather(Canvas canvas, float alpha) {
        for (IBaseHolder holder : this.holders) {
            holder.updateAndDraw(canvas, paint);
        }
        return true;
    }

    @Override
    protected int[] getFloatingBackgroundGradient() {
        return FloatingBackground.WHITE;
    }

    ArrayList<IBaseHolder> getHolders(){
        return holders;
    }

}
