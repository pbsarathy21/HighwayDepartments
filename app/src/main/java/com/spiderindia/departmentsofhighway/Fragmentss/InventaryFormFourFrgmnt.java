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
public class InventaryFormFourFrgmnt extends Fragment implements AdapterView.OnItemSelectedListener {

    boolean preset = false;

    boolean preloadSpinner = true;

    DataItem dataItem;

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
        String MLF = preferences.getString("MLF", null);
        String floor_protection_type = preferences.getString("floor_protection_type", null);

        if (!TextUtils.isEmpty(MLF))
        {
            MLFEdtTxt.setText(MLF);
        }

        if (!TextUtils.isEmpty(floor_protection_type))
        {
            floorProtectnTypEdtTxt.setText(floor_protection_type);
        }
    }

    private void presetValues() {

        dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

        MLFEdtTxt.setText(dataItem.getMFL());
        floorProtectnTypEdtTxt.setText(dataItem.getFLOORPROTECTIONTYPE());
    }

    public InventaryFormFourFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;
    Context mContext;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";

    EditText abutmentFounEdtTxt,MLFEdtTxt,bankProtectTypeEdtTxt,approachTypEdtTxt,floorProtctnEdtTxt,floorProtectnTypEdtTxt,typeSubStructureEdtTxt;
    String abutmentFoun,MLF,bankProtectType,approachTyp,floorProtctn,floorProtectnTyp,typeSubStructure;

    Spinner abutmentSpinner, bankProtectionSpinner, approachSpinner, floorProtectionSpinner, typeOfSucstructureSpinner;

    MyDataBaseHandler dbase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_inventary_form_four_frgmnt, container, false);
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

      //  abutmentFounEdtTxt=(EditText)root.findViewById(R.id.abutmnt_foundtn_edtTxt);
        MLFEdtTxt=(EditText)root.findViewById(R.id.MLF_edtTxt);
      //  bankProtectTypeEdtTxt=(EditText)root.findViewById(R.id.bank_protext_type_edt);
      // approachTypEdtTxt=(EditText)root.findViewById(R.id.approach_type_edt);
       // floorProtctnEdtTxt=(EditText)root.findViewById(R.id.floor_protectn_edttxt);
        floorProtectnTypEdtTxt=(EditText)root.findViewById(R.id.floor_protectn_typ_edttxt);
      //  typeSubStructureEdtTxt=(EditText)root.findViewById(R.id.typ_of_sub_structre);

        abutmentSpinner=(Spinner) root.findViewById(R.id.abutment_spinner);
        bankProtectionSpinner=(Spinner) root.findViewById(R.id.bank_protection_spinner);
        approachSpinner=(Spinner) root.findViewById(R.id.approach_spinner);
        floorProtectionSpinner=(Spinner) root.findViewById(R.id.floor_protection_spinner);
        typeOfSucstructureSpinner=(Spinner) root.findViewById(R.id.type_of_substructure_spinner);

        abutmentSpinner.setOnItemSelectedListener(this);
        bankProtectionSpinner.setOnItemSelectedListener(this);
        approachSpinner.setOnItemSelectedListener(this);
        floorProtectionSpinner.setOnItemSelectedListener(this);
        typeOfSucstructureSpinner.setOnItemSelectedListener(this);

        loadSpinners();

        new asyncToGetDetails().execute();

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormThreeFrgmnt inventaryFormThreeFrgmnt = new InventaryFormThreeFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormThreeFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormOneFrgmnt conditionFormOneFrgmnt = new ConditionFormOneFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormOneFrgmnt);

                fragmentTransaction.commit();
            }
        });

        return root;

    }

    private void loadSpinners() {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String abutment_foundation = preferences.getString("abutment_foundation", null);
        String bank_protectType = preferences.getString("bank_protectType", null);
        String approach_type = preferences.getString("approach_type", null);
        String floor_protection = preferences.getString("floor_protection", null);
        String type_substructure = preferences.getString("type_substructure", null);

        ArrayList<String> abutmentList = new ArrayList<>();

        abutmentList.add("Select");
        abutmentList.add("pile");
        abutmentList.add("Well");
        abutmentList.add("Open");
        abutmentList.add("Raft");

       /* abutmentSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, abutmentList));*/

        final ArrayAdapter<String> abutmentList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, abutmentList);
        abutmentList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        abutmentSpinner.setAdapter(abutmentList_adapter);

        if(preset)
        {
            int position = Integer.parseInt(dataItem.getABUTMENTFOUNDATION());
            abutmentSpinner.setSelection(position);
        }

        if (preloadSpinner && !TextUtils.isEmpty(abutment_foundation))
        {
            abutmentSpinner.setSelection(Integer.parseInt(abutment_foundation));
        }


        ArrayList<String> bankProtectionList = new ArrayList<>();

        bankProtectionList.add("Select");
        bankProtectionList.add("Stone");
        bankProtectionList.add("Slab");
        bankProtectionList.add("Others");

       /* bankProtectionSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, bankProtectionList));*/

        final ArrayAdapter<String> bankProtectionList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, bankProtectionList);
        bankProtectionList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankProtectionSpinner.setAdapter(bankProtectionList_adapter);

        if(preset)
        {
            int position1 = Integer.parseInt(dataItem.getBANKPROTECTIONTYPE());
            bankProtectionSpinner.setSelection(position1);
        }

        if (preloadSpinner && !TextUtils.isEmpty(bank_protectType))
        {
            bankProtectionSpinner.setSelection(Integer.parseInt(bank_protectType));
        }




        ArrayList<String> approachList = new ArrayList<>();

        approachList.add("Select");
        approachList.add("Protected");
        approachList.add("Unprotected");

       /* approachSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, approachList));*/

        final ArrayAdapter<String> approachList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, approachList);
        approachList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        approachSpinner.setAdapter(approachList_adapter);

        if(preset)
        {
            int position2 = Integer.parseInt(dataItem.getAPPROACHTYPE());
            approachSpinner.setSelection(position2);
        }

        if (preloadSpinner && !TextUtils.isEmpty(approach_type))
        {
            approachSpinner.setSelection(Integer.parseInt(approach_type));
        }




        ArrayList<String> floorProtectionList = new ArrayList<>();

        floorProtectionList.add("Select");
        floorProtectionList.add("Yes");
        floorProtectionList.add("No");

        /*floorProtectionSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, floorProtectionList));*/

        final ArrayAdapter<String> floorProtectionList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, floorProtectionList);
        floorProtectionList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorProtectionSpinner.setAdapter(floorProtectionList_adapter);

        if(preset)
        {
            int position3 = Integer.parseInt(dataItem.getFLOORPROTECTION());
            floorProtectionSpinner.setSelection(position3);
        }

        if (preloadSpinner && !TextUtils.isEmpty(floor_protection))
        {
            floorProtectionSpinner.setSelection(Integer.parseInt(floor_protection));
        }




        ArrayList<String> typeOfSubstructureList = new ArrayList<>();

        typeOfSubstructureList.add("Select");
        typeOfSubstructureList.add("PCC");
        typeOfSubstructureList.add("RCC");

       /* typeOfSucstructureSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, typeOfSubstructureList));*/

        final ArrayAdapter<String> typeOfSubstructureList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, typeOfSubstructureList);
        typeOfSubstructureList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfSucstructureSpinner.setAdapter(typeOfSubstructureList_adapter);

        if(preset)
        {
            int position4 = Integer.parseInt(dataItem.getTYPEOFSUBSTRUCTURE());
            typeOfSucstructureSpinner.setSelection(position4);
        }

        if (preloadSpinner && !TextUtils.isEmpty(type_substructure))
        {
            typeOfSucstructureSpinner.setSelection(Integer.parseInt(type_substructure));
        }


    }

    private void saveDetails() {

       // abutmentFoun=abutmentFounEdtTxt.getText().toString();
        MLF=MLFEdtTxt.getText().toString();
       // bankProtectType=bankProtectTypeEdtTxt.getText().toString();
       // approachTyp=approachTypEdtTxt.getText().toString();
       // floorProtctn=floorProtctnEdtTxt.getText().toString();
        floorProtectnTyp=floorProtectnTypEdtTxt.getText().toString();
       // typeSubStructure=typeSubStructureEdtTxt.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("MLF",MLF );
        editor.putString("floor_protection_type",floorProtectnTyp );
        editor.apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = adapterView.getId();

        if (id == R.id.abutment_spinner)
        {
            //String spinnerString = adapterView.getItemAtPosition(i).toString();
            String spinnerString = String.valueOf(i);
            editor.putString("abutment_foundation",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.bank_protection_spinner)
        {
            //String spinnerString = adapterView.getItemAtPosition(i).toString();
            String spinnerString = String.valueOf(i);
            editor.putString("bank_protectType",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.approach_spinner)
        {
            //String spinnerString = adapterView.getItemAtPosition(i).toString();
            String spinnerString = String.valueOf(i);
            editor.putString("approach_type",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.floor_protection_spinner)
        {
            //String spinnerString = adapterView.getItemAtPosition(i).toString();
            String spinnerString = String.valueOf(i);
            editor.putString("floor_protection",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.type_of_substructure_spinner)
        {
            //String spinnerString = adapterView.getItemAtPosition(i).toString();
            String spinnerString = String.valueOf(i);
            editor.putString("type_substructure",spinnerString );
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
                //id from 24
                inventaryCon.put(dbase.ABUTMNT_FOUNDATN_I,abutmentFoun);
                inventaryCon.put(dbase.MLF_I,MLF);
                inventaryCon.put(dbase.BANK_PROTECTION_TYP_I,bankProtectType);
                inventaryCon.put(dbase.APPROACH_TYP_I,approachTyp);
                inventaryCon.put(dbase.FLOOR_PROTECTION_I,floorProtctn);
                inventaryCon.put(dbase.FLOOR_PROTECTION_TYP_I,floorProtectnTyp);
                inventaryCon.put(dbase.TYPE_OF_SUBSTRUCTURE_I,typeSubStructure);
                
               //31


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

                        //id from 24
                        abutmentFounEdtTxt.setText(cur.getString(25).toString());
                        MLFEdtTxt.setText(cur.getString(26).toString());
                        bankProtectTypeEdtTxt.setText(cur.getString(27).toString());
                        approachTypEdtTxt.setText(cur.getString(28).toString());
                        floorProtctnEdtTxt.setText(cur.getString(29).toString());
                        floorProtectnTypEdtTxt.setText(cur.getString(30).toString());
                        typeSubStructureEdtTxt.setText(cur.getString(31).toString());//31


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
