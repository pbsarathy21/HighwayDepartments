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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventaryFormTwoFrgmnt extends Fragment implements AdapterView.OnItemSelectedListener {

    DataItem dataItem;

    @Override
    public void onStart() {
        super.onStart();


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            presetValues();

        }
    }

    private void presetValues() {

        dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

        designDischargeEdtTxt.setText(dataItem.getDESIGNDISCHARGE());
        noOfLanesEdtTxt.setText(dataItem.getNOOFLANES());
        spansEdtTxt.setText(dataItem.getNOOFSPANS());
        maxSpansEdtTxt.setText(dataItem.getMAXSPANWIDTH());
        verticalClearnceEdtTxt.setText(dataItem.getVERTICALCLEARANCE());

    }

    public InventaryFormTwoFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;
    EditText designDischargeEdtTxt,loadingEdtTxt,slabDeignEdtTxt,noOfLanesEdtTxt,spansEdtTxt,maxSpansEdtTxt,verticalClearnceEdtTxt;
    String designDischarge,loading,slabDeign,noOfLanes,spans,maxSpans,verticalClearnce;

    Context mContext;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    MyDataBaseHandler dbase;

    Spinner loadingSpinner, slabSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_inventary_form_two_frgmnt, container, false);

        mContext = container.getContext();

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

        designDischargeEdtTxt=(EditText)root.findViewById(R.id.design_dischrg_edtTxt);
       // loadingEdtTxt=(EditText)root.findViewById(R.id.loadinEdttxt) ;
       // slabDeignEdtTxt=(EditText)root.findViewById(R.id.slab_dsgn_edttxt);
        noOfLanesEdtTxt=(EditText)root.findViewById(R.id.no_lanes_edttxxt);
        spansEdtTxt=(EditText)root.findViewById(R.id.spans_edttxt);
        maxSpansEdtTxt=(EditText)root.findViewById(R.id.max_spans_edttxt);
        verticalClearnceEdtTxt=(EditText)root.findViewById(R.id.vertical_edttxt);

        loadingSpinner = (Spinner)root.findViewById(R.id.loading_spinner);
        slabSpinner = (Spinner)root.findViewById(R.id.slab_spinner);

        loadingSpinner.setOnItemSelectedListener(this);
        slabSpinner.setOnItemSelectedListener(this);

        loadSpinner();

        dbase = new MyDataBaseHandler(getActivity());

        new asyncToGetDetails().execute();

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormOneFrgmnt inventaryFormOneFrgmnt = new InventaryFormOneFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormOneFrgmnt);

                fragmentTransaction.commit();
            }
        });

        //By partha

        /*previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity._mViewPager.setCurrentItem(1);
            }
        });*/



        nextBttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormThreeFrgmnt inventaryFormThreeFrgmnt = new InventaryFormThreeFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormThreeFrgmnt);

                fragmentTransaction.commit();
            }
        });
        // By partha
        /*nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    designDischarge=designDischargeEdtTxt.getText().toString();
                    loading=loadingEdtTxt.getText().toString();
                    slabDeign=slabDeignEdtTxt.getText().toString();
                    noOfLanes=noOfLanesEdtTxt.getText().toString();
                    spans=spansEdtTxt.getText().toString();
                    maxSpans=maxSpansEdtTxt.getText().toString();
                    verticalClearnce=verticalClearnceEdtTxt.getText().toString();

                    new asyncToSendDetails().execute();

                    HomeActivity._mViewPager.setCurrentItem(3);
                }
                catch(NullPointerException e ) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });*/
        return root;
    }

    private void loadSpinner() {

        ArrayList<String> loadingList = new ArrayList<>();

        loadingList.add("Select");
        loadingList.add("Class A (Normal Type)");
        loadingList.add("Class 70R");

        final ArrayAdapter<String> loadingList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, loadingList);
        loadingList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loadingSpinner.setAdapter(loadingList_adapter);

        ArrayList<String> slabList = new ArrayList<>();

        slabList.add("Select");
        slabList.add("RCC T beam cum slab");
        slabList.add("RCC solid slab");
        slabList.add("Others");

        /*slabSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, slabList));*/


        final ArrayAdapter<String> slabList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, slabList);
        slabList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        slabSpinner.setAdapter(slabList_adapter);


    }

    private void saveDetails() {

        designDischarge=designDischargeEdtTxt.getText().toString();
      //  loading=loadingEdtTxt.getText().toString();
      //  slabDeign=slabDeignEdtTxt.getText().toString();
        noOfLanes=noOfLanesEdtTxt.getText().toString();
        spans=spansEdtTxt.getText().toString();
        maxSpans=maxSpansEdtTxt.getText().toString();
        verticalClearnce=verticalClearnceEdtTxt.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("design_discharge",designDischarge );


        editor.putString("no_of_lanes",noOfLanes );
        editor.putString("spans",spans );
        editor.putString("max_spans",maxSpans );
        editor.putString("vertical_clearance",verticalClearnce );

        editor.apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = adapterView.getId();

        if (id == R.id.loading_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("loading",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.slab_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("slab_design",spinnerString );
            editor.apply();
            return;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                //id from 10
                inventaryCon.put(dbase.DESIGN_DISCHARGE_I,designDischarge);
                inventaryCon.put(dbase.LOADING_I,loading);
                inventaryCon.put(dbase.SLAB_DESIGN_I,slabDeign);
                inventaryCon.put(dbase.NO_LANES_I,noOfLanes);
                inventaryCon.put(dbase.SPANS_I,spans);
                inventaryCon.put(dbase.MAX_SPANS_I,maxSpans);
                inventaryCon.put(dbase.VERTICAL_CLEARENCE_I,verticalClearnce);//16

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
                Log.v("Cursor Object form two", DatabaseUtils.dumpCursorToString(cur));

                if (cur.moveToFirst())
                {
                    do {

                        designDischargeEdtTxt.setText(cur.getString(10).toString());
                        loadingEdtTxt.setText(cur.getString(11).toString());
                        slabDeignEdtTxt.setText(cur.getString(12).toString());
                        noOfLanesEdtTxt.setText(cur.getString(13).toString());
                        spansEdtTxt.setText(cur.getString(14).toString());
                        maxSpansEdtTxt.setText(cur.getString(15).toString());
                        verticalClearnceEdtTxt.setText(cur.getString(16).toString());

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

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

}
