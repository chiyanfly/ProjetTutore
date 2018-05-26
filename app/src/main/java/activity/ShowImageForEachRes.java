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
import org.achartengine.chart.BarChart;

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
        Intent intent = getIntent();
        graphsourcemap = (HashMap<String, HashMap<Integer, Integer>>) intent.getSerializableExtra("graphinfo");
        init();
        initclickevent();
        resname = intent.getStringExtra("resname");
        put_imagedata(/*getmylist()*/);
    }

    void setAppSpinnerAdapter() {
        ArrayList<String> spinnerArray = new ArrayList<>();
        for (String res : graphsourcemap.keySet()) {
            spinnerArray.add(res);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_app.setAdapter(spinnerArrayAdapter);
    }

    void init() {
        Spinner_time = (Spinner) findViewById(R.id.time_in_mine_res);
        Spinner_app = (Spinner) findViewById(R.id.resource_in_mine_res);
        setAppSpinnerAdapter();
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
                myrecyclerView./*smoothS*/scrollToPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // this function is used for updating the image with the time interval chosen
    private void updateimage(String tablename) {
        graphsourcemap = DataToimagetool.Table_to_graphsourcemap2(getApplicationContext(), resname, tablename);
        setAppSpinnerAdapter();
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
        Recyleradapter recyleradapter = new Recyleradapter(arrayListitem);
        System.out.println(recyleradapter.getItemCount());
        myrecyclerView.setAdapter(recyleradapter);
    }
}
