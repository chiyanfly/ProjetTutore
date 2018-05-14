package reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import android.util.JsonReader;

/**
 * Created by richa on 15/03/2018.
 */

public class JsonFileReader  {



    public JsonFileReader(){


    }


    public ArrayList<StatEntry> readFile( JsonReader j) {
      //  File file = new File(path);
        JsonReader reader = j;
        try {

            return readEntryArray(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidEntryException e) {
            e.display();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ArrayList<StatEntry> readEntryArray(JsonReader reader) throws IOException, InvalidEntryException {
        ArrayList<StatEntry> statEntries = new ArrayList<StatEntry>();
        reader.beginArray();
        while (reader.hasNext()) {
            statEntries.add(readEntry(reader));
        }
        reader.endArray();
        return statEntries;
    }

    private StatEntry readEntry(JsonReader reader) throws IOException, InvalidEntryException {
        Timestamp timestamp = null;
        String app_name = null;
        StatEntry.Resource resource = null;
        Detail detail = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            System.out.println("name");
            if (name.equals("timestamp")) {
               // System.out.println("lalallalal");

                timestamp = new Timestamp(reader.nextLong());
                System.out.println(timestamp.toString());
            } else if (name.equals("app")) {
                app_name = reader.nextString();
                System.out.println(app_name);
            } else if (name.equals("resource")) {
                resource = StatEntry.readResourceFromString(reader.nextString());
                System.out.println(resource);
            } else if (name.equals("detail")) {
                detail=readDetail(reader);

                System.out.println("detail");
            } else {
                throw new InvalidEntryException();
            }
        }
        reader.endObject();
        return new StatEntry(timestamp, app_name, resource, detail);
    }

    private Detail readDetail(JsonReader reader) throws IOException {
        reader.skipValue();
        return new Detail();
    }
}
