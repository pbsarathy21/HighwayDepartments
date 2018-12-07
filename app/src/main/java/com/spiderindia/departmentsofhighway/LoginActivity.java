package com.spiderindia.departmentsofhighway;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.JSON.RetrofitClient;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelLoginResponse.LoginResponse;
import com.spiderindia.departmentsofhighway.Utils.NetUtils;
import com.spiderindia.departmentsofhighway.Utils.OtpListener;
import com.spiderindia.departmentsofhighway.Utils.OtpView;
import com.spiderindia.departmentsofhighway.Utils.WarningDialog;
import com.spiderindia.departmentsofhighway.Utils.WarningDialogForValidation;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText mobileNumEdtTxt;
    Button submit_btn;
    String mobileNo, otp, otpResponse = "", FcmRegId = "";
    LinearLayout progress_layout;
    ProgressBar loading_process;
    String loginResponse = "", signOut = "", error_msg = "";
    SharedPreferences spref, spf;
    SharedPreferences.Editor editor;
    private static final int REQUEST_IMAGE = 1;
    SmsVerifyCatcher smsVerifyCatcher;
    OtpView otpView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(LoginActivity.this.getResources().getColor(R.color.status_color
            ));
        }


        loading_process = (ProgressBar) findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout = (LinearLayout) findViewById(R.id.progress_loading_layout);

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        spf = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        signOut = spf.getString("SignOut", "");

        if ((signOut.equalsIgnoreCase("")) || (signOut.equalsIgnoreCase("1"))) {
            permissionToGetImage();
        } else {
            spref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            editor = spref.edit();
            editor.putString("firstTimeAfterLogin", "0");
            editor.commit();

            Intent i = new Intent(getApplicationContext(), DashBoardActivity.class);
            startActivity(i);
            finish();

        }


        mobileNumEdtTxt = (EditText) findViewById(R.id.mbl_edtTxt);
        submit_btn = (Button) findViewById(R.id.sub_btn);
        otpView = (OtpView) findViewById(R.id.otp_edtTxt);

        otpView.setListener(new OtpListener() {
            @Override
            public void onOtpEntered(String otp) {

                System.out.println("OTP VAue " + otp);
            }
        });


        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code
                System.out.println("Your otp is " + code);
                otpView.setOTP(code);//set code in edit text
                //then you can send verification code to server
            }
        });

        otpView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                otpView.post(new Runnable() {
                    @Override
                    public void run() {

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(otpView, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        otpView.requestFocus();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileNo = mobileNumEdtTxt.getText().toString();

                otp = otpView.getOTP();


                if (submit_btn.getText().equals("Login")) {
                    if (mobileNo.trim().equals("")) {
                        mobileNumEdtTxt.setError("Enter UserName");

                    } else if (!(mobileNo.equals(""))) {
                        if (NetUtils.isNetworkConnect(LoginActivity.this)) {

                            new asyncToSendDetails().execute();
                        } else {
                            new WarningDialog(LoginActivity.this, getResources().getString(R.string.net_connection_fail));
                        }
                    }
                } else {
                    if (otp.trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter your OTP", Toast.LENGTH_SHORT).show();

                    } else if (!(otp.trim().equals(""))) {
                        if (NetUtils.isNetworkConnect(LoginActivity.this)) {

                           // new asyncToSendOtpDetails().execute();

                            newRetrofitRequest();

                        } else {
                            new WarningDialog(LoginActivity.this, getResources().getString(R.string.net_connection_fail));
                        }
                    }
                }

            }
        });

        /*Notification start*/

        String registrationId = FirebaseInstanceId.getInstance().getToken();
        if (registrationId != null) {
            Log.w("notification", registrationId);
            System.out.println("notification ... " + registrationId);


            if (!TextUtils.isEmpty(registrationId)) {
                FcmRegId = registrationId;

                Log.e("Login Activity", "Firebase reg id: " + FcmRegId);

                spref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                editor.putString("FcmRegId", FcmRegId);
                editor.commit();


                //Toast.makeText(getApplicationContext(), "Firebase Reg Id: " + regId, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_SHORT).show();

        }



        /*Notification end*/


    }

    private void newRetrofitRequest() {

        progress_layout.setVisibility(View.VISIBLE);

        String mobileNO = mobileNumEdtTxt.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("phone_no", mobileNO);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserDetails(jsonObject.toString());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (progress_layout.getVisibility() == View.VISIBLE)
                {
                    progress_layout.setVisibility(View.GONE);
                }

                LoginResponse loginResponse = response.body();

                String userId = loginResponse.getData().get(0).getUSERKEYID();
                String subId = loginResponse.getData().get(0).getSUBDIVISIONKEYID();
                String authentication_token = loginResponse.getData().get(0).getAUTHENTICATIONTOKEN();
                String name = loginResponse.getData().get(0).getFIRSTNAME();
                String divId = loginResponse.getData().get(0).getDIVISIONKEYID();
                String CirId = loginResponse.getData().get(0).getCIRCLEKEYID();
                String authority = loginResponse.getData().get(0).getAUTHORITY();


                SharedPreferences saved_values = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = saved_values.edit();

                editor.putString("userId",userId);
                editor.putString("Subdivision_Id", subId);
                editor.putString("authenticationToken", authentication_token);
                editor.putString("userName", name);
                editor.putString("div_id", divId);
                editor.putString("Cir_id", CirId);
                editor.putString("authority", authority);
                editor.putString("firstTimeAfterLogin", "1");

                editor.apply();

                SharedPreferences preferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putString("SignOut", "true");
                editor1.apply();


                Toast.makeText(LoginActivity.this, ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });





    }

   /* class asyncToSendDetails extends AsyncTask<Void, Void, String> {
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

                if(!(loginResponse.equals("")) && loginResponse.equalsIgnoreCase("TRUE"))
                {

                    Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(loginResponse.equalsIgnoreCase("FALSE"))
                {
                    if(!(error_msg.equalsIgnoreCase("")))
                    {
                        Toast.makeText(getApplicationContext(),error_msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Something Wrong", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    private void sendUserDetailsServer()
    {
        Config userFunction1 = new Config();
        JSONObject json = userFunction1.LoginProcess(mobileNo,password);

        try {
            loginResponse=json.getString("success");

            if(loginResponse.equalsIgnoreCase("TRUE"))
            {

               *//* String data=json.getString("login");
                JSONObject obj=new JSONObject(data);

                // String celebration_name=obj.getString("celebration_name");
                //System.out.println("celebration_name "+celebration_name);
                JSONObject jsonObj=obj.getJSONObject("user_data");

                String userId=jsonObj.getString("client_id");
                String authentication_token=jsonObj.getString("authentication_token");
                String name=jsonObj.getString("username");

                String celebration_name=obj.getString("celebration_name");
                String celebration_id=obj.getString("celebration_id");
                String imageCount=obj.getString("imageCount");
                String profileImg=obj.getString("logo");
                String folder=obj.getString("folder");

                System.out.println("authentication_token "+authentication_token);

                spref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                editor = spref.edit();
                editor.putString("userId", userId);
                editor.putString("authenticationToken", authentication_token);
                editor.putString("uesrName", name);
                editor.putString("userLogo", profileImg);
                editor.putString("celebrationName",celebration_name);
                editor.putString("celebrationId",celebration_id);
                editor.putString("imageCount", imageCount);
                editor.putString("imageFolder", folder);
                editor.putString("neededImageCount", "0");
                editor.putString("SignOut", "0");
                editor.commit();*//*

            }
            else if(loginResponse.equalsIgnoreCase("False"))
            {
                *//*String data=json.getString("error");
                JSONObject obj=new JSONObject(data);
                error_msg=obj.getString("error_message");*//*
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }*/

    class asyncToSendOtpDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {
                sendOtpDetailsServer();


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

                if (!(otpResponse.equals("")) && otpResponse.equalsIgnoreCase("success")) {

                    Intent i = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(i);
                    finish();

                } else if (otpResponse.equalsIgnoreCase("error")) {
                    if (!(error_msg.equalsIgnoreCase(""))) {
                        Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    private void sendOtpDetailsServer() {
        mobileNo = mobileNumEdtTxt.getText().toString().trim();
        System.out.println("mobileNo " + mobileNo);
        Config userFunction1 = new Config();

        JSONObject json = userFunction1.senOtpToServer(mobileNo, otp);


        try {
            otpResponse = json.getString("result");


            if (otpResponse.equalsIgnoreCase("success")) {


                Toast.makeText(getApplicationContext(), "OTP Success...", Toast.LENGTH_LONG).show();

                JSONArray jsonArray = json.getJSONArray("data");

                JSONObject obj = jsonArray.getJSONObject(0);


                String userId = obj.getString("USER_KEY_ID");
                String subId = obj.getString("SUBDIVISION_KEY_ID");
                String authentication_token = obj.getString("AUTHENTICATION_TOKEN");
                String name = obj.getString("FIRST_NAME");
                String divId = obj.getString("DIVISION_KEY_ID");
                String CirId = obj.getString("CIRCLE_KEY_ID");
                String authority = obj.getString("AUTHORITY");

                SharedPreferences saved_values = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = saved_values.edit();

                editor.putString("userId",userId);
                editor.putString("Subdivision_Id", subId);
                editor.putString("authenticationToken", authentication_token);
                editor.putString("userName", name);
                editor.putString("div_id", divId);
                editor.putString("Cir_id", CirId);
                editor.putString("authority", authority);
                editor.putString("firstTimeAfterLogin", "1");
                editor.apply();

                SharedPreferences preferences = getSharedPreferences("credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = preferences.edit();
                editor1.putString("SignOut", "true");
                editor1.apply();


            } else if (otpResponse.equalsIgnoreCase("error")) {
                error_msg = json.getString("smsLogin");

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void sendUserDetailsServer() {
        Config userFunction1 = new Config();
        JSONObject json = userFunction1.LoginProcess(mobileNo);
        try {
            loginResponse = json.getString("result");

            if (loginResponse.equalsIgnoreCase("success")) {


            } else if (loginResponse.equalsIgnoreCase("error")) {

            }

        } catch (JSONException e) {
            e.printStackTrace();
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

                if (!(loginResponse.equals("")) && loginResponse.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), "OTP sent to your mobile number", Toast.LENGTH_SHORT).show();
                    mobileNo = mobileNumEdtTxt.getText().toString();
                    mobileNumEdtTxt.setVisibility(View.GONE);
                    otpView.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mobileNumEdtTxt.getWindowToken(), 0);

                    otpView.requestFocus();

                    submit_btn.setText("Submit");
                } else if (loginResponse.equalsIgnoreCase("error")) {
                    // Toast.makeText(getApplicationContext(),"Mobile Number Not Exists", Toast.LENGTH_SHORT).show();
                    new WarningDialogForValidation(getApplicationContext(), "Mobile Number Not Exists");

                } else {
                    Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    public void permissionToGetImage() {
        if ((ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, android.Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_IMAGE);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case REQUEST_IMAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {

                    } catch (Exception e) {

                    }

                }

                return;
            }
            default:
                try {

                    smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
                } catch (Exception e) {

                }


        }
    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }


    @Override
    protected void onStart() {

        spf = getSharedPreferences("credentials", Context.MODE_PRIVATE);

        String login = spf.getString("SignOut", "false");

        SharedPreferences preferences = getSharedPreferences("Modify", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("data", "false");

        editor.apply();

        if (login.equalsIgnoreCase("true")) {
            startActivity(new Intent(this, DashBoardActivity.class));
        }

        super.onStart();


        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
}
