package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.example.ressources.R;

/**
 * Created by XH on 2018/4/11.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.testitem);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tetsframe);


    //    ArrayList<GraphicalView> graphlist = new ArrayList<GraphicalView>();

      /*  graphlist.add((GraphicalView) GraphUtils.getInstance()
                .getLineChartView(TestActivity.this, mine_main_activity.getlist(),
                        "B"));

        linearLayout.removeAllViews();
        linearLayout.addView(graphlist.get(0));
*/

    }
}
