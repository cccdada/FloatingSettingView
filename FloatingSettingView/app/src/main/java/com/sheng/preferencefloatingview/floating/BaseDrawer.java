package com.sheng.preferencefloatingview.floating;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * 抽象的基础绘制类 需要定制浮动的图像请继承之
 * @author sheng
 */
public abstract class BaseDrawer {

	protected ArrayList<IBaseHolder> holders;
	protected Paint paint;
	/**
	 * 暂停
	 */
	private boolean isStop;

	public static final class FloatingBackground {
		public static final int[] WHITE = new int[] { 0xFFFFFFFF, 0xFFFFFFFF };
		public static final int[] TRANS = new int[] { 0x00000000, 0x00000000 };

		public static final int[] CLEAR_D = new int[] { 0xff3d99c2, 0xff4f9ec5 };

	}

	protected Context context;
	protected int width, height;
	private GradientDrawable gradientDrawable;

	public BaseDrawer(Context context) {
		super();
		this.context = context;
		this.holders = new ArrayList<>();
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public GradientDrawable makeFloatingBackground(){
		return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getFloatingBackgroundGradient());
	}

	protected void drawFloatingBackground(Canvas canvas, float alpha) {
		if (gradientDrawable == null) {
			gradientDrawable = makeFloatingBackground();
			gradientDrawable.setBounds(0, 0, width, height);
		}
		gradientDrawable.setAlpha(Math.round(alpha * 255f));
		gradientDrawable.draw(canvas);
	}

	/**
	 * @param canvas
	 * @return needDrawNextFrame
	 */
	public boolean draw(Canvas canvas, float alpha) {
		drawFloatingBackground(canvas, alpha);
		boolean needDrawNextFrame = drawGraphics(canvas, alpha);
		return needDrawNextFrame;
	}

	/**
	 * 绘制
	 * @param canvas 画布
	 * @param alpha 透明度
	 * @return 是否需要绘制下一帧
	 */
	public boolean drawGraphics(Canvas canvas, float alpha){
		for (IBaseHolder holder : this.holders) {
			holder.updateAndDraw(canvas, paint);
		}
		return true;
	}

	public void addHolder(IBaseHolder holder){
		if (holders!=null) {
			holders.add(holder);
		}
	}

	public ArrayList<IBaseHolder> getIHolders(){
		return holders;
	}

	protected int[] getFloatingBackgroundGradient() {
		return FloatingBackground.TRANS;
	}

	protected void setSize(int width, int height) {
		if(this.width != width && this.height != height){
			this.width = width;
			this.height = height;
			if (this.gradientDrawable != null) {
				gradientDrawable.setBounds(0, 0, width, height);
			}
		}
		
	}

	/**
	 * 处理触摸事件 默认为空 子类覆写
	 *
	 * @param event 由view传递过来的事件
	 */
	protected void onTouch(MotionEvent event) {

	}

	/**
	 * 获得0--n之内的不等概率随机整数，0概率最大，1次之，以此递减，n最小
 	 */
	public static int getAnyRandInt(int n) {
		int max = n + 1;
		int bigend = ((1 + max) * max) / 2;
		Random rd = new Random();
		int x = Math.abs(rd.nextInt() % bigend);
		int sum = 0;
		for (int i = 0; i < max; i++) {
			sum += (max - i);
			if (sum > x) {
				return i;
			}
		}
		return 0; 
	}

	/**
	 * 获取[min, max)内的随机数，越大的数概率越小
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static float getDownRandFloat(float min, float max) {
		float bigend = ((min + max) * max) / 2f;
		// Random rd = new Random();
		// Math.abs(rd.nextInt() % bigend)
		float x = getRandom(min, bigend);
		int sum = 0;
		for (int i = 0; i < max; i++) {
			sum += (max - i);
			if (sum > x) {
				return i;
			}
		}
		return min;
	}

	/**
	 * 获取在最大最小区间中的随机数
	 * [min, max)
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static float getRandom(float min, float max) {
		if (max < min) {
			throw new IllegalArgumentException("max should bigger than min!!!!");
		}
		return (float) (min + Math.random() * (max - min));
	}

	public boolean setStop(boolean stop) {
		isStop = stop;
		for (IBaseHolder holder : holders) {
			holder.stop(stop);
		}
		return isStop;
	}
}
