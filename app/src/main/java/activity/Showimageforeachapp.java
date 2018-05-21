package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ressources.GraphUtils;
import com.example.ressources.Pie;
import com.example.ressources.R;
import com.example.ressources.Recyleradapter;
import com.example.ressources.StudentGradeMessage;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import supportelement.GraphItem;
import supportelement.Interval;

/**
 * Created by hxu on 04/04/18.
 */


public class Showimageforeachapp extends Activity {

    private Button CPU, GPS;
    private Spinner time;
    private Spinner resource;
    private LinearLayout framelist;
    private android.support.v7.widget.RecyclerView myrecyclerView;
    private ArrayList<GraphItem> arrayListitem = new ArrayList<>();
    HashMap<String, HashMap<Integer, Integer>> graphsourcemap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimageforeachapp);


        Intent intent = getIntent();
        graphsourcemap = (HashMap<String, HashMap<Integer, Integer>>) intent.getSerializableExtra("graphinfo");
/*
        for (String res : graphsourcemap.keySet()) {
            HashMap<Integer, Integer> map = graphsourcemap.get(res);
            System.out.println(res);
            for (Integer interval : map.keySet()) {
                System.out.println(interval);
                System.out.println(map.get(interval));

            }

        }
*/

        init();
        initclickevent();
        getmylist();

        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));
