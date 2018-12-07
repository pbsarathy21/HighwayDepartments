package com.spiderindia.departmentsofhighway;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcel;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.spiderindia.departmentsofhighway.Adapter.ViewPagerForHome;
import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;
import com.spiderindia.departmentsofhighway.Utils.CustomViewPager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static android.os.Parcel.obtain;

public class HomeActivity extends AppCompatActivity {

    TextView headerTxt;
    Toolbar toolbar;
    LinearLayout inventoryLayout, conditionLayout,maintainanceLayout;
    TextView inventoryTxt, conditionTxt, maintainanceTxt;
    public static CustomViewPager _mViewPager;
    ViewPagerForHome adapter_pager;
    LinearLayout nextPrevsLayout;
    MyDataBaseHandler dbase;
    String response="",userId,authenticationCode,firstTimeAfterLogin;
    SharedPreferences spf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

     /*   CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Muli-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());*/


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(HomeActivity.this.getResources().getColor(R.color.status_color));
        }

        spf = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = spf.getString("userId", "");
        authenticationCode = spf.getString("authenticationToken", "");
        firstTimeAfterLogin = spf.getString("firstTimeAfterLogin", "");


        dbase = new MyDataBaseHandler(this);

        dbase.insertAllTables();
        
        //dbase.createTable();


       /* ContentValues con=new ContentValues();
        con.put(dbase.CIRCLE,"0");
        con.put(dbase.DIVISION,"0");
        con.put(dbase.SUBDIVISION,"0");
        con.put(dbase.ROAD,"0");
        con.put(dbase.LINKID,"0");
        con.put(dbase.DESCRIPTION,"");
        con.put(dbase.LOCATION,"");
        con.put(dbase.STARTCHAINAGE,"");
        con.put(dbase.BRIDGE,"");
        con.put(dbase.BRIDGENO,"");
        con.put(dbase.YR_OF_CONSTRU,"");
        con.put(dbase.CONSTRU_COST,"");
        con.put(dbase.CONSTRU_DATE,"");
        con.put(dbase.DOCUMENT_PATH,"");
        con.put(dbase.DOCUMENT_NAME,"No File Choosen");
        con.put(dbase.IMAGE_PATH,"");
        con.put(dbase.SECOND_IMAGE_PATH,"");
        con.put(dbase.ENCODED_IMAGE_PATH,"");
        con.put(dbase.ENCODED_SECOND_IMAGE_PATH,"");

        ContentValues innentaryCon=new ContentValues();
        innentaryCon.put(dbase.BRIDGE_TYPE_I,"");
        innentaryCon.put(dbase.LENGTH_I,"");
        innentaryCon.put(dbase.WIDTH_I,"");
        innentaryCon.put(dbase.PARTIAL_WED_I,"0");
        innentaryCon.put(dbase.RIVER_NAME_I,"");
        innentaryCon.put(dbase.CONTROL_AUTHORITY_RIVER_I,"");
        innentaryCon.put(dbase.RIVER_FLOW_I,"");
        innentaryCon.put(dbase.CHECK_DAM_I,"0");
        innentaryCon.put(dbase.LINEAR_WATRE_WAY_I,"");

        //form 2
        innentaryCon.put(dbase.DESIGN_DISCHARGE_I,"");
        innentaryCon.put(dbase.LOADING_I,"");
        innentaryCon.put(dbase.SLAB_DESIGN_I,"");
        innentaryCon.put(dbase.NO_LANES_I,"");
        innentaryCon.put(dbase.SPANS_I,"");
        innentaryCon.put(dbase.MAX_SPANS_I,"");
        innentaryCon.put(dbase.VERTICAL_CLEARENCE_I,"");

        //form 3

        innentaryCon.put(dbase.LEFT_FOOT_PATH_I,"");
        innentaryCon.put(dbase.RIGHT_FOOT_PATH_I,"");
        innentaryCon.put(dbase.SUPER_STRUCTURE_TYP_I,"");
        innentaryCon.put(dbase.SLAB_THIKNS_I,"");
        innentaryCon.put(dbase.BEARING_TYPE_I,"");
        innentaryCon.put(dbase.PARAPET_I,"");
        innentaryCon.put(dbase.WEARING_COAT_TYP_I,"");
        innentaryCon.put(dbase.PIER_FOUNDATN_I,"");

        //form 4
        innentaryCon.put(dbase.ABUTMNT_FOUNDATN_I,"");
        innentaryCon.put(dbase.MLF_I,"");
        innentaryCon.put(dbase.BANK_PROTECTION_TYP_I,"");
        innentaryCon.put(dbase.APPROACH_TYP_I,"");
        innentaryCon.put(dbase.FLOOR_PROTECTION_I,"");
        innentaryCon.put(dbase.FLOOR_PROTECTION_TYP_I,"");
        innentaryCon.put(dbase.TYPE_OF_SUBSTRUCTURE_I,"");



        //CONDITION
        ContentValues conditionCon=new ContentValues();

        conditionCon.put(dbase.SHAPE_OF_PIER,"");
        conditionCon.put(dbase.BRIDGE_ANGLE,"");
        conditionCon.put(dbase.BED_LEVEL,"");
        conditionCon.put(dbase.BED_SLOPE,"");

        //form 2
        conditionCon.put(dbase.CRACKS_LEFT_UPS_F,"0");

        conditionCon.put(dbase.CRACKS_RIGHT_UPS_F,"0");
        conditionCon.put(dbase.SETTLEMENT_LEFT_UPS_F,"0");
        conditionCon.put(dbase.SETTLEMENT_RIGHT_UPS_F,"0");
        conditionCon.put(dbase.SPALIT_LEFT_UPS_F,"0");
        conditionCon.put(dbase.SPALIT_RIGHT_UPS_F,"0");

        conditionCon.put(dbase.CRACKS_LEFT_DOWNS_F,"0");
        conditionCon.put(dbase.CRACKS_RIGHT_DOWNS_F,"0");
        conditionCon.put(dbase.SETTLEMENT_LEFT_DOWNS_F,"0");
        conditionCon.put(dbase.SETTLEMENT_RIGHT_DOWNS_F,"0");
        conditionCon.put(dbase.SPALIT_LEFT_DOWNS_F,"0");
        conditionCon.put(dbase.SPALIT_RIGHT_DOWNS_F,"0");

        conditionCon.put(dbase.SCOUR_F,"0");
        conditionCon.put(dbase.SPALLED_F,"0");
        conditionCon.put(dbase.CRACKED_F,"0");

        //form 3

        conditionCon.put(dbase.CRACKS_LEFT_UPS_SUBS,"0");
        conditionCon.put(dbase.CRACKS_RIGHT_UPS_SUBS,"0");
        conditionCon.put(dbase.VEGETATION_LEFT_UPS_SUBS,"0");

        conditionCon.put(dbase.VEGETATION_RIGHT_UPS_SUBS,"0");
        conditionCon.put(dbase.TILTING_LEFT_UPS_SUBS,"0");
        conditionCon.put(dbase.TILTING_RIGHT_UPS_SUBS,"0");
        conditionCon.put(dbase.SPALIT_LEFT_UPS_SUBS,"0");
        conditionCon.put(dbase.SPALIT_RIGHT_UPS_SUBS,"0");
        conditionCon.put(dbase.CRACKS_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(dbase.CRACKS_RIGHT_DOWNS_SUBS,"0");
        conditionCon.put(dbase.VEGETATION_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(dbase.VEGETATION_RIGHT_DOWNS_SUBS,"0");

        conditionCon.put(dbase.TILTING_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(dbase.TILTING_RIGHT_DOWNS_SUBS,"0");
        conditionCon.put(dbase.SPALIT_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(dbase.SPALIT_RIGHT_DOWNS_SUBS,"0");

        conditionCon.put(dbase.CRACKS_PIERS,"0");
        conditionCon.put(dbase.TILTING_PIERS,"0");
        conditionCon.put(dbase.VEGETATION_PIERS,"0");
        conditionCon.put(dbase.SPALIT_PIERS,"0");

        //form4

        conditionCon.put(dbase.CRACKED_SUPERS,"0");
        conditionCon.put(dbase.CORROSION_SUPERS,"0");
        conditionCon.put(dbase.SPALLED_SUPERS,"0");
        conditionCon.put(dbase.LEACHED_SUPERS,"0");

        conditionCon.put(dbase.BUCKLED_SUPERS,"0");
        conditionCon.put(dbase.VEGETATION_SUPERS,"0");
        conditionCon.put(dbase.SALIYO_SUPERS,"0");
        conditionCon.put(dbase.SCALED_SUPERS,"0");
        conditionCon.put(dbase.SPALITY_SUPERS,"0");

        conditionCon.put(dbase.BROKEN_HR,"0");
        conditionCon.put(dbase.CORRODED_HR,"0");
        conditionCon.put(dbase.SPALLING_HR,"0");

        conditionCon.put(dbase.BROKEN_FP,"0");
        conditionCon.put(dbase.DISINTEGRATION_NO_Fp,"0");

        conditionCon.put(dbase.CRACKS_WC,"0");
        conditionCon.put(dbase.POTHOLES_WC,"0");
        conditionCon.put(dbase.RAVELLED_WC,"0");
        conditionCon.put(dbase.FAILED_JOINTS_WC,"0");
        conditionCon.put(dbase.POOR_DRAINAGE_WC,"0");

        conditionCon.put(dbase.RUSTED_B,"0");
        conditionCon.put(dbase.TILTED_B,"0");
        conditionCon.put(dbase.FLATTERING_B,"0");
        conditionCon.put(dbase.SPLITINAL_CRACK_B,"0");

        conditionCon.put(dbase.WORNOUT_EJ,"0");
        conditionCon.put(dbase.BLEED_EJ,"0");
        conditionCon.put(dbase.CRACKED_EJ,"0");
        conditionCon.put(dbase.SILTED_VW,"0");
        conditionCon.put(dbase.SCOUR_VW,"0");



        ContentValues maintainanceCon=new ContentValues();
        maintainanceCon.put(dbase.YEAR_MH,"");
        maintainanceCon.put(dbase.TYPE_MH,"0");
        maintainanceCon.put(dbase.COMPONENT_MH,"0");
        maintainanceCon.put(dbase.AMOUNT_MH,"");


        if(dbase.isEmpty(dbase.TABLE_NAME_BRIDGE))
        dbase.InsertDetails(con,dbase.TABLE_NAME_BRIDGE);

        if(dbase.isEmpty(dbase.TABLE_NAME_INVENTARY))
        dbase.InsertDetails(innentaryCon,dbase.TABLE_NAME_INVENTARY);

        if(dbase.isEmpty(dbase.TABLE_NAME_CONDITION))
        dbase.InsertDetails(conditionCon,dbase.TABLE_NAME_CONDITION);

        if(dbase.isEmpty(dbase.TABLE_NAME_MAINTAINANCE))
        dbase.InsertDetails(maintainanceCon,dbase.TABLE_NAME_MAINTAINANCE);*/

        new asyncToGetDetails().execute();



    /*    JSONObject student1 = new JSONObject();
        try {
            student1.put("id", "3");
            student1.put("name", "Chennai");
            student1.put("type", "division");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONObject student2 = new JSONObject();
        try {
            student2.put("id", "2");
            student2.put("name", "Same area");
            student2.put("type", "circle");


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        JSONArray jsonArray = new JSONArray();

        jsonArray.put(student1);
        jsonArray.put(student2);

        JSONObject studentsObj = new JSONObject();
        try {
            studentsObj.put("Students", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        headerTxt = (TextView) toolbar.findViewById(R.id.header_txt);

        inventoryLayout = (LinearLayout) findViewById(R.id.inventory_ll);
        conditionLayout = (LinearLayout) findViewById(R.id.condition_ll);
        maintainanceLayout = (LinearLayout) findViewById(R.id.maintnce_ll);

        inventoryTxt = (TextView) findViewById(R.id.inventory_txt);
        conditionTxt = (TextView) findViewById(R.id.condition_txt);
        maintainanceTxt = (TextView) findViewById(R.id.maintance_txt);

        _mViewPager = (CustomViewPager) findViewById(R.id.viewPager);

        setUpView();
        setTab();

        _mViewPager.disableScroll(true);
        _mViewPager.setCurrentItem(1);
        inventoryLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                _mViewPager.setCurrentItem(0);

                //YoYo.with(Techniques.SlideInUp).duration(700).playOn(mainAnimLayout);
            }
        });

        conditionLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                _mViewPager.setCurrentItem(1);


                // YoYo.with(Techniques.SlideInUp).duration(700).playOn(mainAnimLayout);
            }
        });

        maintainanceLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                _mViewPager.setCurrentItem(2);

                // YoYo.with(Techniques.SlideInUp).duration(700).playOn(mainAnimLayout);
            }
        });


    }
    private void setUpView() {

        adapter_pager = new ViewPagerForHome(HomeActivity.this, getSupportFragmentManager());
        _mViewPager.setAdapter(adapter_pager);
        _mViewPager.setCurrentItem(0);
        initButton();
    }

    void initButton() {
        inventoryLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
        inventoryTxt.setTextColor(getResources().getColor(R.color.white));

        conditionLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg_high));
        conditionTxt.setTextColor(getResources().getColor(R.color.light_white));

        maintainanceLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg_high));
        maintainanceTxt.setTextColor(getResources().getColor(R.color.light_white));
    }

    private void setTab() {
        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub

                if (position == 0)
                {
                    inventoryLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
                    conditionLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
                    maintainanceLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));

                    inventoryTxt.setTextColor(getResources().getColor(R.color.light_white));
                    conditionTxt.setTextColor(getResources().getColor(R.color.light_white));
                    maintainanceTxt.setTextColor(getResources().getColor(R.color.light_white));

                    headerTxt.setText("ADD BRIDGE");
                }
                else if ( (position == 1) || (position == 2) || (position == 3) || (position == 4) || (position==5)) {

                    inventoryLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg_high));
                    conditionLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
                    maintainanceLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));

                    inventoryTxt.setTextColor(getResources().getColor(R.color.white));
                    conditionTxt.setTextColor(getResources().getColor(R.color.light_white));
                    maintainanceTxt.setTextColor(getResources().getColor(R.color.light_white));

                    headerTxt.setText("INVENTORY");

                }
                else if( (position == 6) || (position == 7) || (position == 8) || (position == 9) || (position == 10)  ){

                    inventoryLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
                    conditionLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg_high));
                    maintainanceLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));

                    inventoryTxt.setTextColor(getResources().getColor(R.color.light_white));
                    conditionTxt.setTextColor(getResources().getColor(R.color.white));
                    maintainanceTxt.setTextColor(getResources().getColor(R.color.light_white));

                    headerTxt.setText("CONDITION");

                }
                else if( position==11) {
                    inventoryLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
                    conditionLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));
                    maintainanceLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg_high));

                    inventoryTxt.setTextColor(getResources().getColor(R.color.light_white));
                    conditionTxt.setTextColor(getResources().getColor(R.color.light_white));
                    maintainanceTxt.setTextColor(getResources().getColor(R.color.white));

                    headerTxt.setText("MAINTAINANCE HISTORY");

                }
            }

        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
       // alerBoxFunction();

    }
    public void alerBoxFunction() {
        final Dialog dialog = new Dialog(HomeActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.alert_box_common);
        dialog.getWindow().getAttributes().windowAnimations = R.style.fade_in_out_popup;
        dialog.show();

        LinearLayout yesLayout = (LinearLayout) dialog.findViewById(R.id.yes_layout);
        LinearLayout noLayout = (LinearLayout) dialog.findViewById(R.id.no_layout);
        LinearLayout mainLayout = (LinearLayout) dialog.findViewById(R.id.main_layOUT);
        LinearLayout header_Txt = (LinearLayout) dialog.findViewById(R.id.header_ll);
        LinearLayout content_Txt = (LinearLayout) dialog.findViewById(R.id.content_ll);
        LinearLayout view_ll = (LinearLayout) dialog.findViewById(R.id.view_ll);

        Animation animation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bottom_up_anim);
        animation.setDuration(900);
        mainLayout.setAnimation(animation);
        mainLayout.animate();
        animation.start();


        Animation animation1 = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bottom_up_anim);
        animation1.setDuration(1200);
        header_Txt.setAnimation(animation1);
        header_Txt.animate();
        animation1.start();

        Animation animation2 = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bottom_up_anim);
        animation2.setDuration(1200);
        content_Txt.setAnimation(animation1);
        content_Txt.animate();
        animation2.start();

        Animation animation3 = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bottom_up_anim);
        animation3.setDuration(1200);
        view_ll.setAnimation(animation3);
        view_ll.animate();
        animation3.start();

        yesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (NetUtils.isLogOut(HomeActivity.this)) {
                    SharedPreferences spref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = spref.edit();
                    editor.putString("userId", "");
                    editor.putString("authenticationToken", "");
                    editor.putString("uesrName", "");
                    editor.putString("SignOut", "1");
                    editor.commit();

                    dbase.dropTable();*/


                dbase.dropTable(dbase.TABLE_NAME_BRIDGE);
                dbase.dropTable(dbase.TABLE_NAME_INVENTARY);
                dbase.dropTable(dbase.TABLE_NAME_CONDITION);
                dbase.dropTable(dbase.TABLE_NAME_MAINTAINANCE);
                dbase.dropTable(dbase.TABLE_NAME_CIRCLE);

                dbase.deleteDataBase(getApplicationContext());

                SharedPreferences spref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                editor.putString("userId", "");
                editor.putString("authenticationToken", "");
                editor.putString("uesrName", "");

                editor.commit();

                SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putString("SignOut", "1");
                editor1.apply();


                finishAffinity();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);


               /* } else {
                    Toast.makeText(getApplicationContext(), "Failed, Try again", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
        noLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }
    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    class asyncToGetDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

          //  progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {

                getCircleDetails();

            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... unsued) {

        }

        @Override
        protected void onPostExecute(String response_str) {
            try {


               /* if (progress_layout.getVisibility() == View.VISIBLE) {
                    progress_layout.setVisibility(View.GONE);
                }

                if(!(response.equals("")) && response.equalsIgnoreCase("TRUE"))
                {

                }
                else
                {
                }*/

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
    private void getCircleDetails() {

        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getBridgeCircleDetailsFromServer(userId,authenticationCode);
        try {
            response=json.getString("success");
            if(response.equalsIgnoreCase("TRUE"))
            {
                dbase.emptyTable(dbase.TABLE_NAME_CIRCLE);

                ContentValues CircleCon=new ContentValues();
                JSONArray jsonArray = json.getJSONArray("circle");

                CircleCon.put(dbase.CIRCLE_ID,"0");
                CircleCon.put(dbase.CIRCLE_NAME,"None");
                dbase.InsertDetails(CircleCon,dbase.TABLE_NAME_CIRCLE);
                Map<String, Object> map = new HashMap<String, Object>();



                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject json_user = jsonArray.getJSONObject(i);

                    for (Iterator<String> it = json_user.keys(); it.hasNext(); ) {
                        String key = it.next();
                        map.put(key, json_user.get(key));
                    }

                    String id = json_user.getString("id");
                    String name = json_user.getString("name");

                    CircleCon.put(dbase.CIRCLE_ID,id);
                    CircleCon.put(dbase.CIRCLE_NAME,name);

                    dbase.InsertDetails(CircleCon,dbase.TABLE_NAME_CIRCLE);
                }

                Set<String> keys = map.keySet();  //get all keys
                for(String i: keys)
                {
                    System.out.println("Hash map key "+map.get(i) +" sixze "+map.size());
                }

              /*  for (String entry : map.keySet()) {
                    String value =  map.get(entry);
                    System.out.print(entry + "" + value + " ");
                    // do stuff
                }*/

                Parcel parcel = obtain();
                parcel.writeMap(map);
                parcel.setDataPosition(0);
                ContentValues values = ContentValues.CREATOR.createFromParcel(parcel);

                printContentValues(values);

                //printContentValues(CircleCon);
            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void printContentValues(ContentValues vals)
    {
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.d("value from map", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
        }
    }

}
