package com.sheng.preferencefloatingview;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.sheng.preferencefloatingview.floating.ContainerView;
import com.sheng.preferencefloatingview.floating.circle.floatingdrawer.PreferenceFloatingDrawer;
import com.sheng.preferencefloatingview.floating.circle.splashdrawer.IOnAnimEndListener;
import com.sheng.preferencefloatingview.floating.circle.splashdrawer.RotationSweepView;

/**
 * @author sheng
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private ContainerView containerView;
    private RotationSweepView rotationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);

        containerView = (ContainerView) findViewById(R.id.floatingView);
        ViewGroup.LayoutParams params = containerView.getLayoutParams();
        int width = params.width=getMetricsWidth(this)*7/5;
        containerView.setLayoutParams(params);
        containerView.setDrawer(new PreferenceFloatingDrawer(this));

        rotationView = (RotationSweepView) findViewById(R.id.rotationView);
        rotationView.setIOnAnimEndListener(new IOnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                animStart();
            }
        });
        rotationView.setDistanceXYR(0,0.35f*width,0.3f*width,0.11f*width);
        rotationView.setDistanceXYR(1,0.75f*width,0.32f*width,0.105f*width);
        rotationView.setDistanceXYR(2,0.25f*width,0.57f*width,0.14f*width);
        rotationView.setDistanceXYR(3,0.68f*width,0.75f*width,0.12f*width);
        rotationView.setDistanceXYR(4,0.42f*width,0.8f*width,0.1f*width);
        rotationView.setDistanceXYR(5,0.57f*width,0.5f*width,0.13f*width);
        rotationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = rotationView.getWidth();
                int height = rotationView.getHeight();
                rotationView.setCenterXY(width/2,height/2);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rotationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                containerView.start();
            }
        });
    }

    private void animStart(){
        ValueAnimator animator = ValueAnimator.ofFloat(255,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rotationView.setAlpha((Float) animation.getAnimatedValue());
                if(((Float) animation.getAnimatedValue())==0){
                    rotationView.setVisibility(View.GONE);
                    containerView.setDrawerStop(false);
                }
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        containerView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        containerView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        containerView.onDestroy();
    }

    public static int getMetricsWidth(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                finish();
                break;
            case R.id.save:
                finish();
                break;
            default:
                break;
        }
    }
}
