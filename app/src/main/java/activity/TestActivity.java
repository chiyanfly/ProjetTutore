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
import database.Databasehandler;
import database.RandomFileCreator;
import reader.JsonFileReader;
import reader.StatEntry;

/**
 * Created by XH on 2018/4/11.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        final Databasehandler databasehandler = new Databasehandler(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        final Database database = Database.getInstance(getApplicationContext());
        final RandomFileCreator randomFileCreator = new RandomFileCreator(getApplicationContext());

        Button dayToMonth = (Button)findViewById(R.id.dayToMonth);
        Button monthToYear = (Button)findViewById(R.id.monthToYear);
        Button test = (Button)findViewById(R.id.test);
        final TextView data = (TextView)findViewById(R.id.text);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tetsframe);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database.deletealldata(getApplicationContext(),"currentDay");
                try{
                    randomFileCreator.fillDetailFile();
                    database.addDataFromFile(getApplicationContext(),getApplication().getExternalCacheDir().toString() + "/one.json","currentDay");
                    database.affichageAppNames();
                    database.affichetable(getApplicationContext(),"currentDay");
                }catch (IOException e){

                }
            }
        });

        dayToMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              System.out.println("Maintenant on commence transformer");
              databasehandler.dayMonthTransfer(getApplicationContext());
              System.out.println("CurrentDay");
              database.affichetable(getApplicationContext(),"currentDay");
              System.out.println("PreviousDay");
              database.affichetable(getApplicationContext(),"previousDay");
              System.out.println("currentMonth");
              database.affichageTableMoyenne("currentMonth");
            }
        });

        monthToYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Transfer month to year");
               databasehandler.monthYearTransfer(getApplicationContext());
                System.out.println("CurrentMonth");
                database.affichageTableMoyenne("currentMonth");
                System.out.println("PreviousMonth");
                database.affichageTableMoyenne("previousMonth");
                System.out.println("currentYear");
                database.affichageTableMoyenne("currentYear");

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
