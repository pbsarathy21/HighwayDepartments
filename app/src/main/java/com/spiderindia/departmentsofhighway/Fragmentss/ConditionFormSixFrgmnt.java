package com.spiderindia.departmentsofhighway.Fragmentss;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionFormSixFrgmnt extends Fragment {

    boolean preset = false;

    DataItem dataItem;

    Boolean preloadSpinner = true;

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");
        boolean preload = sharedPreferences.getBoolean("preload", false);

        if (preload)
        {
            preloadSpinner = true;
            preloadValues();
        }

        if (data.equalsIgnoreCase("true")) {

            preset = true;

            dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

            if (!TextUtils.isEmpty(dataItem.getEXPANSIONJOINTSWORNOUT()) && dataItem.getEXPANSIONJOINTSWORNOUT().equalsIgnoreCase("1"))
            {
                worntoutChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getEXPANSIONJOINTSBLEED()) && dataItem.getEXPANSIONJOINTSBLEED().equalsIgnoreCase("1"))
            {
                bleedChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getEXPANSIONJOINTSCRACKED()) && dataItem.getEXPANSIONJOINTSCRACKED().equalsIgnoreCase("1"))
            {
                crackedChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getVENTWATERWAYSILTED()) && dataItem.getVENTWATERWAYSILTED().equalsIgnoreCase("1"))
            {
                siltedChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getVENTWATERWAYSCOURED()) && dataItem.getVENTWATERWAYSCOURED().equalsIgnoreCase("1"))
            {
                scourChkBx.setChecked(true);
            }



        }
    }

    private void preloadValues() {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String worntoutId = preferences.getString("worntoutId", null);
        String bleedId = preferences.getString("bleedId", null);
        String crackedId = preferences.getString("crackedId", null);
        String siltedId = preferences.getString("siltedId", null);
        String scourId = preferences.getString("scourId", null);


        if (!TextUtils.isEmpty(worntoutId) && worntoutId.equalsIgnoreCase("1"))
        {
            worntoutChkBx.setChecked(true);
        }


        if (!TextUtils.isEmpty(bleedId) && bleedId.equalsIgnoreCase("1"))
        {
            bleedChkBx.setChecked(true);
        }


        if (!TextUtils.isEmpty(crackedId) && crackedId.equalsIgnoreCase("1"))
        {
            crackedChkBx.setChecked(true);
        }


        if (!TextUtils.isEmpty(siltedId) && siltedId.equalsIgnoreCase("1"))
        {
            siltedChkBx.setChecked(true);
        }


        if (!TextUtils.isEmpty(scourId) && scourId.equalsIgnoreCase("1"))
        {
            scourChkBx.setChecked(true);
        }
    }


    public ConditionFormSixFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    Context mContext;
    CheckBox worntoutChkBx,bleedChkBx,crackedChkBx,siltedChkBx,scourChkBx;
    String worntoutId,bleedId,crackedId,siltedId,scourId;
    MyDataBaseHandler dbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_condition_form_six_frgmnt, container, false);

        mContext = container.getContext();

        dbase=new MyDataBaseHandler(getActivity());

        loading_process= (ProgressBar)root.findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout= (LinearLayout)root.findViewById(R.id.progress_loading_layout);

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        nextBttn=(Button)root.findViewById(R.id.nxt_btn);
        previousBttn=(Button)root.findViewById(R.id.previous_bttn);
        worntoutChkBx=(CheckBox)root.findViewById(R.id.wornout_chkBx_ventway);
        bleedChkBx=(CheckBox)root.findViewById(R.id.bleed_chkBx_ventway);
        crackedChkBx=(CheckBox)root.findViewById(R.id.cracked_chkBx_ventway);
        siltedChkBx=(CheckBox)root.findViewById(R.id.silted_chkBx_ventway);
        scourChkBx=(CheckBox)root.findViewById(R.id.scour_chkBx_ventway);


        new asyncToGetDetails().execute();

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormFiveFrgmnt conditionFormFiveFrgmnt = new ConditionFormFiveFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormFiveFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MaintenanceFormOneFrgmnt maintenanceFormOneFrgmnt = new MaintenanceFormOneFrgmnt();

                fragmentTransaction.replace(R.id.container, maintenanceFormOneFrgmnt);

                fragmentTransaction.commit();
            }
        });


        return root;
    }

    private void saveDetails() {

        if (worntoutChkBx.isChecked())
            worntoutId = "1";

        if (bleedChkBx.isChecked())
            bleedId = "1";

        if (crackedChkBx.isChecked())
            crackedId = "1";

        if (siltedChkBx.isChecked())
            siltedId = "1";

        if (scourChkBx.isChecked())
            scourId = "1";


        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("worntoutId",worntoutId );
        editor.putString("bleedId",bleedId );
        editor.putString("crackedId",crackedId );
        editor.putString("siltedId",siltedId );
        editor.putString("scourId",scourId );

        editor.apply();
    }

    class asyncToSendDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {

                //id from 63
                ContentValues conditionCon=new ContentValues();

                conditionCon.put(dbase.WORNOUT_EJ,worntoutId);
                conditionCon.put(dbase.BLEED_EJ,bleedId);
                conditionCon.put(dbase.CRACKED_EJ,crackedId);
                conditionCon.put(dbase.SILTED_VW,siltedId);
                conditionCon.put(dbase.SCOUR_VW,scourId);

                //67

                boolean updateStatus=dbase.UpdateDetails(conditionCon,dbase.TABLE_NAME_CONDITION,dbase.CONDITION_ID);

                System.out.println("updateStatus inventaryCon "+updateStatus);

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


                if (progress_layout.getVisibility() == View.VISIBLE) {
                    progress_layout.setVisibility(View.GONE);
                }


            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    class asyncToGetDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {
                             Cursor cur=dbase.getAllDetails(dbase.TABLE_NAME_CONDITION,dbase.CONDITION_ID);
                Log.v("Cursor Object form two", DatabaseUtils.dumpCursorToString(cur));

                if (cur.moveToFirst())
                {
                    do {
                        //id from 63
                        worntoutId=cur.getString(63).toString();
                        bleedId=cur.getString(64).toString();
                        crackedId=cur.getString(65).toString();
                        siltedId=cur.getString(66).toString();
                        scourId=cur.getString(67).toString();
                        //67

                    }while (cur.moveToNext());
                }


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


                if (progress_layout.getVisibility() == View.VISIBLE) {
                    progress_layout.setVisibility(View.GONE);
                }

                if(worntoutId.equalsIgnoreCase("1"))
                    worntoutChkBx.setChecked(true);

                if(bleedId.equalsIgnoreCase("1"))
                    bleedChkBx.setChecked(true);

                if(siltedId.equalsIgnoreCase("1"))
                    siltedChkBx.setChecked(true);

                if(crackedId.equalsIgnoreCase("1"))
                    crackedChkBx.setChecked(true);

                if(scourId.equalsIgnoreCase("1"))
                    scourChkBx.setChecked(true);


                if(!(response.equals("")) && response.equalsIgnoreCase("TRUE"))
                {

                }
                else
                {
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

}
