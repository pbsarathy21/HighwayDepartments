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

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventaryFormThreeFrgmnt extends Fragment implements AdapterView.OnItemSelectedListener {

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

    private void presetValues()
    {
        dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

        leftFootpathEdtTxt.setText(dataItem.getLEFTFOOTPATH());
        rightFootPathEdtTxt.setText(dataItem.getRIGHTFOOTPATH());
        slapThiknsEdtTxt.setText(dataItem.getSLABTHICKNESS());


    }

    public InventaryFormThreeFrgmnt() {
        // Required empty public constructor
    }

    Button nextBttn,previousBttn;
    Context mContext;
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response="";
    EditText leftFootpathEdtTxt,rightFootPathEdtTxt,structureTypEdtTxt,slapThiknsEdtTxt,bearingTypEdtTxt,parapetHandRailEdtTxt,wearingCoatEdtTxt,pierFoundatnEdtTxt;
    String leftFootpath,rightFootPath,structureTyp,slapThikns,bearingTyp,parapetHandRail,wearingCoat,pierFoundatn;
    MyDataBaseHandler dbase;

    Spinner superSpinner, bearingSpinner, parapetSpinner, wearingSpinner, pierFoundationSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_inventary_form_three_frgmnt, container, false);

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

        leftFootpathEdtTxt=(EditText)root.findViewById(R.id.left_foot_path_edt);
        rightFootPathEdtTxt=(EditText)root.findViewById(R.id.right_foot_path_edt);
       // structureTypEdtTxt=(EditText)root.findViewById(R.id.super_structure_typ_edt);
        slapThiknsEdtTxt=(EditText)root.findViewById(R.id.slab_thickness_edt);
     //   bearingTypEdtTxt=(EditText)root.findViewById(R.id.bearing_typ_edt);
       // parapetHandRailEdtTxt=(EditText)root.findViewById(R.id.parapet_hand_rain_typ);
       // wearingCoatEdtTxt=(EditText)root.findViewById(R.id.wearing_coat_edttxt);
      //  pierFoundatnEdtTxt=(EditText)root.findViewById(R.id.pier_foundtn_edttxt);

        superSpinner=(Spinner) root.findViewById(R.id.super_spinner);
        bearingSpinner=(Spinner) root.findViewById(R.id.bearing_spinner);
        parapetSpinner=(Spinner) root.findViewById(R.id.parapet_spinner);
        wearingSpinner=(Spinner) root.findViewById(R.id.wearing_spinner);
        pierFoundationSpinner=(Spinner) root.findViewById(R.id.pier_foundation_spinner);

        superSpinner.setOnItemSelectedListener(this);
        bearingSpinner.setOnItemSelectedListener(this);
        parapetSpinner.setOnItemSelectedListener(this);
        wearingSpinner.setOnItemSelectedListener(this);
        pierFoundationSpinner.setOnItemSelectedListener(this);

        loadSpinner();

        dbase=new MyDataBaseHandler(getActivity());

        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormTwoFrgmnt inventaryFormTwoFrgmnt = new InventaryFormTwoFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormTwoFrgmnt);

                fragmentTransaction.commit();
            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDetails();

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormFourFrgmnt inventaryFormFourFrgmnt = new InventaryFormFourFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormFourFrgmnt);

                fragmentTransaction.commit();
            }
        });

        //By partha

        /*previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new asyncToSendDetails().execute();


                HomeActivity._mViewPager.setCurrentItem(2);
            }
        });
        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    leftFootpath=leftFootpathEdtTxt.getText().toString();
                    rightFootPath=rightFootPathEdtTxt.getText().toString();
                    structureTyp=structureTypEdtTxt.getText().toString();
                    slapThikns=slapThiknsEdtTxt.getText().toString();
                    bearingTyp=bearingTypEdtTxt.getText().toString();
                    parapetHandRail=parapetHandRailEdtTxt.getText().toString();
                    wearingCoat=wearingCoatEdtTxt.getText().toString();
                    pierFoundatn=pierFoundatnEdtTxt.getText().toString();

                    new asyncToSendDetails().execute();

                    HomeActivity._mViewPager.setCurrentItem(4);
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
        new asyncToGetDetails().execute();

        return root;

    }

    private void loadSpinner() {

        ArrayList<String> superList = new ArrayList<>();

        superList.add("Select");
        superList.add("Concrete");
        superList.add("Arch");
        superList.add("Steel");

        /*superSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, superList));*/

        final ArrayAdapter<String> superList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, superList);
        superList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        superSpinner.setAdapter(superList_adapter);

        int position = Integer.parseInt(dataItem.getSUPERSTRUCTURETYPE());
        superSpinner.setSelection(position);

        ArrayList<String> bearingList = new ArrayList<>();

        bearingList.add("Select");
        bearingList.add("Metallic");
        bearingList.add("Elastomeric");
        bearingList.add("Bed Block");

       /* bearingSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, bearingList));*/
        final ArrayAdapter<String> bearingList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, bearingList);
        bearingList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bearingSpinner.setAdapter(bearingList_adapter);

        int position1 = Integer.parseInt(dataItem.getBEARINGTYPE());
        bearingSpinner.setSelection(position1);

        ArrayList<String> parapetList = new ArrayList<>();

        parapetList.add("Select");
        parapetList.add("Concrete");
        parapetList.add("Steel");
        parapetList.add("Others");

        /*parapetSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, parapetList));*/

        final ArrayAdapter<String> parapetList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, parapetList);
        parapetList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parapetSpinner.setAdapter(parapetList_adapter);

        int position2 = Integer.parseInt(dataItem.getPARAPETHANDRAILTYPE());
        parapetSpinner.setSelection(position2);

        ArrayList<String> wearingList = new ArrayList<>();

        wearingList.add("Select");
        wearingList.add("Flexible");
        wearingList.add("Rigid");

        /*wearingSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, wearingList));*/

        final ArrayAdapter<String> wearingList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, wearingList);
        wearingList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wearingSpinner.setAdapter(wearingList_adapter);

        int position3 = Integer.parseInt(dataItem.getWEARINGCOATTYPE());
        wearingSpinner.setSelection(position3);


        ArrayList<String> pierFoundationList = new ArrayList<>();

        pierFoundationList.add("Select");
        pierFoundationList.add("well");
        pierFoundationList.add("pile");
        pierFoundationList.add("Open");
        pierFoundationList.add("Raft");

        /*pierFoundationSpinner.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, pierFoundationList));*/

        final ArrayAdapter<String> pierFoundationList_adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, pierFoundationList);
        pierFoundationList_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pierFoundationSpinner.setAdapter(pierFoundationList_adapter);

        int position4 = Integer.parseInt(dataItem.getPIERFOUNDATION());
        pierFoundationSpinner.setSelection(position4);
    }

    private void saveDetails() {

        leftFootpath=leftFootpathEdtTxt.getText().toString();
        rightFootPath=rightFootPathEdtTxt.getText().toString();
       // structureTyp=structureTypEdtTxt.getText().toString();
        slapThikns=slapThiknsEdtTxt.getText().toString();
      //  bearingTyp=bearingTypEdtTxt.getText().toString();
     //   parapetHandRail=parapetHandRailEdtTxt.getText().toString();
      //  wearingCoat=wearingCoatEdtTxt.getText().toString();
      //  pierFoundatn=pierFoundatnEdtTxt.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("left_footpath",leftFootpath );
        editor.putString("right_footpath",rightFootPath );

        editor.putString("slap_thickness",slapThikns );





        editor.apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = adapterView.getId();

        if (id == R.id.super_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("structure_type",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.bearing_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("bearing_type",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.parapet_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("parapet_handrail",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.wearing_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("wearing_coat",spinnerString );
            editor.apply();
            return;
        }

        if (id == R.id.pier_foundation_spinner)
        {
            String spinnerString = adapterView.getItemAtPosition(i).toString();
            editor.putString("pier_foundation",spinnerString );
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
                //id from 17
                inventaryCon.put(dbase.LEFT_FOOT_PATH_I,leftFootpath);
                inventaryCon.put(dbase.RIGHT_FOOT_PATH_I,rightFootPath);
                inventaryCon.put(dbase.SUPER_STRUCTURE_TYP_I,structureTyp);
                inventaryCon.put(dbase.SLAB_THIKNS_I,slapThikns);
                inventaryCon.put(dbase.BEARING_TYPE_I,bearingTyp);
                inventaryCon.put(dbase.PARAPET_I,parapetHandRail);
                inventaryCon.put(dbase.WEARING_COAT_TYP_I,wearingCoat);
                inventaryCon.put(dbase.PIER_FOUNDATN_I,pierFoundatn); //24


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

                        //id from 17
                        leftFootpathEdtTxt.setText(cur.getString(17).toString());
                        rightFootPathEdtTxt.setText(cur.getString(18).toString());
                        structureTypEdtTxt.setText(cur.getString(19).toString());
                        slapThiknsEdtTxt.setText(cur.getString(20).toString());
                        bearingTypEdtTxt.setText(cur.getString(21).toString());
                        parapetHandRailEdtTxt.setText(cur.getString(22).toString());
                        wearingCoatEdtTxt.setText(cur.getString(23).toString());
                        pierFoundatnEdtTxt.setText(cur.getString(24).toString()); //24


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
