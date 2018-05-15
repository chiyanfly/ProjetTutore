package activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ressources.R;

import java.util.ArrayList;

import Database.Database;

/**
 * Created by hxu on 15/05/18.
 */

public class Chooseapp extends Activity {




    ArrayList<String>  appnamelist= new ArrayList<>();

    //Database database;
    ListView  appnamelistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chooseapp);

           // database= new Database(getApplicationContext());
            getdata();
            appnamelistview= (ListView) findViewById(R.id.id_applist);
             ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chooseapp.this,
                android.R.layout.simple_list_item_1, appnamelist);
            appnamelistview.setAdapter(adapter);






    }

// get appname from the database and put it in the appnamelist
    void getdata(){

        // select appname from table
      Cursor c = Database.getInstance(getApplicationContext()).searchdata(getApplicationContext(),"select appName from donneesRessources");


        while(c.moveToNext()){

            String  appname = c.getString(c.getColumnIndex("appName"));
            System.out.println("dqd");
            appnamelist.add(appname);
        }
        c.close();
    }
}
