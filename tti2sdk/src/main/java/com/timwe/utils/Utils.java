package com.timwe.utils;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;


/**
 * Created by Roberto Fernandes on 3/16/2021.
 */
public class Utils {
    private static final String TAG = "Utils";

    public static Drawable getDrawableFromId(Context context, int id) {
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(context, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

}
