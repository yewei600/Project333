package com.ericwei.project333;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ericwei.project333.data.Item;

import java.util.ArrayList;

/**
 * Created by ericwei on 2017-04-13.
 */

public class PickedOutfitImageAdapter extends RecyclerView.Adapter<PickedOutfitImageAdapter.ImageViewHolder> {

    private static final String TAG = PickedOutfitImageAdapter.class.getSimpleName();

    private ArrayList<Item> clothesItems;
    private ItemCardClickListener clickListener;
    private Context context;

    public interface ItemCardClickListener {
        void onItemClick(Item item);
    }

    public PickedOutfitImageAdapter(ItemCardClickListener clickListener, Context context) {
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.pick_outfit_card, parent, shouldAttachToParentImmediately);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return clothesItems.size();
    }

    public void setItemData(ArrayList<Item> itemData) {
        clothesItems = itemData;
        Log.d(TAG, "clothesItems length==" + clothesItems.size());
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            this.itemView = (ImageView) itemView.findViewById(R.id.iv_item_image);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(clothesItems.get(getAdapterPosition()));
        }

        void bind(int position) {
            itemView.setImageBitmap(clothesItems.get(position).getItemImage());
        }
    }
}
