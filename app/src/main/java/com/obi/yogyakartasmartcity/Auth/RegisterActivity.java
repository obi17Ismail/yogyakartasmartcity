package com.obi.yogyakartasmartcity.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Network.DataSources;
import com.obi.yogyakartasmartcity.Network.ObjectSourcesCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ObjectSourcesCallback {
    EditText et_name, et_email, et_phone, et_password, et_ulangi_password;
    ImageView img_check, img_check2;
    TextView btn_login;
    Button btn_register;
    public static RelativeLayout relativeLayout_pb;

    private String TAG = "Register";

    SessionUser sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionUser = new SessionUser(RegisterActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);

        relativeLayout_pb = findViewById(R.id.relativeLayout_pb_register);

        et_name = findViewById(R.id.et_name_register);
        et_email = findViewById(R.id.et_email_register);
        et_phone = findViewById(R.id.et_phone_register);
        et_password = findViewById(R.id.et_password_register);
        et_password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        et_ulangi_password = findViewById(R.id.et_password2_register);
        et_ulangi_password.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        img_check = findViewById(R.id.img_check_register);
        img_check2 = findViewById(R.id.img_check2_register);

        btn_login = findViewById(R.id.btn_login_register);
        btn_login.setOnClickListener(this);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        et_ulangi_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_password.length() >= 6 && et_ulangi_password.getText().toString().equals(et_password.getText().toString())) {
                    img_check.setVisibility(View.VISIBLE);
                    img_check2.setVisibility(View.VISIBLE);
                } else {
                    img_check.setVisibility(View.INVISIBLE);
                    img_check2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_register:
                if(!et_password.getText().toString().equals(et_ulangi_password.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Password tidak sesuai",
                            Toast.LENGTH_LONG).show();
                } else if (et_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password minimal 6 karakter",
                            Toast.LENGTH_LONG).show();
                } else {
                    if(!et_name.getText().toString().equals("") || !et_email.getText().toString().equals("") || !et_phone.getText().toString().equals("") || !et_password.getText().toString().equals("") || !et_ulangi_password.getText().toString().equals("")){
                        InputMethodManager inputMethodManager = (InputMethodManager) RegisterActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(RegisterActivity.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

                        getDataSources().postRegister(DataSources.URL_REGISTER, et_name.getText().toString(), et_email.getText().toString(), et_phone.getText().toString(), et_password.getText().toString(), this);

                        relativeLayout_pb.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Harap diisi semua bidang",
                                Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.btn_login:

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public DataSources getDataSources() {
        return new DataSources();
    }

    @Override
    public void onSuccess(JSONObject respone) {
        relativeLayout_pb.setVisibility(View.GONE);
        Log.d(TAG, String.valueOf(respone));

        try {
            String success = respone.getString("success");

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            Toast.makeText(RegisterActivity.this , success+". Silakan Login", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(RegisterActivity.this , j_error+", "+error2[1], Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        finish();
    }
}
