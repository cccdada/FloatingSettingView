package com.sheng.preferencefloatingview.dynamicweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;

import com.sheng.preferencefloatingview.UiUtil;

import java.util.Random;


public abstract class BaseDrawer {

	public enum Type {
		DEFAULT, BACKGROUND_GREY
	}

	public static final class FloatingBackground {
		public static final int[] WHITE = new int[] { 0xFFDDEDEC, 0xFFDDEDEC };

		public static final int[] CLEAR_D = new int[] { 0xff3d99c2, 0xff4f9ec5 };

	}

	public static BaseDrawer makeDrawerByType(Context context, Type type) {
		switch (type) {

		case BACKGROUND_GREY:
			return new PreferenceFloatingDrawer(context);
		case DEFAULT:
		default:
			return new PreferenceFloatingDrawer(context);
		}
	}

	static final String TAG = BaseDrawer.class.getSimpleName();
	protected Context context;
	protected int width, height;
	private GradientDrawable floatingDrawable;

	public BaseDrawer(Context context) {
		super();
		this.context = context;
	}

	public GradientDrawable makeFloatingBackground(){
		return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getFloatingBackgroundGradient());
	}

	protected void drawFloatingBackground(Canvas canvas, float alpha) {
		if (floatingDrawable == null) {
			floatingDrawable = makeFloatingBackground();
			floatingDrawable.setBounds(0, 0, width, height);
		}
		floatingDrawable.setAlpha(Math.round(alpha * 255f));
		floatingDrawable.draw(canvas);
	}

	/**
	 * @param canvas
	 * @return needDrawNextFrame
	 */
	public boolean draw(Canvas canvas, float alpha) {
		drawFloatingBackground(canvas, alpha);
		boolean needDrawNextFrame = drawWeather(canvas, alpha);
		return needDrawNextFrame;
	}

	public abstract boolean drawWeather(Canvas canvas, float alpha);

	protected int[] getFloatingBackgroundGradient() {
		return FloatingBackground.CLEAR_D;
	}

	protected void setSize(int width, int height) {
		if(this.width != width && this.height != height){
			this.width = width;
			this.height = height;
			UiUtil.logDebug(TAG, "setSize");
			if (this.floatingDrawable != null) {
				floatingDrawable.setBounds(0, 0, width, height);
			}
		}
		
	}

	public static int convertAlphaColor(float percent,final int originalColor) {
		int newAlpha = (int) (percent * 255) & 0xFF;
		return (newAlpha << 24) | (originalColor & 0xFFFFFF);
	}

	// 获得0--n之内的不等概率随机整数，0概率最大，1次之，以此递减，n最小
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
		float x = getRandom(min, bigend);// Math.abs(rd.nextInt() % bigend);
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

}
