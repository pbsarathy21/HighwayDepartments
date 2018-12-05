package com.spiderindia.departmentsofhighway.NewActivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.JSON.RetrofitClient;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvertAddResponse.CulvertAddResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvertEditResponse.CulvetEditResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvetResponse.DataItem;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.YearPicker.YearPickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CulvertFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    TextView headerTxt;
    EditText yrOfConstrEdtTxt, culvet_key_id, geoLocation;
    YearPickerDialog yearPickerDialog;
    Button submitBttn, choosePhoto, setLocation;
    EditText chainageEdtTxt,culvertTypeEdtTxt,culvertNumEdtTxt,noOfRowsEdtTxt,pipesEdtTxt,
            ventWidthEdtTxt,ventHeightEdtTxt,noOfSpanEdtTxt,culvertLengthEdtTxt,conditiondEdtTxt;
    LinearLayout progress_layout, culvetKey;
    ProgressBar loading_process;
    Spinner conditionSpnr, culvertTypSpnr;
    String[] culvertTypSpinnerArr;
    HashMap<Integer,String> culvertTypSpinnerMap;
    String[] conditionSpinnerArr;
    HashMap<Integer,String> conditionSpinnerMap;

    ImageView CulvetImage;

    String imagePath = null;

    public static final int REQUEST_CAMERA = 111, SELECT_PHOTO = 222;

    Uri cameraUri, galleryUri;

    Bitmap bitmap;

    SharedPreferences preferences;

    String culvertType, conditionType;

    ProgressDialog progressDialog;

    DataItem dataItem;

    @Override
    protected void onStart() {

        SharedPreferences sharedPreferences = getSharedPreferences("Modify",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            culvetKey.setVisibility(View.VISIBLE);

            presetValues();

            /*editor.putString("data", "false");*/
        } else {
            culvetKey.setVisibility(View.GONE);
        }

        super.onStart();
    }

    private void presetValues() {

        dataItem  = (DataItem) getIntent().getSerializableExtra("item");

        culvet_key_id.setText(dataItem.getCULVERTKEYID());
        chainageEdtTxt.setText(dataItem.getCHAINAGE());
        culvertNumEdtTxt.setText(dataItem.getCULVERTNUMBER());
        noOfRowsEdtTxt.setText(dataItem.getNOOFROWS());
        pipesEdtTxt.setText(dataItem.getPIPESDIAMETER());
        ventWidthEdtTxt.setText(dataItem.getVENTWIDTH());
        ventHeightEdtTxt.setText(dataItem.getVENTHEIGHT());
        noOfSpanEdtTxt.setText(dataItem.getNOOFSPAN());
        culvertLengthEdtTxt.setText(dataItem.getCULVERTLENGTH());

    }

    @Override
    protected void onPostResume() {

        SharedPreferences sharedPreferences = getSharedPreferences("Modify",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            culvetKey.setVisibility(View.VISIBLE);

            presetValues();

            /*editor.putString("data", "false");*/
        } else {
            culvetKey.setVisibility(View.GONE);
        }

        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culvert_form);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait...");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        headerTxt = (TextView) findViewById(R.id.header_txt);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
        headerTxt.setText("CULVERT");

        yrOfConstrEdtTxt=(EditText)findViewById(R.id.constru_yr_edt);

        culvet_key_id = (EditText) findViewById(R.id.culvet_key_id);

        culvet_key_id.setEnabled(false);

        culvetKey = (LinearLayout) findViewById(R.id.culvetKey);

        conditionSpnr=(Spinner)findViewById(R.id.condition_spnr);

        culvertTypSpnr=(Spinner)findViewById(R.id.culvert_typ_spnr);

        conditionSpnr.setOnItemSelectedListener(this);
        culvertTypSpnr.setOnItemSelectedListener(this);

        loading_process= (ProgressBar)findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout= (LinearLayout)findViewById(R.id.progress_loading_layout);


        geoLocation=(EditText) findViewById(R.id.culvet_geoLocation);

        geoLocation.setVisibility(View.GONE);

        setLocation=(Button) findViewById(R.id.setCulvetLocation);
        choosePhoto=(Button) findViewById(R.id.choosePhoto);

        CulvetImage = (ImageView) findViewById(R.id.culvet_img);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogbox();

            }
        });

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(CulvertFormActivity.this, MapsActivity.class));

               SharedPreferences spf = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                String lat =  spf.getString("latitude", "0");
                String lon =  spf.getString("longitude", "0");

                geoLocation.setText(lat + " " + lon);

            }
        });

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        yrOfConstrEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearPickerDialog.show();
            }
        });

        chainageEdtTxt=(EditText)findViewById(R.id.chainage_edt);
        culvertNumEdtTxt=(EditText)findViewById(R.id.culvert_num_edt);
        culvertTypeEdtTxt=(EditText)findViewById(R.id.culvert_typ_edt);
        noOfRowsEdtTxt=(EditText)findViewById(R.id.no_of_row_edt);
        pipesEdtTxt=(EditText)findViewById(R.id.pipe_dia_edt);
        ventWidthEdtTxt=(EditText)findViewById(R.id.vent_width_edt);
        ventHeightEdtTxt=(EditText)findViewById(R.id.vent_height_edt);
        noOfSpanEdtTxt=(EditText)findViewById(R.id.no_of_span_edt);
        culvertLengthEdtTxt=(EditText)findViewById(R.id.culvert_length_edt);
        conditiondEdtTxt=(EditText)findViewById(R.id.condition_edt);

        submitBttn=(Button)findViewById(R.id.submit_btn);

        submitBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = validate();

                if (valid)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("Modify",Context.MODE_PRIVATE);

                    String data = sharedPreferences.getString("data", "false");

                    if (data.equalsIgnoreCase("true"))
                    {
                        submitBttn.setText("Update");
                        updateCulvets();

                    } else
                    {
                        uploadCulvets();  }
                }
            }
        });
  /*year picker start*/

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear,mMonth,mDay);

        yearPickerDialog = new YearPickerDialog(CulvertFormActivity.this, calendar, new YearPickerDialog.OnDateSetListener() {
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

        new asyncToGetDetails().execute();

    }

    private boolean validate() {

        if (TextUtils.isEmpty(imagePath))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Image is Mandatory");
            builder.setMessage("Please upload a photo first");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.create();
            builder.show();

            return false;
        }

        preferences=getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String latitude = preferences.getString("latitude", null);
        String longitude = preferences.getString("longitude", null);

        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error in location");
            builder.setMessage("Please set the location again");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.create();
            builder.show();

            return false;
        }

        return true;
    }

    private void openDialogbox() {

        final Dialog dialog = new Dialog(CulvertFormActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.popup_to_choose_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        ImageView closeImg = (ImageView) dialog.findViewById(R.id.close_icon_);
        LinearLayout cameraLL=(LinearLayout)dialog.findViewById(R.id.camera_layout);
        LinearLayout galleryLL=(LinearLayout)dialog.findViewById(R.id.gallery_ll);


        cameraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
                dialog.dismiss();
                CulvetImage.requestFocus();
            }
        });
        galleryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                dialog.dismiss();
                CulvetImage.requestFocus();
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                CulvetImage.requestFocus();

            }
        });
    }

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PHOTO);
    }

    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK)
        {
            cameraUri = data.getData();

            bitmap = (Bitmap) data.getExtras().get("data");

            CulvetImage.setImageBitmap(bitmap);

            imagePath = getRealpathfromUri(cameraUri);

        }

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK)
        {
            galleryUri = data.getData();

            CulvetImage.setImageURI(galleryUri);

            imagePath = getRealpathfromUri(galleryUri);

            /*imagePath = data.getData().getPath();*/

        }
    }

    private String getRealpathfromUri(Uri cameraUri) {
        String projection = (MediaStore.Images.Media.DATA);
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),cameraUri, new String[]{projection}, null,null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_indx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_indx);
        cursor.close();
        return result;

    }


    private void updateCulvets() {

        progressDialog.show();


        preferences=getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String circle_id = preferences.getString("Cir_id", "0");
        String div_id = preferences.getString("div_id", "0");
        String subdiv_id = preferences.getString("Subdivision_Id", "0");
        String user_id = preferences.getString("userId", "0");
        String latitude = preferences.getString("latitude", null);
        String longitude = preferences.getString("longitude", null);
        String road_id = preferences.getString("Road_Id", "0");
        String link_id = preferences.getString("Link_Id", "0");
        String condition = preferences.getString("condition_type_spinner", "0");
        String type = preferences.getString("culvert_type_spinner", "0");


        String culvet_key = culvet_key_id.getText().toString();

        String chainage = chainageEdtTxt.getText().toString();
        String culvert_no = culvertNumEdtTxt.getText().toString();
        String year = yrOfConstrEdtTxt.getText().toString();
        String no_rows = noOfRowsEdtTxt.getText().toString();
        String pipes_dia = pipesEdtTxt.getText().toString();
        String vent_width = ventWidthEdtTxt.getText().toString();
        String vent_height = ventHeightEdtTxt.getText().toString();
        String no_span = noOfSpanEdtTxt.getText().toString();
        String culvet_len = culvertLengthEdtTxt.getText().toString();
        String description = "culvert description";


        String  closed_date = "test", work_flow = "test", session_id = "236", cuvert_id = "13345";

        File photo = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("attach_photo", photo.getName(), requestBody);

        Call<CulvetEditResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .EditCulvetDetails(culvet_key, description,chainage,culvert_no,cuvert_id, year, circle_id, div_id, subdiv_id, road_id, link_id, user_id,
                        no_rows,pipes_dia, no_span,vent_width,vent_height,culvet_len, condition, closed_date, work_flow, session_id, latitude, longitude, type, body);

        call.enqueue(new Callback<CulvetEditResponse>() {
            @Override
            public void onResponse(Call<CulvetEditResponse> call, Response<CulvetEditResponse> response) {
                progressDialog.dismiss();

                if (response.code() == 200)
                {
                    CulvetEditResponse culvetEditResponse = response.body();
                    if (!culvetEditResponse.getMessage().isEmpty())
                    {
                        Toast.makeText(CulvertFormActivity.this, ""+culvetEditResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(CulvertFormActivity.this);
                        builder.setTitle(culvetEditResponse.getResult());
                        builder.setMessage(culvetEditResponse.getMessage());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                                preferences.edit().remove("latitude").commit();
                                preferences.edit().remove("longitude").commit();

                                finish();
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        Toast.makeText(CulvertFormActivity.this, response.code()+" : Response body Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CulvertFormActivity.this, response.code()+" : Server Error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<CulvetEditResponse> call, Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

    private void uploadCulvets() {

        progressDialog.show();


        preferences=getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String circle_id = preferences.getString("Cir_id", "0");
        String div_id = preferences.getString("div_id", "0");
        String subdiv_id = preferences.getString("Subdivision_Id", "0");
        String user_id = preferences.getString("userId", "0");
        String latitude = preferences.getString("latitude", null);
        String longitude = preferences.getString("longitude", null);
        String road_id = preferences.getString("Road_Id", "0");
        String link_id = preferences.getString("Link_Id", "0");
        String condition = preferences.getString("condition_type_spinner", "0");
        String type = preferences.getString("culvert_type_spinner", "0");


      //  String culvet_key = culvet_key_id.getText().toString();

        String chainage = chainageEdtTxt.getText().toString();
        String culvert_no = culvertNumEdtTxt.getText().toString();
        String year = yrOfConstrEdtTxt.getText().toString();
        String no_rows = noOfRowsEdtTxt.getText().toString();
        String pipes_dia = pipesEdtTxt.getText().toString();
        String vent_width = ventWidthEdtTxt.getText().toString();
        String vent_height = ventHeightEdtTxt.getText().toString();
        String no_span = noOfSpanEdtTxt.getText().toString();
        String culvet_len = culvertLengthEdtTxt.getText().toString();
        String description = "culvert description";


        String  closed_date = "test", work_flow = "test", session_id = "236", cuvert_id = "13345";

        File photo = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("attach_photo", photo.getName(), requestBody);
        Call<CulvertAddResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .addCulvetDetails(description,chainage,culvert_no,cuvert_id, year,
                        circle_id, div_id, subdiv_id, road_id, link_id, user_id,
                        no_rows,pipes_dia, no_span,vent_width,vent_height,culvet_len,
                        condition, closed_date, work_flow, session_id, latitude, longitude,type, body);

        call.enqueue(new Callback<CulvertAddResponse>() {
            @Override
            public void onResponse(@NonNull Call<CulvertAddResponse> call, @NonNull Response<CulvertAddResponse> response) {

                progressDialog.dismiss();

                if (response.code() == 200)
                {
                    CulvertAddResponse culvertAddResponse = response.body();
                    if (!culvertAddResponse.getMessage().isEmpty())
                    {
                        Toast.makeText(CulvertFormActivity.this, ""+culvertAddResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(CulvertFormActivity.this);
                        builder.setTitle(culvertAddResponse.getResult());
                        builder.setMessage(culvertAddResponse.getMessage());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                                preferences.edit().remove("latitude").commit();
                                preferences.edit().remove("longitude").commit();

                                finish();
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        Toast.makeText(CulvertFormActivity.this, response.code()+" : Response body Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CulvertFormActivity.this, response.code()+" : Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CulvertAddResponse> call, @NonNull Throwable t) {

                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int id = adapterView.getId();

        if (id == R.id.culvert_typ_spnr)
        {
            culvertType= adapterView.getItemAtPosition(i).toString();

            editor.putString("culvert_type_spinner", culvertType);
            editor.apply();

            return;
        }

        if (id == R.id.condition_spnr)
        {
            conditionType = adapterView.getItemAtPosition(i).toString();
            editor.putString("condition_type_spinner", conditionType);
            editor.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

                sendUserDetailsServer();

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

                final ArrayAdapter<String> adapter_culType = new ArrayAdapter<String>(CulvertFormActivity.this,R.layout.custom_spinner, culvertTypSpinnerArr);
                adapter_culType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                culvertTypSpnr.setAdapter(adapter_culType);

                int position = Integer.parseInt(dataItem.getCULVERTTYPE());

                if (position>=0 && position<10)
                {
                    culvertTypSpnr.setSelection(position);
                }

                final ArrayAdapter<String> adapter_condition = new ArrayAdapter<String>(CulvertFormActivity.this,R.layout.custom_spinner, conditionSpinnerArr);
                adapter_condition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                conditionSpnr.setAdapter(adapter_condition);

                int position1 = Integer.parseInt(dataItem.getCULVERTCONDITION());

                if (position1>=0 && position1<10)
                {
                    culvertTypSpnr.setSelection(position1);
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
    void  sendUserDetailsServer()
    {
        culvertTypSpinnerArr = new String[6];
        conditionSpinnerArr = new String[5];

        culvertTypSpinnerMap = new HashMap<Integer, String>();
        conditionSpinnerMap = new HashMap<Integer, String>();
        for(int i=0;i<6;i++)
        {
            String id ="0";
            if(i==0)
            {
                id="0";
                culvertTypSpinnerArr[0]="Select";
                conditionSpinnerArr[0]="Select";
            }

            if(i==1)
            {
                id="1";
                culvertTypSpinnerArr[1]="Box";
                conditionSpinnerArr[1]="Good";
            }

            if(i==2)
            {
                id="2";
                culvertTypSpinnerArr[2]="Slab";
                conditionSpinnerArr[2]="Fair";
            }

            if(i==3)
            {
                id="3";
                culvertTypSpinnerArr[3]="Pipe";
                conditionSpinnerArr[3]="Slight Damage";
            }

            if(i==4)
            {
                id="4";
                culvertTypSpinnerArr[4]="Cause ways";
                conditionSpinnerArr[4]="Severe damage";
            }

            if(i==5)
            {
                id="5";
                culvertTypSpinnerArr[5]="Cut Stone Culvert";
            }


            culvertTypSpinnerMap.put(i,id);
            conditionSpinnerMap.put(i,id);

        }

    }


}
