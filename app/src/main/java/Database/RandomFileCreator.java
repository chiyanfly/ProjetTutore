package database;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import database.Database;

public class RandomFileCreator {
    private Database database;
    public Context context;

    public RandomFileCreator(Context c) {

        context = c;
    }

    public void fillDetailFile() throws IOException {
        int resourcesNumber = database.tableLength(context, "ressources");
        String[] resourcesNames = new String[resourcesNumber];
        for (int an = 0; an < resourcesNumber; an++) {
            resourcesNames[an] = database.returnResourceName(an);
        }
        BufferedWriter fw;
        int lignes = 0;
        long timestamp = 1522857960;
        System.out.println(context.getExternalCacheDir());
        File file = new File(context.getExternalCacheDir().toString() + "//one.json");
        System.out.println(file.getAbsolutePath());
        database = Database.getInstance(context);
        fw = new BufferedWriter(new FileWriter(file));
        fw.write("[");
        fw.write("{\"timestamp\":" + timestamp + ",\"app\":\"myApp" + (Math.round(Math.random() * 100)) % 30 + "\" ,\"ressource\":" + resourcesNames[(int) Math.round(Math.random() * resourcesNames.length)] + ",\"detail\":{}}");
        fw.newLine();
        while (lignes < 100) {
            fw.write(",{\"timestamp\":" + timestamp + ",\"app\":\"myApp" + (Math.round(Math.random() * 100)) % 30 + "\" ,\"ressource\":" + resourcesNames[(int) Math.round(Math.random() * resourcesNames.length)] + ",\"detail\":{}}");
            fw.newLine();
            lignes++;
            timestamp = timestamp + (Math.round(Math.random() * 100)) % 50;

        }
        fw.write("]");
        fw.close();
    }
}
