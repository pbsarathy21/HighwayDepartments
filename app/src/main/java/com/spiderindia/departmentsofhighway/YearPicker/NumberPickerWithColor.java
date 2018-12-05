package com.spiderindia.departmentsofhighway.YearPicker;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import com.spiderindia.departmentsofhighway.R;

import java.lang.reflect.Field;

/**
 * Created by pyr on 22-Mar-18.
 */

public class NumberPickerWithColor extends NumberPicker {

    public NumberPickerWithColor(Context context, AttributeSet attrs) {
        super(context, attrs);

        Class<?> numberPickerClass = null;
        try {
            numberPickerClass = Class.forName("android.widget.NumberPicker");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Field selectionDivider = null;
        try {
            selectionDivider = numberPickerClass.getDeclaredField("mSelectionDivider");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            selectionDivider.setAccessible(true);
            selectionDivider.set(this, AppCompatResources.getDrawable(context, R.drawable.picker_divider_color));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
