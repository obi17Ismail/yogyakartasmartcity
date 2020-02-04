package com.obi.yogyakartasmartcity.Network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.obi.yogyakartasmartcity.BuildConfig;
import com.obi.yogyakartasmartcity.Data.Respone.AnnouncementRespone;
import com.obi.yogyakartasmartcity.Data.Respone.ArticleRespone;

import org.json.JSONObject;

import java.io.File;

public class DataSources {
    public static final String URL_ARTICLE = BuildConfig.BASE_URL_TMDB + "articles";
    public static final String URL_SEARCH_ARTICLE = BuildConfig.BASE_URL_TMDB + "articles/search";
    public static final String URL_DETAIL_ARTICLE = BuildConfig.BASE_URL_TMDB + "articles/detail";
    public static final String URL_ANNOUNCEMENT = BuildConfig.BASE_URL_TMDB + "announcements";
    public static final String URL_ADDANNOUNCEMENT = BuildConfig.BASE_URL_TMDB + "announcements/create";
    public static final String URL_LOGIN = BuildConfig.BASE_URL_TMDB + "user/login";
    public static final String URL_LOGINSOCMED = BuildConfig.BASE_URL_TMDB + "user/login_socmed";
    public static final String URL_REGISTER = BuildConfig.BASE_URL_TMDB + "user/register";
    public static final String URL_PROFILE = BuildConfig.BASE_URL_TMDB + "user/profile";
    public static final String URL_EDITPROFILE = BuildConfig.BASE_URL_TMDB + "user/update";
    public static final String URL_CHANGEIMAGE = BuildConfig.BASE_URL_TMDB + "user/change_image";
    public static final String URL_CHANGEPASS = BuildConfig.BASE_URL_TMDB + "user/change_password";

    public void getArticle(String articleEndpoint, final ArticleSourcesCallback callback) {

        AndroidNetworking.get(articleEndpoint)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsObject(ArticleRespone.class, new ParsedRequestListener<ArticleRespone>() {
                    @Override
                    public void onResponse(ArticleRespone response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postArticleSearch(String articleEndpoint, String term, final ArticleSourcesCallback callback) {

        AndroidNetworking.post(articleEndpoint)
                .addBodyParameter("term", term)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsObject(ArticleRespone.class, new ParsedRequestListener<ArticleRespone>() {
                    @Override
                    public void onResponse(ArticleRespone response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postArticleDetail(String articleEndpoint, String article_id, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(articleEndpoint)
                .addBodyParameter("article_id", article_id)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang, atau Login terlebih dahulu");
                    }
                });
    }

    public void getAnnouncement(String announcementEndpoint, final AnnouncementSourcesCallback callback) {

        AndroidNetworking.get(announcementEndpoint)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsObject(AnnouncementRespone.class, new ParsedRequestListener<AnnouncementRespone>() {
                    @Override
                    public void onResponse(AnnouncementRespone response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postAnnouncement(String addAnnouncementEndpoint, String token, String user_id, String title, String announcement, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(addAnnouncementEndpoint)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer "+token)
                .addBodyParameter("author", user_id)
                .addBodyParameter("title", title)
                .addBodyParameter("announcement", announcement)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        int errorCode = anError.getErrorCode();
                        String error = anError.getErrorBody();

                        Log.d("ERROR", "onError: "+ error+","+errorCode);

                        callback.onFailed(error+","+errorCode);
                    }
                });
    }

    public void postLogin(String loginEndpoint, String email, String password, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(loginEndpoint)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        int errorCode = anError.getErrorCode();
                        String error = anError.getErrorBody();

                        Log.d("ERROR", "onError: "+ error+","+errorCode);

                        callback.onFailed(error+","+errorCode);
//
//                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postLoginSocmed(String loginEndpoint, String provider_id, String provider_name, String provider_email, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(loginEndpoint)
                .addBodyParameter("provider_id", provider_id)
                .addBodyParameter("provider_name", provider_name)
                .addBodyParameter("provider_email", provider_email)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        int errorCode = anError.getErrorCode();
                        String error = anError.getErrorBody();

                        Log.d("ERROR", "onError: "+ error+","+errorCode);

                        callback.onFailed(error+","+errorCode);
//
//                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postRegister(String registerEndpoint, String name, String email, String phone, String password, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(registerEndpoint)
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("phone", phone)
                .addBodyParameter("password", password)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        int errorCode = anError.getErrorCode();
                        String error = anError.getErrorBody();

                        Log.d("ERROR", "onError: "+ error+","+errorCode);

                        callback.onFailed(error+","+errorCode);
                    }
                });
    }

    public void getProfile(String profileEndpoint, String token, final ObjectSourcesCallback callback) {

        AndroidNetworking.get(profileEndpoint)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer "+token)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postChangeImage(String profileEndpoint, String token, String user_id, String image, final ObjectSourcesCallback callback) {

        AndroidNetworking.upload(profileEndpoint)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer "+token)
                .addMultipartParameter("user_id", user_id)
                .addMultipartFile("image", new File(image))
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("ERROR", "onError: ", anError);
                        callback.onFailed("Gagal menghubungkan ke server. Silakan swipe down untuk muat ulang");
                    }
                });
    }

    public void postEditProfile(String profileEndpoint, String token, String user_id, String name, String email, String phone, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(profileEndpoint+"/"+user_id)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer "+token)
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("phone", phone)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        int errorCode = anError.getErrorCode();
                        String error = anError.getErrorBody();

                        Log.d("ERROR", "onError: "+ error+","+errorCode);

                        callback.onFailed(error+","+errorCode);
                    }
                });
    }

    public void postChangePass(String profileEndpoint, String token, String user_id, String new_pass, String repeat_pass, final ObjectSourcesCallback callback) {

        AndroidNetworking.post(profileEndpoint+"/"+user_id)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer "+token)
                .addBodyParameter("new_password", new_pass)
                .addBodyParameter("repeat_new_password", repeat_pass)
                .setTag(DataSources.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.d("Analytics", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.d("Analytics", " bytesSent : " + bytesSent);
                        Log.d("Analytics", " bytesReceived : " + bytesReceived);
                        Log.d("Analytics", " isFromCache : " + isFromCache);
                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        int errorCode = anError.getErrorCode();
                        String error = anError.getErrorBody();

                        Log.d("ERROR", "onError: "+ error+","+errorCode);

                        callback.onFailed(error+","+errorCode);
                    }
                });
    }
}