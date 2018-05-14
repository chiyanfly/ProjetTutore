package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import reader.StatEntry;

public class Exemple {

    private ArrayList<StatEntry> test = new ArrayList<>();
    // il faut penser a context
    private Context context;

    Database mydb = Database.getInstance(context);



}
