package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ressources.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import Database.Database;
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
        ImageView gotoApp = (ImageView) findViewById(R.id.gotoApp);
        ImageView gotoRessource = (ImageView) findViewById(R.id.gotoRessource);
        // create database  firstly

        //database= new Database(getApplicationContext());


        adddata();
        //We will go to the page app
        gotoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choosemode.this, Chooseapp.class);
                startActivity(i);
            }
        });
        //Click this image, we will goto the page ressource
        gotoRessource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choosemode.this, Chooseres.class);
                startActivity(i);
            }
        });


    }

    void adddata() {


        InputStream in = getResources().openRawResource(getResources().getIdentifier("input", "raw", getPackageName()));

        try {
            JsonReader j = new JsonReader(new InputStreamReader(in, "UTF-8"));

            JsonFileReader reader = new JsonFileReader();
            ArrayList<StatEntry> statEntries = reader.readFile(j);


            String s = "";
            for (int i = 0; i < statEntries.size(); i++) {
                // s+=statEntries.get(i).toString()+"\n";
                System.out.println(statEntries.get(i).toString());

                Database.getInstance(getApplicationContext()).addData(getApplicationContext(), statEntries.get(i));
            }



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}