package com.spiderindia.departmentsofhighway.NewActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.Adapter.BridgeAdapter;
import com.spiderindia.departmentsofhighway.JSON.RetrofitClient;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.BridgeResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCircleResponse.CircleResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelDivisionResponse.DivisionResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelLinkResponse.LinkResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelRoadResponse.RoadResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelSubdivisionResponse.SubdivisionResponse;
import com.spiderindia.departmentsofhighway.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BRIDGES extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Spinner spinnerRoad, spinnerLink, spinnerCircle, spinnerDivision, spinnerSubdivision;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    ArrayList<String> roadarraylist;
    ArrayList<String> linkArraylist;
    ArrayList<String> circleArrayList;
    ArrayList<String> divisionArrayList;
    ArrayList<String> subdivisionArrayList;

    String RoadKeyId, UserId, SubDivisionId,
            RoadId, Link_name, LinkStartPlace,
            LinkEndPlace, LinkId,  RoadStartPlace,
            RoadEndPlace;

    BridgeAdapter bridgeAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    LinearLayout circlelayout, divisionLayout, subdivisionLayout;

    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String authority = sharedPreferences.getString("authority", "4");

        if (authority.equalsIgnoreCase("1"))
        {
            loadCircleSpinner();
            return;
        }

        if (authority.equalsIgnoreCase("2"))
        {
            circlelayout.setVisibility(View.GONE);
            loadDivisionSpinner();
            return;
        }

        if (authority.equalsIgnoreCase("3"))
        {
            circlelayout.setVisibility(View.GONE);
            divisionLayout.setVisibility(View.GONE);
            loadSubdivisionSpinner();
            return;
        }

        if (authority.equalsIgnoreCase("4"))
        {
            circlelayout.setVisibility(View.GONE);
            divisionLayout.setVisibility(View.GONE);
            subdivisionLayout.setVisibility(View.GONE);
            loadRoadSpinner();

        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String authority = sharedPreferences.getString("authority", "4");

        if (authority.equalsIgnoreCase("1"))
        {
            loadCircleSpinner();
            return;
        }

        if (authority.equalsIgnoreCase("2"))
        {
            circlelayout.setVisibility(View.GONE);
            loadDivisionSpinner();
            return;
        }

        if (authority.equalsIgnoreCase("3"))
        {
            circlelayout.setVisibility(View.GONE);
            divisionLayout.setVisibility(View.GONE);
            return;
        }

        if (authority.equalsIgnoreCase("4"))
        {
            circlelayout.setVisibility(View.GONE);
            divisionLayout.setVisibility(View.GONE);
            subdivisionLayout.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridges);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

       sharedPreferences = getSharedPreferences("Modify", MODE_PRIVATE);

       editor = sharedPreferences.edit();

        editor.putString("data","false");

        editor.apply();

        roadarraylist = new ArrayList<>();
        linkArraylist = new ArrayList<>();
        circleArrayList = new ArrayList<>();
        divisionArrayList = new ArrayList<>();
        subdivisionArrayList = new ArrayList<>();

        toolbar = findViewById(R.id.tool_bar);
        spinnerRoad = findViewById(R.id.road);
        spinnerLink = findViewById(R.id.linkId);
        spinnerCircle = findViewById(R.id.circle);
        spinnerDivision = findViewById(R.id.division);
        spinnerSubdivision = findViewById(R.id.subdivision);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);

        circlelayout = findViewById(R.id.circle_layout);
        divisionLayout = findViewById(R.id.division_layout);
        subdivisionLayout = findViewById(R.id.subdivision_layout);

        spinnerRoad.setOnItemSelectedListener(this);
        spinnerLink.setOnItemSelectedListener(this);
        spinnerCircle.setOnItemSelectedListener(this);
        spinnerDivision.setOnItemSelectedListener(this);
        spinnerSubdivision.setOnItemSelectedListener(this);

        SharedPreferences preferences = getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        String Userid = preferences.getString("userId", "0");
        String Subdivid = preferences.getString("Subdivision_Id", "0");

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), position + " is selected!", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences("Modify", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("data", "true");
                editor.apply();

                Intent intent = new Intent(BRIDGES.this,NewBridgeForm.class);
                intent.putExtra("Position", position);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("Modify", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("data", "false");
                editor.putBoolean("preload", false);
                editor.apply();

                startActivity(new Intent(BRIDGES.this,NewBridgeForm.class));
            }
        });


        Toast.makeText(this, Userid, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Subdivid, Toast.LENGTH_SHORT).show();

       // loadRoadSpinner(Userid, Subdivid);

      /*  spinnerRoad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RoadId = adapterView.getItemAtPosition(i).toString().substring(0,7).trim();

                //Toast.makeText(BRIDGES.this, RoadId, Toast.LENGTH_SHORT).show();
                linkArraylist.clear();

               // loadLinkSpinner(RoadId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
     /*   spinnerLink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //  LinkId = adapterView.getItemAtPosition(i).toString().substring(0,13);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


    }

    private void loadRecyclerView(String linkId) {

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("link_id", linkId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<BridgeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getBridgeDetails(paramObject.toString());

        call.enqueue(new Callback<BridgeResponse>() {
            @Override
            public void onResponse(Call<BridgeResponse> call, Response<BridgeResponse> response) {
              //  Toast.makeText(BRIDGES.this,"Success...",Toast.LENGTH_LONG).show();
                BridgeResponse bridgeResponse = response.body();

                List<DataItem> arrayLists = bridgeResponse.getResult().getData();


                if (arrayLists.isEmpty()) {

                    Toast.makeText(BRIDGES.this, "There are no bridge details", Toast.LENGTH_SHORT).show();

                } else {
                       String Road_Id = bridgeResponse.getResult().getData().get(0).getROADKEYID();
                    String Link_Id = bridgeResponse.getResult().getData().get(0).getLINKKEYID();

                    SharedPreferences preferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Road_Id", Road_Id);
                    editor.putString("Link_Id", Link_Id);

                    editor.apply();
                }



                bridgeAdapter = new BridgeAdapter(bridgeResponse.getResult().getData(), BRIDGES.this);
                recyclerView.setAdapter(bridgeAdapter);

            }

            @Override
            public void onFailure(Call<BridgeResponse> call, Throwable t) {

                Toast.makeText(BRIDGES.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }





    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int id = adapterView.getId();

        if (id == R.id.circle)
        {
            String circleId = adapterView.getItemAtPosition(i).toString().substring(0,2).trim();

            sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("Cir_id", circleId);
            editor.apply();

            loadDivisionSpinner();

            return;
        }

        if (id == R.id.division)
        {
            String divisionId = adapterView.getItemAtPosition(i).toString().substring(0,3).trim();

            sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("div_id", divisionId);
            editor.apply();

            loadSubdivisionSpinner();

            return;
        }

        if (id == R.id.subdivision)
        {
            String subdivId = adapterView.getItemAtPosition(i).toString().substring(0,3).trim();

            sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("Subdivision_Id", subdivId);
            editor.apply();

            loadRoadSpinner();

            return;
        }

        if (id == R.id.road)
        {

            RoadId = adapterView.getItemAtPosition(i).toString().substring(0,8).trim();

            Toast.makeText(BRIDGES.this, RoadId, Toast.LENGTH_SHORT).show();
            linkArraylist.clear();

            loadLinkSpinner(RoadId);
            return;
        }

        if (id == R.id.linkId)
        {
            LinkId = adapterView.getItemAtPosition(i).toString();

            loadRecyclerView(LinkId);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private void loadCircleSpinner() {

        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        String Userid = preferences.getString("userId", "0");

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("user_id", Userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<CircleResponse> circleResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .getCircleDetails(paramObject.toString());

        circleResponseCall.enqueue(new Callback<CircleResponse>() {
            @Override
            public void onResponse(Call<CircleResponse> call, Response<CircleResponse> response) {
                progressDialog.dismiss();

                CircleResponse circleResponse = response.body();

                circleArrayList.clear();

                for (int i = 0; i< circleResponse.getData().size(); i++)
                {
                    String circleID = circleResponse.getData().get(i).getId();
                    String circleName = circleResponse.getData().get(i).getName();
                    circleArrayList.add(circleID + " (" + circleName + ")");
                }

                if (!circleArrayList.isEmpty())
                {
                    final ArrayAdapter<String> spinner_circle_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, circleArrayList);
                    spinner_circle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCircle.setAdapter(spinner_circle_adapter);
                }

            }

            @Override
            public void onFailure(@NonNull Call<CircleResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BRIDGES.this, "circle : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadDivisionSpinner() {

        progressDialog.show();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String circleID = sharedPreferences.getString("Cir_id", "44");

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("circle_key_id", circleID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<DivisionResponse> divisionResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .getDivisionDetails(paramObject.toString());

        divisionResponseCall.enqueue(new Callback<DivisionResponse>() {
            @Override
            public void onResponse(Call<DivisionResponse> call, Response<DivisionResponse> response) {
                progressDialog.dismiss();

                DivisionResponse divisionResponse = response.body();

                divisionArrayList.clear();

                for (int i = 0; i< divisionResponse.getData().size(); i++)
                {
                    String divisionID = divisionResponse.getData().get(i).getDIVISIONKEYID();
                    String divisionName = divisionResponse.getData().get(i).getDIVISIONNAME();
                    divisionArrayList.add(divisionID + " (" + divisionName + ")");
                }

                if (!divisionArrayList.isEmpty())
                {
                    final ArrayAdapter<String> spinner_division_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, divisionArrayList);
                    spinner_division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDivision.setAdapter(spinner_division_adapter);
                }
                else
                {
                    divisionArrayList.add("None");

                    final ArrayAdapter<String> spinner_division_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, divisionArrayList);
                    spinner_division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDivision.setAdapter(spinner_division_adapter);
                }
            }

            @Override
            public void onFailure(Call<DivisionResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BRIDGES.this, "division"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void loadSubdivisionSpinner() {

        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("UserDetails",Context.MODE_PRIVATE);
        String divisionId = preferences.getString("div_id", "0");

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("division_key_id", divisionId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<SubdivisionResponse> subdivisionResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .getSubdivisionDetails(paramObject.toString());

        subdivisionResponseCall.enqueue(new Callback<SubdivisionResponse>() {
            @Override
            public void onResponse(Call<SubdivisionResponse> call, Response<SubdivisionResponse> response) {
                progressDialog.dismiss();

                SubdivisionResponse subdivisionResponse = response.body();

                subdivisionArrayList.clear();

                for (int i = 0; i< subdivisionResponse.getData().size(); i++)
                {
                    String subdivisionID = subdivisionResponse.getData().get(i).getSUBDIVISIONKEYID();
                    String subdivisionName = subdivisionResponse.getData().get(i).getSUBDIVISIONNAME();
                    subdivisionArrayList.add(subdivisionID + " (" + subdivisionName + ")");
                }

                if (!subdivisionArrayList.isEmpty())
                {
                    final ArrayAdapter<String> spinner_subdivision_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, subdivisionArrayList);
                    spinner_subdivision_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubdivision.setAdapter(spinner_subdivision_adapter);
                }
                else
                {
                    subdivisionArrayList.add("None");

                    final ArrayAdapter<String> spinner_subdivision_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, subdivisionArrayList);
                    spinner_subdivision_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubdivision.setAdapter(spinner_subdivision_adapter);
                }
            }

            @Override
            public void onFailure(Call<SubdivisionResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BRIDGES.this, "Subdivision "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loadRoadSpinner() {

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);

        String Userid = sharedPreferences.getString("userId", "0");
        String Subdivid = sharedPreferences.getString("Subdivision_Id", "0");

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("user_id", Userid);
            paramObject.put("subdivision_id", Subdivid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<RoadResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getRoadDetails(paramObject.toString());

        call.enqueue(new Callback<RoadResponse>() {
            @Override
            public void onResponse(Call<RoadResponse> call, Response<RoadResponse> response) {
                RoadResponse roadResponse = response.body();

                roadarraylist.clear();
                //Toast.makeText(BRIDGES.this, "success", Toast.LENGTH_LONG).show();
                for (int i = 0; i < roadResponse.getResult().getData().size(); i++) {
                    //  RoadKeyId = roadResponse.getResult().getData().get(i).getROADKEYID();
                    String name = roadResponse.getResult().getData().get(i).getnAME();
                    RoadStartPlace = roadResponse.getResult().getData().get(i).getSTARTPLACE();
                    RoadEndPlace = roadResponse.getResult().getData().get(i).getENDPLACE();
                    roadarraylist.add(name + "    (" + RoadStartPlace + "-" + RoadEndPlace + ")");

                }
                /*spinnerRoad.setAdapter(new ArrayAdapter<String>(BRIDGES.this, R.layout.support_simple_spinner_dropdown_item, roadarraylist));*/

                if (!roadarraylist.isEmpty())
                {
                    final ArrayAdapter<String> spinner_road_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, roadarraylist);
                    spinner_road_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRoad.setAdapter(spinner_road_adapter);
                }

                else
                {
                    roadarraylist.add("None");

                    final ArrayAdapter<String> spinner_road_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, roadarraylist);
                    spinner_road_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerRoad.setAdapter(spinner_road_adapter);
                }


            }

            @Override
            public void onFailure(Call<RoadResponse> call, Throwable t) {
                Toast.makeText(BRIDGES.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadLinkSpinner(String Roadid) {

        //Toast.makeText(this, RoadId, Toast.LENGTH_SHORT).show();

        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("name", Roadid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<LinkResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getLinkDetails(paramObject.toString());

        call.enqueue(new Callback<LinkResponse>() {
            @Override
            public void onResponse(Call<LinkResponse> call, Response<LinkResponse> response) {
                LinkResponse linkResponse = response.body();
                // Toast.makeText(BRIDGES.this, "success", Toast.LENGTH_LONG).show();
                for (int i = 0; i < linkResponse.getResult().getData().size(); i++) {
                    Link_name = linkResponse.getResult().getData().get(i).getLINKID();
                    LinkStartPlace = linkResponse.getResult().getData().get(i).getSTARTPLACE();
                    LinkEndPlace = linkResponse.getResult().getData().get(i).getENDPLACE();
                    // linkArraylist.add(Link_name + " (" + LinkStartPlace + "-" + LinkEndPlace + ")");
                    linkArraylist.add(Link_name);

                }
                /* spinnerLink.setAdapter(new ArrayAdapter<String>(BRIDGES.this, R.layout.support_simple_spinner_dropdown_item, linkArraylist));*/

                if (!linkArraylist.isEmpty())
                {
                    final ArrayAdapter<String> spinner_link_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, linkArraylist);
                    spinner_link_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerLink.setAdapter(spinner_link_adapter);
                }

                else
                {
                    linkArraylist.add("None");

                    final ArrayAdapter<String> spinner_link_adapter = new ArrayAdapter<String>(BRIDGES.this,R.layout.custom_spinner, linkArraylist);
                    spinner_link_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerLink.setAdapter(spinner_link_adapter);
                }


            }

            @Override
            public void onFailure(Call<LinkResponse> call, Throwable t) {
                Toast.makeText(BRIDGES.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

}
