package Database;

import android.content.Context;

/**
 * Created by XH on 2018/5/20.
 */
/*this class is for the use of handling the event of the new datas
  used in the situation that broadcast receiver receives  the events
  of new datas it  will use this class to munipulate the database
  ex : move 5min datatable to 1h datatable and  put the new datas into the
  5min table

  */
public class Databasehandler {


    private static Databasehandler databasehandler = null;
    private Database database;


    public static Databasehandler gethandler(Context context) {

        if (databasehandler == null) {
            databasehandler = new Databasehandler(context);
        }
        return databasehandler;

    }


    public Databasehandler(Context context) {
        database = Database.getInstance(context);
    }

/*
*
* move data from 5min table to 1h table
*
*
* */


    private void movedata() {


    }


}

