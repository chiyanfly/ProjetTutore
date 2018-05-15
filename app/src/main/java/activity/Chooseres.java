package activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ressources.R;

import java.util.ArrayList;

import Database.Database;

/**
 * Created by hxu on 15/05/18.
 */

public class Chooseres extends Activity {




    ArrayList<String> resnamelist= new ArrayList<>();

   // Database database;
    ListView resnamelistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chooseres);

        //database= new Database(getApplicationContext());
        getdata();
        resnamelistview= (ListView) findViewById(R.id.id_reslist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chooseres.this,
                android.R.layout.simple_list_item_1, resnamelist);
        resnamelistview.setAdapter(adapter);






    }

    // get appname from the database and put it in the appnamelist
    void getdata(){

        // select appname from table
        Cursor c =  Database.getInstance(getApplicationContext()).searchdata(getApplicationContext(),"select RESSOURCES from donneesRessources");


        while(c.moveToNext()){

            String  resname = c.getString(c.getColumnIndex("RESSOURCES"));
            resnamelist.add(resname);
        }
        c.close();
    }
}




