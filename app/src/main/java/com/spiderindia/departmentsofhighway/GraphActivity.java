package com.spiderindia.departmentsofhighway;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    ArrayList<BarEntry> entries;
    BarData data;
    Toolbar toolbar;
    TextView headerTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        headerTxt = (TextView) findViewById(R.id.header_txt);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
        headerTxt.setText("Graph");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(GraphActivity.this.getResources().getColor(R.color.status_color));
        }


             /*Bar chart start */
        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);

        // creating labels
        ArrayList<String> labels = new ArrayList<String>();
               /* labels.add("New Leads");
                labels.add("Product Demo");
                labels.add("Proposal");*/

        labels.add("Under Negotiation");
        labels.add("New Bridges");
        labels.add("Finished");

        entries = new ArrayList<>();
        entries.add(new BarEntry(Float.parseFloat("5"), 0));
        entries.add(new BarEntry(Float.parseFloat("10"), 1));
        entries.add(new BarEntry(Float.parseFloat("3"), 2));


        //set the data
        BarDataSet dataset = new BarDataSet(entries, "Under negotiation,New Bridges,Finished");
        // BarDataSet dataset = new BarDataSet(entries, "New Leads,Product Demo,Propsal,Under negotiation,Success,Order Lost");

        BarData data = new BarData(labels, dataset);
        barChart.setData(data);

        barChart.setDescription("Leads");

        dataset.setColors(COLORFUL_COLORS_);

        barChart.animateY(1000);

        barChart.setNoDataText("No leads");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        /*dataset.setColors(
                ContextCompat.getColor(getApplicationContext(), R.color.blue),
                ContextCompat.getColor(barChart.getContext(), R.color.orange),
               ContextCompat.getColor(barChart.getContext(), R.color.green) );
*/
        XAxis x = barChart.getXAxis();
        x.setTextColor(getResources().getColor(R.color.app_common_color));


        System.out.println("Barchart color "+barChart.getSolidColor());
        System.out.println("Barchart color getDrawingCacheBackgroundColor "+barChart.getDrawingCacheBackgroundColor());
        barChart.getSolidColor();



    }

    public static final int[] COLORFUL_COLORS_ = {
            Color.rgb(135,206,235), Color.rgb(50,205,50), Color.rgb(178,34,34),

    };

}
