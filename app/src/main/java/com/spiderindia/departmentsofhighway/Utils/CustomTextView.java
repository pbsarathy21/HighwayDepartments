package com.spiderindia.departmentsofhighway.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by pyr on 12-Jun-18.
 */

@SuppressLint("AppCompatCustomView")
public class CustomTextView  extends TextView {

        Context context;

    public CustomTextView(Context context)
        {
            super(context);
            this.context=context;
            init();

        }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            this.context=context;
            init();
        }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            this.context=context;
            init();
        }

    public void init()
    {
        //setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/MyriadPro-Regular.ttf"));
        setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Muli-Bold.ttf"));

    }
}
