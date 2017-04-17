package com.ericwei.project333.wardrobe_tab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericwei.project333.ItemsDatabaseUtils;
import com.ericwei.project333.R;

import java.util.List;

/**
 * Created by ericwei on 2017-04-03.
 */

public class CategoryCardsAdapter extends RecyclerView.Adapter<CategoryCardsAdapter.ViewHolder> {

    private final List<String> categoryItems;
    private final int[] categoryItemsCount;
    private Context context;

    private CategoryCardClickListener clickListener;

    public interface CategoryCardClickListener {
        void onItemClick(int cardIndex);
    }

    public CategoryCardsAdapter(List<String> categoryItems, CategoryCardClickListener listener, Context context) {
        this.categoryItems = categoryItems;
        this.clickListener = listener;
        this.context = context;
        this.categoryItemsCount = new int[categoryItems.size()];
    }

    @Override
    public CategoryCardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_category_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryCardsAdapter.ViewHolder holder, int position) {
        holder.categoryName.setText(categoryItems.get(position));
        if (categoryItemsCount[position] < 2) {
            holder.numItems.setText(categoryItemsCount[position] + " item");
        } else {
            holder.numItems.setText(categoryItemsCount[position] + " items");
        }
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public void updateCategoryItemCount() {
        for (int i = 0; i < categoryItems.size(); i++) {
            categoryItemsCount[i] = ItemsDatabaseUtils.numItemForCategory(context, categoryItems.get(i));
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView categoryName;
        private TextView numItems;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.category_title);
            numItems = (TextView) itemView.findViewById(R.id.num_items);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }
}