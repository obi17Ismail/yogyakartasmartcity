package com.obi.yogyakartasmartcity.Network;

import com.obi.yogyakartasmartcity.Data.Respone.AnnouncementRespone;

public interface AnnouncementSourcesCallback {

    void onSuccess(AnnouncementRespone respone);

    void onFailed(String error);
}