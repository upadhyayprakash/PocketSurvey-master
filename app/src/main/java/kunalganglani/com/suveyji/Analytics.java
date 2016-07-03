package kunalganglani.com.suveyji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Analytics extends AppCompatActivity {
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    public static String Location[]= new String[6];
    public static float Value[]= new float[6];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Location[0]="Delhi" ;
        Location[1] = "Indore";
        Location[2] = "Mumbai";
        Location[3] = "Bangalore";
        Location[4] = "Pune";
        Location[5] = "Kolkata";

        Value[0]=4f;
        Value[1] = 8f;
        Value[2] = 6f;
        Value[3] = 12f;
        Value[4] = 18f;
        Value[5] = 9f;


        setContentView(R.layout.activity_analytics);
        Spinner dropdown = (Spinner)findViewById(R.id.spinner2);
        String[] items = new String[]{"Bar Graph", "Pie Chart", "Line Graph"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BarChart barChart = (BarChart) findViewById(R.id.chart);
                PieChart pieChart = (PieChart) findViewById(R.id.piechart);
                LineChart lineChart = (LineChart)findViewById(R.id.linechart);

                switch (position) {
                    case 0:
                        pieChart.setVisibility(View.INVISIBLE);
                        lineChart.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.VISIBLE);

                        // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);

                        ArrayList<BarEntry> entries1 = new ArrayList<>();
                        entries1.add(new BarEntry(Value[0], 0));
                        entries1.add(new BarEntry(Value[1], 1));
                        entries1.add(new BarEntry(Value[2], 2));
                        entries1.add(new BarEntry(Value[3], 3));
                        entries1.add(new BarEntry(Value[4], 4));
                        entries1.add(new BarEntry(Value[5], 5));

                        BarDataSet dataset1 = new BarDataSet(entries1, "# of People Interested in Computer Course");

                        ArrayList<String> labels1 = new ArrayList<String>();
                        labels1.add(Location[0]);
                        labels1.add(Location[1]);
                        labels1.add(Location[2]);
                        labels1.add(Location[3]);
                        labels1.add(Location[4]);
                        labels1.add(Location[5]);

        /* for create Grouped Bar chart
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(18f, 4));
        group1.add(new BarEntry(9f, 5));

        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        BarDataSet barDataSet1 = new BarDataSet(group1, "Group 1");
        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet2 = new BarDataSet(group2, "Group 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> dataset = new ArrayList<>();
        dataset.add(barDataSet1);
        dataset.add(barDataSet2);
        */

                        BarData data1 = new BarData(labels1, dataset1);
                        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                        barChart.setData(data1);
                        barChart.setDescription("Location Vs Count-of-Interested-folks");
                        barChart.setNoDataText(""); // this is the top line
                        barChart.setNoDataTextDescription(""); // this is one line below the no-data-text
                        barChart.invalidate();
                        barChart.animateY(3000);
                        break;
                    case 1:
                        barChart.setVisibility(View.INVISIBLE);
                        lineChart.setVisibility(View.INVISIBLE);
                        pieChart.setVisibility(View.VISIBLE);
                        ArrayList<Entry> entries = new ArrayList<>();
                        entries.add(new Entry(Value[0], 0));
                        entries.add(new Entry(Value[1], 1));
                        entries.add(new Entry(Value[2], 2));
                        entries.add(new Entry(Value[3], 3));
                        entries.add(new Entry(Value[4], 4));
                        entries.add(new Entry(Value[5], 5));

                        PieDataSet dataset = new PieDataSet(entries, "#People Interested");

                        ArrayList<String> labels = new ArrayList<String>();
                        labels.add(Location[0]);
                        labels.add(Location[1]);
                        labels.add(Location[2]);
                        labels.add(Location[3]);
                        labels.add(Location[4]);
                        labels.add(Location[5]);

                        PieData data = new PieData(labels, dataset);
                        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                        pieChart.setDescription("Location Vs Count-of-Interested-folks");
                        pieChart.setNoDataText(""); // this is the top line
                        pieChart.setNoDataTextDescription(""); // this is one line below the no-data-text
                        pieChart.invalidate();
                        pieChart.setData(data);

                        pieChart.animateY(3000);

                        //pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
                        break;
                    case 2:
                        pieChart.setVisibility(View.INVISIBLE);
                        barChart.setVisibility(View.INVISIBLE);
                        lineChart.setVisibility(View.VISIBLE);

                        ArrayList<Entry> entries2 = new ArrayList<>();
                        entries2.add(new Entry(Value[0], 0));
                        entries2.add(new Entry(Value[1], 1));
                        entries2.add(new Entry(Value[2], 2));
                        entries2.add(new Entry(Value[3], 3));
                        entries2.add(new Entry(Value[4], 4));
                        entries2.add(new Entry(Value[5], 5));

                        LineDataSet dataset2 = new LineDataSet(entries2, "#People Interested");

                        ArrayList<String> labels2 = new ArrayList<String>();
                        labels2.add(Location[0]);
                        labels2.add(Location[1]);
                        labels2.add(Location[2]);
                        labels2.add(Location[3]);
                        labels2.add(Location[4]);
                        labels2.add(Location[5]);

                        LineData data2 = new LineData(labels2, dataset2);
                        lineChart.setData(data2); // set the data and list of lables into chart

                        //dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                        lineChart.setDescription("Location Vs Count-of-Interested-folks");
                        dataset2.setDrawCubic(true);
                        dataset2.setDrawFilled(true);

                        lineChart.animateY(3000);

                        //pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
                        break;

                }
                //Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


    }

}
