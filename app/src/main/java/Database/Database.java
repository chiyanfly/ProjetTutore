package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Enumeration;

import reader.StatEntry;

/**
 * Created by 张广洁 et Morgan on 2018/4/9.
 */

public class Database extends SQLiteOpenHelper {

    private final static String DB_NAME = "BDDRessource";
    private final static int VERSION = 1;
  //  enum Resource {GPS, MobileData, Wifi, SMS, Contacts} ;
    private static Database instance = null;

    public static Database getInstance(Context context){
        if(instance == null){
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

        String enumTable = "CREATE TABLE ressources("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT,"+
                "ressourcegroup TEXT"+
                ");";
//TODO
        String addRessource0 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('GPS','Location')";
        String addRessource1 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('MobileData','Communication')";
        String addRessource2 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Wifi','Communication')";
        String addRessource3 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Bluetooth','Communication')";
        String addRessource4 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('NFC','Communication')";
        String addRessource5 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Camera','Peripheral')";
        String addRessource6 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Microphone','Peripheral')";
        String addRessource7 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Sensors','Peripheral')";
        String addRessource8 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Sms','PersonnalInformation')";
        String addRessource9 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Contacts','PersonnalInformation')";
        String addRessource10 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('Phone','PersonnalInformation')";
        String addRessource11 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('InternalStorage','Storage')";
        String addRessource12 = "INSERT INTO ressources (type,ressourcegroup) VALUES ('ExternalStorage','Storage')";

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

        String createTable = "CREATE TABLE donneesRessources("
                 +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +"timeStamp TIMESTAMP,"
                 +"appName VARCHAR(20),"
                 +"RESSOURCES REFERENCES ressources(type),"+
                 "detail TEXT);";
         /* ici , il veut des entiers mais on a besoin des string */
        db.execSQL(createTable);

        String fiveMinutes =  "CREATE TABLE fiveMinutes("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"timeStamp TIMESTAMP,"
                +"appName VARCHAR(20),"
                +"RESSOURCES REFERENCES ressources(type),"+
                "detail INTEFER);";
        db.execSQL(fiveMinutes);

        String onehour =  "CREATE TABLE oneHour("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"timeStamp TIMESTAMP,"
                +"appName VARCHAR(20),"
                +"RESSOURCES REFERENCES ressources(type),"+
                "detail TEXT);";
        db.execSQL(onehour);

        String  oneday="CREATE TABLE oneDay("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"timeStamp TIMESTAMP,"
                +"appName VARCHAR(20),"
                +"RESSOURCES REFERENCES ressources(type),"+
                "detail TEXT);";
        db.execSQL(oneday);

        String oneweek =  "CREATE TABLE oneWeek("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"timeStamp TIMESTAMP,"
                +"appName VARCHAR(20),"
                +"RESSOURCES REFERENCES ressources(type),"+
                "detail TEXT);";
        db.execSQL(oneweek);

        String oneyear =  "CREATE TABLE oneYear("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"timeStamp TIMESTAMP,"
                +"appName VARCHAR(20),"
                +"RESSOURCES REFERENCES ressources(type),"+
                "detail TEXT);";
        db.execSQL(oneyear);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    public Cursor searchdata(Context context,String sql){


        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();


        String columns[]={"id","timeStamp","appName","RESSOURCES"};
        //Cursor cursor = db.query(
          //      "donneesRessources",columns , null, null, null, null, null);


        Cursor cursor =   db.rawQuery(sql,null);

/*
        while(cursor.moveToNext()){

            int  id = cursor.getInt(cursor.getColumnIndex("id"));

            String  time = cursor.getString(cursor.getColumnIndex("timeStamp"));

            String  appname = cursor.getString(cursor.getColumnIndex("appName"));
            String  resource = cursor.getString(cursor.getColumnIndex("RESSOURCES"));

            System.out.println("mla"+id+" "+time+" "+appname+" "+resource);

        }

  */
        return  cursor;

    }
    //to add datas
    public void addData(Context context, StatEntry statEntry){

        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timeStamp", statEntry.getTimestamp().getTime());
        contentValues.put("appName",statEntry.getApp_name());
        contentValues.put("ressources", String.valueOf(statEntry.getResource()));
        contentValues.put("detail",statEntry.getDetails().toString());
        db.insert("donneesRessources",null,contentValues);
        db.close();

    }

    // on utilise jamais
    private void deleteData (Context context,int id){
        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        db.delete("donneesRessources","id="+id,null);
        db.close();
    }

    // 5 min to hour
    private void toHour(Context context){

        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ArrayList<String> apps = new ArrayList<>();
        ArrayList<String> ressourcesNames = new ArrayList<>();

        Cursor appNames = db.rawQuery("select appName from fiveMinutes",null);
        Cursor ressources = db.rawQuery("select RESSOURCES from fiveMinutes",null);

        while (appNames.moveToNext()){
           String  appname = appNames.getString(appNames.getColumnIndex("appName"));
           apps.add(appname);
        }
        appNames.close();

        while (ressources.moveToNext()){
            String  toadd = ressources.getString(ressources.getColumnIndex("RESSOURCES"));
            ressourcesNames.add(toadd);
        }
        ressources.close();

        for(int i = 0; i< apps.size();i++){
                int fois;
                String request = "select COUNT(*) from fiveMinutes where appName = "+apps.indexOf(i)+
                        "group by RESSOURCES";
                Cursor cursor = db.rawQuery(request,null);

        }
    }

    public void deletealldata(Context context) {

        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();

        db.delete("donneesRessources",null,null);
        db.close();


    }
}
