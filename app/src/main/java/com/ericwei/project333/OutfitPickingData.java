package com.ericwei.project333;

import java.util.ArrayList;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitPickingData {

    private static ArrayList<Integer> outfitNumbers;

    private static OutfitPickingData singleton = new OutfitPickingData();

    private OutfitPickingData() {
        if (outfitNumbers == null) {
            outfitNumbers = new ArrayList<>();
        }
    }

    public static OutfitPickingData getInstance() {
        return singleton;
    }

    public static void appendOutNumber(int num) {
        outfitNumbers.add(num);
    }

    public static ArrayList<Integer> getOutfitArrayList() {
        return outfitNumbers;
    }

    public static int getListLength() {
        return outfitNumbers.size();
    }

}
