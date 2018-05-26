package activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ressources.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

/**
 * Created by XH on 26/05/2018.
 */

public class TestSpider extends Activity {


    RadarChart chart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spidertest);

        chart = (RadarChart) findViewById(R.id.spider);
        ArrayList<Entry> list = new ArrayList<>();
        list.add(new Entry(2f, 0));
        list.add(new Entry(4f, 1));
        list.add(new Entry(3f, 2));
        list.add(new Entry(4f, 3));
        list.add(new Entry(7f, 4));
        list.add(new Entry(5f, 5));
        list.add(new Entry(8f, 6));


        RadarDataSet dataset1 = new RadarDataSet(list, "label1");
        dataset1.setColor(Color.BLUE);
        dataset1.setDrawFilled(true);

        ArrayList<String> labellist = new ArrayList<>();
        labellist.add("CPU");
        labellist.add("GPS");
        labellist.add("SMS");
        labellist.add("LQLLQ");
        labellist.add("LOL");
        labellist.add("doubleLOL");
        labellist.add("doubleLOLlolo");

        ArrayList<RadarDataSet> dataSetArrayList = new ArrayList<>();
        dataSetArrayList.add(dataset1);
        RadarData datas = new RadarData(labellist, dataSetArrayList);

        chart.setData(datas);

    }
}
