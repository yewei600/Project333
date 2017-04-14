package com.ericwei.project333;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class PickOutfitActivity extends AppCompatActivity {

    private static final String TAG = PickOutfitActivity.class.getSimpleName();

    private ListView listView;
    private GridView gridView;
    private String[] categoryNames;
    private static ArrayList<Integer> itemNumbers;
    private OutfitImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()!!!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_outfit);

        listView = (ListView) findViewById(R.id.listView);
        gridView = (GridView) findViewById(R.id.gridView);

        categoryNames = getResources().getStringArray(R.array.clothes_category);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, categoryNames);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PickOutfitActivity.this, ClothesImagesActivity.class);
                intent.putExtra("category", categoryNames[i]);
                intent.putExtra("selecting", true);
                startActivity(intent);
            }
        });

        //ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_2,)
        imageAdapter = new OutfitImageAdapter(this);
        gridView.setAdapter(imageAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "len of pickingArray = " + OutfitPickingData.getInstance().getListLength());
        // imageAdapter.notifyDataSetChanged();
    }
}
