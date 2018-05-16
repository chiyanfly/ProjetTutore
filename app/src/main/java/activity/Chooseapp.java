package activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ressources.R;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import Database.Database;

/**
 * Created by hxu on 15/05/18.
 */

public class Chooseapp extends Activity {


    ArrayList<String> appnamelist = new ArrayList<>();

    Database database;
    ListView appnamelistview;
    MultiValueMap map_info = new MultiValueMap();


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

        database = Database.getInstance(getApplicationContext());
        getdata();
        appnamelistview = (ListView) findViewById(R.id.id_applist);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Chooseapp.this,
                android.R.layout.simple_list_item_1, appnamelist);
        appnamelistview.setAdapter(adapter);


        appnamelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                String appname = appnamelist.get(position);
                System.out.println(appname);
                String sql = "select * from donneesRessources where appName = '" + appname + "'";

                Cursor c = database.searchdata(getApplicationContext(), sql);

                // for each res how many times   there are

                ArrayList<String> reslist = new ArrayList<>();
                ArrayList<String> timelist = new ArrayList<>();

                while (c.moveToNext()) {


                    String res = c.getString(c.getColumnIndex("RESSOURCES"));

                    String timestamp = c.getString(c.getColumnIndex("timeStamp"));

                    put_info_into_map(res, timestamp);
                    timelist.add(timestamp);
                    if (!reslist.contains(res))
                        reslist.add(res);
                }

                Timestamp timebegin = begintime(timelist);
                Timestamp timeend = endtime(timelist);

                System.out.println(timebegin + " " + timeend);

                long timeinterval = (timebegin.getTime() - timeend.getTime()) / 5;
// let's say we just divide the interval into 5 parts


                HashMap<String, HashMap<Integer, Integer>> graphsourcemap = new HashMap<>();

                for (String res : reslist) {

                    HashMap<Integer, Integer> graphsourcemap_for_one_res = new HashMap<>();
                    Collection timecollection_for_one_res = map_info.getCollection(res);
                    Iterator i = timecollection_for_one_res.iterator();

                    int t0 = 0;
                    int t1 = 0;
                    int t2 = 0;
                    int t3 = 0;
                    int t4 = 0;
                    while (i.hasNext()) {
                        Timestamp time = new Timestamp(Long.parseLong((String) i.next()));
                        int t = 0;
                        if (timeinterval != 0)
                            t = (int) (time.getTime() - timebegin.getTime() / timeinterval);


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
                            default:
                                break;
                        }

                    }
                    graphsourcemap_for_one_res.put(0, t0);
                    graphsourcemap_for_one_res.put(1, t1);
                    graphsourcemap_for_one_res.put(2, t2);
                    graphsourcemap_for_one_res.put(3, t3);
                    graphsourcemap_for_one_res.put(4, t4);

                    System.out.println(res +" "+t0 + " " + t1 + " " + t2 + " " + t3 + " " + t4);
                    graphsourcemap.put(res, graphsourcemap_for_one_res);
                }


                c.close();

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

    private void put_info_into_map(String res, String timestamp) {

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
    void getdata() {

        // select appname from table
        Cursor c = database.searchdata(getApplicationContext(), "select appName from donneesRessources");


        while (c.moveToNext()) {

            String appname = c.getString(c.getColumnIndex("appName"));
            System.out.println("dqd");

            if(!appnamelist.contains(appname))
            appnamelist.add(appname);
        }
        c.close();
    }
}
