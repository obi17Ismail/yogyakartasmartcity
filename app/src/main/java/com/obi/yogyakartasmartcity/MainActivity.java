package com.obi.yogyakartasmartcity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.obi.yogyakartasmartcity.Account.AccountFragment;
import com.obi.yogyakartasmartcity.Announcement.AnnouncementFragment;
import com.obi.yogyakartasmartcity.Article.HomeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppBarLayout appBarLayout;
    Fragment fragment;
    Fragment fragment2;
    Fragment fragment3;
    RelativeLayout btn_home, btn_announcement, btn_account;
    FrameLayout frameLayout_home, frameLayout_announcement, frameLayout_account;
    TextView txt_toolbar, txt_home, txt_announcement, txt_account;
    ImageView img_home, img_announcement, img_account;

    public static String s_fragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        txt_toolbar = findViewById(R.id.toolbar_title_main);
        appBarLayout = findViewById(R.id.appbar_main);

        if(getIntent().getStringExtra("FRAGMENT") != null){
            s_fragment = getIntent().getStringExtra("FRAGMENT");
        }

        frameLayout_home = findViewById(R.id.frameLayout_home_main);
        frameLayout_announcement = findViewById(R.id.frameLayout_announcement_main);
        frameLayout_account = findViewById(R.id.frameLayout_account_main);

        btn_home = findViewById(R.id.btn_home_main);
        btn_home.setOnClickListener(this);
        img_home = findViewById(R.id.img_home_main);
        txt_home = findViewById(R.id.txt_home_main);
        btn_announcement = findViewById(R.id.btn_announcement_main);
        btn_announcement.setOnClickListener(this);
        img_announcement = findViewById(R.id.img_announcement_main);
        txt_announcement = findViewById(R.id.txt_announcement_main);
        btn_account = findViewById(R.id.btn_account_main);
        btn_account.setOnClickListener(this);
        img_account = findViewById(R.id.img_account_main);
        txt_account = findViewById(R.id.txt_account_main);

        if(s_fragment.equals("Announcement")){
            String s_fragment2 = String.valueOf(fragment2);

            if(!s_fragment2.contains("AnnouncementFragment")){
                fragment2 = new AnnouncementFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout_announcement_main, fragment2, fragment2.getClass().getSimpleName())
                        .commit();
            }

            frameLayout_home.setVisibility(View.GONE);
            frameLayout_announcement.setVisibility(View.VISIBLE);
            frameLayout_account.setVisibility(View.GONE);

            txt_home.setTextColor(getResources().getColor(R.color.textColorSecond));
            txt_announcement.setTextColor(getResources().getColor(R.color.textColorPrimary));
            txt_account.setTextColor(getResources().getColor(R.color.textColorSecond));

            img_home.setImageResource(R.drawable.ic_home_grey);
            img_announcement.setImageResource(R.drawable.ic_announcement);
            img_account.setImageResource(R.drawable.ic_user_grey);

            txt_toolbar.setText("Pengumuman");
            appBarLayout.setBackgroundResource(R.drawable.bg_shadow_white_bottom);
        } else if(s_fragment.equals("Account")){
            String s_fragment3 = String.valueOf(fragment3);

            if(!s_fragment3.contains("AccountFragment")){
                fragment2 = new AccountFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout_account_main, fragment3, fragment3.getClass().getSimpleName())
                        .commit();
            }

            frameLayout_home.setVisibility(View.GONE);
            frameLayout_announcement.setVisibility(View.GONE);
            frameLayout_account.setVisibility(View.VISIBLE);

            txt_home.setTextColor(getResources().getColor(R.color.textColorSecond));
            txt_announcement.setTextColor(getResources().getColor(R.color.textColorSecond));
            txt_account.setTextColor(getResources().getColor(R.color.textColorPrimary));

            img_home.setImageResource(R.drawable.ic_home_grey);
            img_announcement.setImageResource(R.drawable.ic_announcement_grey);
            img_account.setImageResource(R.drawable.ic_user);

            txt_toolbar.setText("Akun");
            appBarLayout.setBackgroundResource(R.drawable.bg_shadow_white_bottom);
        } else {
            String s_fragment = String.valueOf(fragment);

            if(!s_fragment.contains("HomeFragment")){
                fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout_home_main, fragment, fragment.getClass().getSimpleName())
                        .commit();
            }

            frameLayout_home.setVisibility(View.VISIBLE);
            frameLayout_announcement.setVisibility(View.GONE);
            frameLayout_account.setVisibility(View.GONE);

            txt_home.setTextColor(getResources().getColor(R.color.textColorPrimary));
            txt_announcement.setTextColor(getResources().getColor(R.color.textColorSecond));
            txt_account.setTextColor(getResources().getColor(R.color.textColorSecond));

            img_home.setImageResource(R.drawable.ic_home);
            img_announcement.setImageResource(R.drawable.ic_announcement_grey);
            img_account.setImageResource(R.drawable.ic_user_grey);

            txt_toolbar.setText("Yogyakarta Smart City");
            appBarLayout.setBackgroundResource(R.drawable.bg_shadow_white_bottom);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home_main:
                String s_fragment = String.valueOf(fragment);

                if(!s_fragment.contains("HomeFragment")){
                    fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_home_main, fragment, fragment.getClass().getSimpleName())
                            .commit();
                }

                frameLayout_home.setVisibility(View.VISIBLE);
                frameLayout_announcement.setVisibility(View.GONE);
                frameLayout_account.setVisibility(View.GONE);

                txt_home.setTextColor(getResources().getColor(R.color.textColorPrimary));
                txt_announcement.setTextColor(getResources().getColor(R.color.textColorSecond));
                txt_account.setTextColor(getResources().getColor(R.color.textColorSecond));

                img_home.setImageResource(R.drawable.ic_home);
                img_announcement.setImageResource(R.drawable.ic_announcement_grey);
                img_account.setImageResource(R.drawable.ic_user_grey);

                txt_toolbar.setText("Yogyakarta Smart City");
                appBarLayout.setBackgroundResource(R.drawable.bg_shadow_white_bottom);

                break;
            case R.id.btn_announcement_main:
                String s_fragment2 = String.valueOf(fragment2);

                if(!s_fragment2.contains("AnnouncementFragment")){
                    fragment2 = new AnnouncementFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_announcement_main, fragment2, fragment2.getClass().getSimpleName())
                            .commit();
                }

                frameLayout_home.setVisibility(View.GONE);
                frameLayout_announcement.setVisibility(View.VISIBLE);
                frameLayout_account.setVisibility(View.GONE);

                txt_home.setTextColor(getResources().getColor(R.color.textColorSecond));
                txt_announcement.setTextColor(getResources().getColor(R.color.textColorPrimary));
                txt_account.setTextColor(getResources().getColor(R.color.textColorSecond));

                img_home.setImageResource(R.drawable.ic_home_grey);
                img_announcement.setImageResource(R.drawable.ic_announcement);
                img_account.setImageResource(R.drawable.ic_user_grey);

                txt_toolbar.setText("Pengumuman");
                appBarLayout.setBackgroundResource(R.drawable.bg_shadow_white_bottom);

                break;
            case R.id.btn_account_main:
                String s_fragment3 = String.valueOf(fragment3);

                if(!s_fragment3.contains("AccountFragment")){
                    fragment3 = new AccountFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_account_main, fragment3, fragment3.getClass().getSimpleName())
                            .commit();
                }

                frameLayout_home.setVisibility(View.GONE);
                frameLayout_announcement.setVisibility(View.GONE);
                frameLayout_account.setVisibility(View.VISIBLE);

                txt_home.setTextColor(getResources().getColor(R.color.textColorSecond));
                txt_announcement.setTextColor(getResources().getColor(R.color.textColorSecond));
                txt_account.setTextColor(getResources().getColor(R.color.textColorPrimary));

                img_home.setImageResource(R.drawable.ic_home_grey);
                img_announcement.setImageResource(R.drawable.ic_announcement_grey);
                img_account.setImageResource(R.drawable.ic_user);

                txt_toolbar.setText("Akun");
                appBarLayout.setBackgroundResource(R.drawable.bg_shadow_white_bottom);

                break;
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

}
