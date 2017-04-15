package com.ericwei.project333;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitPickingData {

    private static ArrayList<Integer> outfitNumbers;

    private static ArrayList<Bitmap> outfitBitmaps;

    private static OutfitPickingData singleton = new OutfitPickingData();

    private OutfitPickingData() {
        if (outfitNumbers == null) {
            outfitNumbers = new ArrayList<>();
            outfitBitmaps = new ArrayList<>();
        }
    }

    public static OutfitPickingData getInstance() {
        return singleton;
    }

    public static void appendOutfitNumber(int num) {
        outfitNumbers.add(num);
    }

    public static void appendOutfitBitmap(Bitmap bitmap) {
        outfitBitmaps.add(bitmap);
    }

    public static ArrayList<Integer> getOutfitNumbers() {
        return outfitNumbers;
    }

    public static ArrayList<Bitmap> getOutfitBitmaps() {
        return outfitBitmaps;
    }

    public static int getListLength() {
        return outfitNumbers.size();
    }

}
