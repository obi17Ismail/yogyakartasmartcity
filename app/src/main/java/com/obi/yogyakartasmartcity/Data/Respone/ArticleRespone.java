package com.obi.yogyakartasmartcity.Data.Respone;

import com.google.gson.annotations.SerializedName;
import com.obi.yogyakartasmartcity.Data.ArticleModel;

import java.util.ArrayList;

public class ArticleRespone {

    @SerializedName("data")

    private ArrayList<ArticleModel> data;

    public ArrayList<ArticleModel> getResults(){
        return data;
    }
}
