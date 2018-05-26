package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ressources.GraphUtils;
import com.example.ressources.R;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import supportelement.GraphItem;
import supportelement.Interval;
import supportelement.Recyleradapter;
import tool.DataToimagetool;

public class ShowImageForEachRes extends Activity {

    private Spinner Spinner_time;
    private Spinner Spinner_app;
    HashMap<String, HashMap<Integer, Integer>> graphsourcemap;
    private String resname;
    private android.support.v7.widget.RecyclerView myrecyclerView;
    private ArrayList<GraphItem> arrayListitem = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimageforeachres);
        init();
        initclickevent();
        Intent intent = getIntent();
        graphsourcemap = (HashMap<String, HashMap<Integer, Integer>>) intent.getSerializableExtra("graphinfo");
        resname = intent.getStringExtra("resname");
        put_imagedata(/*getmylist()*/);
    }

    void init() {
        Spinner_time = (Spinner) findViewById(R.id.time_in_mine_res);
        Spinner_app = (Spinner) findViewById(R.id.resource_in_mine_res);
        List<String> spinnerArray = Arrays.asList("App0", "App1", "App2", "App3", "App4");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerArray);
                //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_app.setAdapter(spinnerArrayAdapter);
        myrecyclerView = (RecyclerView) findViewById(R.id.showimage_recycler_res);
    }

    void initclickevent() {
        Spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sInfo = parent.getItemAtPosition(position).toString();
                if (sInfo.equals("Five Minutes")) {
                    //when user choose five minutes
                    updateimage("fiveMinutes");
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                } else if (sInfo.equals("One Hour")) {
                    //when user choose one hour
                    updateimage("oneHour");
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                } else if (sInfo.equals("One Week")) {
                    //when user choose one week
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                } else if (sInfo.equals("One Month")) {
                    //when user choose one month
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner_app.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rInfo = parent.getItemAtPosition(position).toString();
                int pos = 0;
                for (GraphItem g : arrayListitem) {
                    if (g.getTag().equals(rInfo)) {
                        myrecyclerView.smoothScrollToPosition(pos);
                        break;
                    }
                    pos++;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public List<HashMap<Integer, Interval>> getmylist() {
        List<HashMap<Integer, Interval>> infolist = new ArrayList<HashMap<Integer, Interval>>();
//        Interval interval;
        ArrayList<Interval> intervallsit = new ArrayList<>();
        int numinterval = 0;
        while (numinterval < 5) {
            intervallsit.add(new Interval(numinterval));
            numinterval++;
        }
        for (String res : graphsourcemap.keySet()) {
            HashMap<Integer, Integer> map = graphsourcemap.get(res);
            int index = 0;
            for (Interval i : intervallsit) {
                i.setvalue(res, map.get(index));
                index++;
            }
        }
//        put the data into map
        for (Interval in : intervallsit) {
            //  System.out.println(interval1.toString());
            Map<Integer, Interval> infomap = new HashMap<>();
            infomap.put(1, in);
            infolist.add((HashMap<Integer, Interval>) infomap);
        }
        return infolist;
    }

    // this function is used for updating the image with the time interval chosen
    private void updateimage(String tablename) {
        graphsourcemap = DataToimagetool.Table_to_graphsourcemap2(getApplicationContext(), resname, tablename);
        put_imagedata();
    }

    private void put_imagedata(){
        arrayListitem.clear();
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<GraphicalView> graphlist = new ArrayList<GraphicalView>();
        for (String res : graphsourcemap.keySet()) {
            graphlist.add((GraphicalView) GraphUtils.getInstance().getLineChartView2
                    (ShowImageForEachRes.this, graphsourcemap.get(res),res));
            arrayListitem.add(new GraphItem((graphlist.get(graphlist.size()-1)), res));
        }
//        graphlist.add((GraphicalView) GraphUtils.getInstance().getmyLineChartView
//                (ShowImageForEachRes.this, datalist,
//                        "App0"));
//        graphlist.add((GraphicalView) GraphUtils.getInstance()
//                .getmyLineChartView(ShowImageForEachRes.this,datalist,
//                        "App1"));
//        graphlist.add((GraphicalView) GraphUtils.getInstance().getmyLineChartView
//                (ShowImageForEachRes.this, datalist,
//                        "App2"));
//        graphlist.add((GraphicalView) GraphUtils.getInstance()
//                .getmyLineChartView(ShowImageForEachRes.this, datalist,
//                        "App3"));
//        graphlist.add((GraphicalView) GraphUtils.getInstance()
//                .getmyLineChartView(ShowImageForEachRes.this, datalist,
//                        "App4"));
        // adapter data
//        arrayListitem.add(new GraphItem(graphlist.get(0), "App0"));
//        arrayListitem.add(new GraphItem(graphlist.get(1), "App1"));
//        arrayListitem.add(new GraphItem(graphlist.get(2), "App2"));
//        arrayListitem.add(new GraphItem(graphlist.get(3), "App3"));
//        arrayListitem.add(new GraphItem(graphlist.get(4), "App4"));
        Recyleradapter recyleradapter = new Recyleradapter(arrayListitem);
        System.out.println(recyleradapter.getItemCount());
        myrecyclerView.setAdapter(recyleradapter);
    }
}
