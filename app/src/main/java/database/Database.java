package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import reader.StatEntry;

/**
 * Created by 张广洁 et Morgan on 2018/4/9.
 */

public class Database extends SQLiteOpenHelper {

    private final static String DB_NAME = "BDDRessource";
    private final static int VERSION = 1;
    //  enum Resource {GPS, MobileData, Wifi, SMS, Contacts} ;
    private static Database instance = null;
    SQLiteDatabase db;

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    }

    public Database(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String enumTable = "CREATE TABLE ressources(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "identifiant INTEGER,"+
                "type TEXT," +
                "ressourcegroup TEXT" +
                ");";
//TODO



        String addRessource0 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('0','GPS','Location')";
        String addRessource1 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('1','Coarse','Location')";
        String addRessource2 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('2','MobileData','Communication')";
        String addRessource3 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('3','Wifi','Communication')";
        String addRessource4 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('4','Bluetooth','Communication')";
        String addRessource5 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('5','NFC','Communication')";
        String addRessource6 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('6','Camera','Peripheral')";
        String addRessource7 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('7','Microphone','Peripheral')";
        String addRessource8 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('8','Sensors','Peripheral')";
        String addRessource9 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('9','Sms','PersonnalInformation')";
        String addRessource10 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('10','Contacts','PersonnalInformation')";
        String addRessource11 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('11','Phone','PersonnalInformation')";
        String addRessource12 = "INSERT INTO ressources (identifianttype,ressourcegroup) VALUES ('12','Identification','PersonnalInformation')";
        String addRessource13 = "INSERT INTO ressources (identifianttype,ressourcegroup) VALUES ('13','InternalStorage','Storage')";
        String addRessource14 = "INSERT INTO ressources (identifianttype,ressourcegroup) VALUES ('14','ExternalStorage','Storage')";
        String addRessource15 = "INSERT INTO ressources (identifianttype,ressourcegroup) VALUES ('15','DrawOverApplications','HighRisksResources')";
        String addRessource16 = "INSERT INTO ressources (identifianttype,ressourcegroup) VALUES ('16','AutomationServices','HighRisksResources')";
        String addRessource17 = "INSERT INTO ressources (identifianttype,ressourcegroup) VALUES ('17','SystemsSettings','HighRisksResources')";

        db.execSQL(enumTable);
        db.execSQL(addRessource0);
        db.execSQL(addRessource1);
        db.execSQL(addRessource2);
        db.execSQL(addRessource3);
        db.execSQL(addRessource4);
        db.execSQL(addRessource5);
        db.execSQL(addRessource6);
        db.execSQL(addRessource7);
        db.execSQL(addRessource8);
        db.execSQL(addRessource9);
        db.execSQL(addRessource10);
        db.execSQL(addRessource11);
        db.execSQL(addRessource12);
        db.execSQL(addRessource13);
        db.execSQL(addRessource14);
        db.execSQL(addRessource15);
        db.execSQL(addRessource16);
        db.execSQL(addRessource17);

        String createTable = "CREATE TABLE donneesRessources("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName VARCHAR(20),"
                + "RESSOURCES REFERENCES ressources(type)," +
                "detail TEXT);";
         /* ici , il veut des entiers mais on a besoin des string */
        db.execSQL(createTable);

        String fiveMinutes = "CREATE TABLE fiveMinutes("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName VARCHAR(20),"
                + "RESSOURCES REFERENCES ressources(type)," +
                "detail INTEFER);";
        db.execSQL(fiveMinutes);

        String onehour = "CREATE TABLE oneHour("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName VARCHAR(20),"
                + "RESSOURCES REFERENCES ressources(type)," +
                "detail TEXT);";
        db.execSQL(onehour);

        String oneday = "CREATE TABLE oneDay("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName VARCHAR(20),"
                + "RESSOURCES REFERENCES ressources(type)," +
                "detail TEXT);";
        db.execSQL(oneday);

        String oneweek = "CREATE TABLE oneWeek("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName VARCHAR(20),"
                + "RESSOURCES REFERENCES ressources(type)," +
                "detail TEXT);";
        db.execSQL(oneweek);

        String oneyear = "CREATE TABLE oneYear("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName VARCHAR(20),"
                + "RESSOURCES REFERENCES ressources(type)," +
                "detail TEXT);";
        db.execSQL(oneyear);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public  void createsqlitedatabase(Context context){

         db = Database.getInstance(context).getWritableDatabase();

    }
    public Cursor searchdata(Context context, String sql) {

        String columns[] = {"id", "timeStamp", "appName", "RESSOURCES"};
        //Cursor cursor = db.query(
        //      "donneesRessources",columns , null, null, null, null, null);

        Cursor cursor = db.rawQuery(sql, null);
/*
        while(cursor.moveToNext()){

            int  id = cursor.getInt(cursor.getColumnIndex("id"));

            String  time = cursor.getString(cursor.getColumnIndex("timeStamp"));

            String  appname = cursor.getString(cursor.getColumnIndex("appName"));
            String  resource = cursor.getString(cursor.getColumnIndex("RESSOURCES"));

            System.out.println("mla"+id+" "+time+" "+appname+" "+resource);

        }

  */
        return cursor;

    }

    //to add datas
    public void addData(Context context, StatEntry statEntry, String tablename) {

      //  SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timeStamp", statEntry.getTimestamp().getTime());
        contentValues.put("appName", statEntry.getApp_name());
        contentValues.put("ressources", statEntry.getResource());
        contentValues.put("detail", statEntry.getDetails().toString());
        db.insert(tablename, null, contentValues);


    }
/*
    // on utilise jamais
    private void deleteData(Context context, int id, String tablename) {
        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        db.delete(tablename, "id=" + id, null);
        db.close();
    }
*/
    // 5 min to hour
    private void toHour(Context context) {

        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ArrayList<String> apps = new ArrayList<>();
        ArrayList<String> ressourcesNames = new ArrayList<>();

        Cursor appNames = db.rawQuery("select appName from fiveMinutes", null);
        Cursor ressources = db.rawQuery("select RESSOURCES from fiveMinutes", null);

        while (appNames.moveToNext()) {
            String appname = appNames.getString(appNames.getColumnIndex("appName"));
            apps.add(appname);
        }
        appNames.close();

        while (ressources.moveToNext()) {
            String toadd = ressources.getString(ressources.getColumnIndex("RESSOURCES"));
            ressourcesNames.add(toadd);
        }
        ressources.close();

        for (int i = 0; i < apps.size(); i++) {
            int fois;
            String request = "select COUNT(*) from fiveMinutes where appName = " + apps.indexOf(i) +
                    "group by RESSOURCES";
            Cursor cursor = db.rawQuery(request, null);

        }
    }

    public void deletealldata(Context context, String tablename) {

        //SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();

        db.delete(tablename, null, null);
       // db.close();

    }

    public void affichetable(Context context, String tablename) {
       // SQLiteDatabase database = Database.getInstance(context).getWritableDatabase();
        String sql = "select * from " + tablename;
        Cursor cursor = db.rawQuery(sql, null);

        System.out.println("tablename : " + tablename);
        System.out.println("timestamp  |  appname  |  res  |  detail");
        while (cursor.moveToNext()) {
            // add data to tables2
            String timestamp = cursor.getString(cursor.getColumnIndex("timeStamp"));
            String appname = cursor.getString(cursor.getColumnIndex("appName"));
            String res = cursor.getString(cursor.getColumnIndex("RESSOURCES"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));

            System.out.println(timestamp + "   |  " + appname + "  |  " + res + "  |  " + detail);

            // System.out.println(appname+" "+res);
        }
        cursor.close();

    }

    public int tableLength(Context context,String tablename){
        int length=0;
        String sql = "select COUNT(*) from " + tablename;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            length = cursor.getColumnCount();
        }
        cursor.close();
        return length;
    }

    public String returnResourceName (int resourceIndex) {

        int index = 0;

        String sql = "select type from ressources where index ="+resourceIndex;

        String resourceName= "";

//TODO

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            resourceName = cursor.getString(cursor.getColumnIndex("type"));
       }
        cursor.close();
        return resourceName;
    }


}
