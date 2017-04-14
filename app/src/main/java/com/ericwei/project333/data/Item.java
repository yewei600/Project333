package com.ericwei.project333.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericwei on 2017-03-26.
 */

public class Item implements Parcelable {

    private String category;
    private String subCategory;
    private Bitmap itemImage;
    private int id;

    public Item(String category, String subCategory, Bitmap itemImage, int id) {
        this.category = category;
        this.subCategory = subCategory;
        this.itemImage = itemImage;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Bitmap getItemImage() {
        return itemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        this.itemImage = itemImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.subCategory);
        dest.writeParcelable(this.itemImage, flags);
        dest.writeInt(this.id);
    }

    protected Item(Parcel in) {
        this.category = in.readString();
        this.subCategory = in.readString();
        this.itemImage = in.readParcelable(Bitmap.class.getClassLoader());
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
