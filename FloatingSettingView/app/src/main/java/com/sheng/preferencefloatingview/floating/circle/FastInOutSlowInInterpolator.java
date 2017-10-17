/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sheng.preferencefloatingview.floating.circle;


import android.view.animation.Interpolator;

/**
 *  @author sheng
 */
public class FastInOutSlowInInterpolator implements Interpolator {

    /**
     * Lookup table values sampled with x at regular intervals between 0 and 1 for a total of
     * 201 points.
     */
    private static final float[] VALUES = new float[] {
            0.0000f, 0.0001f, 0.0002f, 0.0005f, 0.0008f, 0.0013f, 0.0018f,
            0.0024f, 0.0032f, 0.0040f, 0.0049f, 0.0059f, 0.0069f, 0.0081f,
            0.0093f, 0.0106f, 0.0120f, 0.0135f, 0.0151f, 0.0167f, 0.0184f,
            0.0201f, 0.0220f, 0.0239f, 0.0259f, 0.0279f, 0.0300f, 0.0322f,
            0.0345f, 0.0368f, 0.0391f, 0.0416f, 0.0441f, 0.0466f, 0.0492f,
            0.0519f, 0.0547f, 0.0574f, 0.0603f, 0.0632f, 0.0662f, 0.0692f,
            0.0722f, 0.0754f, 0.0785f, 0.0817f, 0.0850f, 0.0884f, 0.0917f,
            0.0952f, 0.0986f, 0.1021f, 0.1057f, 0.1093f, 0.1130f, 0.1167f,
            0.1205f, 0.1243f, 0.1281f, 0.1320f, 0.1359f, 0.1399f, 0.1439f,
            0.1480f, 0.1521f, 0.1562f, 0.1604f, 0.1647f, 0.1689f, 0.1732f,
            0.1776f, 0.1820f, 0.1864f, 0.1909f, 0.1954f, 0.1999f, 0.2045f,
            0.2091f, 0.2138f, 0.2184f, 0.2232f, 0.2279f, 0.2327f, 0.2376f,
            0.2424f, 0.2473f, 0.2523f, 0.2572f, 0.2622f, 0.2673f, 0.2723f,
            0.2774f, 0.2826f, 0.2877f, 0.2929f, 0.2982f, 0.3034f, 0.3087f,
            0.3141f, 0.3194f, 0.3248f, 0.3302f, 0.3357f, 0.3412f, 0.3467f,
            0.3522f, 0.3578f, 0.3634f, 0.3690f, 0.3747f, 0.3804f, 0.3861f,
            0.3918f, 0.3976f, 0.4034f, 0.4092f, 0.4151f, 0.4210f, 0.4269f,
            0.4329f, 0.4388f, 0.4448f, 0.4508f, 0.4569f, 0.4630f, 0.4691f,
            0.4752f, 0.4814f, 0.4876f, 0.4938f, 0.5000f, 0.5063f, 0.5126f,
            0.5189f, 0.5252f, 0.5316f, 0.5380f, 0.5444f, 0.5508f, 0.5573f,
            0.5638f, 0.5703f, 0.5768f, 0.5834f, 0.5900f, 0.5966f, 0.6033f,
            0.6099f, 0.6166f, 0.6233f, 0.6257f, 0.6322f, 0.6387f, 0.6450f, 0.6512f, 0.6574f,
            0.6635f, 0.6695f, 0.6754f, 0.6812f, 0.6870f, 0.6927f, 0.6983f,
            0.7038f, 0.7093f, 0.7147f, 0.7200f, 0.7252f, 0.7304f, 0.7355f,
            0.7406f, 0.7455f, 0.7504f, 0.7553f, 0.7600f, 0.7647f, 0.7694f,
            0.7740f, 0.7785f, 0.7829f, 0.7873f, 0.7917f, 0.7959f, 0.8002f,
            0.8043f, 0.8084f, 0.8125f, 0.8165f, 0.8204f, 0.8243f, 0.8281f,
            0.8319f, 0.8356f, 0.8392f, 0.8429f, 0.8464f, 0.8499f, 0.8534f,
            0.8568f, 0.8601f, 0.8634f, 0.8667f, 0.8699f, 0.8731f, 0.8762f,
            0.8792f, 0.8823f, 0.8852f, 0.8882f, 0.8910f, 0.8939f, 0.8967f,
            0.8994f, 0.9021f, 0.9048f, 0.9074f, 0.9100f, 0.9125f, 0.9150f,
            0.9174f, 0.9198f, 0.9222f, 0.9245f, 0.9268f, 0.9290f, 0.9312f,
            0.9334f, 0.9355f, 0.9376f, 0.9396f, 0.9416f, 0.9436f, 0.9455f,
            0.9474f, 0.9492f, 0.9510f, 0.9528f, 0.9545f, 0.9562f, 0.9579f,
            0.9595f, 0.9611f, 0.9627f, 0.9642f, 0.9657f, 0.9672f, 0.9686f,
            0.9700f, 0.9713f, 0.9726f, 0.9739f, 0.9752f, 0.9764f, 0.9776f,
            0.9787f, 0.9798f, 0.9809f, 0.9820f, 0.9830f, 0.9840f, 0.9849f,
            0.9859f, 0.9868f, 0.9876f, 0.9885f, 0.9893f, 0.9900f, 0.9908f,
            0.9915f, 0.9922f, 0.9928f, 0.9934f, 0.9940f, 0.9946f, 0.9951f,
            0.9956f, 0.9961f, 0.9966f, 0.9970f, 0.9974f, 0.9977f, 0.9981f,
            0.9984f, 0.9987f, 0.9989f, 0.9992f, 0.9994f, 0.9995f, 0.9997f,
            0.9998f, 0.9999f, 0.9999f, 1.0000f, 1.0000f
    };

    private final float[] mValues;
    private final float mStepSize;

    public FastInOutSlowInInterpolator() {
        mValues = VALUES;
        mStepSize = 1f / (mValues.length - 1);
    }

    @Override
    public float getInterpolation(float input) {
        if (input >= 1.0f) {
            return 1.0f;
        }
        if (input <= 0f) {
            return 0f;
        }

        // Calculate index - We use min with length - 2 to avoid IndexOutOfBoundsException when
        // we lerp (linearly interpolate) in the return statement
        int position = Math.min((int) (input * (mValues.length - 1)), mValues.length - 2);

        // Calculate values to account for small offsets as the lookup table has discrete values
        float quantized = position * mStepSize;
        float diff = input - quantized;
        float weight = diff / mStepSize;

        // Linearly interpolate between the table values
        return mValues[position] + weight * (mValues[position + 1] - mValues[position]);
    }

}
