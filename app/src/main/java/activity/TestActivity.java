package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ressources.R;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import database.Database;
import database.RandomFileCreator;
import reader.JsonFileReader;
import reader.StatEntry;

/**
 * Created by XH on 2018/4/11.
 */

public class TestActivity extends Activity {

    private Database database;
    private RandomFileCreator randomFileCreator ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        database = Database.getInstance(getApplicationContext());
        randomFileCreator = new RandomFileCreator(getApplicationContext());

        Button test = (Button)findViewById(R.id.test);
        final TextView data = (TextView)findViewById(R.id.text);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tetsframe);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try{
                randomFileCreator.fillDetailFile();
                database.addDataFromFile(getApplicationContext(),getApplication().getExternalCacheDir().toString() + "/one.json","donneesRessources");
            }catch (IOException e){
               System.out.println("error de file");
            }


            }
        });


    //    ArrayList<GraphicalView> graphlist = new ArrayList<GraphicalView>();

      /*  graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getLineChartView(TestActivity.this, mine_main_activity.getlist(),
                        "B"));

        linearLayout.removeAllViews();
        linearLayout.addView(graphlist.get(0));
*/

    }
}
