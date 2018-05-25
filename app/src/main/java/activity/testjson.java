package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.widget.TextView;

import com.example.ressources.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Database.Database;
import reader.JsonFileReader;
import reader.StatEntry;


/**
 * Created by hxu on 18/04/18.
 */

public class testjson extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.test);
         InputStream in = getResources().openRawResource(getResources().getIdentifier("input","raw",getPackageName()));

        TextView t = (TextView) findViewById(R.id.text);
       // JsonFileReader reader = new JsonFileReader();

         Database database= new Database(getApplicationContext());


        try {
            JsonReader j = new JsonReader(new InputStreamReader(in,"UTF-8"));

            JsonFileReader reader = new JsonFileReader();
            ArrayList<StatEntry> statEntries = reader.readFile(j);


             String s ="";
        for (int i = 0; i < statEntries.size(); i++) {
           // s+=statEntries.get(i).toString()+"\n";
            System.out.println(statEntries.get(i).toString());

            database.addData(getApplicationContext(),statEntries.get(i),"donneesRessources");
        }



        database.searchdata(getApplicationContext(),"");



        t.setText(s);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
