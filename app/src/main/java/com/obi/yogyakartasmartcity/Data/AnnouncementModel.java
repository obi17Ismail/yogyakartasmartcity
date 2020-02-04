package com.obi.yogyakartasmartcity.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnnouncementModel implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("author_name")
    private String author;

    @SerializedName("created_at")
    private String date;

    @SerializedName("announcement")
    private String announcement;

    public AnnouncementModel() {
    }

    protected AnnouncementModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        slug = in.readString();
        author = in.readString();
        date = in.readString();
        announcement = in.readString();
    }

    public static final Creator<AnnouncementModel> CREATOR = new Creator<AnnouncementModel>() {
        @Override
        public AnnouncementModel createFromParcel(Parcel in) {
            return new AnnouncementModel(in);
        }

        @Override
        public AnnouncementModel[] newArray(int size) {
            return new AnnouncementModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public static Creator<AnnouncementModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(slug);
        parcel.writeString(author);
        parcel.writeString(date);
        parcel.writeString(announcement);
    }
}
