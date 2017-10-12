package com.sheng.preferencefloatingview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.sheng.preferencefloatingview.floating.BaseDrawer;
import com.sheng.preferencefloatingview.floating.FloatingPreferenceView;

public class MainActivity extends AppCompatActivity {

    private FloatingPreferenceView floatingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingView = (FloatingPreferenceView) findViewById(R.id.floatingView);
        ViewGroup.LayoutParams params = floatingView.getLayoutParams();
        params.width=getMetricsWidth(this)*7/5;
        floatingView.setLayoutParams(params);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                floatingView.setDrawerType(BaseDrawer.Type.BACKGROUND_GREY);
            }
        },100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        floatingView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        floatingView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        floatingView.onDestroy();
    }

    public static int getMetricsWidth(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;// 屏幕高（像素，如：800px）
    }

}
