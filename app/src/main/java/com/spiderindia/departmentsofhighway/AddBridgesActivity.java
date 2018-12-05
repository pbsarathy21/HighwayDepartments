package com.spiderindia.departmentsofhighway;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.os.Environment;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;
import com.spiderindia.departmentsofhighway.Utils.WarningDialogForValidation;
import com.spiderindia.departmentsofhighway.YearPicker.YearPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static android.os.Parcel.obtain;

public class AddBridgesActivity extends AppCompatActivity {

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

    Spinner mbridgeSpinner;
    String[] mbridgeSpinnerArr;

    HashMap<Integer,String> circleSpinnerMap;
    HashMap<Integer,String> divisionSpinnerMap;
    HashMap<Integer,String> subDivisionSpinnerMap;
    HashMap<Integer,String> roadSpinnerMap;
    HashMap<Integer,String> linkIsSpinnerMap;
    HashMap<Integer,String> mbridgeSpinnerMap;

    Button nextBttn,chooseDocBttn;

    EditText descriptionEdtTxt,locationEdtTxt,startChainageEdtTxt,brdgNoEdtTxt,brdgEdtTxt,yrOfConstrEdtTxt,construcCostEdtTxt,condtnSurvyDateEdtTxt;

    String choosenDate,choosenTime,response="";
    Context mContext;
    LinearLayout progress_layout;
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
    String circleId="",divisionId,subDivisionId,roadId,linkId,mBrdgeId,firstlinkId="",description,location,startChainage,brdgeNo,brdge,yrOfContrctn,contrctnCost,contrctnDate,documentPath,finalDocumentName="No File Choosen";
    private static final int PICKFILE_RESULT_CODE = 5;

    TextView documentNameTxt;
    ArrayAdapter<String> adapter_division,adapter_subDivision,adapter_road,adapter_linkId,adapter_mBridge;

    String responseCircleHolder="",userId,authenticationCode,responseDivision="",responseSubDivision="",responseRoad="",responseLinkId="",response_mBridge="",firstTimeAfterLogin="";
    SharedPreferences spf;

