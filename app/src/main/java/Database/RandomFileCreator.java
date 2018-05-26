package Database;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

public class RandomFileCreator {
    public static void fillDetailFile () throws IOException {
        BufferedWriter fw;
        int lignes =0;
        long timestamp = 1522857960;
        fw = new BufferedWriter(new FileWriter(new File("details.json")));
        fw.write("[");
        fw.write("\"timestamp\""+timestamp+",\"app\":\"myApp\""+Math.random()*100+",\"ressource\":"+Math.random()*20+",\"detail\":{}");
        fw.newLine();
        while(lignes<100) {
            fw.write(",\"timestamp\""+timestamp+",\"app\":\"myApp\""+Math.random()*100+",\"ressource\":"+Math.random()*20+",\"detail\":{}");
            fw.newLine();
            lignes++;
            timestamp++;
        }
        fw.write("]");
    }

    public static void main() {
        try {
            fillDetailFile();
        } catch (IOException e) {
            System.out.println("Il n'a pas trouvé le fichier lol");
        }
    }
}
