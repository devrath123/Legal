package com.example.devrathrathee.legal.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

import com.example.devrathrathee.legal.R;


public class RobotoBoldTextView extends AppCompatTextView {


    public RobotoBoldTextView(Context context) {
        super(context);
        initFont(context,null);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFont(context,attrs);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont(context,attrs);
    }

    private void initFont(Context context, AttributeSet attrs) {
        if (attrs != null) {
            Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
            this.setTypeface(face);
        }
    }
}
