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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionFormFourFrgmnt extends Fragment {

    DataItem dataItem;

    boolean preset = false;

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            preset = true;

            dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

            if (!TextUtils.isEmpty(dataItem.getSPSCONCREATECRACKED()) && dataItem.getSPSCONCREATECRACKED().equalsIgnoreCase("1"))
            {
                crackedChkBx.setChecked(true);
            }
            if (!TextUtils.isEmpty(dataItem.getSPSCONCREATELEACHED()) && dataItem.getSPSCONCREATELEACHED().equalsIgnoreCase("1"))
            {
                leachedChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSCONCREATESALLIYO()) && dataItem.getSPSCONCREATESALLIYO().equalsIgnoreCase("1"))
            {
                saliyoChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSCONCREATESPALITY()) && dataItem.getSPSCONCREATESPALITY().equalsIgnoreCase("1"))
            {
                spalityChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSSTEELCORROSION()) && dataItem.getSPSSTEELCORROSION().equalsIgnoreCase("1"))
            {
                corrosionChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSSTEELBUCKLED()) && dataItem.getSPSSTEELBUCKLED().equalsIgnoreCase("1"))
            {
                buckledChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSARCHSPALLED()) && dataItem.getSPSARCHSPALLED().equalsIgnoreCase("1"))
            {
                spalledChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSARCHSPALLED()) && dataItem.getSPSARCHVEGETATION().equalsIgnoreCase("1"))
            {
                vegetationChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getSPSARCHSCALED()) && dataItem.getSPSARCHSCALED().equalsIgnoreCase("1"))
            {
                scaledChkBx.setChecked(true);
            }


            if (!TextUtils.isEmpty(dataItem.getHANDRAILSBROKEN()) && dataItem.getHANDRAILSBROKEN().equalsIgnoreCase("1"))
            {
                brokenChkBxHandRails.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getHANDRAILSCORRODED()) && dataItem.getHANDRAILSCORRODED().equalsIgnoreCase("1"))
            {
                corrodedChkBxHandRail.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOOTPATHBROKEN()) && dataItem.getFOOTPATHBROKEN().equalsIgnoreCase("1"))
            {
                brokenChkBxFootPath.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOOTPATHDISINTEGRATION()) && dataItem.getFOOTPATHDISINTEGRATION().equalsIgnoreCase("1"))
            {
                disintegratnNoChkBxFoot.setChecked(true);
            }



        }
    }

    public ConditionFormFourFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;

    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    Context mContext;

    CheckBox crackedChkBx,corrosionChkBx,spalledChkBx,leachedChkBx,buckledChkBx,vegetationChkBx,saliyoChkBx,
            scaledChkBx,spalityChkBx,brokenChkBxHandRails,corrodedChkBxHandRail,brokenChkBxFootPath,disintegratnNoChkBxFoot;

    String crackedId,corrosionId,spalledId,leachedId,buckledId,vegetationId,saliyoId,
            scaledId,spalityId,brokenHandRailsId,corrodedHandRailId,brokenFootPathId,disintegratnNoFootId,spallingSpnrSuperSId;

    Spinner spallingSpnrSuperS;

    String[] spallingSpnrArr;
    HashMap<Integer,String> spallingSpnrMap;

    MyDataBaseHandler dbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_condition_form_four_frgmnt, container, false);
        View root= inflater.inflate(R.layout.fragment_condition_form_four_frgmnt, container, false);
        mContext = container.getContext();

        loading_process= (ProgressBar)root.findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout= (LinearLayout)root.findViewById(R.id.progress_loading_layout);

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dbase=new MyDataBaseHandler(getActivity());

        nextBttn=(Button)root.findViewById(R.id.nxt_btn);
        previousBttn=(Button)root.findViewById(R.id.previous_bttn);

        crackedChkBx=(CheckBox)root.findViewById(R.id.cracked_chkBx_superS);
        corrosionChkBx=(CheckBox)root.findViewById(R.id.corrosion_chkBx_superS);
        spalledChkBx=(CheckBox)root.findViewById(R.id.spalled_chkBx_superS);
        leachedChkBx=(CheckBox)root.findViewById(R.id.leached_chkBx_superS);
        buckledChkBx=(CheckBox)root.findViewById(R.id.buckled_chkBx_superS);
        vegetationChkBx=(CheckBox)root.findViewById(R.id.vegetation_chkBx_superS);
        saliyoChkBx=(CheckBox)root.findViewById(R.id.saliyo_chkBx_superS);
        scaledChkBx=(CheckBox)root.findViewById(R.id.scaled_chkBx_superS);
        spalityChkBx=(CheckBox)root.findViewById(R.id.spality_chkBx_superS);

        brokenChkBxHandRails=(CheckBox)root.findViewById(R.id.broken_chkBx_hand_superS);
        corrodedChkBxHandRail=(CheckBox)root.findViewById(R.id.corrded_chkBx_hand_superS);

        brokenChkBxFootPath=(CheckBox)root.findViewById(R.id.broken_foot_chkBx_superS);
        disintegratnNoChkBxFoot=(CheckBox)root.findViewById(R.id.disintegration_foot_chkBx_superS);
        spallingSpnrSuperS=(Spinner)root.findViewById(R.id.spalling_spnr_superS);

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormThreeFrgmnt conditionFormThreeFrgmnt = new ConditionFormThreeFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormThreeFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormFiveFrgmnt conditionFormFiveFrgmnt = new ConditionFormFiveFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormFiveFrgmnt);

                fragmentTransaction.commit();
            }
        });


        new asyncToGetDetails().execute();
        return root;

    }

    private void saveDetails() {

        spallingSpnrSuperSId = spallingSpnrMap.get(spallingSpnrSuperS.getSelectedItemPosition());

        if (crackedChkBx.isChecked())
            crackedId = "1";

        if (corrosionChkBx.isChecked())
            corrosionId = "1";

        if (spalledChkBx.isChecked())
            spalledId = "1";

        if (leachedChkBx.isChecked())
            leachedId = "1";

        if (buckledChkBx.isChecked())
            buckledId = "1";

        if (vegetationChkBx.isChecked())
            vegetationId = "1";

        if (saliyoChkBx.isChecked())
            saliyoId = "1";

        if (scaledChkBx.isChecked())
            scaledId = "1";

        if (spalityChkBx.isChecked())
            spalityId = "1";

        if (brokenChkBxHandRails.isChecked())
            brokenHandRailsId = "1";

        if (corrodedChkBxHandRail.isChecked())
            corrodedHandRailId = "1";

        if (brokenChkBxFootPath.isChecked())
            brokenFootPathId = "1";

        if (disintegratnNoChkBxFoot.isChecked())
            disintegratnNoFootId = "1";


        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("spallingSpnrSuperSId",spallingSpnrSuperSId );
        editor.putString("crackedId1",crackedId );
        editor.putString("corrosionId",corrosionId );
        editor.putString("spalledId",spalledId );
        editor.putString("leachedId",leachedId );
        editor.putString("buckledId",buckledId );
        editor.putString("vegetationId",vegetationId );
        editor.putString("saliyoId",saliyoId );
        editor.putString("scaledId",scaledId );
        editor.putString("spalityId",spalityId );
        editor.putString("brokenHandRailsId",brokenHandRailsId );
        editor.putString("corrodedHandRailId",corrodedHandRailId );
        editor.putString("brokenFootPathId",brokenFootPathId );
        editor.putString("disintegratnNoFootId",disintegratnNoFootId );

        editor.apply();
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
                        //id from 40
                        crackedId=cur.getString(40).toString();
                        corrosionId=cur.getString(41).toString();
                        spalledId=cur.getString(42).toString();
                        leachedId=cur.getString(43).toString();

                        buckledId=cur.getString(44).toString();
                        vegetationId=cur.getString(45).toString();
                        saliyoId=cur.getString(46).toString();
                        scaledId=cur.getString(47).toString();

                        spalityId=cur.getString(48).toString();
                        brokenHandRailsId=cur.getString(49).toString();
                        corrodedHandRailId=cur.getString(50).toString();
                        spallingSpnrSuperSId=cur.getString(51).toString();

                        brokenFootPathId=cur.getString(52).toString();
                        disintegratnNoFootId=cur.getString(53).toString();

                        //53

                    }while (cur.moveToNext());
                }

                getUserDetailsFromServer();

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

                final ArrayAdapter<String> adapter_leftCrack = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, spallingSpnrArr);
                adapter_leftCrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spallingSpnrSuperS.setAdapter(adapter_leftCrack);

                if(!TextUtils.isEmpty(dataItem.getHANDRAILSSPALLING()) && preset)
                {
                    int position = Integer.parseInt(dataItem.getHANDRAILSSPALLING());
                    spallingSpnrSuperS.setSelection(position);
                }


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
    private void getUserDetailsFromServer() {

        spallingSpnrArr = new String[4];

        spallingSpnrMap = new HashMap<Integer, String>();
        for(int i=0;i<4;i++)
        {
            String id = "0";
            if(i==0)
            {
                id="0";
                spallingSpnrArr[0]="None";

            }
            if(i==1)
            {
                id="1";
                spallingSpnrArr[1]="Normal";

            }
            if(i==2)
            {
                id="2";
                spallingSpnrArr[2]="Medium";

            } if(i==3)
        {
            id="3";
            spallingSpnrArr[3]="Severe";

        }
            spallingSpnrMap.put(i,id);

        }

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

                //id from 40
                ContentValues conditionCon=new ContentValues();

                conditionCon.put(dbase.CRACKED_SUPERS,crackedId);
                conditionCon.put(dbase.CORROSION_SUPERS,corrosionId);
                conditionCon.put(dbase.SPALLED_SUPERS,spalledId);
                conditionCon.put(dbase.LEACHED_SUPERS,leachedId);
                conditionCon.put(dbase.BUCKLED_SUPERS,buckledId);
                conditionCon.put(dbase.VEGETATION_SUPERS,vegetationId);
                conditionCon.put(dbase.SALIYO_SUPERS,saliyoId);
                conditionCon.put(dbase.SCALED_SUPERS,scaledId);
                conditionCon.put(dbase.SPALITY_SUPERS,spalityId);

                conditionCon.put(dbase.BROKEN_HR,brokenHandRailsId);
                conditionCon.put(dbase.CORRODED_HR,corrodedHandRailId);
                conditionCon.put(dbase.SPALLING_HR,spallingSpnrSuperSId);

                conditionCon.put(dbase.BROKEN_FP,brokenFootPathId);
                conditionCon.put(dbase.DISINTEGRATION_NO_Fp,disintegratnNoFootId);

                //53

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
