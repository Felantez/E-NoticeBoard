package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private static int SPLASH_SCREEN=5000;
    Animation tpAnim, btmAnim;
    ImageView imageView;
    TextView label, slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tpAnim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        btmAnim=AnimationUtils.loadAnimation(this,R.anim.btm_anim);

        label=findViewById(R.id.label);
        slogan=findViewById(R.id.slogan);
        imageView=findViewById(R.id.imageView);
        imageView.setAnimation(tpAnim);
        slogan.setAnimation(btmAnim);
        label.setAnimation(btmAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this, logIn.class);

                Pair[] pairs = new Pair[2];
                pairs[0]=new Pair< View, String >(imageView, "logo");
                pairs[1]=new Pair< View, String >(label, "label");

                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(i, options.toBundle());

            }
        },SPLASH_SCREEN);


    }
}