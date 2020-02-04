package com.obi.yogyakartasmartcity.Network;

import com.obi.yogyakartasmartcity.Data.Respone.ArticleRespone;

public interface ArticleSourcesCallback {

    void onSuccess(ArticleRespone respone);

    void onFailed(String error);
}