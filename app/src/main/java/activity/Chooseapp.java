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

import org.apache.commons.collections.map.MultiValueMap;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import database.Database;

/**
 * Created by hxu on 15/05/18.
 */

public class Chooseapp extends Activity {


    private ArrayList<String> appnamelist = new ArrayList<>();
    private Database database;
    private ListView appnamelistview;


    /**
     * String enumTable = "CREATE TABLE ressources("+
     * "id INTEGER PRIMARY KEY AUTOINCREMENT,
     * resourcetype TEXT
     * <p>
     * " +
     * "type TEXT"+
     * ");";
     * <p>
     * <p>
     * <p>
     * String createTable = "CREATE TABLE donneesRessources("
     * +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
     * +"timeStamp TIMESTAMP,"
     * +"appName VARCHAR(20),"
     * +"RESSOURCES REFERENCES ressources(type),"+
     * "detail TEXT);";
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chooseapp);

        database= Database.getInstance(getApplicationContext());

        getappname("fiveMinutes");
        appnamelistview = (ListView) findViewById(R.id.id_applist);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chooseapp.this,
                android.R.layout.simple_list_item_1, appnamelist);
        appnamelistview.setAdapter(adapter);


        appnamelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String appname = appnamelist.get(position);
                System.out.println(appname);
                HashMap<String, HashMap<Integer, Integer>> graphsourcemap
               = DataToimagetool.Table_to_graphsourcemap(getApplicationContext(),appname,"fiveMinutes");

                Intent intent = new Intent();
                intent.setClass(Chooseapp.this, Showimageforeachapp.class);
                intent.putExtra("graphinfo", (Serializable) graphsourcemap);
                intent.putExtra("appname",appname);
                startActivity(intent);


                /*
                MultiValueMap map_info = new MultiValueMap();

                String sql = "select * from fiveMinutes where appName = '" + appname + "'";

                Cursor c = database.searchdata(getApplicationContext(), sql);

                // for each res how many times   there are

                ArrayList<String> reslist = new ArrayList<>();
                ArrayList<String> timelist = new ArrayList<>();

                while (c.moveToNext()) {


                    String res = c.getString(c.getColumnIndex("RESSOURCES"));

                    String timestamp = c.getString(c.getColumnIndex("timeStamp"));

                    put_info_into_map(map_info, res, timestamp);
                    timelist.add(timestamp);
                    if (!reslist.contains(res))
                        reslist.add(res);
                }

                Timestamp timebegin = begintime(timelist);
                Timestamp timeend = endtime(timelist);
                long timeinterval = (timeend.getTime() - timebegin.getTime()) / 5;
// let's say we just divide the interval into 5 parts
 System.out.println(timebegin.getTime() + " " + timeend.getTime() + " " + timeinterval);
                HashMap<String, HashMap<Integer, Integer>> graphsourcemap = new HashMap<>();

                for (String res : reslist) {

                    HashMap<Integer, Integer> graphsourcemap_for_one_res = new HashMap<>();
                    System.out.println(res);
                    Collection timecollection_for_one_res = map_info.getCollection(res);
                    Iterator i = timecollection_for_one_res.iterator();

                    int t0 = 0;
                    int t1 = 0;
                    int t2 = 0;
                    int t3 = 0;
                    int t4 = 0;
                    while (i.hasNext()) {


                        //  System.out.println(res +" "+i.next());

                        Timestamp time = new Timestamp(Long.parseLong((String) i.next()));
                        int t = 0;
                        if (timeinterval != 0) {
                            t = (int) ((time.getTime() - timebegin.getTime()) / timeinterval);
                            // System.out.println(t);
                        }
                        switch (t) {
                            case 0:
                                t0++;
                                break;
                            case 1:
                                t1++;
                                break;
                            case 2:
                                t2++;
                                break;
                            case 3:
                                t3++;
                                break;
                            case 4:
                                t4++;
                                break;
                            case 5:
                                t4++;
                                break;
                            default:
                                break;
                        }


                    }
                    // System.out.println(res +" "+t0 + " " + t1 + " " + t2 + " " + t3 + " " + t4);
                    graphsourcemap_for_one_res.put(0, t0);
                    graphsourcemap_for_one_res.put(1, t1);
                    graphsourcemap_for_one_res.put(2, t2);
                    graphsourcemap_for_one_res.put(3, t3);
                    graphsourcemap_for_one_res.put(4, t4);
                    graphsourcemap.put(res, graphsourcemap_for_one_res);
                }

/* final result test
                System.out.println("testresult");

                for (String res : graphsourcemap.keySet()) {
                    HashMap<Integer, Integer> map = graphsourcemap.get(res);
                    System.out.println(res);
                    for (Integer interval : map.keySet()) {
                        System.out.println(interval);
                        System.out.println(map.get(interval));

                    }

                }
                System.out.println("endtestresult");
//test success
 after this we use the graphsourcemap as the data resource for paint the image


                */


            }
        });


    }

    private Timestamp begintime(ArrayList<String> timelist) {


        Timestamp result = new Timestamp(Long.parseLong(timelist.get(0)));

        for (String s : timelist) {


            if (new Timestamp(Long.parseLong(s)).before(result)) {


                result = new Timestamp(Long.parseLong(s));

            }


        }

        return result;
    }


    private Timestamp endtime(ArrayList<String> timelist) {
        Timestamp result = new Timestamp(Long.parseLong(timelist.get(0)));

        for (String s : timelist) {


            if (new Timestamp(Long.parseLong(s)).after(result)) {


                result = new Timestamp(Long.parseLong(s));

            }


        }

        return result;
    }

    private void put_info_into_map(MultiValueMap map_info, String res, String timestamp) {

        map_info.put(res, timestamp);
    }


        /*      switch (res){


            case "CPU":


                break;


            case "GPS":


                break;


            case "Wifi":


                break;


            case "SMS":


                break;


            case "Contacts":


                break;


            default:break;



        }

*/


    // get appname from the database and put it in the appnamelist
    void getappname(String tablename) {

        // select appname from table
        String sql="select appName from "+tablename;
        Cursor c = database.searchdata(getApplicationContext(), sql);
        while (c.moveToNext()) {

            String appname = c.getString(c.getColumnIndex("appName"));

            if (!appnamelist.contains(appname))
                appnamelist.add(appname);
        }
        c.close();
    }
}
