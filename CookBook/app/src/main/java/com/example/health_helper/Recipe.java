package com.example.health_helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable
{
    private String imageURL;
    private String recipeName;
    private String pageURL;

    public Recipe(String imageURL, String recipeName, String pageURL)
    {
        this.imageURL = imageURL;
        this.recipeName = recipeName;
        this.pageURL = pageURL;
    }

    protected Recipe(Parcel in)
    {
        String[] data = new String[3];
        in.readStringArray(data);
        imageURL = data[0];
        recipeName = data[1];
        pageURL = data[2];
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>()
    {
        @Override
        public Recipe createFromParcel(Parcel in)
        {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[]{imageURL, recipeName, pageURL});
    }
}
