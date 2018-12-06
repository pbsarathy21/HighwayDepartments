package com.spiderindia.departmentsofhighway.Fragmentss;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;
import com.spiderindia.departmentsofhighway.Utils.NetUtils;
import com.spiderindia.departmentsofhighway.Utils.WarningDialog;
import com.spiderindia.departmentsofhighway.Utils.WarningDialogForValidation;
import com.spiderindia.departmentsofhighway.YearPicker.YearPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBridgeFragment extends Fragment {

    DataItem dataItem;

    public AddBridgeFragment() {
        // Required empty public constructor
    }

    Spinner circleSpinner;
    String[] circleSpinnerArr;

    Spinner divisionSpinner;
    String[] divisionSpinnerArr;

    Spinner subDivisionSpinner;
    String[] subDivisionSpinnerArr;

    Spinner roadSpinner;
    String[] roadSpinnerArr;

    Spinner linkIdSpinner;
    String[] linkIdSpinnerArr;

    HashMap<Integer,String> circleSpinnerMap;
    HashMap<Integer,String> divisionSpinnerMap;
    HashMap<Integer,String> subDivisionSpinnerMap;
    HashMap<Integer,String> roadSpinnerMap;
    HashMap<Integer,String> linkIsSpinnerMap;

    Button nextBttn,chooseDocBttn;

    EditText descriptionEdtTxt,locationEdtTxt,startChainageEdtTxt,brdgNoEdtTxt,brdgEdtTxt,yrOfConstrEdtTxt,construcCostEdtTxt,condtnSurvyDateEdtTxt, bridge_key_id;

    String choosenDate,choosenTime,response="", photoPath, filePath;
    Context mContext;
    LinearLayout progress_layout, key_id;
    ProgressBar loading_process;
    YearPickerDialog yearPickerDialog;

    private static final int REQUEST_FOR_PERMISSION = 1;
    ImageView firstImage,secondImag;
    Intent intent;
    private static final int CAMERA_PHOTO = 111;
    private static final int CAMERA_SECOND_PHOTO = 112;
    private Uri imageToUploadUri;
    private Uri secondImageToUploadUri;
    private byte[] imageArray = null,secondImageArray = null;
    String selectedImagePath="",imagePath="",secondSelectedImagePath="",secondImagePath="", encodedFirstImagePath="",encodedSecondImagePath="",encodedDocumentPath="";
    public static int RESULT_LOAD_IMAGE = 1;
    public static int RESULT_SECOND_LOAD_IMAGE = 2;
    Dialog dialog;
    Button chooseFileBttn,chooseSecondFileBttn;
    MyDataBaseHandler dbase;
    String circleId="",divisionId,subDivisionId,roadId,linkId,firstlinkId="",description,location,startChainage,brdgeNo,brdge,yrOfContrctn,contrctnCost,contrctnDate,documentPath,finalDocumentName="No File Choosen";
    private static final int PICKFILE_RESULT_CODE = 5;

    TextView documentNameTxt;
    ArrayAdapter<String> adapter_division,adapter_subDivision,adapter_road,adapter_linkId;

    String responseCircleHolder="",userId,authenticationCode,responseDivision="",responseSubDivision="",responseRoad="",responseLinkId="",firstTimeAfterLogin="";
    SharedPreferences spf;

    @Override
    public void onStart() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            key_id.setVisibility(View.VISIBLE);
            presetValues();

        } else {
            key_id.setVisibility(View.GONE);
        }

        super.onStart();
    }

    private void presetValues() {

        dataItem = (DataItem) getActivity().getIntent().getSerializableExtra("dataItem");

        descriptionEdtTxt.setText(dataItem.getBRIDGEDESC());
        locationEdtTxt.setText(dataItem.getLOCATION());
        startChainageEdtTxt.setText(dataItem.getCHAINAGE());
        brdgNoEdtTxt.setText(dataItem.getBRIDGENUMBER());
        brdgEdtTxt.setText(dataItem.getBRIDGENAME());
        yrOfConstrEdtTxt.setText(dataItem.getCONSTRUCTIONYEAR());
        construcCostEdtTxt.setText(dataItem.getCONSTRUCTIONCOST());
        condtnSurvyDateEdtTxt.setText(dataItem.getCONDITIONSURVEYDATE());
        bridge_key_id.setText(dataItem.getBRIDGEKEYID());

        bridge_key_id.setEnabled(false);


    }

    @Override
    public void onResume() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            key_id.setVisibility(View.VISIBLE);

        } else {
            key_id.setVisibility(View.GONE);
        }
        super.onResume();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_add_bridge, container, false);
        mContext = container.getContext();

        loading_process= (ProgressBar)root.findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout= (LinearLayout)root.findViewById(R.id.progress_loading_layout);

        firstImage=(ImageView)root.findViewById(R.id.profile_img);
        secondImag=(ImageView)root.findViewById(R.id.second_img);
        chooseFileBttn=(Button)root.findViewById(R.id.choose_File_bttn_doc);
        chooseSecondFileBttn=(Button)root.findViewById(R.id.choose_second_photo);


        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dbase = new MyDataBaseHandler(getActivity());

        spf = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = spf.getString("userId", "");
        authenticationCode = spf.getString("authenticationToken", "");
        firstTimeAfterLogin = spf.getString("firstTimeAfterLogin", "");

        nextBttn=(Button)root.findViewById(R.id.add_brdg_next_bttn);

        circleSpinner=(Spinner)root.findViewById(R.id.circle_spiner);
        divisionSpinner=(Spinner)root.findViewById(R.id.division_spiner);
        subDivisionSpinner=(Spinner)root.findViewById(R.id.sub_division_spiner);
        roadSpinner=(Spinner)root.findViewById(R.id.road_spiner);
        linkIdSpinner=(Spinner)root.findViewById(R.id.link_id_spnr);

        descriptionEdtTxt=(EditText)root.findViewById(R.id.descrp_edtTxt);
        locationEdtTxt=(EditText)root.findViewById(R.id.location_edtTxt);
        startChainageEdtTxt=(EditText)root.findViewById(R.id.chainage_edtTxt);
        brdgNoEdtTxt=(EditText)root.findViewById(R.id.bridgeNo_edtTxt);
        bridge_key_id=(EditText) root.findViewById(R.id.bridge_key_id);

        brdgEdtTxt=(EditText)root.findViewById(R.id.bridge_edtTxt);
        yrOfConstrEdtTxt=(EditText)root.findViewById(R.id.yr_of_constrctn_edtTxt);
        construcCostEdtTxt=(EditText)root.findViewById(R.id.constrctn_cost_edtTxt);
        condtnSurvyDateEdtTxt=(EditText)root.findViewById(R.id.constrctn_survey_date_edtTxt);
        chooseDocBttn=(Button) root.findViewById(R.id.choose_doc_bttn);
        documentNameTxt=(TextView) root.findViewById(R.id.document_nam_Txt);



        key_id = (LinearLayout) root.findViewById(R.id.key_id);



        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        new  asyncToGetDetails().execute();

        condtnSurvyDateEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) v;
                editText.setOnFocusChangeListener(v.getOnFocusChangeListener());
                editText.setCursorVisible(false);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DecimalFormat formatter = new DecimalFormat("00");

                                choosenDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                condtnSurvyDateEdtTxt.setText(choosenDate);

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        nextBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDeatails();

                SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

               String photo = preferences.getString("photoPath", null);
            //   String file = preferences.getString("filePath", null);

                if (TextUtils.isEmpty(photo))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Image is Mandatory");
                    builder.setMessage("Please load the image properly");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create();
                    builder.show();
                    return;
                }

                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                InventaryFormOneFrgmnt inventaryFormOneFrgmnt = new InventaryFormOneFrgmnt();

                fragmentTransaction.replace(R.id.container, inventaryFormOneFrgmnt);

                fragmentTransaction.commit();
            }
        });



        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear,mMonth,mDay);

        yearPickerDialog = new YearPickerDialog(getActivity(), calendar, new YearPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

                yrOfConstrEdtTxt.setText(dateFormat.format(calendar.getTime()));

                //choosenYear=dateFormat.format(calendar.getTime())+"";

            }
        });


        /*year picker end */

        chooseFileBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionRequest("FirstImage");

            }
        });

        chooseSecondFileBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionRequest("SecondImage");

            }
        });

        chooseDocBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                Intent i = Intent.createChooser(intent, "File");
                startActivityForResult(i, PICKFILE_RESULT_CODE);

            }
        });

        SpinnerInteractionListener listener = new SpinnerInteractionListener();
        circleSpinner.setOnTouchListener(listener);
        circleSpinner.setOnItemSelectedListener(listener);

        SpinnerListenerForDivision divListener = new SpinnerListenerForDivision();
        divisionSpinner.setOnTouchListener(divListener);
        divisionSpinner.setOnItemSelectedListener(divListener);

        SpinnerListenerForSubDivision subDivListener = new SpinnerListenerForSubDivision();
        subDivisionSpinner.setOnTouchListener(subDivListener);
        subDivisionSpinner.setOnItemSelectedListener(subDivListener);

        SpinnerListenerForRoad roadListener = new SpinnerListenerForRoad();
        roadSpinner.setOnTouchListener(roadListener);
        roadSpinner.setOnItemSelectedListener(roadListener);

