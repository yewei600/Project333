package com.ericwei.project333.wardrobe_tab;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.ericwei.project333.R;
import com.ericwei.project333.clothes_tab.AddClothesActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WardrobeFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;

    public static final String[] clothesCategory = {""};

    public WardrobeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wardrobe, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.simple_expandable_listview);
        setItems();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Intent intent = new Intent(getContext(), AddClothesActivity.class);
            intent.putExtra("Image", imageBitmap);
            startActivity(intent);
        }

    }

    // Setting headers and childs to expandable listview
    void setItems() {

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();
        List<String> child4 = new ArrayList<String>();

        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

        // Adding headers to list
        for (int i = 1; i < 5; i++) {
            header.add("Group " + i);
        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child1.add("Group 1  " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 5; i++) {
            child2.add("Group 2  " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 6; i++) {
            child3.add("Group 3  " + " : Child" + i);
        }
        // Adding child data
        for (int i = 1; i < 7; i++) {
            child4.add("Group 4  " + " : Child" + i);
        }

        // Adding header and childs to hash map
        hashMap.put(header.get(0), child1);
        hashMap.put(header.get(1), child2);
        hashMap.put(header.get(2), child3);
        hashMap.put(header.get(3), child4);

        adapter = new com.ericwei.project333.ExpandableListAdapter(getContext(), header, hashMap);
        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);

    }
}
