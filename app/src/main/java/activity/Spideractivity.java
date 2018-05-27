package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ressources.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import tool.Groupresource;

/**
 * Created by XH on 26/05/2018.
 */

public class Spideractivity extends Activity {

    private RadarChart chart;

    private HashMap<String, HashMap<Integer, Integer>> graphsourcemap;
    private String appname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spider);

        chart = (RadarChart) findViewById(R.id.spider);
        final Intent intent = getIntent();
        graphsourcemap = (HashMap<String, HashMap<Integer, Integer>>) intent.getSerializableExtra("graphinfo");
        appname = intent.getStringExtra("appname");
        System.out.println("appname is" + appname);

        /*
        *Hashmap  String: name of res
        *       Hashmap<Integer, Integer>
        *               numof inertval  , times of usage
        *
        * ex:  GPS (0,1)(1,4)(2,5).......
        *
        * */
        ArrayList<Integer> timeslist = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            timeslist.add(0);

        for (String resname : graphsourcemap.keySet()) {

            int index = Groupresource.whichgroup(resname);
            System.out.println(resname + " belongs to " + index);
            if (index != -1) {
                int total = 0;
                for (Integer i : graphsourcemap.get(resname).values()) {
                    total = total + i;
                }


                System.out.println(total);
                timeslist.set(index, timeslist.get(index) + total);
            }

        }
// data for the spider image
        for (Integer integer : timeslist) {
            System.out.println(integer);

        }
        ArrayList<Entry> list = new ArrayList<>();

        for (int index = 0; index < timeslist.size(); index++) {

            list.add(new Entry(timeslist.get(index), index));

        }

        RadarDataSet dataset1 = new RadarDataSet(list, appname);
        dataset1.setColor(Color.BLUE);
        dataset1.setDrawFilled(true);

        ArrayList<RadarDataSet> dataSetArrayList = new ArrayList<>();
        dataSetArrayList.add(dataset1);

        RadarData datas = new RadarData(Groupresource.Groupenamelist, dataSetArrayList);

        chart.setData(datas);
// click listener
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
              String groupname= Groupresource.Groupenamelist.get(e.getXIndex());
                Intent intent1 = new Intent();
                intent.setClass(Spideractivity.this,Showimageforeachapp.class);
                intent.putExtra("graphinfo", (Serializable) graphsourcemap);
                intent.putExtra("appname",appname);
                intent.putExtra("resname",groupname);
                startActivity(intent);


            }

            @Override
            public void onNothingSelected() {

            }
        });


    }
}
