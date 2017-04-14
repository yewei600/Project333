package com.ericwei.project333;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ericwei.project333.data.Item;

/**
 * Created by ericwei on 2017-04-05.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Item[] clothesItems;
    private ItemCardClickListener clickListener;
    private Context context;

    public interface ItemCardClickListener {
        void onItemClick(Item item);
    }

    public ImageAdapter(ItemCardClickListener clickListener, Context context) {
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item_card, parent, shouldAttachToParentImmediately);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (clothesItems == null) return 0;
        return clothesItems.length;
    }

    public void setItemData(Item[] itemData) {
        clothesItems = itemData;
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
            clickListener.onItemClick(clothesItems[getAdapterPosition()]);
        }

        void bind(int position) {
            itemView.setImageBitmap(clothesItems[position].getItemImage());
        }
    }
}
