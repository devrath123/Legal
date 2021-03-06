package com.example.devrathrathee.legal.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class RobotoRegularTextView extends AppCompatTextView {

    public RobotoRegularTextView(Context context) {
        super(context);
        initFont(context, null);
    }

    public RobotoRegularTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFont(context, attrs);
    }

    public RobotoRegularTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont(context, attrs);
    }

    private void initFont(Context context, AttributeSet attrs) {

        if (attrs != null) {
            Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
            this.setTypeface(face);
        }
    }
}
