package com.ericwei.project333.data;

import android.content.Context;

import com.ericwei.project333.R;

import java.util.List;

/**
 * Created by ericwei on 2017-03-26.
 */

public class ClothesConstant {

    List<String> clothesCategory;
    List<String> clothesSubCategory;

    public ClothesConstant(Context context) {
        String[] category = context.getResources().getStringArray(R.array.clothes_category);
       //String[] subCategory = context.getResources().getStringArray(R.array.)

    }

    public static void addClothesCategory() {

    }

}
