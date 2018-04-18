package com.example.ressources;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

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

        try {
            JsonReader j = new JsonReader(new InputStreamReader(in,"UTF-8"));

            JsonFileReader reader = new JsonFileReader();
            ArrayList<StatEntry> statEntries = reader.readFile(j);


        String s ="";
        for (int i = 0; i < statEntries.size(); i++) {
            s+=statEntries.get(i).toString();
            System.out.println(statEntries.get(i).toString());


        }
        t.setText(s);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
