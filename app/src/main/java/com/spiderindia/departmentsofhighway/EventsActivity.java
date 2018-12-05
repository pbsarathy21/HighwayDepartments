package com.spiderindia.departmentsofhighway;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spiderindia.departmentsofhighway.Adapter.CustomAdapterForEventList;
import com.spiderindia.departmentsofhighway.Details.EventListDetails;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    ListView listview;
    CustomAdapterForEventList adapter;
    ArrayList<EventListDetails> eventList;
    LinearLayout progress_layout;
    ProgressBar loading_process;    //char deepa=0;
    Toolbar toolbar;
    TextView headerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        headerTxt = (TextView) findViewById(R.id.header_txt);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
        headerTxt.setText("Events");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(EventsActivity.this.getResources().getColor(R.color.status_color));
        }

        listview=(ListView)findViewById(R.id.event_listview);
        eventList=new ArrayList<>();
        loading_process = (ProgressBar) findViewById(R.id.process_Loading);
        loading_process.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.app_common_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        progress_layout = (LinearLayout) findViewById(R.id.progress_loading_layout);
        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        new asynTogetEvents().execute();

    }

    void fillData()
    {
        eventList.clear();
        for(int i=0;i<10; i++)
        {
            eventList.add(new EventListDetails(i+"","bridge "+i,"The works and the status of the viaduct were subject to constant observation and supervision, the statement said. The causes for the collapse will be the object of an in-depth analysis as soon as it will be possible to safely access the site.","16-8-2018"));
        }
    }

    class asynTogetEvents extends  AsyncTask<Void, Void, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... voids) {

            fillData();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter=new CustomAdapterForEventList(getApplicationContext(),eventList);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            if (progress_layout.getVisibility() == View.VISIBLE) {
                progress_layout.setVisibility(View.GONE);
            }


        }
    }

}
