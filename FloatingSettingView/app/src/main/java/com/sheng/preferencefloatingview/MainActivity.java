package com.sheng.preferencefloatingview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.sheng.preferencefloatingview.floating.BaseDrawer;
import com.sheng.preferencefloatingview.floating.FloatingPreferenceView;
import com.sheng.preferencefloatingview.floating.circle.OnAnimEndListener;
import com.sheng.preferencefloatingview.floating.circle.RotationSweepView;

/**
 * @author sheng
 */
public class MainActivity extends AppCompatActivity {

    private FloatingPreferenceView floatingView;
    private RotationSweepView rotationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingView = (FloatingPreferenceView) findViewById(R.id.floatingView);
        ViewGroup.LayoutParams params = floatingView.getLayoutParams();
        int width = params.width=getMetricsWidth(this)*7/5;
        floatingView.setLayoutParams(params);
        floatingView.setDrawerType(BaseDrawer.Type.CIRCLE_FLOATING);
        floatingView.startFloating();

        rotationView = (RotationSweepView) findViewById(R.id.rotationView);
        rotationView.setOnAnimEndListener(new OnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                rotationView.setVisibility(View.GONE);
                floatingView.getDrawer().setStop(false);
            }
        });
        rotationView.setDistanceXYR(0,0.35f*width,0.3f*width,0.11f*width);
        rotationView.setDistanceXYR(1,0.75f*width,0.32f*width,0.105f*width);
        rotationView.setDistanceXYR(2,0.25f*width,0.57f*width,0.14f*width);
        rotationView.setDistanceXYR(3,0.68f*width,0.75f*width,0.12f*width);
        rotationView.setDistanceXYR(4,0.42f*width,0.8f*width,0.1f*width);
        rotationView.setDistanceXYR(5,0.57f*width,0.5f*width,0.1f*width);
        rotationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = rotationView.getWidth();
                int height = rotationView.getHeight();
                rotationView.setCenterXY(width/2,height/2);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rotationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
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
        return dm.widthPixels;
    }

}
