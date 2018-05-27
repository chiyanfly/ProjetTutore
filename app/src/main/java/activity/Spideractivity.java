package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

import tool.DataToimagetool;
import tool.Groupresource;

/**
 * Created by XH on 26/05/2018.
 */

public class Spideractivity extends Activity {

    private RadarChart chart;

    private HashMap<String, HashMap<Integer, Integer>> graphsourcemap;
    private String appname;
    private Spinner timespiner;
    private  Spinner timespinerchild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spider);

        chart = (RadarChart) findViewById(R.id.spider);
        timespiner = (Spinner) findViewById(R.id.spider_spiner);
        timespinerchild=(Spinner) findViewById(R.id.spider_spinerchild);
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

// click listener
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                String groupname = Groupresource.Groupenamelist.get(e.getXIndex());
                Intent intent1 = new Intent();
                intent.setClass(Spideractivity.this, Showimageforeachapp.class);
                intent.putExtra("graphinfo", (Serializable) graphsourcemap);
                intent.putExtra("appname", appname);
                intent.putExtra("resname", groupname);
                startActivity(intent);


            }

            @Override
            public void onNothingSelected() {

            }
        });


        timespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sInfo = parent.getItemAtPosition(position).toString();
                if (sInfo.equals("Five Minutes")) {
                    //when user choose five minutes
                    settimechildSpinnerAdapter("Five Minutes");
                    updateimage("fiveMinutes");
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                } else if (sInfo.equals("One Hour")) {
                    //when user choose one hour
                    settimechildSpinnerAdapter("One Hour");
                    updateimage("oneHour");

                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                } else if (sInfo.equals("One Week")) {
                    //when user choose one week
                    settimechildSpinnerAdapter("One Week");
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                } else if (sInfo.equals("One Month")) {
                    //when user choose one month
                    settimechildSpinnerAdapter("One Month");
                    Toast.makeText(getApplicationContext(), sInfo, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void setspiderdata() {


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


    }


    private void updateimage(String tablename) {


        graphsourcemap = DataToimagetool.Table_to_graphsourcemap(getApplicationContext(), appname, tablename);
        chart.clear();
        setspiderdata();

    }


    void settimechildSpinnerAdapter(String time) {
        ArrayList<String> spinnerArray = new ArrayList<>();

      switch (time){

          case "Five Minutes":

              spinnerArray.add("1");

              break;

          case "One Hour":
              break;


          case "One Week":
              break;


          case "One Month":
              break;


          default:
              break;


      }



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timespinerchild.setAdapter(spinnerArrayAdapter);
    }


}