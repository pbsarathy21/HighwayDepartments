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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionFormOneFrgmnt extends Fragment implements AdapterView.OnItemSelectedListener {

    boolean preset = false;

    DataItem dataItem;

    boolean preloadSpinner = true;

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");
        boolean preload = sharedPreferences.getBoolean("preload", false);

        if (data.equalsIgnoreCase("true")) {
            presetValues();
            preset = true;
        }

        if (preload)
        {
            preloadSpinner = true;
            preloadValues();
        }
    }

    private void preloadValues() {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String bridge_angle = preferences.getString("bridge_angle", null);
        String bed_level = preferences.getString("bed_level", null);
        String bed_slope = preferences.getString("bed_slope", null);


        if (!TextUtils.isEmpty(bridge_angle))
        {
            brdgeAngleEdtTxt.setText(bridge_angle);
        }

        if (!TextUtils.isEmpty(bed_level))
        {
            bedLevelEdttxt.setText(bed_level);
        }

        if (!TextUtils.isEmpty(bed_slope))
        {
            bedSlopeEdtTxt.setText(bed_slope);
        }
    }

    private void presetValues() {

        dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

        brdgeAngleEdtTxt.setText(dataItem.getBRIDGEANGLE());
        bedLevelEdttxt.setText(dataItem.getBEDLEVEL());
        bedSlopeEdtTxt.setText(dataItem.getBEDSLOPE());
    }

    public ConditionFormOneFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;
    Context mContext;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    EditText shapePierEdtTxt,brdgeAngleEdtTxt,bedLevelEdttxt,bedSlopeEdtTxt;
    String shapePier,brdgeAngle,bedLevel,bedSlope;

    Spinner shapeOfPierSpinner;

    MyDataBaseHandler dbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_condition_form_one_frgmnt, container, false);
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

       // shapePierEdtTxt=(EditText)root.findViewById(R.id.shape_pier_edttxt);
        brdgeAngleEdtTxt=(EditText)root.findViewById(R.id.brdg_angle_edttxt);
        bedLevelEdttxt=(EditText)root.findViewById(R.id.bed_level_edttxt);
        bedSlopeEdtTxt=(EditText)root.findViewById(R.id.bed_slope_edttxt);

        shapeOfPierSpinner=(Spinner) root.findViewById(R.id.shape_of_pier_spinner);

        loadSpinner();

        dbase=new MyDataBaseHandler(getActivity());

        new asyncToGetDetails().execute();

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormFourFrgmnt inventaryFormFourFrgmnt = new InventaryFormFourFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormFourFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormTwoFrgmnt conditionFormTwoFrgmnt = new ConditionFormTwoFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormTwoFrgmnt);

                fragmentTransaction.commit();
            }
        });


        return root;
    }

    private void loadSpinner() {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String shape_pier = preferences.getString("shape_pier", null);


        ArrayList<String> shapeOfPierList = new ArrayList<>();

        shapeOfPierList.add("Select");
        shapeOfPierList.add("Rectangular");
        shapeOfPierList.add("Circular");

        /*shapeOfPierSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, shapeOfPierList));*/


        final ArrayAdapter<String> shapeOfPierList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, shapeOfPierList);
        shapeOfPierList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeOfPierSpinner.setAdapter(shapeOfPierList_adapter);

        if(preset)
        {
            int position = Integer.parseInt(dataItem.getSHAPEOFPIER());
            shapeOfPierSpinner.setSelection(position);
        }

        if (preloadSpinner && !TextUtils.isEmpty(shape_pier))
        {
            shapeOfPierSpinner.setSelection(Integer.parseInt(shape_pier));
        }


    }

    private void saveDetails() {

      //  shapePier=shapePierEdtTxt.getText().toString();
        brdgeAngle=brdgeAngleEdtTxt.getText().toString();
        bedLevel=bedLevelEdttxt.getText().toString();
        bedSlope=bedSlopeEdtTxt.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("bridge_angle",brdgeAngle );
        editor.putString("bed_level",bedLevel );
        editor.putString("bed_slope",bedSlope );


        editor.apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = adapterView.getId();

        if (id == R.id.shape_of_pier_spinner)
        {
           // String spinnerString = adapterView.getItemAtPosition(i).toString();
            String spinnerString = String.valueOf(i);
            editor.putString("shape_pier",spinnerString );
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

                //id from 1
                ContentValues conditionCon=new ContentValues();
                conditionCon.put(dbase.SHAPE_OF_PIER,shapePier);
                conditionCon.put(dbase.BRIDGE_ANGLE,brdgeAngle);
                conditionCon.put(dbase.BED_LEVEL,bedLevel);
                conditionCon.put(dbase.BED_SLOPE,bedSlope);
                //4


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

                        //id from 1
                        shapePierEdtTxt.setText(cur.getString(1).toString());
                        brdgeAngleEdtTxt.setText(cur.getString(2).toString());
                        bedLevelEdttxt.setText(cur.getString(3).toString());
                        bedSlopeEdtTxt.setText(cur.getString(4).toString());
                        //4

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
