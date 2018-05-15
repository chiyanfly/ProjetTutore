package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ressources.R;

public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH=3000;//延迟三秒

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);/*
        //setContentView(R.layout.welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent=new Intent(Splash.this, SortActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);*/
    }


}