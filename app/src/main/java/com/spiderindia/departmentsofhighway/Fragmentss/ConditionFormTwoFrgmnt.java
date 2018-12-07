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

import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionFormTwoFrgmnt extends Fragment {

    boolean preset = false;

    DataItem dataItem;

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            preset = true;

            dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

                if (!TextUtils.isEmpty(dataItem.getFOUNDATIONSETTLEMENTUL()) &&  dataItem.getFOUNDATIONSETTLEMENTUL().equalsIgnoreCase("1"))
                {
                    leftSetlemntUpsChkBx.setChecked(true);
                }

            if (!TextUtils.isEmpty(dataItem.getFOUNDATIONSETTLEMENTUR()) && dataItem.getFOUNDATIONSETTLEMENTUR().equalsIgnoreCase("1"))
            {
                rightSetlemntUpSChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOUNDATIONSETTLEMENTDL()) && dataItem.getFOUNDATIONSETTLEMENTDL().equalsIgnoreCase("1"))
            {
                leftSetlemntDownSChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOUNDATIONSETTLEMENTDR()) && dataItem.getFOUNDATIONSETTLEMENTDR().equalsIgnoreCase("1"))
            {
                rightSetlemntDownSChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOUNDATIONSPALLEDPIERS()) && dataItem.getFOUNDATIONSPALLEDPIERS().equalsIgnoreCase("1"))
            {
                spalledChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOUNDATIONSCOURPIERS()) && dataItem.getFOUNDATIONSCOURPIERS().equalsIgnoreCase("1"))
            {
                scourChkBx.setChecked(true);
            }

            if (!TextUtils.isEmpty(dataItem.getFOUNDATIONCRACKEDPIERS()) && dataItem.getFOUNDATIONCRACKEDPIERS().equalsIgnoreCase("1"))
            {
                crackedChkBx.setChecked(true);
            }
        }
    }

    public ConditionFormTwoFrgmnt() {
        // Required empty public constructor
    }
    Button nextBttn,previousBttn;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    Context mContext;
    Spinner leftCrackUpSSpinr,rightCrackUpSSpinr,leftSplaityUpSSpinr,rightSplaityUpSSpinr,leftCrackDownSSpinr,rightCrackDownSSpinr,leftSplaityDownSSpinr,rightSplaityDownSSpinr;
    HashMap<Integer,String> leftCrackUpSSpinrMap,rightCrackUpSSpinrMap,leftSplaityUpSSpinrMap,rightSplaityUpSSpinrMap,leftCrackDownSSpinrMap,rightCrackDownSSpinrMap,leftSplaityDownSSpinrMap,rightSplaityDownSSpinrMap;
    String[] leftCrackUpSSpinrArr,rightCrackUpSSpinrArr,leftSplaityUpSSpinrArr,rightSplaityUpSSpinrArr,leftCrackDownSSpinrArr,rightCrackDownSSpinrArr,leftSplaityDownSSpinrArr,rightSplaityDownSSpinrArr;
    CheckBox leftSetlemntUpsChkBx,rightSetlemntUpSChkBx,leftSetlemntDownSChkBx,rightSetlemntDownSChkBx,scourChkBx,spalledChkBx,crackedChkBx;

   /* String[] leftCrackSpinnerArr;
    HashMap<Integer,String> leftCrackSpinnerMap;*/

    String leftCrackUpSSpinrId,rightCrackUpSSpinrId,leftSplaityUpSSpinrId,rightSplaityUpSSpinrId,leftCrackDownSSpinrId,rightCrackDownSSpinrId,leftSplaityDownSSpinrId,
            rightSplaityDownSSpinrId,leftSetlemntUpsChkBxId,rightSetlemntUpSChkBxId,leftSetlemntDownSChkBxId,rightSetlemntDownSChkBxId,scourChkBxId,spalledChkBxId,crackedChkBxId;
    MyDataBaseHandler dbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_condition_form_two_frgmnt, container, false);

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

        leftCrackUpSSpinr=(Spinner)root.findViewById(R.id.crack_left_spnr);
        rightCrackUpSSpinr=(Spinner)root.findViewById(R.id.right_crack_spnr);

        leftSplaityUpSSpinr=(Spinner)root.findViewById(R.id.left_spality_spnr);
        rightSplaityUpSSpinr=(Spinner)root.findViewById(R.id.right_spality_spnr);

        leftCrackDownSSpinr=(Spinner)root.findViewById(R.id.left_crack_spnr_downS);
        rightCrackDownSSpinr=(Spinner)root.findViewById(R.id.right_crack_spnr_downS);

        leftSplaityDownSSpinr=(Spinner)root.findViewById(R.id.left_spality_spnr_downS);
        rightSplaityDownSSpinr=(Spinner)root.findViewById(R.id.right_spality_spnr_downS);

        leftSetlemntUpsChkBx=(CheckBox)root.findViewById(R.id.left_setlemt_chkBx);
        leftSetlemntDownSChkBx=(CheckBox)root.findViewById(R.id.left_setlemnt_chkBx_downS);

        rightSetlemntUpSChkBx=(CheckBox)root.findViewById(R.id.right_setlment_chkBx);
        rightSetlemntDownSChkBx=(CheckBox)root.findViewById(R.id.right_setlemnt_chkBx_downS);


        scourChkBx=(CheckBox)root.findViewById(R.id.scour_chkBx);
        spalledChkBx=(CheckBox)root.findViewById(R.id.spalled_chkBx);
        crackedChkBx=(CheckBox)root.findViewById(R.id.cracked_chkBx);

        new asyncToGetetails().execute();

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormOneFrgmnt conditionFormOneFrgmnt = new ConditionFormOneFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormOneFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormThreeFrgmnt conditionFormThreeFrgmnt = new ConditionFormThreeFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormThreeFrgmnt);

                fragmentTransaction.commit();
            }
        });

        return root;

    }

    private void saveDetails() {

        leftCrackUpSSpinrId = leftCrackUpSSpinrMap.get(leftCrackUpSSpinr.getSelectedItemPosition());
        rightCrackUpSSpinrId = rightCrackUpSSpinrMap.get(rightCrackUpSSpinr.getSelectedItemPosition());
        leftSplaityUpSSpinrId = leftSplaityUpSSpinrMap.get(leftSplaityUpSSpinr.getSelectedItemPosition());
        rightSplaityUpSSpinrId = rightSplaityUpSSpinrMap.get(rightSplaityUpSSpinr.getSelectedItemPosition());

        leftCrackDownSSpinrId = leftCrackDownSSpinrMap.get(leftCrackDownSSpinr.getSelectedItemPosition());
        rightCrackDownSSpinrId = rightCrackDownSSpinrMap.get(rightCrackDownSSpinr.getSelectedItemPosition());
        leftSplaityDownSSpinrId = leftSplaityDownSSpinrMap.get(leftSplaityDownSSpinr.getSelectedItemPosition());
        rightSplaityDownSSpinrId = rightSplaityDownSSpinrMap.get(rightSplaityDownSSpinr.getSelectedItemPosition());

        if(leftSetlemntUpsChkBx.isChecked())
            leftSetlemntUpsChkBxId="1";

        if(rightSetlemntUpSChkBx.isChecked())
            rightSetlemntUpSChkBxId="1";

        if(leftSetlemntDownSChkBx.isChecked())
            leftSetlemntDownSChkBxId="1";

        if(rightSetlemntDownSChkBx.isChecked())
            rightSetlemntDownSChkBxId="1";

        if(scourChkBx.isChecked())
            scourChkBxId="1";

        if(spalledChkBx.isChecked())
            spalledChkBxId="1";

        if(crackedChkBx.isChecked())
            crackedChkBxId="1";

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("leftCrackUpSSpinrId",leftCrackUpSSpinrId );
        editor.putString("rightCrackUpSSpinrId",rightCrackUpSSpinrId );
        editor.putString("leftSplaityUpSSpinrId",leftSplaityUpSSpinrId );
        editor.putString("rightSplaityUpSSpinrId",rightSplaityUpSSpinrId );
        editor.putString("leftCrackDownSSpinrId",leftCrackDownSSpinrId );
        editor.putString("rightCrackDownSSpinrId",rightCrackDownSSpinrId );
        editor.putString("leftSplaityDownSSpinrId",leftSplaityDownSSpinrId );
        editor.putString("rightSplaityDownSSpinrId",rightSplaityDownSSpinrId );
        editor.putString("leftSetlemntUpsChkBxId",leftSetlemntUpsChkBxId );
        editor.putString("rightSetlemntUpSChkBxId",rightSetlemntUpSChkBxId );
        editor.putString("leftSetlemntDownSChkBxId",leftSetlemntDownSChkBxId );
        editor.putString("rightSetlemntDownSChkBxId",rightSetlemntDownSChkBxId );
        editor.putString("scourChkBxId",scourChkBxId );
        editor.putString("spalledChkBxId",spalledChkBxId );
        editor.putString("crackedChkBxId",crackedChkBxId );


        editor.apply();
    }

    class asyncToGetetails extends AsyncTask<Void, Void, String> {
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

                        //id from 1
                        leftCrackUpSSpinrId=cur.getString(5).toString();
                        rightCrackUpSSpinrId=cur.getString(6).toString();
                        leftSetlemntUpsChkBxId=cur.getString(7).toString();
                        rightSetlemntUpSChkBxId=cur.getString(8).toString();
                        leftSplaityUpSSpinrId=cur.getString(9).toString();
                        rightSplaityUpSSpinrId=cur.getString(10).toString();

                        leftCrackDownSSpinrId=cur.getString(11).toString();
                        rightCrackDownSSpinrId=cur.getString(12).toString();
                        leftSetlemntDownSChkBxId=cur.getString(13).toString();
                        rightSetlemntDownSChkBxId=cur.getString(14).toString();
                        leftSplaityDownSSpinrId=cur.getString(15).toString();
                        rightSplaityDownSSpinrId=cur.getString(16).toString();

                        scourChkBxId=cur.getString(17).toString();
                        spalledChkBxId=cur.getString(18).toString();
                        crackedChkBxId=cur.getString(19).toString();

                        //19

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


                if (progress_layout.getVisibility() == View.VISIBLE) {
                    progress_layout.setVisibility(View.GONE);
                }

                final ArrayAdapter<String> adapter_leftCrack = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, leftCrackUpSSpinrArr);
                adapter_leftCrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                leftCrackUpSSpinr.setAdapter(adapter_leftCrack);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONCRACKSUL()) && preset)
                {
                    Integer position = Integer.parseInt(dataItem.getFOUNDATIONCRACKSUL());

                    if (position<10)
                    leftCrackUpSSpinr.setSelection(position);

                }



                final ArrayAdapter<String> adapter_rightCrack = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, rightCrackUpSSpinrArr);
                adapter_rightCrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rightCrackUpSSpinr.setAdapter(adapter_rightCrack);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONCRACKSUR()) && preset)
                {
                    Integer position1 = Integer.parseInt(dataItem.getFOUNDATIONCRACKSUR());
                    if (position1!=null && position1<10) {
                        rightCrackUpSSpinr.setSelection(3);
                    }

                    else {
                        rightCrackUpSSpinr.setSelection(1);
                    }
                }


                final ArrayAdapter<String> adapter_leftSplaity = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, leftSplaityUpSSpinrArr);
                adapter_leftSplaity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                leftSplaityUpSSpinr.setAdapter(adapter_leftSplaity);
                leftSplaityUpSSpinr.setSelection(2);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONSPALITYUL()) && preset)
                {
                   int position2 = Integer.parseInt(dataItem.getFOUNDATIONSPALITYUL());
                    if (position2 != 0 && position2<10)
                    {
                        leftSplaityUpSSpinr.setSelection(position2);
                    }


                }



                final ArrayAdapter<String> adapter_rightSplaityUpSSpinr = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, rightSplaityUpSSpinrArr);
                adapter_rightSplaityUpSSpinr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rightSplaityUpSSpinr.setAdapter(adapter_rightSplaityUpSSpinr);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONSPALITYUR()) && preset)
                {
                    int position3 = Integer.parseInt(dataItem.getFOUNDATIONSPALITYUR());
                    if (position3<10)
                    rightSplaityUpSSpinr.setSelection(position3);

                }


                final ArrayAdapter<String> adapter_leftCrackDownSSpinr = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, leftCrackDownSSpinrArr);
                adapter_leftCrackDownSSpinr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                leftCrackDownSSpinr.setAdapter(adapter_leftCrackDownSSpinr);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONCRACKSDL()) && preset)
                {
                    int position4 = Integer.parseInt(dataItem.getFOUNDATIONCRACKSDL());
                    if (position4<10)
                    leftCrackDownSSpinr.setSelection(position4);
                }



                final ArrayAdapter<String> adapter_rightCrackDownSSpinr = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, rightCrackDownSSpinrArr);
                adapter_rightCrackDownSSpinr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rightCrackDownSSpinr.setAdapter(adapter_rightCrackDownSSpinr);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONCRACKSDR()) && preset)
                {
                    int position5 = Integer.parseInt(dataItem.getFOUNDATIONCRACKSDR());
                    if (position5<10)
                    rightCrackDownSSpinr.setSelection(position5);
                }



                final ArrayAdapter<String> adapter_leftSplaityDownSSpinr = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, leftSplaityDownSSpinrArr);
                adapter_leftSplaityDownSSpinr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                leftSplaityDownSSpinr.setAdapter(adapter_leftSplaityDownSSpinr);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONSPALITYDL()) && preset)
                {
                    int position6 = Integer.parseInt(dataItem.getFOUNDATIONSPALITYDL());
                    if (position6<10)
                    leftSplaityDownSSpinr.setSelection(position6);
                }



                final ArrayAdapter<String> adapter_rightSplaityDownSSpinr = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, rightSplaityDownSSpinrArr);
                adapter_rightSplaityDownSSpinr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rightSplaityDownSSpinr.setAdapter(adapter_rightSplaityDownSSpinr);

                if(!TextUtils.isEmpty(dataItem.getFOUNDATIONSPALITYDR()) && preset)
                {
                    int position7 = Integer.parseInt(dataItem.getFOUNDATIONSPALITYDR());
                    if (position7<10)
                    rightSplaityDownSSpinr.setSelection(position7);
                }




                /*leftCrackUpSSpinr.setSelection(Integer.parseInt(leftCrackUpSSpinrId));
                rightCrackUpSSpinr.setSelection(Integer.parseInt(rightCrackUpSSpinrId));

                leftSplaityUpSSpinr.setSelection(Integer.parseInt(leftSplaityUpSSpinrId));
                rightSplaityUpSSpinr.setSelection(Integer.parseInt(rightSplaityUpSSpinrId));

                leftCrackDownSSpinr.setSelection(Integer.parseInt(leftCrackDownSSpinrId));
                rightCrackDownSSpinr.setSelection(Integer.parseInt(rightCrackDownSSpinrId));

                leftSplaityDownSSpinr.setSelection(Integer.parseInt(leftSplaityDownSSpinrId));
                rightSplaityDownSSpinr.setSelection(Integer.parseInt(rightSplaityDownSSpinrId));

                if (leftSetlemntUpsChkBxId.equalsIgnoreCase("1")) {
                    leftSetlemntUpsChkBx.setChecked(true);
                }

                if (rightSetlemntUpSChkBxId.equalsIgnoreCase("1"))
                    rightSetlemntUpSChkBx.setChecked(true);

                if (leftSetlemntDownSChkBxId.equalsIgnoreCase("1"))
                    leftSetlemntDownSChkBx.setChecked(true);

                if (rightSetlemntDownSChkBxId.equalsIgnoreCase("1"))
                    rightSetlemntDownSChkBx.setChecked(true);


                if (scourChkBxId.equalsIgnoreCase("1"))
                    scourChkBx.setChecked(true);

                if (spalledChkBxId.equalsIgnoreCase("1"))
                    spalledChkBx.setChecked(true);

                if (crackedChkBxId.equalsIgnoreCase("1"))
                    crackedChkBx.setChecked(true);
*/
                if(!(response.equals("")) && response.equalsIgnoreCase("TRUE"))
                {

                }
                else
                {
                }

        }

    }
    private void getUserDetailsFromServer() {

        leftCrackUpSSpinrArr = new String[4];
        rightCrackUpSSpinrArr = new String[4];
        leftSplaityUpSSpinrArr = new String[4];
        rightSplaityUpSSpinrArr = new String[4];
        leftCrackDownSSpinrArr = new String[4];
        rightCrackDownSSpinrArr = new String[4];
        leftSplaityDownSSpinrArr = new String[4];
        rightSplaityDownSSpinrArr = new String[4];

        leftCrackUpSSpinrMap = new HashMap<Integer, String>();
        rightCrackUpSSpinrMap = new HashMap<Integer, String>();
        leftSplaityUpSSpinrMap = new HashMap<Integer, String>();
        rightSplaityUpSSpinrMap = new HashMap<Integer, String>();
        leftCrackDownSSpinrMap = new HashMap<Integer, String>();
        rightCrackDownSSpinrMap = new HashMap<Integer, String>();
        leftSplaityDownSSpinrMap = new HashMap<Integer, String>();
        rightSplaityDownSSpinrMap = new HashMap<Integer, String>();

        for(int i=0;i<4;i++)
        {
            String id = "0";
            if(i==0)
            {
                id="0";
                leftCrackUpSSpinrArr[0]="None";
                rightCrackUpSSpinrArr[0]="None";
                leftSplaityUpSSpinrArr[0]="None";
                rightSplaityUpSSpinrArr[0]="None";
                leftCrackDownSSpinrArr[0]="None";
                rightCrackDownSSpinrArr[0]="None";
                leftSplaityDownSSpinrArr[0]="None";
                rightSplaityDownSSpinrArr[0]="None";

            }
            if (i==1)
            {
                id="1";
                leftCrackUpSSpinrArr[1]="Normal";
                rightCrackUpSSpinrArr[1]="Normal";
                leftSplaityUpSSpinrArr[1]="Normal";
                rightSplaityUpSSpinrArr[1]="Normal";
                leftCrackDownSSpinrArr[1]="Normal";
                rightCrackDownSSpinrArr[1]="Normal";
                leftSplaityDownSSpinrArr[1]="Normal";
                rightSplaityDownSSpinrArr[1]="Normal";
            }
            if (i==2)
            {
                id="2";
                leftCrackUpSSpinrArr[2]="Medium";
                rightCrackUpSSpinrArr[2]="Medium";
                leftSplaityUpSSpinrArr[2]="Medium";
                rightSplaityUpSSpinrArr[2]="Medium";
                leftCrackDownSSpinrArr[2]="Medium";
                rightCrackDownSSpinrArr[2]="Medium";
                leftSplaityDownSSpinrArr[2]="Medium";
                rightSplaityDownSSpinrArr[2]="Medium";
            }
            if (i==3)
            {
                id="3";
                leftCrackUpSSpinrArr[3]="Severe";
                rightCrackUpSSpinrArr[3]="Severe";
                leftSplaityUpSSpinrArr[3]="Severe";
                rightSplaityUpSSpinrArr[3]="Severe";
                leftCrackDownSSpinrArr[3]="Severe";
                rightCrackDownSSpinrArr[3]="Severe";
                leftSplaityDownSSpinrArr[3]="Severe";
                rightSplaityDownSSpinrArr[3]="Severe";
            }

            leftCrackUpSSpinrMap.put(i,id);
            rightCrackUpSSpinrMap.put(i,id);
            leftSplaityUpSSpinrMap.put(i,id);
            rightSplaityUpSSpinrMap.put(i,id);
            leftCrackDownSSpinrMap.put(i,id);
            rightCrackDownSSpinrMap.put(i,id);
            leftSplaityDownSSpinrMap.put(i,id);
            rightSplaityDownSSpinrMap.put(i,id);

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

                //id from 5
                ContentValues conditionCon=new ContentValues();

                conditionCon.put(dbase.CRACKS_LEFT_UPS_F,leftCrackUpSSpinrId);
                conditionCon.put(dbase.CRACKS_RIGHT_UPS_F,rightCrackUpSSpinrId);
                conditionCon.put(dbase.SETTLEMENT_LEFT_UPS_F,leftSetlemntUpsChkBxId);
                conditionCon.put(dbase.SETTLEMENT_RIGHT_UPS_F,rightSetlemntUpSChkBxId);
                conditionCon.put(dbase.SPALIT_LEFT_UPS_F,leftSplaityUpSSpinrId);
                conditionCon.put(dbase.SPALIT_RIGHT_UPS_F,rightSplaityUpSSpinrId);

                conditionCon.put(dbase.CRACKS_LEFT_DOWNS_F,leftCrackDownSSpinrId);
                conditionCon.put(dbase.CRACKS_RIGHT_DOWNS_F,rightCrackDownSSpinrId);
                conditionCon.put(dbase.SETTLEMENT_LEFT_DOWNS_F,leftSetlemntDownSChkBxId);
                conditionCon.put(dbase.SETTLEMENT_RIGHT_DOWNS_F,rightSetlemntDownSChkBxId);
                conditionCon.put(dbase.SPALIT_LEFT_DOWNS_F,leftSplaityDownSSpinrId);
                conditionCon.put(dbase.SPALIT_RIGHT_DOWNS_F,rightSplaityDownSSpinrId);

                conditionCon.put(dbase.SCOUR_F,scourChkBxId);
                conditionCon.put(dbase.SPALLED_F,spalledChkBxId);
                conditionCon.put(dbase.CRACKED_F,crackedChkBxId);
                //19

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
