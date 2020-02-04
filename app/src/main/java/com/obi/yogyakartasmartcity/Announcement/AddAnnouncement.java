package com.obi.yogyakartasmartcity.Announcement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.obi.yogyakartasmartcity.MainActivity;
import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Auth.SessionUser;
import com.obi.yogyakartasmartcity.Network.DataSources;
import com.obi.yogyakartasmartcity.Network.ObjectSourcesCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddAnnouncement extends AppCompatActivity implements View.OnClickListener, ObjectSourcesCallback {
    EditText et_title, et_announcement;
    Button btn_send;
    public static RelativeLayout relativeLayout_pb;

    private String TAG = "EditProfile";

    SessionUser sessionUser;

    private static String token;
    private static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);

        sessionUser = new SessionUser(AddAnnouncement.this);

        Toolbar toolbar = findViewById(R.id.toolbar_add_announcement);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

        HashMap<String, String> user = sessionUser.getUserDetails();

        token = user.get(SessionUser.KEY_TOKEN);
        user_id = user.get(SessionUser.KEY_ID);

        relativeLayout_pb = findViewById(R.id.relativeLayout_pb_add_announcement);

        et_title = findViewById(R.id.et_title_add_announcement);
        et_announcement = findViewById(R.id.et_add_announcement);

        btn_send = findViewById(R.id.btn_send_add_announcement);
        btn_send.setOnClickListener(this);
    }

    public DataSources getDataSources() {
        return new DataSources();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_add_announcement:
                if(!et_title.getText().toString().equals("") || !et_announcement.getText().toString().equals("")){
                    InputMethodManager inputMethodManager = (InputMethodManager) AddAnnouncement.this.getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(AddAnnouncement.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

                    getDataSources().postAnnouncement(DataSources.URL_ADDANNOUNCEMENT, token, user_id, et_title.getText().toString(), et_announcement.getText().toString(), this);

                    relativeLayout_pb.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(AddAnnouncement.this, "Harap diisi semua bidang",
                            Toast.LENGTH_LONG).show();
                }

                break;

        }
    }

    @Override
    public void onSuccess(JSONObject respone) {

        relativeLayout_pb.setVisibility(View.GONE);
        Log.d(TAG, String.valueOf(respone));

        try {
            String j_status = respone.getString("success");

            Intent intent = new Intent(AddAnnouncement.this, MainActivity.class);
            intent.putExtra("FRAGMENT", "Announcement");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            Toast.makeText(AddAnnouncement.this, j_status,
                    Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        relativeLayout_pb.setVisibility(View.GONE);

        String[] error2 = error.split(",");

        try {
            JSONObject jsonObj = new JSONObject(error2[0]);
            String j_error = jsonObj.getString("error");

            Toast.makeText(AddAnnouncement.this , j_error+", "+error2[1], Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
