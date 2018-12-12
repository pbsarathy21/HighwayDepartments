package com.spiderindia.departmentsofhighway.NewActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.spiderindia.departmentsofhighway.Fragmentss.AddBridgeFragment;
import com.spiderindia.departmentsofhighway.R;

public class NewBridgeForm extends FragmentActivity {

    @Override
    public void onBackPressed() {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().apply();

        SharedPreferences sharedPreferences = getSharedPreferences("Modify", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putBoolean("preload", false);
        editor1.apply();

        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bridge_form2);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddBridgeFragment addBridgeFragment = new AddBridgeFragment();
        fragmentTransaction.add(R.id.container, addBridgeFragment);
        fragmentTransaction.commit();


    }
}
