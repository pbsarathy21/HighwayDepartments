package com.spiderindia.departmentsofhighway.Fragmentss;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.NewActivities.CULVETS;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventaryFormOneFrgmnt extends Fragment implements AdapterView.OnItemSelectedListener {

    DataItem dataItem;

    @Override
    public void onStart() {
        super.onStart();


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            presetValues();

        } else {
        }

    }

    private void presetValues() {

        dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

        bLengthEdtTxt.setText(dataItem.getBRIDGELENGTH());
        bWidthEdtTxt.setText(dataItem.getBRIDGEWIDTH());
        riverNameEdtTxt.setText(dataItem.getRIVERNAME());
        linearWtrWatEdtTxt.setText(dataItem.getLINEARWATERWAY());
        linearWtrWatEdtTxt.setText(dataItem.getLINEARWATERWAY());

    }

    public InventaryFormOneFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;


    EditText brdgeTypeEdttxt,bLengthEdtTxt,bWidthEdtTxt,riverNameEdtTxt,authorityOfRiverEdtTxt,flowOfRiverEdtTxt,linearWtrWatEdtTxt;
    CheckBox partialWedChkBx,closeChkDamChkBx;
    Context mContext;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    String brdgType,bLength,bWidth,riverName,authorityofRiver,flowOfRiver,linearWtrWay,partialWidening,closeCheckDam;
    MyDataBaseHandler dbase;
    View view=null;
    boolean isViewShown;

    Spinner bridgeTypeSpinner, controllingSpinner, flowofRiverSpinner;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_inventary_form_one_frgmnt, container, false);
        view=root;


        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mContext = container.getContext();

        loading_process= (ProgressBar)root.findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout= (LinearLayout)root.findViewById(R.id.progress_loading_layout);

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dbase = new MyDataBaseHandler(getActivity());

        previousBttn=(Button)root.findViewById(R.id.previous_bttn);
        nextBttn=(Button)root.findViewById(R.id.nxt_btn);

       // brdgeTypeEdttxt=(EditText)root.findViewById(R.id.bidge_typ_edtTxt);
        bLengthEdtTxt=(EditText)root.findViewById(R.id.b_length_edt);
        bWidthEdtTxt=(EditText)root.findViewById(R.id.b_width_edt);
        riverNameEdtTxt=(EditText)root.findViewById(R.id.riverName_edttxt);
       // authorityOfRiverEdtTxt=(EditText)root.findViewById(R.id.authority_river_edt);
      //  flowOfRiverEdtTxt=(EditText)root.findViewById(R.id.flow_river_edt);
        linearWtrWatEdtTxt=(EditText)root.findViewById(R.id.linear_wtrwaY_edt);

        partialWedChkBx=(CheckBox)root.findViewById(R.id.patialWid_chkBx);
        closeChkDamChkBx=(CheckBox)root.findViewById(R.id.check_dam_chkBx);

        bridgeTypeSpinner = (Spinner) root.findViewById(R.id.bridge_type_spinner);
        controllingSpinner = (Spinner) root.findViewById(R.id.controlling_spinner);
        flowofRiverSpinner = (Spinner) root.findViewById(R.id.flow_of_river_spinner);

        bridgeTypeSpinner.setOnItemSelectedListener(this);
        controllingSpinner.setOnItemSelectedListener(this);
        flowofRiverSpinner.setOnItemSelectedListener(this);

      /*  *//*bridgeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               switch (view.getId())
               {
                   case R.id.bridge_type_spinner :
                   String name = adapterView.getItemAtPosition(i).toString();
                   Toast.makeText(mContext, ""+name, Toast.LENGTH_SHORT).show();
                   break;
               }

                Toast.makeText(mContext, "gone wrong", Toast.LENGTH_SHORT).show();

            }*//*

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        loadspinners();



        new asyncToGetDetails().execute();

        if (!isViewShown) {
            System.out.println("! isViewShown");

        }

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormTwoFrgmnt inventaryFormTwoFrgmnt = new InventaryFormTwoFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormTwoFrgmnt);

                fragmentTransaction.commit();
            }
        });
// By partha
       /* nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    brdgType=brdgeTypeEdttxt.getText().toString();
                    bLength=bLengthEdtTxt.getText().toString();
                    bWidth=bWidthEdtTxt.getText().toString();
                    riverName=riverNameEdtTxt.getText().toString();
                    authorityofRiver=authorityOfRiverEdtTxt.getText().toString();
                    flowOfRiver=flowOfRiverEdtTxt.getText().toString();
                    linearWtrWay=linearWtrWatEdtTxt.getText().toString();

                    if(partialWedChkBx.isChecked())
                    {
                        partialWidening="1";
                    }
                    else {
                        partialWidening="0";
                    }

                    if(closeChkDamChkBx.isChecked())
                    {
                        closeCheckDam="1";
                    }
                    else {
                        closeCheckDam="0";
                    }

                    new asyncToSendDetails().execute();

                    HomeActivity._mViewPager.setCurrentItem(2);

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
*/

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Can't go",Toast.LENGTH_SHORT).show();
                //  HomeActivity._mViewPager.setCurrentItem(1);
            }
        });


        return root;
    }


    private void loadspinners() {

        ArrayList<String> BridgeTypelist = new ArrayList<>();

        BridgeTypelist.add("Select");
        BridgeTypelist.add("Major");
        BridgeTypelist.add("Minor");
        BridgeTypelist.add("ROB (Railway Over Bridge)");
        BridgeTypelist.add("RUB (Railway Under Bridge)");
        BridgeTypelist.add("Foot Over Bridge");
        BridgeTypelist.add("Subway");

        /*bridgeTypeSpinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, BridgeTypelist));*/

        final ArrayAdapter<String> BridgeTypelist_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, BridgeTypelist);
        BridgeTypelist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bridgeTypeSpinner.setAdapter(BridgeTypelist_adapter);

        ArrayList<String> Controllinglist = new ArrayList<>();

        Controllinglist.add("Select");
        Controllinglist.add("Public");
        Controllinglist.add("PWD");
        Controllinglist.add("HD");
        Controllinglist.add("Others");

        /*controllingSpinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, Controllinglist));*/

        final ArrayAdapter<String> Controllinglist_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, Controllinglist);
        Controllinglist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        controllingSpinner.setAdapter(Controllinglist_adapter);

        ArrayList<String> flowofRiverlist = new ArrayList<>();

        flowofRiverlist.add("Select");
        flowofRiverlist.add("Left to Right");
        flowofRiverlist.add("Right to Left");

        /*flowofRiverSpinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, flowofRiverlist));*/

        final ArrayAdapter<String> flowofRiverlist_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, flowofRiverlist);
        flowofRiverlist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flowofRiverSpinner.setAdapter(flowofRiverlist_adapter);
    }

    private void saveDetails() {

       // brdgType=brdgeTypeEdttxt.getText().toString();
        bLength=bLengthEdtTxt.getText().toString();
        bWidth=bWidthEdtTxt.getText().toString();
        riverName=riverNameEdtTxt.getText().toString();
       // authorityofRiver=authorityOfRiverEdtTxt.getText().toString();
       // flowOfRiver=flowOfRiverEdtTxt.getText().toString();
        linearWtrWay=linearWtrWatEdtTxt.getText().toString();

        if(partialWedChkBx.isChecked())
        {
            partialWidening="1";
        }
        else {
            partialWidening="0";
        }

        if(closeChkDamChkBx.isChecked())
        {
            closeCheckDam="1";
        }
        else {
            closeCheckDam="0";
        }


        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("bridge_len",bLength );
        editor.putString("bridge_width",bWidth );
        editor.putString("river_name",riverName );


        editor.putString("linear_wtr_way",linearWtrWay );
        editor.putString("partial_widening",partialWidening );
        editor.putString("close_checkdam",closeCheckDam );

        editor.apply();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = adapterView.getId();

                if (id == R.id.bridge_type_spinner)
                {
                    String bridgeSpinnerString = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(mContext, ""+bridgeSpinnerString, Toast.LENGTH_SHORT).show();
                    editor.putString("bridge_type",bridgeSpinnerString );
                    editor.apply();
                    return;
                }

              if (id == R.id.controlling_spinner)
              {
                  String controllingSpinnerString = adapterView.getItemAtPosition(i).toString();
                  Toast.makeText(mContext, ""+controllingSpinnerString, Toast.LENGTH_SHORT).show();
                  editor.putString("authority_of_river",controllingSpinnerString );
                  editor.apply();
                  return;
              }

            if (id == R.id.flow_of_river_spinner)
            {
                String flowOfRiverSpinnerString = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(mContext, ""+flowOfRiverSpinnerString, Toast.LENGTH_SHORT).show();
                editor.putString("flow_of_river",flowOfRiverSpinnerString );
                editor.apply();
                return;
            }

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

     @Override
     public void setUserVisibleHint(boolean isVisibleToUser) {
         super.setUserVisibleHint(isVisibleToUser);

         if (isVisibleToUser && view!=null)
         {
             System.out.println("setUserVisibleHint hi");

             new asyncToGetNewDetails().execute();
         }

         if (getView() != null) {
             isViewShown = true;
             // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
            // fetchData();

         } else {
             isViewShown = false;
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

                ContentValues inventaryCon=new ContentValues();
                inventaryCon.put(dbase.BRIDGE_TYPE_I,brdgType);
                inventaryCon.put(dbase.LENGTH_I,bLength);
                inventaryCon.put(dbase.WIDTH_I,bWidth);
                inventaryCon.put(dbase.PARTIAL_WED_I,partialWidening);
                inventaryCon.put(dbase.RIVER_NAME_I,riverName);
                inventaryCon.put(dbase.CONTROL_AUTHORITY_RIVER_I,authorityofRiver);
                inventaryCon.put(dbase.RIVER_FLOW_I,flowOfRiver);
                inventaryCon.put(dbase.CHECK_DAM_I,closeCheckDam);
                inventaryCon.put(dbase.LINEAR_WATRE_WAY_I,linearWtrWay);

                boolean updateStatus=dbase.UpdateDetails(inventaryCon,dbase.TABLE_NAME_INVENTARY,dbase.INVENTARY_ID_I);
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
    class asyncToGetDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {

                Cursor cur=dbase.getAllDetails(dbase.TABLE_NAME_INVENTARY,dbase.INVENTARY_ID_I);
                Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cur));

                if (cur.moveToFirst())
                {
                    do {

                        brdgeTypeEdttxt.setText(cur.getString(1).toString());
                        bLengthEdtTxt.setText(cur.getString(2).toString());
                        bWidthEdtTxt.setText(cur.getString(3).toString());

                        //riverNameEdtTxt.setText(cur.getString(5).toString());
                        riverNameEdtTxt.setText(cur.getString(5).toString());
                        authorityOfRiverEdtTxt.setText(cur.getString(6).toString());
                        flowOfRiverEdtTxt.setText(cur.getString(7).toString());
                        //closeChkDamChkBx.setText(cur.getString(8).toString());
                        linearWtrWatEdtTxt.setText(cur.getString(9).toString());

                        partialWidening=cur.getString(4).toString();
                        closeCheckDam=cur.getString(8).toString();

                        System.out.println("In asyn brdge closeCheckDam"+closeCheckDam);

                        if(partialWidening.equalsIgnoreCase("1"))
                        {
                            partialWedChkBx.setChecked(true);
                        }
                        else {
                            partialWedChkBx.setChecked(false);
                        }

                        if(closeCheckDam.equalsIgnoreCase("1"))
                        {
                            System.out.println("closeCheckDam came"+closeCheckDam);
                            closeChkDamChkBx.setChecked(true);
                        }
                        else
                            {
                            closeChkDamChkBx.setChecked(false);
                        }

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

                if(!(response.equals("")) && response.equalsIgnoreCase("TRUE"))
                {

                }
                else
                {
                }

                if(closeCheckDam.equalsIgnoreCase("1"))
                {
                    closeChkDamChkBx.setChecked(true);
                }


            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    class asyncToGetNewDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {

                Cursor cur=dbase.getAllDetails(dbase.TABLE_NAME_INVENTARY,dbase.INVENTARY_ID_I);
                Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cur));


                if (cur.moveToFirst())
                {
                    do {

                        System.out.println("river name "+cur.getString(3).toString());
                        System.out.println("In asyn to checkkkkk  + "+cur.getString(1).toString());
                        System.out.println("In asyn to checkkkkk ");
                        brdgeTypeEdttxt.setText(cur.getString(1).toString());

                      /*  bLengthEdtTxt.setText(cur.getString(2).toString());
                        bWidthEdtTxt.setText(cur.getString(3).toString());

                        //riverNameEdtTxt.setText(cur.getString(5).toString());
                        riverNameEdtTxt.setText(cur.getString(3).toString());
                        authorityOfRiverEdtTxt.setText(cur.getString(6).toString());
                        flowOfRiverEdtTxt.setText(cur.getString(7).toString());
                        //closeChkDamChkBx.setText(cur.getString(8).toString());
                        linearWtrWatEdtTxt.setText(cur.getString(9).toString());

                        partialWidening=cur.getString(4).toString();
                        closeCheckDam=cur.getString(8).toString();

                        System.out.println("In asyn brdge closeCheckDam"+closeCheckDam);

                        if(partialWidening.equalsIgnoreCase("1"))
                        {
                            partialWedChkBx.setChecked(true);
                        }
                        else {
                            partialWedChkBx.setChecked(false);
                        }

                        if(closeCheckDam.equalsIgnoreCase("1"))
                        {
                            System.out.println("closeCheckDam came"+closeCheckDam);
                            closeChkDamChkBx.setChecked(true);
                        }
                        else {
                            closeChkDamChkBx.setChecked(false);
                        }
*/
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

                if(!(response.equals("")) && response.equalsIgnoreCase("TRUE"))
                {

                }
                else
                {

                }

               /* if(closeCheckDam.equalsIgnoreCase("1"))
                {
                    closeChkDamChkBx.setChecked(true);
                }*/


            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

  /*  @Override
    public void passDataToFragment(String someValue){
        activityAssignedValue = someValue;
        textView.setText(activityAssignedValue);
    }
*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        System.out.println("activity came now");
    }
}
