package com.ericwei.project333;

import com.ericwei.project333.data.Item;

import java.util.ArrayList;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitPickingData {

    private static ArrayList<Item> outfitItems;

    private static OutfitPickingData singleton = new OutfitPickingData();

    private OutfitPickingData() {
        if (outfitItems == null) {
            outfitItems = new ArrayList<>();
        }
    }

    public static OutfitPickingData getInstance() {
        return singleton;
    }

    public static void appendOutfitItems(Item item) {
        outfitItems.add(item);
    }

    public static ArrayList<Item> getOutfitItems() {
        return outfitItems;
    }

    public static int getListLength() {
        return outfitItems.size();
    }

}
