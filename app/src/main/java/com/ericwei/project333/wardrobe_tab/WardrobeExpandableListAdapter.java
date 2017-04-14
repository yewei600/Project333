package com.ericwei.project333.wardrobe_tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ericwei.project333.R;

/**
 * Created by ericwei on 2017-04-03.
 */

public class WardrobeExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    String[] listTitles;
    String[][] listDetails;

    public WardrobeExpandableListAdapter(Context context, String[] listTitles, String[][] listDetails) {
        this.context = context;
        this.listTitles = listTitles;
        this.listDetails = listDetails;
    }

    @Override
    public int getGroupCount() {
        return this.listTitles.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return this.listDetails[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return this.listTitles[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.listDetails[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String listTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.category_item_title, null);
        }
        TextView listTitleTextView = (TextView) view.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String expandedListText = (String) getChild(i, i1);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.category_item_detail, null);
        }
        TextView expandedListTextView = (TextView) view.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
