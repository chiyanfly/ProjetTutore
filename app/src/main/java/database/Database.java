package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;

import reader.Detail;
import reader.JsonFileReader;
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
        String addRessource12 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('12','Identification','PersonnalInformation')";
        String addRessource13 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('13','InternalStorage','Storage')";
        String addRessource14 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('14','ExternalStorage','Storage')";
        String addRessource15 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('15','DrawOverApplications','HighRisksResources')";
        String addRessource16 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('16','AutomationServices','HighRisksResources')";
        String addRessource17 = "INSERT INTO ressources (identifiant,type,ressourcegroup) VALUES ('17','SystemsSettings','HighRisksResources')";

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

        String createAppTable = "CREATE TABLE appNames(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "identifiant INTEGER,"+
                "name VARCHAR(20)" +
                ");";
        db.execSQL(createAppTable);

        String insertAppName = "INSERT INTO appNames (identifiant,name) VALUES ('1','myApp0')";
        db.execSQL(insertAppName);


        String actualDay = "CREATE TABLE currentDay("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "detail TEXT);";
        db.execSQL(actualDay);

        String createTable = "CREATE TABLE donneesRessources("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "detail TEXT);";
         /* ici , il veut des entiers mais on a besoin des string */

        db.execSQL(createTable);

        String precedentDay = "CREATE TABLE previousDay("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "detail TEXT);";
        db.execSQL(precedentDay);

        String actualMonth = "CREATE TABLE currentMonth("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "moyenne INTEGER);";
        db.execSQL(actualMonth);

        String precedentMonth = "CREATE TABLE previousMonth("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "moyenne INTEGER);";
        db.execSQL(precedentMonth);

        String actualYear = "CREATE TABLE currentYear("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "moyenne INTEGER);";
        db.execSQL(actualYear);

        String precedentYear = "CREATE TABLE previousYear("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "timeStamp TIMESTAMP,"
                + "appName REFERENCES appNames(identifiant),"
                + "RESSOURCES REFERENCES ressources(identifiant)," +
                "moyenne INTEGER);";
        db.execSQL(precedentYear);

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

    //to add datas to details
    public void addData(Context context, StatEntry statEntry, String tablename) {
      //  SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timeStamp", statEntry.getTimestamp().getTime());
        int appname = getAppIdByName(statEntry.getApp_name());
        System.out.println("La fonction getappIpByName return "+appname);
        contentValues.put("appName",appname );
        System.out.println("dans add data on a une sortie de getappbyname de : "+appname);
        int resource = getResourceIdByName(statEntry.getResource());
        System.out.println("La fonction getresIpByName return "+resource);
        contentValues.put("RESSOURCES", resource);
        contentValues.put("detail", statEntry.getDetails().toString());
        db.insert(tablename, null, contentValues);
    }

    public void addDataDay(Context context, long timestamps,int appname,int res,String detail,String tablename) {
        //  SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timeStamp", timestamps);
        contentValues.put("appName",appname);
        contentValues.put("RESSOURCES", res);
        contentValues.put("detail", detail);
        db.insert(tablename, null, contentValues);
    }


    //to add datas to moyenne
    public void addDataMoyenne(Context context,long timeStamp,int appName,int resource,int moyenne, String tablename) {
        //  SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timeStamp", timeStamp);
        contentValues.put("appName",appName );
        contentValues.put("ressources", resource);
        contentValues.put("moyenne",moyenne);
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
    // 5 min to hour maybe never use
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

    // this function present the tables with detail column
    public void affichetable(Context context, String tablename) {
       // SQLiteDatabase database = Database.getInstance(context).getWritableDatabase();
        String sql = "select * from " + tablename;
        Cursor cursor = db.rawQuery(sql, null);

        System.out.println("tablename : " + tablename);
        System.out.println("timestamp  |  appname  |  res  |  detail");
        while (cursor.moveToNext()) {
            // add data to tables2
            String timestamp = cursor.getString(cursor.getColumnIndex("timeStamp"));
            int appname = Integer.parseInt(cursor.getString(cursor.getColumnIndex("appName")));
            String res = cursor.getString(cursor.getColumnIndex("RESSOURCES"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));

            System.out.println(timestamp + "   |  " + appname + "  |  " + res + "  |  " + detail);

            // System.out.println(appname+" "+res);
        }
        cursor.close();

    }

    public int tableLength(Context context,String tablename){
        int length=0;
        String sql = "select * from " + tablename;
        Cursor cc = db.rawQuery(sql,null) ;
        length = cc.getCount();
        cc.close();
        return length;
    }

    public String returnResourceName (int resourceIndentifiant) {
        int index = 0;
        String sql = "select type from ressources where identifiant = " + resourceIndentifiant;
        String resourceName= "";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            resourceName = cursor.getString(cursor.getColumnIndex("type"));
       }
        cursor.close();
        return resourceName;
    }

    public int getResourceIdByName(String resource){
        int id = 0;
        String sql = "select identifiant from ressources where type = " + "\""+resource+"\"";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            id = Integer.parseInt(  cursor.getString(cursor.getColumnIndex("identifiant")));
        }
        cursor.close();
        return id;
    }

    public String returnAppName (int appIdentifiant) {
        int index = 0;
        String sql = "select type from appNames where identifiant = " + appIdentifiant;
        String appName= "";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            appName = cursor.getString(cursor.getColumnIndex("name"));
        }
        if(appName.equals("")){
            System.out.println("Don't find the AppName with this index "+appIdentifiant);
        }
        cursor.close();
        return appName;
    }

    /**
     *This function find appId by his name, if not found, returns -1
     * @param appName
     * @return
     */
    public int getAppIdByName(String appName){
        int id = -1;
        String sql1 = "select identifiant from appNames where name = \"" +appName+"\"";
        Cursor cursor1 = db.rawQuery(sql1, null);
        if(cursor1.moveToFirst()==false){
           // System.out.println("You don't have this app name in your table "+appName);
            id = addNewAppName(appName);
        }else{
            System.out.println("ON PASSE PAR LENDROIT OU TU CROIS AUON PASSE PAS");
            id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("identifiant")));
        }
        System.out.println("id retenue: "+id);
        cursor1.close();
        return id;
    }

    public int addNewAppName(String appName){
        int newId = 0;
        int aux;
        String request = "select identifiant from appNames";
        Cursor cursor = db.rawQuery(request,null);
        while(cursor.moveToNext()){
            aux = Integer.parseInt(cursor.getString(cursor.getColumnIndex("identifiant")));
            if(aux>newId) {
                newId = aux;
            }
        }
        newId++;
        String newAppName = "INSERT INTO appNames (identifiant,name) VALUES (\'"+newId+"\',\'"+appName+"\')";
        db.execSQL(newAppName);
        return newId;
    }

    /**
     *  This function add data from file
     * @param context
     * @param pathName
     * @param table
     */
    public void addDataFromFile(Context context,String pathName,String table){

        try{

            System.out.println("pathname is "+pathName);
            File file = new File(pathName);
            InputStream in = new FileInputStream(file);
            JsonReader j = new JsonReader(new InputStreamReader(in,"UTF-8"));
            JsonFileReader reader = new JsonFileReader();
            ArrayList<StatEntry> statEntries = reader.readFile(j);

            if(statEntries==null){
                System.out.println("StatEntry is null");
            }else{

                String s ="";
                for (int i = 0; i < statEntries.size(); i++) {
                    // s+=statEntries.get(i).toString()+"\n";
                    //System.out.println(statEntries.get(i).toString());

                    this.addData(context,statEntries.get(i),table);
            }

            }

        }catch (IOException e){

        }
    }

    public void affichageAppNames(){
        String toExcute = "select * from appNames";
        Cursor cursor = db.rawQuery(toExcute,null);
        System.out.println("name"+" | "+"identifiant");
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String id = cursor.getString(cursor.getColumnIndex("identifiant"));
            System.out.println(name+"   "+id);
        }

    }

    public void affichageTableMoyenne(String table) {
        String toExcute = "select * from "+table;
        Cursor cursor = db.rawQuery(toExcute,null);
        System.out.println("timestamps"+" | "+"name"+" | "+"ressources"+" | "+"moyenne");
        while (cursor.moveToNext()){

            long timeStamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("timeStamp")));
            int name = Integer.parseInt(cursor.getString(cursor.getColumnIndex("appName")));
            int resource = Integer.parseInt(cursor.getString(cursor.getColumnIndex("RESSOURCES")));
            int moyenne = Integer.parseInt(cursor.getString(cursor.getColumnIndex("moyenne")));
            System.out.println(timeStamp+"   "+name+"   "+resource+"   "+moyenne);
        }

    }
}
