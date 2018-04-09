package com.example.ressources;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v7.widget.*;
/**
 * Created by hxu on 04/04/18.
 */

public class mine_main_activity extends Activity {



    Button CPU,GPS;

    LinearLayout   framelist;
    android.support.v7.widget.RecyclerView recyclerView;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);


        init();

//new test




    }


    void init(){

        CPU= (Button) findViewById(R.id.mine_CPU);
        GPS= (Button) findViewById(R.id.mine_GPS);
        framelist= (LinearLayout) findViewById(R.id.mine_framelayoutlist);
        recyclerView=(RecyclerView) findViewById(R.id.recycler);


    }


}
