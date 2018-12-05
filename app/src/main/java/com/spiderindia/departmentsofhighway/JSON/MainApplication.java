package com.spiderindia.departmentsofhighway.JSON;

import android.app.Application;

import com.spiderindia.departmentsofhighway.Utils.TypefaceUtil;


/**
 * Created by pyr on 19-May-18.
 */

public class MainApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
       // TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Muli-Bold.ttf");
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Muli-Regular.ttf");
        TypefaceUtil.overrideFont(getApplicationContext(), "sans-serif-condensed", "fonts/Muli-Bold.ttf");
    }
}
