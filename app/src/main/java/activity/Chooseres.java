package activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ressources.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import database.Database;
import tool.DataToimagetool;

/**
 * Created by hxu on 15/05/18.
 */

public class Chooseres extends Activity {

    private ArrayList<String> resnamelist= new ArrayList<>();
    private Database database;
    private ListView resnamelistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chooseres);

        database= Database.getInstance(getApplicationContext());
        getResName("oneHour");
        resnamelistview= (ListView) findViewById(R.id.id_reslist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chooseres.this,
                android.R.layout.simple_list_item_1, resnamelist);
        resnamelistview.setAdapter(adapter);

        resnamelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                String resname = resnamelist.get(position);
                System.out.println(resname);
                HashMap<String, HashMap<Integer, Integer>> graphsourcemap
                        = DataToimagetool.Table_to_graphsourcemap2(getApplicationContext(),resname,"fiveMinutes");
                Intent intent = new Intent();
                intent.setClass(Chooseres.this, ShowImageForEachRes.class);
                intent.putExtra("graphinfo", (Serializable) graphsourcemap);
                intent.putExtra("resname",resname);
                startActivity(intent);
            }
        });




    }

    // get resource from the database and put it in the resnamelist
    void getResName(String tablename){

        // select appname from table
        Cursor c =  database.searchdata(getApplicationContext(),"select RESSOURCES from " + tablename);
        while(c.moveToNext()){
            String  resname = c.getString(c.getColumnIndex("RESSOURCES"));
            if(!resnamelist.contains(resname))
                resnamelist.add(resname);
        }
        c.close();
    }
}




