package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import supportelement.GraphItem;
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

/**
 * Created by hxu on 04/04/18.
 */

public class mine_main_activity extends Activity {


    Button CPU, GPS;

    LinearLayout framelist;
    android.support.v7.widget.RecyclerView myrecyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);


        init();
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(this));
// graphicalview data
        ArrayList<GraphicalView> graphlist = new ArrayList<GraphicalView>();
        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getLineChartView(mine_main_activity.this, getlist(),
                        "B"));
        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getLineChartView(mine_main_activity.this, getlist(),
                        "C"));

        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getLineChartView(mine_main_activity.this, getlist(),
                        "B"));
        graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getLineChartView(mine_main_activity.this, getlist(),
                        "B"));




    // adapter data
        ArrayList<GraphItem> arrayListitem = new ArrayList<>();
        arrayListitem.add(new GraphItem(graphlist.get(0), "CPU"));
        arrayListitem.add(new GraphItem(graphlist.get(1), "GPS"));

        arrayListitem.add(new GraphItem(graphlist.get(2), "GPS"));
        arrayListitem.add(new GraphItem(graphlist.get(3), "GPS"));

        Recyleradapter recyleradapter = new Recyleradapter(arrayListitem);

        System.out.println(recyleradapter.getItemCount());

        myrecyclerView.setAdapter(recyleradapter);


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

        CPU = (Button) findViewById(R.id.mine_CPU);
        GPS = (Button) findViewById(R.id.mine_GPS);

        myrecyclerView = (RecyclerView) findViewById(R.id.recycler);


    }


}
