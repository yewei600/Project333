package com.ericwei.project333.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericwei on 2017-03-26.
 */

public class Clothes implements Parcelable {

    public final String category;
    public final String subCategory;
    //

    public Clothes(String category, String subCategory, String imagePath) {
        this.category = category;
        this.subCategory = subCategory;
        // this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