    LinearLayout bridgeLayout;
    Button bridgeBttn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bridges);

        mContext=getApplicationContext();
        loading_process= (ProgressBar)findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout= (LinearLayout)findViewById(R.id.progress_loading_layout);

        firstImage=(ImageView)findViewById(R.id.profile_img);
        secondImag=(ImageView)findViewById(R.id.second_img);
        chooseFileBttn=(Button)findViewById(R.id.choose_File_bttn_doc);
        chooseSecondFileBttn=(Button)findViewById(R.id.choose_second_photo);


        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dbase = new MyDataBaseHandler(AddBridgesActivity.this);

        spf = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = spf.getString("userId", "");
        authenticationCode = spf.getString("authenticationToken", "");
        firstTimeAfterLogin = spf.getString("firstTimeAfterLogin", "");

        nextBttn=(Button)findViewById(R.id.add_brdg_next_bttn);

        circleSpinner=(Spinner)findViewById(R.id.circle_spiner);
        divisionSpinner=(Spinner)findViewById(R.id.division_spiner);
        subDivisionSpinner=(Spinner)findViewById(R.id.sub_division_spiner);
        roadSpinner=(Spinner)findViewById(R.id.road_spiner);
        linkIdSpinner=(Spinner)findViewById(R.id.link_id_spnr);
        mbridgeSpinner=(Spinner)findViewById(R.id.brdg_spnr);

        descriptionEdtTxt=(EditText)findViewById(R.id.descrp_edtTxt);
        locationEdtTxt=(EditText)findViewById(R.id.location_edtTxt);
        startChainageEdtTxt=(EditText)findViewById(R.id.chainage_edtTxt);
        brdgNoEdtTxt=(EditText)findViewById(R.id.bridgeNo_edtTxt);

        brdgEdtTxt=(EditText)findViewById(R.id.bridge_edtTxt);
        yrOfConstrEdtTxt=(EditText)findViewById(R.id.yr_of_constrctn_edtTxt);
        construcCostEdtTxt=(EditText)findViewById(R.id.constrctn_cost_edtTxt);
        condtnSurvyDateEdtTxt=(EditText)findViewById(R.id.constrctn_survey_date_edtTxt);
        chooseDocBttn=(Button) findViewById(R.id.choose_doc_bttn);
        documentNameTxt=(TextView) findViewById(R.id.document_nam_Txt);

        bridgeBttn=(Button)findViewById(R.id.newBridgeBttn);
        bridgeLayout=(LinearLayout) findViewById(R.id.brdg_layout);

        // bridgeBttn.setEnabled(false);
        bridgeBttn.setBackgroundResource(R.drawable.bridge_bttn_disabled);



        bridgeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mbridgeSpinner.setEnabled(false);
                //bridgeBttn.setEnabled(true);
                bridgeBttn.setBackgroundResource(R.drawable.rounded_corner_for_layout_bg);
                bridgeLayout.setVisibility(View.VISIBLE);
            }
        });

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


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBridgesActivity.this,
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
            public void onClick(View v) {

               /* encodedFirstImagePath=getEncodedImage(selectedImagePath);
                encodedDocumentPath=getEncodedImage(documentPath);

                byte[] decodedString = Base64.decode(encodedFirstImagePath, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                firstImage.setImageBitmap(decodedByte);*/

                try{
                    circleId = circleSpinnerMap.get(circleSpinner.getSelectedItemPosition());
                    divisionId = divisionSpinnerMap.get(divisionSpinner.getSelectedItemPosition());
                    subDivisionId = subDivisionSpinnerMap.get(subDivisionSpinner.getSelectedItemPosition());
                    roadId = roadSpinnerMap.get(roadSpinner.getSelectedItemPosition());

                    linkId = linkIsSpinnerMap.get(linkIdSpinner.getSelectedItemPosition());

                    startChainage=startChainageEdtTxt.getText().toString();
                    location=locationEdtTxt.getText().toString();
                    description=descriptionEdtTxt.getText().toString();
                    brdgeNo=brdgNoEdtTxt.getText().toString();
                    brdge=brdgEdtTxt.getText().toString();
                    yrOfContrctn=yrOfConstrEdtTxt.getText().toString();
                    contrctnCost=construcCostEdtTxt.getText().toString();
                    contrctnDate=condtnSurvyDateEdtTxt.getText().toString();

                    if(circleId.equalsIgnoreCase("0"))
                    {
                        new WarningDialogForValidation(mContext,"Select Circle");
                    }
                    else  if(divisionId.equalsIgnoreCase("0"))
                    {
                        new WarningDialogForValidation(mContext,"Select Division");

                    }
                    else  if(subDivisionId.equalsIgnoreCase("0"))
                    {
                        new WarningDialogForValidation(mContext,"Select SubDivision");
                    }
                    else  if(roadId.equalsIgnoreCase("0"))
                    {
                        new WarningDialogForValidation(mContext,"Select Road");
                    }
                    else  if(linkId.equalsIgnoreCase("0"))
                    {
                        new WarningDialogForValidation(mContext,"Select Link Id");
                    }
                    else
                    {
                        if( !(firstlinkId.equalsIgnoreCase("0"))){

                            if(firstlinkId.equalsIgnoreCase(linkId))
                            {
                               // HomeActivity._mViewPager.setCurrentItem(1);
                                new asyncToSendDetails().execute();
                            }
                            else
                            {
                                //HomeActivity._mViewPager.setCurrentItem(1);
                                new asyncToSendDetails().execute();

                                Toast.makeText(AddBridgesActivity.this,"Are you sure you want to destroy old Details..",Toast.LENGTH_SHORT).show();

                                // successPopup("Are you sure you want to destroy old Details..");
                            }
                        }
                        else
                        {
                           // HomeActivity._mViewPager.setCurrentItem(1);
                            new asyncToSendDetails().execute();
                        }

                    }


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

        yrOfConstrEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearPickerDialog.show();
            }
        });
  /*year picker start*/

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear,mMonth,mDay);

        yearPickerDialog = new YearPickerDialog(AddBridgesActivity.this, calendar, new YearPickerDialog.OnDateSetListener() {
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

        SpinnerListenerForLinkId linkIdListener = new SpinnerListenerForLinkId();
        linkIdSpinner.setOnTouchListener(roadListener);
        linkIdSpinner.setOnItemSelectedListener(linkIdListener);

    }

    class asyncToGetDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            progress_layout.setVisibility(View.VISIBLE);

            getCircleDetails();
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

                final ArrayAdapter<String> adapter_circle = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, circleSpinnerArr);
                adapter_circle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                circleSpinner.setAdapter(adapter_circle);


                adapter_division = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, divisionSpinnerArr);
                adapter_division.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                divisionSpinner.setAdapter(adapter_division);

                adapter_subDivision = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, subDivisionSpinnerArr);
                adapter_subDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subDivisionSpinner.setAdapter(adapter_subDivision);

                adapter_road = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, roadSpinnerArr);
                adapter_road.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                roadSpinner.setAdapter(adapter_road);


                adapter_linkId = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, linkIdSpinnerArr);
                adapter_linkId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                linkIdSpinner.setAdapter(adapter_linkId);


                adapter_mBridge = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, mbridgeSpinnerArr);
                adapter_mBridge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mbridgeSpinner.setAdapter(adapter_mBridge);


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
                    if(!(roadId.equalsIgnoreCase("0")))
                    {
                        update_mBridge(roadId,"1");
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

                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);

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
        mbridgeSpinnerArr = new String[5];

        //circleSpinnerMap = new HashMap<Integer, String>();
        divisionSpinnerMap = new HashMap<Integer, String>();
        subDivisionSpinnerMap = new HashMap<Integer, String>();
        roadSpinnerMap = new HashMap<Integer, String>();
        linkIsSpinnerMap = new HashMap<Integer, String>();
        mbridgeSpinnerMap = new HashMap<Integer, String>();




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
                mbridgeSpinnerArr[0]="None";
            }
            else
            {
                id=i+"";
                //circleSpinnerArr[(i)]="India "+i;
                divisionSpinnerArr[(i)]="Division "+i;
                subDivisionSpinnerArr[(i)]="Sub Division "+i;
                roadSpinnerArr[(i)]="Road "+i;
                linkIdSpinnerArr[(i)]="Link Id "+i;
                mbridgeSpinnerArr[(i)]="Bridge Id "+i;
            }

            //circleSpinnerMap.put(i,id);
            divisionSpinnerMap.put(i,id);
            subDivisionSpinnerMap.put(i,id);
            roadSpinnerMap.put(i,id);
            linkIsSpinnerMap.put(i,id);
            mbridgeSpinnerMap.put(i,id);

        }

        divisionSpinner.setEnabled(false);
        divisionSpinner.setClickable(false);

        subDivisionSpinner.setEnabled(false);
        subDivisionSpinner.setClickable(false);

        roadSpinner.setEnabled(false);
        roadSpinner.setClickable(false);

        linkIdSpinner.setEnabled(false);
        linkIdSpinner.setClickable(false);

        mbridgeSpinner.setEnabled(false);
        mbridgeSpinner.setClickable(false);




    }


    private void alertBoxToChooseImage(String imageIdentity) {

        dialog = new Dialog(AddBridgesActivity.this, R.style.FullHeightDialog);
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
        if (ContextCompat.checkSelfPermission(AddBridgesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddBridgesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_FOR_PERMISSION);
        }
        else
        {
            alertBoxToChooseImage(imageIdentity);
        }

    }

    public void PermissionRequestForSecondImage(String imageIdentity)
    {
        if (ContextCompat.checkSelfPermission(AddBridgesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddBridgesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_FOR_PERMISSION);
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
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    selectedImagePath=imageToUploadUri.getPath();


                    Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        firstImage.setImageBitmap(reducedSizeBitmap);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(AddBridgesActivity.this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(AddBridgesActivity.this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }
            else if (requestCode == CAMERA_SECOND_PHOTO && resultCode == RESULT_OK) {
                if(secondImageToUploadUri != null){
                    Uri selectedImage = secondImageToUploadUri;
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    secondSelectedImagePath=secondImageToUploadUri.getPath();


                    Bitmap reducedSizeBitmap = getBitmap(secondImageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        secondImag.setImageBitmap(reducedSizeBitmap);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(AddBridgesActivity.this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(AddBridgesActivity.this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }

            else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = AddBridgesActivity.this.getContentResolver().query(selectedImage,
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
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = AddBridgesActivity.this.getContentResolver().query(selectedImage,
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
    }
    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = AddBridgesActivity.this.getContentResolver().openInputStream(uri);

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
            in = AddBridgesActivity.this.getContentResolver().openInputStream(uri);
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
    public class SpinnerListenerForLinkId implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

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

                update_mBridge(roadId,"0");

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

                    adapter_division = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, divisionSpinnerArr);
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

                        mbridgeSpinner.setEnabled(false);
                        mbridgeSpinner.setClickable(false);

                        subDivisionId="0";
                        roadId="0";
                        linkId="0";
                        mBrdgeId="0";


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

                    adapter_subDivision = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, subDivisionSpinnerArr);
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

                        mbridgeSpinner.setEnabled(false);
                        mbridgeSpinner.setClickable(false);

                        roadId="0";
                        linkId="0";
                        mBrdgeId="0";
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

                    mbridgeSpinner.setEnabled(false);
                    mbridgeSpinner.setClickable(false);

                    subDivisionId="0";
                    roadId="0";
                    linkId="0";
                    mBrdgeId="0";


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

                    adapter_road = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, roadSpinnerArr);
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

                        mbridgeSpinner.setEnabled(false);
                        mbridgeSpinner.setClickable(false);


                        linkId="0";
                        mBrdgeId="0";
                    }
                }
                else
                {
                    new WarningDialogForValidation(mContext,"No Roads for this SubDivision");
                    roadSpinner.setEnabled(false);
                    roadSpinner.setClickable(false);

                    linkIdSpinner.setEnabled(false);
                    linkIdSpinner.setClickable(false);

                    mbridgeSpinner.setEnabled(false);
                    mbridgeSpinner.setClickable(false);


                    roadId="0";
                    linkId="0";
                    mBrdgeId="0";


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

                    adapter_linkId = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, linkIdSpinnerArr);
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
                    mBrdgeId="0";

                    linkIdSpinner.setEnabled(false);
                    linkIdSpinner.setClickable(false);

                    mbridgeSpinner.setEnabled(false);
                    mbridgeSpinner.setClickable(false);



                }

            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void update_mBridge(String linkId_id,String updateSpin)
    {
        System.out.println("response_mBridge main "+response_mBridge);
        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getLinkIdDetailsFromServer(userId,authenticationCode,linkId_id);
        try {
            response_mBridge=json.getString("success");
            System.out.println("response_mBridge "+response_mBridge);
            if(response_mBridge.equalsIgnoreCase("TRUE"))
            {
                ContentValues linkCon=new ContentValues();
                JSONArray jsonArray = json.getJSONArray("link");

                System.out.println("response_mBridge tr"+response_mBridge);
                if(jsonArray.length()>0)
                {
                    System.out.println("response_mBridge came "+response_mBridge);

                    mbridgeSpinner.setEnabled(true);
                    mbridgeSpinner.setClickable(true);


                    dbase.emptyTable(dbase.TABLE_NAME_mBRIDGE);


                    linkCon.put(dbase.mBRIDGE_Id,"0");
                    linkCon.put(dbase.mBRIDGE_NAME,"None");

                    dbase.InsertDetails(linkCon,dbase.TABLE_NAME_mBRIDGE);

                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject json_user = jsonArray.getJSONObject(i);
                        String divisionId = json_user.getString("id");
                        String name = json_user.getString("link_id");

                        linkCon.put(dbase.mBRIDGE_Id,divisionId);
                        linkCon.put(dbase.mBRIDGE_NAME,name);

                        dbase.InsertDetails(linkCon,dbase.TABLE_NAME_mBRIDGE);

                    }

                    Cursor cur=dbase.getAllCircleDetails(dbase.TABLE_NAME_mBRIDGE);
                    mbridgeSpinnerArr = new String[cur.getCount()];
                    mbridgeSpinnerMap = new HashMap<Integer, String>();

                    if (cur.moveToFirst())
                    {
                        do {

                            String idNew=cur.getString(0).toString();
                            String Name=cur.getString(1).toString();

                            mbridgeSpinnerArr[cur.getPosition()]=Name;
                            mbridgeSpinnerMap.put(cur.getPosition(),idNew);


                        }while (cur.moveToNext());
                    }

                    adapter_mBridge = new ArrayAdapter<String>(AddBridgesActivity.this,R.layout.custom_spinner, mbridgeSpinnerArr);
                    adapter_mBridge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mbridgeSpinner.setAdapter(adapter_mBridge);
                    adapter_mBridge.notifyDataSetChanged();

                    if((updateSpin.equalsIgnoreCase("1")))
                    {
                        for(int i=0;i<mbridgeSpinnerArr.length;i++)
                        {
                            if(mbridgeSpinnerMap.get(i).equalsIgnoreCase(linkId))
                            {
                                mbridgeSpinner.setSelection(i);
                            }
                        }
                    }

                }
                else
                {
                    System.out.println("response_mBridge else "+response_mBridge);

                    new WarningDialogForValidation(mContext,"No Bridge for this Link Id");

                    mBrdgeId="0";
                    mbridgeSpinner.setEnabled(false);
                    mbridgeSpinner.setClickable(false);

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

  /*  void successPopup(String message)
    {
        TextView txt_message;
        Button close_buton;

        final Dialog dialog_popup=new Dialog(AddBridgesActivity.this);
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
    }*/
    private void getCircleDetails() {

        Config userFunction1 = new Config();
        JSONObject json = userFunction1.getBridgeCircleDetailsFromServer(userId,authenticationCode);
        try {
            response=json.getString("success");
            if(response.equalsIgnoreCase("TRUE"))
            {
                dbase.emptyTable(dbase.TABLE_NAME_CIRCLE);

                ContentValues CircleCon=new ContentValues();
                JSONArray jsonArray = json.getJSONArray("circle");

                CircleCon.put(dbase.CIRCLE_ID,"0");
                CircleCon.put(dbase.CIRCLE_NAME,"None");
                dbase.InsertDetails(CircleCon,dbase.TABLE_NAME_CIRCLE);
                Map<String, Object> map = new HashMap<String, Object>();



                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject json_user = jsonArray.getJSONObject(i);

                    for (Iterator<String> it = json_user.keys(); it.hasNext(); ) {
                        String key = it.next();
                        map.put(key, json_user.get(key));
                    }

                    String id = json_user.getString("id");
                    String name = json_user.getString("name");

                    CircleCon.put(dbase.CIRCLE_ID,id);
                    CircleCon.put(dbase.CIRCLE_NAME,name);

                    dbase.InsertDetails(CircleCon,dbase.TABLE_NAME_CIRCLE);
                }

                Set<String> keys = map.keySet();  //get all keys
                for(String i: keys)
                {
                    System.out.println("Hash map key "+map.get(i) +" sixze "+map.size());
                }

              /*  for (String entry : map.keySet()) {
                    String value =  map.get(entry);
                    System.out.print(entry + "" + value + " ");
                    // do stuff
                }*/

                Parcel parcel = obtain();
                parcel.writeMap(map);
                parcel.setDataPosition(0);
                ContentValues values = ContentValues.CREATOR.createFromParcel(parcel);

                printContentValues(values);

                //printContentValues(CircleCon);
            }
            else
            {

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
