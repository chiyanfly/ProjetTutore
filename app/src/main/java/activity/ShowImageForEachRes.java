package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ressources.GraphUtils;
import com.example.ressources.R;

import org.achartengine.GraphicalView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import supportelement.GraphItem;
import supportelement.Recyleradapter;
import tool.DataToimagetool;

public class ShowImageForEachRes extends Activity {

    private String scale;
    private Spinner scaleSpinner;
    private Spinner timeSpinner;
    HashMap<String, HashMap<Long, Integer>> dataset;
    private String resname;
    private android.support.v7.widget.RecyclerView myrecyclerView;
    private ArrayList<GraphItem> arrayListitem = new ArrayList<>();
    private ArrayList<Date> periodList;
    private TextView resourceTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimageforeachres);
        Intent intent = getIntent();
        resname = intent.getStringExtra("resname");
        init();
        initclickevent();
    }

    ArrayList<Date> generateExamplePeriod(String scale) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<Date> periodList = new ArrayList<>();
        switch (scale) {
            case "Hour":
                calendar.set(2018, 5, 5, 7, 0);
                periodList.add(calendar.getTime());
                calendar.set(2018, 5, 5, 6, 0);
                periodList.add(calendar.getTime());
                calendar.set(2018, 5, 4, 20, 0);
                periodList.add(calendar.getTime());
                calendar.set(2018, 5, 4, 19, 0);
                periodList.add(calendar.getTime());
                calendar.set(2018, 5, 4, 18, 0);
                periodList.add(calendar.getTime());
                calendar.set(2018, 5, 4, 17, 0);
                periodList.add(calendar.getTime());
                break;
            case "Day":
                calendar.set(2018, 5, 5);
                periodList.add(calendar.getTime());
                calendar.set(2018, 5, 4);
                periodList.add(calendar.getTime());
                break;
            case "Month":
                calendar.set(2018, 5, 1);
                periodList.add(calendar.getTime());
                calendar.set(2018, 4, 1);
                periodList.add(calendar.getTime());
                break;
            case "Year":
                calendar.set(2018, 0, 2);
                periodList.add(calendar.getTime());
                calendar.set(2017, 0, 2);
                periodList.add(calendar.getTime());
        }
        return periodList;
    }

    HashMap<String, HashMap<Long, Integer>> generateData(String scale, long period) {
        HashMap<String, HashMap<Long, Integer>> dataSet = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            HashMap<Long, Integer> accessList = new HashMap<>();
            int maxInterval = 0;
            int multiplier = 0;
            long addedTime = 0;
            switch (scale) {
                case "Hour":
                    maxInterval = 12;
                    multiplier = 1;
                    addedTime = 5 * 60 * 1000;
                    break;
                case "Day":
                    maxInterval = 24;
                    multiplier = 24;
                    addedTime = 60 * 60 * 1000;
                    break;
                case "Month":
                    maxInterval = 31;
                    multiplier = 24 * 31;
                    addedTime = 24 * 60 * 60 * 1000;
                    break;
                case "Year":
                    maxInterval = 12;
                    multiplier = 24 * 31 * 12;
                    addedTime = 31 * 24;
                    addedTime *= 3600000;
            }
            System.out.println("generating app" + i + "...");
            for (int j = 0; j < maxInterval; j++) {
                accessList.put(period + addedTime * (long) j, random.nextInt(20 * multiplier));
                System.out.println((period + addedTime * (long) j) + ": " + accessList.get(period + addedTime * (long) i));
            }
            dataSet.put("app" + i, accessList);
        }
        return dataSet;
    }

    void setScaleSpinnerAdapter() {
        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Hour");
        spinnerArray.add("Day");
        spinnerArray.add("Month");
        spinnerArray.add("Year");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scaleSpinner.setAdapter(spinnerArrayAdapter);
    }

    void setTimeSpinnerAdapter(String scale) {
        SimpleDateFormat dateFormat = null;
        switch (scale) {
            case "Hour":
                dateFormat = new SimpleDateFormat("dd/MM-HH:mm");
                break;
            case "Day":
                dateFormat = new SimpleDateFormat("dd/MM");
                break;
            case "Month":
                dateFormat = new SimpleDateFormat("MMM");
                break;
            case "Year":
                dateFormat = new SimpleDateFormat("YYYY");
                break;
            default:
                dateFormat = new SimpleDateFormat("");

        }
        ArrayList<String> spinnerArray = new ArrayList<>();
        for (Date period : periodList) {
            spinnerArray.add(dateFormat.format(period));
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(spinnerArrayAdapter);
    }

    void init() {
        scaleSpinner = (Spinner) findViewById(R.id.scale_in_mine_res);
        timeSpinner = (Spinner) findViewById(R.id.time_in_mine_res);
        resourceTextView = (TextView) findViewById(R.id.resNameView);
        resourceTextView.setText(resname);
        resourceTextView.setTextColor(Color.BLACK);
        setScaleSpinnerAdapter();
        myrecyclerView = (RecyclerView) findViewById(R.id.showimage_recycler_res);
    }

    void initclickevent() {
        scaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scale = parent.getItemAtPosition(position).toString();
                System.out.println("Selected scale: "+scale);
                /* update timeSpinner */
                periodList = generateExamplePeriod(scale);
                setTimeSpinnerAdapter(scale);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long period = periodList.get(position).getTime();
                System.out.println("Selected position: " + position);
                /* update data set according to scale and period */
                dataset = generateData(scale, period);
                put_imagedata();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void put_imagedata(){
        arrayListitem.clear();
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<GraphicalView> graphList = new ArrayList<GraphicalView>();
        for (String app : dataset.keySet()) {
            ArrayList<Date> dateList = new ArrayList<>();
            ArrayList<Integer> dataList = new ArrayList<>();
            System.out.println("displaying " + app + "...");
            for (Long period : dataset.get(app).keySet()) {
                dateList.add(new Date(period));
                dataList.add(dataset.get(app).get(period));
                System.out.println(period + ": " + dataset.get(app).get(period));
            }
            graphList.add((GraphicalView) GraphUtils.createGraph
                    ( ShowImageForEachRes.this, dateList, dataList, app, scale));
            arrayListitem.add(new GraphItem((graphList.get(graphList.size()-1)), "app"));
        }
        /* Applying Tests */
        Recyleradapter recyleradapter = new Recyleradapter(arrayListitem);
//        System.out.println(recyleradapter.getItemCount());
        myrecyclerView.setAdapter(recyleradapter);
//        // Testing Hour
//        ArrayList<Date> dateList = new ArrayList<>();
//        ArrayList<Integer> dataList = new ArrayList<>();
//        Random random = new Random();
//        long timestamp = 0;
////        Long timestamp = System.currentTimeMillis();
//        for (int i = 0; i < 12; i++) {
//            dateList.add(new Date(timestamp + (((long) i) * 300000)));
//            dataList.add(random.nextInt(21));
//        }
//        graphList.add((GraphicalView) GraphUtils.createGraph
//                ( ShowImageForEachRes.this, dateList, dataList, "app", "Hour"));
//        arrayListitem.add(new GraphItem((graphList.get(graphList.size()-1)), "app"));
//        // Testing Day
//        dataList.clear();
//        dateList.clear();
//        for (int i = 0; i < 24; i++) {
//            dateList.add(new Date(timestamp + ((long) i) * 3600000));
//            dataList.add(random.nextInt(21) * 24);
//        }
//        graphList.add((GraphicalView) GraphUtils.createGraph
//                ( ShowImageForEachRes.this, dateList, dataList, "app", "Day"));
//        arrayListitem.add(new GraphItem((graphList.get(graphList.size()-1)), "app"));
//        // Testing Month
//        dataList.clear();
//        dateList.clear();
//        for (int i = 0; i < 31; i++) {
//            dateList.add(new Date(timestamp + (((long) i) * 3600000 * 24)));
//            dataList.add(random.nextInt(21) * 24 * 31);
//        }
//        graphList.add((GraphicalView) GraphUtils.createGraph
//                ( ShowImageForEachRes.this, dateList, dataList, "app", "Month"));
//        arrayListitem.add(new GraphItem((graphList.get(graphList.size()-1)), "app"));
//        // Testing Year
//        dataList.clear();
//        dateList.clear();
//        for (int i = 0; i < 12; i++) {
//            dateList.add(new Date(timestamp + (((long) i) * 3600000 * 24 * 31)));
//            dataList.add(random.nextInt(21) * 24 * 31 * 12);
//        }
//        graphList.add((GraphicalView) GraphUtils.createGraph
//                ( ShowImageForEachRes.this, dateList, dataList, "app", "Year"));
//        arrayListitem.add(new GraphItem((graphList.get(graphList.size()-1)), "app"));
//        // Applying Tests
//        Recyleradapter recyleradapter = new Recyleradapter(arrayListitem);
//        System.out.println(recyleradapter.getItemCount());
//        myrecyclerView.setAdapter(recyleradapter);
    }
}
