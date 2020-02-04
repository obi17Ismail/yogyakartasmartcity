package com.obi.yogyakartasmartcity.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.obi.yogyakartasmartcity.MainActivity;
import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Network.DataSources;
import com.obi.yogyakartasmartcity.Network.ObjectSourcesCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ObjectSourcesCallback,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;

    Button btn_login;
    ImageButton imgbtn_fb, imgbtn_google;
    LoginButton loginButton;
    EditText et_email, et_password;
    TextView btn_register;
    public static RelativeLayout relativeLayout_pb;

    private String TAG = "Login";

    SessionUser sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        sessionUser = new SessionUser(LoginActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        callbackManager = CallbackManager.Factory.create();
        hashKey();

        relativeLayout_pb = findViewById(R.id.relativeLayout_pb_login);

        et_email = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password_login);
        et_password.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        btn_register = findViewById(R.id.btn_register_login);
        btn_register.setOnClickListener(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        imgbtn_google = findViewById(R.id.imgbtn_google_login);
        imgbtn_google.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        imgbtn_fb = findViewById(R.id.imgbtn_fb_login);
        imgbtn_fb.setOnClickListener(this);

        loginButton = findViewById(R.id.btn_fb_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest data_request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject json_object,
                                    final GraphResponse response) {
                                try {
                                    final JSONObject jsonObject = new JSONObject(json_object.toString());
                                    final String s_id = jsonObject.get("id").toString();
                                    final String s_name = jsonObject.get("name").toString();
                                    final String s_email = jsonObject.get("email").toString();
                                    //final String s_phone = jsonObject.get("phone").toString();

                                    InputMethodManager inputMethodManager = (InputMethodManager) LoginActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                                    inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

                                    getDataSources().postLoginSocmed(DataSources.URL_LOGINSOCMED, s_id, s_name, s_email, LoginActivity.this);

                                    relativeLayout_pb.setVisibility(View.VISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Log.d(TAG + "Facebook", "Facebook " + json_object.toString());

                            }

                        });

                Bundle permission_param = new Bundle();
                permission_param.putString("fields", "id,email,name");
                data_request.setParameters(permission_param);
                data_request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG + "Facebook", "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG + "Facebook", "Facebook " + exception);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_register_login:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

                break;
            case R.id.btn_login:
                if(!et_email.getText().toString().equals("") || !et_password.getText().toString().equals("")){
                    InputMethodManager inputMethodManager = (InputMethodManager) LoginActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

                    getDataSources().postLogin(DataSources.URL_LOGIN, et_email.getText().toString(), et_password.getText().toString(), this);

                    relativeLayout_pb.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(LoginActivity.this, "Harap diisi semua bidang",
                            Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.imgbtn_google_login:
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                }
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

                break;
            case R.id.imgbtn_fb_login:
                loginButton.performClick();

                break;
        }
    }

    private void hashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.obi.yogyakartasmartcity", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                String sign = Base64
                        .encodeToString(md.digest(), Base64.DEFAULT);

                Log.d("MYKEYHASH:", sign);

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

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
            JSONObject data = respone.getJSONObject("data");
            String j_user_id = data.getString("id");
            String j_name = data.getString("name");
            String j_email = data.getString("email");

            JSONObject meta = respone.getJSONObject("meta");
            String j_token = meta.getString("token");

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            Toast.makeText(LoginActivity.this , "Selamat datang "+j_name, Toast.LENGTH_SHORT).show();

            sessionUser.create(j_token, j_user_id, j_email);

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

            Toast.makeText(LoginActivity.this , j_error+", "+error2[1], Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();

            String s_id = acct.getId();
            String s_personName = acct.getDisplayName();
            String s_email = acct.getEmail();

            InputMethodManager inputMethodManager = (InputMethodManager) LoginActivity.this.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(LoginActivity.this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

            getDataSources().postLoginSocmed(DataSources.URL_LOGINSOCMED, s_id, s_personName, s_email, this);

            relativeLayout_pb.setVisibility(View.VISIBLE);

            Log.e(TAG, "Name: " + s_personName + ", email: " + s_email
                    + ", Id: " + s_id);
        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
