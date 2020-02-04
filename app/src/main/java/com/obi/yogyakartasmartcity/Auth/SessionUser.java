package com.obi.yogyakartasmartcity.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Operasional13 on 14/11/2017.
 */

public class SessionUser {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Yogyakarta";

    // All Shared Preferences Keys
    private static final String IS_USER = "UserAuth";

    // User name (make variable public to access from outside)
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionUser(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void create(String token, String id, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER, true);

        // Storing name in pref
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> alamat2 = new HashMap<String, String>();
        // user name
        alamat2.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        alamat2.put(KEY_ID, pref.getString(KEY_ID, null));
        alamat2.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return alamat2;
    }

    /**
     * Clear session details
     * */
    public void clearData(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent intent3 = new Intent(_context, LoginActivity.class);
        _context.startActivity(intent3);
        ((Activity)_context).finish();
    }

    public boolean Flow(){
        return pref.getBoolean(IS_USER, false);
    }
}
