package com.ericwei.project333.wardrobe_tab;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.ericwei.project333.R;
import com.ericwei.project333.clothes_tab.AddClothesActivity;
import com.ericwei.project333.data.ClothesContract.ClothesEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WardrobeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = WardrobeFragment.class.getSimpleName();

    private ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;

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

        String[] category = getContext().getResources().getStringArray(R.array.clothes_category);

        // Array list for header
        ArrayList<String> header = new ArrayList<String>();

        // Array list for child items
        List<String> topsList = new ArrayList<String>();
        List<String> bottomsList = new ArrayList<String>();
        List<String> shoesList = new ArrayList<String>();
        List<String> dressesList = new ArrayList<String>();
        List<String> accessoriesList = new ArrayList<String>();
        List<String> outerwearList = new ArrayList<String>();

        // Hash map for both header and child
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();

//        // Adding headers to list
        for (int i = 0; i < category.length; i++) {
            header.add(category[i]);
        }
        topsList.add("no items added yet!");
        bottomsList.add("no items added yet!");
        shoesList.add("no items added yet!");
        dressesList.add("no items added yet!");
        accessoriesList.add("no items added yet!");
        outerwearList.add("no items added yet!");


//        // Adding child data
//        for (int i = 1; i < 5; i++) {
//            child1.add("Group 1  " + " : Child" + i);
//        }
//        // Adding child data
//        for (int i = 1; i < 5; i++) {
//            child2.add("Group 2  " + " : Child" + i);
//        }
//        // Adding child data
//        for (int i = 1; i < 6; i++) {
//            child3.add("Group 3  " + " : Child" + i);
//        }
//        // Adding child data
//        for (int i = 1; i < 7; i++) {
//            child4.add("Group 4  " + " : Child" + i);
//        }


        // Adding header and childs to hash map
        hashMap.put(header.get(0), topsList);
        hashMap.put(header.get(1), bottomsList);
        hashMap.put(header.get(2), shoesList);
        hashMap.put(header.get(3), dressesList);
        hashMap.put(header.get(4), accessoriesList);
        hashMap.put(header.get(5), outerwearList);

        adapter = new com.ericwei.project333.ExpandableListAdapter(getContext(), header, hashMap);
        // Setting adpater over expandablelistview
        expandableListView.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getContext()) {
            Cursor clothesItem = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
//                if (clothesItem !)
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    Cursor retCursor = getContext().getContentResolver().query(ClothesEntry.CONTENT_URI,
                            null, null, null,
                            ClothesEntry.COLUMN_ID);
                    return retCursor;
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
