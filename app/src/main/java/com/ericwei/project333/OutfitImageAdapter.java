package com.ericwei.project333;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ericwei on 2017-04-13.
 */

public class OutfitImageAdapter extends BaseAdapter {
    private Context context;

    public OutfitImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return OutfitPickingData.getInstance().getOutfitBitmaps().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if (view == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) view;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);

        imageView.setImageBitmap(OutfitPickingData.getInstance().getOutfitBitmaps().get(i));
        return imageView;
    }
}
