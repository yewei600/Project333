package com.ericwei.project333.wardrobe_tab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ericwei.project333.data.Clothes;

import java.util.List;

/**
 * Created by ericwei on 2017-03-29.
 */

public class ShowClothesItemsAdapter extends RecyclerView.Adapter<ShowClothesItemsAdapter.ViewHolder> {

    private Context context;
    private final List<Clothes> clothesItems;
    private final String[] subcategoryTitles;

    public ShowClothesItemsAdapter(Context context, List<Clothes> clothesItems, String[] subcategoryTitles) {
        this.context = context;
        this.clothesItems = clothesItems;
        this.subcategoryTitles = subcategoryTitles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.)
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
