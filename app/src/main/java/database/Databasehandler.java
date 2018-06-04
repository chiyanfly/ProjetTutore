package database;

import android.content.Context;
import android.database.Cursor;
import android.util.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;

import reader.Detail;
import reader.JsonFileReader;
import reader.StatEntry;

/**
 * Created by XH on 2018/5/20.
 */
/*this class is for the use of handling the events of the new datas
  used in the situation that broadcast receiver receives  the events
  of new datas and it  will use this class to manipulate the database
  ex : move 5min datatable to 1h datatable and  put the new datas into the
  5min table

  */
public class Databasehandler {

    private Database database;
    Context context;
    public Databasehandler(Context c) {
        context = c;
        database = database.getInstance(context);
        database.createsqlitedatabase(context);
    }

/*
*
* copy data from 5min table to 1h table
*
*
* */


    public Database getDatabase() {
        return database;
    }

    public void copydata(String table1, String table2) {

        String sql = "select * from " + table1;
        Cursor cursor = database.searchdata(context, sql);

        while (cursor.moveToNext()) {
            // add data to tables2
            Long timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("timeStamp")));
            int appname = Integer.parseInt(cursor.getString(cursor.getColumnIndex("appName")));
            int res = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RESSOURCES")));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));
            database.addDataDay(context, timestamp,appname,res, detail,table2);
            // System.out.println(appname+" "+res);
        }
        cursor.close();

    }



    public void copydataMoyenne(String table1, String table2) {

        String sql = "select * from " + table1;
        Cursor cursor = database.searchdata(context, sql);

        while (cursor.moveToNext()) {
            // add data to tables2
            Long timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("timeStamp")));
            int appname = Integer.parseInt(cursor.getString(cursor.getColumnIndex("appName")));
            int res = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RESSOURCES")));
            int moyenne = Integer.parseInt(cursor.getString(cursor.getColumnIndex("moyenne")));



            database.addDataMoyenne(context, timestamp,appname,res, moyenne, table2);

            // System.out.println(appname+" "+res);
        }
        cursor.close();

    }

    /*
    *
    * delete all the existing data in the table
    * */
    public void cleandata(String tablename) {

        database.deletealldata(context, tablename);

    }
// just for testing . initialise the data for data move test

    public void initdata(String filename, String tablmename) {
        InputStream in = context.getResources().openRawResource(context.getResources().getIdentifier(filename, "raw", context.getPackageName()));
        JsonFileReader reader = new JsonFileReader();
        try {
            JsonReader j = new JsonReader(new InputStreamReader(in, "UTF-8"));


            ArrayList<StatEntry> statEntries = reader.readFile(j);
            String s = "";
            for (int i = 0; i < statEntries.size(); i++) {
                // s+=statEntries.get(i).toString()+"\n";
                System.out.println(statEntries.get(i).toString());

                database.addData(context, statEntries.get(i), tablmename);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
//There are three things to do : 1 transfer currentDay data to previousDay 2 fill in the table currentMonth with currentDay
// 3 clear all data in currentDay
    public void dayMonthTransfer(Context context){

        copydata("currentDay","previousDay");

        String sql = "SELECT timeStamp,appName,RESSOURCES, COUNT(RESSOURCES) as moyenne from currentDay GROUP BY appName,RESSOURCES ";
        String affichage = "";
        Cursor cursor = database.searchdata(context,sql);

        while (cursor.moveToNext()) {

            Long timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("timeStamp")));
            int appname = Integer.parseInt(cursor.getString(cursor.getColumnIndex("appName")));
            int res = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RESSOURCES")));
            int moyenne = Integer.parseInt(cursor.getString(cursor.getColumnIndex("moyenne")));
            System.out.println("appName "+appname+" resource "+res+" moyenne "+moyenne);
            database.addDataMoyenne(context,timestamp,appname,res,moyenne,"currentMonth");

        }

        cursor.close();

        cleandata("currentDay");
    }

    //There are three things to do : 1 transfer currentMonth data to previousMonth 2 fill in the table currentYear with currentMonth
    // 3 clear all data in currentMonth
    public void monthYearTransfer(Context context){

        copydataMoyenne("currentMonth","previousMonth");

        String sql = "SELECT timeStamp,appName,RESSOURCES,COUNT(moyenne) as average from currentMonth GROUP BY appName,RESSOURCES ";
        String affichage = "";
        Cursor cursor = database.searchdata(context,sql);

        while (cursor.moveToNext()) {
            Long timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("timeStamp")));
            int appname = Integer.parseInt(cursor.getString(cursor.getColumnIndex("appName")));
            int res = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RESSOURCES")));
            int average = Integer.parseInt(cursor.getString(cursor.getColumnIndex("average")));
            //System.out.println("appName "+appname+" resource "+res+" moyenne "+average);
            database.addDataMoyenne(context,timestamp,appname,res,average,"currentYear");

        }

        cursor.close();

        cleandata("currentMonth");
    }


}