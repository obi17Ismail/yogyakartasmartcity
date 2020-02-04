package com.obi.yogyakartasmartcity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.obi.yogyakartasmartcity.Auth.LoginActivity;
import com.obi.yogyakartasmartcity.Auth.SessionUser;

import java.util.HashMap;

public class SplashScreen extends Activity {
    SessionUser sessionUser;

    private static String token;
    private static String user_id;

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 16) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.splash_screen);

        sessionUser = new SessionUser(SplashScreen.this);

        HashMap<String, String> user = sessionUser.getUserDetails();

        token = user.get(SessionUser.KEY_TOKEN);
        user_id = user.get(SessionUser.KEY_ID);

        StartAnimations();
    }

    private void StartAnimations(){

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout_splash);
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(anim);

        splashTread = new Thread(){
            public void run(){
                try{
                    int waited = 0;
                    while (waited < 3000){
                        sleep(20);
                        waited += 20;
                    }

                    if(user_id == null){
                        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                    } else {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                    }

                } catch (InterruptedException e){

                }

            }
        };
        splashTread.start();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        finish();
    }
}