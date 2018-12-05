package com.spiderindia.departmentsofhighway.Fragmentss;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;
import com.spiderindia.departmentsofhighway.Utils.CustomFontCheckBox;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionFormThreeFrgmnt extends Fragment {


    public ConditionFormThreeFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;

    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    Context mContext;
    Spinner leftCrackUpSSpinr,rightCrackUpSSpinr,leftCrackDownSSpinr,rightCrackDownSSpinr;
    CheckBox leftVegetatnChkBx,rightVegetationChkBx,leftVegetatnChkBxDownS,rightVegetationChkBxDownS,leftTiltingChkBx,lefTspalityChkBx,rightTiltingChkBx,rightSpalityChkBx,leftTiltingChkBxDownS,lefTspalityChkBxDownS,rightTiltingChkBxDownS,rightSpalityChkBxDownS;
    String leftCrackUpSSpinrId,rightCrackUpSSpinrId,leftCrackDownSSpinrId,rightCrackDownSSpinrId,leftVegetatnId,rightVegetationId,leftVegetatnDownSId,rightVegetationDownSId,leftTiltingId,lefTspalityId,rightTiltingId,rightSpalityId,leftTiltingDownSId,lefTspalityDownSId,rightTiltingDownSId,rightSpalityDownSId;
    CustomFontCheckBox cracksChkBx,tiltingChkBx,vegetationChkBx,spalityChkBx;
    String cracksId,tiltingId,vegetationId,spalityId;

    String[]  leftCrackUpSSpinrArr,rightCrackUpSSpinrArr,leftCrackDownSSpinrArr,rightCrackDownSSpinrArr;
    HashMap<Integer,String> leftCrackUpSSpinrMap,rightCrackUpSSpinrMap,leftCrackDownSSpinrMap,rightCrackDownSSpinrMap;
    MyDataBaseHandler dbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_condition_form_three_frgmnt, container, false);

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

        leftCrackUpSSpinr=(Spinner)root.findViewById(R.id.crack_left_spnr_subStr);
        rightCrackUpSSpinr=(Spinner)root.findViewById(R.id.right_crack_spnr_subStr);
        leftCrackDownSSpinr=(Spinner)root.findViewById(R.id.left_crack_spnr_downS_subStr);
        rightCrackDownSSpinr=(Spinner)root.findViewById(R.id.right_crack_spnr_downS_subStr);

        leftVegetatnChkBx=(CheckBox)root.findViewById(R.id.left_vegetatn_chkBx);
        leftVegetatnChkBxDownS=(CheckBox)root.findViewById(R.id.left_vegetatn_chkBx_downS);
        rightVegetationChkBx=(CheckBox)root.findViewById(R.id.right_vegetatn_chkBx);
        rightVegetationChkBxDownS=(CheckBox)root.findViewById(R.id.right_vegetatn_chkBx_downS);

        leftTiltingChkBx=(CheckBox)root.findViewById(R.id.left_tiling_chkBx);
        leftTiltingChkBxDownS=(CheckBox)root.findViewById(R.id.left_tiling_chkbx_downS);
        rightTiltingChkBx=(CheckBox)root.findViewById(R.id.right_tiling_chkbx);
        rightTiltingChkBxDownS=(CheckBox)root.findViewById(R.id.right_tiling_chkbx_downS);

        lefTspalityChkBx=(CheckBox)root.findViewById(R.id.left_spality_chkBx);
        lefTspalityChkBxDownS=(CheckBox)root.findViewById(R.id.left_spality_chkBx_downS);
        rightSpalityChkBx=(CheckBox)root.findViewById(R.id.right_splaity_chkBx);
        rightSpalityChkBxDownS=(CheckBox)root.findViewById(R.id.right_spality_chkBx_downS);

        cracksChkBx=(CustomFontCheckBox) root.findViewById(R.id.crack_chkBx_subS);
        tiltingChkBx=(CustomFontCheckBox)root.findViewById(R.id.tillting_chkBx_subS);
        vegetationChkBx=(CustomFontCheckBox)root.findViewById(R.id.vegetation_chkBx_subS);
        spalityChkBx=(CustomFontCheckBox)root.findViewById(R.id.spality_chkBx_subS);

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormTwoFrgmnt conditionFormTwoFrgmnt = new ConditionFormTwoFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormTwoFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormFourFrgmnt conditionFormFourFrgmnt = new ConditionFormFourFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormFourFrgmnt);

                fragmentTransaction.commit();
            }
        });
        //By partha

       /* nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    leftCrackUpSSpinrId = leftCrackUpSSpinrMap.get(leftCrackUpSSpinr.getSelectedItemPosition());
                    rightCrackUpSSpinrId = rightCrackUpSSpinrMap.get(rightCrackUpSSpinr.getSelectedItemPosition());
                    leftCrackDownSSpinrId = leftCrackDownSSpinrMap.get(leftCrackDownSSpinr.getSelectedItemPosition());
                    rightCrackDownSSpinrId = rightCrackDownSSpinrMap.get(rightCrackDownSSpinr.getSelectedItemPosition());

                    if (leftVegetatnChkBx.isChecked())
                        leftVegetatnId = "1";

                    if (rightVegetationChkBx.isChecked())
                        rightVegetationId = "1";

                    if (leftTiltingChkBx.isChecked())
                        leftTiltingId = "1";

                    if (rightTiltingChkBx.isChecked())
                        rightTiltingId = "1";

                    if (lefTspalityChkBx.isChecked())
                        lefTspalityId = "1";

                    if (rightSpalityChkBx.isChecked())
                        rightSpalityId = "1";

                    if (leftVegetatnChkBxDownS.isChecked())
                        leftVegetatnDownSId = "1";

                    if (rightVegetationChkBxDownS.isChecked())
                        rightVegetationDownSId = "1";

                    if (leftTiltingChkBxDownS.isChecked())
                        leftTiltingDownSId = "1";

                    if (rightTiltingChkBxDownS.isChecked())
                        rightTiltingDownSId = "1";

                    if (lefTspalityChkBxDownS.isChecked())
                        lefTspalityDownSId = "1";

                    if (rightSpalityChkBxDownS.isChecked())
                        rightSpalityDownSId = "1";

                    if (cracksChkBx.isChecked())
                        cracksId = "1";

                    if (spalityChkBx.isChecked())
                        spalityId = "1";

                    if (vegetationChkBx.isChecked())
                        vegetationId = "1";

                    if (tiltingChkBx.isChecked())
                        tiltingId = "1";

                    new asyncToSendDetails().execute();

                    HomeActivity._mViewPager.setCurrentItem(8);
                }
                catch(NullPointerException e ) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new asyncToSendDetails().execute();

                HomeActivity._mViewPager.setCurrentItem(6);
            }
        });*/
        new asyncToGetDetails().execute();
        return root;


    }

    private void saveDetails() {

        leftCrackUpSSpinrId = leftCrackUpSSpinrMap.get(leftCrackUpSSpinr.getSelectedItemPosition());
        rightCrackUpSSpinrId = rightCrackUpSSpinrMap.get(rightCrackUpSSpinr.getSelectedItemPosition());
        leftCrackDownSSpinrId = leftCrackDownSSpinrMap.get(leftCrackDownSSpinr.getSelectedItemPosition());
        rightCrackDownSSpinrId = rightCrackDownSSpinrMap.get(rightCrackDownSSpinr.getSelectedItemPosition());

        if (leftVegetatnChkBx.isChecked())
            leftVegetatnId = "1";

        if (rightVegetationChkBx.isChecked())
            rightVegetationId = "1";

        if (leftTiltingChkBx.isChecked())
            leftTiltingId = "1";

        if (rightTiltingChkBx.isChecked())
            rightTiltingId = "1";

        if (lefTspalityChkBx.isChecked())
            lefTspalityId = "1";

        if (rightSpalityChkBx.isChecked())
            rightSpalityId = "1";

        if (leftVegetatnChkBxDownS.isChecked())
            leftVegetatnDownSId = "1";

        if (rightVegetationChkBxDownS.isChecked())
            rightVegetationDownSId = "1";

        if (leftTiltingChkBxDownS.isChecked())
            leftTiltingDownSId = "1";

        if (rightTiltingChkBxDownS.isChecked())
            rightTiltingDownSId = "1";

        if (lefTspalityChkBxDownS.isChecked())
            lefTspalityDownSId = "1";

        if (rightSpalityChkBxDownS.isChecked())
            rightSpalityDownSId = "1";

        if (cracksChkBx.isChecked())
            cracksId = "1";

        if (spalityChkBx.isChecked())
            spalityId = "1";

        if (vegetationChkBx.isChecked())
            vegetationId = "1";

        if (tiltingChkBx.isChecked())
            tiltingId = "1";

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("leftCrackUpSSpinrId1",leftCrackUpSSpinrId );
        editor.putString("rightCrackUpSSpinrId1",rightCrackUpSSpinrId );
        editor.putString("leftVegetatnId",leftVegetatnId );
        editor.putString("rightVegetationId",rightVegetationId );
        editor.putString("leftCrackDownSSpinrId1",leftCrackDownSSpinrId );
        editor.putString("rightCrackDownSSpinrId1",rightCrackDownSSpinrId );
        editor.putString("leftTiltingId",leftTiltingId );
        editor.putString("rightTiltingId",rightTiltingId );
        editor.putString("lefTspalityId",lefTspalityId );
        editor.putString("rightSpalityId",rightSpalityId );
        editor.putString("leftVegetatnDownSId",leftVegetatnDownSId );
        editor.putString("rightVegetationDownSId",rightVegetationDownSId );
        editor.putString("leftTiltingDownSId",leftTiltingDownSId );
        editor.putString("rightTiltingDownSId",rightTiltingDownSId );
        editor.putString("lefTspalityDownSId",lefTspalityDownSId );
        editor.putString("rightSpalityDownSId",rightSpalityDownSId );
        editor.putString("cracksId",cracksId );
        editor.putString("spalityId1",spalityId );
        editor.putString("vegetationId1",vegetationId );
        editor.putString("tiltingId",tiltingId );


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

                        //id from 20

                        leftCrackUpSSpinrId=cur.getString(20).toString();
                        rightCrackUpSSpinrId=cur.getString(21).toString();
                        leftVegetatnId=cur.getString(22).toString();
                        rightVegetationId=cur.getString(23).toString();

                        leftTiltingId=cur.getString(24).toString();
                        rightTiltingId=cur.getString(25).toString();
                        lefTspalityId=cur.getString(26).toString();
                        rightSpalityId=cur.getString(27).toString();

                        leftCrackDownSSpinrId=cur.getString(28).toString();
                        rightCrackDownSSpinrId=cur.getString(29).toString();
                        leftVegetatnDownSId=cur.getString(30).toString();
                        rightVegetationDownSId=cur.getString(31).toString();

                        leftTiltingDownSId=cur.getString(32).toString();
                        rightTiltingDownSId=cur.getString(33).toString();
                        lefTspalityDownSId=cur.getString(34).toString();
                        rightSpalityDownSId=cur.getString(35).toString();

                        cracksId=cur.getString(36).toString();
                        tiltingId=cur.getString(37).toString();
                        vegetationId=cur.getString(38).toString();
                        spalityId=cur.getString(39).toString();

                        //39

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

                final ArrayAdapter<String> adapter_leftCrack = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, leftCrackUpSSpinrArr);
                adapter_leftCrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                leftCrackUpSSpinr.setAdapter(adapter_leftCrack);

                final ArrayAdapter<String> adapter_rightCrackUp = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, rightCrackUpSSpinrArr);
                adapter_rightCrackUp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rightCrackUpSSpinr.setAdapter(adapter_rightCrackUp);

                final ArrayAdapter<String> adapter_leftCrackDown = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, leftCrackDownSSpinrArr);
                adapter_leftCrackDown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                leftCrackDownSSpinr.setAdapter(adapter_leftCrackDown);

                final ArrayAdapter<String> adapter_rightCrackDown = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, rightCrackDownSSpinrArr);
                adapter_rightCrackDown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                rightCrackDownSSpinr.setAdapter(adapter_rightCrackDown);


                leftCrackUpSSpinr.setSelection(Integer.parseInt(leftCrackUpSSpinrId));
                rightCrackUpSSpinr.setSelection(Integer.parseInt(rightCrackUpSSpinrId));

                leftCrackDownSSpinr.setSelection(Integer.parseInt(leftCrackDownSSpinrId));
                rightCrackDownSSpinr.setSelection(Integer.parseInt(rightCrackDownSSpinrId));

                if (leftVegetatnId.equalsIgnoreCase("1")) {
                    leftVegetatnChkBx.setChecked(true);
                }

                if (rightVegetationId.equalsIgnoreCase("1"))
                    rightVegetationChkBx.setChecked(true);

                if (leftTiltingId.equalsIgnoreCase("1"))
                    leftTiltingChkBx.setChecked(true);

                if (rightTiltingId.equalsIgnoreCase("1"))
                    rightTiltingChkBx.setChecked(true);

                if (lefTspalityId.equalsIgnoreCase("1")) {
                    lefTspalityChkBx.setChecked(true);
                }

                if (rightSpalityId.equalsIgnoreCase("1"))
                    rightSpalityChkBx.setChecked(true);

                if (leftVegetatnDownSId.equalsIgnoreCase("1"))
                    leftVegetatnChkBxDownS.setChecked(true);

                if (rightVegetationDownSId.equalsIgnoreCase("1"))
                    rightVegetationChkBxDownS.setChecked(true);

                if (leftTiltingDownSId.equalsIgnoreCase("1"))
                    leftTiltingChkBxDownS.setChecked(true);


                if (rightTiltingDownSId.equalsIgnoreCase("1"))
                    rightTiltingChkBxDownS.setChecked(true);

                if (lefTspalityDownSId.equalsIgnoreCase("1"))
                    lefTspalityChkBxDownS.setChecked(true);
                if (rightSpalityDownSId.equalsIgnoreCase("1"))
                    rightSpalityChkBxDownS.setChecked(true);


                if (cracksId.equalsIgnoreCase("1"))
                    cracksChkBx.setChecked(true);

                if (tiltingId.equalsIgnoreCase("1"))
                    tiltingChkBx.setChecked(true);

                if (vegetationId.equalsIgnoreCase("1"))
                    vegetationChkBx.setChecked(true);

                if (spalityId.equalsIgnoreCase("1"))
                    spalityChkBx.setChecked(true);

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

        leftCrackUpSSpinrArr = new String[4];
        rightCrackUpSSpinrArr = new String[4];
        leftCrackDownSSpinrArr = new String[4];
        rightCrackDownSSpinrArr = new String[4];

        leftCrackUpSSpinrMap = new HashMap<Integer, String>();
        rightCrackUpSSpinrMap = new HashMap<Integer, String>();
        leftCrackDownSSpinrMap = new HashMap<Integer, String>();
        rightCrackDownSSpinrMap = new HashMap<Integer, String>();

        for(int i=0;i<4;i++)
        {
            String id = "0";
            if(i==0)
            {
                id="0";
                leftCrackUpSSpinrArr[0]="None";
                rightCrackUpSSpinrArr[0]="None";
                leftCrackDownSSpinrArr[0]="None";
                rightCrackDownSSpinrArr[0]="None";

            }
            if (i==1)
            {
                id="1";
                leftCrackUpSSpinrArr[1]="Normal";
                rightCrackUpSSpinrArr[1]="Normal";
                leftCrackDownSSpinrArr[1]="Normal";
                rightCrackDownSSpinrArr[1]="Normal";
            }

            if (i==2)
            {
                id="2";
                leftCrackUpSSpinrArr[2]="Medium";
                rightCrackUpSSpinrArr[2]="Medium";
                leftCrackDownSSpinrArr[2]="Medium";
                rightCrackDownSSpinrArr[2]="Medium";
            }

            if (i==3)
            {
                id="3";
                leftCrackUpSSpinrArr[3]="Severe";
                rightCrackUpSSpinrArr[3]="Severe";
                leftCrackDownSSpinrArr[3]="Severe";
                rightCrackDownSSpinrArr[3]="Severe";
            }
            leftCrackUpSSpinrMap.put(i,id);
            rightCrackUpSSpinrMap.put(i,id);
            leftCrackDownSSpinrMap.put(i,id);
            rightCrackDownSSpinrMap.put(i,id);

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

                //id from 19
                ContentValues conditionCon=new ContentValues();

                conditionCon.put(dbase.CRACKS_LEFT_UPS_SUBS,leftCrackUpSSpinrId);
                conditionCon.put(dbase.CRACKS_RIGHT_UPS_SUBS,rightCrackUpSSpinrId);
                conditionCon.put(dbase.VEGETATION_LEFT_UPS_SUBS,leftVegetatnId);
                conditionCon.put(dbase.VEGETATION_RIGHT_UPS_SUBS,rightVegetationId);

                conditionCon.put(dbase.TILTING_LEFT_UPS_SUBS,leftTiltingId);
                conditionCon.put(dbase.TILTING_RIGHT_UPS_SUBS,rightTiltingId);
                conditionCon.put(dbase.SPALIT_LEFT_UPS_SUBS,lefTspalityId);
                conditionCon.put(dbase.SPALIT_RIGHT_UPS_SUBS,rightSpalityId);

                conditionCon.put(dbase.CRACKS_LEFT_DOWNS_SUBS,leftCrackDownSSpinrId);
                conditionCon.put(dbase.CRACKS_RIGHT_DOWNS_SUBS,rightCrackDownSSpinrId);
                conditionCon.put(dbase.VEGETATION_LEFT_DOWNS_SUBS,leftVegetatnDownSId);
                conditionCon.put(dbase.VEGETATION_RIGHT_DOWNS_SUBS,rightVegetationDownSId);

                conditionCon.put(dbase.TILTING_LEFT_DOWNS_SUBS,leftTiltingDownSId);
                conditionCon.put(dbase.TILTING_RIGHT_DOWNS_SUBS,rightTiltingDownSId);
                conditionCon.put(dbase.SPALIT_LEFT_DOWNS_SUBS,lefTspalityDownSId);
                conditionCon.put(dbase.SPALIT_RIGHT_DOWNS_SUBS,rightSpalityDownSId);

                conditionCon.put(dbase.CRACKS_PIERS,cracksId);
                conditionCon.put(dbase.TILTING_PIERS,tiltingId);
                conditionCon.put(dbase.VEGETATION_PIERS,vegetationId);
                conditionCon.put(dbase.SPALIT_PIERS,spalityId);

                //39
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
