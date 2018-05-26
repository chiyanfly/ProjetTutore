package database;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RandomFileCreator {


    public Context context;

    public RandomFileCreator(Context c) {

        context = c;
    }

    public void fillDetailFile() throws IOException {
        BufferedWriter fw;
        int lignes = 0;
        long timestamp = 1522857960;

        System.out.println(context.getExternalCacheDir());
        File file = new File(context.getExternalCacheDir().toString() + "//one.json");
        System.out.println(file.getAbsolutePath());

        fw = new BufferedWriter(new FileWriter(file));
        fw.write("[");
        fw.write("{\"timestamp\":" + timestamp + ",\"app\":\"myApp" + (Math.round(Math.random() * 100))%30 + "\" ,\"ressource\":" + Math.round(Math.random() * 100)%20 + ",\"detail\":{}}");
        fw.newLine();
        while (lignes < 100) {
            fw.write(",{\"timestamp\":" + timestamp + ",\"app\":\"myApp" + (Math.round(Math.random() * 100))%30 + "\" ,\"ressource\":" + Math.round(Math.random() * 100)%20 + ",\"detail\":{}}");
            fw.newLine();
            lignes++;
            timestamp=timestamp+10;
        }
        fw.write("]");
        fw.close();
    }
/*
    public static void main() {
        try {
            fillDetailFile();
        } catch (IOException e) {
            System.out.println("Il n'a pas trouvé le fichier lol");
        }
    }*/
}
