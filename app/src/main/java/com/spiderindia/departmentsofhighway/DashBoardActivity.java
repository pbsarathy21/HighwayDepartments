package com.spiderindia.departmentsofhighway;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.NewActivities.BRIDGES;
import com.spiderindia.departmentsofhighway.NewActivities.CULVETS;

public class DashBoardActivity extends AppCompatActivity {

    TextView dashBoardTxtf;
    LinearLayout addBridgeLL,eventsLL,graphLL,kalvetsLL;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        setsharedpreference();


        addBridgeLL=(LinearLayout)findViewById(R.id.AddBridge_ll);
        eventsLL=(LinearLayout)findViewById(R.id.events_ll);
        graphLL=(LinearLayout)findViewById(R.id.graph_ll);
        kalvetsLL=(LinearLayout)findViewById(R.id.kalvets_ll);

        dashBoardTxtf=(TextView) findViewById(R.id.dashBoard_txtf);
        dashBoardTxtf.setTypeface(Config.fontBold);
        addBridgeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),BRIDGES.class);
                startActivity(i);
            }
        });
        eventsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),EventsActivity.class);
                startActivity(i);
            }
        });
        graphLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),GraphActivity.class);
                startActivity(i);
            }
        });
        kalvetsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),CULVETS.class);
                startActivity(i);
            }
        });


    }

    private void setsharedpreference() {

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs" ,Context.MODE_PRIVATE);

        String authority = sharedPref.getString("authority", "");
       // String userid = preferences.getString("userId", "");

        Toast.makeText(this,"level:"+authority,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        alerBoxFunction();
    }

    public void alerBoxFunction() {

        final Dialog dialog = new Dialog(DashBoardActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.alert_box_common);
        dialog.getWindow().getAttributes().windowAnimations = R.style.fade_in_out_popup;
        dialog.show();

        LinearLayout yesLayout = (LinearLayout) dialog.findViewById(R.id.yes_layout);
        LinearLayout noLayout = (LinearLayout) dialog.findViewById(R.id.no_layout);
        LinearLayout mainLayout = (LinearLayout) dialog.findViewById(R.id.main_layOUT);
        LinearLayout header_Txt = (LinearLayout) dialog.findViewById(R.id.header_ll);
        LinearLayout content_Txt = (LinearLayout) dialog.findViewById(R.id.content_ll);
        LinearLayout view_ll = (LinearLayout) dialog.findViewById(R.id.view_ll);

        Animation animation = AnimationUtils.loadAnimation(DashBoardActivity.this, R.anim.bottom_up_anim);
        animation.setDuration(900);
        mainLayout.setAnimation(animation);
        mainLayout.animate();
        animation.start();


        Animation animation1 = AnimationUtils.loadAnimation(DashBoardActivity.this, R.anim.bottom_up_anim);
        animation1.setDuration(1200);
        header_Txt.setAnimation(animation1);
        header_Txt.animate();
        animation1.start();

        Animation animation2 = AnimationUtils.loadAnimation(DashBoardActivity.this, R.anim.bottom_up_anim);
        animation2.setDuration(1200);
        content_Txt.setAnimation(animation1);
        content_Txt.animate();
        animation2.start();

        Animation animation3 = AnimationUtils.loadAnimation(DashBoardActivity.this, R.anim.bottom_up_anim);
        animation3.setDuration(1200);
        view_ll.setAnimation(animation3);
        view_ll.animate();
        animation3.start();

        yesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences spref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                editor.putString("userId", "");
                editor.putString("authenticationToken", "");
                editor.putString("uesrName", "");
                editor.putString("SignOut", "1");
                editor.commit();
                
                finishAffinity();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);


            }
        });
        noLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

}
