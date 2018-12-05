package com.spiderindia.departmentsofhighway.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by pyr on 14-Jun-18.
 */

public class CustomFontCheckBox  extends CheckBox {

    // Constructors
    public CustomFontCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomFontCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFontCheckBox(Context context) {
        super(context);
        init();
    }

    // This class requires myfont.ttf to be in the assets/fonts folder
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Muli-Bold.ttf");
        setTypeface(tf);
    }
}