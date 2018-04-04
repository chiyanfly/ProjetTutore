package com.example.ressources;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by hxu on 04/04/18.
 */

public class mine_main_activity extends Activity {



    Button CPU,GPS;

    LinearLayout   framelist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);


        init();


    }


    void init(){

        CPU= (Button) findViewById(R.id.mine_CPU);
        GPS= (Button) findViewById(R.id.mine_GPS);
        framelist= (LinearLayout) findViewById(R.id.mine_framelayoutlist);
    }

}
