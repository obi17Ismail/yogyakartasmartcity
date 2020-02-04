package com.obi.yogyakartasmartcity.Network;

import org.json.JSONObject;

public interface ObjectSourcesCallback {

    void onSuccess(JSONObject respone);

    void onFailed(String error);
}