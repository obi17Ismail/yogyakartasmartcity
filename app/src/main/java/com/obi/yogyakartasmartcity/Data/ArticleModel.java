package com.obi.yogyakartasmartcity.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ArticleModel implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("slug")
    private String slug;

    @SerializedName("author_name")
    private String author;

    @SerializedName("view")
    private String view;

    @SerializedName("created_at")
    private String date;

    @SerializedName("image")
    private String image;

    public ArticleModel() {
    }

    protected ArticleModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        slug = in.readString();
        author = in.readString();
        view = in.readString();
        date = in.readString();
        image = in.readString();
    }

    public static final Creator<ArticleModel> CREATOR = new Creator<ArticleModel>() {
        @Override
        public ArticleModel createFromParcel(Parcel in) {
            return new ArticleModel(in);
        }

        @Override
        public ArticleModel[] newArray(int size) {
            return new ArticleModel[size];
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

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Creator<ArticleModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(slug);
        parcel.writeString(author);
        parcel.writeString(view);
        parcel.writeString(date);
        parcel.writeString(image);
    }
}
