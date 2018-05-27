package activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.JsonReader;
import android.view.View;

import com.example.ressources.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import database.Database;
import database.Databasehandler;
import reader.JsonFileReader;
import reader.StatEntry;
/**
 * Created by 张广洁 on 2018/3/15.
 */

public class Choosemode extends Activity {


    // public static  Database database ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort);
        CardView gotoApp = (CardView) findViewById(R.id.gotoApp);
        CardView gotoRessource = (CardView) findViewById(R.id.gotoRessource);
        // create database  firstly

        //database= new Database(getApplicationContext());

        Databasehandler databasehandler= new Databasehandler(getApplicationContext());

        databasehandler.initdata("fiveminutes","fiveMinutes");
        databasehandler.initdata("onehour","oneHour");
//        databasehandler.initdata("onehour","donneesRessources");

        databasehandler.getDatabase().affichetable(getApplicationContext(),"fiveMinutes");
        databasehandler.getDatabase().affichetable(getApplicationContext(),"oneHour");



       // databasehandler.copydata("fiveMinutes","oneHour");
      //  databasehandler.getDatabase().affichetable(getApplicationContext(),"oneHour");


       // databasehandler.cleandata("fiveMinutes");
        //databasehandler.getDatabase().affichetable(getApplicationContext(),"fiveMinutes");




        //  adddata();
        //We will go to the page app
        gotoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choosemode.this, Chooseapp.class);
                startActivity(i);
               // Choosemode.this.finish();
            }
        });
        //Click this image, we will goto the page ressource
        gotoRessource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choosemode.this, Chooseres.class);
                startActivity(i);
                //Choosemode.this.finish();
//                Database.getInstance(getApplicationContext()).deletealldata(getApplicationContext(),"fiveMinutes");
//                Database.getInstance(getApplicationContext()).deletealldata(getApplicationContext(),"oneHour");
//                Database.getInstance(getApplicationContext()).deletealldata(getApplicationContext(),"donneesRessources");
            }
        });
    }
// we need to guarantee that this function executes only once until there is new data
   private void adddata() {


        InputStream in = getResources().openRawResource(getResources().getIdentifier("input", "raw", getPackageName()));

        try {
            JsonReader j = new JsonReader(new InputStreamReader(in, "UTF-8"));

            JsonFileReader reader = new JsonFileReader();
            ArrayList<StatEntry> statEntries = reader.readFile(j);

            for (int i = 0; i < statEntries.size(); i++) {
                // s+=statEntries.get(i).toString()+"\n";
//                System.out.println(statEntries.get(i).toString());

                Database.getInstance(getApplicationContext()).addData(getApplicationContext(), statEntries.get(i),"donneesRessources");
            }

        // for output the database to see
            Cursor c = Database.getInstance(getApplicationContext()).searchdata(getApplicationContext(),"select * from donneesRessources");


            while(c.moveToNext()){

                String  appname = c.getString(c.getColumnIndex("appName"));
                String  res = c.getString(c.getColumnIndex("RESSOURCES"));
               // System.out.println(appname+" "+res);
            }
            c.close();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}