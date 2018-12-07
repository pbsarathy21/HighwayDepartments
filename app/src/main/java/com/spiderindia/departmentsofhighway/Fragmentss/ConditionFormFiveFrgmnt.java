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
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionFormFiveFrgmnt extends Fragment {

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

            if (!TextUtils.isEmpty(dataItem.getWEARINGCOATFAILEDJOINTS()) && dataItem.getWEARINGCOATFAILEDJOINTS().equalsIgnoreCase("1"))
            {
                failedJointChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getWEARINGCOATPOORDRAINAGE()) && dataItem.getWEARINGCOATPOORDRAINAGE().equalsIgnoreCase("1"))
            {
                poorDraingeChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getBEARINGSSTEELTILTED()) && dataItem.getBEARINGSSTEELTILTED().equalsIgnoreCase("1"))
            {
                tiledChkBx.setChecked(true);
            }

        }
    }

    public ConditionFormFiveFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    Context mContext;

    Spinner cracksSpnr,potholesSpnr,ravelledSpnr,rustedSpnr,flatteringSpnr,splitinalSpnr;
    String cracksSpnrId,potholesSpnrId,ravelledSpnrId,rustedSpnrId,flatteringSpnrId,splitinalSpnrId,failedJointChkBxId,poorDraingeChkBxId,tiledChkBxId;
    CheckBox failedJointChkBx,poorDraingeChkBx,tiledChkBx;
    String[] cracksSpnrArr,potholesSpnrArr,ravelledSpnrArr,rustedSpnrArr,flatteringSpnrArr,splitinalSpnrArr;;
    HashMap<Integer,String> cracksSpnrMap,potholesSpnrMap,ravelledSpnrMap,rustedSpnrMap,flatteringSpnrMap,splitinalSpnrMap;;

    MyDataBaseHandler dbase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment_condition_form_five_frgmnt, container, false);
        View root= inflater.inflate(R.layout.fragment_condition_form_five_frgmnt, container, false);

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

        cracksSpnr=(Spinner)root.findViewById(R.id.cracks_spinr_warn);
        potholesSpnr=(Spinner)root.findViewById(R.id.potholes_spnr);
        ravelledSpnr=(Spinner)root.findViewById(R.id.ravelled_spinr);
        rustedSpnr=(Spinner)root.findViewById(R.id.rusted_spinr_warn);
        flatteringSpnr=(Spinner)root.findViewById(R.id.flattering_spinner_warn);
        splitinalSpnr=(Spinner)root.findViewById(R.id.splitinal_crack_spinr_warn);

        failedJointChkBx=(CheckBox)root.findViewById(R.id.failed_joint_chkBx);
        poorDraingeChkBx=(CheckBox)root.findViewById(R.id.poor_drainage_chkBx);
        tiledChkBx=(CheckBox)root.findViewById(R.id.tiled_chkBx_warn);

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormFourFrgmnt conditionFormFourFrgmnt = new ConditionFormFourFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormFourFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormSixFrgmnt conditionFormSixFrgmnt = new ConditionFormSixFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormSixFrgmnt);

                fragmentTransaction.commit();
            }
        });


        new asyncToGetDetails().execute();

        return root;

    }

    private void saveDetails() {

        cracksSpnrId = cracksSpnrMap.get(cracksSpnr.getSelectedItemPosition());
        potholesSpnrId = potholesSpnrMap.get(potholesSpnr.getSelectedItemPosition());
        ravelledSpnrId = ravelledSpnrMap.get(ravelledSpnr.getSelectedItemPosition());
        rustedSpnrId = rustedSpnrMap.get(rustedSpnr.getSelectedItemPosition());
        flatteringSpnrId = flatteringSpnrMap.get(flatteringSpnr.getSelectedItemPosition());
        splitinalSpnrId = splitinalSpnrMap.get(splitinalSpnr.getSelectedItemPosition());


        if (failedJointChkBx.isChecked())
            failedJointChkBxId = "1";

        if (poorDraingeChkBx.isChecked())
            poorDraingeChkBxId = "1";

        if (tiledChkBx.isChecked())
            tiledChkBxId = "1";

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cracksSpnrId",cracksSpnrId );
        editor.putString("potholesSpnrId",potholesSpnrId );
        editor.putString("ravelledSpnrId",ravelledSpnrId );
        editor.putString("rustedSpnrId",rustedSpnrId );
        editor.putString("flatteringSpnrId",flatteringSpnrId );
        editor.putString("splitinalSpnrId",splitinalSpnrId );
        editor.putString("failedJointChkBxId",failedJointChkBxId );
        editor.putString("poorDraingeChkBxId",poorDraingeChkBxId );
        editor.putString("tiledChkBxId",tiledChkBxId );

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
                        //id from 54
                        cracksSpnrId=cur.getString(54).toString();
                        potholesSpnrId=cur.getString(55).toString();
                        ravelledSpnrId=cur.getString(56).toString();
                        failedJointChkBxId=cur.getString(57).toString();

                        poorDraingeChkBxId=cur.getString(58).toString();
                        rustedSpnrId=cur.getString(59).toString();
                        tiledChkBxId=cur.getString(60).toString();
                        flatteringSpnrId=cur.getString(61).toString();

                        splitinalSpnrId=cur.getString(62).toString();

                        //62

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

                final ArrayAdapter<String> adapter_leftCrack = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),R.layout.custom_spinner, cracksSpnrArr);
                adapter_leftCrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cracksSpnr.setAdapter(adapter_leftCrack);

                if(!TextUtils.isEmpty(dataItem.getWEARINGCOATCRACKS()) && preset)
                {
                    int position = Integer.parseInt(dataItem.getWEARINGCOATCRACKS());
                    cracksSpnr.setSelection(position);
                }



                final ArrayAdapter<String> adapter_potholesSpnr = new ArrayAdapter<>(getActivity(),R.layout.custom_spinner, potholesSpnrArr);
                adapter_potholesSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                potholesSpnr.setAdapter(adapter_potholesSpnr);

                if(!TextUtils.isEmpty(dataItem.getWEARINGCOATPOTHOLES()) && preset)
                {
                    int position1 = Integer.parseInt(dataItem.getWEARINGCOATPOTHOLES());
                    potholesSpnr.setSelection(position1);
                }



                final ArrayAdapter<String> adapter_ravelledSpnr = new ArrayAdapter<>(getActivity(),R.layout.custom_spinner, ravelledSpnrArr);
                adapter_ravelledSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ravelledSpnr.setAdapter(adapter_ravelledSpnr);

                if(!TextUtils.isEmpty(dataItem.getWEARINGCOATRAVELLED()) && preset)
                {
                    int position2 = Integer.parseInt(dataItem.getWEARINGCOATRAVELLED());
                    ravelledSpnr.setSelection(position2);

                }


                final ArrayAdapter<String> adapter_rustedSpnr = new ArrayAdapter<>(getActivity(),R.layout.custom_spinner, rustedSpnrArr);
                adapter_rustedSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rustedSpnr.setAdapter(adapter_rustedSpnr);

                if(!TextUtils.isEmpty(dataItem.getBEARINGSSTEELRUSTED()) && preset)
                {
                    int position3 = Integer.parseInt(dataItem.getBEARINGSSTEELRUSTED());
                    rustedSpnr.setSelection(position3);

                }


                final ArrayAdapter<String> adapter_flatteringSpnr = new ArrayAdapter<>(getActivity(),R.layout.custom_spinner, flatteringSpnrArr);
                adapter_flatteringSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                flatteringSpnr.setAdapter(adapter_flatteringSpnr);

                if(!TextUtils.isEmpty(dataItem.getBEARINGSELASTOFLATTERING()) && preset)
                {
                    int position4 = Integer.parseInt(dataItem.getBEARINGSELASTOFLATTERING());
                    flatteringSpnr.setSelection(position4);
                }



                final ArrayAdapter<String> adapter_splitinalSpnr = new ArrayAdapter<>(getActivity(),R.layout.custom_spinner, splitinalSpnrArr);
                adapter_splitinalSpnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                splitinalSpnr.setAdapter(adapter_splitinalSpnr);

                if(!TextUtils.isEmpty(dataItem.getBEARINGSELASTOCRACKING()) && preset)
                {
                    int position5 = Integer.parseInt(dataItem.getBEARINGSELASTOCRACKING());
                    splitinalSpnr.setSelection(position5);
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
    private void getUserDetailsFromServer() {

        cracksSpnrArr = new String[4];
        potholesSpnrArr = new String[4];
        ravelledSpnrArr = new String[4];
        rustedSpnrArr = new String[4];
        flatteringSpnrArr = new String[4];
        splitinalSpnrArr = new String[4];

        cracksSpnrMap = new HashMap<Integer, String>();
        potholesSpnrMap = new HashMap<Integer, String>();
        ravelledSpnrMap = new HashMap<Integer, String>();
        rustedSpnrMap = new HashMap<Integer, String>();
        flatteringSpnrMap = new HashMap<Integer, String>();
        splitinalSpnrMap = new HashMap<Integer, String>();

        for(int i=0;i<5;i++)
        {
            String id = "0";
            if(i==0)
            {
                id="0";
                cracksSpnrArr[0]="None";
                potholesSpnrArr[0]="None";
                ravelledSpnrArr[0]="None";
                rustedSpnrArr[0]="None";
                flatteringSpnrArr[0]="None";
                splitinalSpnrArr[0]="None";

            }

            if(i==1)
            {
                id="1";
                cracksSpnrArr[1]="Normal";
                potholesSpnrArr[1]="Normal";
                ravelledSpnrArr[1]="Normal";
                rustedSpnrArr[1]="Normal";
                flatteringSpnrArr[1]="Normal";
                splitinalSpnrArr[1]="Normal";
            }

            if(i==2)
            {
                id="2";
                cracksSpnrArr[2]="Medium";
                potholesSpnrArr[2]="Medium";
                ravelledSpnrArr[2]="Medium";
                rustedSpnrArr[2]="Medium";
                flatteringSpnrArr[2]="Medium";
                splitinalSpnrArr[2]="Medium";
            }

            if(i==3)
            {
                id="3";
                cracksSpnrArr[3]="Severe";
                potholesSpnrArr[3]="Severe";
                ravelledSpnrArr[3]="Severe";
                rustedSpnrArr[3]="Severe";
                flatteringSpnrArr[3]="Severe";
                splitinalSpnrArr[3]="Severe";
            }

            cracksSpnrMap.put(i,id);
            potholesSpnrMap.put(i,id);
            ravelledSpnrMap.put(i,id);
            rustedSpnrMap.put(i,id);
            flatteringSpnrMap.put(i,id);
            splitinalSpnrMap.put(i,id);

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

                //id from 54
                ContentValues conditionCon=new ContentValues();

                conditionCon.put(dbase.CRACKS_WC,cracksSpnrId);
                conditionCon.put(dbase.POTHOLES_WC,potholesSpnrId);
                conditionCon.put(dbase.RAVELLED_WC,ravelledSpnrId);
                conditionCon.put(dbase.FAILED_JOINTS_WC,failedJointChkBxId);
                conditionCon.put(dbase.POOR_DRAINAGE_WC,poorDraingeChkBxId);

                conditionCon.put(dbase.RUSTED_B,rustedSpnrId);
                conditionCon.put(dbase.TILTED_B,tiledChkBxId);
                conditionCon.put(dbase.FLATTERING_B,flatteringSpnrId);
                conditionCon.put(dbase.SPLITINAL_CRACK_B,splitinalSpnrId);

                //62
                
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