// graphicalview data
        ArrayList<GraphicalView> graphlist = new ArrayList<GraphicalView>();

        graphlist.add((GraphicalView) GraphUtils.getInstance().getmyLineChartView
                (Showimageforeachapp.this, getmylist(),
                        "GPS"));
        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getmyLineChartView(Showimageforeachapp.this, getmylist(),
                        "MobileData"));
        graphlist.add((GraphicalView) GraphUtils.getInstance().getmyLineChartView
                (Showimageforeachapp.this, getmylist(),
                        "SMS"));
        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getmyLineChartView(Showimageforeachapp.this, getmylist(),
                        "WIFI"));
        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getmyLineChartView(Showimageforeachapp.this, getmylist(),
                        "Contacts"));


     // adapter data
        arrayListitem.add(new GraphItem(graphlist.get(0), "GPS"));
        arrayListitem.add(new GraphItem(graphlist.get(1), "MobileData"));
        arrayListitem.add(new GraphItem(graphlist.get(2), "SMS"));
        arrayListitem.add(new GraphItem(graphlist.get(3), "WIFI"));
        arrayListitem.add(new GraphItem(graphlist.get(4), "Contacts"));
        Recyleradapter recyleradapter = new Recyleradapter(arrayListitem);
        System.out.println(recyleradapter.getItemCount());
        myrecyclerView.setAdapter(recyleradapter);


        //click event for button


    }


    private ArrayList<Pie> getpie() {

        ArrayList<Pie> pie = new ArrayList<>();
        Pie p;
        p = new Pie();
        p.setName("1");
        p.setValue(15);
        pie.add(p);
        p = new Pie();
        p.setName("2");
        p.setValue(20);
        pie.add(p);
        p = new Pie();
        p.setName("3");
        p.setValue(3);
        pie.add(p);
        p = new Pie();
        p.setName("4");
        p.setValue(5);
        pie.add(p);

        return pie;
    }

    //timedureee


    public List<HashMap<Integer, Interval>> getmylist() {


        List<HashMap<Integer, Interval>> infolist = new ArrayList<HashMap<Integer, Interval>>();
        Interval interval;

        ArrayList<Interval> intercvallsit = new ArrayList<>();
        int numinterval = 0;
        while (numinterval <5) {

            intercvallsit.add(new Interval(numinterval));
            numinterval++;
        }

        for (String res : graphsourcemap.keySet()) {
            HashMap<Integer, Integer> map = graphsourcemap.get(res);

            int index = 0;
            for (Interval i : intercvallsit) {
             i.setvalue(res, map.get(index));
                index++;
            }


        }
// put the dada inyo map
            for (Interval in :intercvallsit){
              //  System.out.println(interval1.toString());
                Map<Integer, Interval> infomap = new HashMap<>();
                infomap.put(1,in);
                infolist.add((HashMap<Integer, Interval>) infomap);
            }


        return infolist;
    }


    public static List<HashMap<String, StudentGradeMessage>> getlist() {

        List<HashMap<String, StudentGradeMessage>> studentGradeList = new ArrayList<HashMap<String, StudentGradeMessage>>();

        StudentGradeMessage sgm;
        Map<String, StudentGradeMessage> stuGradeMap;

        stuGradeMap = new HashMap<String, StudentGradeMessage>();
        sgm = new StudentGradeMessage();
        sgm.setName("1.1");
        sgm.setMath(80);
        sgm.setChinese(87);
        sgm.setEnglish(78);
        sgm.setTotal(248);
        sgm.setNumChinese(10);
        sgm.setNumEnglish(25);
        sgm.setNumMath(9);
        sgm.setNumTotal(12);
        stuGradeMap.put("name", sgm);
        studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);


        stuGradeMap = new HashMap<String, StudentGradeMessage>();
        sgm = new StudentGradeMessage();
        sgm.setName("1.2");
        sgm.setMath(67);
        sgm.setChinese(89);
        sgm.setEnglish(80);
        sgm.setTotal(236);
        sgm.setNumChinese(5);
        sgm.setNumEnglish(21);
        sgm.setNumMath(23);
        sgm.setNumTotal(16);
        stuGradeMap.put("name", sgm);
        studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);


        stuGradeMap = new HashMap<String, StudentGradeMessage>();
        sgm = new StudentGradeMessage();
        sgm.setName("1.3");
        sgm.setMath(50);
        sgm.setChinese(80);
        sgm.setEnglish(70);
        sgm.setTotal(200);
        sgm.setNumChinese(10);
        sgm.setNumEnglish(35);
        sgm.setNumMath(39);
        sgm.setNumTotal(29);
        stuGradeMap.put("name", sgm);
        studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);


        stuGradeMap = new HashMap<String, StudentGradeMessage>();
        sgm = new StudentGradeMessage();
        sgm.setName("1.4");
        sgm.setMath(60);
        sgm.setChinese(67);
        sgm.setEnglish(60);
        sgm.setTotal(187);
        sgm.setNumChinese(40);
        sgm.setNumEnglish(30);
        sgm.setNumMath(30);
        sgm.setNumTotal(40);
        stuGradeMap.put("name", sgm);
        studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);


        stuGradeMap = new HashMap<String, StudentGradeMessage>();
        sgm = new StudentGradeMessage();
        sgm.setName("1.5");
        sgm.setMath(80);
        sgm.setChinese(87);
        sgm.setEnglish(88);
        sgm.setTotal(258);
        sgm.setNumChinese(9);
        sgm.setNumEnglish(7);
        sgm.setNumMath(13);
        sgm.setNumTotal(14);
        stuGradeMap.put("name", sgm);
        studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);


        stuGradeMap = new HashMap<String, StudentGradeMessage>();
        sgm = new StudentGradeMessage();
        sgm.setName("1.6");
        sgm.setMath(90);
        sgm.setChinese(80);
        sgm.setEnglish(50);
        sgm.setTotal(220);
        sgm.setNumChinese(10);
        sgm.setNumEnglish(35);
        sgm.setNumMath(2);
        sgm.setNumTotal(21);
        stuGradeMap.put("name", sgm);
        studentGradeList.add((HashMap<String, StudentGradeMessage>) stuGradeMap);
        return studentGradeList;

    }


    void init() {

        time = (Spinner) findViewById(R.id.time_in_mine);
        resource = (Spinner) findViewById(R.id.resource_in_mine);
/*        CPU = (Button) findViewById(R.id.showimage_CPU);
        GPS = (Button) findViewById(R.id.showimage_GPS);*/
        myrecyclerView = (RecyclerView) findViewById(R.id.showimage_recycler);


    }


    void initclickevent() {


        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sInfo = parent.getItemAtPosition(position).toString();
                if(sInfo.equals("Five Minutes")){
                    //when user choose five minutes
                    Toast.makeText(getApplicationContext(),sInfo,Toast.LENGTH_LONG).show();
                }else if(sInfo.equals("One Hour")){
                    //when user choose one hour
                    Toast.makeText(getApplicationContext(),sInfo,Toast.LENGTH_LONG).show();
                }else if(sInfo.equals("One Week")){
                    //when user choose one week
                    Toast.makeText(getApplicationContext(),sInfo,Toast.LENGTH_LONG).show();
                }else if(sInfo.equals("One Month")){
                    //when user choose one month
                    Toast.makeText(getApplicationContext(),sInfo,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        resource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rInfo = parent.getItemAtPosition(position).toString();
                switch (rInfo){
                    case "GPS":
                        int pos1 = 0;
                        for (GraphItem g : arrayListitem) {

                            if (g.getTag().equals("GPS")) {

                                myrecyclerView.smoothScrollToPosition(pos1);

                            }

                        }
                        break;
                    case "CPU":
                        int pos2 = 0;
                        for (GraphItem g : arrayListitem) {

                            if (g.getTag().equals("CPU")) {
                                myrecyclerView.smoothScrollToPosition(pos2);
                            }

                        }
                        break;
                    case "Mobile Data":
                        Toast.makeText(getApplicationContext(),rInfo,Toast.LENGTH_LONG).show();
                        break;
                    case "Wifi":

                        break;
                    case "Bluetooth":

                        break;
                    case "NFC":

                        break;
                    case "Camera":

                        break;
                    case "Sensors":

                        break;
                    case "SMS":

                        break;
                    case "Contacts":

                        break;
                    case "Phone":

                        break;
                    case "Intenal Memory":

                        break;
                    case "External Memory":

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     /*   CPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                for (GraphItem g : arrayListitem) {

                    if (g.getTag().equals("CPU")) {
                        myrecyclerView.smoothScrollToPosition(position);
                    }

                }
            }
        });


        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = 0;
                for (GraphItem g : arrayListitem) {

                    if (g.getTag().equals("GPS")) {

                        myrecyclerView.smoothScrollToPosition(position);

                    }

                }
            }
        });
*/

    }
}