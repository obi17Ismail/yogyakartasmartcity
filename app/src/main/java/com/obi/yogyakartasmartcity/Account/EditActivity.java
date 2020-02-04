package com.obi.yogyakartasmartcity.Account;

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

public class EditActivity extends AppCompatActivity implements View.OnClickListener, ObjectSourcesCallback {
    EditText et_name, et_email, et_phone;
    Button btn_save;
    public static RelativeLayout relativeLayout_pb;

    private String TAG = "EditProfile";

    SessionUser sessionUser;

    private static String token;
    private static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        sessionUser = new SessionUser(EditActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

        final String s_name = getIntent().getStringExtra("NAME");
        final String s_email = getIntent().getStringExtra("EMAIL");
        final String s_phone = getIntent().getStringExtra("PHONE");

        HashMap<String, String> user = sessionUser.getUserDetails();

        token = user.get(SessionUser.KEY_TOKEN);
        user_id = user.get(SessionUser.KEY_ID);

        relativeLayout_pb = (RelativeLayout) findViewById(R.id.relativeLayout_pb_edit);

        et_name = findViewById(R.id.et_name_edit);
        et_email = findViewById(R.id.et_email_edit);
        et_phone = findViewById(R.id.et_phone_edit);

        et_name.setText(s_name);
        et_email.setText(s_email);
        et_phone.setText(s_phone);

        btn_save = findViewById(R.id.btn_save_edit);
        btn_save.setOnClickListener(this);
    }

    public DataSources getDataSources() {
        return new DataSources();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_edit:
                if(!et_name.getText().toString().equals("") || !et_email.getText().toString().equals("") || !et_phone.getText().toString().equals("")){
                    InputMethodManager inputMethodManager = (InputMethodManager) EditActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(EditActivity.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

                    getDataSources().postEditProfile(DataSources.URL_EDITPROFILE, token, user_id, et_name.getText().toString(), et_email.getText().toString(), et_phone.getText().toString(), this);

                    relativeLayout_pb.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(EditActivity.this, "Harap diisi semua bidang",
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
            String j_status = respone.getString("status");

            Intent intent = new Intent(EditActivity.this, MainActivity.class);
            intent.putExtra("FRAGMENT", "Account");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            Toast.makeText(EditActivity.this, j_status,
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

            Toast.makeText(EditActivity.this , j_error+", "+error2[1], Toast.LENGTH_SHORT).show();
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
