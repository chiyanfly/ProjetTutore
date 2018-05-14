package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                "type TEXT"+
                ");";
//TODO
        String addRessource = "INSERT INTO ressources (type) VALUES ('GPS')";

        String addRessource1 = "INSERT INTO ressources (type) VALUES ('MobileData')";

        String addRessource2 = "INSERT INTO ressources (type) VALUES ('Wifi')";

        String addRessource3 = "INSERT INTO ressources (type) VALUES ('SMS')";

        String addRessource4 = "INSERT INTO ressources (type) VALUES ('Contacts')";

        db.execSQL(enumTable);
        db.execSQL(addRessource1);
        db.execSQL(addRessource2);
        db.execSQL(addRessource3);
        db.execSQL(addRessource4);

         String createTable = "CREATE TABLE donneesRessources("
                 +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +"timeStamp TIMESTAMP,"
                 +"appName VARCHAR(20),"
                 +"RESSOURCES REFERENCES ressources(type),"+
                 "detail TEXT);";
         /* ici , il veut des entiers mais on a besoin des string */
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    public void searchdata(Context context){


        SQLiteDatabase db = Database.getInstance(context).getWritableDatabase();


        String columns[]={"id","timeStamp","appName","RESSOURCES"};
        //Cursor cursor = db.query(
          //      "donneesRessources",columns , null, null, null, null, null);


        Cursor cursor =   db.rawQuery("select * from donneesRessources",null);


        while(cursor.moveToNext()){

            int  id = cursor.getInt(cursor.getColumnIndex("id"));

            String  time = cursor.getString(cursor.getColumnIndex("timeStamp"));

            String  appname = cursor.getString(cursor.getColumnIndex("appName"));
            String  resource = cursor.getString(cursor.getColumnIndex("RESSOURCES"));

            System.out.println("mla"+id+" "+time+" "+appname+" "+resource);

        }



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

    //update demande algo
    private void updateData(){

    }

}
