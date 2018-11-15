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
        initFont(null);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFont(attrs);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont(attrs);
    }

    private void initFont(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RobotoRegularTextView);
            String fontName = a.getString(R.styleable.RobotoRegularTextView_RobotoRegular);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+ fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}
