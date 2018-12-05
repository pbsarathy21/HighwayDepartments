package com.spiderindia.departmentsofhighway.Fragmentss;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spiderindia.departmentsofhighway.HomeActivity;
import com.spiderindia.departmentsofhighway.JSON.Config;
import com.spiderindia.departmentsofhighway.JSON.RetrofitClient;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelAddBridgeResponse.AddBridgeResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelEdidBridgeResponse.EditBridgeResponse;
import com.spiderindia.departmentsofhighway.NewActivities.BRIDGES;
import com.spiderindia.departmentsofhighway.NewActivities.MapsActivity;
import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;
import com.spiderindia.departmentsofhighway.Utils.WarningDialogForValidation;
import com.spiderindia.departmentsofhighway.YearPicker.YearPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaintenanceFormOneFrgmnt extends Fragment {


    public MaintenanceFormOneFrgmnt() {
        // Required empty public constructor
    }

    LinearLayout progress_layout;
    ProgressBar loading_process;
    String response = "", userId, authenticationCode;
    Context mContext;

    EditText amountEdtTxt, yearEdtTxt, geo_location;
    Spinner typeSpinr, componentSpinr;
    String typeSpinrId, componentSpinrId, amount, year;
    Button previousBttn, submitButton, setLocation;

    String[] componentSpinnerArr;
    HashMap<Integer, String> componentSpinnerMap;

    String[] typeSpinnerArr;
    HashMap<Integer, String> typeSpinnerMap;
    MyDataBaseHandler dbase;
    YearPickerDialog yearPickerDialog;
    JSONObject jsonObj = null;
    SharedPreferences spf;
    String circleId = "", divisionId, subDivisionId, roadId, linkId, encodedFirstImagePath, encodedSecondImagePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_maintenance_form_one_frgmnt, container, false);

        mContext = container.getContext();

        dbase = new MyDataBaseHandler(getActivity());
        spf = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userId = spf.getString("userId", "");
        authenticationCode = spf.getString("authenticationToken", "");

        loading_process = (ProgressBar) root.findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout = (LinearLayout) root.findViewById(R.id.progress_loading_layout);

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        submitButton = (Button) root.findViewById(R.id.submit_button);
        previousBttn = (Button) root.findViewById(R.id.previous_bttn);
        yearEdtTxt = (EditText) root.findViewById(R.id.year_edttxt_maint);
        amountEdtTxt = (EditText) root.findViewById(R.id.amount_edtTxt_maint);
        typeSpinr = (Spinner) root.findViewById(R.id.type_spnr_maint);
        componentSpinr = (Spinner) root.findViewById(R.id.component_spnr_maint);

        setLocation=(Button) root.findViewById(R.id.setLocation);

        geo_location = (EditText) root.findViewById(R.id.geo_location);
        geo_location.setVisibility(View.GONE);

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             startActivity(new Intent(getActivity(), MapsActivity.class));


            }
        });




        previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ConditionFormSixFrgmnt conditionFormSixFrgmnt = new ConditionFormSixFrgmnt();

                fragmentTransaction.replace(R.id.container, conditionFormSixFrgmnt);

                fragmentTransaction.commit();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

                String data = sharedPreferences.getString("data", "false");

                spf = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

                String lat =  spf.getString("latitude", null);
                String lon =  spf.getString("longitude", null);

                if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lon))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Error in Location");
                    builder.setMessage("Please set the location again");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create();
                    builder.show();
                    return;
                }

                if (data.equalsIgnoreCase("true"))
                {
                    saveDetails();
                    updateDetails();

                } else
                {

                saveDetails();

                uploadDetails();  }
            }
        });

        //By partha


        /*previousBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity._mViewPager.setCurrentItem(10);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    amount=amountEdtTxt.getText().toString();
                    year=yearEdtTxt.getText().toString();

                    typeSpinrId = typeSpinnerMap.get(typeSpinr.getSelectedItemPosition());
                    componentSpinrId = componentSpinnerMap.get(componentSpinr.getSelectedItemPosition());

                    new  asyncToSendDetails().execute();

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
        new asyncToGetDetails().execute();

        Config con = new Config();
        submitButton.setTypeface(con.fontBold);


        yearEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearPickerDialog.show();
            }
        });
        /*year picker start*/


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);

        yearPickerDialog = new YearPickerDialog(getActivity(), calendar, new YearPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

                System.out.println("Year of construction " + dateFormat.format(calendar.getTime()));
                yearEdtTxt.setText(dateFormat.format(calendar.getTime()));

                //choosenYear=dateFormat.format(calendar.getTime())+"";

            }
        });


        /*year picker end */


        return root;
    }

    private void updateDetails() {

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        //String photo1 = "";
        //String photo2 = "";
       // String bridgeNme = "Bobby Test";
        String rec_cre_date = "";
        String rec_mod_date = "";
        String closed_date = "";
        String modified_user = "";
        String sessionId = "";
        String workflow_status = "";
        // String user_id = "86";
        // String Subdivision_Id = "375";
        //  String div_id = "201";
        //   String Cir_id = "44";
        //String Road_Id = "27761";
        //String Link_Id = "15263";
        // String User_id = "86";

     /*   String latitude = "";
        String longitude = "";*/

        String photo1 = preferences.getString("photoPath", null);
        String photo2 = preferences.getString("filePath", null);

        String bridge_key_id = preferences.getString("bridge_key_id", "14240");

        String user_id = preferences.getString("userId", null);
        String Subdivision_Id = preferences.getString("Subdivision_Id", null);
        String userName = preferences.getString("userName", null);
        String div_id = preferences.getString("div_id", null);
        String Cir_id = preferences.getString("Cir_id", null);
        String Road_Id = preferences.getString("Road_Id", null);
        String Link_Id = preferences.getString("Link_Id", null);
        String start_chainage = preferences.getString("start_chainage", "56");
        String location = preferences.getString("location", "chennai");
        String description = preferences.getString("description", "testing");
        String bridge_id = preferences.getString("bridge_id", "12345");
        String bridge_no = preferences.getString("bridge_no", "132525");
        String bridgeNme = preferences.getString("bridge", "TEST");
        String Year_cons = preferences.getString("Year_cons", "2016");
        String Cons_cost = preferences.getString("Cons_cost", null);
        String Cons_date = preferences.getString("Cons_date", "29/10/2018");
        String bridge_type = preferences.getString("bridge_type", null);
        String bridge_len = preferences.getString("bridge_len", null);
        String bridge_width = preferences.getString("bridge_width", null);
        String river_name = preferences.getString("river_name", null);
        String authority_of_river = preferences.getString("authority_of_river", null);
        String flow_of_river = preferences.getString("flow_of_river", null);
        String linear_wtr_way = preferences.getString("linear_wtr_way", null);
        String partial_widening = preferences.getString("partial_widening", null);
        String close_checkdam = preferences.getString("close_checkdam", null);
        String design_discharge = preferences.getString("design_discharge", null);
        String loading = preferences.getString("loading", null);
        String slab_design = preferences.getString("slab_design", null);
        String no_of_lanes = preferences.getString("no_of_lanes", null);
        String spans = preferences.getString("spans", null);
        String max_spans = preferences.getString("max_spans", null);
        String vertical_clearance = preferences.getString("vertical_clearance", null);
        String left_footpath = preferences.getString("left_footpath", null);
        String right_footpath = preferences.getString("right_footpath", null);
        String structure_type = preferences.getString("structure_type", null);
        String slap_thickness = preferences.getString("slap_thickness", null);
        String bearing_type = preferences.getString("bearing_type", null);
        String parapet_handrail = preferences.getString("parapet_handrail", null);
        String wearing_coat = preferences.getString("wearing_coat", null);
        String pier_foundation = preferences.getString("pier_foundation", null);
        String abutment_foundation = preferences.getString("abutment_foundation", null);
        String MLF = preferences.getString("MLF", null);
        String bank_protectType = preferences.getString("bank_protectType", null);
        String approach_type = preferences.getString("approach_type", null);
        String floor_protection = preferences.getString("floor_protection", null);
        String floor_protection_type = preferences.getString("floor_protection_type", null);
        String type_substructure = preferences.getString("type_substructure", null);
        String shape_pier = preferences.getString("shape_pier", null);
        String bridge_angle = preferences.getString("bridge_angle", null);
        String bed_level = preferences.getString("bed_level", null);
        String bed_slope = preferences.getString("bed_slope", null);
        String leftCrackUpSSpinrId = preferences.getString("leftCrackUpSSpinrId", null);
        String rightCrackUpSSpinrId = preferences.getString("rightCrackUpSSpinrId", null);
        String leftSplaityUpSSpinrId = preferences.getString("leftSplaityUpSSpinrId", null);
        String rightSplaityUpSSpinrId = preferences.getString("rightSplaityUpSSpinrId", null);
        String leftCrackDownSSpinrId = preferences.getString("leftCrackDownSSpinrId", null);
        String rightCrackDownSSpinrId = preferences.getString("rightCrackDownSSpinrId", null);
        String leftSplaityDownSSpinrId = preferences.getString("leftSplaityDownSSpinrId", null);
        String rightSplaityDownSSpinrId = preferences.getString("rightSplaityDownSSpinrId", null);
        String leftSetlemntUpsChkBxId = preferences.getString("leftSetlemntUpsChkBxId", null);
        String rightSetlemntUpSChkBxId = preferences.getString("rightSetlemntUpSChkBxId", null);
        String leftSetlemntDownSChkBxId = preferences.getString("leftSetlemntDownSChkBxId", null);
        String rightSetlemntDownSChkBxId = preferences.getString("rightSetlemntDownSChkBxId", null);
        String scourChkBxId = preferences.getString("scourChkBxId", null);
        String spalledChkBxId = preferences.getString("spalledChkBxId", null);
        String crackedChkBxId = preferences.getString("crackedChkBxId", null);
        String leftCrackUpSSpinrId1 = preferences.getString("leftCrackUpSSpinrId1", null);
        String rightCrackUpSSpinrId1 = preferences.getString("rightCrackUpSSpinrId1", null);
        String leftVegetatnId = preferences.getString("leftVegetatnId", null);
        String rightVegetationId = preferences.getString("rightVegetationId", null);
        String leftCrackDownSSpinrId1 = preferences.getString("leftCrackDownSSpinrId1", null);
        String rightCrackDownSSpinrId1 = preferences.getString("rightCrackDownSSpinrId1", null);
        String leftTiltingId = preferences.getString("leftTiltingId", null);
        String rightTiltingId = preferences.getString("rightTiltingId", null);
        String lefTspalityId = preferences.getString("lefTspalityId", null);
        String rightSpalityId = preferences.getString("rightSpalityId", null);
        String leftVegetatnDownSId = preferences.getString("leftVegetatnDownSId", null);
        String rightVegetationDownSId = preferences.getString("rightVegetationDownSId", null);
        String leftTiltingDownSId = preferences.getString("leftTiltingDownSId", null);
        String rightTiltingDownSId = preferences.getString("rightTiltingDownSId", null);
        String lefTspalityDownSId = preferences.getString("lefTspalityDownSId", null);
        String rightSpalityDownSId = preferences.getString("rightSpalityDownSId", null);
        String cracksId = preferences.getString("cracksId", null);
        String spalityId1 = preferences.getString("spalityId1", null);
        String vegetationId1 = preferences.getString("vegetationId1", null);
        String tiltingId = preferences.getString("tiltingId", null);
        String spallingSpnrSuperSId = preferences.getString("spallingSpnrSuperSId", null);
        String crackedId1 = preferences.getString("crackedId", null);
        String corrosionId = preferences.getString("corrosionId", null);
        String spalledId = preferences.getString("spalledId", null);
        String leachedId = preferences.getString("leachedId", null);
        String buckledId = preferences.getString("buckledId", null);
        String vegetationId = preferences.getString("vegetationId", null);
        String saliyoId = preferences.getString("saliyoId", null);
        String scaledId = preferences.getString("scaledId", null);
        String spalityId = preferences.getString("spalityId", null);
        String brokenHandRailsId = preferences.getString("brokenHandRailsId", null);
        String corrodedHandRailId = preferences.getString("corrodedHandRailId", null);
        String brokenFootPathId = preferences.getString("brokenFootPathId", null);
        String disintegratnNoFootId = preferences.getString("disintegratnNoFootId", null);
        String cracksSpnrId = preferences.getString("cracksSpnrId", null);
        String potholesSpnrId = preferences.getString("potholesSpnrId", null);
        String ravelledSpnrId = preferences.getString("ravelledSpnrId", null);
        String rustedSpnrId = preferences.getString("rustedSpnrId", null);
        String flatteringSpnrId = preferences.getString("flatteringSpnrId", null);
        String splitinalSpnrId = preferences.getString("splitinalSpnrId", null);
        String failedJointChkBxId = preferences.getString("failedJointChkBxId", null);
        String poorDraingeChkBxId = preferences.getString("poorDraingeChkBxId", null);
        String tiledChkBxId = preferences.getString("tiledChkBxId", null);
        String worntoutId = preferences.getString("worntoutId", null);
        String bleedId = preferences.getString("bleedId", null);
        String crackedId = preferences.getString("crackedId", null);
        String siltedId = preferences.getString("siltedId", null);
        String scourId = preferences.getString("scourId", null);
        String amount = preferences.getString("amount", null);
        String year = preferences.getString("year", null);
        String typeSpinrId = preferences.getString("typeSpinrId", null);
        String componentSpinrId = preferences.getString("componentSpinrId", null);

        String latitude = preferences.getString("latitude", null);
        String longitude = preferences.getString("longitude", null);


        File photo = new File(photo1);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("attach_photo", photo.getName(), requestBody);


        MultipartBody.Part body1 = null;

        if (!TextUtils.isEmpty(photo2))
        {
            File doc = new File(photo2);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), doc);
            body1 = MultipartBody.Part.createFormData("attach_document", doc.getName(), requestBody1);
        }

        Call<EditBridgeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .editBridgeResponse(bridge_key_id, Cir_id, div_id, Subdivision_Id, Road_Id, Link_Id, user_id,
                        location, start_chainage, bridge_id, bridgeNme, bridge_no, Year_cons, Cons_cost, body,
                        body1, bridge_type, bridge_len, loading, partial_widening, linear_wtr_way, close_checkdam,
                        bridge_width, river_name, flow_of_river, authority_of_river,
                        slab_design, no_of_lanes, spans, max_spans, vertical_clearance, left_footpath, right_footpath,
                        structure_type, slap_thickness, bearing_type, parapet_handrail,
                        wearing_coat, pier_foundation, abutment_foundation, MLF, approach_type, floor_protection,
                        floor_protection_type, bridge_angle, bank_protectType, design_discharge,
                        leftCrackUpSSpinrId, rightCrackUpSSpinrId, leftCrackDownSSpinrId, rightCrackDownSSpinrId,
                        leftSetlemntUpsChkBxId, rightSetlemntUpSChkBxId, leftSetlemntDownSChkBxId,
                        rightSetlemntDownSChkBxId, leftSplaityUpSSpinrId, rightSplaityUpSSpinrId, leftSplaityDownSSpinrId,
                        rightSplaityDownSSpinrId, scourChkBxId, spalledChkBxId,
                        crackedChkBxId, leftCrackUpSSpinrId1, rightCrackUpSSpinrId1, leftCrackDownSSpinrId1, rightCrackDownSSpinrId1,
                        leftVegetatnId, rightVegetationId, leftVegetatnDownSId,
                        rightVegetationDownSId, leftTiltingId, rightTiltingId, leftTiltingDownSId, rightTiltingDownSId, lefTspalityId,
                        rightSpalityId, lefTspalityDownSId, rightSpalityDownSId,
                        cracksId, vegetationId, tiltingId, spalityId1, crackedId1, leachedId, saliyoId, spalledId, corrosionId, buckledId,
                        spalityId, vegetationId1, scaledId, brokenHandRailsId,
                        corrodedHandRailId, spallingSpnrSuperSId, brokenFootPathId, disintegratnNoFootId, cracksSpnrId, potholesSpnrId,
                        ravelledSpnrId, failedJointChkBxId, poorDraingeChkBxId,
                        rustedSpnrId, tiledChkBxId, flatteringSpnrId, splitinalSpnrId, worntoutId, bleedId, crackedId, siltedId,
                        scourId, bed_level, bed_slope, closed_date, workflow_status, sessionId,
                        Cons_date, type_substructure, shape_pier, description, latitude, longitude);

        call.enqueue(new Callback<EditBridgeResponse>() {
            @Override
            public void onResponse(Call<EditBridgeResponse> call, Response<EditBridgeResponse> response) {


                if (response.code() == 200)
                {
                    EditBridgeResponse editBridgeResponse = response.body();
                    if (!editBridgeResponse.getMessage().isEmpty())
                    {
                        Toast.makeText(getActivity(), ""+editBridgeResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(editBridgeResponse.getResult());
                        builder.setMessage(editBridgeResponse.getMessage());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                               SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

                                preferences.edit().remove("latitude").apply();
                                preferences.edit().remove("longitude").apply();

                               getActivity().finish();
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), response.code()+" : Response body Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), response.code()+" : Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<EditBridgeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadDetails() {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        //String photo1 = "";
        //String photo2 = "";
       // String bridgeNme = "Bobby Test";
        String rec_cre_date = "";
        String rec_mod_date = "";
        String closed_date = "";
        String modified_user = "";
        String sessionId = "";
        String workflow_status = "";
        // String user_id = "86";
        // String Subdivision_Id = "375";
        //  String div_id = "201";
        //   String Cir_id = "44";
        //String Road_Id = "27761";
        //String Link_Id = "15263";
        // String User_id = "86";

        String latitude = "";
        String longitude = "";

        String photo1 = preferences.getString("photoPath", null);
        String photo2 = preferences.getString("filePath", null);

        String bridge_key_id = preferences.getString("bridge_key_id", "14240");

        String user_id = preferences.getString("userId", null);
        String Subdivision_Id = preferences.getString("Subdivision_Id", null);
        String userName = preferences.getString("userName", null);
        String div_id = preferences.getString("div_id", null);
        String Cir_id = preferences.getString("Cir_id", null);
        String Road_Id = preferences.getString("Road_Id", null);
        String Link_Id = preferences.getString("Link_Id", null);
        String start_chainage = preferences.getString("start_chainage", "56");
        String location = preferences.getString("location", "chennai");
        String description = preferences.getString("description", "testing");
        String bridge_id = preferences.getString("bridge_id", "12345");
        String bridge_no = preferences.getString("bridge_no", "132525");
        String bridgeNme = preferences.getString("bridge", "TEST");
        String Year_cons = preferences.getString("Year_cons", "2016");
        String Cons_cost = preferences.getString("Cons_cost", null);
        String Cons_date = preferences.getString("Cons_date", "29/10/2018");
        String bridge_type = preferences.getString("bridge_type", null);
        String bridge_len = preferences.getString("bridge_len", null);
        String bridge_width = preferences.getString("bridge_width", null);
        String river_name = preferences.getString("river_name", null);
        String authority_of_river = preferences.getString("authority_of_river", null);
        String flow_of_river = preferences.getString("flow_of_river", null);
        String linear_wtr_way = preferences.getString("linear_wtr_way", null);
        String partial_widening = preferences.getString("partial_widening", null);
        String close_checkdam = preferences.getString("close_checkdam", null);
        String design_discharge = preferences.getString("design_discharge", null);
        String loading = preferences.getString("loading", null);
        String slab_design = preferences.getString("slab_design", null);
        String no_of_lanes = preferences.getString("no_of_lanes", null);
        String spans = preferences.getString("spans", null);
        String max_spans = preferences.getString("max_spans", null);
        String vertical_clearance = preferences.getString("vertical_clearance", null);
        String left_footpath = preferences.getString("left_footpath", null);
        String right_footpath = preferences.getString("right_footpath", null);
        String structure_type = preferences.getString("structure_type", null);
        String slap_thickness = preferences.getString("slap_thickness", null);
        String bearing_type = preferences.getString("bearing_type", null);
        String parapet_handrail = preferences.getString("parapet_handrail", null);
        String wearing_coat = preferences.getString("wearing_coat", null);
        String pier_foundation = preferences.getString("pier_foundation", null);
        String abutment_foundation = preferences.getString("abutment_foundation", null);
        String MLF = preferences.getString("MLF", null);
        String bank_protectType = preferences.getString("bank_protectType", null);
        String approach_type = preferences.getString("approach_type", null);
        String floor_protection = preferences.getString("floor_protection", null);
        String floor_protection_type = preferences.getString("floor_protection_type", null);
        String type_substructure = preferences.getString("type_substructure", null);
        String shape_pier = preferences.getString("shape_pier", null);
        String bridge_angle = preferences.getString("bridge_angle", null);
        String bed_level = preferences.getString("bed_level", null);
        String bed_slope = preferences.getString("bed_slope", null);
        String leftCrackUpSSpinrId = preferences.getString("leftCrackUpSSpinrId", null);
        String rightCrackUpSSpinrId = preferences.getString("rightCrackUpSSpinrId", null);
        String leftSplaityUpSSpinrId = preferences.getString("leftSplaityUpSSpinrId", null);
        String rightSplaityUpSSpinrId = preferences.getString("rightSplaityUpSSpinrId", null);
        String leftCrackDownSSpinrId = preferences.getString("leftCrackDownSSpinrId", null);
        String rightCrackDownSSpinrId = preferences.getString("rightCrackDownSSpinrId", null);
        String leftSplaityDownSSpinrId = preferences.getString("leftSplaityDownSSpinrId", null);
        String rightSplaityDownSSpinrId = preferences.getString("rightSplaityDownSSpinrId", null);
        String leftSetlemntUpsChkBxId = preferences.getString("leftSetlemntUpsChkBxId", null);
        String rightSetlemntUpSChkBxId = preferences.getString("rightSetlemntUpSChkBxId", null);
        String leftSetlemntDownSChkBxId = preferences.getString("leftSetlemntDownSChkBxId", null);
        String rightSetlemntDownSChkBxId = preferences.getString("rightSetlemntDownSChkBxId", null);
        String scourChkBxId = preferences.getString("scourChkBxId", null);
        String spalledChkBxId = preferences.getString("spalledChkBxId", null);
        String crackedChkBxId = preferences.getString("crackedChkBxId", null);
        String leftCrackUpSSpinrId1 = preferences.getString("leftCrackUpSSpinrId1", null);
        String rightCrackUpSSpinrId1 = preferences.getString("rightCrackUpSSpinrId1", null);
        String leftVegetatnId = preferences.getString("leftVegetatnId", null);
        String rightVegetationId = preferences.getString("rightVegetationId", null);
        String leftCrackDownSSpinrId1 = preferences.getString("leftCrackDownSSpinrId1", null);
        String rightCrackDownSSpinrId1 = preferences.getString("rightCrackDownSSpinrId1", null);
        String leftTiltingId = preferences.getString("leftTiltingId", null);
        String rightTiltingId = preferences.getString("rightTiltingId", null);
        String lefTspalityId = preferences.getString("lefTspalityId", null);
        String rightSpalityId = preferences.getString("rightSpalityId", null);
        String leftVegetatnDownSId = preferences.getString("leftVegetatnDownSId", null);
        String rightVegetationDownSId = preferences.getString("rightVegetationDownSId", null);
        String leftTiltingDownSId = preferences.getString("leftTiltingDownSId", null);
        String rightTiltingDownSId = preferences.getString("rightTiltingDownSId", null);
        String lefTspalityDownSId = preferences.getString("lefTspalityDownSId", null);
        String rightSpalityDownSId = preferences.getString("rightSpalityDownSId", null);
        String cracksId = preferences.getString("cracksId", null);
        String spalityId1 = preferences.getString("spalityId1", null);
        String vegetationId1 = preferences.getString("vegetationId1", null);
        String tiltingId = preferences.getString("tiltingId", null);
        String spallingSpnrSuperSId = preferences.getString("spallingSpnrSuperSId", null);
        String crackedId1 = preferences.getString("crackedId", null);
        String corrosionId = preferences.getString("corrosionId", null);
        String spalledId = preferences.getString("spalledId", null);
        String leachedId = preferences.getString("leachedId", null);
        String buckledId = preferences.getString("buckledId", null);
        String vegetationId = preferences.getString("vegetationId", null);
        String saliyoId = preferences.getString("saliyoId", null);
        String scaledId = preferences.getString("scaledId", null);
        String spalityId = preferences.getString("spalityId", null);
        String brokenHandRailsId = preferences.getString("brokenHandRailsId", null);
        String corrodedHandRailId = preferences.getString("corrodedHandRailId", null);
        String brokenFootPathId = preferences.getString("brokenFootPathId", null);
        String disintegratnNoFootId = preferences.getString("disintegratnNoFootId", null);
        String cracksSpnrId = preferences.getString("cracksSpnrId", null);
        String potholesSpnrId = preferences.getString("potholesSpnrId", null);
        String ravelledSpnrId = preferences.getString("ravelledSpnrId", null);
        String rustedSpnrId = preferences.getString("rustedSpnrId", null);
        String flatteringSpnrId = preferences.getString("flatteringSpnrId", null);
        String splitinalSpnrId = preferences.getString("splitinalSpnrId", null);
        String failedJointChkBxId = preferences.getString("failedJointChkBxId", null);
        String poorDraingeChkBxId = preferences.getString("poorDraingeChkBxId", null);
        String tiledChkBxId = preferences.getString("tiledChkBxId", null);
        String worntoutId = preferences.getString("worntoutId", null);
        String bleedId = preferences.getString("bleedId", null);
        String crackedId = preferences.getString("crackedId", null);
        String siltedId = preferences.getString("siltedId", null);
        String scourId = preferences.getString("scourId", null);
        String amount = preferences.getString("amount", null);
        String year = preferences.getString("year", null);
        String typeSpinrId = preferences.getString("typeSpinrId", null);
        String componentSpinrId = preferences.getString("componentSpinrId", null);


        File photo = new File(photo1);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photo);
        MultipartBody.Part body = MultipartBody.Part.createFormData("attach_photo", photo.getName(), requestBody);


        File doc = new File(photo2);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), doc);
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("attach_document", doc.getName(), requestBody1);


        Call<AddBridgeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .addBridgeDetails(Cir_id, div_id, Subdivision_Id, Road_Id, Link_Id, user_id,
                        location, start_chainage, bridge_id, bridgeNme, bridge_no, Year_cons, Cons_cost, body,
                        body1, bridge_type, bridge_len, loading, partial_widening, linear_wtr_way, close_checkdam,
                        bridge_width, river_name, flow_of_river, authority_of_river,
                        slab_design, no_of_lanes, spans, max_spans, vertical_clearance, left_footpath, right_footpath,
                        structure_type, slap_thickness, bearing_type, parapet_handrail,
                        wearing_coat, pier_foundation, abutment_foundation, MLF, approach_type, floor_protection,
                        floor_protection_type, bridge_angle, bank_protectType, design_discharge,
                        leftCrackUpSSpinrId, rightCrackUpSSpinrId, leftCrackDownSSpinrId, rightCrackDownSSpinrId,
                        leftSetlemntUpsChkBxId, rightSetlemntUpSChkBxId, leftSetlemntDownSChkBxId,
                        rightSetlemntDownSChkBxId, leftSplaityUpSSpinrId, rightSplaityUpSSpinrId, leftSplaityDownSSpinrId,
                        rightSplaityDownSSpinrId, scourChkBxId, spalledChkBxId,
                        crackedChkBxId, leftCrackUpSSpinrId1, rightCrackUpSSpinrId1, leftCrackDownSSpinrId1, rightCrackDownSSpinrId1,
                        leftVegetatnId, rightVegetationId, leftVegetatnDownSId,
                        rightVegetationDownSId, leftTiltingId, rightTiltingId, leftTiltingDownSId, rightTiltingDownSId, lefTspalityId,
                        rightSpalityId, lefTspalityDownSId, rightSpalityDownSId,
                        cracksId, vegetationId, tiltingId, spalityId1, crackedId1, leachedId, saliyoId, spalledId, corrosionId, buckledId,
                        spalityId, vegetationId1, scaledId, brokenHandRailsId,
                        corrodedHandRailId, spallingSpnrSuperSId, brokenFootPathId, disintegratnNoFootId, cracksSpnrId, potholesSpnrId,
                        ravelledSpnrId, failedJointChkBxId, poorDraingeChkBxId,
                        rustedSpnrId, tiledChkBxId, flatteringSpnrId, splitinalSpnrId, worntoutId, bleedId, crackedId, siltedId,
                        scourId, bed_level, bed_slope, closed_date, workflow_status, sessionId,
                        Cons_date, type_substructure, shape_pier, description, latitude, longitude);

        call.enqueue(new Callback<AddBridgeResponse>() {
            @Override
            public void onResponse(Call<AddBridgeResponse> call, Response<AddBridgeResponse> response) {


                if (response.code() == 200)
                {
                    AddBridgeResponse addBridgeResponse = response.body();
                    if (!addBridgeResponse.getMessage().isEmpty())
                    {
                        Toast.makeText(getActivity(), ""+addBridgeResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(addBridgeResponse.getResult());
                        builder.setMessage(addBridgeResponse.getMessage());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);

                                preferences.edit().remove("latitude").apply();
                                preferences.edit().remove("longitude").apply();

                                getActivity().finish();
                            }
                        });

                        builder.create();
                        builder.show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), response.code()+" : Response body Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), response.code()+" : Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddBridgeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void saveDetails() {

        amount = amountEdtTxt.getText().toString();
        year = yearEdtTxt.getText().toString();



        typeSpinrId = typeSpinnerMap.get(typeSpinr.getSelectedItemPosition());
        componentSpinrId = componentSpinnerMap.get(componentSpinr.getSelectedItemPosition());


        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("amount",amount );
        editor.putString("year",year );
        editor.putString("typeSpinrId",typeSpinrId );
        editor.putString("componentSpinrId",componentSpinrId );

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

                Cursor cur=dbase.getAllDetails(dbase.TABLE_NAME_MAINTAINANCE,dbase.MAINTAINANCE_ID);
                Log.v("Cursor Object form two", DatabaseUtils.dumpCursorToString(cur));

                if (cur.moveToFirst())
                {
                    do {
                        //id from 1
                        year=cur.getString(1).toString();
                        typeSpinrId=cur.getString(2).toString();
                        componentSpinrId=cur.getString(3).toString();
                        amount=cur.getString(4).toString();
                        //4

                    }while (cur.moveToNext());
                }



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

                final ArrayAdapter<String> adapter_linkId = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, componentSpinnerArr);
                adapter_linkId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                componentSpinr.setAdapter(adapter_linkId);

                final ArrayAdapter<String> adapter_type = new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner, typeSpinnerArr);
                adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinr.setAdapter(adapter_type);

                componentSpinr.setSelection(Integer.parseInt(componentSpinrId));
                typeSpinr.setSelection(Integer.parseInt(typeSpinrId));

                yearEdtTxt.setText(year);
                amountEdtTxt.setText(amount);


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
    void  sendUserDetailsServer()
    {
        componentSpinnerArr = new String[8];
        typeSpinnerArr = new String[4];

        componentSpinnerMap = new HashMap<Integer, String>();
        typeSpinnerMap = new HashMap<Integer, String>();
        for(int i=0;i<8;i++)
        {
            String id = "0";
            if(i==0)
            {
                id="0";
                componentSpinnerArr[0]="Select";
                typeSpinnerArr[0]="Select";

            }

            if(i==1)
            {
                id="1";
                componentSpinnerArr[1]="Slab";
                typeSpinnerArr[1]="Routine";
            }

            if(i==2)
            {
                id="2";
                componentSpinnerArr[2]="Abutment";
                typeSpinnerArr[2]="Minor Repair";
            }

            if(i==3)
            {
                id="3";
                componentSpinnerArr[3]="Pier";
                typeSpinnerArr[3]="Major Repair";
            }

            if(i==4)
            {
                id="4";
                componentSpinnerArr[4]="Bed";
            }

            if(i==5)
            {
                id="5";
                componentSpinnerArr[5]="Vent";
            }

            if(i==6)
            {
                id="6";
                componentSpinnerArr[6]="Parapet";
            }

            if(i==7)
            {
                id="7";
                componentSpinnerArr[7]="Bearings";
            }

            componentSpinnerMap.put(i,id);
            typeSpinnerMap.put(i,id);

        }

    }
    class asyncToSendDetails extends AsyncTask<Void, Void, String> {
        private String Error = null;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            progress_layout.setVisibility(View.VISIBLE);


            //id from 1
            ContentValues maintainanceCon=new ContentValues();

            maintainanceCon.put(dbase.YEAR_MH,year);
            maintainanceCon.put(dbase.TYPE_MH,typeSpinrId);
            maintainanceCon.put(dbase.COMPONENT_MH,componentSpinrId);
            maintainanceCon.put(dbase.AMOUNT_MH,amount);

            //4

            boolean updateStatus=dbase.UpdateDetails(maintainanceCon,dbase.TABLE_NAME_MAINTAINANCE,dbase.MAINTAINANCE_ID);

            System.out.println("updateStatus inventaryCon "+updateStatus);


        }

        @Override
        protected String doInBackground(Void... unsued) {
            try {


                Cursor addBridgeCur=dbase.getAllDetails(dbase.TABLE_NAME_BRIDGE,dbase.BRIDGE_ID);


                if (addBridgeCur.moveToFirst())
                {
                    do {

                        circleId=addBridgeCur.getString(1).toString();
                        divisionId=addBridgeCur.getString(2).toString();
                        subDivisionId=addBridgeCur.getString(3).toString();
                        roadId=addBridgeCur.getString(4).toString();
                        linkId=addBridgeCur.getString(5).toString();
                        encodedFirstImagePath=addBridgeCur.getString(18).toString();
                        encodedSecondImagePath=addBridgeCur.getString(19).toString();

                    }while (addBridgeCur.moveToNext());
                }

                Cursor inventoryCur=dbase.getAllDetails(dbase.TABLE_NAME_INVENTARY,dbase.INVENTARY_ID_I);
                Cursor conditionCur=dbase.getAllDetails(dbase.TABLE_NAME_CONDITION,dbase.CONDITION_ID);
                Cursor maintainanceCur=dbase.getAllDetails(dbase.TABLE_NAME_MAINTAINANCE,dbase.MAINTAINANCE_ID);

                JSONArray addBridgeJArray=cur2Json(addBridgeCur);
                JSONArray inventoryJArray=cur2Json(inventoryCur);
                JSONArray conditionJArray=cur2Json(conditionCur);
                JSONArray maintainanceJArray=cur2Json(maintainanceCur);

                jsonObj = new JSONObject();
                try {

                    jsonObj.put("addBridge", addBridgeJArray);
                    jsonObj.put("inventory", inventoryJArray);
                    jsonObj.put("condition", conditionJArray);
                    jsonObj.put("maintainance", maintainanceJArray);

                    Log.v("jArray obj",jsonObj+"");
                    System.out.println("jArray obj.."+jsonObj);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if(jsonObj!=null)
                {
                    Config userFunction1 = new Config();
                    JSONObject json = userFunction1.sendBridgeDetailsToServer(userId,authenticationCode,circleId,divisionId,subDivisionId,roadId,
                            linkId,encodedFirstImagePath,encodedSecondImagePath,jsonObj);

                    response=json.getString("success");


                }
                else {

                    Toast.makeText(getActivity(),"Something error",Toast.LENGTH_SHORT).show();
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
                    dbase.emptyTable(dbase.TABLE_NAME_BRIDGE);
                    dbase.emptyTable(dbase.TABLE_NAME_INVENTARY);
                    dbase.emptyTable(dbase.TABLE_NAME_CONDITION);
                    dbase.emptyTable(dbase.TABLE_NAME_MAINTAINANCE);
                    dbase.emptyTable(dbase.TABLE_NAME_CIRCLE);

                    successPopup("Details Updated Successfully..!");

                }
                else if(!(response.equals("")) && response.equalsIgnoreCase("TRUE"))
                {

                    new WarningDialogForValidation(getActivity(),"Failed to Update.");
                }
                else
                {
                    new WarningDialogForValidation(getActivity(),"Somthing went wrong..!");
                }

            } catch (Exception e) {

                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
    public JSONArray cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        Log.d("Cursor exception", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }
/*
    public JSONArray cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        Log.d("Cursor exception", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }
*/

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

             SharedPreferences spref = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
             SharedPreferences.Editor editor = spref.edit();
             editor.putString("firstTimeAfterLogin", "0");
             editor.commit();

             Intent i=new Intent(getActivity(),HomeActivity.class);
             startActivity(i);

         }
     });
 }
}
