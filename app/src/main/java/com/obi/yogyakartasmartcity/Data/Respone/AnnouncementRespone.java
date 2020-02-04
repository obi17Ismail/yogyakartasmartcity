package com.obi.yogyakartasmartcity.Data.Respone;

import com.google.gson.annotations.SerializedName;
import com.obi.yogyakartasmartcity.Data.AnnouncementModel;

import java.util.ArrayList;

public class AnnouncementRespone {

    @SerializedName("data")

    private ArrayList<AnnouncementModel> data;

    public ArrayList<AnnouncementModel> getResults(){
        return data;
    }
}