/*
        circleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                for(int i=0;i<5;i++) {
                    String id1="";
                    if (i == 0) {
                        id1 = 0 + "";
                        divisionSpinnerArr[0] = "None";
                        subDivisionSpinnerArr[0] = "None";
                        roadSpinnerArr[0] = "None";
                        linkIdSpinnerArr[0] = "None";
                    } else {
                        id1 = i + "";
                        String name=circleSpinnerArr[position];
                        divisionSpinnerArr[(i)] = name + i;
                        subDivisionSpinnerArr[(i)] = name + i;
                        roadSpinnerArr[(i)] = name+ i;
                        linkIdSpinnerArr[(i)] = name + i;
                    }

                    circleSpinnerMap.put(i, id1);
                    divisionSpinnerMap.put(i, id1);
                    subDivisionSpinnerMap.put(i, id1);
                    roadSpinnerMap.put(i, id1);
                    linkIsSpinnerMap.put(i, id1);
                }

                adapter_division.clear();
                adapter_division.addAll(divisionSpinnerArr);
                adapter_division.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
*/

        return root;
    }

    private void saveDeatails() {

        String bridge_id = bridge_key_id.getText().toString();

        startChainage=startChainageEdtTxt.getText().toString();
        location=locationEdtTxt.getText().toString();
        description=descriptionEdtTxt.getText().toString();
        brdgeNo=brdgNoEdtTxt.getText().toString();
        brdge=brdgEdtTxt.getText().toString();
        yrOfContrctn=yrOfConstrEdtTxt.getText().toString();
        contrctnCost=construcCostEdtTxt.getText().toString();
        contrctnDate=condtnSurvyDateEdtTxt.getText().toString();

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("bridge_key_id",bridge_id );

        editor.putString("start_chainage",startChainage );
        editor.putString("location",location );
        editor.putString("description",description );
        editor.putString("bridge_no",brdgeNo );
        editor.putString("bridge",brdge );
        editor.putString("Year_cons",yrOfContrctn );
        editor.putString("Cons_cost",contrctnCost );
        editor.putString("Cons_date",contrctnDate );

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

                getUserDetailsFromServer();
                //getDivisionDetails("1");
                Cursor cur=dbase.getAllDetails(dbase.TABLE_NAME_BRIDGE,dbase.BRIDGE_ID);
                Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cur));

                if (cur.moveToFirst())
                {
                    do {

                        circleId=cur.getString(1).toString();
                        divisionId=cur.getString(2).toString();
                        subDivisionId=cur.getString(3).toString();
                        roadId=cur.getString(4).toString();
                        linkId=cur.getString(5).toString();

                        firstlinkId=linkId;

                        descriptionEdtTxt.setText(cur.getString(6).toString());
                        String location=cur.getString(7).toString();
                        locationEdtTxt.setText(location);
                        startChainageEdtTxt.setText(cur.getString(8).toString());
                        brdgEdtTxt.setText(cur.getString(9).toString());
                        brdgNoEdtTxt.setText(cur.getString(10).toString());
                        yrOfConstrEdtTxt.setText(cur.getString(11).toString());
                        construcCostEdtTxt.setText(cur.getString(12).toString());
                        condtnSurvyDateEdtTxt.setText(cur.getString(13).toString());
                        documentPath=cur.getString(14).toString();
                        finalDocumentName=cur.getString(15).toString();
                        imagePath=cur.getString(16).toString();
                        secondImagePath=cur.getString(17).toString();

                        documentNameTxt.setText(finalDocumentName);


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

                final ArrayAdapter<String> adapter_circle = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, circleSpinnerArr);
                adapter_circle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                circleSpinner.setAdapter(adapter_circle);


                adapter_division = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, divisionSpinnerArr);
                adapter_division.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                divisionSpinner.setAdapter(adapter_division);

                adapter_subDivision = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, subDivisionSpinnerArr);
                adapter_subDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subDivisionSpinner.setAdapter(adapter_subDivision);

                 adapter_road = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, roadSpinnerArr);
                adapter_road.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roadSpinner.setAdapter(adapter_road);


                adapter_linkId = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, linkIdSpinnerArr);
                adapter_linkId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                linkIdSpinner.setAdapter(adapter_linkId);

                if(firstTimeAfterLogin.equalsIgnoreCase("1"))
                {
                  /*  circleSpinner.setSelection(Integer.parseInt(circleId));
                    divisionSpinner.setSelection(Integer.parseInt(divisionId));
                    subDivisionSpinner.setSelection(Integer.parseInt(subDivisionId));
                    roadSpinner.setSelection(Integer.parseInt(roadId));
                    linkIdSpinner.setSelection(Integer.parseInt(linkId));*/

                }
                else
                {
                    if(!(circleId.equalsIgnoreCase("0")))
                    {
                        for(int i=0;i<circleSpinnerArr.length;i++)
                        {
                            if(circleSpinnerMap.get(i).equalsIgnoreCase(divisionId))
                            {
                                circleSpinner.setSelection(i);
                            }
                        }


                        updateDivisions(circleId,"1");
                    }
                    if(!(divisionId.equalsIgnoreCase("0")))
                    {
                        updateSubDivision(divisionId,"1");
                    }
                    if(!(subDivisionId.equalsIgnoreCase("0")))
                    {
                        updateRoad(subDivisionId,"1");
                    }
                    if(!(roadId.equalsIgnoreCase("0")))
                    {
                        updateLink(roadId,"1");
                    }

                }

                if(!(imagePath.equalsIgnoreCase("")))
                {

                    selectedImagePath=imagePath;
                    setImageOnImageView(imagePath,"firstImage");
                }

                if(!(secondImagePath.equalsIgnoreCase("")))
                {

                    secondSelectedImagePath=secondImagePath;
                    setImageOnImageView(secondSelectedImagePath,"secondImage");
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


    class asyncToSendDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {

                if(!(secondImagePath.equalsIgnoreCase("")))
                {
                    encodedFirstImagePath=getEncodedImage(selectedImagePath);
                }
                else if(!(secondSelectedImagePath.equalsIgnoreCase("")))
                {
                    encodedSecondImagePath=getEncodedImage(secondSelectedImagePath);
                }
                else if(!(documentPath.equalsIgnoreCase("")))
                {
                    encodedDocumentPath=getEncodedImage(documentPath);
                }

                ContentValues con=new ContentValues();
                con.put(dbase.CIRCLE,circleId);
                con.put(dbase.DIVISION,divisionId);
                con.put(dbase.SUBDIVISION,subDivisionId);
                con.put(dbase.ROAD,roadId);
                con.put(dbase.LINKID,linkId);
                con.put(dbase.DESCRIPTION,description);
                con.put(dbase.LOCATION,location);
                con.put(dbase.STARTCHAINAGE,startChainage);
                con.put(dbase.BRIDGE,brdge);
                con.put(dbase.BRIDGENO,brdgeNo);
                con.put(dbase.YR_OF_CONSTRU,yrOfContrctn);
                con.put(dbase.CONSTRU_COST,contrctnCost);
                con.put(dbase.CONSTRU_DATE,contrctnDate);
                con.put(dbase.DOCUMENT_PATH,documentPath);
                con.put(dbase.DOCUMENT_NAME,finalDocumentName);
                con.put(dbase.IMAGE_PATH,selectedImagePath);
                con.put(dbase.SECOND_IMAGE_PATH,secondSelectedImagePath);
                con.put(dbase.ENCODED_IMAGE_PATH,encodedFirstImagePath);
                con.put(dbase.ENCODED_SECOND_IMAGE_PATH,encodedSecondImagePath);

                printContentValues(con);

                boolean updateStatus=dbase.UpdateDetails(con,dbase.TABLE_NAME_BRIDGE,dbase.BRIDGE_ID);

                //dbase.getGivenValue();


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
    class asyncToInsertDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

            dbase.emptyTable(dbase.TABLE_NAME_BRIDGE);
            dbase.emptyTable(dbase.TABLE_NAME_INVENTARY);
            dbase.emptyTable(dbase.TABLE_NAME_CONDITION);
            dbase.emptyTable(dbase.TABLE_NAME_MAINTAINANCE);
            //dbase.emptyTable(dbase.TABLE_NAME_CIRCLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {
                dbase = new MyDataBaseHandler(getActivity());

                dbase.insertAllTables();

                if(!(secondImagePath.equalsIgnoreCase("")))
                {
                    encodedFirstImagePath=getEncodedImage(selectedImagePath);
                }
                else if(!(secondSelectedImagePath.equalsIgnoreCase("")))
                {
                    encodedSecondImagePath=getEncodedImage(secondSelectedImagePath);
                }
                else if(!(documentPath.equalsIgnoreCase("")))
                {
                    encodedDocumentPath=getEncodedImage(documentPath);
                }

                ContentValues con=new ContentValues();
                con.put(dbase.CIRCLE,circleId);
                con.put(dbase.DIVISION,divisionId);
                con.put(dbase.SUBDIVISION,subDivisionId);
                con.put(dbase.ROAD,roadId);
                con.put(dbase.LINKID,linkId);
                con.put(dbase.DESCRIPTION,description);
                con.put(dbase.LOCATION,location);
                con.put(dbase.STARTCHAINAGE,startChainage);
                con.put(dbase.BRIDGE,brdge);
                con.put(dbase.BRIDGENO,brdgeNo);
                con.put(dbase.YR_OF_CONSTRU,yrOfContrctn);
                con.put(dbase.CONSTRU_COST,contrctnCost);
                con.put(dbase.CONSTRU_DATE,contrctnDate);
                con.put(dbase.DOCUMENT_PATH,documentPath);
                con.put(dbase.DOCUMENT_NAME,finalDocumentName);
                con.put(dbase.IMAGE_PATH,selectedImagePath);
                con.put(dbase.SECOND_IMAGE_PATH,secondSelectedImagePath);
                con.put(dbase.ENCODED_IMAGE_PATH,encodedFirstImagePath);
                con.put(dbase.ENCODED_SECOND_IMAGE_PATH,encodedSecondImagePath);

               // printContentValues(con);
                boolean updateStatus=dbase.UpdateDetails(con,dbase.TABLE_NAME_BRIDGE,dbase.BRIDGE_ID);

                //dbase.getGivenValue();


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

                HomeActivity._mViewPager.setCurrentItem(1);


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

        Cursor cur=dbase.getAllCircleDetails(dbase.TABLE_NAME_CIRCLE);
        Log.v("Cursor Object Circle", DatabaseUtils.dumpCursorToString(cur) +" Count "+ cur.getCount());

        circleSpinnerArr = new String[cur.getCount()];
        circleSpinnerMap = new HashMap<Integer, String>();
        if (cur.moveToFirst())
        {
            do {

                String idNew=cur.getString(0).toString();
                String Name=cur.getString(1).toString();


                //circleSpinnerArr[(cur.getPosition())]=cur.getString(1).toString();
                circleSpinnerArr[cur.getPosition()]=Name;
                circleSpinnerMap.put(cur.getPosition(),idNew);


            }while (cur.moveToNext());
        }


        //circleSpinnerArr = new String[5];
        divisionSpinnerArr = new String[5];
        subDivisionSpinnerArr = new String[5];
        roadSpinnerArr = new String[5];
        linkIdSpinnerArr = new String[5];

        //circleSpinnerMap = new HashMap<Integer, String>();
        divisionSpinnerMap = new HashMap<Integer, String>();
        subDivisionSpinnerMap = new HashMap<Integer, String>();
        roadSpinnerMap = new HashMap<Integer, String>();
        linkIsSpinnerMap = new HashMap<Integer, String>();




        for(int i=0;i<5;i++)
        {
            String id;
            if(i==0)
            {
                id=0+"";
               // circleSpinnerArr[0]="None";
                divisionSpinnerArr[0]="None";
                subDivisionSpinnerArr[0]="None";
                roadSpinnerArr[0]="None";
                linkIdSpinnerArr[0]="None";
            }
            else
            {
                id=i+"";
                //circleSpinnerArr[(i)]="India "+i;
                divisionSpinnerArr[(i)]="Division "+i;
                subDivisionSpinnerArr[(i)]="Sub Division "+i;
                roadSpinnerArr[(i)]="Road "+i;
                linkIdSpinnerArr[(i)]="Link Id "+i;
            }

            //circleSpinnerMap.put(i,id);
            divisionSpinnerMap.put(i,id);
            subDivisionSpinnerMap.put(i,id);
            roadSpinnerMap.put(i,id);
            linkIsSpinnerMap.put(i,id);

        }

        divisionSpinner.setEnabled(false);
        divisionSpinner.setClickable(false);

        subDivisionSpinner.setEnabled(false);
        subDivisionSpinner.setClickable(false);

        roadSpinner.setEnabled(false);
        roadSpinner.setClickable(false);

        linkIdSpinner.setEnabled(false);
        linkIdSpinner.setClickable(false);



    }

    private void sendUserDetailsServer() {

        /*dbase = new MyDataBaseHandler(getActivity());
        dbase.getGivenValue();
        dbase.Update("3434");
        dbase.getGivenValue();*/

    }


    private void alertBoxToChooseImage(String imageIdentity) {

        dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
        dialog.setContentView(R.layout.popup_to_choose_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        ImageView closeImg = (ImageView) dialog.findViewById(R.id.close_icon_);
        LinearLayout cameraLL=(LinearLayout)dialog.findViewById(R.id.camera_layout);
        LinearLayout galleryLL=(LinearLayout)dialog.findViewById(R.id.gallery_ll);

        final String image=imageIdentity;
        cameraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureCameraImage(image);
            }
        });
        galleryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery(image);
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

    }


    public void PermissionRequest(String imageIdentity)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_FOR_PERMISSION);
        }
        else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},REQUEST_FOR_PERMISSION);
        }
        else
        {
            alertBoxToChooseImage(imageIdentity);

        }

    }

    public void PermissionRequestForSecondImage(String imageIdentity)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_FOR_PERMISSION);
        }
        else
        {
            if(imageIdentity.equalsIgnoreCase("FirstImage"))
            {
                alertBoxToChooseImage(imageIdentity);
            }
            else
            {

            }


        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FOR_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {

                        alertBoxToChooseImage("FirstImage");
                    }
                    catch (Exception e)
                    {

                    }
                }
                else
                {

                }
                return;
            }
        }
    }

    /*Image from camera start*/

    private void captureCameraImage(String imageIdentity) {

        if(imageIdentity.equalsIgnoreCase("FirstImage"))
        {
            Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
            chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            imageToUploadUri = Uri.fromFile(f);
            startActivityForResult(chooserIntent, CAMERA_PHOTO);
        }
        else
        {
            Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
            chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            secondImageToUploadUri = Uri.fromFile(f);
            startActivityForResult(chooserIntent, CAMERA_SECOND_PHOTO);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == CAMERA_PHOTO && resultCode == RESULT_OK) {
                if(imageToUploadUri != null){
                    Uri selectedImage = imageToUploadUri;

                    photoPath = getRealpathfromUri(selectedImage);
                    Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    selectedImagePath=imageToUploadUri.getPath();


                    Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        firstImage.setImageBitmap(reducedSizeBitmap);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }
            else if (requestCode == CAMERA_SECOND_PHOTO && resultCode == RESULT_OK) {
                if(secondImageToUploadUri != null){
                    Uri selectedImage = secondImageToUploadUri;

                    photoPath = getRealpathfromUri(selectedImage);
                    Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    secondSelectedImagePath=secondImageToUploadUri.getPath();


                    Bitmap reducedSizeBitmap = getBitmap(secondImageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        secondImag.setImageBitmap(reducedSizeBitmap);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }

            else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();

                photoPath = getRealpathfromUri(selectedImage);
                Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                imageArray = imageToByteArray(new File(picturePath));
                selectedImagePath=picturePath;
                setImagePicture(picturePath,"first_img");
            }
            else if (requestCode == RESULT_SECOND_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                photoPath = getRealpathfromUri(selectedImage);
                Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                secondImageArray = imageToByteArray(new File(picturePath));
                secondSelectedImagePath=picturePath;
                setImagePicture(picturePath,"second_img");
            }
            else if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && null != data) {
                documentPath = data.getData().getPath();
                Uri path = data.getData();

                filePath = path.getPath();

                Toast.makeText(getActivity(),""+filePath,Toast.LENGTH_LONG).show();

                try {
                    String myString = documentPath;
                    finalDocumentName = myString.substring(myString.lastIndexOf("/")+1, myString.indexOf("."));

                    documentNameTxt.setText(finalDocumentName);



                }
                catch (Exception e)
                {

                }

            }
            else {

            }

        }
        catch (Exception e)
        {

        }

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("photoPath",photoPath);
        editor.putString("filePath",photoPath);

        editor.apply();
    }

    private String getRealpathfromUri(Uri selectedImage) {

        String projection = (MediaStore.Images.Media.DATA);
        CursorLoader cursorLoader = new CursorLoader(getActivity(),selectedImage, new String[]{projection}, null,null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_indx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_indx);
        cursor.close();
        return result;
    }



    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getActivity().getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getActivity().getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    /*Image from camera end*/

    /*Image from gallery start*/

    public void openGallery(String imageIdentity) {

        if(imageIdentity.equalsIgnoreCase("FirstImage"))
        {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
        else
        {
            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_SECOND_LOAD_IMAGE);
        }

    }


    private byte[] imageToByteArray(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            // create FileInputStream which obtains input bytes from a file in a
            // file system
            // FileInputStream is meant for reading streams of raw bytes such as
            // image data. For reading streams of characters, consider using
            // FileReader.

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                // Writes to this byte array output stream
                bos.write(buf, 0, readNum);
            }
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void setImagePicture(String photoPath, String imgIdentity) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

        if(imgIdentity.equalsIgnoreCase("first_img"))
        {
            firstImage.setImageBitmap(bitmap);
        }
        else
        {
            secondImag.setImageBitmap(bitmap);
        }



        dialog.dismiss();
    }

  private void setImageOnImageView(String photoPath, String imageIdenti) {

      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

        if(imageIdenti.equalsIgnoreCase("firstImage"))
        {
            firstImage.setImageBitmap(bitmap);
        }
        else
        {
            secondImag.setImageBitmap(bitmap);
        }

   }


    /*Image from gallery end*/
    public void printContentValues(ContentValues vals)
    {
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        Log.d("DatabaseSync", "ContentValue Length :: " +vals.size());

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
        }
    }

    public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (userSelect) {
                // Your selection handling code here
                userSelect = false;

                circleId = circleSpinnerMap.get(circleSpinner.getSelectedItemPosition());

                if(!(circleId.equalsIgnoreCase("0")))
                {
                    updateDivisions(circleId,"0");
                }
                else
                {
                    divisionSpinner.setEnabled(false);
                    divisionSpinner.setClickable(false);
                }


            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }
    public class SpinnerListenerForDivision implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (userSelect) {
                // Your selection handling code here
                userSelect = false;

                divisionId = divisionSpinnerMap.get(divisionSpinner.getSelectedItemPosition());

                updateSubDivision(divisionId,"0");



            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    public class SpinnerListenerForSubDivision implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (userSelect) {
                // Your selection handling code here
                userSelect = false;
                subDivisionId = subDivisionSpinnerMap.get(subDivisionSpinner.getSelectedItemPosition());

                updateRoad(subDivisionId,"0");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    public class SpinnerListenerForRoad implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (userSelect) {
                // Your selection handling code here
                userSelect = false;
                roadId = roadSpinnerMap.get(roadSpinner.getSelectedItemPosition());

                updateLink(roadId,"0");

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    void updateDivisions(String circleId,String updateSpin)
    {
        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getDivisionDetailsFromServer(userId,authenticationCode,circleId);
        try {
            responseCircleHolder=json.getString("success");
            if(responseCircleHolder.equalsIgnoreCase("TRUE"))
            {

                JSONArray jsonArray = json.getJSONArray("division");


                if(jsonArray.length()>0)
                {
                    dbase.emptyTable(dbase.TABLE_NAME_DIVISION);

                    divisionSpinner.setEnabled(true);
                    divisionSpinner.setClickable(true);

                    ContentValues DivisionCon=new ContentValues();
                    DivisionCon.put(dbase.DIVISION_ID,"0");
                    DivisionCon.put(dbase.DIVISION_NAME,"None");
                    dbase.InsertDetails(DivisionCon,dbase.TABLE_NAME_DIVISION);

                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject json_user = jsonArray.getJSONObject(i);
                        String divisionId = json_user.getString("id");
                        String name = json_user.getString("name");

                        DivisionCon.put(dbase.DIVISION_ID,divisionId);
                        DivisionCon.put(dbase.DIVISION_NAME,name);

                        dbase.InsertDetails(DivisionCon,dbase.TABLE_NAME_DIVISION);

                    }

                    Cursor cur=dbase.getAllCircleDetails(dbase.TABLE_NAME_DIVISION);
                    divisionSpinnerArr = new String[cur.getCount()];
                    divisionSpinnerMap = new HashMap<Integer, String>();

                    if (cur.moveToFirst())
                    {
                        do {

                            String idNew=cur.getString(0).toString();
                            String Name=cur.getString(1).toString();


                            divisionSpinnerArr[cur.getPosition()]=Name;
                            divisionSpinnerMap.put(cur.getPosition(),idNew);


                        }while (cur.moveToNext());
                    }

                    adapter_division = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, divisionSpinnerArr);
                    adapter_division.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    divisionSpinner.setAdapter(adapter_division);
                    adapter_division.notifyDataSetChanged();

                    if((updateSpin.equalsIgnoreCase("1")))
                    {
                        for(int i=0;i<divisionSpinnerArr.length;i++)
                        {
                            if(divisionSpinnerMap.get(i).equalsIgnoreCase(divisionId))
                            {
                                divisionSpinner.setSelection(i);
                            }
                        }
                    }
                    else
                    {

                        subDivisionSpinner.setEnabled(false);
                        subDivisionSpinner.setClickable(false);

                        roadSpinner.setEnabled(false);
                        roadSpinner.setClickable(false);

                        linkIdSpinner.setEnabled(false);
                        linkIdSpinner.setClickable(false);

                        subDivisionId="0";
                        roadId="0";
                        linkId="0";


                    }


                }
                else
                {
                    new WarningDialogForValidation(mContext,"No Division for this Circle");

                    divisionSpinner.setEnabled(false);
                    divisionSpinner.setClickable(false);

                    subDivisionSpinner.setEnabled(false);
                    subDivisionSpinner.setClickable(false);

                    roadSpinner.setEnabled(false);
                    roadSpinner.setClickable(false);

                    linkIdSpinner.setEnabled(false);
                    linkIdSpinner.setClickable(false);

                    divisionId="0";
                    subDivisionId="0";
                    roadId="0";
                    linkId="0";



                }

            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }




    }

    void updateSubDivision(String division_Id,String updateSpin)
    {

        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getSubDivisionDetailsFromServer(userId,authenticationCode,division_Id);
        try {
            responseDivision=json.getString("success");
            if(responseDivision.equalsIgnoreCase("TRUE"))
            {
                ContentValues subdivisionCon=new ContentValues();
                JSONArray jsonArray = json.getJSONArray("sub_division");

                if(jsonArray.length()>0)
                {
                    dbase.emptyTable(dbase.TABLE_NAME_SUBDIVISION);

                    subDivisionSpinner.setEnabled(true);
                    subDivisionSpinner.setClickable(true);

                    subdivisionCon.put(dbase.SubDIVISION_ID,"0");
                    subdivisionCon.put(dbase.SubDIVISION_NAME,"None");
                    dbase.InsertDetails(subdivisionCon,dbase.TABLE_NAME_DIVISION);

                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject json_user = jsonArray.getJSONObject(i);
                        String divisionId = json_user.getString("id");
                        String name = json_user.getString("name");

                        subdivisionCon.put(dbase.SubDIVISION_ID,divisionId);
                        subdivisionCon.put(dbase.SubDIVISION_NAME,name);

                        dbase.InsertDetails(subdivisionCon,dbase.TABLE_NAME_DIVISION);

                    }

                    Cursor cur=dbase.getAllCircleDetails(dbase.TABLE_NAME_DIVISION);
                    subDivisionSpinnerArr = new String[cur.getCount()];
                    subDivisionSpinnerMap = new HashMap<Integer, String>();

                    if (cur.moveToFirst())
                    {
                        do {

                            String idNew=cur.getString(0).toString();
                            String Name=cur.getString(1).toString();

                            subDivisionSpinnerArr[cur.getPosition()]=Name;
                            subDivisionSpinnerMap.put(cur.getPosition(),idNew);


                        }while (cur.moveToNext());
                    }

                    adapter_subDivision = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, subDivisionSpinnerArr);
                    adapter_subDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subDivisionSpinner.setAdapter(adapter_subDivision);
                    adapter_subDivision.notifyDataSetChanged();


                    if((updateSpin.equalsIgnoreCase("1")))
                    {
                        for(int i=0;i<subDivisionSpinnerArr.length;i++)
                        {
                            if(subDivisionSpinnerMap.get(i).equalsIgnoreCase(subDivisionId))
                            {
                                subDivisionSpinner.setSelection(i);
                            }
                        }
                    }
                    else
                    {
                        roadSpinner.setEnabled(false);
                        roadSpinner.setClickable(false);

                        linkIdSpinner.setEnabled(false);
                        linkIdSpinner.setClickable(false);

                        roadId="0";
                        linkId="0";
                    }
                }
                else
                {
                    new WarningDialogForValidation(mContext,"No SubDivision for this Division");

                    subDivisionSpinner.setEnabled(false);
                    subDivisionSpinner.setClickable(false);

                    roadSpinner.setEnabled(false);
                    roadSpinner.setClickable(false);

                    linkIdSpinner.setEnabled(false);
                    linkIdSpinner.setClickable(false);

                    subDivisionId="0";
                    roadId="0";
                    linkId="0";


                }

            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void updateRoad(String subDivisionId,String updateSpin){

        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getRoadDetailsFromServer(userId,authenticationCode,subDivisionId);
        try {
            responseRoad=json.getString("success");
            if(responseRoad.equalsIgnoreCase("TRUE"))
            {
                ContentValues roadCon=new ContentValues();
                JSONArray jsonArray = json.getJSONArray("road");

                if(jsonArray.length()>0)
                {
                    dbase.emptyTable(dbase.TABLE_NAME_ROAD);

                    roadSpinner.setEnabled(true);
                    roadSpinner.setClickable(true);


                    roadCon.put(dbase.ROAD_ID,"0");
                    roadCon.put(dbase.ROAD_NAME,"None");
                    dbase.InsertDetails(roadCon,dbase.TABLE_NAME_ROAD);


                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject json_user = jsonArray.getJSONObject(i);
                        String divisionId = json_user.getString("id");
                        String name = json_user.getString("name");

                        roadCon.put(dbase.ROAD_ID,divisionId);
                        roadCon.put(dbase.ROAD_NAME,name);

                        dbase.InsertDetails(roadCon,dbase.TABLE_NAME_ROAD);

                    }

                    Cursor cur=dbase.getAllCircleDetails(dbase.TABLE_NAME_ROAD);
                    roadSpinnerArr = new String[cur.getCount()];
                    roadSpinnerMap = new HashMap<Integer, String>();

                    if (cur.moveToFirst())
                    {
                        do {

                            String idNew=cur.getString(0).toString();
                            String Name=cur.getString(1).toString();

                            roadSpinnerArr[cur.getPosition()]=Name;
                            roadSpinnerMap.put(cur.getPosition(),idNew);


                        }while (cur.moveToNext());
                    }

                    adapter_road = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, roadSpinnerArr);
                    adapter_road.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    roadSpinner.setAdapter(adapter_road);
                    adapter_road.notifyDataSetChanged();

                    if((updateSpin.equalsIgnoreCase("1")))
                    {
                        for(int i=0;i<roadSpinnerArr.length;i++)
                        {
                            if(roadSpinnerMap.get(i).equalsIgnoreCase(roadId))
                            {
                                roadSpinner.setSelection(i);
                            }
                        }
                    }
                    else
                    {
                        linkIdSpinner.setEnabled(false);
                        linkIdSpinner.setClickable(false);

                        linkId="0";
                    }
                }
                else
                {
                    new WarningDialogForValidation(mContext,"No Roads for this SubDivision");
                    roadSpinner.setEnabled(false);
                    roadSpinner.setClickable(false);

                    linkIdSpinner.setEnabled(false);
                    linkIdSpinner.setClickable(false);

                    roadId="0";
                    linkId="0";


                }

            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void updateLink(String road_id,String updateSpin)
    {

        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getLinkIdDetailsFromServer(userId,authenticationCode,road_id);
        try {
            responseLinkId=json.getString("success");
            if(responseLinkId.equalsIgnoreCase("TRUE"))
            {
                ContentValues linkCon=new ContentValues();
                JSONArray jsonArray = json.getJSONArray("link");

                if(jsonArray.length()>0)
                {

                    dbase.emptyTable(dbase.TABLE_NAME_LINKID);

                    linkIdSpinner.setEnabled(true);
                    linkIdSpinner.setClickable(true);

                    linkCon.put(dbase.LINK_ID,"0");
                    linkCon.put(dbase.LINK_NAME,"None");

                    dbase.InsertDetails(linkCon,dbase.TABLE_NAME_LINKID);

                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject json_user = jsonArray.getJSONObject(i);
                        String divisionId = json_user.getString("id");
                        String name = json_user.getString("link_id");

                        linkCon.put(dbase.LINK_ID,divisionId);
                        linkCon.put(dbase.LINK_NAME,name);

                        dbase.InsertDetails(linkCon,dbase.TABLE_NAME_LINKID);

                    }

                    Cursor cur=dbase.getAllCircleDetails(dbase.TABLE_NAME_LINKID);
                    linkIdSpinnerArr = new String[cur.getCount()];
                    linkIsSpinnerMap = new HashMap<Integer, String>();

                    if (cur.moveToFirst())
                    {
                        do {

                            String idNew=cur.getString(0).toString();
                            String Name=cur.getString(1).toString();

                            linkIdSpinnerArr[cur.getPosition()]=Name;
                            linkIsSpinnerMap.put(cur.getPosition(),idNew);


                        }while (cur.moveToNext());
                    }

                    adapter_linkId = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, linkIdSpinnerArr);
                    adapter_linkId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    linkIdSpinner.setAdapter(adapter_linkId);
                    adapter_linkId.notifyDataSetChanged();

                    if((updateSpin.equalsIgnoreCase("1")))
                    {
                        for(int i=0;i<linkIdSpinnerArr.length;i++)
                        {
                            if(linkIsSpinnerMap.get(i).equalsIgnoreCase(linkId))
                            {
                                linkIdSpinner.setSelection(i);
                            }
                        }
                    }

                }
                else
                {
                    new WarningDialogForValidation(mContext,"No Link id for this Road");

                    linkId="0";
                    linkIdSpinner.setEnabled(false);
                    linkIdSpinner.setClickable(false);

                }

            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    String getEncodedImage(String imagePath)
    {
        String encodedImage="";
        try {
            if(!(imagePath.equalsIgnoreCase("")))
            {
              /*get Image start*/
                BitmapFactory.Options options0 = new BitmapFactory.Options();
                options0.inSampleSize = 2;
                // options.inJustDecodeBounds = true;
                options0.inScaled = false;
                options0.inDither = false;
                options0.inPreferredConfig = Bitmap.Config.ARGB_8888;

                Bitmap bmp = BitmapFactory.decodeFile(imagePath);

                ByteArrayOutputStream baos0 = new ByteArrayOutputStream();

               // bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos0);
                byte[] imageBytes0 = baos0.toByteArray();

                //image.setImageBitmap(bmp);

                encodedImage= Base64.encodeToString(imageBytes0, Base64.DEFAULT);

        /*get Image end*/
            }

            return encodedImage;
        }
        catch (OutOfMemoryError e)
        {
            e.printStackTrace();
        }
        return "";
    }

    void successPopup(String message)
    {
        TextView txt_message;
        Button close_buton;

        final Dialog dialog_popup=new Dialog(getActivity());
        dialog_popup.setContentView(R.layout.popup_to_show_validation_msg);
        dialog_popup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_popup.getWindow().getAttributes().windowAnimations = R.style.fade_in_out_popup;
        close_buton = (Button) dialog_popup.findViewById(R.id.okay_btn);
        txt_message = (TextView) dialog_popup.findViewById(R.id.warning_msg);

        txt_message.setText(message);

        close_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_popup.dismiss();
            }
        });

        dialog_popup.show();

        close_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_popup.dismiss();

                new asyncToInsertDetails().execute();

            }
        });
    }

}
