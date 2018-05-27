package tool;

import android.content.Context;
import android.database.Cursor;

import org.apache.commons.collections.map.MultiValueMap;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import Database.Database;

/**
 * Created by XH on 21/05/2018.
 */

public class DataToimagetool {


    public static HashMap<String, HashMap<Integer, Integer>> Table_to_graphsourcemap(Context context,String appname,String tablename) {

        String sql = "select * from "+tablename+" where appName = '" + appname + "'";
        System.out.println(sql);
        Database database = Database.getInstance(context);
        Cursor c = database.searchdata(context, sql);

        // for each res how many times   there are
        MultiValueMap map_info = new MultiValueMap();
        ArrayList<String> reslist = new ArrayList<>();
        ArrayList<String> timelist = new ArrayList<>();

        while (c.moveToNext()) {
            String res = c.getString(c.getColumnIndex("RESSOURCES"));
            String timestamp = c.getString(c.getColumnIndex("timeStamp"));
            map_info.put(res,timestamp);
            timelist.add(timestamp);
            if (!reslist.contains(res))
                reslist.add(res);
        }
            c.close();
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
        return graphsourcemap;


    }

    private  static Timestamp begintime(ArrayList<String> timelist) {


        Timestamp result = new Timestamp(Long.parseLong(timelist.get(0)));

        for (String s : timelist) {


            if (new Timestamp(Long.parseLong(s)).before(result)) {


                result = new Timestamp(Long.parseLong(s));

            }


        }

        return result;
    }


    private static  Timestamp endtime(ArrayList<String> timelist) {
        Timestamp result = new Timestamp(Long.parseLong(timelist.get(0)));

        for (String s : timelist) {


            if (new Timestamp(Long.parseLong(s)).after(result)) {


                result = new Timestamp(Long.parseLong(s));

            }


        }

        return result;
    }

    public static HashMap<String, HashMap<Integer, Integer>> Table_to_graphsourcemap2(Context context,String resname,String tablename) {
        String sql = "select * from "+tablename+" where RESSOURCES = '" + resname + "'";
        System.out.println(sql);
        Database database = Database.getInstance(context);
        Cursor c = database.searchdata(context, sql);

        // for each res how many times there are
        MultiValueMap map_info = new MultiValueMap();
        ArrayList<String> applist = new ArrayList<>();
        ArrayList<String> timelist = new ArrayList<>();

        while (c.moveToNext()) {
            String app = c.getString(c.getColumnIndex("appName"));
            String timestamp = c.getString(c.getColumnIndex("timeStamp"));
            map_info.put(app,timestamp);
            timelist.add(timestamp);
            if (!applist.contains(app))
                applist.add(app);
        }
        c.close();
        Timestamp timebegin = begintime(timelist);
        Timestamp timeend = endtime(timelist);
        long timeinterval = (timeend.getTime() - timebegin.getTime()) / 5;
// let's say we just divide the interval into 5 parts
        System.out.println(timebegin.getTime() + " " + timeend.getTime() + " " + timeinterval);
        HashMap<String, HashMap<Integer, Integer>> graphsourcemap = new HashMap<>();

        for (String app : applist) {

            HashMap<Integer, Integer> graphsourcemap_for_one_app = new HashMap<>();
            System.out.println(app);
            Collection timecollection_for_one_app = map_info.getCollection(app);
            Iterator i = timecollection_for_one_app.iterator();

            int t0 = 0;
            int t1 = 0;
            int t2 = 0;
            int t3 = 0;
            int t4 = 0;
            while (i.hasNext()) {
                //  System.out.println(app +" "+i.next());
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
            // System.out.println(app +" "+t0 + " " + t1 + " " + t2 + " " + t3 + " " + t4);
            graphsourcemap_for_one_app.put(0, t0);
            graphsourcemap_for_one_app.put(1, t1);
            graphsourcemap_for_one_app.put(2, t2);
            graphsourcemap_for_one_app.put(3, t3);
            graphsourcemap_for_one_app.put(4, t4);
            graphsourcemap.put(app, graphsourcemap_for_one_app);
        }
        return graphsourcemap;
    }

}
